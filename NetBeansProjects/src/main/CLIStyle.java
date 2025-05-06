import java.util.Scanner;

public class CLIStyle {
    // Cross-platform terminal clear method
    public static void clearScreen() throws Exception {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }  
    }
    
    // Decorative Header
    public static void printHeader(String title) {
        int width = 50;
        String border = "=".repeat(width);
        System.out.println(border);
        System.out.printf("%" + ((width + title.length()) / 2) + "s%n", title);
        System.out.println(border);
    }
    
    public static void printSuccess(String message) {
        System.out.println("\033[32m" + message + "\033[0m");
    }

    public static void printError(String message) {
        System.out.println("\033[31m" + message + "\033[0m");
    }

    // Generic menu interface
    public static int showMenu(String title, String[] options) throws Exception {
        clearScreen();
        printHeader(title);
        
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%d. %s%n", i + 1, options[i]);
        }
        System.out.print("\nSelect an option: ");
        
        try (Scanner scanner = new Scanner(System.in)) {
            int choice = scanner.nextInt();
            
            // Validate choice
            if (choice < 1 || choice > options.length) {
                printError("Invalid option. Please try again.");
                return showMenu(title, options);
            }
            
            return choice;
        }
    }

    // Login/Register menu
    public static int showLoginMenu() throws Exception {
        String[] loginOptions = {
            "Login",
            "Register",
            "Exit"
        };
        
        return showMenu("Welcome to 1BG3 Arcade", loginOptions);
    }

    // Main game menu after login
    public static int showMainMenu(Account account) throws Exception {
        clearScreen();
        printHeader("Welcome, " + account.getUsername());
        
        String[] mainOptions = {
            "Play Games",
            "Top Up",
            "View Balance",
            "Game History",
            "Logout"
        };
        
        return showMenu("Main Menu", mainOptions);
    }
}