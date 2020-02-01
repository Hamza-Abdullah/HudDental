import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

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
