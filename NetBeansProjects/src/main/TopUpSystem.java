import java.text.*;
import java.util.*;

public class TopUpSystem extends Account {
    Scanner scan = new Scanner(System.in);
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy (HH:mm)");

    public TopUpSystem() {
        super(); 
    }
    
    // Add constructor to match Account(username, password)
    public TopUpSystem(String username, String password) {
        super(username, password);
    }

    public void TopUp() {
        int balanceChange = 0;
        int tokensChange = 0;
        
        // Choice        
        System.out.println("""
            What do you want to do?
            [1] Top Up
            [2] Exchange Tickets
            """);
        int choice = scan.nextInt();
        scan.nextLine(); // Consume newline left-over

        switch (choice) {
            case 1 -> {
                System.out.print("""
                        [Top Up Balance]

                        10 balance = 1 token

                        Input your purchase in pesos amount
                        """);
                int purchase = scan.nextInt();
                scan.nextLine(); // Consume newline left-over

                addTokens(purchase / 10);
                addBalance(purchase % 10); 
                balanceChange = purchase % 10;
                tokensChange = purchase / 10;
            } case 2 -> {
                System.out.print("""
                        [Exchange Tickets]

                        10 tickets = 1 token

                        Input your purchase in ticket amount
                        """);
                int purchase = scan.nextInt();
                scan.nextLine(); // Consume newline left-over

                if (exchangeTickets(purchase)) {
                    addTickets(-purchase);
                    addTokens(purchase / 10);
                    balanceChange = 0;
                    tokensChange = purchase / 10;
                } else {
                    System.out.print("Not enough tickets");
                }
            }
        }

        String transactionLog = """
            [%s] Balance: %.2f (+%d) | Tokens: %d (+%d)
            """.formatted(
                formatter.format(new Date()),
                getBalance(), balanceChange,
                getTokens(), tokensChange
            );
        FileHelper.saveTransaction(this, transactionLog);
        // Pause before returning
        System.out.println("\nPress Enter to return to the main menu...");
        scan.nextLine();
    }
}
