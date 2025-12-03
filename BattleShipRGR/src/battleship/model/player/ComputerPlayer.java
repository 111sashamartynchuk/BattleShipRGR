package battleship.model.player;

import battleship.model.Coordinate;
import battleship.model.Player;
import battleship.model.strategy.ShootingStrategy;

public class ComputerPlayer extends Player {
    private final ShootingStrategy strategy;

    // Dependency Injection - The strategy is passed into the constructor. This makes the ComputerPlayer flexible (Open/Closed Principle).
    public ComputerPlayer(String name, ShootingStrategy strategy) {
        super(name);
        this.strategy = strategy;
    }

    public Coordinate makeMove() {
        System.out.println(name + " is calculating target...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Coordinate target = strategy.determineTarget(enemyBoard);
        System.out.println(name + " fires at " + formatCoordinate(target));
        return target;
    }

    private String formatCoordinate(Coordinate c) {
        return (char)('A' + c.getX()) + "" + (c.getY() + 1);
    }
}