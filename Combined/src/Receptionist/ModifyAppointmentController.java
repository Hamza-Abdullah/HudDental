package Receptionist;

import Database.MySQL;
import Login.LoginController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;

public class ModifyAppointmentController implements Initializable {
    public Text dashboardButton;
    public TextField phoneField;
    public DatePicker dobField;
    public TextArea notesField;
    public Text statusText;
    public Text staffName;
    public ListView appField;
    public ComboBox treatmentField;
    public Button updateBtn;
    public Button getBtn;
    public Button deleteBtn;

    ArrayList<String> appointmentList = new ArrayList<String>();
    String phone;
    LocalDate dob;
    int selectedAppointmentID, selectedTreatmentID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateBtn.setDisable(true);
        MySQL.getStaffByID(LoginController.staffID);
        staffName.setText(MySQL.staffFirstName + " " + MySQL.staffSurname);

        dobField.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) > 0);
            }
        });

        appField.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                deleteBtn.setDisable(false);
                deleteBtn.setVisible(true);
                treatmentField.setDisable(false);
                notesField.setDisable(false);
                updateBtn.setDisable(false);
                selectedAppointmentID = Integer.parseInt(appointmentList.get(appField.getSelectionModel().getSelectedIndex()));
                HashMap<String, Object> thisAppointment = MySQL.getResults("SELECT * FROM appointments WHERE appointment_id = " +
                        selectedAppointmentID + ";").get(0);
                selectedTreatmentID = (int) thisAppointment.get("appointment_treatment");
                MySQL.getTreatment(selectedTreatmentID);
                treatmentField.getSelectionModel().select(MySQL.treatmentName);
                notesField.setText(thisAppointment.get("appointment_notes").toString());
            }
        });
    }

    public void registerPatient(MouseEvent mouseEvent) throws IOException {
        Parent rp = FXMLLoader.load(getClass().getResource("RegisterPatient.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(rp);
        stage.setTitle("HudDental | Register patient");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        //stage.show();
    }

    public void clearBtnAction(MouseEvent mouseEvent) {
        phoneField.setText(null);
        dobField.setValue(null);
        notesField.setText(null);
        treatmentField.getItems().clear();
        appointmentList.clear();
        appField.getItems().clear();
        statusText.setFill(Color.web("0x0000AA",1.0));
        statusText.setText("Status: Cleared fields.");
        disableFields(true);
        deleteBtn.setVisible(false);
        phoneField.setDisable(false);
        dobField.setDisable(false);
        getBtn.setDisable(false);
    }

    public void disableFields(Boolean active) {
        phoneField.setDisable(active);
        dobField.setDisable(active);
        notesField.setDisable(active);
        appField.setDisable(active);
        treatmentField.setDisable(active);
        updateBtn.setDisable(active);
        deleteBtn.setDisable(active);
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

    public void bookApp(MouseEvent mouseEvent) {
        Parent recParent = null;
        try {
            recParent = FXMLLoader.load(getClass().getResource("BookAppointment.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene recScene = new Scene(recParent);

        //Set stage info
        Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        window.setScene(recScene);
        window.show();
    }

    public void updateBtnAction(MouseEvent mouseEvent) {
        statusText.setFill(Color.web("0xFFBB00",1.0));
        statusText.setText("Status: Updating appointment...");
        disableFields(true);
        getBtn.setDisable(true);

        String newTreatmentName = treatmentField.getSelectionModel().getSelectedItem().toString();
        int newTreatment = (int) MySQL.getResults("SELECT * FROM treatments WHERE treatment_name = '" +
                newTreatmentName + "';").get(0).get("treatment_id");
        String newNotes = notesField.getText();
        MySQL.getResults("UPDATE appointments SET appointment_treatment = " + newTreatment +
                ", appointment_notes = '" + newNotes + "' WHERE appointment_id = " + selectedAppointmentID + ";");

        statusText.setFill(Color.web("0x00AA00",1.0));
        statusText.setText("Status: Updated appointment treatment and notes.");
    }

    public void getBtnAction(MouseEvent mouseEvent) {
        statusText.setFill(Color.web("0xFFBB00",1.0));
        statusText.setText("Status: Getting appointments...");

        phone = phoneField.getText();
        dob = dobField.getValue();
        disableFields(true);

        if (phone.equals("") | dob == null) {
            statusText.setFill(Color.web("0xAA0000",1.0));
            statusText.setText("Status: Failed to retrieve. Make sure you enter the two fields.");
            phoneField.setDisable(false);
            dobField.setDisable(false);
            return;
        }

        MySQL.getPatient(phone, dob.toString());
        if (MySQL.patientID == -1) {
            statusText.setFill(Color.web("0xAA0000",1.0));
            statusText.setText("Status: Failed to retrieve. Patient doesn't exist with those details.");
            disableFields(false);
            return;
        }

        // List treatments
        ArrayList<HashMap<String, Object>> treatments = MySQL.getResults("SELECT * FROM treatments;");
        for (int count = 0; count < treatments.size(); count++) {
            treatmentField.getItems().add(treatments.get(count).get("treatment_name"));
        }

        statusText.setFill(Color.web("0x0000AA",1.0));
        statusText.setText("Status: Retrieved appointments.");
        disableFields(false);
        treatmentField.setDisable(true);
        notesField.setDisable(true);
        updateBtn.setDisable(true);
        appField.getItems().clear();

        int treatmentID;
        ArrayList<HashMap<String, Object>> appointments = MySQL.getResults("SELECT * FROM appointments " +
                "WHERE appointment_patient = " + MySQL.patientID + " " +
                "AND appointment_date > '" + LocalDate.now() + "' " +
                "OR (appointment_time > '" + LocalTime.now() +
                "' AND appointment_date > '" + LocalDate.now().minusDays(1) + "');");
        if (appointments.isEmpty()) {
            appField.getItems().add("No appointments booked for this patient.");
            statusText.setText("");
            appField.setDisable(true);
            notesField.setDisable(true);
            return;
        }

        for (int count = 0; count < appointments.size(); count++) {
            treatmentID = (int) appointments.get(count).get("appointment_treatment");
            MySQL.getTreatment(treatmentID);
            String time = LocalTime.parse(appointments.get(count).get("appointment_time").toString()).minusHours(1).toString();
            String item = appointments.get(count).get("appointment_id") + ". Date: " + appointments.get(count).get("appointment_date") +
                    ", Time: " + time + ", Treatment: " + MySQL.treatmentName;
            appField.getItems().add(item);
            appointmentList.add(appointments.get(count).get("appointment_id").toString());
        }

        getBtn.setDisable(true);
    }

    public void deleteBtnAction(MouseEvent mouseEvent) {
        statusText.setFill(Color.web("0xFFBB00",1.0));
        statusText.setText("Status: Deleting appointment...");
        disableFields(true);
        getBtn.setDisable(true);

        MySQL.getResults("DELETE FROM appointments WHERE appointment_id = " + selectedAppointmentID + ";");

        statusText.setFill(Color.web("0xAA0000",1.0));
        statusText.setText("Status: Permanently deleted appointment.");
    }

    public void profile(MouseEvent mouseEvent) {
        Parent recParent = null;
        try {
            recParent = FXMLLoader.load(getClass().getResource("../Profile/Profile.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene recScene = new Scene(recParent);

        //Set stage info
        Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        window.setScene(recScene);
        window.show();
    }

    public void requestLeave(MouseEvent mouseEvent) {
        Parent recParent = null;
        try {
            recParent = FXMLLoader.load(getClass().getResource("../LeaveRequest/LeaveRequest.fxml"));
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
