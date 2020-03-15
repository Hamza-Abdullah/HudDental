package LeaveRequest;

import Database.MySQL;
import Login.LoginController;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.DetailedDayView;
import com.calendarfx.view.DetailedWeekView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Time;
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Controller for staff leave request form
 *
 * @author Mohammad Danyal
 * @version February 2020
 */

public class LeaveRequestController implements Initializable {


    public Button btnSubmit;
    public ComboBox reason;
    public DatePicker dateStart;
    public DatePicker dateEnd;
    public TextArea notes;
    //public int staffID = 4;
    public int staffID = LoginController.staffID;
    public Text staffName;


    MySQL results = new MySQL();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MySQL.getStaffByID(staffID);
        staffName.setText(MySQL.staffFirstName + " " + MySQL.staffSurname);
        setupDatePickers();
        setupComboBox();
    }

    public void comboAction(ActionEvent event) {

        if ((reason.getSelectionModel().getSelectedItem().equals("Other")) && (!(dateStart.getValue() ==null)) && (!(dateEnd.getValue() ==null))) {
            notes.setVisible(true);

        } else if (((!(reason.getSelectionModel().getSelectedItem().equals("Other")) )&& (reason.getSelectionModel().isEmpty()==false))  && (!(dateStart.getValue() ==null)) && (!(dateEnd.getValue() ==null))) {
            notes.setVisible(false);
            btnSubmit.setDisable(false);
        }
    }

    @FXML
    public void handleButtonAction(MouseEvent event) {
        System.out.println(event.getTarget());

        //if (event.getTarget() == btnSubmit) {

            if ( dateStart.getValue() == null ) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No start date selected", ButtonType.OK);
                alert.showAndWait();
            } else if (dateEnd.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No end date selected", ButtonType.OK);
                alert.showAndWait();
            } else if (reason.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No reason selected", ButtonType.OK);
                alert.showAndWait();
            } else {

                LocalDate localStart = dateStart.getValue();
                LocalDate localEnd = dateEnd.getValue();

                Date start = Date.valueOf(localStart);
                Date end = Date.valueOf(localEnd);

                    //System.out.println(reason.getSelectionModel().getSelectedIndex());
                    MySQL.makeLeaveRequest(start, end, staffID, reason.getSelectionModel().getSelectedIndex() + 1);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Leave Requested", ButtonType.OK);
                    alert.showAndWait();

            }
        //}
    }

    public void restrictDatePicker(DatePicker datePicker, LocalDate minDate) {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(minDate)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        datePicker.setDayCellFactory(dayCellFactory);
    }

    private void setupDatePickers(){
        restrictDatePicker(dateStart, LocalDate.now());
        restrictDatePicker(dateEnd, LocalDate.now());

        dateStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    if (dateStart.getValue().isAfter(dateEnd.getValue())){
                        dateEnd.setValue(null);
                    }
                }catch (NullPointerException e){}
            }
        });

        dateEnd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (dateEnd.getValue().isBefore(dateStart.getValue())){
                        dateStart.setValue(null);
                    }
                } catch (NullPointerException e) { }
            }
        });
    }

    private void setupComboBox(){

        ArrayList<HashMap<String,Object>> allResults =
                results.getResults("SELECT * FROM holidayReasons;");

        for (int i=1; i < allResults.size() + 1; i++) {
            MySQL.getReasonName(i);
            reason.getItems().add(MySQL.reasonName);
        }
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

    public void backToSchedule(MouseEvent mouseEvent) {

        Parent recParent = null;
        try {
            recParent = FXMLLoader.load(getClass().getResource("../StaffSchedule/GUI.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene recScene = new Scene(recParent);


        //Set stage info
        Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        window.setScene(recScene);
        window.show();
    }

    public void backToRec(MouseEvent mouseEvent) {

        Parent recParent = null;
        try {
            recParent = FXMLLoader.load(getClass().getResource("../Receptionist/BookAppointment.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene recScene = new Scene(recParent);


        //Set stage info
        Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        window.setScene(recScene);
        window.show();
    }

    public void back(MouseEvent mouseEvent) {

        if (MySQL.staffRole.equals("Receptionist")) {
            backToRec(mouseEvent);
        } else {
            backToSchedule(mouseEvent);
        }
    }
}