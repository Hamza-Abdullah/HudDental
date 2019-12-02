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

public class BookController {

    @FXML
    public Button book2;



    public void book(ActionEvent event) throws IOException {

        Stage stage;
        Parent root;


        if (event.getSource() == book2) {
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("Confirmation.fxml"));
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(book2.getScene().getWindow());
            stage.showAndWait();

        } else {
            stage = (Stage) book2.getScene().getWindow();
            stage.close();
        }





        }


    }

