package battleship.observer;

import battleship.model.Coordinate;

public interface GameObserver {
    void onHit(Coordinate c, String playerName);
    void onMiss(Coordinate c, String playerName);
    void onShipSunk(String shipName, String playerName);
}