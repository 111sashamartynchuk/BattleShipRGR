package battleship.model;

import battleship.exception.BattleshipException;
import battleship.exception.InvalidCoordinateException;
import battleship.exception.ShipOverlapException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//Represents the game board. Implements Iterable (Behavioral Pattern) to allow easy traversal of ships.

public class Board implements Iterable<Ship> {
    public static final int SIZE = 10; // In real app, could take from GameSettings

    private final List<Ship> ships;
    private final List<Coordinate> misses;
    private final List<Coordinate> hits;

    public Board() {
        this.ships = new ArrayList<>();
        this.misses = new ArrayList<>();
        this.hits = new ArrayList<>();
    }

    //Iterator Pattern implementation.

    public Iterator<Ship> iterator() {
        return ships.iterator();
    }

    public void placeShip(Ship ship) throws BattleshipException {
        for (Coordinate coord : ship.getCoordinates()) {
            if (!isValidCoordinate(coord)) {
                throw new InvalidCoordinateException("Ship is out of bounds at: " + coord);
            }
            if (isOccupied(coord)) {
                throw new ShipOverlapException("Position occupied at: " + coord);
            }
        }
        ships.add(ship);
    }

    public boolean receiveAttack(Coordinate coordinate) throws InvalidCoordinateException {
        if (!isValidCoordinate(coordinate)) {
            throw new InvalidCoordinateException("Shot out of bounds: " + coordinate);
        }

        for (Ship ship : ships) {
            if (ship.occupies(coordinate)) {
                ship.hit(coordinate);
                hits.add(coordinate);
                return true;
            }
        }

        misses.add(coordinate);
        return false;
    }

    public boolean isValidCoordinate(Coordinate c) {
        return c.getX() >= 0 && c.getX() < SIZE && c.getY() >= 0 && c.getY() < SIZE;
    }

    public boolean isOccupied(Coordinate c) {
        return getShipAt(c) != null;
    }

    public Ship getShipAt(Coordinate c) {
        for (Ship ship : ships) {
            if (ship.occupies(c)) {
                return ship;
            }
        }
        return null;
    }

    public boolean allShipsSunk() {
        return ships.stream().noneMatch(Ship::isAlive);
    }

    public List<Ship> getShips() { return new ArrayList<>(ships); }
    public List<Coordinate> getMisses() { return new ArrayList<>(misses); }
    public List<Coordinate> getHits() { return new ArrayList<>(hits); }
}