package battleship.model.factory;

import battleship.model.Coordinate;
import battleship.model.Ship;
import battleship.model.ShipType;
import battleship.model.ship.*;

import java.util.List;

public class StandardShipFactory implements ShipFactory {

    public Ship createShip(ShipType type, List<Coordinate> coordinates) {
        if (coordinates.size() != type.getSize()) {
            throw new IllegalArgumentException(
                    String.format("Invalid coordinates for %s. Expected %d, got %d.",
                            type.getName(), type.getSize(), coordinates.size())
            );
        }

        switch (type) {
            case BATTLESHIP: return new Battleship(coordinates);
            case CRUISER:    return new Cruiser(coordinates);
            case DESTROYER:  return new Destroyer(coordinates);
            case SUBMARINE:  return new Submarine(coordinates);
            default: throw new IllegalArgumentException("Unknown ship type: " + type);
        }
    }
}