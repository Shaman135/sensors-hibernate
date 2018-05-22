package GUI.tables;

import GUI.popups.ConfirmationPopUp;
import GUI.popups.ErrorPopUp;
import GUI.popups.ItemOperationsPopUp;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.xml.internal.ws.util.StringUtils;
import database.HibernateSingleton;
import database.entities.ContractEntity;
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubcontractorTableView {

    private TableView table = new TableView();
    private SessionFactory sessionFactory = HibernateSingleton.getInstance();
    private List<String> columnNames = new ArrayList<>();
    private UtilityFunctions utils = new UtilityFunctions();
    private SubcontractorService service = new SubcontractorService();
    private StackPane stackPane;
    private ObservableList<SubcontractorEntity> list;
    private FilteredList<SubcontractorEntity> filteredData;

    public SubcontractorTableView (StackPane stackPane){
        this.stackPane = stackPane;
        ArrayList<String> properties = new ArrayList<>();
        properties.add(sessionFactory.getClassMetadata(SubcontractorEntity.class).getIdentifierPropertyName());
        String[] propertyNames = sessionFactory.getClassMetadata(SubcontractorEntity.class).getPropertyNames();
        properties.addAll(Arrays.asList(propertyNames));
        for(String property : properties){
            if(!property.contains("Entity")){
                String columnName = StringUtils.capitalize(utils.getHumanReadable(property));
                columnNames.add(columnName);
                TableColumn tableColumn = new TableColumn(columnName);
                tableColumn.setPrefWidth(1360 / propertyNames.length);
                tableColumn.setCellValueFactory(new PropertyValueFactory<SubcontractorEntity, String>(property));
                table.getColumns().add(tableColumn);
            }
        }

        refresh();

    }

    public void deleteItem(){
        SubcontractorEntity entity = (SubcontractorEntity) table.getSelectionModel().getSelectedItem();
        if(table.getSelectionModel().getSelectedIndex() != -1){
            ConfirmationPopUp popUp = new ConfirmationPopUp(stackPane);
            popUp.getConfirm().setOnAction(confirm -> {
                service.delete(entity.getSubcontractorId());
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
        JFXTextField companyName = new JFXTextField();
        JFXTextField address = new JFXTextField();
        JFXTextField NIP = new JFXTextField();
        JFXTextField price = new JFXTextField();
        companyName.setPromptText("Company name");
        companyName.setLabelFloat(true);
        address.setPromptText("Address");
        address.setLabelFloat(true);
        NIP.setPromptText("NIP");
        NIP.setLabelFloat(true);
        price.setPromptText("Price per hour");
        price.setLabelFloat(true);

        inputs.getChildren().addAll(companyName, address, NIP, price);

        ItemOperationsPopUp popUp = new ItemOperationsPopUp(stackPane, inputs);
        popUp.getConfirm().setOnAction(confirm -> {
            String companyVal = StringUtils.capitalize(companyName.getText());
            String addressVal = StringUtils.capitalize(address.getText());
            if(companyVal.equals("") || addressVal.equals("")) {
                new ErrorPopUp("Fields can't be empty", stackPane);
            } else if (NIP.getText().equals("") || !NIP.getText().matches("[0-9]+") || NIP.getText().startsWith("0") || NIP.getText().length() != 10) {
                new ErrorPopUp("NIP is wrong", stackPane);
            } else if(price.getText().startsWith("0") || price.getText().equals("") || !price.getText().matches("([0-9]*)\\.([0-9]*)")) {
                new ErrorPopUp("Price is wrong", stackPane);
            } else {
                SubcontractorEntity entity = new SubcontractorEntity();
                BigInteger nipVal = new BigInteger(NIP.getText());
                BigDecimal priceVal = new BigDecimal(price.getText());
                entity.setCompanyName(companyVal);
                entity.setSubcontractorAddress(addressVal);
                entity.setPricePerHour(priceVal);
                entity.setSubcontractorNip(nipVal);
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
            SubcontractorEntity entity = (SubcontractorEntity) table.getSelectionModel().getSelectedItem();
            VBox inputs = new VBox(32);
            JFXTextField companyName = new JFXTextField();
            JFXTextField address = new JFXTextField();
            JFXTextField NIP = new JFXTextField();
            JFXTextField price = new JFXTextField();
            companyName.setPromptText("Company name");
            companyName.setLabelFloat(true);
            companyName.setText(entity.getCompanyName());
            address.setPromptText("Address");
            address.setLabelFloat(true);
            address.setText(entity.getSubcontractorAddress());
            NIP.setPromptText("NIP");
            NIP.setLabelFloat(true);
            NIP.setText(String.valueOf(entity.getSubcontractorNip()));
            price.setPromptText("Price per hour");
            price.setLabelFloat(true);
            price.setText(String.valueOf(entity.getPricePerHour()));

            inputs.getChildren().addAll(companyName, address, NIP, price);

            ItemOperationsPopUp popUp = new ItemOperationsPopUp(stackPane, inputs);
            popUp.getConfirm().setOnAction(confirm -> {
                String companyVal = StringUtils.capitalize(companyName.getText());
                String addressVal = StringUtils.capitalize(address.getText());
                if(companyVal.equals("") || addressVal.equals("")) {
                    new ErrorPopUp("Fields can't be empty", stackPane);
                } else if (NIP.getText().equals("") || !NIP.getText().matches("[0-9]+") || NIP.getText().startsWith("0") || NIP.getText().length() != 10) {
                    new ErrorPopUp("NIP is wrong", stackPane);
                } else if(price.getText().startsWith("0") || price.getText().equals("") || !price.getText().matches("([0-9]*)\\.([0-9]*)")) {
                    new ErrorPopUp("Price is wrong", stackPane);
                } else {
                    BigInteger nipVal = new BigInteger(NIP.getText());
                    BigDecimal priceVal = new BigDecimal(price.getText());
                    entity.setCompanyName(companyVal);
                    entity.setSubcontractorAddress(addressVal);
                    entity.setPricePerHour(priceVal);
                    entity.setSubcontractorNip(nipVal);
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
