package patientsUI;

import javafx.geometry.Insets;
import javafx.scene.layout.*;

public class PatientAppointmentUI extends PatientBaseUI {
    private FlowPane flowPane;

    public PatientAppointmentUI() {
        super();
        this.flowPane = new FlowPane();
        flowPane.setPadding(new Insets(40, 40, 40, 40));
        flowPane.setHgap(40);
        flowPane.setVgap(40);
        this.getMainVBox().getChildren().add(flowPane);
    }

    public void addAppointment(String date, String time, String appointment, String dentist, String room) {
        this.flowPane.getChildren().add(new AppointmentBox(date, time, appointment, dentist, room));
    }
}
