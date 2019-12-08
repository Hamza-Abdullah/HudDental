package patientsUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChangeRequestDialog extends Stage {
    private TextArea textArea;
    private Button sendButton;
    private Button cancelButton;

    public ChangeRequestDialog() {
        Label label = new Label("Change Request:");

        this.textArea = new TextArea();
        this.textArea.setWrapText(true);

        this.sendButton = new Button("Send Change Request");
        this.sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //update database - send change request
                ChangeRequestDialog.this.close();
            }
        });

        this.cancelButton = new Button("Cancel");
        this.cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeRequestDialog.this.close();
            }
        });

        VBox vBox = new VBox();
        HBox hBox = new HBox();

        hBox.getChildren().addAll(this.sendButton, this.cancelButton);
        vBox.getChildren().addAll(label, this.textArea, hBox);

        Scene dialog = new Scene(vBox, 200, 200);
        this.setScene(dialog);
    }
}
