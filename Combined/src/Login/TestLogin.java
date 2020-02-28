package Login;

import Database.MySQL;

public class TestLogin {

    public static void main(String[] args) {
        String passwordHash = BCrypt.hashpw("qwerty123", BCrypt.gensalt());
//        MySQL.getResults("INSERT INTO users (user_phone, user_isStaff, user_passwordHash) " +
//                "VALUES ('00000000000', 1, '" + passwordHash + "');");
        System.out.println(passwordHash);
//        System.out.println(MySQL.getPasswordHash("01234567890"));
    }
}
