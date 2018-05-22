package GUI.tables;

import GUI.popups.ItemOperationsPopUp;
import GUI.popups.ConfirmationPopUp;
import GUI.popups.ErrorPopUp;
import com.jfoenix.controls.JFXTextField;
import com.sun.xml.internal.ws.util.StringUtils;
import database.entities.AdministrationEntity;
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
import utils.UtilityFunctions;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdministrationTableView {

    private TableView table = new TableView();
    private SessionFactory sessionFactory = HibernateSingleton.getInstance();
    private List<String> columnNames = new ArrayList<>();
    private UtilityFunctions utils = new UtilityFunctions();
    private AdministrationService service = new AdministrationService();
    private StackPane stackPane;
    private ObservableList<AdministrationEntity> list;
    private FilteredList<AdministrationEntity> filteredData;

    public AdministrationTableView (StackPane stackPane){
        this.stackPane = stackPane;
        ArrayList<String> properties = new ArrayList<>();
        properties.add(sessionFactory.getClassMetadata(AdministrationEntity.class).getIdentifierPropertyName());
        String[] propertyNames = sessionFactory.getClassMetadata(AdministrationEntity.class).getPropertyNames();
        properties.addAll(Arrays.asList(propertyNames));
        for(String property : properties){
            if(!property.contains("Entity")){
                String columnName = StringUtils.capitalize(utils.getHumanReadable(property));
                columnNames.add(columnName);
                TableColumn tableColumn = new TableColumn(columnName);
                tableColumn.setPrefWidth(1360 / properties.size());
                tableColumn.setCellValueFactory(new PropertyValueFactory<AdministrationEntity, String>(property));
                table.getColumns().add(tableColumn);
            }
        }

        refresh();
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

    public void refresh(){
        list = FXCollections.observableArrayList(service.findAll());
        table.setItems(list);
        table.refresh();
    }

    public void deleteItem(){
        AdministrationEntity entity = (AdministrationEntity) table.getSelectionModel().getSelectedItem();
        if(table.getSelectionModel().getSelectedIndex() != -1){
            ConfirmationPopUp popUp = new ConfirmationPopUp(stackPane);
            popUp.getConfirm().setOnAction(confirm -> {
                service.delete(entity.getAdministrationId());
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
        VBox inputs = new VBox(32);
        JFXTextField address = new JFXTextField();
        JFXTextField NIP = new JFXTextField();
        address.setPromptText("Administration address");
        address.setLabelFloat(true);
        NIP.setPromptText("NIP");
        NIP.setLabelFloat(true);

        inputs.getChildren().addAll(address, NIP);

        ItemOperationsPopUp popUp = new ItemOperationsPopUp(stackPane, inputs);
        popUp.getConfirm().setOnAction(confirm -> {
            String administrationAddress = address.getText();
            if(administrationAddress.equals("")){
                new ErrorPopUp("Address can't be empty", stackPane);
            } else if(NIP.getText().equals("") || !NIP.getText().matches("[0-9]+") || NIP.getText().startsWith("0") || NIP.getText().length() != 10){
                new ErrorPopUp("NIP is wrong", stackPane);
            } else {
                AdministrationEntity entity = new AdministrationEntity();
                BigInteger administrationNip = new BigInteger(NIP.getText());
                entity.setAdministrationAddress(administrationAddress);
                entity.setNip(administrationNip);
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

        if(table.getSelectionModel().getSelectedIndex() != -1) {

            AdministrationEntity entity = (AdministrationEntity) table.getSelectionModel().getSelectedItem();

            VBox inputs = new VBox(32);
            JFXTextField address = new JFXTextField();
            JFXTextField NIP = new JFXTextField();
            address.setPromptText("Administration address");
            address.setText(entity.getAdministrationAddress());
            address.setLabelFloat(true);
            NIP.setPromptText("NIP");
            NIP.setText(String.valueOf(entity.getNip()));
            NIP.setLabelFloat(true);

            inputs.getChildren().addAll(address, NIP);


            ItemOperationsPopUp popUp = new ItemOperationsPopUp(stackPane, inputs);
            popUp.getConfirm().setOnAction(confirm -> {
                String administrationAddress = address.getText();
                if (administrationAddress.equals("")) {
                    new ErrorPopUp("Address can't be empty", stackPane);
                } else if (NIP.getText().equals("") || !NIP.getText().matches("[0-9]+") || NIP.getText().startsWith("0") || NIP.getText().length() != 10) {
                    new ErrorPopUp("NIP is wrong", stackPane);
                } else {
                    BigInteger administrationNip = new BigInteger(NIP.getText());
                    entity.setAdministrationAddress(administrationAddress);
                    entity.setNip(administrationNip);
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
