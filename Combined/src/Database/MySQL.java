package Database;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class for getting data from a MySQL Database
 *
 * @author Hamza Abdullah
 * @version January 2020
 *
 */

public class MySQL {

    public MySQL() {
    }

    // Helpful methods

    // To get a single address
    public static int addressID;
    public static String houseNumber, street, city, postcode;
    public static void getAddress(int givenAddressID) {
        ArrayList<HashMap<String, Object>> addressResults = rawQuery(
                "SELECT * FROM addresses " +
                        "WHERE address_id = " + givenAddressID + ";"
        );
        HashMap address = addressResults.get(0);
        MySQL.addressID = (int) address.get("address_id");
        MySQL.houseNumber = address.get("address_number").toString();
        MySQL.street = address.get("address_street").toString();
        MySQL.city = address.get("address_city").toString();
        MySQL.postcode = address.get("address_postcode").toString();
    }

    // To get a sing patient's details by phone number and date of birth
    public static String patientFirstName, patientSurname, patientDOB, patientPhone;
    public static int patientID, patientAddressID;
    public static void getPatient(String phoneNum, String dateOfBirth) {
        ArrayList<HashMap<String, Object>> patientResults = rawQuery(
                "SELECT * FROM patients " +
                        "WHERE patient_phone = " +
                        "'" + phoneNum + "' " +
                        "AND patient_dob = " +
                        "'" + dateOfBirth + "';"
        );

        if (patientResults.size() == 0) {
            MySQL.patientID = -1;
            return;
        }

        HashMap patient = patientResults.get(0);
        MySQL.patientID = (int) patient.get("patient_id");
        MySQL.patientFirstName = patient.get("patient_firstname").toString();
        MySQL.patientSurname = patient.get("patient_surname").toString();
        MySQL.patientDOB = patient.get("patient_dob").toString();
        MySQL.patientPhone = patient.get("patient_phone").toString();
        MySQL.patientAddressID = (int) patient.get("patient_address");
    }

    // To get a sing patient's details
    public static void getPatientByID(int givenID) {
        ArrayList<HashMap<String, Object>> patientResults = rawQuery(
                "SELECT * FROM patients " +
                        "WHERE patient_id = " + givenID + ";"
        );

        if (patientResults.size() == 0) {
            MySQL.patientID = -1;
            return;
        }

        HashMap patient = patientResults.get(0);
        MySQL.patientID = (int) patient.get("patient_id");
        MySQL.patientFirstName = patient.get("patient_firstname").toString();
        MySQL.patientSurname = patient.get("patient_surname").toString();
        MySQL.patientDOB = patient.get("patient_dob").toString();
        MySQL.patientPhone = patient.get("patient_phone").toString();
        MySQL.patientAddressID = (int) patient.get("patient_address");
    }

    // To get a sing patient's details
    public static void getPatientByPhone(String givenPhone) {
        ArrayList<HashMap<String, Object>> patientResults = rawQuery(
                "SELECT * FROM patients " +
                        "WHERE patient_phone = " + givenPhone + ";"
        );

        if (patientResults.size() == 0) {
            MySQL.patientID = -1;
            return;
        }

        HashMap patient = patientResults.get(0);
        MySQL.patientID = (int) patient.get("patient_id");
        MySQL.patientFirstName = patient.get("patient_firstname").toString();
        MySQL.patientSurname = patient.get("patient_surname").toString();
        MySQL.patientDOB = patient.get("patient_dob").toString();
        MySQL.patientPhone = patient.get("patient_phone").toString();
        MySQL.patientAddressID = (int) patient.get("patient_address");
    }

    // To get a single staff's details by phone number
    public static int staffID, staffAddressID;
    public static String staffFirstName, staffSurname, staffRole, staffPhone;
    public static void getStaff(String phoneNum) {
        ArrayList<HashMap<String, Object>> staffResults = rawQuery(
                "SELECT * FROM staff " +
                        "WHERE staff_phone = " +
                        "'" + phoneNum + "';"
        );
        HashMap staff = staffResults.get(0);
        MySQL.staffID = (int) staff.get("staff_id");
        MySQL.staffFirstName = staff.get("staff_firstname").toString();
        MySQL.staffSurname = staff.get("staff_surname").toString();
        MySQL.staffRole = staff.get("staff_role").toString();
        MySQL.staffPhone = staff.get("staff_phone").toString();
        MySQL.staffAddressID = (int) staff.get("staff_address");
    }

