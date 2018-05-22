package GUI.tables;

import GUI.popups.ItemOperationsPopUp;
import GUI.popups.ConfirmationPopUp;
import GUI.popups.ErrorPopUp;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.sun.xml.internal.ws.util.StringUtils;
import database.entities.AdministrationEntity;
import database.entities.ClientEntity;
import database.entities.ContractEntity;
import database.HibernateSingleton;
import database.entities.SubcontractorEntity;
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
import services.ContractService;
import services.SubcontractorService;
import utils.UtilityFunctions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContractTableView {

    private TableView table = new TableView();
    private SessionFactory sessionFactory = HibernateSingleton.getInstance();
    private List<String> columnNames = new ArrayList<>();
    private UtilityFunctions utils = new UtilityFunctions();
    private ContractService service = new ContractService();
    private StackPane stackPane;
    private ObservableList<ContractEntity> list;
    private FilteredList<ContractEntity> filteredData;

    public ContractTableView (StackPane stackPane){
        this.stackPane = stackPane;
        ArrayList<String> properties = new ArrayList<>();
        properties.add(sessionFactory.getClassMetadata(ContractEntity.class).getIdentifierPropertyName());
        String[] propertyNames = sessionFactory.getClassMetadata(ContractEntity.class).getPropertyNames();
        properties.addAll(Arrays.asList(propertyNames));
        for(String property : properties){
            if(!property.contains("Entity")){
                String columnName = StringUtils.capitalize(utils.getHumanReadable(property));
                columnNames.add(columnName);
                TableColumn tableColumn = new TableColumn(columnName);
                tableColumn.setPrefWidth(1360 / propertyNames.length);
                tableColumn.setCellValueFactory(new PropertyValueFactory<ContractEntity, String>(property));
                table.getColumns().add(tableColumn);
            }
        }

        refresh();

    }

    public void deleteItem(){
        ContractEntity entity = (ContractEntity) table.getSelectionModel().getSelectedItem();
        if(table.getSelectionModel().getSelectedIndex() != -1){
            ConfirmationPopUp popUp = new ConfirmationPopUp(stackPane);
            popUp.getConfirm().setOnAction(confirm -> {
                service.delete(entity.getContractId());
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
        SubcontractorService subcontractorService = new SubcontractorService();

        List<AdministrationEntity> administrationEntities = administrationService.findAll();
        List<SubcontractorEntity> subcontractorEntities = subcontractorService.findAll();

        JFXComboBox<Integer> administrationIds = new JFXComboBox<>();
        JFXComboBox<Integer> subcontractorIds = new JFXComboBox<>();

        for(AdministrationEntity a_entity : administrationEntities){
            administrationIds.getItems().add(a_entity.getAdministrationId());
        }

        for(SubcontractorEntity s_entity : subcontractorEntities){
            subcontractorIds.getItems().add(s_entity.getSubcontractorId());
        }

        administrationIds.setPromptText("Choose administration id");
        administrationIds.setLabelFloat(true);
        subcontractorIds.setPromptText("Choose subcontractor id");
        subcontractorIds.setLabelFloat(true);

        VBox inputs = new VBox(32);
        JFXTextField numberOfHours = new JFXTextField();
        JFXDatePicker startDate = new JFXDatePicker();
        JFXDatePicker endDate = new JFXDatePicker();
        numberOfHours.setPromptText("Number of hours");
        numberOfHours.setLabelFloat(true);
        startDate.setPromptText("Start date");
        endDate.setPromptText("End date");

        inputs.getChildren().addAll(administrationIds, subcontractorIds, startDate, endDate, numberOfHours);

        ItemOperationsPopUp popUp = new ItemOperationsPopUp(stackPane, inputs);
        popUp.getConfirm().setOnAction(confirm -> {
            LocalDate startVal = startDate.getValue();
            LocalDate endVal = endDate.getValue();
            if (startVal.isAfter(endVal)) {
                new ErrorPopUp("End date can't be before start date", stackPane);
            } else if (!numberOfHours.getText().matches("[0-9]+") || numberOfHours.getText().startsWith("0") || numberOfHours.getText().equals("")) {
                new ErrorPopUp("Wrong number of hours", stackPane);
            } else if (administrationIds.getSelectionModel().isEmpty() || subcontractorIds.getSelectionModel().isEmpty()){
                new ErrorPopUp("Wrong ids", stackPane);
            } else {
                Integer administrationVal = administrationIds.getSelectionModel().getSelectedItem();
                Integer subcontractorVal = subcontractorIds.getSelectionModel().getSelectedItem();
                Integer num = Integer.valueOf(numberOfHours.getText());
                ContractEntity entity = new ContractEntity();
                entity.setAdministrationId(administrationVal);
                entity.setSubcontractorId(subcontractorVal);
                entity.setStartDate(java.sql.Date.valueOf(startVal));
                entity.setEndDate(java.sql.Date.valueOf(endVal));
                entity.setNumberOfHours(num);
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
            ContractEntity entity = (ContractEntity) table.getSelectionModel().getSelectedItem();
            AdministrationService administrationService = new AdministrationService();
            SubcontractorService subcontractorService = new SubcontractorService();

            List<AdministrationEntity> administrationEntities = administrationService.findAll();
            List<SubcontractorEntity> subcontractorEntities = subcontractorService.findAll();

            JFXComboBox<Integer> administrationIds = new JFXComboBox<>();
            JFXComboBox<Integer> subcontractorIds = new JFXComboBox<>();

            for (AdministrationEntity a_entity : administrationEntities) {
                administrationIds.getItems().add(a_entity.getAdministrationId());
            }

            for (SubcontractorEntity s_entity : subcontractorEntities) {
                subcontractorIds.getItems().add(s_entity.getSubcontractorId());
            }

            administrationIds.setPromptText("Choose administration id");
            administrationIds.setLabelFloat(true);
            administrationIds.getSelectionModel().select(entity.getAdministrationId());
            subcontractorIds.setPromptText("Choose subcontractor id");
            subcontractorIds.setLabelFloat(true);
            subcontractorIds.getSelectionModel().select(entity.getSubcontractorId());

            VBox inputs = new VBox(32);
            JFXTextField numberOfHours = new JFXTextField();
            JFXDatePicker startDate = new JFXDatePicker();
            JFXDatePicker endDate = new JFXDatePicker();
            numberOfHours.setPromptText("Number of hours");
            numberOfHours.setLabelFloat(true);
            numberOfHours.setText(String.valueOf(entity.getNumberOfHours()));
            startDate.setPromptText("Start date");
            startDate.setValue(entity.getStartDate().toLocalDate());
            endDate.setPromptText("End date");
            endDate.setValue(entity.getEndDate().toLocalDate());

            inputs.getChildren().addAll(administrationIds, subcontractorIds, startDate, endDate, numberOfHours);

            ItemOperationsPopUp popUp = new ItemOperationsPopUp(stackPane, inputs);
            popUp.getConfirm().setOnAction(confirm -> {
                LocalDate startVal = startDate.getValue();
                LocalDate endVal = endDate.getValue();
                if (startVal.isAfter(endVal)) {
                    new ErrorPopUp("End date can't be before start date", stackPane);
                } else if (!numberOfHours.getText().matches("[0-9]+") || numberOfHours.getText().startsWith("0") || numberOfHours.getText().equals("")) {
                    new ErrorPopUp("Wrong number of hours", stackPane);
                } else if (administrationIds.getSelectionModel().isEmpty() || subcontractorIds.getSelectionModel().isEmpty()) {
                    new ErrorPopUp("Wrong ids", stackPane);
                } else {
                    Integer administrationVal = administrationIds.getSelectionModel().getSelectedItem();
                    Integer subcontractorVal = subcontractorIds.getSelectionModel().getSelectedItem();
                    Integer num = Integer.valueOf(numberOfHours.getText());
                    entity.setAdministrationId(administrationVal);
                    entity.setSubcontractorId(subcontractorVal);
                    entity.setStartDate(java.sql.Date.valueOf(startVal));
                    entity.setEndDate(java.sql.Date.valueOf(endVal));
                    entity.setNumberOfHours(num);
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
