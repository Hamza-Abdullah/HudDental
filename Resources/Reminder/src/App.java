

public class App {

    public static void main(String[] args) throws Exception {

        System.out.println("Start");

        Reminder reminder = new Reminder(2);

        reminder.start();
        // add some delay
        Thread.sleep(13000);
        reminder.stop();

        System.out.println("End");
    }

}
