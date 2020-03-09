package Patient;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

public class PatientController {
    @FXML
    public BorderPane borderPane;
    @FXML
    public ImageView notificationImage;

    public void addVBox(VBox vBox){
        borderPane.setCenter(vBox);

        notificationImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showNotifications();
            }
        });
    }

    private void showNotifications(){
        NotificationDialog dialog = new NotificationDialog();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(notificationImage.getScene().getWindow());
        dialog.show();
    }
}
