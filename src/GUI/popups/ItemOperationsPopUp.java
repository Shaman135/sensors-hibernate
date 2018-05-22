package GUI.popups;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ItemOperationsPopUp {

    StackPane stackPane;
    JFXDialog dialog;
    JFXButton confirm = new JFXButton("Confirm");
    JFXButton cancel = new JFXButton("Cancel");

    public ItemOperationsPopUp(StackPane stackPane, VBox inputs) {
        this.stackPane = stackPane;
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        HBox buttons = new HBox(12);
        buttons.getChildren().addAll(confirm, cancel);
        cancel.setDisableVisualFocus(true);

        dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.TOP);
        cancel.getStyleClass().add("my_button");
        confirm.getStyleClass().add("my_button");

        dialogLayout.setHeading(new Label("Do you want to change items table?"));
        dialogLayout.setBody(inputs);
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

    public void hide(){
        dialog.setVisible(false);
    }

}
