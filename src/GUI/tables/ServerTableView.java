package GUI.tables;

import GUI.popups.ConfirmationPopUp;
import GUI.popups.ErrorPopUp;
import GUI.popups.ItemOperationsPopUp;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.sun.xml.internal.ws.util.StringUtils;
import database.HibernateSingleton;
import database.entities.AdministrationEntity;
import database.entities.ClientEntity;
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
import services.ClientService;
import services.ServerService;
import utils.UtilityFunctions;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerTableView {

    private TableView table = new TableView();
    private SessionFactory sessionFactory = HibernateSingleton.getInstance();
    private List<String> columnNames = new ArrayList<>();
    private UtilityFunctions utils = new UtilityFunctions();
    private ServerService service = new ServerService();
    private StackPane stackPane;
    private ObservableList<ServerEntity> list;
    private FilteredList<ServerEntity> filteredData;

    public ServerTableView (StackPane stackPane){
        this.stackPane = stackPane;
        ArrayList<String> properties = new ArrayList<>();
        properties.add(sessionFactory.getClassMetadata(ServerEntity.class).getIdentifierPropertyName());
        String[] propertyNames = sessionFactory.getClassMetadata(ServerEntity.class).getPropertyNames();
        properties.addAll(Arrays.asList(propertyNames));
        for(String property : properties){
            if(!property.contains("Entity")){
                String columnName = StringUtils.capitalize(utils.getHumanReadable(property));
                columnNames.add(columnName);
                TableColumn tableColumn = new TableColumn(columnName);
                tableColumn.setPrefWidth(1360 / propertyNames.length);
                tableColumn.setCellValueFactory(new PropertyValueFactory<ServerEntity, String>(property));
                table.getColumns().add(tableColumn);
            }
        }

        refresh();
    }

    public void deleteItem(){
        ServerEntity entity = (ServerEntity) table.getSelectionModel().getSelectedItem();
        if(table.getSelectionModel().getSelectedIndex() != -1){
            ConfirmationPopUp popUp = new ConfirmationPopUp(stackPane);
            popUp.getConfirm().setOnAction(confirm -> {
                service.delete(entity.getServerId());
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
        AdministrationService administrationService = new AdministrationService();
        ClientService clientService = new ClientService();

        List<AdministrationEntity> administrationEntities = administrationService.findAll();
        List<ClientEntity> clientEntities = clientService.findAll();

        JFXComboBox<Integer> administrationIds = new JFXComboBox<>();
        JFXComboBox<Integer> clientIds = new JFXComboBox<>();

        for(AdministrationEntity a_entity : administrationEntities){
            administrationIds.getItems().add(a_entity.getAdministrationId());
        }

        for(ClientEntity c_entity : clientEntities){
            clientIds.getItems().add(c_entity.getClientId());
        }

        administrationIds.setPromptText("Choose administration id");
        administrationIds.setLabelFloat(true);
        clientIds.setPromptText("Choose subcontractor id");
        clientIds.setLabelFloat(true);

        VBox inputs = new VBox(32);
        JFXTextField serverName = new JFXTextField();
        serverName.setPromptText("Server name");
        serverName.setLabelFloat(true);

        inputs.getChildren().addAll(administrationIds, clientIds, serverName);

        ItemOperationsPopUp popUp = new ItemOperationsPopUp(stackPane, inputs);
        popUp.getConfirm().setOnAction(confirm -> {
            if (serverName.getText().equals("")) {
                new ErrorPopUp("Wrong server name", stackPane);
            } else if (administrationIds.getSelectionModel().isEmpty() || clientIds.getSelectionModel().isEmpty()){
                new ErrorPopUp("Wrong ids", stackPane);
            } else {
                String serverNameVal = StringUtils.capitalize(serverName.getText());
                Integer administrationVal = administrationIds.getSelectionModel().getSelectedItem();
                Integer clientVal = clientIds.getSelectionModel().getSelectedItem();
                ServerEntity entity = new ServerEntity();
                entity.setAdministrationId(administrationVal);
                entity.setClientId(clientVal);
                entity.setServerName(serverNameVal);
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
            ServerEntity entity = (ServerEntity) table.getSelectionModel().getSelectedItem();
            AdministrationService administrationService = new AdministrationService();
            ClientService clientService = new ClientService();

            List<AdministrationEntity> administrationEntities = administrationService.findAll();
            List<ClientEntity> clientEntities = clientService.findAll();

            JFXComboBox<Integer> administrationIds = new JFXComboBox<>();
            JFXComboBox<Integer> clientIds = new JFXComboBox<>();

            for(AdministrationEntity a_entity : administrationEntities){
                administrationIds.getItems().add(a_entity.getAdministrationId());
            }

            for(ClientEntity c_entity : clientEntities){
                clientIds.getItems().add(c_entity.getClientId());
            }

            administrationIds.setPromptText("Choose administration id");
            administrationIds.setLabelFloat(true);
            administrationIds.getSelectionModel().select(entity.getAdministrationId());
            clientIds.setPromptText("Choose subcontractor id");
            clientIds.setLabelFloat(true);
            clientIds.getSelectionModel().select(entity.getClientId());

            VBox inputs = new VBox(32);
            JFXTextField serverName = new JFXTextField();
            serverName.setPromptText("Server name");
            serverName.setLabelFloat(true);
            serverName.setText(entity.getServerName());

            inputs.getChildren().addAll(administrationIds, clientIds, serverName);

            ItemOperationsPopUp popUp = new ItemOperationsPopUp(stackPane, inputs);
            popUp.getConfirm().setOnAction(confirm -> {
                if (serverName.getText().equals("")) {
                    new ErrorPopUp("Wrong server name", stackPane);
                } else if (administrationIds.getSelectionModel().isEmpty() || clientIds.getSelectionModel().isEmpty()){
                    new ErrorPopUp("Wrong ids", stackPane);
                } else {
                    String serverNameVal = StringUtils.capitalize(serverName.getText());
                    Integer administrationVal = administrationIds.getSelectionModel().getSelectedItem();
                    Integer clientVal = clientIds.getSelectionModel().getSelectedItem();
                    entity.setAdministrationId(administrationVal);
                    entity.setClientId(clientVal);
                    entity.setServerName(serverNameVal);
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
