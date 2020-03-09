package Patient;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class NotificationDialog extends Stage {
    private VBox notificationVBox;

    public NotificationDialog() {
        Label title = new Label("Notifications");
        title.setFont(new Font("Quicksand", 15));

        notificationVBox = new VBox();
        notificationVBox.setSpacing(5);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(notificationVBox);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        VBox containerVBox = new VBox();
        containerVBox.setAlignment(Pos.TOP_CENTER);
        containerVBox.setPadding(new Insets(5, 5, 5, 5));
        containerVBox.setSpacing(5);
        containerVBox.getChildren().addAll(title, scrollPane);

        Scene dialog = new Scene(containerVBox, 250, 300);
        setScene(dialog);
    }

    public void addNotification(NotificationBox notification){
        notificationVBox.getChildren().add(notification);
    }
}
