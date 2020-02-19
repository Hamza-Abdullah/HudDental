package LeaveRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class LeaveRequestSQL {

    public static void makeLeaveRequest(String start, String end, int staff){
        rawQuery(
                "INSERT INTO holidays " +
                        "(holiday_start, holiday_end, holiday_staff, holiday_approved) " +
                        "VALUES ('" + start + "', '" + end + "', " + staff + ", " + 0 + ");"
        );
    }

    // Method to execute a MySQL query
    private static ArrayList<HashMap<String,Object>> rawQuery(String fullCommand) {
        Connection huddental = null;
        Statement stm = null;
        ResultSet result = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

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
