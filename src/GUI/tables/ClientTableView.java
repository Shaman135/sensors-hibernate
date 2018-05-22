package GUI.tables;

import GUI.popups.ConfirmationPopUp;
import GUI.popups.ErrorPopUp;
import GUI.popups.ItemOperationsPopUp;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.xml.internal.ws.util.StringUtils;
import database.entities.AdministrationEntity;
import database.entities.ClientEntity;
import database.HibernateSingleton;
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
import utils.UtilityFunctions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientTableView {

    private TableView table = new TableView();
    private SessionFactory sessionFactory = HibernateSingleton.getInstance();
    private List<String> columnNames = new ArrayList<>();
    private UtilityFunctions utils = new UtilityFunctions();
    private ClientService service = new ClientService();
    private StackPane stackPane;
    private ObservableList<ClientEntity> list;
    private FilteredList<ClientEntity> filteredData;

    public ClientTableView (StackPane stackPane){
        this.stackPane = stackPane;
        ArrayList<String> properties = new ArrayList<>();
        properties.add(sessionFactory.getClassMetadata(ClientEntity.class).getIdentifierPropertyName());
        String[] propertyNames = sessionFactory.getClassMetadata(ClientEntity.class).getPropertyNames();
        properties.addAll(Arrays.asList(propertyNames));
        for(String property : properties){
            if(!property.contains("Entity")){
                String columnName = StringUtils.capitalize(utils.getHumanReadable(property));
                columnNames.add(columnName);
                TableColumn tableColumn = new TableColumn(columnName);
                tableColumn.setPrefWidth(1360 / propertyNames.length);
                tableColumn.setCellValueFactory(new PropertyValueFactory<ClientEntity, String>(property));
                table.getColumns().add(tableColumn);
            }
        }

        refresh();

    }

    public void deleteItem(){
        ClientEntity entity = (ClientEntity) table.getSelectionModel().getSelectedItem();
        if(table.getSelectionModel().getSelectedIndex() != -1){
            ConfirmationPopUp popUp = new ConfirmationPopUp(stackPane);
            popUp.getConfirm().setOnAction(confirm -> {
                service.delete(entity.getClientId());
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
        List<AdministrationEntity> administrationEntities = administrationService.findAll();
        JFXComboBox<Integer> administrationIds = new JFXComboBox<>();
        JFXComboBox<String> subscriptionModel = new JFXComboBox<>();

        for(AdministrationEntity a_entity : administrationEntities){
            administrationIds.getItems().add(a_entity.getAdministrationId());
        }

        subscriptionModel.getItems().addAll("Basic", "Extended", "Premium");
        subscriptionModel.setPromptText("Choose subscription model");
        subscriptionModel.setLabelFloat(true);

        administrationIds.setPromptText("Choose administration id");
        administrationIds.setLabelFloat(true);

        VBox inputs = new VBox(32);
        JFXTextField firstName = new JFXTextField();
        JFXTextField secondName = new JFXTextField();
        firstName.setPromptText("First name");
        firstName.setLabelFloat(true);
        secondName.setPromptText("Second name");
        secondName.setLabelFloat(true);

        inputs.getChildren().addAll(administrationIds, subscriptionModel, firstName, secondName);

        ItemOperationsPopUp popUp = new ItemOperationsPopUp(stackPane, inputs);
        popUp.getConfirm().setOnAction(confirm -> {
            String firstNameVal = StringUtils.capitalize(firstName.getText());
            String secondNameVal = StringUtils.capitalize(secondName.getText());
            Integer administrationVal = administrationIds.getSelectionModel().getSelectedItem();
            String subscriptionModelVal = subscriptionModel.getSelectionModel().getSelectedItem();
            if(firstNameVal.equals("") || secondNameVal.equals("") || administrationIds.getSelectionModel().isEmpty() || subscriptionModel.getSelectionModel().isEmpty()){
                new ErrorPopUp("Fields can't be empty", stackPane);
            } else {
                ClientEntity entity = new ClientEntity();
                entity.setAdministrationId(administrationVal);
                entity.setClientFirstname(firstNameVal);
                entity.setClientSecondname(secondNameVal);
                entity.setSubscriptionModel(subscriptionModelVal);
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
            ClientEntity entity = (ClientEntity) table.getSelectionModel().getSelectedItem();
            AdministrationService administrationService = new AdministrationService();
            List<AdministrationEntity> administrationEntities = administrationService.findAll();
            JFXComboBox<Integer> administrationIds = new JFXComboBox<>();
            JFXComboBox<String> subscriptionModel = new JFXComboBox<>();

            for(AdministrationEntity a_entity : administrationEntities){
                administrationIds.getItems().add(a_entity.getAdministrationId());
            }

            subscriptionModel.getItems().addAll("Basic", "Extended", "Premium");
            subscriptionModel.setPromptText("Choose subscription model");
            subscriptionModel.setLabelFloat(true);
            subscriptionModel.getSelectionModel().select(entity.getSubscriptionModel());

            administrationIds.setPromptText("Choose administration id");
            administrationIds.setLabelFloat(true);
            administrationIds.getSelectionModel().select(entity.getAdministrationId());

            VBox inputs = new VBox(32);
            JFXTextField firstName = new JFXTextField();
            JFXTextField secondName = new JFXTextField();
            firstName.setPromptText("First name");
            firstName.setLabelFloat(true);
            firstName.setText(entity.getClientFirstname());
            secondName.setPromptText("Second name");
            secondName.setLabelFloat(true);
            secondName.setText(entity.getClientSecondname());

            inputs.getChildren().addAll(administrationIds, subscriptionModel, firstName, secondName);

            ItemOperationsPopUp popUp = new ItemOperationsPopUp(stackPane, inputs);
            popUp.getConfirm().setOnAction(confirm -> {
                String firstNameVal = StringUtils.capitalize(firstName.getText());
                String secondNameVal = StringUtils.capitalize(secondName.getText());
                Integer administrationVal = administrationIds.getSelectionModel().getSelectedItem();
                String subscriptionModelVal = subscriptionModel.getSelectionModel().getSelectedItem();
                if(firstNameVal.equals("") || secondNameVal.equals("") || administrationIds.getSelectionModel().isEmpty() || subscriptionModel.getSelectionModel().isEmpty()){
                    new ErrorPopUp("Fields can't be empty", stackPane);
                } else {
                    entity.setAdministrationId(administrationVal);
                    entity.setClientFirstname(firstNameVal);
                    entity.setClientSecondname(secondNameVal);
                    entity.setSubscriptionModel(subscriptionModelVal);
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
