package Sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ReceptionistBookingController {


    public Button btnbook;
    public TextField contact;
    public TextField dob;

    public void findpatient(ActionEvent actionEvent) {


            int x = 3;

            MySQL results = new MySQL();
            ArrayList<HashMap<String, Object>> allResults = results.getResults("SELECT patient_id, patient_firstname, patient_surename FROM patients WHERE 1;");
            String patientid = ((HashMap) allResults.get(x)).get("patient_id").toString();
            String patientfirstname = ((HashMap) allResults.get(x)).get("patient_firstname").toString();
            String patientsurname = ((HashMap) allResults.get(x)).get("patient_surname").toString();
            System.out.println(":" + patientid);
            System.out.println(":" + patientfirstname);
            System.out.println(":" + patientsurname);

            return();


        }

    }
