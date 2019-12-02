package patientsUI;

        import javafx.application.Application;
        import javafx.geometry.Insets;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.layout.*;
        import javafx.scene.paint.Color;
        import javafx.scene.text.Font;
        import javafx.stage.Stage;

public class PatientAppointmentUI extends Application {

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("HudDental");

        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #80CEE1, #ffffff; -fx-background-insets: 0, 0 0 0 260;");

        HBox mainHBox = new HBox();
        pane.getChildren().add(mainHBox);

        VBox sideBar = new VBox();
        sideBar.setPadding(new Insets(40,60,0,60));
        sideBar.setSpacing(40);

        Button logOutButton = new Button("Log Out");
        logOutButton.setMinWidth(140);
        logOutButton.setMinHeight(140);

        Button appointmentsButton = new Button("Appointments");
        appointmentsButton.setMinWidth(140);
        appointmentsButton.setMinHeight(140);

        Button notificationsButton = new Button("Notifications");
        notificationsButton.setMinWidth(140);
        notificationsButton.setMinHeight(140);

        sideBar.getChildren().addAll(logOutButton, appointmentsButton, notificationsButton);
        mainHBox.getChildren().add(sideBar);

        VBox mainVBox = new VBox();

        mainHBox.getChildren().add(mainVBox);

        AnchorPane topAnchorPane = new AnchorPane();
        mainVBox.getChildren().add(topAnchorPane);

        Label title = new Label("Your Appointments");
        title.setPadding(new Insets(45,0,0,20));
        title.setFont(new Font("Arial Black", 60));
        title.setTextFill(Color.rgb(128, 206, 225));
        AnchorPane.setLeftAnchor(title, 10.0);

        Label patientName = new Label("Patient Name");
        patientName.setPadding(new Insets(60,0,0,20));
        patientName.setFont(new Font("Arial Black", 40));
        patientName.setTextFill(Color.rgb(128, 206, 225));
        AnchorPane.setRightAnchor(patientName, 10.0);
        AnchorPane.setLeftAnchor(patientName, 1300.0);

        topAnchorPane.getChildren().addAll(title, patientName);

        FlowPane flowPane = new FlowPane();
        flowPane.setPadding(new Insets(40, 40, 40, 40));
        flowPane.setHgap(40);
        flowPane.setVgap(40);
        mainVBox.getChildren().add(flowPane);

        AppointmentBox test = new AppointmentBox("18th December 2019", "14:30", "Dental X-Ray", "Dr Winters", "Examination Room 3");
        AppointmentBox test2 = new AppointmentBox("18th December 2019", "14:30", "Dental X-Ray", "Dr Winters", "Examination Room 3");
        AppointmentBox test3 = new AppointmentBox("18th December 2019", "14:30", "Dental X-Ray", "Dr Winters", "Examination Room 3");
        AppointmentBox test4 = new AppointmentBox("18th December 2019", "14:30", "Dental X-Ray", "Dr Winters", "Examination Room 3");
        AppointmentBox test5 = new AppointmentBox("18th December 2019", "14:30", "Dental X-Ray", "Dr Winters", "Examination Room 3");

        flowPane.getChildren().add(test);
        flowPane.getChildren().add(test2);
        flowPane.getChildren().add(test3);
        flowPane.getChildren().add(test4);
        flowPane.getChildren().add(test5);

        primaryStage.setScene(new Scene(pane, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
