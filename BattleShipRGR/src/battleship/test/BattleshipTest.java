package battleship.test;

import battleship.exception.BattleshipException;
import battleship.exception.InvalidCoordinateException;
import battleship.exception.ShipOverlapException;
import battleship.model.Board;
import battleship.model.Coordinate;
import battleship.model.Ship;
import battleship.model.ShipType;
import battleship.model.factory.StandardShipFactory;

import java.util.Arrays;

public class BattleshipTest {

    public static void main(String[] args) {
        System.out.println("=== RUNNING BATTLESHIP TDD SUITE ===");
        int passed = 0;
        int failed = 0;

        // Test 1: Valid Placement
        try {
            testValidShipPlacement();
            System.out.println("testValidShipPlacement: PASSED");
            passed++;
        } catch (Exception e) {
            System.err.println("testValidShipPlacement: FAILED - " + e.getMessage());
            e.printStackTrace();
            failed++;
        }

        // Test 2: Boundary Check (Out of bounds)
        try {
            testOutOfBoundsPlacement();
            System.out.println("testOutOfBoundsPlacement: PASSED");
            passed++;
        } catch (Exception e) {
            System.err.println("testOutOfBoundsPlacement: FAILED - " + e.getMessage());
            failed++;
        }

        // Test 3: Overlap Check
        try {
            testOverlappingShips();
            System.out.println("testOverlappingShips: PASSED");
            passed++;
        } catch (Exception e) {
            System.err.println("testOverlappingShips: FAILED - " + e.getMessage());
            failed++;
        }

        // Test 4: Shooting Mechanics
        try {
            testShootingAndSinking();
            System.out.println("testShootingAndSinking: PASSED");
            passed++;
        } catch (Exception e) {
            System.err.println("testShootingAndSinking: FAILED - " + e.getMessage());
            e.printStackTrace();
            failed++;
        }
        System.out.println("SUMMARY: Passed: " + passed + " | Failed: " + failed);
    }

    // --- TEST CASES ---

    private static void testValidShipPlacement() throws BattleshipException {
        Board board = new Board();
        // Create a Destroyer (Size 2) using helper
        Ship ship = createTestShip(new Coordinate(0, 0), new Coordinate(0, 1));

        board.placeShip(ship);

        if (board.getShips().size() != 1) {
            throw new RuntimeException("Ship was not added to the board list.");
        }
        if (board.getShipAt(new Coordinate(0, 0)) != ship) {
            throw new RuntimeException("Board cannot find the ship at valid coordinate (0,0).");
        }
    }

    private static void testOutOfBoundsPlacement() {
        Board board = new Board();
        // Ship goes from (9,9) to (9,10). (9,10) is outside the 10x10 grid.
        Ship ship = createTestShip(new Coordinate(9, 9), new Coordinate(9, 10));

        try {
            board.placeShip(ship);
            throw new RuntimeException("Expected InvalidCoordinateException was NOT thrown for out-of-bounds ship.");
        } catch (InvalidCoordinateException e) {
        } catch (BattleshipException e) {
            throw new RuntimeException("Wrong exception type thrown: " + e.getClass().getSimpleName());
        }
    }

    private static void testOverlappingShips() throws BattleshipException {
        Board board = new Board();
        // Ship 1: (2,2) - (2,3)
        Ship ship1 = createTestShip(new Coordinate(2, 2), new Coordinate(2, 3));
        board.placeShip(ship1);

        // Ship 2: (2,2) - (3,2) -> Intersects at (2,2)
        Ship ship2 = createTestShip(new Coordinate(2, 2), new Coordinate(3, 2));

        try {
            board.placeShip(ship2);
            throw new RuntimeException("Expected ShipOverlapException was NOT thrown for overlapping ships.");
        } catch (ShipOverlapException e) {
        }
    }

    private static void testShootingAndSinking() throws BattleshipException {
        Board board = new Board();
        Coordinate bow = new Coordinate(5, 5);
        Coordinate stern = new Coordinate(5, 6);
        Ship ship = createTestShip(bow, stern);

        board.placeShip(ship);

        // 1. Shoot and Miss
        boolean hit = board.receiveAttack(new Coordinate(0, 0)); // Old signature for Commit 4
        if (hit) throw new RuntimeException("Shot at (0,0) should be a miss.");

        // 2. Shoot and Hit (Deck 1)
        hit = board.receiveAttack(bow);
        if (!hit) throw new RuntimeException("Shot at (5,5) should be a hit.");
        if (!ship.isAlive()) throw new RuntimeException("Ship should still be alive after 1 hit (size 2).");

        // 3. Shoot and Hit (Deck 2) – Sink
        hit = board.receiveAttack(stern);
        if (!hit) throw new RuntimeException("Shot at (5,6) should be a hit.");
        if (ship.isAlive()) throw new RuntimeException("Ship should be sunk after all decks are hit.");
    }

    private static Ship createTestShip(Coordinate... coords) {
        StandardShipFactory factory = new StandardShipFactory();

        // Determine type based on size for testing purposes
        ShipType type;
        switch (coords.length) {
            case 4: type = ShipType.BATTLESHIP; break;
            case 3: type = ShipType.CRUISER; break;
            case 2: type = ShipType.DESTROYER; break;
            case 1: type = ShipType.SUBMARINE; break;
            default: throw new IllegalArgumentException("Unsupported test ship size: " + coords.length);
        }

        return factory.createShip(type, Arrays.asList(coords));
    }
}