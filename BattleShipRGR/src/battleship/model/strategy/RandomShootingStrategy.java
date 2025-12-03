package battleship.model.strategy;

import battleship.model.Board;
import battleship.model.Coordinate;
import java.util.Random;

public class RandomShootingStrategy implements ShootingStrategy {
    private final Random random = new Random();

    public Coordinate determineTarget(Board enemyBoard) {
        int x, y;
        Coordinate target;

        do {
            x = random.nextInt(Board.SIZE);
            y = random.nextInt(Board.SIZE);
            target = new Coordinate(x, y);
        } while (isAlreadyShot(enemyBoard, target));

        return target;
    }

    private boolean isAlreadyShot(Board board, Coordinate c) {
        return board.getMisses().contains(c) || board.getHits().contains(c);
    }
}