    // To get a single staff's details by phone number
    public static void getStaffByID(int staffID) {
        ArrayList<HashMap<String, Object>> staffResults = rawQuery(
                "SELECT * FROM staff " +
                        "WHERE staff_id = " +
                        "" + staffID + ";"
        );
        HashMap staff = staffResults.get(0);
        MySQL.staffID = (int) staff.get("staff_id");
        MySQL.staffFirstName = staff.get("staff_firstname").toString();
        MySQL.staffSurname = staff.get("staff_surname").toString();
        MySQL.staffRole = staff.get("staff_role").toString();
        MySQL.staffPhone = staff.get("staff_phone").toString();
        MySQL.staffAddressID = (int) staff.get("staff_address");
    }

    // To get a single treatment's details
    public static int treatmentID, treatmentDuration;
    public static String treatmentName, treatmentBand;
    public static void getTreatment(int givenTreatmentID) {
        ArrayList<HashMap<String, Object>> treatmentResults = rawQuery(
                "SELECT * FROM treatments " +
                        "WHERE treatment_id = " + givenTreatmentID + ";"
        );
        HashMap treatment = treatmentResults.get(0);
        MySQL.treatmentID = (int) treatment.get("treatment_id");
        MySQL.treatmentName = treatment.get("treatment_name").toString();
        MySQL.treatmentBand = treatment.get("treatment_band").toString();
        MySQL.treatmentDuration = (int) treatment.get("treatment_duration");
    }

    // To get a single price band
    public static int priceBand;
    public static BigDecimal price;
    public static void getPrice(int givenPriceBand) {
        ArrayList<HashMap<String, Object>> pricesResults = rawQuery(
                "SELECT * FROM prices " +
                        "WHERE price_band = " + givenPriceBand + ";"
        );
        HashMap prices = pricesResults.get(0);
        MySQL.priceBand = (int) prices.get("price_band");
        MySQL.price = (BigDecimal) prices.get("price");
    }

    // To get a single room
    public static int roomID;
    public static String roomFacilities;
    public static void getRoom(int givenRoomID) {
        ArrayList<HashMap<String, Object>> roomResults = rawQuery(
                "SELECT * FROM rooms " +
                        "WHERE room_id = " + givenRoomID + ";"
        );
        HashMap room = roomResults.get(0);
        MySQL.roomID = (int) room.get("room_id");
        MySQL.roomFacilities = room.get("room_facilities").toString();
    }

    // For login
    public static String getPasswordHash(String phoneNumber) {
        return getResults("SELECT * FROM users WHERE user_phone = '" + phoneNumber + "';").get(0).get("user_passwordHash").toString();
    }

    public static boolean isStaff;
    public static boolean isRegistered(String phoneNumber) {
        MySQL.isStaff = false;
        ArrayList<HashMap<String, Object>> getUsers = getResults("SELECT * FROM users WHERE user_phone = '" + phoneNumber + "';");
        if (getUsers.isEmpty()) {
            return false;
        } else {
            if ((Boolean) getUsers.get(0).get("user_isStaff")) {MySQL.isStaff = true;}
            return true;
        }
    }

    // For ordinary staff: returns requested holidays for an ordinary staff member
    public static ArrayList<HashMap<String, Object>> getStaffHolidays(int givenStaffID) {
        ArrayList<HashMap<String, Object>> getStaffHolidaysResults = rawQuery(
                "SELECT * FROM holidays " +
                        "WHERE holiday_staff = " + givenStaffID + ";"
        );
        return getStaffHolidaysResults;
    }

    // For manager: returns all requested holidays
    public static ArrayList<HashMap<String, Object>> getAllHolidays(int givenStaffID) {
        ArrayList<HashMap<String, Object>> getAllHolidaysResults = rawQuery(
                "SELECT * FROM holidays;"
        );
        return getAllHolidaysResults;
    }

