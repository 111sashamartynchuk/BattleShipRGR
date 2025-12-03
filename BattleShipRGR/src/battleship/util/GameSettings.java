package battleship.util;

//Singleton Pattern (Creational).

public class GameSettings {
    private static GameSettings instance;

    // Default settings
    private int boardSize = 10;
    private boolean debugMode = false;

    private GameSettings() {
        // Private constructor prevents instantiation from other classes
    }

    public static synchronized GameSettings getInstance() {
        if (instance == null) {
            instance = new GameSettings();
        }
        return instance;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public boolean isDebugMode() {
        return debugMode;
    }
}