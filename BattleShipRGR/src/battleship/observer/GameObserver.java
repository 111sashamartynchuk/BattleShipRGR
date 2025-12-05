package battleship.observer;

import battleship.model.Coordinate;

// Interface for the Observer Pattern.
// Allows decoupling the Model (Board) from the View (Console).

public interface GameObserver {
    void onHit(Coordinate c, String playerName);
    void onMiss(Coordinate c, String playerName);
    void onShipSunk(String shipName, String playerName);
}