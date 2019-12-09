package patientsUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AppointmentUIMain extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        primaryStage.setTitle("HudDental");
        PatientAppointmentUI appointmentUI = new PatientAppointmentUI(primaryStage, this);
        primaryStage.setScene(new Scene(appointmentUI, 300, 275));
        primaryStage.show();
    }

    public void switchScene(Pane ui){
        this.primaryStage.setScene(new Scene(ui));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
