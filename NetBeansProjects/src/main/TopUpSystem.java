import java.text.*;
import java.util.*;
import java.io.*;

public class TopUpSystem extends Account {
    Scanner scan = new Scanner(System.in);
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public TopUpSystem() {
        super();
    }
    
public void TopUp() throws IOException {
    int balanceChange = 0;
    int tokensChange = 0;
    
    // Choice        
    System.out.println("""
            What do you want to do?
            [1] Top Up
            [2] Exchange Tickets
            """);
    int choice = scan.nextInt();

    switch (choice) {
        case 1:
            System.out.print("""
                    [Top Up Balance]

                    10 balance = 1 token

                    Input your purchase in balance amount
                    """);
            int purchase = scan.nextInt();

            addTokens(purchase / 10);
            addBalance(purchase);
            balanceChange = purchase;
            tokensChange = purchase / 10;

            break;
        case 2:
            System.out.print("""
                    [Exchange Tickets]

                    10 tickets = 1 token

                    Input your purchase in ticket amount
                    """);
            purchase = scan.nextInt();

            if (exchangeTickets(purchase)) {
                addTickets(-purchase);
                addTokens(purchase / 10);
                
                balanceChange = 0;
                tokensChange = purchase / 10;
            } else {
                System.out.print("Not enough tickets");
                return; // Avoid logging if not enough tickets
            }
    }

    String transactionLog = """
        [%s] Balance: %d (+%d) | Tokens: %d (+%d)
        """.formatted(
            formatter.format(new Date()),
            getBalance(), balanceChange,
            getTokens(), tokensChange
        );
    FileHelper.saveTransaction(this, transactionLog);
    }
}