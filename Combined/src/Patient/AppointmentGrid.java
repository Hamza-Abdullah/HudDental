package Patient;

import Database.MySQL;
import Login.LoginMain;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimeZone;

public class AppointmentGrid {

    private FlowPane flowPane;
    private ScrollPane scrollPane;
    private int patientID;

    public AppointmentGrid(int patientID) {
        this.patientID = patientID;

        flowPane = new FlowPane();
        flowPane.setPadding(new Insets(25, 25, 25, 25));
        flowPane.setHgap(10);
        flowPane.setVgap(10);

        scrollPane = new ScrollPane();
        scrollPane.setContent(flowPane);
        scrollPane.setFitToWidth(true);
    }

    public ScrollPane getAppointmentsUI(){
        flowPane.getChildren().clear();
        ArrayList<HashMap<String, Object>> appointments = MySQL.getAppointments(patientID);
        for (HashMap appointment:appointments){
            addAppointment(
                    (int) appointment.get("appointment_id"),
                    appointment.get("appointment_date").toString(),
                    LocalTime.parse(appointment.get("appointment_time").toString()).minusHours(1).toString(),
                    MySQL.getTreatmentName((int) appointment.get("appointment_treatment")),
                    MySQL.getDentistName((int) appointment.get("appointment_dentist")),
                    (int) appointment.get("appointment_room")
            );
        }
        return scrollPane;
    }

    private void addAppointment(int appointmentID, String date, String time, String appointment, String dentist, int room) {
        flowPane.getChildren().add(
                new AppointmentBox(appointmentID, date, time, appointment, dentist, room, flowPane, LoginMain.getPrimaryStage()));
    }
}