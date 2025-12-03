package battleship.model.strategy;

import battleship.model.Board;
import battleship.model.Coordinate;

public interface ShootingStrategy {
    Coordinate determineTarget(Board enemyBoard);
}