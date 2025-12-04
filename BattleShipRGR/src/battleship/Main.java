package battleship;

import battleship.controller.GameController;
import battleship.model.builder.GameBuilder;
import battleship.model.strategy.RandomShootingStrategy;

public class Main {
    public static void main(String[] args) {
        // Using Builder Pattern to construct the game
        GameController game = new GameBuilder()
                .setPlayer1("Captain Jack")
                .setAIPlayer2("Davy Jones", new RandomShootingStrategy())
                .build();

        game.startGame();
    }
}