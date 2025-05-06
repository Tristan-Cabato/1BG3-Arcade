import java.io.*;

public abstract class Game {
    protected Account player;
    protected String name;
    protected boolean victoryStatus;
    protected int ticketsWon;
    // Protected is like private but also allows subclasses to access directly

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

    public abstract void play() throws IOException;

    protected void reward(int ticketsWon) {
        System.out.println("You won " + ticketsWon + " tickets!");
        player.addTickets(ticketsWon);
    }

    protected void logGameResult(String gameName, boolean won, int tickets) throws IOException {
        new GameHistory().gameLog(gameName, won, tickets);
    }

}
