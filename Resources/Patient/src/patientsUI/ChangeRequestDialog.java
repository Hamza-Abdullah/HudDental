package patientsUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ChangeRequestDialog extends Stage {

    public ChangeRequestDialog() {
        Label title = new Label("Change Request:");
        title.setFont(new Font("Arial Black", 20));
        title.setTextFill(Color.rgb(128, 206, 225));

        TextArea textArea = new TextArea();
        textArea.setWrapText(true);

        Button sendButton = new Button("Send Change Request");
        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //update database - send change request
                ChangeRequestDialog.this.close();
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeRequestDialog.this.close();
            }
        });

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(10);
        vBox.setFillWidth(true);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);

        hBox.getChildren().addAll(sendButton, cancelButton);
        vBox.getChildren().addAll(title, textArea, hBox);

        Scene dialog = new Scene(vBox, 500, 200);
        setScene(dialog);
    }
}
