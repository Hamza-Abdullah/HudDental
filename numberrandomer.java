import java.util.Random;
package sample;

public class numberrandomer {
    public static void main (String [] args) {
        Random dice = new Random();
        int number;

        for (int counter=100; counter<=400;counter++){
            number = dice.nextInt (8) ;
            Systemout.out.println(number + "  ");

        }


    }


}
