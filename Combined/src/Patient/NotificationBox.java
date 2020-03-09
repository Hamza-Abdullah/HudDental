package Patient;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class NotificationBox extends VBox{

    public NotificationBox(String description, String timeCreated){
        setStyle("-fx-background-color: #0FBCF9");
        setPadding(new Insets(5, 5, 5, 5));

        Label descriptionLabel = new Label(description);
        Label timeCreatedLabel = new Label(timeCreated);

        getChildren().addAll(descriptionLabel, timeCreatedLabel);
    }

}
