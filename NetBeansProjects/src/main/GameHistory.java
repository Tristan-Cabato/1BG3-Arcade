import java.io.*;
import java.text.*;
import java.util.*;

public class GameHistory extends Account { 
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    public GameHistory() {}

    void gameLog(String gameName, boolean victoryStatus, int tickets) throws IOException {
        Date currentDate = new Date();
        String formattedDate = formatter.format(currentDate);
                
        String textLog = "(%s) %s at %s. %s tickets.".formatted(
            formattedDate,
            victoryStatus ? "Won" : "Lost",
            gameName,
            victoryStatus ? "Earned %d tickets".formatted(tickets) : "Earned no tickets");

        FileHelper.saveGameHistory(this, textLog);
    }
}