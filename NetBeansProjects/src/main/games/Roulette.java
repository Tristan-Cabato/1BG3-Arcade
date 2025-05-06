// Proposed

import java.io.*;
import java.util.*;

public class Roulette extends Game {
    Scanner scanner = new Scanner(System.in);
    Random random = new Random();

    public Roulette(Account player) {
        super(player);
        this.name = "Roulette";
    }

    @Override
    public void play() throws IOException {
        System.out.println("Welcome to Roulette!");
        System.out.print("Place your bet (number between 0 and 36): ");
        int playerBet;

        // Input loop to ensure valid bet (0–36)
        while (true) {
            playerBet = scanner.nextInt();
            
            if (playerBet >= 0 && playerBet <= 36) {
                break; 
            } else {
                System.out.println("Invalid input. Please enter a number between 0 and 36:");
            }
        }

        int winningNumber = random.nextInt(37);
        System.out.println("The wheel spins... and lands on: " + winningNumber);

        if (playerBet == winningNumber) {
            reward(30);  // Already the winning message
            logGameResult(name, true, 30);
        } else {
            System.out.println("Sorry, you lost this round.");
            logGameResult(name, false, 0);
        }
        scanner.close();
    }
}
