package patientsUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

class AppointmentBox extends VBox {

    AppointmentBox(String date, String time, String appointment, String dentist, String room, FlowPane flowPane, Stage primaryStage) {
        super();
        setStyle("-fx-background-color: #80CEE1");
        setPadding(new Insets(40, 40, 40, 40));
        setSpacing(10);
        setMaxWidth(USE_COMPUTED_SIZE);

        Font labelFont = new Font("Arial Black", 25);

        Label dateLabel = new Label(date);
        dateLabel.setFont(labelFont);

        Label timeLabel = new Label(time);
        timeLabel.setFont(labelFont);

        Label appointmentLabel = new Label(appointment);
        appointmentLabel.setFont(labelFont);

        Label dentistLabel = new Label(dentist);
        dentistLabel.setFont(labelFont);

        Label roomLabel = new Label(room);
        roomLabel.setFont(labelFont);

        getChildren().addAll(dateLabel, timeLabel, appointmentLabel, dentistLabel, roomLabel);

        HBox hBox = new HBox();
        getChildren().add(hBox);

        Font buttonFont = new Font("Arial Black", 15);

        //check in button
        Button checkInButton = new Button("Check In");
        checkInButton.setFont(buttonFont);

        if (/*currentDate != appointmentDate*/true){
            checkInButton.setDisable(true);
        }

        checkInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //send notification
                flowPane.getChildren().remove(AppointmentBox.this);
            }
        });

        //change button
        Button changeButton = new Button("Request Change");
        changeButton.setFont(buttonFont);
        changeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeRequestDialog dialog = new ChangeRequestDialog();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(primaryStage);
                dialog.show();
            }
        });

        //cancel button
        Button cancelButton = new Button("Cancel");
        cancelButton.setFont(buttonFont);
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //update database - cancel appointment
                flowPane.getChildren().remove(AppointmentBox.this);
            }
        });

        hBox.getChildren().addAll(checkInButton, changeButton, cancelButton);
    }

}