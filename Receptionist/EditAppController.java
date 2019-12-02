package Receptionist;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class EditAppController {

    @FXML
    public Button searchbutton;


    public void searchbuttonclick(ActionEvent event) throws IOException {

        Stage stage;
        Parent root;


        if (event.getSource() == searchbutton) {
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("EditAppConfirmation.fxml"));
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(searchbutton.getScene().getWindow());
            stage.showAndWait();

        } else {
            stage = (Stage) searchbutton.getScene().getWindow();
            stage.close();
        }
    }
}
