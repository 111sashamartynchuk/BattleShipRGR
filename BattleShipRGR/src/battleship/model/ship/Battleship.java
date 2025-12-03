package battleship.model.ship;

import battleship.model.Coordinate;
import battleship.model.Ship;
import battleship.model.ShipType;
import java.util.List;

public class Battleship extends Ship {
    public Battleship(List<Coordinate> coordinates) {
        super(coordinates);
    }

    public String getName() {
        return ShipType.BATTLESHIP.getName();
    }

    public int getSize() {
        return ShipType.BATTLESHIP.getSize();
    }
}