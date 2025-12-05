package battleship.model.builder;

import battleship.controller.GameController;
import battleship.model.player.ComputerPlayer;
import battleship.model.player.HumanPlayer;
import battleship.model.Player;
import battleship.model.strategy.RandomShootingStrategy;
import battleship.model.strategy.ShootingStrategy;


// * Implements Builder Pattern (Creational).
// *Encapsulates the construction of the GameController and its dependencies.
public class GameBuilder {
    private Player player1;
    private Player player2;

    public GameBuilder() {
        // Defaults
        this.player1 = new HumanPlayer("Player 1");
        this.player2 = new ComputerPlayer("Computer", new RandomShootingStrategy());
    }

    public GameBuilder setPlayer1(String name) {
        this.player1 = new HumanPlayer(name);
        return this;
    }

    public GameBuilder setAIPlayer2(String name, ShootingStrategy strategy) {
        this.player2 = new ComputerPlayer(name, strategy);
        return this;
    }

    public GameController build() {
        // Link logic implies creating the controller with these specific players
        return new GameController(player1, player2);
    }
}