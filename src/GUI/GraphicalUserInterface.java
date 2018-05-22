package GUI;

import GUI.tables.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXTabPane;
import database.entities.AdministrationEntity;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import services.AdministrationService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class GraphicalUserInterface extends Application {

    public void run() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Stage window = primaryStage;
        window.setResizable(false);
        window.setTitle("Hibernate Example Application");

        StackPane stackPane = new StackPane();
        JFXDecorator decorator = new JFXDecorator(window, stackPane);
        Scene scene = new Scene(decorator, 1366, 768);

        JFXTabPane tabPane = new JFXTabPane();
        tabPane.getStylesheets().add("/css/main_window.css");
        Tab administrationTab = new Tab("Administration");
        Tab employeesTab = new Tab("Employees");
        Tab clientTab = new Tab("Client");
        Tab subcontractorTab = new Tab("Subcontractors");
        Tab contractTab = new Tab("Contracts");
        Tab serverTab = new Tab("Servers");
        Tab buildingTab = new Tab("Buildings");
        Tab roomTab = new Tab("Rooms");
        Tab sensorTab = new Tab("Sensors");
        Tab dataRecordTab = new Tab("Data Records");

        AdministrationTableView administrationTableView = new AdministrationTableView(stackPane);
        AdministrationEmployeeTableView administrationEmployeeTableView = new AdministrationEmployeeTableView(stackPane);
        ClientTableView clientTableView = new ClientTableView(stackPane);
        SubcontractorTableView subcontractorTableView = new SubcontractorTableView(stackPane);
        ContractTableView contractTableView = new ContractTableView(stackPane);
        ServerTableView serverTableView = new ServerTableView(stackPane);
        BuildingTableView buildingTableView = new BuildingTableView(stackPane);
        RoomTableView roomTableView = new RoomTableView(stackPane);
        SensorTableView sensorTableView = new SensorTableView(stackPane);
        DataRecordTableView dataRecordTableView = new DataRecordTableView(stackPane);

        TableView administrationTable = administrationTableView.getTable();
        TableView administrationEmployeeTable = administrationEmployeeTableView.getTable();
        TableView clientTable = clientTableView.getTable();
        TableView subcontractorTable = subcontractorTableView.getTable();
        TableView contractTable = contractTableView.getTable();
        TableView serverTable = serverTableView.getTable();
        TableView buildingTable = buildingTableView.getTable();
        TableView roomTable = roomTableView.getTable();
        TableView sensorTable = sensorTableView.getTable();
        TableView dataRecordTable = dataRecordTableView.getTable();


        TabContent administrationTabLayout = new TabContent(administrationTable);
        TabContent administrationEmployeeTabLayout = new TabContent(administrationEmployeeTable);
        TabContent clientTabLayout = new TabContent(clientTable);
        TabContent subcontractorTabLayout = new TabContent(subcontractorTable);
        TabContent contractTabLayout = new TabContent(contractTable);
        TabContent serverTabLayout = new TabContent(serverTable);
        TabContent buildingTabLayout = new TabContent(buildingTable);
        TabContent roomTabLayout = new TabContent(roomTable);
        TabContent sensorTabLayout = new TabContent(sensorTable);
        TabContent dataRecordTabLayout = new TabContent(dataRecordTable);


        administrationTab.setContent(administrationTabLayout.getLayout());
        employeesTab.setContent(administrationEmployeeTabLayout.getLayout());
        clientTab.setContent(clientTabLayout.getLayout());
        subcontractorTab.setContent(subcontractorTabLayout.getLayout());
        contractTab.setContent(contractTabLayout.getLayout());
        serverTab.setContent(serverTabLayout.getLayout());
        buildingTab.setContent(buildingTabLayout.getLayout());
        roomTab.setContent(roomTabLayout.getLayout());
        sensorTab.setContent(sensorTabLayout.getLayout());
        dataRecordTab.setContent(dataRecordTabLayout.getLayout());

        administrationTableView.setFilter(administrationTabLayout);
        administrationEmployeeTableView.setFilter(administrationEmployeeTabLayout);
        clientTableView.setFilter(clientTabLayout);
        subcontractorTableView.setFilter(subcontractorTabLayout);
        contractTableView.setFilter(contractTabLayout);
        serverTableView.setFilter(contractTabLayout);
        buildingTableView.setFilter(buildingTabLayout);
        roomTableView.setFilter(roomTabLayout);
        sensorTableView.setFilter(sensorTabLayout);
        dataRecordTableView.setFilter(dataRecordTabLayout);


        tabPane.getTabs().addAll(administrationTab,
                employeesTab,
                clientTab,
                subcontractorTab,
                contractTab,
                serverTab,
                buildingTab,
                roomTab,
                sensorTab,
                dataRecordTab);

        JFXButton create = new JFXButton();
        JFXButton delete = new JFXButton();
        JFXButton update = new JFXButton();

        List<JFXButton> buttons = new ArrayList<>();
        create.getStyleClass().add("add_button");
        update.getStyleClass().add("edit_button");
        delete.getStyleClass().add("remove_button");

        stackPane.getChildren().addAll(tabPane, delete, update, create);
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            if (stackPane.getChildren().size() >= 3) {
                for (int i = 1; i < 4; i++) {
                    stackPane.getChildren().get(i).setTranslateX(scene.getWidth() / 2 - 50);
                    stackPane.getChildren().get(i).setTranslateY(scene.getHeight() / 2 - (i - 1) * 60 - 80);
                }
            }
        };

        scene.widthProperty().addListener(stageSizeListener);
        scene.heightProperty().addListener(stageSizeListener);

        scene.getStylesheets().add("/css/main_window.css");
        window.setScene(scene);
        window.show();

        create.setOnAction(addItem -> {
            switch (tabPane.getSelectionModel().getSelectedIndex()) {
                case 0:
                    administrationTableView.addItem();
                    break;

                case 1:
                    administrationEmployeeTableView.addItem();
                    break;

                case 2:
                    clientTableView.addItem();
                    break;

                case 3:
                    subcontractorTableView.addItem();
                    break;

                case 4:
                    contractTableView.addItem();
                    break;

                case 5:
                    serverTableView.addItem();
                    break;

                case 6:
                    buildingTableView.addItem();
                    break;

                case 7:
                    roomTableView.addItem();
                    break;

                case 8:
                    sensorTableView.addItem();
                    break;

                case 9:
                    dataRecordTableView.addItem();
                    break;

            }
        });

        update.setOnAction(updateItem -> {
            switch (tabPane.getSelectionModel().getSelectedIndex()) {
                case 0:
                    administrationTableView.updateItem();
                    break;

                case 1:
                    administrationEmployeeTableView.updateItem();
                    break;

                case 2:
                    clientTableView.updateItem();
                    break;

                case 3:
                    subcontractorTableView.updateItem();
                    break;

                case 4:
                    contractTableView.updateItem();
                    break;

                case 5:
                    serverTableView.updateItem();
                    break;

                case 6:
                    buildingTableView.updateItem();
                    break;

                case 7:
                    roomTableView.updateItem();
                    break;

                case 8:
                    sensorTableView.updateItem();
                    break;

                case 9:
                    dataRecordTableView.updateItem();
                    break;
            }
        });

        delete.setOnAction(deleteItem -> {
            System.out.println("Deleting from: " + tabPane.getSelectionModel().getSelectedIndex());
            switch (tabPane.getSelectionModel().getSelectedIndex()) {
                case 0:
                    administrationTableView.deleteItem();
                    break;

                case 1:
                    administrationEmployeeTableView.deleteItem();
                    break;

                case 2:
                    clientTableView.deleteItem();
                    break;

                case 3:
                    subcontractorTableView.deleteItem();
                    break;

                case 4:
                    contractTableView.deleteItem();
                    break;

                case 5:
                    serverTableView.deleteItem();
                    break;

                case 6:
                    buildingTableView.deleteItem();
                    break;

                case 7:
                    roomTableView.deleteItem();
                    break;

                case 8:
                    serverTableView.deleteItem();
                    break;

                case 9:
                    dataRecordTableView.deleteItem();
                    break;

            }
        });


        tabPane.getSelectionModel().selectedItemProperty().addListener((ov, t, t1) -> {
            switch (tabPane.getSelectionModel().getSelectedIndex()) {
                case 0:
                    administrationTableView.refresh();
                    break;
                case 1:
                    administrationEmployeeTableView.refresh();
                    break;
                case 2:
                    clientTableView.refresh();
                    break;
                case 3:
                    subcontractorTableView.refresh();
                    break;
                case 4:
                    contractTableView.refresh();
                    break;
                case 5:
                    serverTableView.refresh();
                    break;
                case 6:
                    buildingTableView.refresh();
                    break;
                case 7:
                    roomTableView.refresh();
                    break;
                case 8:
                    sensorTableView.refresh();
                    break;
                case 9:
                    dataRecordTableView.refresh();
                    break;
            }
        });
    }
}
