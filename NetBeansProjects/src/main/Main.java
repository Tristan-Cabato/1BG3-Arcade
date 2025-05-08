import java.util.*;

public class Main {
    private static void showGameSelector(Account account, Scanner scanner) {
        String[] gameOptions = {
            "Hangman",
            "Ride The Bus", 
            "Rock Paper Scissors",
            "Roulette",
            "Back to Main Menu"
        };

        int choice;
        do {
            choice = CLIStyle.showMenu("Game Selection", gameOptions, scanner); 
            try {
                switch (choice) {
                    case 1 -> { 
                        Hangman hangmanGame = new Hangman(account);
                        hangmanGame.start();
                        FileHelper.saveUserInfo(account); 
                    } case 2 -> { 
                        RideTheBus rideTheBusGame = new RideTheBus(account); 
                        rideTheBusGame.start();
                        FileHelper.saveUserInfo(account);
                    } case 3 -> { 
                        RockPaperScissors rpsGame = new RockPaperScissors(account);
                        rpsGame.start();
                        FileHelper.saveUserInfo(account);
                    } case 4 -> { 
                        Roulette rouletteGame = new Roulette(account); 
                        rouletteGame.start(); 
                        FileHelper.saveUserInfo(account);
                    } case 5 -> System.out.println("Returning to main menu...");
                } // No default needed. Single line cases don't need opening bracket
            } catch (Exception e){
                CLIStyle.printError("Error launching game: " + e.getMessage());
            }
        } while (choice != gameOptions.length); // Loop until proper choice is made
    }

    public static void main(String[] args) {
        FileHelper.ensureDataDirectoriesExist();
        try (Scanner scanner = new Scanner(System.in)) { 
            LoginSystem loginSystem = new LoginSystem(scanner);
            Account currentAccount = loginSystem.AccountCreation(); 
            if (currentAccount != null) {
                int choice;
                do {
                    choice = CLIStyle.showMainMenu(currentAccount, scanner);
                    switch (choice) { 
                        case 1 -> {
                            showGameSelector(currentAccount, scanner); 
                        } case 2 -> { 
                            try {
                                ((TopUpSystem)currentAccount).TopUp();
                                FileHelper.saveUserInfo(currentAccount); 
                            } catch (ClassCastException cce) {
                                CLIStyle.printError("Account type mismatch during Top Up.");
                            } catch (Exception e) {
                                CLIStyle.printError("Error during Top Up/Exchange: " + e.getMessage());
                            }
                        } case 3 -> {
                            System.out.println("Logging out...");
                        }
                    }
                } while (choice != 3); // Repeat until proper choice is made
                System.out.println("Thank you for playing!");
            } else {
                System.out.println("Login failed or user chose not to log in. Exiting.");
            }
        }
    }
}


