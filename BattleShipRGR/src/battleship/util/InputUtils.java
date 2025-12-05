package battleship.util;

import battleship.model.Coordinate;

//Implements GRASP Pure Fabrication pattern to avoid code duplication

public class InputUtils {

    // Private constructor to prevent instantiation of utility class
    private InputUtils() {
    }

    public static Coordinate parseCoordinate(String input) {
        if (input == null || input.length() < 2) {
            throw new IllegalArgumentException("Input is too short. Expected format like 'A1'.");
        }

        String cleanInput = input.trim().toUpperCase();
        char colChar = cleanInput.charAt(0);

        if (colChar < 'A' || colChar > 'J') {
            throw new IllegalArgumentException("Invalid column '" + colChar + "'. Expected A-J.");
        }

        try {
            String numPart = cleanInput.substring(1);
            int row = Integer.parseInt(numPart) - 1; 

            if (row < 0 || row > 9) {
                throw new IllegalArgumentException("Row " + (row + 1) + " is out of bounds.");
            }

            int col = colChar - 'A';
            return new Coordinate(col, row);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid row format. Expected a number.");
        }
    }
}