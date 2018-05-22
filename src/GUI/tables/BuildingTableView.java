package GUI.tables;

import GUI.popups.ConfirmationPopUp;
import GUI.popups.ErrorPopUp;
import GUI.popups.ItemOperationsPopUp;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.xml.internal.ws.util.StringUtils;
import database.entities.AdministrationEntity;
import database.entities.BuildingEntity;
import database.HibernateSingleton;
import database.entities.ServerEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.hibernate.SessionFactory;
import services.AdministrationService;
import services.BuildingService;
import services.ServerService;
import utils.UtilityFunctions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuildingTableView {

    private TableView table = new TableView();
    private SessionFactory sessionFactory = HibernateSingleton.getInstance();
    private List<String> columnNames = new ArrayList<>();
    private UtilityFunctions utils = new UtilityFunctions();
    private BuildingService service = new BuildingService();
    private StackPane stackPane;
    private ObservableList<BuildingEntity> list;
    private FilteredList<BuildingEntity> filteredData;

    public BuildingTableView (StackPane stackPane){
        this.stackPane = stackPane;
        ArrayList<String> properties = new ArrayList<>();
        properties.add(sessionFactory.getClassMetadata(BuildingEntity.class).getIdentifierPropertyName());
        String[] propertyNames = sessionFactory.getClassMetadata(BuildingEntity.class).getPropertyNames();
        properties.addAll(Arrays.asList(propertyNames));
        for(String property : properties){
            if(!property.contains("Entity")){
                String columnName = StringUtils.capitalize(utils.getHumanReadable(property));
                columnNames.add(columnName);
                TableColumn tableColumn = new TableColumn(columnName);
                tableColumn.setPrefWidth(1360 / propertyNames.length);
                tableColumn.setCellValueFactory(new PropertyValueFactory<BuildingEntity, String>(property));
                table.getColumns().add(tableColumn);
            }
        }
        refresh();
    }

    public void deleteItem(){
        BuildingEntity entity = (BuildingEntity) table.getSelectionModel().getSelectedItem();
        if(table.getSelectionModel().getSelectedIndex() != -1){
            ConfirmationPopUp popUp = new ConfirmationPopUp(stackPane);
            popUp.getConfirm().setOnAction(confirm -> {
                service.delete(entity.getBuildingId());
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
        ServerService serverService = new ServerService();
        List<ServerEntity> serverEntities = serverService.findAll();
        JFXComboBox<Integer> serverIds = new JFXComboBox<>();

        for(ServerEntity s_entity : serverEntities){
            serverIds.getItems().add(s_entity.getServerId());
        }

        serverIds.setPromptText("Choose server id");
        serverIds.setLabelFloat(true);

        VBox inputs = new VBox(32);
        JFXTextField address = new JFXTextField();
        JFXTextField numberOfRooms = new JFXTextField();
        address.setPromptText("Address");
        address.setLabelFloat(true);
        numberOfRooms.setPromptText("Number of rooms");
        numberOfRooms.setLabelFloat(true);

        inputs.getChildren().addAll(serverIds, address, numberOfRooms);

        ItemOperationsPopUp popUp = new ItemOperationsPopUp(stackPane, inputs);
        popUp.getConfirm().setOnAction(confirm -> {
            if(address.equals("") || serverIds.getSelectionModel().isEmpty()){
                new ErrorPopUp("Fields can't be empty", stackPane);
            } else if(!numberOfRooms.getText().matches("[0-9]+") || numberOfRooms.getText().startsWith("0") || numberOfRooms.getText().equals("") ) {
                new ErrorPopUp("Wrong number of rooms", stackPane);
            } else {
                BuildingEntity entity = new BuildingEntity();
                entity.setBuildingAddress(address.getText());
                entity.setNumberOfRooms(Integer.valueOf(numberOfRooms.getText()));
                entity.setServerId(serverIds.getSelectionModel().getSelectedItem());
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

    public void updateItem() {
        if (table.getSelectionModel().getSelectedIndex() != -1) {
            BuildingEntity entity = (BuildingEntity) table.getSelectionModel().getSelectedItem();
            ServerService serverService = new ServerService();
            List<ServerEntity> serverEntities = serverService.findAll();
            JFXComboBox<Integer> serverIds = new JFXComboBox<>();

            for (ServerEntity s_entity : serverEntities) {
                serverIds.getItems().add(s_entity.getServerId());
            }

            serverIds.setPromptText("Choose server id");
            serverIds.setLabelFloat(true);
            serverIds.getSelectionModel().select(entity.getServerId());

            VBox inputs = new VBox(32);
            JFXTextField address = new JFXTextField();
            JFXTextField numberOfRooms = new JFXTextField();
            address.setPromptText("Address");
            address.setLabelFloat(true);
            address.setText(entity.getBuildingAddress());
            numberOfRooms.setPromptText("Number of rooms");
            numberOfRooms.setLabelFloat(true);
            numberOfRooms.setText(String.valueOf(entity.getNumberOfRooms()));

            inputs.getChildren().addAll(serverIds, address, numberOfRooms);

            ItemOperationsPopUp popUp = new ItemOperationsPopUp(stackPane, inputs);
            popUp.getConfirm().setOnAction(confirm -> {
                if (address.equals("") || serverIds.getSelectionModel().isEmpty()) {
                    new ErrorPopUp("Fields can't be empty", stackPane);
                } else if (!numberOfRooms.getText().matches("[0-9]+") || numberOfRooms.getText().startsWith("0") || numberOfRooms.getText().equals("")) {
                    new ErrorPopUp("Wrong number of rooms", stackPane);
                } else {
                    entity.setBuildingAddress(address.getText());
                    entity.setNumberOfRooms(Integer.valueOf(numberOfRooms.getText()));
                    entity.setServerId(serverIds.getSelectionModel().getSelectedItem());
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
