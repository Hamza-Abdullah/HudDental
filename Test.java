import com.mysql.cj.protocol.a.MysqlBinaryValueDecoder;
import com.sun.tools.javac.Main;

import java.util.ArrayList;
import java.util.HashMap;

public class Test {
    public static void main(String[] args) {
        /*
        HOW TO GET DATA FROM THE DATABASE
        Put your SQL query as the parameter inside results.getResults()
        This will put each row into a HashMap(String, Object)
        Each row is put into an ArrayList
        E.g. If I wanted the value of patient_firstname for the fifth row:
        ArrayList<HashMap<String,Object>> allResults = MySQL.getResults("SELECT * FROM patients;");
        String firstname = (allResults.get(4).get("patient_firstname")).toString();
        I can now use the variable firstname wherever I want and it won't alter anything in the database.
        Since all the results from the query are stored in the variable allResults, I can get other values too:
        String dateOfBirth = (allResults.get(4).get("patient_dob")).toString();
        Etc
        */

        // Say we have a given phone number, and we want to retrieve data for a staff member:
        MySQL.getStaff("01234567810");
        System.out.println("STAFF DETAILS");
        System.out.println("ID: " + MySQL.staffID);
        System.out.println("First name: " + MySQL.staffFirstName);
        System.out.println("Surname: " + MySQL.staffSurname);
        System.out.println("Role: " + MySQL.staffRole);
        System.out.println("Phone: " + MySQL.staffPhone);
        System.out.println("Address ID: " + MySQL.staffAddressID);

        MySQL.getAddress(MySQL.staffAddressID);
        System.out.println(MySQL.staffFirstName.toUpperCase() + " " + MySQL.staffSurname.toUpperCase() + "'s ADDRESS");
        System.out.println(MySQL.houseNumber);
        System.out.println(MySQL.street);
        System.out.println(MySQL.city);
        System.out.println(MySQL.postcode);

        // List treatments example
        for (int count = 1; count < 15; count++) {
            MySQL.getTreatment(count);
            System.out.println("Treatment " + MySQL.treatmentID + " is " + MySQL.treatmentName + " and lasts " + MySQL.treatmentDuration + " mins.");
        }

        // Prices example
        MySQL.getPrice(1);
        System.out.println("Band 1 is £" + MySQL.price);
        MySQL.getPrice(2);
        System.out.println("Band 2 is £" + MySQL.price);
        MySQL.getPrice(3);
        System.out.println("Band 3 is £" + MySQL.price);

        // Appointment details example
        ArrayList<HashMap<String,Object>> allAppointments = MySQL.getResults("SELECT * FROM appointments");
        for (int count = 0; count < allAppointments.size(); count ++) {
            HashMap<String,Object> singleAppointment = allAppointments.get(count);
            // Just printed the whole Hash map because I couldn't be asked.
            // Single columns can be accessed as shown at the top
            System.out.println(singleAppointment);
        }
    }
}
