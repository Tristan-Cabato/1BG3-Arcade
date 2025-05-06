import java.io.*;
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
    public void play() throws IOException {
        String computerMove = rps[new Random().nextInt(rps.length)];
        System.out.printf("%s is starting the game!\n", player.getUsername());
        
        // Setting up Player Move
        while(true) {
            System.out.println("""
                Please enter your move
                ('r' = Rock, 'p' = Paper, 's' = Scissors)
                """);
            playerMove = sc.next();
            // Keeps running until player puts in a valid move
            if (playerMove.equalsIgnoreCase("r") || 
                playerMove.equalsIgnoreCase("p") || 
                playerMove.equalsIgnoreCase("s")) {
                break;
            } else {
                System.out.println(playerMove + " is not a valid move.");
            }
        } sc.close();

        // Setting up Computer Move
        System.out.println("Opponent played: " + computerMove);

        // Game Logic and Conditions
        if (playerMove.equalsIgnoreCase(computerMove)) {
            System.out.println("It's a tie!");
        } else {
            boolean playerWins = 
                (playerMove.equalsIgnoreCase("r") && computerMove.equalsIgnoreCase("s")) ||
                (playerMove.equalsIgnoreCase("p") && computerMove.equalsIgnoreCase("r")) ||
                (playerMove.equalsIgnoreCase("s") && computerMove.equalsIgnoreCase("p"));

            System.out.println(playerWins ? "You win!" : "You lose!");
            if (playerWins) {
                reward(10);
                new GameHistory().gameLog(name, true, 10);
            } else {
                new GameHistory().gameLog(name, false, 0);
            }
        }
    }
}
    
// On Hold:
/*
public void end() {
    System.out.println("Game over for " + playerAccount.getPlayerName());
} -- Could be used as a message on game exit but not here
*/
