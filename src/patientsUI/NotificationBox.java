package patientsUI;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class NotificationBox extends VBox {
    private Label timeLabel;
    private Label notificationLabel;

    public NotificationBox() {
        this.setStyle("-fx-background-color: #80CEE1");
        this.setFillWidth(false);
        this.setPadding(new Insets(10, 10, 10, 10));

        this.timeLabel = new Label();
        this.timeLabel.setFont(new Font("Arial Black", 15));

        this.notificationLabel = new Label();
        this.notificationLabel.setFont(new Font("Arial Black", 20));

        this.getChildren().addAll(timeLabel, notificationLabel);
    }

    public void setTimeLabel(String string){
        this.timeLabel.setText(string);
    }

    public void setNotificationLabel(String string){
        this.notificationLabel.setText(string);
    }
}