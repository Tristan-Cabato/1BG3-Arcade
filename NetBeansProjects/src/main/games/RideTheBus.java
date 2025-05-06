// Proposed

import java.io.*; // This includes Scanner and Random
import java.util.*;

public class RideTheBus extends Game {
    Scanner scanner = new Scanner(System.in);
    Random random = new Random();

    public RideTheBus(Account player) {
        super(player);
        this.name = "Ride the Bus";
    }

    @Override
    public void play() throws IOException {
        int[] values = new int[4]; // Stores card values
        String[] cardSuits = new String[4]; // Stores card suit
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String guess;
        Boolean correct;

        // Generating the 4 Cards
        for (int i=0; i<4; i++) {
            values[i] = random.nextInt(13)+1; // Normally starts from 0-12, +1 makes it 1-13
            cardSuits[i] = suits[random.nextInt(suits.length)];   
        }
        /*
        - assigns random number 1-13 to values[i] array element (e.g. 1=ace, 11=jack, 12=queen,)
        - assigns random suit(♥,♦,♠,♣) to cardsSuits[i] element, by randomly selecting an index from 'suits' array.
        */

        //ROUND 1 (first card - red or black)
        System.out.println("Is the first card red or black? (r/b): ");
        guess = scanner.nextLine();
        String color;

        if (cardSuits[0].equals("Hearts") || cardSuits[0].equals("Diamonds")) {
            color = "r";
        } else {
            color = "b";
        } // Diamond and Hearts are always red in a standard deck

        /* ============================================================================= 
        This can be done to shorten:
            String color = (cardSuits[0].equals("Hearts") || cardSuits[0].equals("Diamonds")) ? "r" : "b";

	 b is the assigned !color or in this case !guess = color
        ====================================================================== */ 

        if(!guess.equalsIgnoreCase(color)) { 
            System.out.printf("Wrong! The card was %s_%d\nBetter luck next time!\n", cardSuits[0], values[0]);
            logGameResult(name, false, 0);
            return;
        } else {
            System.out.println("Correct! The card was " + cardSuits[0] + values[0]);
        }

        //ROUND 2 (second card - higher/lower than previous card's number)
        System.out.print("Is the second card higher or lower than the previous card? (h/l): ");
        guess = scanner.nextLine();

        correct = (guess.equalsIgnoreCase("h") && values[1] > values[0]) || (guess.equalsIgnoreCase("l") && values[1] < values[0]);
        /* Boolean is:
                True if: guess is "h" and card 2 is higher than card 1 or (not and):
                    if guess is "l" and card 2 is lower than card 1
                False if: anything else
	The "correct" variable can be reused since separate rounds aren't tracked and so is "guess"
         */

        if (!correct) {
            System.out.printf("Wrong! The card was %s_%d\nBetter luck next time!\n", cardSuits[1], values[1]);
            logGameResult(name, false, 0);
            return;
        } else {
            System.out.println("Correct! The card was " + cardSuits[1] + values[1]);
        }

        /* ============================================================================= 
         Boolean variable can be removed but it is more cluttered to use:
            if (!(guess.equalsIgnoreCase("h") && values[1] > values[0]) && !(guess.equalsIgnoreCase("l") && values[1] < values[0])) { 
                System.out.printf("Wrong! The card was %s_%d\nBetter luck next time!\n", cardSuits[1], values[1]);
                logGameResult(false);
                return;
            }
        ====================================================================== */

        //ROUND 3 (third card - in or out of range of previous 2 cards)
        System.out.printf("Will the third card be inside or outside the range of %d to %d? (i/o): ", Math.min(values[0], values[1]), 
        Math.max(values[0], values[1]));
        // The min and max logic ensures the range is always correct regardless in the case where value 0 higher than value 1
        guess = scanner.nextLine();
        
        correct = guess.equalsIgnoreCase("i") 
        ? (values[2] >= Math.min(values[0], values[1]) && values[2] <= Math.max(values[0], values[1]))
        : (guess.equalsIgnoreCase("o") && (values[2] < Math.min(values[0], values[1]) || values[2] > Math.max(values[0], values[1])));
        /* Visual:
          Player guess "i" affects the boolean variable:
            True if: 2nd card >= lowestOf(card1, card2) and 2nd card <= highestOf(card1, card2) [Between or Equals]
            False if: 2nd card < lowestOf(card1, card2) or 2nd card > highestOf(card1, card2) [Outside]
            
            Essentially, it's the same as:
                if (guess.equals("i")) {
                    correct = values[2] >= Math.min(values[0], values[1]) && values[2] <= Math.max(values[0], values[1]);
                } else {
                    correct = guess.equals("o") && (values[2] < Math.min(values[0], values[1]) || values[2] > Math.max(values[0], values[1]);
                }
            I don't know if a case where player choice is wrong should be made. Made guess "o" as explicit else it would take in "anything but i" as correct
        */
        if (!correct) {
            System.out.printf("Wrong! The card was %s_%d\nBetter luck next time!\n", cardSuits[2], values[2]);
            logGameResult(name, false, 0);
            return;
        } else {
            System.out.println("Correct! The card was " + cardSuits[2] + values[2]);
        }

        //ROUND 4 (fourth card - guess the suit)
        System.out.print("Guess the final card's suit (Hearts, Diamonds, Clubs, Spades): ");
        guess = scanner.nextLine();

        if (!guess.equalsIgnoreCase(cardSuits[3])) {
            System.out.printf("Wrong! The suit was %s\nBetter luck next time!\n", cardSuits[3]);
            logGameResult(name, false, 0); // No need for return since it's the last line
        } else {
            System.out.println("Correct! The card was " + cardSuits[3] + values[3] + "\n\nCongratulations! You get to ride the bus!"); // reward method already displays a message
            reward(20);
            logGameResult(name, true, 20);
        }
    }
}   

// Program is built only to reward the player after guessing all 4 correct,
