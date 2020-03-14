package GUI;

import Database.MySQL;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
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
 * Controller displaying requests for manager to approve or deny.
 *
 * @author Mohammad Danyal
 * @version March 2020
 *
 */

public class RequestController implements Initializable {

    public ListView listRequests;
    public Button btnApprove;
    public Button btnDeny;

    public HBox btnDashboard;

    ObservableList list= FXCollections.observableArrayList();
    ObservableList listOfRequestID = FXCollections.observableArrayList();

    MySQL results = new MySQL();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        retrieveRequests();

        }

    @FXML
    private void handleButtonAction(MouseEvent event) {
        Parent recParent = null;
        if (event.getTarget() == btnDashboard) {
            try {
                recParent = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            Scene recScene = new Scene(recParent);

            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            window.setScene(recScene);
            window.show();
        }

    }



    private void retrieveRequests() {

        ArrayList<HashMap<String,Object>> requests = results.getResults("SELECT *" +
                "FROM holidays WHERE holiday_approved = 0 ORDER BY holiday_start ASC");

        int numberOfEntries = (int) requests.size();

        for (int i=0; i<numberOfEntries; i++) {
            int requestsStaffID = (int) requests.get(i).get("holiday_staff");
            int requestID = (int) requests.get(i).get("holiday_id");

            int reasonID = (int) requests.get(i).get("holiday_reason");
            MySQL.getReasonName(reasonID);
            String reasonName = MySQL.reasonName;

            MySQL.getStaffByID(requestsStaffID);

            Date dateStart = (Date) requests.get(i).get("holiday_start");
            Date dateEnd = (Date) requests.get(i).get("holiday_end");


            list.add("Staff ID:               Name:               Position:               " +
                    "Duration:                                               Reason: \n " + requestsStaffID + "                         " +
                    MySQL.staffFirstName + "                 " + MySQL.staffRole + "                   " + dateStart + " to " + dateEnd
            + "                   " + reasonName);

            listOfRequestID.add(requestID);

        }

        listRequests.getItems().addAll(list);

    }

    public void approve(ActionEvent actionEvent) {
        int i = listRequests.getSelectionModel().getSelectedIndex();
        if (i != -1) {

            int id = (int) listOfRequestID.get(i);
            MySQL.approve(id);

            list.remove(i);
            listOfRequestID.remove(i);
            listRequests.getItems().remove(i);
            listRequests.refresh();
        }
    }

    public void deny(ActionEvent actionEvent) {
        int i = listRequests.getSelectionModel().getSelectedIndex();
        if (i != -1) {

            int id = (int) listOfRequestID.get(i);
            MySQL.deny(id);

            list.remove(i);
            listOfRequestID.remove(i);
            listRequests.getItems().remove(i);
            listRequests.refresh();
        }
    }



}

