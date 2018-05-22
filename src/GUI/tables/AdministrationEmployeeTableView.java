package GUI.tables;

import GUI.popups.ItemOperationsPopUp;
import GUI.popups.ConfirmationPopUp;
import GUI.popups.ErrorPopUp;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.xml.internal.ws.util.StringUtils;
import database.entities.AdministrationEmployeeEntity;
import database.HibernateSingleton;
import database.entities.AdministrationEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.hibernate.SessionFactory;
import services.AdministrationEmployeeService;
import services.AdministrationService;
import utils.UtilityFunctions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdministrationEmployeeTableView {

    private TableView table = new TableView();
    private SessionFactory sessionFactory = HibernateSingleton.getInstance();
    private List<String> columnNames = new ArrayList<>();
    private UtilityFunctions utils = new UtilityFunctions();
    private AdministrationEmployeeService service = new AdministrationEmployeeService();
    private StackPane stackPane;
    private ObservableList<AdministrationEmployeeEntity> list;
    private FilteredList<AdministrationEmployeeEntity> filteredData;

    public AdministrationEmployeeTableView (StackPane stackPane){
        this.stackPane = stackPane;
        ArrayList<String> properties = new ArrayList<>();
        properties.add(sessionFactory.getClassMetadata(AdministrationEmployeeEntity.class).getIdentifierPropertyName());
        String[] propertyNames = sessionFactory.getClassMetadata(AdministrationEmployeeEntity.class).getPropertyNames();
        properties.addAll(Arrays.asList(propertyNames));
        for(String property : properties){
            if(!property.contains("Entity")) {
                String columnName = StringUtils.capitalize(utils.getHumanReadable(property));
                columnNames.add(columnName);
                TableColumn tableColumn = new TableColumn(columnName);
                tableColumn.setPrefWidth(1360 / propertyNames.length);
                tableColumn.setCellValueFactory(new PropertyValueFactory<AdministrationEmployeeEntity, String>(property));
                table.getColumns().add(tableColumn);
            }
        }

        refresh();
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

    public void deleteItem(){
        AdministrationEmployeeEntity entity = (AdministrationEmployeeEntity) table.getSelectionModel().getSelectedItem();
        if(table.getSelectionModel().getSelectedIndex() != -1){
            ConfirmationPopUp popUp = new ConfirmationPopUp(stackPane);
            popUp.getConfirm().setOnAction(confirm -> {
                service.delete(entity.getEmployeeId());
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

        for(AdministrationEntity a_entity : administrationEntities){
            administrationIds.getItems().add(a_entity.getAdministrationId());
        }

        administrationIds.setPromptText("Choose administration id");
        administrationIds.setLabelFloat(true);

        VBox inputs = new VBox(32);
        JFXTextField firstName = new JFXTextField();
        JFXTextField secondName = new JFXTextField();
        JFXTextField position = new JFXTextField();
        firstName.setPromptText("First name");
        firstName.setLabelFloat(true);
        secondName.setPromptText("Second name");
        secondName.setLabelFloat(true);
        position.setPromptText("Position");
        position.setLabelFloat(true);

        inputs.getChildren().addAll(administrationIds, firstName, secondName, position);

        ItemOperationsPopUp popUp = new ItemOperationsPopUp(stackPane, inputs);
        popUp.getConfirm().setOnAction(confirm -> {
            String firstNameVal = StringUtils.capitalize(firstName.getText());
            String secondNameVal = StringUtils.capitalize(secondName.getText());
            String positionVal = StringUtils.capitalize(position.getText());
            Integer administrationVal = administrationIds.getSelectionModel().getSelectedItem();
            if(firstNameVal.equals("") || secondNameVal.equals("") || positionVal.equals("") || administrationIds.getSelectionModel().isEmpty()){
                new ErrorPopUp("Fields can't be empty", stackPane);
            } else {
                AdministrationEmployeeEntity entity = new AdministrationEmployeeEntity();
                entity.setAdministrationId(administrationVal);
                entity.setFirstname(firstNameVal);
                entity.setSecondname(secondNameVal);
                entity.setPosition(positionVal);
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
            AdministrationEmployeeEntity entity = (AdministrationEmployeeEntity) table.getSelectionModel().getSelectedItem();
            AdministrationService administrationService = new AdministrationService();
            List<AdministrationEntity> administrationEntities = administrationService.findAll();
            JFXComboBox<Integer> administrationIds = new JFXComboBox<>();

            for (AdministrationEntity a_entity : administrationEntities) {
                administrationIds.getItems().add(a_entity.getAdministrationId());
            }

            administrationIds.setPromptText("Choose administration id");
            administrationIds.setLabelFloat(true);
            administrationIds.getSelectionModel().select(entity.getAdministrationId());

            VBox inputs = new VBox(32);
            JFXTextField firstName = new JFXTextField();
            JFXTextField secondName = new JFXTextField();
            JFXTextField position = new JFXTextField();
            firstName.setPromptText("First name");
            firstName.setLabelFloat(true);
            firstName.setText(entity.getFirstname());
            secondName.setPromptText("Second name");
            secondName.setLabelFloat(true);
            secondName.setText(entity.getSecondname());
            position.setPromptText("Position");
            position.setLabelFloat(true);
            position.setText(entity.getPosition());

            inputs.getChildren().addAll(administrationIds, firstName, secondName, position);

            ItemOperationsPopUp popUp = new ItemOperationsPopUp(stackPane, inputs);
            popUp.getConfirm().setOnAction(confirm -> {
                String firstNameVal = StringUtils.capitalize(firstName.getText());
                String secondNameVal = StringUtils.capitalize(secondName.getText());
                String positionVal = StringUtils.capitalize(position.getText());
                Integer administrationVal = administrationIds.getSelectionModel().getSelectedItem();
                if (firstNameVal.equals("") || secondNameVal.equals("") || positionVal.equals("") || administrationIds.getSelectionModel().isEmpty()) {
                    new ErrorPopUp("Fields can't be empty", stackPane);
                } else {
                    entity.setAdministrationId(administrationVal);
                    entity.setFirstname(firstNameVal);
                    entity.setSecondname(secondNameVal);
                    entity.setPosition(positionVal);
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

}
