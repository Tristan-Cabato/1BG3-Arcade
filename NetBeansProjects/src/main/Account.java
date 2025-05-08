public class Account {
    private String username;
    private String password;
    private double balance;
    private int tokens;
    private int tickets;
    private Game currentGame;

    public Account() {}
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 0;
        this.tokens = 10;
        this.tickets = 0;
    }

    public void setGame(Game game) {
        this.currentGame = game;
    }
    public Game getCurrentGame() {
        return currentGame;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    // Balance
    public double getBalance() {
        return balance;
    }
    public void addBalance(double amount) {
        this.balance += amount;
    }
    public void addBalance(int amount) {
        // Call the double version after casting
        addBalance((double) amount);
    }

    // Tokens
    public int getTokens() {
        return tokens;
    }
    public void addTokens(int amount) {
        this.tokens += amount;
    }
    public boolean spendTokens(int amount) {
        if (tokens >= amount) {
            tokens -= amount;
            return true;
        }
        return false;
    }
    
    // Tickets
    public int getTickets() {
        return tickets;
    }
    public void addTickets(int amount) {
        this.tickets += amount;
    }
    public boolean exchangeTickets(int ticketCost) {
        if (tickets >= ticketCost) {
            tickets -= ticketCost;
            return true;
        }
        return false;
    }
}
