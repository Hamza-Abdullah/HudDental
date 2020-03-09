package Profile;

import Database.MySQL;
import Login.LoginController;
import Login.NewLogin;
import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    public TextField firstNameField;
    public TextField surnameField;
    public TextField phoneField;
    public TextField houseNumField;
    public TextField streetField;
    public TextField cityField;
    public TextField postcodeField;
    public Button editBtn;
    public Button saveBtn;
    public Text statusText;
    public Text backBtn;
    public PasswordField newPassField;
    public PasswordField confirmPassField;
    public Text profileName;

    private int staffID = LoginController.staffID;
    private int patientID = LoginController.patientID;

    private String firstName, surname, phone, houseNumber, street, city, postcode;
    private String newFirstName, newSurname, newPhone, newHouseNumber, newStreet, newCity, newPostcode, newPass, confirmPass;
    private boolean changingPass = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Staff
        if (staffID != -1 && patientID == -1) {
            getStaffDetails();
        }
        //Patient
        if (patientID != -1 && staffID == -1) {
            getPatientDetails();
        }

        houseNumField.setText(MySQL.houseNumber);
        streetField.setText(MySQL.street);
        cityField.setText(MySQL.city);
        postcodeField.setText(MySQL.postcode);
        storeFields();
        profileName.setText(firstName + " " + surname);
    }

    public void getStaffDetails() {
        MySQL.getStaffByID(staffID);
        firstNameField.setText(MySQL.staffFirstName);
        surnameField.setText(MySQL.staffSurname);
        phoneField.setText(MySQL.staffPhone);
        MySQL.getAddress(MySQL.staffAddressID);
    }

    public void getPatientDetails() {
        MySQL.getPatientByID(patientID);
        firstNameField.setText(MySQL.patientFirstName);
        surnameField.setText(MySQL.patientSurname);
        phoneField.setText(MySQL.patientPhone);
        MySQL.getAddress(MySQL.patientAddressID);
    }

    public void disableFields(Boolean active) {
        houseNumField.setDisable(active);
        streetField.setDisable(active);
        cityField.setDisable(active);
        postcodeField.setDisable(active);
        firstNameField.setDisable(active);
        surnameField.setDisable(active);
        phoneField.setDisable(active);
        newPassField.setDisable(active);
        confirmPassField.setDisable(active);
        saveBtn.setDisable(active);
    }

    public void storeFields() {
        firstName = firstNameField.getText();
        surname = surnameField.getText();
        phone = phoneField.getText();
        houseNumber = houseNumField.getText();
        street = streetField.getText();
        city = cityField.getText();
        postcode = postcodeField.getText();
    }

    public void storeNewFields() {
        newFirstName = firstNameField.getText();
        newSurname = surnameField.getText();
        newPhone = phoneField.getText();
        newHouseNumber = houseNumField.getText();
        newStreet = streetField.getText();
        newCity = cityField.getText();
        newPostcode = postcodeField.getText();
        newPass = newPassField.getText();
        confirmPass = confirmPassField.getText();
    }

    public void editBtnAction(MouseEvent mouseEvent) {
        if (editBtn.getText().equals("Edit")) {
            disableFields(false);
            editBtn.setText("Cancel");
        } else {
            disableFields(true);
            firstNameField.setText(firstName);
            surnameField.setText(surname);
            phoneField.setText(phone);
            houseNumField.setText(houseNumber);
            streetField.setText(street);
            cityField.setText(city);
            postcodeField.setText(postcode);
            newPassField.setText("");
            confirmPassField.setText("");
            editBtn.setText("Edit");
        }
    }

    public void saveBtnAction(MouseEvent mouseEvent) {
        storeNewFields();
        disableFields(true);
        statusText.setFill(Color.web("0xAA0000",1.0));
        // Validation
        if (newPhone.length() != 11 | !newPhone.matches("[0-9]*")) {
            statusText.setText("Status: Enter a valid phone number.");
            disableFields(false);
            return;
        }
        if (newPostcode.length() > 5 && !newPostcode.substring(newPostcode.length() - 4, newPostcode.length() - 3).equals(" ")) {
            statusText.setText("Status: Enter a valid postcode.");
            disableFields(false);
            return;
        }
        if (newHouseNumber.equals("") | newStreet.equals("") | newCity.equals("") | newPostcode.equals("")
                | newFirstName.equals("") | newSurname.equals("") | phone.equals("")) {
            statusText.setText("Status: Make sure you enter all the fields.");
            disableFields(false);
            return;
        }
        if (!newPass.equals("") && !confirmPass.equals(newPass)) {
            statusText.setText("Status: Passwords do not match.");
            disableFields(false);
            return;
        }

        statusText.setFill(Color.web("0x0000AA",1.0));
        if (!newPass.equals("") && confirmPass.equals(newPass)) {
            changingPass = true;
            statusText.setText("Status: Passwords match.");
        }

        ArrayList<HashMap<String, Object>> addresses = MySQL.getResults("SELECT * FROM addresses WHERE " +
                "address_number = '" + newHouseNumber + "' AND " +
                "address_street = '" + newStreet + "' AND " +
                "address_city = '" + newCity + "' AND " +
                "address_postcode = '" + newPostcode + "';");

        if (addresses.isEmpty()) {
            MySQL.newAddress(newHouseNumber, newStreet, newCity, newPostcode);
            addresses = MySQL.getResults("SELECT * FROM addresses WHERE " +
                    "address_number = '" + newHouseNumber + "' AND " +
                    "address_street = '" + newStreet + "' AND " +
                    "address_city = '" + newCity + "' AND " +
                    "address_postcode = '" + newPostcode + "';");
            int returnedAddressID = (int) addresses.get(0).get("address_id");
            MySQL.getAddress(returnedAddressID);

            statusText.setText("Status: Created new address.");
        } else if (addresses.size() == 1) {
            int returnedAddressID = (int) addresses.get(0).get("address_id");
            MySQL.getAddress(returnedAddressID);

            statusText.setText("Status: Address already exists. Retrieved existing data.");
        } else {
            statusText.setText("Status: Failed to retrieve address.");
            return;
        }

        if (patientID != -1) {
            MySQL.getResults("UPDATE patients SET " +
                    "patient_firstname = '" + newFirstName + "', " +
                    "patient_surname = '" + newSurname + "', " +
                    "patient_phone = '" + newPhone + "', " +
                    "patient_address = " + MySQL.addressID + " WHERE " +
                    "patient_id = " + patientID + ";");
            if (changingPass) {
                NewLogin.updateLogin(newPass, newPhone, phone);
            }
        }

        if (staffID != -1) {
            MySQL.getResults("UPDATE staff SET " +
                    "staff_firstname = '" + newFirstName + "', " +
                    "staff_surname = '" + newSurname + "', " +
                    "staff_phone = '" + newPhone + "', " +
                    "staff_address = " + MySQL.addressID + " WHERE " +
                    "staff_id = " + staffID + ";");
            if (changingPass) {
                NewLogin.updateLogin(newPass, newPhone, phone);
            }
        }

        statusText.setFill(Color.web("0x00AA00",1.0));
        statusText.setText("Status: Saved details. Logout and log back in to update.");
        editBtn.setDisable(true);
        saveBtn.setDisable(true);
    }

    public void logout(MouseEvent mouseEvent) {
        Parent recParent = null;
        try {
            recParent = FXMLLoader.load(getClass().getResource("../Login/Login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene recScene = new Scene(recParent);

        //Set stage info
        Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        window.setScene(recScene);
        window.show();
    }
}
