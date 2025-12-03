package battleship.model;

public abstract class Player {
    protected String name;
    protected Board myBoard;
    protected Board enemyBoard; // Це поле має бути!

    public Player(String name) {
        this.name = name;
        this.myBoard = new Board();
    }

    public abstract Coordinate makeMove();

    // GETTERS and SETTERS

    public void setEnemyBoard(Board enemyBoard) {
        this.enemyBoard = enemyBoard;
    }

    public Board getEnemyBoard() {
        return enemyBoard;
    }

    public Board getMyBoard() {
        return myBoard;
    }

    public String getName() {
        return name;
    }
}