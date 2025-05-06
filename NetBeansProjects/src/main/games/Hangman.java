import java.io.*;
import java.util.*;

public class Hangman extends Game {
    String[] words = {"java", "hangman", "programming", "challenge", "computer science", "project"};
    int lives = 7;
    Scanner scanner = new Scanner(System.in);

    public Hangman (Account player) {
        super(player);
        this.name = "Hangman";
    }

    @Override
    public void play() throws IOException {
        Random random = new Random();
        String randomizedWord = words[random.nextInt(words.length)].toUpperCase();
        
        // Use StringBuilder for the word being displayed (starts with underscores)
        StringBuilder wordDisplay = new StringBuilder();
        for (int i = 0; i < randomizedWord.length(); i++) {
            wordDisplay.append("_");
        }
        
        // Use a String to track guessed letters
        String guessedLetters = ""; 
        boolean guessedWord = false;

        System.out.println("Guess the word!");

        // --- Main Game Loop ---
        while (lives > 0 && !guessedWord) {

            // --- Display current state ---
            System.out.println("\nCurrent word: " + wordDisplay.toString().replace("", " ").trim());
            System.out.println("Lives remaining: " + lives);
            System.out.print("Guessed letters: ");
            
            for (char letter : guessedLetters.toCharArray()) {
                System.out.print(letter + " ");
            } // Print guessed letters with spaces
            System.out.println(); 
            System.out.print("Guess a letter: ");

            // --- Get and Validate Input ---
            String input = scanner.next().toUpperCase();
            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                System.out.println("Invalid input. Please enter a single letter.");
                continue; 
            }
            char userGuess = input.charAt(0);
            if (guessedLetters.indexOf(userGuess) != -1) {
                System.out.println("You already guessed that letter ('" + userGuess + "'). Try again.");
                continue;
            } guessedLetters += userGuess; 

            // --- Check if Guess is Correct and Update Displayed Word ---
            boolean foundLetter = false;
            for (int i = 0; i < randomizedWord.length(); i++) {
                if (randomizedWord.charAt(i) == userGuess) {
                    wordDisplay.setCharAt(i, userGuess); // Update StringBuilder at correct position
                    foundLetter = true;
                    // No break here, in case the letter appears multiple times
                }
            }

            // --- Process Guess Result ---
            if (foundLetter) {
                System.out.println("Good guess!");
                if (wordDisplay.toString().equals(randomizedWord)) guessedWord = true;
            } else {
                System.out.println("Incorrect guess.");
                lives--; 
            }
        }

        // --- Game Over Logic ---
        System.out.println("\n--- Game Over ---");
        if (guessedWord) {
            reward(40);
            logGameResult(name, true, 40);
        } else {
            System.out.println("You ran out of lives! The word was: " + randomizedWord);
            logGameResult(name, false, 0);
        }
    }
}
