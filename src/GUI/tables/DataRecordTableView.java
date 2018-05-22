package GUI.tables;

import GUI.popups.ConfirmationPopUp;
import GUI.popups.ErrorPopUp;
import GUI.popups.ItemOperationsPopUp;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.xml.internal.ws.util.StringUtils;
import database.entities.ClientEntity;
import database.entities.DataRecordEntity;
import database.HibernateSingleton;
import database.entities.SensorEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.hibernate.SessionFactory;
import services.DataRecordService;
import services.SensorService;
import utils.UtilityFunctions;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataRecordTableView {

    private TableView table = new TableView();
    private SessionFactory sessionFactory = HibernateSingleton.getInstance();
    private List<String> columnNames = new ArrayList<>();
    private UtilityFunctions utils = new UtilityFunctions();
    private DataRecordService service = new DataRecordService();
    private StackPane stackPane;
    private ObservableList<DataRecordEntity> list;
    private FilteredList<DataRecordEntity> filteredData;

    public DataRecordTableView (StackPane stackPane){
        this.stackPane = stackPane;
        ArrayList<String> properties = new ArrayList<>();
        properties.add(sessionFactory.getClassMetadata(DataRecordEntity.class).getIdentifierPropertyName());
        String[] propertyNames = sessionFactory.getClassMetadata(DataRecordEntity.class).getPropertyNames();
        properties.addAll(Arrays.asList(propertyNames));
        for(String property : properties){
            if(!property.contains("Entity")){
                String columnName = StringUtils.capitalize(utils.getHumanReadable(property));
                columnNames.add(columnName);
                TableColumn tableColumn = new TableColumn(columnName);
                tableColumn.setPrefWidth(1360 / propertyNames.length);
                tableColumn.setCellValueFactory(new PropertyValueFactory<DataRecordEntity, String>(property));
                table.getColumns().add(tableColumn);
            }
        }

        refresh();

    }

    public void deleteItem(){
        DataRecordEntity entity = (DataRecordEntity) table.getSelectionModel().getSelectedItem();
        if(table.getSelectionModel().getSelectedIndex() != -1){
            ConfirmationPopUp popUp = new ConfirmationPopUp(stackPane);
            popUp.getConfirm().setOnAction(confirm -> {
                service.delete(entity.getRecordId());
                refresh();
                popUp.close();
            });

            popUp.getCancel().setOnAction(cancel -> {
                popUp.close();
            });

            popUp.show();
        }

        refresh();
    }

    public void addItem(){
        SensorService sensorService = new SensorService();
        List<SensorEntity> sensorEntities = sensorService.findAll();
        JFXComboBox<Integer> sensorIds = new JFXComboBox<>();

        for(SensorEntity s_entity : sensorEntities){
            sensorIds.getItems().add(s_entity.getSensorId());
        }

        sensorIds.setPromptText("Choose sensor id");
        sensorIds.setLabelFloat(true);

        VBox inputs = new VBox(32);
        JFXTextField record = new JFXTextField();
        JFXTextField dataType = new JFXTextField();
        record.setPromptText("Data");
        record.setLabelFloat(true);
        dataType.setPromptText("Data type");
        dataType.setLabelFloat(true);


        inputs.getChildren().addAll(sensorIds, record, dataType);

        ItemOperationsPopUp popUp = new ItemOperationsPopUp(stackPane, inputs);
        popUp.getConfirm().setOnAction(confirm -> {
            String recordVal = record.getText();
            String typeVal = dataType.getText();
            Integer sensorVal = sensorIds.getSelectionModel().getSelectedItem();
            if(recordVal.equals("") || typeVal.equals("") || sensorIds.getSelectionModel().isEmpty()){
                new ErrorPopUp("Fields can't be empty", stackPane);
            } else {
                DataRecordEntity entity = new DataRecordEntity();
                entity.setRecord(recordVal);
                entity.setDataType(typeVal);
                entity.setSensorId(sensorVal);

                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                entity.setTimestap(timestamp);
                service.create(entity);
                refresh();
            }
            popUp.close();
        });

        popUp.getCancel().setOnAction(cancel -> {
            popUp.close();
        });

        popUp.show();
    }

    public void updateItem(){
        if (table.getSelectionModel().getSelectedIndex() != -1) {
            DataRecordEntity entity = (DataRecordEntity) table.getSelectionModel().getSelectedItem();
            SensorService sensorService = new SensorService();
            List<SensorEntity> sensorEntities = sensorService.findAll();
            JFXComboBox<Integer> sensorIds = new JFXComboBox<>();

            for(SensorEntity s_entity : sensorEntities){
                sensorIds.getItems().add(s_entity.getSensorId());
            }

            sensorIds.setPromptText("Choose sensor id");
            sensorIds.setLabelFloat(true);
            sensorIds.getSelectionModel().select(entity.getSensorId());

            VBox inputs = new VBox(32);
            JFXTextField record = new JFXTextField();
            JFXTextField dataType = new JFXTextField();
            record.setPromptText("Data");
            record.setLabelFloat(true);
            record.setText(entity.getRecord());
            dataType.setPromptText("Data type");
            dataType.setLabelFloat(true);
            dataType.setText(entity.getDataType());


            inputs.getChildren().addAll(sensorIds, record, dataType);

            ItemOperationsPopUp popUp = new ItemOperationsPopUp(stackPane, inputs);
            popUp.getConfirm().setOnAction(confirm -> {
                String recordVal = record.getText();
                String typeVal = dataType.getText();
                Integer sensorVal = sensorIds.getSelectionModel().getSelectedItem();
                if(recordVal.equals("") || typeVal.equals("") || sensorIds.getSelectionModel().isEmpty()){
                    new ErrorPopUp("Fields can't be empty", stackPane);
                } else {
                    entity.setRecord(recordVal);
                    entity.setDataType(typeVal);
                    entity.setSensorId(sensorVal);

                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    entity.setTimestap(timestamp);
                    service.update(entity);
                    refresh();
                }
                popUp.close();
            });

            popUp.getCancel().setOnAction(cancel -> {
                popUp.close();
            });

            popUp.show();
        }
    }

    public void refresh(){
        list = FXCollections.observableArrayList(service.findAll());
        table.setItems(list);
        table.refresh();
    }

    public void setFilter(TabContent content) {
        filteredData = new FilteredList<>(list, p -> true);
        content.getFilterField().textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(item -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (item.toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
            table.setItems(filteredData);
        });
    }

    public TableView getTable() {
        return table;
    }

}
