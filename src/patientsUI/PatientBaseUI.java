package patientsUI;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PatientBaseUI extends Pane {
    private VBox mainVBox;

    public PatientBaseUI() {
        this.setStyle("-fx-background-color: #80CEE1, #ffffff; -fx-background-insets: 0, 0 0 0 260;");

        HBox mainHBox = new HBox();
        this.getChildren().add(mainHBox);

        VBox sideBar = new VBox();
        sideBar.setPadding(new Insets(40, 60, 0, 60));
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

        this.mainVBox = new VBox();

        mainHBox.getChildren().add(mainVBox);

        AnchorPane topAnchorPane = new AnchorPane();
        mainVBox.getChildren().add(topAnchorPane);

        Label title = new Label("Your Appointments");
        title.setPadding(new Insets(45, 0, 0, 20));
        title.setFont(new Font("Arial Black", 60));
        title.setTextFill(Color.rgb(128, 206, 225));
        AnchorPane.setLeftAnchor(title, 10.0);

        Label patientName = new Label("Patient Name");
        patientName.setPadding(new Insets(60, 0, 0, 20));
        patientName.setFont(new Font("Arial Black", 40));
        patientName.setTextFill(Color.rgb(128, 206, 225));
        AnchorPane.setRightAnchor(patientName, 10.0);
        AnchorPane.setLeftAnchor(patientName, 1300.0);

        topAnchorPane.getChildren().addAll(title, patientName);
    }

    public VBox getMainVBox() {
        return this.mainVBox;
    }
}
