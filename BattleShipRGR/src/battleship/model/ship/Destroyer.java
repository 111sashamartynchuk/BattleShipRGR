package battleship.model.ship;

import battleship.model.Coordinate;
import battleship.model.Ship;
import battleship.model.ShipType;
import java.util.List;

public class Destroyer extends Ship {
    public Destroyer(List<Coordinate> coordinates) {
        super(coordinates);
    }

    public String getName() {
        return ShipType.DESTROYER.getName();
    }

    public int getSize() {
        return ShipType.DESTROYER.getSize();
    }
}