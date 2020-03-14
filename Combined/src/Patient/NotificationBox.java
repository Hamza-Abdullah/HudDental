package Patient;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class NotificationBox extends VBox{

    public NotificationBox(String description, String timeCreated){
        setStyle("-fx-background-color: #0FBCF9");
        setPadding(new Insets(5, 5, 5, 5));

        Label descriptionLabel = new Label(description);
        descriptionLabel.setFont(new Font("Quicksand", 13));
        descriptionLabel.setTextFill(Paint.valueOf("#FFFFFF"));

        Label timeCreatedLabel = new Label("Time Sent: " + timeCreated);
        timeCreatedLabel.setFont(new Font("Quicksand", 11));
        timeCreatedLabel.setTextFill(Paint.valueOf("#FFFFFF"));

        getChildren().addAll(descriptionLabel, timeCreatedLabel);
    }

}
