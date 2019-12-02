package patientsUI;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

class AppointmentBox extends VBox {
    private Button checkInButton;
    private Button changeButton;
    private Button cancelButton;

    AppointmentBox(String date, String time, String appointment, String dentist, String room){
        super();
        this.setStyle("-fx-background-color: #80CEE1");
        this.setPadding(new Insets(40, 40, 40, 40));
        this.setSpacing(10);
        this.setMaxWidth(USE_COMPUTED_SIZE);

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

        this.getChildren().addAll(dateLabel, timeLabel, appointmentLabel, dentistLabel, roomLabel);

        HBox hBox = new HBox();
        this.getChildren().add(hBox);

        Font buttonFont = new Font("Arial Black", 15);

        checkInButton = new Button("Check In");
        checkInButton.setFont(buttonFont);
        changeButton = new Button("Request Change");
        changeButton.setFont(buttonFont);
        cancelButton = new Button("Cancel");
        cancelButton.setFont(buttonFont);
        hBox.getChildren().addAll(checkInButton, changeButton, cancelButton);
    }

}
