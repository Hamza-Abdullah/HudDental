package Patient;

import Database.MySQL;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CancelDialog extends Stage {
    public CancelDialog(AppointmentBox appointmentBox, int appointmentID){
        Label title = new Label("Are you sure you want to cancel this appointment");
        title.setFont(new Font("Quicksand", 15));

        Button noButton = new Button("No");
        noButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                CancelDialog.this.close();
            }
        });

        Button yesButton = new Button("Yes");
        yesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MySQL.cancelAppointment(appointmentID);
                appointmentBox.removeAppointment();
                CancelDialog.this.close();
            }
        });

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(noButton, yesButton);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(10);
        vBox.getChildren().addAll(title, hBox);

        Scene dialog = new Scene(vBox);
        setScene(dialog);
    }
}
