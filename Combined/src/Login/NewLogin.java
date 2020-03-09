package Login;

import Database.MySQL;

public class NewLogin {

    public static void updateLogin(String givenPassword, String newPhone, String oldPhone) {
        String passwordHash = BCrypt.hashpw(givenPassword, BCrypt.gensalt());
        MySQL.getResults("UPDATE users SET " +
                "user_phone = '" + newPhone + "', " +
                "user_PasswordHash = '" + passwordHash + "' WHERE " +
                "user_phone = '" + oldPhone + "' ;");
    }

    public static void createPatientLogin(String givenPassword, String givenPhone) {
        String passwordHash = BCrypt.hashpw(givenPassword, BCrypt.gensalt());
        MySQL.getResults("INSERT INTO users (user_phone, user_isStaff, user_passwordHash) " +
                "VALUES ('" + givenPhone + "', 0, '" + passwordHash + "');");
    }
}
