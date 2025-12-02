package battleship.model;
import java.util.ArrayList;
import java.util.List;

public abstract class Ship {
    protected List<Coordinate> coordinates;
    protected List<Coordinate> hitCoordinates;
    protected boolean isSunk;

    public Ship(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
        this.hitCoordinates = new ArrayList<>();
        this.isSunk = false;
    }

    public abstract String getName();
    public abstract int getSize();

    public void hit(Coordinate coordinate) {
        if (coordinates.contains(coordinate) && !hitCoordinates.contains(coordinate)) {
            hitCoordinates.add(coordinate);
            checkIsSunk();
        }
    }

    private void checkIsSunk() {
        if (hitCoordinates.size() >= coordinates.size()) {
            isSunk = true;
        }
    }

    public boolean isAlive() {
        return !isSunk;
    }

    public boolean occupies(Coordinate coordinate) {
        return coordinates.contains(coordinate);
    }

    public List<Coordinate> getCoordinates() {
        return new ArrayList<>(coordinates);
    }
}