package battleship.observer;

import battleship.model.Coordinate;

public class ConsoleObserver implements GameObserver {

    public void onHit(Coordinate c, String playerName) {
        System.out.println(">>> " + playerName + " hits at " + format(c) + "!");
    }

    public void onMiss(Coordinate c, String playerName) {
        System.out.println(">>> " + playerName + " missed at " + format(c) + ".");
    }

    public void onShipSunk(String shipName, String playerName) {
        System.out.println(">>> " + playerName + " sank a " + shipName + "!");
    }

    private String format(Coordinate c) {
        return (char)('A' + c.getX()) + "" + (c.getY() + 1);
    }
}