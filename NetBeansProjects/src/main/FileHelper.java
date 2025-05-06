import java.io.*;
import java.util.*;

public class FileHelper {
    // ================= To Add (I don't want to accidentally overwrite what's already here)
    // Base directory for the arcade project
    private static final String BASE_DIR = System.getProperty("user.home") + File.separator + "NetBeansProjects" + File.separator + "Arcade";
    private static final String DATA_DIR = BASE_DIR + File.separator + "data";
    private static final String USERS_DIR = DATA_DIR + File.separator + "users";

    // Update master accounts list
    public static void updateAccountsList(List<String> usernames) {
        File accountsFile = new File(DATA_DIR, "accounts.txt");
        try (PrintWriter writer = new PrintWriter(new FileWriter(accountsFile))) {
            for (String username : usernames) {
                writer.println(username);
            }
        } catch (IOException e) {
            System.err.println("Error updating accounts list: " + e.getMessage());
            // Optionally, log the exception or handle it differently
        }
    }

    // Save user info
    public static void saveUserInfo(Account account) {
        // Ensure users directory exists
        File usersDir = new File(USERS_DIR);
        usersDir.mkdirs();

        // Prepare file paths
        String username = account.getUsername();
        String infoPath = new File(usersDir, username + "_info.txt").getPath();
        String transactionsPath = new File(usersDir, username + "_transactions.txt").getPath();
        String gameHistoryPath = new File(usersDir, username + "_gamehistory.txt").getPath();

        // Write user info
        writeFile(infoPath, 
            "Balance: " + account.getBalance() + "\n" +
            "Tokens: " + account.getTokens() + "\n" +
            "Tickets: " + account.getTickets()
        );

        // Initial headers
        writeFile(transactionsPath, 
            "======================= Transactions ======================="
        );
        writeFile(gameHistoryPath, 
            "======================= Game History ======================="
        );
    }

// -------------- Essentially replaces appendLine ------------------------------- //
    // Save game history
    public static void saveGameHistory(Account account, String gameLog) {
        File gameHistoryFile = new File(USERS_DIR, account.getUsername() + "_gamehistory.txt");
        try (PrintWriter writer = new PrintWriter(new FileWriter(gameHistoryFile, true))) {
            writer.println(gameLog);
        } catch (IOException e) {
            System.err.println("Error saving game history for " + account.getUsername() + ": " + e.getMessage());
        }
    }

    // Save transactions
    public static void saveTransaction(Account account, String transaction) {
        File transactionsFile = new File(USERS_DIR, account.getUsername() + "_transactions.txt");
        try (PrintWriter writer = new PrintWriter(new FileWriter(transactionsFile, true))) {
            writer.println(transaction);
        } catch (IOException e) {
            System.err.println("Error saving transaction for " + account.getUsername() + ": " + e.getMessage());
        }
    }
// ----------------------------------------------------------------------------- //

// ----------- Mostly used in LoginSystem, there's probably a better solution -----------
    // Check if username exists
    public static boolean usernameExists(String username) {
        File accountsFile = new File(DATA_DIR, "accounts.txt");
        try (Scanner scanner = new Scanner(accountsFile)) {
            while (scanner.hasNextLine()) {
                if (scanner.nextLine().trim().equals(username)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Accounts file not found while checking username: " + e.getMessage());
            // Assuming if file not found, username cannot exist in it
        }
        return false;
    }

    // Get all usernames
    public static List<String> getAllUsernames() {
        List<String> usernames = new ArrayList<>();
        File accountsFile = new File(DATA_DIR, "accounts.txt");
        try (Scanner scanner = new Scanner(accountsFile)) {
            while (scanner.hasNextLine()) {
                usernames.add(scanner.nextLine().trim());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Accounts file not found while getting usernames: " + e.getMessage());
            // Return empty list if file cannot be read
        }
        return usernames;
    }

    // Read user file and create an Account object
    public static Account readUserFile(String username) {
        File infoFile = new File(USERS_DIR, username + "_info.txt");
        Account account = new Account(username, ""); // Create a default account

        try (Scanner scanner = new Scanner(infoFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                try {
                    if (line.startsWith("Balance: ")) {
                        account.addBalance(Double.parseDouble(line.split(": ")[1]));
                    } else if (line.startsWith("Tokens: ")) {
                        account.addTokens(Integer.parseInt(line.split(": ")[1]));
                    } else if (line.startsWith("Tickets: ")) {
                        // Assuming Account class has setTickets or addTickets
                        // account.addTickets(Integer.parseInt(line.split(": ")[1])); 
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Skipping malformed line in " + infoFile.getName() + ": '" + line + "'");
                }
            }
            return account; // Return the populated account if file read successfully
        } catch (FileNotFoundException e) {
            System.err.println("Info file not found for user '" + username + "': " + e.getMessage());
            return null; // Indicate user file could not be read
        }
    }
// ----------------------------------------------------------------------------- //
 // ================================================================ //

    // Read lines from a file into a List
    public static List<String> readFile(String path) {
        List<String> lines = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(path))) {
            while (sc.hasNextLine()) {
                lines.add(sc.nextLine());
            }
        } catch (IOException e) {
            System.out.println("File not found: " + path);
        }
        return lines;
    }

    // Write (overwrite) lines to a file
    public static void writeFile(String path, List<String> lines) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            for (String line : lines) {
                pw.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + path);
        }
    }

    // Write (overwrite) a single string to a file
    public static void writeFile(String path, String content) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            pw.println(content);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + path);
        }
    }

    // Append a single line to a file
    public static void appendLine(String path, String line) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path, true))) {
            pw.println(line);
        } catch (IOException e) {
            System.out.println("Error appending to file: " + path);
        }
    }
}
