package Login;

public class TestLogin {

    public static void main(String[] args) {
        String passwordHash = BCrypt.hashpw("qwerty123", BCrypt.gensalt());
//        MySQL.getResults("INSERT INTO users (user_phone, user_isStaff, user_passwordHash) " +
//                "VALUES ('01234567813', 1, '" + passwordHash + "');");
        System.out.println(passwordHash);
//        System.out.println(MySQL.getPasswordHash("01234567890"));
    }
}
