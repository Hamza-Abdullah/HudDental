package StaffSchedule;

import Database.MySQL;
import Login.LoginController;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.DetailedDayView;
import com.calendarfx.view.DetailedWeekView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * ScheduleController for the appointment viewing page.
 *
 * @author Mohammad Danyal
 * @version February 2020
 */

public class ScheduleController implements Initializable {

    public DetailedDayView timetable_day;
    public DetailedWeekView timetable_week;
    public Button btnDayOn;
    public Button btnDayOff;
    public Button btnWeekOn;
    public Button btnWeekOff;

    public boolean IsStaff;
    public Text staffName;
    public int staffID = LoginController.staffID;

    Calendar cal = new Calendar("cal");
    public Button btnDay;
    public Button btnWeek;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addWeekEntries();
        addDayEntries();
        //Set schedule time constraints
        timetable_day.setStartTime(LocalTime.of(9,0));
        timetable_day.setEndTime(LocalTime.of(17,0));
        timetable_week.setStartTime(LocalTime.of(9,0));
        timetable_week.setEndTime(LocalTime.of(17,0));

        MySQL details = new MySQL();
        MySQL.getStaffByID(staffID);
        staffName.setText(MySQL.staffFirstName + " " + MySQL.staffSurname);
    }

    @FXML
    public void handleButtonAction(MouseEvent event) {
        System.out.println(event.getTarget());
        if (event.getTarget()==btnDayOff) {
            System.out.println("day");
            btnDayOn.toFront();
            btnWeekOff.toFront();
            timetable_day.setVisible(true);
            timetable_week.setVisible(false);
            refreshDayTimeTable();
        }

        if (event.getTarget()==btnWeekOff) {
            System.out.println("week");
            btnWeekOn.toFront();
            btnDayOff.toFront();
            timetable_day.setVisible(false);
            timetable_week.setVisible(true);
            refreshWeekTimeTable();
        }
    }

    public void addDayEntries() {
        CalendarSource calendarSource = new CalendarSource("Appointments");
        Calendar c = new Calendar("Day");

        c.isReadOnly();

        MySQL results = new MySQL();
        LocalDate locald = LocalDate.now();
        Date currentdate = Date.valueOf(locald);

        MySQL.getStaffByID(staffID);

        if (MySQL.staffRole.equals("Dentist")) {
        ArrayList<HashMap<String,Object>> allResults = results.getResults("SELECT * FROM appointments WHERE appointment_date = '" + currentdate + "'" + "AND appointment_dentist = " + staffID);

            populateTimeTable(allResults,c);
            calendarSource.getCalendars().setAll(c);

//        do not remove these below lines.
            timetable_day.getCalendarSources().add(calendarSource);
            timetable_day.refreshData();
        }

        else { ArrayList<HashMap<String,Object>> allResults = results.getResults("SELECT * FROM appointments WHERE appointment_date = '" + currentdate + "'" + "AND appointment_nurse = " + staffID);

            populateTimeTable(allResults,c);
            calendarSource.getCalendars().setAll(c);

//        do not remove these below lines.
            timetable_day.getCalendarSources().add(calendarSource);
            timetable_day.refreshData();
        }
    }


    public void addWeekEntries() {
        CalendarSource calendarSource = new CalendarSource("Appointments");
        Calendar c = new Calendar("Week");

        c.isReadOnly();

        MySQL results = new MySQL();
        int localmonth = LocalDate.now().getMonthValue();

        MySQL.getStaffByID(staffID);

        if (MySQL.staffRole.equals("Dentist")) {
            ArrayList<HashMap<String, Object>> allResults = results.getResults("SELECT * FROM appointments WHERE MONTH(appointment_date)='" + localmonth + "'" + "AND appointment_dentist = " + staffID);

            populateTimeTable(allResults,c);

            calendarSource.getCalendars().setAll(c);

//        do not remove these below lines.
            timetable_week.getCalendarSources().add(calendarSource);
            timetable_week.refreshData();
        }

        else {

            ArrayList<HashMap<String, Object>> allResults = results.getResults("SELECT * FROM appointments WHERE MONTH(appointment_date)='" + localmonth + "'" + "AND appointment_nurse = " + staffID);

            populateTimeTable(allResults,c);

            calendarSource.getCalendars().setAll(c);

//        do not remove these below lines.
            timetable_week.getCalendarSources().add(calendarSource);
            timetable_week.refreshData();
        }

    }


    private void refreshDayTimeTable() {
        timetable_day.getCalendarSources().removeAll();
    }

    private void refreshWeekTimeTable() {
        timetable_week.getCalendarSources().removeAll();
    }

    private void populateTimeTable(ArrayList<HashMap<String,Object>>  allResults, Calendar c) {

        int numberOfEntries = (allResults.size());
        int treatmentid;
        int nurseid;
        int dentistid;

        for(int i = 0; i < numberOfEntries; i++) {

            Entry<String> entry = new Entry<>();
            treatmentid = (int) allResults.get(i).get("appointment_treatment");
            MySQL.getTreatment(treatmentid);

            MySQL.getStaffByID(staffID);
            MySQL.getRoom((int) allResults.get(i).get("appointment_room"));

            if (MySQL.staffRole.equals("Dentist")) {
                nurseid = (int) allResults.get(i).get("appointment_nurse");
                MySQL.getStaffByID(nurseid);
                entry.setTitle("Treatment:  " + MySQL.treatmentName + " \nRoom Number:  " + allResults.get(i).get("appointment_room") + "\nFacilities:  " + MySQL.roomFacilities + "\nNurse:  " + MySQL.staffFirstName + " " + MySQL.staffSurname);
            } else {
                dentistid = (int) allResults.get(i).get("appointment_dentist");
                MySQL.getStaffByID(dentistid);
                entry.setTitle("Treatment:  " + MySQL.treatmentName + " \nRoom Number:  " + allResults.get(i).get("appointment_room") + "\nFacilities:  " + MySQL.roomFacilities + "\nDentist:  " + MySQL.staffFirstName + " " + MySQL.staffSurname);
            }
            Date d1 = (Date) allResults.get(i).get("appointment_date");
            LocalDate ld1 = d1.toLocalDate();
            java.sql.Time sqlTime = (Time) allResults.get(i).get("appointment_time");
            LocalTime lt1 = sqlTime.toLocalTime();
            LocalDateTime ldt = LocalDateTime.of(ld1,lt1);
            entry.setInterval(ldt.minusHours(1),ldt.plusMinutes((MySQL.treatmentDuration)));

            c.addEntry(entry);
        }

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

