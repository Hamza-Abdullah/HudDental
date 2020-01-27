package patientsUI;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

public class PatientAppointmentUI extends PatientBaseUI {
    private FlowPane flowPane;

    public PatientAppointmentUI(PatientUIMain controller) {
        super(controller);
        setTitle("Your Appointments");
        flowPane = new FlowPane();
        flowPane.setPadding(new Insets(40, 40, 40, 40));
        flowPane.setHgap(40);
        flowPane.setVgap(40);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(flowPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setMaxHeight(850);
        scrollPane.setPrefHeight(850);

        getMainVBox().getChildren().add(scrollPane);

        //get appointments from database
        testData();
    }

    private void addAppointment(String date, String time, String appointment, String dentist, String room) {
        flowPane.getChildren().add(
                new AppointmentBox(date, time, appointment, dentist, room, flowPane, getController().getPrimaryStage()));
    }

    private void testData(){
        addAppointment("18th December 2019", "09:30", "Dental X-Ray", "Dr Winters", "Examination Room 3");
        addAppointment("18th December 2019", "10:30", "Dental X-Ray", "Dr Winters", "Examination Room 3");
        addAppointment("18th December 2019", "11:30", "Dental X-Ray", "Dr Winters", "Examination Room 3");
        addAppointment("18th December 2019", "12:30", "Dental X-Ray", "Dr Winters", "Examination Room 3");
        addAppointment("18th December 2019", "13:30", "Dental X-Ray", "Dr Winters", "Examination Room 3");
        addAppointment("18th December 2019", "14:30", "Dental X-Ray", "Dr Winters", "Examination Room 3");
        addAppointment("18th December 2019", "15:30", "Dental X-Ray", "Dr Winters", "Examination Room 3");
        addAppointment("18th December 2019", "16:30", "Dental X-Ray", "Dr Winters", "Examination Room 3");
        addAppointment("18th December 2019", "17:30", "Dental X-Ray", "Dr Winters", "Examination Room 3");
        addAppointment("18th December 2019", "18:30", "Dental X-Ray", "Dr Winters", "Examination Room 3");
    }
}