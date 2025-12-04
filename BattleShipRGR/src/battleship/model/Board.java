package battleship.model;

import battleship.exception.BattleshipException;
import battleship.exception.InvalidCoordinateException;
import battleship.exception.ShipOverlapException;
import battleship.observer.GameObserver; // Add import

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Board implements Iterable<Ship> {
    public static final int SIZE = 10;

    private final List<Ship> ships;
    private final List<Coordinate> misses;
    private final List<Coordinate> hits;

    // NEW List of observers
    private final List<GameObserver> observers;

    public Board() {
        this.ships = new ArrayList<>();
        this.misses = new ArrayList<>();
        this.hits = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    // NEW Method to attach observers
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

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

    // UPDATED: Now accepts attackerName to notify observers properly.

    public boolean receiveAttack(Coordinate coordinate, String attackerName) throws InvalidCoordinateException {
        if (!isValidCoordinate(coordinate)) {
            throw new InvalidCoordinateException("Shot out of bounds: " + coordinate);
        }

        for (Ship ship : ships) {
            if (ship.occupies(coordinate)) {
                ship.hit(coordinate);
                hits.add(coordinate);

                notifyHit(coordinate, attackerName);

                if (!ship.isAlive()) {
                    notifySunk(ship.getName(), attackerName);
                }
                return true;
            }
        }

        misses.add(coordinate);
        notifyMiss(coordinate, attackerName);
        return false;
    }

    // Notification Helpers
    private void notifyHit(Coordinate c, String name) {
        for (GameObserver o : observers) o.onHit(c, name);
    }

    private void notifyMiss(Coordinate c, String name) {
        for (GameObserver o : observers) o.onMiss(c, name);
    }

    private void notifySunk(String shipName, String playerName) {
        for (GameObserver o : observers) o.onShipSunk(shipName, playerName);
    }

    // Old validation methods remain unchanged
    public boolean receiveAttack(Coordinate c) throws InvalidCoordinateException {
        // Overload for backward compatibility/testing if needed
        return receiveAttack(c, "Unknown");
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