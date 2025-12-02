package battleship.model;

public abstract class Player {
    protected String name;
    protected Board myBoard;
    protected Board enemyBoard;

    public Player(String name) {
        this.name = name;
        this.myBoard = new Board();
    }

    public void setEnemyBoard(Board enemyBoard) {
        this.enemyBoard = enemyBoard;
    }

    public Board getMyBoard() {
        return myBoard;
    }

    public String getName() {
        return name;
    }

    // головний метод, який буде відрізнятися у гравця та бота
    public abstract Coordinate makeMove();
}