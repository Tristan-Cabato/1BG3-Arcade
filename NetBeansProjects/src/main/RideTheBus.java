// Final

import java.util.*;

public class RideTheBus extends Game {
    Scanner scanner = new Scanner(System.in);
    Random random = new Random();

    private void losePause() {
        System.out.println("\nPress Enter to return to the game menu...");
        scanner.nextLine();
    }

    public RideTheBus(Account player) {
        super(player);
        this.name = "Ride the Bus";
    }

    @Override
    public void play() {
        int[] values = new int[4]; // Card values
        String[] cardSuits = new String[4]; // Card suits
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String guess;
        Boolean correct;

        // Generating Cards
        for (int i=0; i<4; i++) {
            values[i] = random.nextInt(13)+1; 
            cardSuits[i] = suits[random.nextInt(suits.length)];   
        }

        //ROUND 1
        System.out.println("Is the first card red or black? (r/b): ");
        guess = scanner.nextLine();
        String color = (cardSuits[0].equals("Hearts") || cardSuits[0].equals("Diamonds")) ? "r" : "b";

        if(!guess.equalsIgnoreCase(color)) { 
            System.out.printf("Wrong! The card was %s_%d\nBetter luck next time!\n", cardSuits[0], values[0]);
            logGameResult(player, name, false, 0);
            losePause();
            return;
        } else {
            System.out.println("Correct! The card was " + cardSuits[0] + values[0]);
        }

        //ROUND 2
        System.out.print("Is the second card higher or lower than the previous card? (h/l): ");
        guess = scanner.nextLine();

        if (!(guess.equalsIgnoreCase("h") && values[1] > values[0]) && !(guess.equalsIgnoreCase("l") && values[1] < values[0])) { 
            System.out.printf("Wrong! The card was %s_%d\nBetter luck next time!\n", cardSuits[1], values[1]);
            logGameResult(player, name, false, 0);
            losePause();
            return;
        } else {
            System.out.println("Correct! The card was " + cardSuits[1] + values[1]);
        }

        //ROUND 3
        System.out.printf("Will the third card be inside or outside the range of %d to %d? (i/o): ", Math.min(values[0], values[1]), 
        Math.max(values[0], values[1]));
        guess = scanner.nextLine();
        
        correct = guess.equalsIgnoreCase("i") 
        ? (values[2] >= Math.min(values[0], values[1]) && values[2] <= Math.max(values[0], values[1]))
        : (guess.equalsIgnoreCase("o") && (values[2] < Math.min(values[0], values[1]) || values[2] > Math.max(values[0], values[1])));
        
        if (!correct) {
            System.out.printf("Wrong! The card was %s_%d\nBetter luck next time!\n", cardSuits[2], values[2]);
            logGameResult(player, name, false, 0);
            losePause();
            return;
        } else {
            System.out.println("Correct! The card was " + cardSuits[2] + values[2]);
        }

        //ROUND 4
        System.out.print("Guess the final card's suit (Hearts, Diamonds, Clubs, Spades): ");
        guess = scanner.nextLine();

        if (!guess.equalsIgnoreCase(cardSuits[3])) {
            System.out.printf("Wrong! The suit was %s\nBetter luck next time!\n", cardSuits[3]);
            logGameResult(player, name, false, 0);
            losePause();
        } else {
            System.out.println("Correct! The card was " + cardSuits[3] + values[3] + "\n\nCongratulations! You get to ride the bus!");
            reward(20);
            logGameResult(player, name, true, 20);
        }
        // Pause
        losePause();
        scanner.nextLine();
    }
} 

