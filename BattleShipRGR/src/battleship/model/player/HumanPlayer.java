package battleship.model.player;

import battleship.model.Coordinate;
import battleship.model.Player;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class HumanPlayer extends Player {
    private final BufferedReader reader;

    public HumanPlayer(String name) {
        super(name);
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public Coordinate makeMove() {
        System.out.println(name + ", enter target coordinates (e.g., A1, B5, J10): ");
        try {
            String line = reader.readLine();
            return parseCoordinate(line);
        } catch (IllegalArgumentException | IOException e) {
            System.out.println("Invalid format! Use Letter + Number (e.g., A1). Try again.");
            return makeMove();
        }
    }

    private Coordinate parseCoordinate(String input) {
        if (input == null || input.length() < 2) {
            throw new IllegalArgumentException();
        }
        input = input.toUpperCase().trim();

        char colChar = input.charAt(0); // 'A'
        if (colChar < 'A' || colChar > 'J') throw new IllegalArgumentException();

        try {
            int row = Integer.parseInt(input.substring(1)) - 1; // "1" -> 0
            int col = colChar - 'A'; // 'A' -> 0
            return new Coordinate(col, row);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }
}