    // For patient: returns all requested appointments
    public static ArrayList<HashMap<String, Object>> getAppointments(int givenPatientID) {
        ArrayList<HashMap<String, Object>> getAllAppointmentResults = rawQuery(
                "SELECT * FROM appointments " +
                        "WHERE appointment_patient = " + givenPatientID + ";"
        );
        return getAllAppointmentResults;
    }

    // For patient: returns the name of a treatment
    public static String getTreatmentName(int givenTreatmentID){
        ArrayList<HashMap<String, Object>> getAllTreatmentResults = rawQuery(
                "SELECT * FROM treatments " +
                        "WHERE treatment_id = " + givenTreatmentID + ";"
        );
        try{
            return (String) getAllTreatmentResults.get(0).get("treatment_name");
        }catch (NullPointerException e){
            return "Treatment Not Found";
        }
    }

    // For patient: returns the name of a dentist
    public static String getDentistName(int givenDentistID){
        ArrayList<HashMap<String, Object>> getAllDentistResults = rawQuery(
                "SELECT * FROM staff " +
                        "WHERE staff_id = " + givenDentistID + ";"
        );
        try{
            String nameString = ((String) getAllDentistResults.get(0).get("staff_firstname")) + " " +
                    ((String) getAllDentistResults.get(0).get("staff_surname"));
            return nameString;
        }catch (NullPointerException e){
            return "Dentist Not Found";
        }
    }

    // For patient: returns the name of a room
    public static String getRoomName(int givenRoomID){
        ArrayList<HashMap<String, Object>> getAllRoomResults = rawQuery(
                "SELECT * FROM rooms " +
                        "WHERE room_id = " + givenRoomID + ";"
        );
        try{
            return (String) getAllRoomResults.get(0).get("room_id");
        }catch (NullPointerException e){
            return "Room Not Found";
        }
    }

    // For patient: returns true if checked into appointment
    public static boolean isCheckedIn(int givenAppointmentID){
        ArrayList<HashMap<String, Object>> getAllAppointmentResults = rawQuery(
                "SELECT * FROM appointments " +
                        "WHERE appointment_id = " + givenAppointmentID + ";"
        );
        try{
            return ((String) getAllAppointmentResults.get(0).get("appointment_checkedin")).equals("1");

        }catch (NullPointerException e){
            return false;
        }
    }

    // For patient: updates appointment_checkedin to 1
    public static void checkIn(int givenAppointmentID){
        rawQuery("UPDATE appointments " +
                "SET appointment_checkedin = 1 " +
                "WHERE appointment_id = " + givenAppointmentID + ";");
    }

    // For patient: removes appointment from table
    public static void cancelAppointment(int givenAppointmentID){
        rawQuery("DELETE FROM appointments " +
                "WHERE appointment_id = " + givenAppointmentID + ";");
    }

    // For patient: returns a single patients notifications
    public static ArrayList<HashMap<String, Object>> getPatientNotifications(int givenPatientID){
        ArrayList<HashMap<String, Object>> getAllNotificationResults = rawQuery(
                "SELECT * FROM notifications " +
                        "WHERE notification_patient = " + givenPatientID + ";"
        );
        return getAllNotificationResults;
    }

    //For patient: creates an appointment booked notification for a patient
    public static void createBookingNotification(int patientID, String description, String dateTime){
        rawQuery(
                "INSERT INTO notifications " +
                        "(notification_patient, notification_description, notification_dateTime) " +
                        "VALUES ('" + patientID + "', '" + description + "', '" + dateTime + "');"
        );
    }

    // Manager methods
    public static void approve (int givenHolidayID){
        rawQuery("UPDATE holidays " +
                "SET holiday_approved = 1 " +
                "WHERE holiday_id = " + givenHolidayID + ";");
    }

    public static void deny (int givenHolidayID){
        rawQuery("UPDATE holidays " +
                "SET holiday_approved = -1 " +
                "WHERE holiday_id = " + givenHolidayID + ";");
    }

