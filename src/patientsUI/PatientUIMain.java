package patientsUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PatientUIMain extends Application {
    private Stage primaryStage;
    private Scene appointments, notifications;

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        primaryStage.setTitle("HudDental");

        PatientAppointmentUI appointmentUI = new PatientAppointmentUI(this);
        appointments = new Scene(appointmentUI);

        PatientNotificationsUI notificationsUI = new PatientNotificationsUI(this);
        notifications = new Scene(notificationsUI);

        appointmentsPage();
        primaryStage.show();
    }

    public void appointmentsPage(){
        primaryStage.setScene(appointments);
    }

    public void notificationsPage(){
        primaryStage.setScene(notifications);
    }

    public Stage getPrimaryStage(){
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
