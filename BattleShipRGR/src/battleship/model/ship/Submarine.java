package battleship.model.ship;

import battleship.model.Coordinate;
import battleship.model.Ship;
import battleship.model.ShipType;
import java.util.List;

public class Submarine extends Ship {
    public Submarine(List<Coordinate> coordinates) {
        super(coordinates);
    }

    public String getName() {
        return ShipType.SUBMARINE.getName();
    }

    public int getSize() {
        return ShipType.SUBMARINE.getSize();
    }
}