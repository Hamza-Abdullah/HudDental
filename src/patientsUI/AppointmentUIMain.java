package patientsUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppointmentUIMain extends Application {

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("HudDental");
        PatientAppointmentUI appointmentUI = new PatientAppointmentUI(primaryStage);

        appointmentUI.addAppointment("18th December 2019", "14:30", "Dental X-Ray", "Dr Winters", "Examination Room 3");
        appointmentUI.addAppointment("18th December 2019", "14:30", "Dental X-Ray", "Dr Winters", "Examination Room 3");
        appointmentUI.addAppointment("18th December 2019", "14:30", "Dental X-Ray", "Dr Winters", "Examination Room 3");
        appointmentUI.addAppointment("18th December 2019", "14:30", "Dental X-Ray", "Dr Winters", "Examination Room 3");
        appointmentUI.addAppointment("18th December 2019", "14:30", "Dental X-Ray", "Dr Winters", "Examination Room 3");

        primaryStage.setScene(new Scene(appointmentUI, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
