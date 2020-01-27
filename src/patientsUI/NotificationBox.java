package patientsUI;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class NotificationBox extends VBox {
    private Label timeLabel;
    private Label notificationLabel;

    public NotificationBox() {
        setStyle("-fx-background-color: #80CEE1");
        setFillWidth(false);
        setPadding(new Insets(10, 10, 10, 10));

        timeLabel = new Label();
        timeLabel.setFont(new Font("Arial Black", 15));

        notificationLabel = new Label();
        notificationLabel.setFont(new Font("Arial Black", 20));

        getChildren().addAll(timeLabel, notificationLabel);
    }

    public void setTimeLabel(String string){
        timeLabel.setText(string);
    }

    public void setNotificationLabel(String string){
        notificationLabel.setText(string);
    }
}