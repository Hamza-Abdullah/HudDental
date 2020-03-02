

import java.util.Timer;

public class Reminder {

    private Timer timer;
    private int seconds;

    public Reminder(int seconds) {
        timer = new Timer();
        this.seconds = seconds;
    }

    public void start() {
        timer.scheduleAtFixedRate(new ReminderTask(), 0, seconds * 1000);
    }

    public void stop() {
        timer.cancel();
    }
}
