package battleship.model.ship;

import battleship.model.Coordinate;
import battleship.model.Ship;
import battleship.model.ShipType;
import java.util.List;

public class Cruiser extends Ship {
    public Cruiser(List<Coordinate> coordinates) {
        super(coordinates);
    }

    public String getName() {
        return ShipType.CRUISER.getName();
    }

    public int getSize() {
        return ShipType.CRUISER.getSize();
    }
}