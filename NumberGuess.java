import java.lang.Math;
import java.util.Scanner;

public class NumberGuess {
    public static void main(String[] args) {
        double rand = Math.random();
        double i = rand *100;
        int computer = (int) i ;
        Scanner sc = new Scanner(System.in) ;
        
        int score = 1;

        while(true){
            System.out.println("Guess the  number between (1-100) :- ");
            int guess= sc.nextInt();            
            if (guess > computer){
                System.out.println("Try something Less...");
                System.out.println(" ");
            }
            else if (guess == computer)
            {
                System.out.printf("You Guess Correctely !! You required Chance to Guess are :-  %d " , score);
                return ;
            }
            else {
                System.out.println("Try something Higher...");
                System.out.println(" ");
            }
            score++;
        }
    }
}
