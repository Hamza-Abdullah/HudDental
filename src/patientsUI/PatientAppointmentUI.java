package patientsUI;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PatientAppointmentUI extends PatientBaseUI {
    private FlowPane flowPane;

    public PatientAppointmentUI(Stage primaryStage, AppointmentUIMain controller) {
        super(primaryStage, controller);
        setTitle("Your Appointments");
        this.flowPane = new FlowPane();
        flowPane.setPadding(new Insets(40, 40, 40, 40));
        flowPane.setHgap(40);
        flowPane.setVgap(40);
        this.getMainVBox().getChildren().add(flowPane);

        //get appointments from database
        testData();
    }

    public void addAppointment(String date, String time, String appointment, String dentist, String room) {
        this.flowPane.getChildren().add(
                new AppointmentBox(date, time, appointment, dentist, room, this.flowPane, this.getPrimaryStage()));
    }

    public void testData(){
        addAppointment("18th December 2019", "14:30", "Dental X-Ray", "Dr Winters", "Examination Room 3");
        addAppointment("18th December 2019", "14:30", "Dental X-Ray", "Dr Winters", "Examination Room 3");
        addAppointment("18th December 2019", "14:30", "Dental X-Ray", "Dr Winters", "Examination Room 3");
        addAppointment("18th December 2019", "14:30", "Dental X-Ray", "Dr Winters", "Examination Room 3");
        addAppointment("18th December 2019", "14:30", "Dental X-Ray", "Dr Winters", "Examination Room 3");
    }
}
