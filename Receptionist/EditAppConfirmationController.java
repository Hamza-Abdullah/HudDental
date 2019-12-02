package Receptionist;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class EditAppConfirmationController {
    public void saveclickbutton(ActionEvent actionEvent) {

        Alert a1 = new Alert(Alert.AlertType.WARNING);
        a1.setTitle("Confirmation");
        a1.setHeaderText("Changes to Appointment");
        a1.setContentText("This is a message to confirm the changes to the patients appointment");
        a1.showAndWait();
    }
}
