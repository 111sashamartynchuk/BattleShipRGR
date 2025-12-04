package battleship.controller;

import battleship.exception.BattleshipException;
import battleship.model.Board;
import battleship.model.Coordinate;
import battleship.model.Player;
import battleship.model.Ship;
import battleship.model.ShipType;
import battleship.model.factory.StandardShipFactory;
import battleship.model.player.ComputerPlayer;
import battleship.observer.ConsoleObserver;
import battleship.util.GameSettings;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private final Player player1;
    private final Player player2;
    private final StandardShipFactory shipFactory;
    private final ConsoleObserver consoleObserver; // View component

    //Constructor now accepts players (Dependency Injection via Builder).

    public GameController(Player p1, Player p2) {
        this.shipFactory = new StandardShipFactory();
        this.consoleObserver = new ConsoleObserver();

        this.player1 = p1;
        this.player2 = p2;

        player1.setEnemyBoard(player2.getMyBoard());
        player2.setEnemyBoard(player1.getMyBoard());

        // Register Observers (GRASP: Low Coupling - Board doesn't know about Controller)
        player1.getEnemyBoard().addObserver(consoleObserver);
        player2.getEnemyBoard().addObserver(consoleObserver);

        if (GameSettings.getInstance().isDebugMode()) {
            System.out.println("[DEBUG] Game Controller initialized via Builder.");
        }
    }

    public void startGame() {
        System.out.println("=== BATTLESHIP GAME ===");

        System.out.println("Deploying ships...");
        placeDemoShips(player1.getMyBoard());
        placeDemoShips(player2.getMyBoard());

        boolean gameOver = false;
        Player currentPlayer = player1;

        while (!gameOver) {
            System.out.println("\n--- Turn: " + currentPlayer.getName() + " ---");
            playTurn(currentPlayer);

            if (currentPlayer.getEnemyBoard().allShipsSunk()) {
                System.out.println("\n GAME OVER! Winner: " + currentPlayer.getName());
                gameOver = true;
            } else {
                currentPlayer = (currentPlayer == player1) ? player2 : player1;
            }
        }
    }

    private void playTurn(Player player) {
        boolean validTurn = false;
        while (!validTurn) {
            try {
                Coordinate target = player.performMove();

                // Pass player name to board so Observer can print who attacked
                boolean hit = player.getEnemyBoard().receiveAttack(target, player.getName());

                if (hit) {
                    if (player.getEnemyBoard().allShipsSunk()) {
                        validTurn = true;
                    } else {
                        System.out.println(">> Bonus turn!"); // Game flow message, allowed in controller
                    }
                } else {
                    validTurn = true;
                }

            } catch (BattleshipException e) {
                System.out.println(" Error: " + e.getMessage());
                if (player instanceof ComputerPlayer) validTurn = true;
            }
        }
    }

    private void placeDemoShips(Board board) {
        try {
            List<Coordinate> coords1 = new ArrayList<>();
            coords1.add(new Coordinate(0, 0));
            coords1.add(new Coordinate(0, 1));
            Ship destroyer = shipFactory.createShip(ShipType.DESTROYER, coords1);
            board.placeShip(destroyer);

            List<Coordinate> coords2 = new ArrayList<>();
            coords2.add(new Coordinate(4, 4));
            Ship sub = shipFactory.createShip(ShipType.SUBMARINE, coords2);
            board.placeShip(sub);
        } catch (BattleshipException e) {
            System.err.println("Setup failed: " + e.getMessage());
        }
    }
}