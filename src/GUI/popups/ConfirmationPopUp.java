package GUI.popups;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class ConfirmationPopUp {

    StackPane stackPane;
    JFXDialog dialog;
    JFXButton confirm = new JFXButton("Confirm");
    JFXButton cancel = new JFXButton("Cancel");

    public ConfirmationPopUp(StackPane stackPane) {
        this.stackPane = stackPane;
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        HBox buttons = new HBox(12);
        buttons.getChildren().addAll(confirm, cancel);
        cancel.setDisableVisualFocus(true);

        dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.TOP);
        cancel.getStyleClass().add("my_button");
        confirm.getStyleClass().add("my_button");

        Label bodyLabel = new Label("It will remove all referencing items");
        bodyLabel.getStyleClass().add("dialog_label");
        dialogLayout.setHeading(new Label("Do you really want to delete item?"));
        dialogLayout.setBody(bodyLabel);
        dialogLayout.setActions(buttons);
        dialog.setOnDialogOpened(opened -> cancel.requestFocus());
    }

    public JFXButton getConfirm() {
        return confirm;
    }

    public JFXButton getCancel() {
        return cancel;
    }

    public void show(){
        dialog.show();
    }

    public void close(){
        dialog.close();
    }
}
