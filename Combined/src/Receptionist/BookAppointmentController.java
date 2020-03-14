package Receptionist;

import Login.LoginController;
import Database.MySQL;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;

public class BookAppointmentController implements Initializable {
    public Text dashboardButton;
    public ComboBox treatmentField;
    public TextField phoneField;
    public DatePicker dobField;
    public DatePicker appointmentDateField;
    public TextField timeField;
    public ComboBox dentistField;
    public ComboBox nurseField;
    public ComboBox roomField;
    public TextArea notesField;
    public Text statusText;
    public Text staffName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MySQL.getStaffByID(LoginController.staffID);
        staffName.setText(MySQL.staffFirstName + " " + MySQL.staffSurname);
        // List treatments
        ArrayList<HashMap<String, Object>> treatments = MySQL.getResults("SELECT * FROM treatments;");
        for (int count = 0; count < treatments.size(); count++) {
            treatmentField.getItems().add(treatments.get(count).get("treatment_name"));
        }

        dobField.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) > 0);
            }
        });

        appointmentDateField.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) < 0);
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

    public void submitBtnAction(MouseEvent mouseEvent) throws InterruptedException {
        statusText.setFill(Color.web("0xFFBB00",1.0));
        statusText.setText("Status: In progress...");

        String treatment = String.valueOf(treatmentField.getValue());
        String phone = phoneField.getText();
        LocalDate dob = dobField.getValue();
        LocalDate appointmentDate = appointmentDateField.getValue();
        String time = timeField.getText();
        String notes = notesField.getText();
        disableFields(true);

        if (treatment.equals("") | phone.equals("") | dob == null | appointmentDate == null | time.equals("")) {
            statusText.setFill(Color.web("0xAA0000",1.0));
            statusText.setText("Status: Submission failed. Make sure you enter all the fields.");
            disableFields(false);
            return;
        }

        if (time.length() != 5) {
            statusText.setFill(Color.web("0xAA0000",1.0));
            statusText.setText("Status: Submission failed. Time needs to be in 24 hour format.");
            disableFields(false);
            return;
        }

        if (time.length() == 5) {
            if (!time.substring(2, 3).equals(":")) {
                System.out.println(time.substring(2, 3));
                statusText.setFill(Color.web("0xAA0000",1.0));
                statusText.setText("Status: Submission failed. Time needs to be in 24 hour format.");
                disableFields(false);
                return;
            }
        }

        MySQL.getPatient(phone, dob.toString());
        if (MySQL.patientID == -1) {
            statusText.setFill(Color.web("0xAA0000",1.0));
            statusText.setText("Status: Submission failed. Patient doesn't exist with those details.");
            disableFields(false);
            return;
        }

        if (notes.equals("")) {notes = "No notes...";}
        String formattedDate = appointmentDate.toString();
        String formattedTime = time + ":00";
        LocalTime startTime = LocalTime.parse(formattedTime);
        startTime =  startTime.minusHours(1);
        startTime = startTime.minusMinutes(29);
        LocalTime endTime = LocalTime.parse(formattedTime);
        endTime = endTime.plusHours(1);
        endTime = endTime.plusMinutes(29);
//        System.out.println(LocalTime.parse(formattedTime).toString() + startTime.toString() + endTime.toString());
//        String startTime = String.valueOf(Integer.parseInt(formattedTime.substring(0, 2)) - 1) + formattedTime.substring(2, 8);
//        String endTime = String.valueOf(Integer.parseInt(formattedTime.substring(0, 2)) + 1) + formattedTime.substring(2, 8);
        ArrayList<Integer> avaiableDentists = new ArrayList<Integer>();
        for (int count = 8; count < 14; count++) {
            ArrayList<HashMap<String, Object>> getDentists = MySQL.getResults("SELECT * FROM appointments " +
                    "WHERE appointment_date = '" + formattedDate + "' " +
                    "AND appointment_dentist = " + count + " " +
                    "AND appointment_time BETWEEN '" + startTime.toString() + "' AND '" + endTime.toString() + "';");
            if (getDentists.size() == 0) {
                avaiableDentists.add(count);
            }
        }
        ArrayList<Integer> avaiableNurses = new ArrayList<Integer>();
        for (int count = 2; count < 8; count++) {
            ArrayList<HashMap<String, Object>> getNurses = MySQL.getResults("SELECT * FROM appointments " +
                    "WHERE appointment_date = '" + formattedDate + "' " +
                    "AND appointment_nurse = " + count + " " +
                    "AND appointment_time BETWEEN '" + startTime.toString() + "' AND '" + endTime.toString() + "';");
            if (getNurses.size() == 0) {
                avaiableNurses.add(count);
            }
        }
        ArrayList<Integer> avaiableRooms = new ArrayList<Integer>();
        for (int count = 1; count < 7; count++) {
            ArrayList<HashMap<String, Object>> getNurses = MySQL.getResults("SELECT * FROM appointments " +
                    "WHERE appointment_date = '" + formattedDate + "' " +
                    "AND appointment_room = " + count + " " +
                    "AND appointment_time BETWEEN '" + startTime.toString() + "' AND '" + endTime.toString() + "';");
            if (getNurses.size() == 0) {
                avaiableRooms.add(count);
            }
        }

        if (avaiableDentists.isEmpty() | avaiableNurses.isEmpty() | avaiableRooms.isEmpty()) {
            statusText.setFill(Color.web("0xAA0000",1.0));
            statusText.setText("Status: Submission failed. No available staff or room at this time.");
            disableFields(false);
            return;
        }

        Random randomNumber = new Random();
        int dentistSelector = randomNumber.ints(0, avaiableDentists.size()).findFirst().getAsInt();
        int nurseSelector = randomNumber.ints(0, avaiableNurses.size()).findFirst().getAsInt();
        int roomSelector = randomNumber.ints(0, avaiableRooms.size()).findFirst().getAsInt();
        int dentistID = avaiableDentists.get(dentistSelector);
        int nurseID = avaiableNurses.get(nurseSelector);
        int roomID = avaiableRooms.get(roomSelector);

        int treatmentID = (int) MySQL.getResults("SELECT * FROM treatments WHERE " +
                "treatment_name = '" + treatment + "';").get(0).get("treatment_id");
        MySQL.newAppointment(MySQL.patientID, dentistID, nurseID, notes, formattedDate, "0", roomID, treatmentID, formattedTime);
        MySQL.createBookingNotification(MySQL.patientID, "Your " + treatment + "appointment has been booked for " + formattedDate + " at " + time, getDateTime());

        MySQL.getTreatment(treatmentID);
        MySQL.getPrice(Integer.parseInt(MySQL.treatmentBand));
        MySQL.getStaffByID(dentistID);
        String dentistName = MySQL.staffFirstName + " " + MySQL.staffSurname;
        MySQL.getStaffByID(nurseID);
        String nurseName = MySQL.staffFirstName + " " + MySQL.staffSurname;
        statusText.setFill(Color.web("0x00AA00",1.0));
        statusText.setText("Status: Successfully booked treatment " + MySQL.treatmentID + " with dentist " + dentistName +
                " and nurse " + nurseName + " on " + formattedDate + " at " + formattedTime + " in room " + roomID +
                ". Cost: £" + MySQL.price);
        dentistField.setValue(dentistName);
        nurseField.setValue(nurseName);
        roomField.setValue(roomID);
    }

    public void clearBtnAction(MouseEvent mouseEvent) {
        treatmentField.setValue(null);
        phoneField.setText(null);
        dobField.setValue(null);
        appointmentDateField.setValue(null);
        timeField.setText(null);
        dentistField.setValue(null);
        nurseField.setValue(null);
        roomField.setValue(null);
        notesField.setText(null);
        statusText.setFill(Color.web("0x0000AA",1.0));
        statusText.setText("Status: Cleared fields.");
        disableFields(false);
    }

    public void disableFields(Boolean active) {
        treatmentField.setDisable(active);
        phoneField.setDisable(active);
        dobField.setDisable(active);
        appointmentDateField.setDisable(active);
        timeField.setDisable(active);
        notesField.setDisable(active);
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

    public void modifyApp(MouseEvent mouseEvent) {
        Parent recParent = null;
        try {
            recParent = FXMLLoader.load(getClass().getResource("ModifyAppointment.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene recScene = new Scene(recParent);

        //Set stage info
        Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        window.setScene(recScene);
        window.show();
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

    private String getDateTime(){
        String timeString = LocalDateTime.now().toString();
        return timeString.replace('T', ' ').substring(0, timeString.indexOf('.'));
    }
}
