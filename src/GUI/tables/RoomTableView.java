package GUI.tables;

import GUI.popups.ConfirmationPopUp;
import GUI.popups.ErrorPopUp;
import GUI.popups.ItemOperationsPopUp;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.xml.internal.ws.util.StringUtils;
import database.HibernateSingleton;
import database.entities.BuildingEntity;
import database.entities.RoomEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.hibernate.SessionFactory;
import services.BuildingService;
import services.RoomService;
import utils.UtilityFunctions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomTableView {

    private TableView table = new TableView();
    private SessionFactory sessionFactory = HibernateSingleton.getInstance();
    private List<String> columnNames = new ArrayList<>();
    private UtilityFunctions utils = new UtilityFunctions();
    private RoomService service = new RoomService();
    private StackPane stackPane;
    private ObservableList<RoomEntity> list;
    private FilteredList<RoomEntity> filteredData;

    public RoomTableView (StackPane stackPane){
        this.stackPane = stackPane;
        ArrayList<String> properties = new ArrayList<>();
        properties.add(sessionFactory.getClassMetadata(RoomEntity.class).getIdentifierPropertyName());
        String[] propertyNames = sessionFactory.getClassMetadata(RoomEntity.class).getPropertyNames();
        properties.addAll(Arrays.asList(propertyNames));
        for(String property : properties){
            if(!property.contains("Entity")){
                String columnName = StringUtils.capitalize(utils.getHumanReadable(property));
                columnNames.add(columnName);
                TableColumn tableColumn = new TableColumn(columnName);
                tableColumn.setPrefWidth(1360 / propertyNames.length);
                tableColumn.setCellValueFactory(new PropertyValueFactory<RoomEntity, String>(property));
                table.getColumns().add(tableColumn);
            }
        }

        refresh();

    }

    public void deleteItem(){
        RoomEntity entity = (RoomEntity) table.getSelectionModel().getSelectedItem();
        if(table.getSelectionModel().getSelectedIndex() != -1){
            ConfirmationPopUp popUp = new ConfirmationPopUp(stackPane);
            popUp.getConfirm().setOnAction(confirm -> {
                service.delete(entity.getRoomId());
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
        BuildingService buildingService = new BuildingService();
        List<BuildingEntity> buildingEntities = buildingService.findAll();
        JFXComboBox<Integer> buildingIds = new JFXComboBox<>();

        for(BuildingEntity b_entity : buildingEntities){
            buildingIds.getItems().add(b_entity.getBuildingId());
        }

        buildingIds.setPromptText("Choose building id");
        buildingIds.setLabelFloat(true);

        VBox inputs = new VBox(32);
        JFXTextField roomType = new JFXTextField();
        JFXTextField roomSpace = new JFXTextField();
        roomType.setPromptText("Room type");
        roomType.setLabelFloat(true);
        roomSpace.setPromptText("Room space in square meters");
        roomSpace.setLabelFloat(true);

        inputs.getChildren().addAll(buildingIds, roomType, roomSpace);

        ItemOperationsPopUp popUp = new ItemOperationsPopUp(stackPane, inputs);
        popUp.getConfirm().setOnAction(confirm -> {
            if(roomType.equals("") || buildingIds.getSelectionModel().isEmpty()){
                new ErrorPopUp("Fields can't be empty", stackPane);
            } else if(!roomSpace.getText().matches("[0-9]+") || roomSpace.getText().startsWith("0") || roomSpace.getText().equals("") ) {
                new ErrorPopUp("Wrong number of rooms", stackPane);
            } else {
                RoomEntity entity = new RoomEntity();
                entity.setRoomType(StringUtils.capitalize(roomType.getText()));
                entity.setRoomSpace(Integer.valueOf(roomSpace.getText()));
                entity.setBuildingId(buildingIds.getSelectionModel().getSelectedItem());
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
            RoomEntity entity = (RoomEntity) table.getSelectionModel().getSelectedItem();
            BuildingService buildingService = new BuildingService();
            List<BuildingEntity> buildingEntities = buildingService.findAll();
            JFXComboBox<Integer> buildingIds = new JFXComboBox<>();

            for (BuildingEntity b_entity : buildingEntities) {
                buildingIds.getItems().add(b_entity.getBuildingId());
            }

            buildingIds.setPromptText("Choose building id");
            buildingIds.setLabelFloat(true);
            buildingIds.getSelectionModel().select(entity.getBuildingId());

            VBox inputs = new VBox(32);
            JFXTextField roomType = new JFXTextField();
            JFXTextField roomSpace = new JFXTextField();
            roomType.setPromptText("Room type");
            roomType.setLabelFloat(true);
            roomType.setText(entity.getRoomType());
            roomSpace.setPromptText("Room space in square meters");
            roomSpace.setLabelFloat(true);
            roomSpace.setText(String.valueOf(entity.getRoomSpace()));

            inputs.getChildren().addAll(buildingIds, roomType, roomSpace);

            ItemOperationsPopUp popUp = new ItemOperationsPopUp(stackPane, inputs);
            popUp.getConfirm().setOnAction(confirm -> {
                if (roomType.equals("") || buildingIds.getSelectionModel().isEmpty()) {
                    new ErrorPopUp("Fields can't be empty", stackPane);
                } else if (!roomSpace.getText().matches("[0-9]+") || roomSpace.getText().startsWith("0") || roomSpace.getText().equals("")) {
                    new ErrorPopUp("Wrong number of rooms", stackPane);
                } else {
                    entity.setRoomType(StringUtils.capitalize(roomType.getText()));
                    entity.setRoomSpace(Integer.valueOf(roomSpace.getText()));
                    entity.setBuildingId(buildingIds.getSelectionModel().getSelectedItem());
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
