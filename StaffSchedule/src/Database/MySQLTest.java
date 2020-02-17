package Database;

import com.sun.tools.javac.Main;

import java.util.ArrayList;
import java.util.HashMap;

public class MySQLTest {
    public static void main(String[] args) {
        /*
        HOW TO GET DATA FROM THE DATABASE
        Put your SQL query as the parameter inside results.getResults()
        This will put each row into a HashMap(String, Object)
        Each row is put into an ArrayList
        E.g. If I wanted the value of patient_firstname for the fifth row:
        MySQL results = new MySQL();
        ArrayList<HashMap<String,Object>> allResults = results.getResults("SELECT * FROM patients;");
        String firstname = (allResults.get(4).get("patient_firstname")).toString();
        I can now use the variable firstname wherever I want and it won't alter anything in the database.
        Since all the results from the query are stored in the variable allResults, I can get other values too:
        String dateOfBirth = (allResults.get(4).get("patient_dob")).toString();
        Etc
        */

        MySQL results = new MySQL();

        MySQL.newAppointment(12, 8, 2, "notes", "20/02/16","0", 3, 4, "13:00");
    }
}