import java.util.*;

public class Hangman extends Game {
    String[] words = {"java", "hangman", "programming", "challenge", "computer science", "project", "Tristan James Mendez Cabato", "Alejandro", "Samantha"};
    Scanner scanner = new Scanner(System.in);

    public Hangman(Account player) {
        super(player);
        this.name = "Hangman";
    }

    @Override
    public void play() {
        Random random = new Random();
        String randomizedWord = words[random.nextInt(words.length)].toUpperCase();

        StringBuilder wordDisplay = new StringBuilder();
        for (int i = 0; i < randomizedWord.length(); i++) {
            wordDisplay.append(randomizedWord.charAt(i) == ' ' ? ' ' : '_'); // Keep spaces visible
        }

        String guessedLetters = "";
        int lives = 7;

        System.out.println("Guess the word!");
        // Recursive turn-based play
        playTurn(randomizedWord, wordDisplay, guessedLetters, lives);
    }

    private void playTurn(String word, StringBuilder wordDisplay, String guessedLetters, int lives) {
        // Base Cases for Recursion End
        if (lives <= 0) {
            System.out.println("\n--- Game Over ---");
            System.out.println("You ran out of lives! The word was: " + word);
            logGameResult(player, name, false, 0);
            // Pause
            System.out.println("\nPress Enter to return to the game menu...");
            scanner.nextLine();
            return;
        }

        if (wordDisplay.toString().equals(word)) {
            System.out.println("\n--- Game Over ---");
            System.out.println("You guessed the word: " + word);
            reward(40);
            logGameResult(player, name, true, 40);
            // Pause
            System.out.println("\nPress Enter to return to the game menu...");
            scanner.nextLine();
            return;
        }

        // Display current state
        System.out.println("\nCurrent word: " + wordDisplay.toString().replace("", " ").trim());
        System.out.println("Lives remaining: " + lives);
        System.out.print("Guessed letters: ");
        // Print guessed letters safely
        if (guessedLetters != null && !guessedLetters.isEmpty()) {
             for (char letter : guessedLetters.toCharArray()) {
                System.out.print(letter + " ");
            }
        }
        System.out.println();

        // Input
        System.out.print("Guess a letter: ");
        String input = scanner.next().toUpperCase(); 
        scanner.nextLine();

        // Input Validation
        if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
            System.out.println("Invalid input. Please enter a single letter.");
            playTurn(word, wordDisplay, guessedLetters, lives);
            return;
        }

        char guess = input.charAt(0);
        if (guessedLetters.indexOf(guess) != -1) {
            System.out.println("You already guessed that letter ('" + guess + "'). Try again.");
            playTurn(word, wordDisplay, guessedLetters, lives);
            return;
        }

        // Update Guessed Letters and Check Guess
        String nextGuessedLetters = guessedLetters + guess;
        boolean found = false;
        StringBuilder nextWordDisplay = new StringBuilder(wordDisplay); // Work on a copy for the next turn

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess) {
                nextWordDisplay.setCharAt(i, guess);
                found = true;
            }
        }

        // Process Guess Result and Recurse
        int nextLives = lives; // Pass current lives or decremented lives
        if (found) {
            System.out.println("Good guess!");
        } else {
            System.out.println("Incorrect guess.");
            nextLives--; // Decrement lives for incorrect guess
        }
        playTurn(word, nextWordDisplay, nextGuessedLetters, nextLives);
    }
}