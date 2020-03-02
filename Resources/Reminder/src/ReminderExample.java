import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class ReminderExample extends TimerTask {
    private final static long ONCE_PER_DAY = 1000*60*60*24;

    //private final static int ONE_DAY = 1;
    private final static int Hours = 15;
    private final static int Minutes = 50;



    @Override
    public void run() {
        long currentTime = System.currentTimeMillis();
        long stopTime = currentTime + 1000;
        while(stopTime != System.currentTimeMillis()){

            System.out.println("Start");
            System.out.println("End");
        }
    }
    private static Date getdate(){

        Date reminder = new java.util.Date();
        reminder.setHours(Hours);
        reminder.setMinutes(Minutes);



        return reminder;
    }
    //call this method from your servlet init method
    public static void startTask(){
        ReminderExample task = new ReminderExample();
        Timer timer = new Timer();
        timer.schedule(task, getdate(),1000*10);
    }
    public static void main(String args[]){
        startTask();

    }

}
