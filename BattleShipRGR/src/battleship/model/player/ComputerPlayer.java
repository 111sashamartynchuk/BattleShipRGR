package battleship.model.player;

import battleship.model.Coordinate;
import battleship.model.Player;
import battleship.model.strategy.ShootingStrategy;

public class ComputerPlayer extends Player {
    private final ShootingStrategy strategy;

    public ComputerPlayer(String name, ShootingStrategy strategy) {
        super(name);
        this.strategy = strategy;
    }

    protected Coordinate getCoordinatesFromInput() {
        System.out.println(name + " is thinking...");
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Coordinate target = strategy.determineTarget(enemyBoard);
        System.out.println(name + " targets " + (char)('A' + target.getX()) + (target.getY() + 1));
        return target;
    }
}