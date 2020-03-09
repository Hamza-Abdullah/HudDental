package Patient;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class NotificationDialog extends Stage {
    public NotificationDialog() {
        Label title = new Label("Notifications");
        title.setFont(new Font("Quicksand", 15));

        VBox notificationVBox = new VBox();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(notificationVBox);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                NotificationDialog.this.close();
            }
        });

        VBox containerVBox = new VBox();
        containerVBox.getChildren().addAll(title, scrollPane, closeButton);

        Scene dialog = new Scene(containerVBox);
        setScene(dialog);
    }
}
