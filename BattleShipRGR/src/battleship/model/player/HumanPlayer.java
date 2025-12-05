package battleship.model.player;

import battleship.model.Coordinate;
import battleship.model.Player;
import battleship.util.InputUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class HumanPlayer extends Player {
    private final BufferedReader reader;

    public HumanPlayer(String name) {
        super(name);
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    protected Coordinate getCoordinatesFromInput() {
        System.out.println(name + ", enter target (e.g., A1): ");
        try {
            String line = reader.readLine();
            // REFACTORING - Use the shared utility instead of duplicated logic
            return InputUtils.parseCoordinate(line);
        } catch (IllegalArgumentException | IOException e) {
            System.out.println(" " + e.getMessage() + " Try again.");
            return getCoordinatesFromInput(); // Retry
        }
    }

}