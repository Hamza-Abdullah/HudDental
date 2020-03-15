package Manager;

import Database.MySQL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Controller for displaying manager dashboard.
 *
 * @author Mohammad Danyal
 * @version March 2020
 *
 */

public class DashboardController implements Initializable {

    public Text textTotalPatients;
    public Text textTotalPatientsYear;
    public Text textTotalPatientsMonth;
    public Text textTotalBookings;
    public Text textTotalBookingsYear;
    public Text textTotalBookingsMonth;
    public Text textTreatment;
    public Text textTreatmentYear;
    public Text textTreatmentMonth;
    public BarChart graphBookings;
    public BarChart graphTreatments;
    public BarChart graphRegistrations;
    public HBox btnRequests;
    public Text staffName;


    MySQL results = new MySQL();
    Date currentdate = Date.valueOf(LocalDate.now());
    Date pastmonth = Date.valueOf(LocalDate.now().minusMonths(1));
    Date pastyear = Date.valueOf(LocalDate.now().minusYears(1));
    int currentyear = LocalDate.now().getYear();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MySQL.getStaffByID(16);
        staffName.setText(MySQL.staffFirstName + " " + MySQL.staffSurname);

        updateAppointmentMetrics();
        updateTreatmentMetrics();

        drawBookingGraph();
        drawTreatmentGraph();
    }

    @FXML
    private void handleButtonAction(MouseEvent event) {
        Parent recParent = null;
        if (event.getTarget() == btnRequests) {

            try {
                recParent = FXMLLoader.load(getClass().getResource("Requests.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            Scene recScene = new Scene(recParent);

            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            window.setScene(recScene);
            window.show();
        }
    }

    private void updateAppointmentMetrics() {
        ArrayList<HashMap<String,Object>> bookings = results.getResults("SELECT * FROM appointments;");
        int noOfBookings = bookings.size();
        textTotalBookings.setText("" + noOfBookings);

        ArrayList<HashMap<String,Object>> bookingsMonth = results.getResults("SELECT * FROM appointments WHERE " +
                "appointment_date BETWEEN '" + pastmonth + "'" + " AND '" + currentdate + "'");
        int noOfBookingsMonth = bookingsMonth.size();
        textTotalBookingsMonth.setText("" + noOfBookingsMonth);

        ArrayList<HashMap<String,Object>> bookingsYear = results.getResults("SELECT * FROM appointments WHERE " +
                "appointment_date BETWEEN '" + pastyear + "'" + " AND '" + currentdate + "'");
        int noOfBookingsYear = bookingsYear.size();
        textTotalBookingsYear.setText("" + noOfBookingsYear);
    }

    private void updateTreatmentMetrics() {
        ArrayList<HashMap<String,Object>> treatment = results.getResults("SELECT appointment_treatment, COUNT(appointment_id) " +
                "AS numberOfBookings FROM appointments GROUP BY appointment_treatment ORDER BY numberOfBookings DESC LIMIT 1");
        int popularTreatment = (int) treatment.get(0).get("appointment_treatment");
        String Treatment = MySQL.getTreatmentName(popularTreatment);
        textTreatment.setText("" + Treatment);

        ArrayList<HashMap<String,Object>> treatmentMonth = results.getResults("SELECT appointment_treatment, COUNT(appointment_id) " +
                "AS numberOfBookings FROM appointments WHERE appointment_date BETWEEN '" + pastmonth + "'" + " AND '" + currentdate + "'"
                + "GROUP BY appointment_treatment ORDER BY numberOfBookings DESC LIMIT 1");
        int popularTreatmentMonth = (int) treatment.get(0).get("appointment_treatment");
        String TreatmentMonth = MySQL.getTreatmentName(popularTreatmentMonth);
        textTreatmentMonth.setText("" + TreatmentMonth);

        ArrayList<HashMap<String,Object>> treatmentYear = results.getResults("SELECT appointment_treatment, COUNT(appointment_id) " +
                "AS numberOfBookings FROM appointments WHERE appointment_date BETWEEN '" + pastyear + "'" + " AND '" + currentdate + "'"
                + "GROUP BY appointment_treatment ORDER BY numberOfBookings DESC LIMIT 1");
        int popularTreatmentYear = (int) treatment.get(0).get("appointment_treatment");
        String TreatmentYear = MySQL.getTreatmentName(popularTreatmentYear);
        textTreatmentYear.setText("" + TreatmentYear);
    }

    private void drawBookingGraph() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);

        XYChart.Series series1 = new XYChart.Series();

        for (int i=1; i<13; i++) {
            ArrayList<HashMap<String,Object>> appointments = results.getResults("SELECT COUNT(*) AS numberOfAppointments FROM appointments " +
                    "WHERE MONTH(appointment_date) = '" + i + "'" + " AND YEAR(appointment_date) = '" + currentyear + "';");
            series1.getData().add(new XYChart.Data(Month.of(i).toString(), appointments.get(0).get("numberOfAppointments")));
        }

        graphBookings.getData().addAll(series1);
    }

    private void drawTreatmentGraph() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);

        XYChart.Series series2 = new XYChart.Series();

        ArrayList<HashMap<String,Object>> treatment = results.getResults("SELECT appointment_treatment, COUNT(appointment_id) " +
                "AS numberOfBookings FROM appointments GROUP BY appointment_treatment ORDER BY numberOfBookings DESC;");

        int treatmentID;

        for (int j=0; j < treatment.size(); j++) {
            treatmentID = (int) treatment.get(j).get("appointment_treatment");
            String treatmentName = MySQL.getTreatmentName(treatmentID);
            String treatmentNameCondensed = (treatmentName.substring(0,5) + "...");
            series2.getData().add(new XYChart.Data(treatmentNameCondensed, treatment.get(j).get("numberOfBookings")));
        }

        graphTreatments.getData().addAll(series2);
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
}
