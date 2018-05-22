package GUI.tables;

import GUI.popups.ConfirmationPopUp;
import GUI.popups.ErrorPopUp;
import GUI.popups.ItemOperationsPopUp;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.xml.internal.ws.util.StringUtils;
import database.HibernateSingleton;
import database.entities.RoomEntity;
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
import services.RoomService;
import services.SensorService;
import utils.UtilityFunctions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SensorTableView {

    private TableView table = new TableView();
    private SessionFactory sessionFactory = HibernateSingleton.getInstance();
    private List<String> columnNames = new ArrayList<>();
    private UtilityFunctions utils = new UtilityFunctions();
    private SensorService service = new SensorService();
    private StackPane stackPane;
    private ObservableList<SensorEntity> list;
    private FilteredList<SensorEntity> filteredData;

    public SensorTableView (StackPane stackPane){
        this.stackPane = stackPane;
        ArrayList<String> properties = new ArrayList<>();
        properties.add(sessionFactory.getClassMetadata(SensorEntity.class).getIdentifierPropertyName());
        String[] propertyNames = sessionFactory.getClassMetadata(SensorEntity.class).getPropertyNames();
        properties.addAll(Arrays.asList(propertyNames));
        for(String property : properties){
            if(!property.contains("Entity")){
                String columnName = StringUtils.capitalize(utils.getHumanReadable(property));
                columnNames.add(columnName);
                TableColumn tableColumn = new TableColumn(columnName);
                tableColumn.setPrefWidth(1360 / propertyNames.length);
                tableColumn.setCellValueFactory(new PropertyValueFactory<SensorEntity, String>(property));
                table.getColumns().add(tableColumn);
            }
        }

        refresh();

    }

    public void deleteItem(){
        SensorEntity entity = (SensorEntity) table.getSelectionModel().getSelectedItem();
        if(table.getSelectionModel().getSelectedIndex() != -1){
            ConfirmationPopUp popUp = new ConfirmationPopUp(stackPane);
            popUp.getConfirm().setOnAction(confirm -> {
                service.delete(entity.getSensorId());
                refresh();
                popUp.close();
            });

            popUp.getCancel().setOnAction(cancel -> {
                popUp.close();
            });

            popUp.show();
        }
    }

    public void addItem(){
        RoomService roomService = new RoomService();
        List<RoomEntity> roomEntities = roomService.findAll();
        JFXComboBox<Integer> roomIds = new JFXComboBox<>();

        for(RoomEntity r_entity : roomEntities){
            roomIds.getItems().add(r_entity.getRoomId());
        }

        roomIds.setPromptText("Choose room id");
        roomIds.setLabelFloat(true);

        VBox inputs = new VBox(32);
        JFXTextField deviceType = new JFXTextField();
        JFXTextField serialNumber = new JFXTextField();
        JFXTextField manufacturer = new JFXTextField();
        deviceType.setPromptText("Device type");
        deviceType.setLabelFloat(true);
        serialNumber.setPromptText("Serial number");
        serialNumber.setLabelFloat(true);
        manufacturer.setPromptText("Manufacturer");
        manufacturer.setLabelFloat(true);

        inputs.getChildren().addAll(roomIds, deviceType, serialNumber, manufacturer);

        ItemOperationsPopUp popUp = new ItemOperationsPopUp(stackPane, inputs);
        popUp.getConfirm().setOnAction(confirm -> {
            String deviceVal = deviceType.getText();
            String serialVal = serialNumber.getText();
            String manufacturerVal = StringUtils.capitalize(manufacturer.getText());
            Integer roomVal = roomIds.getSelectionModel().getSelectedItem();
            if(deviceVal.equals("") || serialVal.equals("") || manufacturerVal.equals("") || roomIds.getSelectionModel().isEmpty()){
                new ErrorPopUp("Fields can't be empty", stackPane);
            } else {
                SensorEntity entity = new SensorEntity();
                entity.setRoomId(roomVal);
                entity.setSensorType(deviceVal);
                entity.setSerialNumber(serialVal);
                entity.setProducent(manufacturerVal);
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
            SensorEntity entity = (SensorEntity) table.getSelectionModel().getSelectedItem();
            RoomService roomService = new RoomService();
            List<RoomEntity> roomEntities = roomService.findAll();
            JFXComboBox<Integer> roomIds = new JFXComboBox<>();

            for(RoomEntity r_entity : roomEntities){
                roomIds.getItems().add(r_entity.getRoomId());
            }

            roomIds.setPromptText("Choose room id");
            roomIds.setLabelFloat(true);
            roomIds.getSelectionModel().select(entity.getRoomId());

            VBox inputs = new VBox(32);
            JFXTextField deviceType = new JFXTextField();
            JFXTextField serialNumber = new JFXTextField();
            JFXTextField manufacturer = new JFXTextField();
            deviceType.setPromptText("Device type");
            deviceType.setLabelFloat(true);
            deviceType.setText(entity.getSensorType());
            serialNumber.setPromptText("Serial number");
            serialNumber.setLabelFloat(true);
            serialNumber.setText(entity.getSerialNumber());
            manufacturer.setPromptText("Manufacturer");
            manufacturer.setLabelFloat(true);
            manufacturer.setText(entity.getProducent());

            inputs.getChildren().addAll(roomIds, deviceType, serialNumber, manufacturer);

            ItemOperationsPopUp popUp = new ItemOperationsPopUp(stackPane, inputs);
            popUp.getConfirm().setOnAction(confirm -> {
                String deviceVal = deviceType.getText();
                String serialVal = serialNumber.getText();
                String manufacturerVal = StringUtils.capitalize(manufacturer.getText());
                Integer roomVal = roomIds.getSelectionModel().getSelectedItem();
                if(deviceVal.equals("") || serialVal.equals("") || manufacturerVal.equals("") || roomIds.getSelectionModel().isEmpty()){
                    new ErrorPopUp("Fields can't be empty", stackPane);
                } else {
                    entity.setRoomId(roomVal);
                    entity.setSensorType(deviceVal);
                    entity.setSerialNumber(serialVal);
                    entity.setProducent(manufacturerVal);
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
