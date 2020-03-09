package Patient;

import Database.MySQL;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.HashMap;

public class AppointmentGrid {
    private PatientMain main;
    private FlowPane flowPane;
    private ScrollPane scrollPane;
    private int patientID;

    public AppointmentGrid(PatientMain main, int patientID) {
        this.main = main;
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
                    appointment.get("appointment_time").toString(),
                    MySQL.getTreatmentName((int) appointment.get("appointment_treatment")),
                    MySQL.getDentistName((int) appointment.get("appointment_dentist")),
                    MySQL.getRoomName((int) appointment.get("appointment_room"))
            );
        }
        return scrollPane;
    }


    private void addAppointment(int appointmentID, String date, String time, String appointment, String dentist, String room) {
        flowPane.getChildren().add(
                new AppointmentBox(appointmentID, date, time, appointment, dentist, room, flowPane, main.getPrimaryStage()));
    }
}