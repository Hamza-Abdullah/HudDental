package Receptionist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class ConfirmationController implements Initializable{

    @FXML
    public ComboBox <String> apptypecombobox;
    @FXML
    public ComboBox<String> dentistcombobox;
    @FXML
    public ComboBox<Integer> roomcombobox;
    @FXML
    public Button bookbutton;


    ObservableList<String> list = FXCollections.observableArrayList("Check-up", "Teeth Whitening", "Brace tightening");

    ObservableList<String> list2 = FXCollections.observableArrayList("Sam Smith", "Claire Jhonson", "William Reid", "Sarah Green");

    ObservableList<Integer> list3 = FXCollections.observableArrayList(1,2,3,4);




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        apptypecombobox.setItems(list);
        dentistcombobox.setItems(list2);
        roomcombobox.setItems(list3);

    }




    public void apptypeclick(javafx.event.ActionEvent actionEvent) {
    }



    public void dentistclick(ActionEvent actionEvent) {
    }

    public void roomcomboboxclick(ActionEvent actionEvent) {
    }

    public void bookclickbutton(ActionEvent actionEvent) {

        Alert a1 = new Alert(Alert.AlertType.WARNING);
        a1.setTitle("Confirmation");
        a1.setHeaderText("Confirmation for Appointment");
        a1.setContentText("This is a message to confirm the patients appointment");
        a1.showAndWait();


    }
}

