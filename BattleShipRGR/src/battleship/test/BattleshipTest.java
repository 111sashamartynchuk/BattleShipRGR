package battleship.test;

import battleship.exception.BattleshipException;
import battleship.exception.InvalidCoordinateException;
import battleship.exception.ShipOverlapException;
import battleship.model.Board;
import battleship.model.Coordinate;
import battleship.model.Ship;
import java.util.Arrays;
import java.util.List;

public class BattleshipTest {

    public static void main(String[] args) {
        System.out.println("=== RUNNING BATTLESHIP TDD SUITE ===");
        int passed = 0;
        int failed = 0;

        // Test 1: Valid Placement
        try {
            testValidShipPlacement();
            System.out.println(" testValidShipPlacement: PASSED");
            passed++;
        } catch (Exception e) {
            System.err.println(" testValidShipPlacement: FAILED - " + e.getMessage());
            e.printStackTrace();
            failed++;
        }

        // Test 2: Boundary Check (Out of bounds)
        try {
            testOutOfBoundsPlacement();
            System.out.println(" testOutOfBoundsPlacement: PASSED");
            passed++;
        } catch (Exception e) {
            System.err.println(" testOutOfBoundsPlacement: FAILED - " + e.getMessage());
            failed++;
        }

        // Test 3: Overlap Check
        try {
            testOverlappingShips();
            System.out.println(" testOverlappingShips: PASSED");
            passed++;
        } catch (Exception e) {
            System.err.println(" testOverlappingShips: FAILED - " + e.getMessage());
            failed++;
        }

        // Test 4: Shooting Mechanics
        try {
            testShootingAndSinking();
            System.out.println(" testShootingAndSinking: PASSED");
            passed++;
        } catch (Exception e) {
            System.err.println(" testShootingAndSinking: FAILED - " + e.getMessage());
            e.printStackTrace();
            failed++;
        }

        System.out.println("SUMMARY: Passed: " + passed + " | Failed: " + failed);
        if (failed == 0) {
            System.out.println(" ALL TESTS PASSED. READY FOR NEXT DEVELOPMENT STAGE.");
        } else {
            System.out.println(" SOME TESTS FAILED. CHECK LOGIC.");
        }
    }

    // TEST CASES

    private static void testValidShipPlacement() throws BattleshipException {
        Board board = new Board();
        // Place a ship at (0,0) and (0,1)
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

        Ship ship1 = createTestShip(new Coordinate(2, 2), new Coordinate(2, 3));
        board.placeShip(ship1);

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

        // Shoot and Miss
        boolean hit = board.receiveAttack(new Coordinate(0, 0));
        if (hit) throw new RuntimeException("Shot at (0,0) should be a miss.");
        if (board.getMisses().size() != 1) throw new RuntimeException("Miss count did not increase.");

        // Shoot and Hit (Deck 1)
        hit = board.receiveAttack(bow);
        if (!hit) throw new RuntimeException("Shot at (5,5) should be a hit.");
        if (!ship.isAlive()) throw new RuntimeException("Ship should still be alive after 1 hit (size 2).");

        // Shoot and Hit (Deck 2) - Sink
        hit = board.receiveAttack(stern);
        if (!hit) throw new RuntimeException("Shot at (5,6) should be a hit.");
        if (ship.isAlive()) throw new RuntimeException("Ship should be sunk after all decks are hit.");

        if (!board.allShipsSunk()) throw new RuntimeException("Board should report all ships sunk.");
    }

    private static Ship createTestShip(Coordinate... coords) {
        return new Ship(Arrays.asList(coords)) {
            public String getName() { return "Test Unit"; }
            public int getSize() { return coordinates.size(); }
        };
    }
}