import java.io.*;
import java.util.*;

public class FileHelper {
    private static final String BASE_DIR = System.getProperty("user.home") + File.separator + "NetBeansProjects" + File.separator + "Arcade";
    private static final String DATA_DIR = BASE_DIR + File.separator + "data";
    public static final String USERS_DIR = DATA_DIR + File.separator + "users";

    public static void ensureDataDirectoriesExist() {
        File dataDir = new File(DATA_DIR);
        File usersDir = new File(USERS_DIR);

        if (!dataDir.exists()) {
            System.out.println("Creating data directory: " + DATA_DIR);
            try {
                if (!dataDir.mkdirs()) {
                    System.err.println("Error: Failed to create data directory: " + DATA_DIR + ". Please check permissions and path validity.");
                }
            } catch (SecurityException se) {
                System.err.println("SecurityError: Failed to create data directory " + DATA_DIR + ": " + se.getMessage());
            } // Permission Error
        }

        if (!usersDir.exists()) {
            System.out.println("Creating users directory: " + USERS_DIR);
            try {
                if (!usersDir.mkdirs()) {
                    System.err.println("Error: Failed to create users directory: " + USERS_DIR + ". Please check permissions and path validity.");
                }
            } catch (SecurityException se2) {
                System.err.println("SecurityError: Failed to create users directory " + USERS_DIR + ": " + se2.getMessage());
            }
        }
    }

    public static void updateAccountsList(List<String> usernames) {
        File accountsFile = new File(DATA_DIR, "accounts.txt");
        try (PrintWriter writer = new PrintWriter(new FileWriter(accountsFile))) {
            for (String username : usernames) {
                writer.println(username);
            }
        } catch (IOException e) {
            System.err.println("Error updating accounts list: " + e.getMessage());
        }
    }

    private static void writeFile(String path, String content) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            pw.println(content);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + path);
        }
    }

    // ----------- User info modifications ----------- //
    public static void saveUserInfo(Account account) {
        // Ensure users directory exists
        File usersDir = new File(USERS_DIR);
        usersDir.mkdirs();

        String username = account.getUsername();
        File infoFile = new File(usersDir, username + "_info.txt");
        File transactionsFile = new File(usersDir, username + "_transactions.txt");
        File gameHistoryFile = new File(usersDir, username + "_gamehistory.txt");

        writeFile(infoFile.getPath(), """
                "Username: %s"
                "Password: %s"
                ----------------
                "Balance: %s"
                "Tokens: %s"
                "Tickets: %s"
                ----------------
                """.formatted(account.getUsername(), account.getPassword(), account.getBalance(), account.getTokens(), account.getTickets()));

        if (!transactionsFile.exists()) {
            writeFile(transactionsFile.getPath(),
                "======================= Transactions ======================="
            );
        }
        if (!gameHistoryFile.exists()) {
            writeFile(gameHistoryFile.getPath(),
                "======================= Game History ======================="
            );
        }
    }

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

// ----------- Verification if user exists ----------- //
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
        }
        return false;
    }

    // Get all usernames, for the sake of Login
    public static List<String> getAllUsernames() {
        List<String> usernames = new ArrayList<>();
        File accountsFile = new File(DATA_DIR, "accounts.txt");
        try (Scanner scanner = new Scanner(accountsFile)) {
            while (scanner.hasNextLine()) {
                usernames.add(scanner.nextLine().trim());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Accounts file not found while getting usernames: " + e.getMessage());
        }
        return usernames;
    }

// ------------------------------------- //
    public static Account readUserFile(String username) {
        File infoFile = new File(USERS_DIR, username + "_info.txt");
        String password = "";
        double balance = 0.0;
        int tokens = 0;
        int tickets = 0;

        try (Scanner scanner = new Scanner(infoFile)) {  
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (line.equals("----------------")) {
                    continue;
                }

                String processedLine = line;
                if (processedLine.startsWith("\"") && processedLine.endsWith("\"")) {
                    processedLine = processedLine.substring(1, processedLine.length() - 1);
                } 
                String[] parts = processedLine.split(": ", 2);
                if (parts.length != 2) {
                    if (!line.isEmpty()) {
                        System.err.println("Skipping malformed line in " + infoFile.getName() + ": '" + line + "' (after quote processing: '" + processedLine + "')");
                    }
                    continue;
                } // Ignores cases like Password: 123: Pass

                String key = parts[0]; // "Password"
                String value = parts[1]; // "The actual Password"

                try {
                    switch (key) {
                        case "Password" -> password = value;
                        case "Balance" -> balance = Double.parseDouble(value);
                        case "Tokens" -> tokens = Integer.parseInt(value);
                        case "Tickets" -> tickets = Integer.parseInt(value);
                        default -> System.err.println("Skipping unknown key in " + infoFile.getName() + ": '" + key + "'");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Skipping malformed value for key '" + key + "' in " + infoFile.getName() + ": '" + value + "'");
                }
            }
            TopUpSystem account = new TopUpSystem(username, password);
            account.addBalance(balance); 
            account.addTokens(tokens);
            account.addTickets(tickets); 
            return account;
        } catch (FileNotFoundException e) {
            System.err.println("Info file not found for user '" + username + "': " + e.getMessage());
            return null;
        }
    }
}
