package battleship;
import battleship.model.Board;
import battleship.model.Coordinate;

public class Main {
    public static void main(String[] args) {
        System.out.println("Initializing Battleship Models...");

        Board board = new Board();
        Coordinate c = new Coordinate(1, 1);

        System.out.println("Board created with size: " + Board.SIZE);
        System.out.println("Coordinate test: " + c);

        System.out.println("Models are working correctly.");
    }
}