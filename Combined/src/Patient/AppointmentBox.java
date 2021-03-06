package Patient;

import Database.MySQL;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;

class AppointmentBox extends VBox {
    private FlowPane flowPane;

    AppointmentBox(int appointmentID, String date, String time, String appointment, String dentist, int room, FlowPane flowPane, Stage primaryStage) {
        super();
        this.flowPane = flowPane;

        setStyle("-fx-background-color: #0FBCF9");
        setPadding(new Insets(40, 40, 40, 40));
        setSpacing(10);
        setMaxWidth(USE_COMPUTED_SIZE);
        
        Label dateLabel = new Label("Date: " + date);
        setLabelFont(dateLabel);

        Label timeLabel = new Label("Time: " + time);
        setLabelFont(timeLabel);

        Label appointmentLabel = new Label(appointment);
        setLabelFont(appointmentLabel);

        Label dentistLabel = new Label("Dentist: " + dentist);
        setLabelFont(dentistLabel);

        Label roomLabel = new Label("Room: " + room);
        setLabelFont(roomLabel);

        getChildren().addAll(dateLabel, timeLabel, appointmentLabel, dentistLabel, roomLabel);

        HBox hBox = new HBox();
        getChildren().add(hBox);

        Font buttonFont = new Font("Arial Black", 15);
        String buttonStyle = "-fx-background-color: #fff; -fx-background-radius: 5em";


        //check in button
        Button checkInButton = new Button("Check In");
        checkInButton.setFont(buttonFont);
        checkInButton.setStyle(buttonStyle);

        if (!LocalDate.now().isEqual(parseDate(date)) || MySQL.isCheckedIn(appointmentID)){
            checkInButton.setDisable(true);
        }

        checkInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MySQL.checkIn(appointmentID);
                checkInButton.setDisable(true);
            }
        });

        //change button
        Button changeButton = new Button("Request Change");
        changeButton.setFont(buttonFont);
        changeButton.setStyle(buttonStyle);
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
        cancelButton.setStyle(buttonStyle);
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                CancelDialog dialog = new CancelDialog(AppointmentBox.this, appointmentID);
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(primaryStage);
                dialog.show();
            }
        });

        hBox.getChildren().addAll(checkInButton, changeButton, cancelButton);
        hBox.setMargin(checkInButton, new Insets(0, 10, 0, 0));
        hBox.setMargin(changeButton, new Insets(0, 10, 0, 0));
    }
    
    private void setLabelFont(Label label){
        label.setStyle("-fx-font-family: Quicksand; -fx-font-size: 25");
        label.setTextFill(Paint.valueOf("#FFFFFF"));
    }

    private LocalDate parseDate(String date){
        String[] digits = date.split("-");
        LocalDate newDate = LocalDate.of(
                Integer.parseInt(digits[0]),
                Integer.parseInt(digits[1]),
                Integer.parseInt(digits[2])
        );
        return newDate;
    }

    public void removeAppointment(){
        flowPane.getChildren().remove(this);
    }
}