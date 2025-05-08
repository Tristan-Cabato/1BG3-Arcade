import java.text.*;
import java.util.*;

public class GameHistory extends Account { 
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy (HH:mm)");
    public GameHistory() {}

    void gameLog(Account playerAccount, String gameName, boolean victoryStatus, int tickets) {
        Date currentDate = new Date();
        String formattedDate = formatter.format(currentDate);
                
        String textLog = "(%s) %s at %s. %s.".formatted(
            formattedDate,
            victoryStatus ? "Won" : "Lost",
            gameName,
            victoryStatus ? "Earned %d tickets".formatted(tickets) : "Earned no tickets");

        FileHelper.saveGameHistory(playerAccount, textLog);
    }
}