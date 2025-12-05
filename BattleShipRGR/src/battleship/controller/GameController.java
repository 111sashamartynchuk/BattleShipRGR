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
import battleship.util.InputUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameController {
    private final Player player1;
    private final Player player2;
    private final StandardShipFactory shipFactory;
    private final ConsoleObserver consoleObserver;
    private final BufferedReader reader;

    public GameController(Player p1, Player p2) {
        this.shipFactory = new StandardShipFactory();
        this.consoleObserver = new ConsoleObserver();
        this.reader = new BufferedReader(new InputStreamReader(System.in));

        this.player1 = p1;
        this.player2 = p2;

        player1.setEnemyBoard(player2.getMyBoard());
        player2.setEnemyBoard(player1.getMyBoard());

        player1.getEnemyBoard().addObserver(consoleObserver);
        player2.getEnemyBoard().addObserver(consoleObserver);

        if (GameSettings.getInstance().isDebugMode()) {
            System.out.println("[DEBUG] Game Controller initialized.");
        }
    }

    public void startGame() {
        System.out.println("===  BATTLESHIP GAME  ===");

        System.out.println("\n---  FLEET DEPLOYMENT PHASE ---");
        setupBoardForPlayer(player1);
        setupBoardForPlayer(player2);

        System.out.println("\n---  BATTLE START  ---");
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

    private void setupBoardForPlayer(Player player) {
        System.out.println("\nPreparing fleet for: " + player.getName());
        Board board = player.getMyBoard();

        if (player instanceof ComputerPlayer) {
            System.out.println("AI is deploying ships...");
            placeShipsRandomly(board);
            System.out.println("AI ships deployed.");
        } else {
            placeShipsManually(board);
        }
    }

    private void placeShipsManually(Board board) {
        for (ShipType type : ShipType.values()) {
            boolean placed = false;
            while (!placed) {
                try {
                    System.out.println("Place " + type.getName() + " (Size: " + type.getSize() + ")");
                    System.out.println("Enter format: START_COORD DIRECTION (e.g., 'A1 H' or 'B2 V'):");

                    String line = reader.readLine();
                    List<Coordinate> coords = parsePlacementInput(line, type.getSize());

                    Ship ship = shipFactory.createShip(type, coords);
                    board.placeShip(ship);
                    placed = true;
                    System.out.println(" " + type.getName() + " placed.");

                } catch (BattleshipException e) {
                    System.out.println(" Placement Error: " + e.getMessage());
                } catch (IllegalArgumentException | IOException e) {
                    System.out.println(" Invalid Input: " + e.getMessage());
                }
            }
        }
    }

    private void placeShipsRandomly(Board board) {
        Random random = new Random();
        for (ShipType type : ShipType.values()) {
            boolean placed = false;
            int attempts = 0;
            while (!placed && attempts < 100) {
                try {
                    int x = random.nextInt(Board.SIZE);
                    int y = random.nextInt(Board.SIZE);
                    boolean horizontal = random.nextBoolean();

                    List<Coordinate> coords = generateCoordinates(new Coordinate(x, y), type.getSize(), horizontal);
                    Ship ship = shipFactory.createShip(type, coords);
                    board.placeShip(ship);
                    placed = true;
                } catch (BattleshipException | IllegalArgumentException e) {
                    attempts++;
                }
            }
        }
    }

    private List<Coordinate> parsePlacementInput(String input, int size) {
        if (input == null || input.trim().isEmpty()) throw new IllegalArgumentException("Empty input");

        String[] parts = input.trim().toUpperCase().split("\\s+");
        if (parts.length < 2) throw new IllegalArgumentException("Missing direction (H or V)");

        // REFACTORING: Using InputUtils
        Coordinate start = InputUtils.parseCoordinate(parts[0]);

        boolean horizontal = parts[1].startsWith("H");
        return generateCoordinates(start, size, horizontal);
    }

    private List<Coordinate> generateCoordinates(Coordinate start, int size, boolean horizontal) {
        List<Coordinate> coords = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int x = horizontal ? start.getX() + i : start.getX();
            int y = horizontal ? start.getY() : start.getY() + i;
            coords.add(new Coordinate(x, y));
        }
        return coords;
    }


    private void playTurn(Player player) {
        boolean validTurn = false;
        while (!validTurn) {
            try {
                Coordinate target = player.performMove();
                boolean hit = player.getEnemyBoard().receiveAttack(target, player.getName());

                if (hit) {
                    if (player.getEnemyBoard().allShipsSunk()) {
                        validTurn = true;
                    } else {
                        System.out.println(">> Bonus turn!");
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
}