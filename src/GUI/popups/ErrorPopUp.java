package GUI.popups;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class ErrorPopUp {

    public ErrorPopUp(String message, StackPane stack){
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXButton ok = new JFXButton("OK");

        JFXDialog dialog = new JFXDialog(stack, dialogLayout, JFXDialog.DialogTransition.TOP);
        ok.setOnAction(k -> dialog.close());
        ok.getStyleClass().add("my_button");

        dialogLayout.setHeading(new Label("Something went wrong..."));
        Label messageLabel = new Label(message);
        messageLabel.getStyleClass().add("dialog_label");
        dialogLayout.setBody(messageLabel );
        dialogLayout.setActions(ok);
        dialog.setOnDialogOpened(opened -> ok.requestFocus());
        dialog.show();
    }

}
