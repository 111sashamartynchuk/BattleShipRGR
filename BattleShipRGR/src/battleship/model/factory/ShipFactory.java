package battleship.model.factory;

import battleship.model.Coordinate;
import battleship.model.Ship;
import battleship.model.ShipType;
import java.util.List;

public interface ShipFactory {
    Ship createShip(ShipType type, List<Coordinate> coordinates);
}