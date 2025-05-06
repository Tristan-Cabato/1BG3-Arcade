public class Main {
    public static void main(String[] args) {
        Account testPlayer = new Account("Sicat", "password");
        
        RockPaperScissors game = new RockPaperScissors(testPlayer);
        game.play();
    }
}