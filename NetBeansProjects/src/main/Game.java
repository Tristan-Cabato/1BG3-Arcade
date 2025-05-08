public abstract class Game {
    protected Account player;
    protected String name;
    protected boolean victoryStatus;
    protected int ticketsWon;

    public Game(Account player) {
        this.player = player;
    }
    
    public String getName() {
        return name;
    }

    public boolean getVictoryStatus() {
        return victoryStatus;
    }

    public int getTicketsWon() {
        return ticketsWon;
    }

    public final void start() {
        if (player.spendTokens(1)) { 
            System.out.println("\n--- Starting " + getName() + " (Cost: 1 Token) ---");
            play();
        } else {
            System.out.println("\nNot enough tokens to play " + getName() + ". You need at least 1 token."); 
             System.out.println("Returned to menu...");
        }
    }

    public abstract void play();

    protected void reward(int ticketsWon) {
        System.out.println("You won " + ticketsWon + " tickets!");
        player.addTickets(ticketsWon);
    }

    protected void logGameResult(Account player, String gameName, boolean won, int tickets) {
        new GameHistory().gameLog(player, gameName, won, tickets);
    }

}
