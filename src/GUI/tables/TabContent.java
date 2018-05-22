package GUI.tables;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.sun.xml.internal.ws.util.StringUtils;
import database.HibernateSingleton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.hibernate.SessionFactory;
import utils.UtilityFunctions;

import java.util.ArrayList;
import java.util.List;

public class TabContent {

    private JFXTextField filterField;
    private VBox vbox = new VBox();
    private HBox searchComponent = new HBox();
    private TableView tableView;

    public TabContent(TableView tableView){
        this.tableView = tableView;
    }

    public VBox getLayout(){

        Image image = new Image("./css/ic_search_white_24dp_1x.png");
        this.initFilterField();
        Label searchLabel = new Label("Find record: ");
        searchLabel.setGraphic(new ImageView(image));
        searchLabel.setPadding(new Insets(0, 0, 0, 12));
        vbox.getStylesheets().add("/css/main_window.css");
        searchLabel.getStyleClass().add("db_search_label");
        searchComponent.setMinHeight(55);
        filterField.setPromptText("Search...");
        filterField.getStyleClass().add("my-text-field");
        searchComponent.getChildren().setAll(searchLabel, filterField);
        searchComponent.setAlignment(Pos.CENTER_LEFT);

        searchComponent.getStyleClass().add("my_hbox");
        searchLabel.getStyleClass().add("dialog_label");

        VBox.setVgrow(tableView, Priority.ALWAYS);
        vbox.getChildren().addAll(searchComponent, tableView);
        return vbox;
    }

    public JFXTextField initFilterField(){
        filterField = new JFXTextField();
        return filterField;
    }

    public JFXTextField getFilterField() {
        return filterField;
    }

    public HBox getSearchComponent() {
        return searchComponent;
    }

    public TableView getTableView() {
        return tableView;
    }

    public void setTableView(TableView tableView) {
        this.tableView = tableView;
    }
}
