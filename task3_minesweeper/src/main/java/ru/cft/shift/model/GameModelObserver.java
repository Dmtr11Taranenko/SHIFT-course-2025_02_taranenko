package ru.cft.shift.model;

/**
 * @author Dmitrii Taranenko
 */
public interface GameModelObserver {

    void onGameStarted(GameState gameState);

    void onCellOpened(int x, int y);

    void onCellFlagged(int x, int y);

    void onGameWon();

    void onGameLost();

    void onMineCountChanged(int remainingMines);
}
