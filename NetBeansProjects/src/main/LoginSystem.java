import java.io.*;
import java.util.*;

public class LoginSystem extends Account {
    Scanner scan = new Scanner(System.in);    

    public void AccountCreation() throws FileNotFoundException, IOException, Exception {
        System.out.println("""
                [Account Creation]
                [1] Login
                [2] Register
                """);
        int choice = scan.nextInt();
        
        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
        }
    }

    public void login() throws FileNotFoundException, Exception {
        System.out.print("Username: ");
        String username = scan.next();
        System.out.print("Password: ");
        String password = scan.next();

        if(!FileHelper.usernameExists(username)) {
            System.out.println("Username does not exist");
            login();
        } else {
            Account account = FileHelper.readUserFile(username);
            if(account.checkPassword(password)) {
                System.out.println("Login successful");
                CLIStyle.showMainMenu(account);
            } else {
                System.out.println("Incorrect password");
                login();
            }
        }
    }

    public void register() throws IOException, Exception {
        System.out.print("Username: ");
        String username = scan.next();
        System.out.print("Password: ");
        String password = scan.next();
        System.out.print("\n Confirm Password:");
        String passwordConfirmation = scan.next();

        // Temporary Solution, I don't get it - refer to FileHelper
        if (passwordConfirmation.equalsIgnoreCase(password)) { 
            Account newAccount = new Account(username, password);
            FileHelper.saveUserInfo(newAccount);
            // Get existing usernames and add the new one
            List<String> usernames = FileHelper.getAllUsernames();
            usernames.add(username);
            FileHelper.updateAccountsList(usernames);
        } else {
            System.out.println("Passwords do not match, please try again");
            register();
        }
        System.out.println("Would you like to login now? [Y/N]");
        String choice = scan.next();
        if (choice.equalsIgnoreCase("Y")) {
            CLIStyle.showLoginMenu();
        } else {
            AccountCreation();
        }
    }
}