import java.util.*;

public class RockPaperScissors extends Game {
    Scanner sc = new Scanner(System.in);
    String[] rps = {"Rock", "Paper", "Scissors"};
    String playerMove;

    public RockPaperScissors(Account player) {
        super(player);
        this.name = "Rock Paper Scissors";
    }

    @Override
    public void play() {
        String computerMove = rps[new Random().nextInt(rps.length)];
        System.out.printf("%s is starting the game!\n", player.getUsername());
        
        // Setting up Player Move
        while(true) {
            System.out.println("""
                Please enter your move
                ('r' = Rock, 'p' = Paper, 's' = Scissors)
                """);
            // Avoid leftover newline issues
            playerMove = sc.nextLine().trim(); 
            // Keeps running until player puts in a valid move
            if (playerMove.equalsIgnoreCase("r") || 
                playerMove.equalsIgnoreCase("p") || 
                playerMove.equalsIgnoreCase("s")) {
                break;
            } else {
                System.out.println(playerMove + " is not a valid move.");
            }
        }

        // Player setup
        if (playerMove.equalsIgnoreCase("r")) {
            playerMove = "Rock";
        } else if (playerMove.equalsIgnoreCase("p")) {
            playerMove = "Paper";
        } else if (playerMove.equalsIgnoreCase("s")) {
            playerMove = "Scissors";
        }
        System.out.println("You played: " + playerMove);
        System.out.println("Opponent played: " + computerMove);

        // Game Logic and Conditions
        if (playerMove.equalsIgnoreCase(computerMove)) {
            System.out.println("It's a tie! Get half the points");
            reward(5);
            logGameResult(player, name, true, 5);
        } else {
            boolean playerWins =
                (playerMove.equalsIgnoreCase("Rock") && computerMove.equalsIgnoreCase("Scissors")) ||
                (playerMove.equalsIgnoreCase("Paper") && computerMove.equalsIgnoreCase("Rock")) ||
                (playerMove.equalsIgnoreCase("Scissors") && computerMove.equalsIgnoreCase("Paper"));

            System.out.println(playerWins ? "You win!" : "You lose!");
            if (playerWins) {
                reward(10);
            }
            logGameResult(player, name, playerWins, playerWins ? 10 : 0);
        }

        // Pause
        System.out.println("\nPress Enter to return to the game menu...");
        sc.nextLine();
        sc.nextLine();
    }
}