package battleship.model;
public enum ShipType {
    BATTLESHIP(4, "Battleship"),
    CRUISER(3, "Cruiser"),
    DESTROYER(2, "Destroyer"),
    SUBMARINE(1, "Submarine");

    private final int size;
    private final String name;

    ShipType(int size, String name) {
        this.size = size;
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }
}