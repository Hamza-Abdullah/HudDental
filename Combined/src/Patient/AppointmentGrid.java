package Patient;

import Database.MySQL;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.HashMap;

public class AppointmentGrid {
    private PatientMain controller;
    private FlowPane flowPane;
    private ScrollPane scrollPane;
    private int patientID;

    public AppointmentGrid(PatientMain controller, int patientID) {
        this.controller = controller;
        this.patientID = patientID;

        flowPane = new FlowPane();
        flowPane.setPadding(new Insets(25, 25, 25, 25));
        flowPane.setHgap(10);
        flowPane.setVgap(10);

        scrollPane = new ScrollPane();
        scrollPane.setContent(flowPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setMaxHeight(850);
        scrollPane.setPrefHeight(850);
    }

    public ScrollPane getAppointmentsUI(){
        flowPane.getChildren().clear();
        ArrayList<HashMap<String, Object>> appointments = MySQL.getAppointments(patientID);
        for (HashMap appointment:appointments){
            addAppointment(
                    (int) appointment.get("appointment_id"),
                    (String) appointment.get("appointment_date"),
                    (String) appointment.get("appointment_time"),
                    MySQL.getTreatmentName(Integer.parseInt((String) appointment.get("appointment_treatment"))),
                    MySQL.getDentistName(Integer.parseInt((String) appointment.get("appointment_dentist"))),
                    MySQL.getRoomName(Integer.parseInt((String) appointment.get("appointment_room")))
            );
        }
        return scrollPane;
    }


    private void addAppointment(int appointmentID, String date, String time, String appointment, String dentist, String room) {
        flowPane.getChildren().add(
                new AppointmentBox(appointmentID, date, time, appointment, dentist, room, flowPane, controller.getPrimaryStage()));
    }
}