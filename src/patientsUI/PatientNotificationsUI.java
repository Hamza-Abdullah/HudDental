package patientsUI;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PatientNotificationsUI extends PatientBaseUI{
    private VBox notificationVBox;

    public PatientNotificationsUI(Stage primaryStage, PatientUIMain controller) {
        super(primaryStage, controller);
        setTitle("Your Notifications");

        notificationVBox = new VBox();
        notificationVBox.setPadding(new Insets(40, 40, 40, 40));
        notificationVBox.setSpacing(20);
        notificationVBox.setFillWidth(false);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setPrefHeight(850);
        scrollPane.setContent(notificationVBox);

        getMainVBox().getChildren().add(scrollPane);

        //get notifications from database
        testData();
    }

    private void testData(){
        //temporary function
        for (int i = 0; i <= 11; i++){
            NotificationBox nb = new NotificationBox();
            nb.setTimeLabel("2hrs ago");
            nb.setNotificationLabel("Appointment is tomorrow at 2:30");
            notificationVBox.getChildren().add(nb);
        }
    }

}