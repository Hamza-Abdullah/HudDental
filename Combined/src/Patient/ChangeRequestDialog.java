package Patient;

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
        Label line1 = new Label("To rebook an appointment call us on 01484 535723");
        Label line2 = new Label("or come down and speak to us at the clinic");
        line1.setFont(new Font("Quicksand", 15));
        line2.setFont(new Font("Quicksand", 15));

        Button closeButton = new Button("Close");
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeRequestDialog.this.close();
            }
        });

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setAlignment(Pos.CENTER);
        VBox.setMargin(closeButton, new Insets(10, 0, 0, 0));
        vBox.getChildren().addAll(line1, line2, closeButton);

        Scene dialog = new Scene(vBox);
        setScene(dialog);
    }
}
