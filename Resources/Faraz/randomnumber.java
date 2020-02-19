package GUI;

import java.util.Random;

public class randomnumber {
    public static void main (String [] args) {
        Random dice = new Random();
        int number;

        for (int counter=400; counter<=5000;counter++){
            number = dice.nextInt (100) ;
            System.out.println(number + "  ");

        }


    }


}
