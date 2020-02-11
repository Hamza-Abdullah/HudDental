package login;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginQueries {
    public static final int NUMBER_NOT_FOUND = 0;
    public static final int PATIENT_NUMBER = 1;
    public static final int STAFF_NUMBER = 2;


    public static int isRegistered(String phoneNum) {
        ArrayList<HashMap<String, Object>> patient = rawQuery(
                "SELECT * FROM patients " +
                        "WHERE patient_phone = '" + phoneNum + "';"
        );

        if (patient.size() != 0){ return PATIENT_NUMBER; }

        ArrayList<HashMap<String, Object>> staff = rawQuery(
                "SELECT * FROM staff " +
                        "WHERE staff_phone = '" + phoneNum + "';"
        );

        if (staff.size() != 0){ return STAFF_NUMBER; }

        return NUMBER_NOT_FOUND;
    }

    public static String getPassword(int numType, String phoneNum){
        if (numType == PATIENT_NUMBER){
            ArrayList<HashMap<String, Object>> patient = rawQuery(
                    "SELECT patient_password FROM patients " +
                            "WHERE patient_phone = '" + phoneNum + "';"
            );
            return (String) patient.get(0).get("patient_password");
        }
        else {
            ArrayList<HashMap<String, Object>> staff = rawQuery(
                    "SELECT staff_password FROM staff " +
                            "WHERE staff_phone = '" + phoneNum + "';"
            );
            return (String) staff.get(0).get("staff_password");
        }
    }

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
