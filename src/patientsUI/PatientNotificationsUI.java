package patientsUI;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PatientNotificationsUI extends PatientBaseUI{
    private VBox notificationVBox;

    public PatientNotificationsUI(Stage primaryStage, AppointmentUIMain controller) {
        super(primaryStage, controller);
        setTitle("Your Notifications");

        notificationVBox = new VBox();
        notificationVBox.setPadding(new Insets(40, 40, 40, 40));
        notificationVBox.setSpacing(20);
        notificationVBox.setFillWidth(false);
        getMainVBox().getChildren().add(notificationVBox);

        //get notifications from database
        testData();
    }

    public void testData(){
        //temporary function
        for (int i = 0; i <= 7; i++){
            NotificationBox nb = new NotificationBox();
            nb.setTimeLabel("2hrs ago");
            nb.setNotificationLabel("Appointment is tomorrow at 2:30");
            notificationVBox.getChildren().add(nb);
        }
    }

}