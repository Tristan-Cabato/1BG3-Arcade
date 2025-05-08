import java.util.*;

public class LoginSystem extends Account {
    private final Scanner scan;
    int choice;

    public LoginSystem(Scanner scanner) {
        this.scan = scanner;
    }

    public Account AccountCreation() { // Expects an Account return type (LoginSystem methods are accounts too)
        while (true) {
            System.out.println("""
                    [Account Creation]
                    [1] Login
                    [2] Register
                    """);
            try {
                choice = scan.nextInt();
                scan.nextLine();

                switch (choice) {
                    case 1 -> {
                        return login(); 
                    } case 2 -> {
                        return register();
                    } default -> {
                        CLIStyle.printError("Invalid choice. Please enter 1 or 2.");
                        return AccountCreation();
                    }
                }
            } catch (InputMismatchException e) {
                CLIStyle.printError("Invalid input. Please enter 1 or 2.");
                scan.nextLine();
                return AccountCreation();
            }
        }
    }

    public Account login() { 
        System.out.print("Username: ");
        String username = scan.nextLine();
        String password = scan.nextLine();
	
        if (!FileHelper.usernameExists(username)) {
            System.out.println("Username does not exist. Please try again or register.");
            return null; 
        } else {
            Account account = FileHelper.readUserFile(username);
            if (account != null && account.checkPassword(password)) {
                System.out.println("Login successful");
                return account; // Return the valid account
            } else {
                System.out.println("Incorrect password. Please try again.");
                return null; 
            }
        }
    }

    public Account register() {
        String username;
        while (true) {
            System.out.print("Enter desired Username: ");
            username = scan.nextLine();
            if (FileHelper.usernameExists(username)) {
                CLIStyle.printError("Username already exists. Please choose another.");
            } else if (username.trim().isEmpty()){
                 CLIStyle.printError("Username cannot be empty.");
            } else {
                break; // Valid username
            }
        }
        
        String password;
        while(true) {
            System.out.print("Enter Password: ");
            password = scan.nextLine();
            System.out.print("Confirm Password: ");
            String passwordConfirmation = scan.nextLine();

            if (password.equals(passwordConfirmation)) { 
                if (password.trim().isEmpty()) {
                     CLIStyle.printError("Password cannot be empty.");
                     continue;
                }
                TopUpSystem newAccount = new TopUpSystem(username, password);
                FileHelper.saveUserInfo(newAccount);
                // Update the master list of usernames
                List<String> usernames = FileHelper.getAllUsernames();
                usernames.add(username);
                FileHelper.updateAccountsList(usernames);
                 CLIStyle.printSuccess("Registration successful!");
                break;
            } else {
                CLIStyle.printError("Passwords do not match, please try again.");
            }
        }

        System.out.println("\nWould you like to login now? [Y/N]");
        String otherChoice = scan.nextLine();
        if (otherChoice.equalsIgnoreCase("Y")) {
            return login(); 
        } else {
            return null; 
        }
    }
}
