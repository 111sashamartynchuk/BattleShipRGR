package battleship;

import battleship.controller.GameController;
import battleship.model.builder.GameBuilder;
import battleship.model.strategy.RandomShootingStrategy;

//This class bootstraps the game using the Builder Pattern to configure
// the controller, players, and AI strategies before starting the game loop.

public class Main {
    public static void main(String[] args) {
        // Using Builder Pattern to construct the game
        GameController game = new GameBuilder()
                .setPlayer1("user")
                .setAIPlayer2("bot", new RandomShootingStrategy())
                .build();

        game.startGame();
    }
}