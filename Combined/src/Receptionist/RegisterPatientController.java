package Receptionist;

import Database.MySQL;
import javafx.animation.PauseTransition;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class RegisterPatientController implements Initializable {

    public TextField firstNameField;
    public TextField surnameField;
    public DatePicker dobField;
    public TextField phoneField;
    public TextField houseNumField;
    public TextField streetField;
    public TextField cityField;
    public TextField postcodeField;
    public Button submitBtn;
    public Button closeBtn;
    public Text statusText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dobField.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) > 0);
            }
        });
    }

    public void submitBtnAction(javafx.scene.input.MouseEvent mouseEvent) throws InterruptedException {
        statusText.setFill(Color.web("0xFFBB00",1.0));
        statusText.setText("Status: In progress...");

        String houseNum = houseNumField.getText();
        String street = streetField.getText();
        String city = cityField.getText();
        String postcode = postcodeField.getText();
        String firstName = firstNameField.getText();
        String surname = surnameField.getText();
        LocalDate dob = dobField.getValue();
        String phone = phoneField.getText();
        disableFields(true);

        if (houseNum.equals("") | street.equals("") | city.equals("") | postcode.equals("") | firstName.equals("")
        | surname.equals("") | dob == null | phone.equals("")) {
            statusText.setFill(Color.web("0xAA0000",1.0));
            statusText.setText("Status: Submission failed. Make sure you enter all the fields.");
            disableFields(false);
            return;
        }

        if (postcode.length() > 5 && !postcode.substring(postcode.length() - 4, postcode.length() - 3).equals(" ")) {
            statusText.setFill(Color.web("0xAA0000",1.0));
            statusText.setText("Status: Submission failed. Make sure you enter a valid postcode.");
            disableFields(false);
            return;
        }

        ArrayList<HashMap<String, Object>> addresses = MySQL.getResults("SELECT * FROM addresses WHERE " +
                "address_number = '" + houseNum + "' AND " +
                "address_street = '" + street + "' AND " +
                "address_city = '" + city + "' AND " +
                "address_postcode = '" + postcode + "';");

        if (addresses.isEmpty()) {
            //statusText.setFill(Color.web("0x00AA00",1.0));
            MySQL.newAddress(houseNum, street, city, postcode);
            addresses = MySQL.getResults("SELECT * FROM addresses WHERE " +
                    "address_number = '" + houseNum + "' AND " +
                    "address_street = '" + street + "' AND " +
                    "address_city = '" + city + "' AND " +
                    "address_postcode = '" + postcode + "';");
            int returnedAddressID = (int) addresses.get(0).get("address_id");
            MySQL.getAddress(returnedAddressID);

            //statusText.setText("Status: Created new address.");
            pauseApp("Status: Created new address.", 2);
        } else if (addresses.size() == 1) {
            int returnedAddressID = (int) addresses.get(0).get("address_id");
            MySQL.getAddress(returnedAddressID);

            //statusText.setText("Status: Address already exists. Retrieved existing data.");
            pauseApp("Status: Address already exists. Retrieved existing data.", 2);
        } else {
            statusText.setFill(Color.web("0xAA0000",1.0));
            statusText.setText("Status: Failed to retrieve address.");
            return;
        }

        String formattedDate = dob.toString();

        ArrayList<HashMap<String, Object>> patients = MySQL.getResults("SELECT * FROM patients WHERE " +
                "patient_firstname = '" + firstName + "' AND " +
                "patient_surname = '" + surname + "' AND " +
                "patient_dob = '" + formattedDate + "' AND " +
                "patient_phone = '" + phone + "' AND " +
                "patient_address = " + MySQL.addressID + ";");

        if (patients.isEmpty()) {
            MySQL.newPatient(firstName, surname, formattedDate, phone, MySQL.addressID);
            MySQL.getPatient(phone, formattedDate);
            // Default password is date of birth in form: yyyy-mm-dd
            Login.NewLogin.createPatientLogin(formattedDate, phone);

            //statusText.setText("Status: Created new patient.");
            pauseApp("Status: Created new patient: " + MySQL.patientFirstName, 4);
        } else if (patients.size() == 1) {
            HashMap<String, Object> returnedPatient = patients.get(0);
            MySQL.getPatient(returnedPatient.get("patient_phone").toString(), returnedPatient.get("patient_dob").toString());

            //statusText.setText("Status: Patient already exists. Retrieved existing data.");
            pauseApp("Status: Patient already exists.", 4);
        } else {
            statusText.setFill(Color.web("0xAA0000",1.0));
            statusText.setText("Status: Failed to retrieve patient details.");
            return;
        }

        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event ->{
            disableFields(false);
        });
        pause.play();
    }

    public void cancelBtnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    public void pauseApp(String statusMessage, int numOfSeconds) {
        PauseTransition pause = new PauseTransition(Duration.seconds(numOfSeconds));
        pause.setOnFinished(event ->{
            statusText.setFill(Color.web("0x0000AA",1.0));
            statusText.setText(statusMessage);
        });
        pause.play();
    }

    public void disableFields(Boolean active) {
        houseNumField.setDisable(active);
        streetField.setDisable(active);
        cityField.setDisable(active);
        postcodeField.setDisable(active);
        firstNameField.setDisable(active);
        surnameField.setDisable(active);
        dobField.setDisable(active);
        phoneField.setDisable(active);
    }
}
