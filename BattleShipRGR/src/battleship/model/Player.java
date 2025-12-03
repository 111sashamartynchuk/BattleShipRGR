package battleship.model;

public abstract class Player {
    protected String name;
    protected Board myBoard;
    protected Board enemyBoard;

    public Player(String name) {
        this.name = name;
        this.myBoard = new Board();
    }

    //Template Method (Behavioral Pattern). Defines the skeleton of a move.
    public final Coordinate performMove() {
        // Common logic can go here (e.g., logging, timer start)
        // System.out.println(name + " is preparing to fire...");

        // The specific step delegated to subclasses
        Coordinate target = getCoordinatesFromInput();

        return target;
    }

    //The primitive operation that subclasses must implement. Was previously named 'makeMove'.

    protected abstract Coordinate getCoordinatesFromInput();

    public void setEnemyBoard(Board enemyBoard) {
        this.enemyBoard = enemyBoard;
    }

    public Board getMyBoard() {
        return myBoard;
    }

    public Board getEnemyBoard() {
        return enemyBoard;
    }

    public String getName() {
        return name;
    }
}