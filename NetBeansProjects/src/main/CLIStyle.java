import java.util.*;

public class CLIStyle {
    
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    // Decoration
    public static void printHeader(String title) {
        int width = 50;
        String border = "=".repeat(width);
        System.out.println(border);
        System.out.printf("%" + ((width + title.length()) / 2) + "s%n", title); // Padding. In technical terms, it ensures the string occupies a certain number of characters.
        System.out.println(border);
    }
    
    public static void printSuccess(String message) {
        System.out.println("\033[32m" + message + "\033[0m");
    }

    public static void printError(String message) {
        System.out.println("\033[31m" + message + "\033[0m");
    }

    // Generic menu interface
    public static int showMenu(String title, String[] options, Scanner scanner) { 
        int choice = -1; 

        while (true) { 
            clearScreen();
            printHeader(title);
            
            // Display menu options
            for (int i = 0; i < options.length; i++) {
                System.out.printf("%d. %s%n", i + 1, options[i]);
            }
            System.out.print("\nSelect an option: ");

            try {
                choice = scanner.nextInt(); 
                scanner.nextLine(); // Consume the newline left-over
                
                if (choice >= 1 && choice <= options.length) {
                    break; // Valid choice, exit the loop
                } else {
                    // Choice is a number but outside the allowed range
                    printError("Invalid option number. Please choose between 1 and " + options.length + ".");
                    // Pause briefly to let user see the error
                    try { Thread.sleep(1500); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
                }
            } catch (InputMismatchException e) { // Choice is not an integer
                printError("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
                // Pause briefly to let user see the error
                try { Thread.sleep(1500); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
            } 
        }
        return choice;
    }

 // Main game menu after login
 public static int showMainMenu(Account account, Scanner scanner) { 
        clearScreen();
        printHeader("Welcome, " + account.getUsername()); 
        
        String[] mainOptions = {
            "Play Games",
            "Top Up / Exchange",
            "Logout"
        };
        return showMenu("Main Menu", mainOptions, scanner); 
    } 
}

