package ru.cft.shift.model;

import lombok.Getter;
import ru.cft.shift.model.component.Cell;
import ru.cft.shift.model.component.Field;
import ru.cft.shift.model.component.Miner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author Dmitrii Taranenko
 */
@Getter
public class GameModel implements ObservableModel {

    private final List<GameModelObserver> listeners = new ArrayList<>();
    private final Miner miner;
    private GameState gameState;
    private Field field;
    private int remainingMines;
    private int openedCells;

    public GameModel(int height, int width, int totalMines) {
        this.field = new Field(height, width, totalMines);
        this.miner = new Miner();
        this.remainingMines = totalMines;
        this.gameState = GameState.STOP;
        this.openedCells = 0;
    }

    public void openCell(int x, int y) {
        Cell cell = field.getCell(x, y);

        if (cell.isOpen() || cell.isFlagged()) return;

        cell.setOpen(true);
        openedCells++;

        if (gameState != GameState.PLAYING) {
            gameState = GameState.PLAYING;
            miner.miningField(field);

            notifyListeners(new GameModelEvent(GameEventType.GAME_STARTED));
        }

        notifyListeners(new GameModelEvent(GameEventType.CELL_OPENED,
                new int[]{x, y}));

        if (cell.isMined()) {
            gameState = GameState.LOSE;

            notifyListeners(new GameModelEvent(GameEventType.GAME_LOST));
            return;
        }

        if (openedCells == (field.getWidth() * field.getHeight() - field.getTotalMine())) {
            gameState = GameState.WON;

            notifyListeners(new GameModelEvent(GameEventType.GAME_WON));
            return;
        }

        if (cell.getMineCount() == 0) {
            openNeighbors(x, y);
        }
    }

    public void openAccorDatedCells(int x, int y) {
        Cell cell = field.getCell(x, y);

        if (!cell.isOpen()) return;

        AtomicInteger flagCount = new AtomicInteger();

        field.checkCellsNeighbors(x, y, cell1 -> {
            if (cell1.isFlagged()) {
                flagCount.getAndIncrement();
            }
        });

        if (flagCount.get() == cell.getMineCount()) {

            field.checkCellsNeighbors(x, y, cell1 -> {
                if (!cell1.isFlagged() && !cell1.isOpen()) {
                    openCell(cell1.getXCoords(), cell1.getYCoords());
                }
            });
        }
    }

    public void flaggedCell(int x, int y) {
        Cell cell = field.getCell(x, y);

        if (cell.isOpen()) return;

        boolean newFlagState = !cell.isFlagged();
        cell.setFlagged(newFlagState);

        if (newFlagState) {
            remainingMines--;
        } else {
            remainingMines++;
        }

        notifyListeners(new GameModelEvent(GameEventType.CELL_FLAGGED,
                new int[]{x, y}));

        notifyListeners(new GameModelEvent(GameEventType.MINE_COUNT_CHANGED,
                new int[]{remainingMines}));
    }

    public void restartGame(int height, int width, int totalMines) {
        this.field = new Field(height, width, totalMines);
        this.remainingMines = totalMines;
        this.gameState = GameState.STOP;
        this.openedCells = 0;

        notifyListeners(new GameModelEvent(GameEventType.GAME_STARTED));
    }

    @Override
    public void addListener(GameModelObserver listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(GameModelObserver listener) {
        listeners.remove(listener);
    }

    @Override
    public void notifyListeners(GameModelEvent event) {
        for (GameModelObserver listener : new ArrayList<>(listeners)) {
            switch (event.getEventType()) {
                case GAME_STARTED -> listener.onGameStarted(gameState);
                case CELL_OPENED -> {
                    int[] coords = event.getData();
                    listener.onCellOpened(coords[0], coords[1]);
                }
                case CELL_FLAGGED -> {
                    int[] data = event.getData();
                    listener.onCellFlagged(data[0], data[1]);
                }
                case GAME_WON -> listener.onGameWon();
                case GAME_LOST -> listener.onGameLost();
                case MINE_COUNT_CHANGED -> listener.onMineCountChanged(event.getData()[0]);
            }
        }
    }

    private void openNeighbors(int x, int y) {
        field.checkCellsNeighbors(x, y, (cell -> {
            int neighborX = cell.getXCoords();
            int neighborY = cell.getYCoords();
            if (!field.getCell(neighborX, neighborY).isMined())
                openCell(neighborX, neighborY);
        }));
    }
}
