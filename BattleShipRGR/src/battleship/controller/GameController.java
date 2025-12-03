package battleship.controller;

import battleship.exception.BattleshipException;
import battleship.model.Board;
import battleship.model.Coordinate;
import battleship.model.Player;
import battleship.model.Ship;
import battleship.model.ShipType;
import battleship.model.factory.StandardShipFactory;
import battleship.model.player.ComputerPlayer;
import battleship.model.player.HumanPlayer;
import battleship.model.strategy.RandomShootingStrategy;

import java.util.ArrayList;
import java.util.List;

//Controller class (GRASP)

public class GameController {
    private final Player player1;
    private final Player player2;
    private final StandardShipFactory shipFactory;

    public GameController() {
        this.shipFactory = new StandardShipFactory();

        this.player1 = new HumanPlayer("User");
        // Injecting Random Strategy into Computer
        this.player2 = new ComputerPlayer("AI Bot", new RandomShootingStrategy());

        // Link boards (Важливо: це працює тільки якщо в Player є setEnemyBoard)
        player1.setEnemyBoard(player2.getMyBoard());
        player2.setEnemyBoard(player1.getMyBoard());
    }

    public void startGame() {
        System.out.println("=== BATTLESHIP GAME START ===");

        // Demo placement so we can play immediately
        setupDemoBoard(player1.getMyBoard());
        setupDemoBoard(player2.getMyBoard());

        boolean gameOver = false;
        Player currentPlayer = player1;

        while (!gameOver) {
            playTurn(currentPlayer);

            if (currentPlayer.getEnemyBoard().allShipsSunk()) {
                System.out.println("GAME OVER! Winner: " + currentPlayer.getName());
                gameOver = true;
            } else {
                // Switch turn
                currentPlayer = (currentPlayer == player1) ? player2 : player1;
            }
        }
    }

    private void playTurn(Player player) {
        boolean validTurn = false;
        while (!validTurn) {
            try {
                Coordinate target = player.makeMove();
                boolean hit = player.getEnemyBoard().receiveAttack(target);
                if (hit) {
                    System.out.println(">>> HIT!");
                    if (!player.getEnemyBoard().allShipsSunk()) {
                        System.out.println("Shoot again!");
                        continue; // Bonus turn
                    }
                } else {
                    System.out.println(">>> MISS.");
                }
                validTurn = true;
            } catch (BattleshipException e) {
                System.out.println("Error: " + e.getMessage());
                // Skip AI error to avoid infinite loop
                if (player instanceof ComputerPlayer) validTurn = true;
            }
        }
    }

    private void setupDemoBoard(Board board) {
        try {
            List<Coordinate> coords = new ArrayList<>();
            coords.add(new Coordinate(0, 0));
            coords.add(new Coordinate(0, 1));
            coords.add(new Coordinate(0, 2));
            Ship cruiser = shipFactory.createShip(ShipType.CRUISER, coords);
            board.placeShip(cruiser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}