    // To retrieve reason by reason id
    public static String reasonName;
    public static void getReasonName(int reasonid) {
        ArrayList<HashMap<String, Object>> staffResults = rawQuery(
                "SELECT * FROM holidayReasons WHERE holidayReasons_id = " + reasonid);
        MySQL.reasonName = (String) staffResults.get(0).get("holidayReasons_description");
    }

    public static void makeLeaveRequest(Date start, Date end, int staff, int reason){
        rawQuery(
                "INSERT INTO holidays " +
                        "(holiday_start, holiday_end, holiday_staff, holiday_approved, holiday_reason) " +
                        "VALUES ('" + start + "', '" + end + "', " + staff + ", " + 0 + ", "+ reason +");"
        );
    }

    // Manual methods
    public static ArrayList<HashMap<String,Object>> getResults(String query) {
        ArrayList<HashMap<String, Object>> results = rawQuery(query);
        return results;
    }

    public static void newAddress(String number, String street, String city, String postcode) {
        rawQuery(
                "INSERT INTO addresses " +
                        "(address_number, address_street, address_city, address_postcode) " +
                        "VALUES ('" + number + "', '" + street + "', '" + city + "', '" + postcode + "');"
        );
    }

    public static void newPatient(String firstname, String surname, String dob, String phone, int address) {
        rawQuery(
                "INSERT INTO patients " +
                        "(patient_firstname, patient_surname, patient_dob, patient_phone, patient_address) " +
                        "VALUES ('" + firstname + "', '" + surname + "', '" + dob + "', '" + phone + "', " + address + ");"
        );
    }

    public static void newAppointment(int patient, int dentist, int nurse, String notes, String date, String checkedIn, int room, int treatment, String time) {
        rawQuery(
                "INSERT INTO appointments " +
                        "(appointment_patient, appointment_dentist, appointment_nurse, appointment_notes, appointment_date, appointment_checkedin, appointment_room, appointment_treatment, appointment_time) " +
                        "VALUES (" + patient + ", " + dentist + ", " + nurse + ", '" + notes + "', '" + date + "', '" + checkedIn + "', " + room + ", " + treatment + ", '" + time + "');"
        );
    }

    // Method to execute a MySQL query
    private static ArrayList<HashMap<String,Object>> rawQuery(String fullCommand) {
        Connection huddental = null;
        Statement stm = null;
        ResultSet result = null;
        try {
            huddental = DriverManager.getConnection("jdbc:mysql://huddental.cw4ubd44egzz.eu-west-2.rds.amazonaws.com:3306/HudDental", "admin", "T34mpr0ject");

            // Creates statement
            stm = huddental.createStatement();

            // Executes query
            boolean returningRows = stm.execute(fullCommand);
            if (returningRows)
                result = stm.getResultSet();
            else
                return new ArrayList<HashMap<String, Object>>();

            // Gets metadata
            ResultSetMetaData meta = null;
            meta = result.getMetaData();

            // Gets column names
            int colCount = meta.getColumnCount();
            ArrayList<String> cols = new ArrayList<String>();
            for (int index = 1; index <= colCount; index++)
                cols.add(meta.getColumnName(index));

            // Fetch rows
            ArrayList<HashMap<String, Object>> rows = new ArrayList<HashMap<String, Object>>();
            // Creates a hashmap and where the key is the column name and the value is the value of that column
            while (result.next()) {
                HashMap<String, Object> row = new HashMap<String, Object>();
                for (String colName : cols) {
                    Object val = result.getObject(colName);
                    row.put(colName, val);
                }
                rows.add(row);
            }

            // Close statement
            stm.close();

            // Pass back rows
            return rows;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return new ArrayList<HashMap<String, Object>>();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            return new ArrayList<HashMap<String, Object>>();
        } finally {
            try {
                if (result != null) {
                    result.close();
                }

                if (stm != null) {
                    stm.close();
                }

                if (huddental != null) {
                    huddental.close();
                }
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
                return new ArrayList<HashMap<String, Object>>();
            }
        }
    }
}
