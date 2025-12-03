package battleship;
import battleship.controller.GameController;

public class Main {
    public static void main(String[] args) {
        // The Main class now simply delegates to the GameController.
        GameController game = new GameController();
        game.startGame();
    }
}