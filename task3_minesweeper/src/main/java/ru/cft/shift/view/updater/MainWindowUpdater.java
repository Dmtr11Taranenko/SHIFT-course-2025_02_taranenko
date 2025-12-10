package ru.cft.shift.view.updater;

import lombok.Getter;
import ru.cft.shift.controller.GameController;
import ru.cft.shift.model.GameModel;
import ru.cft.shift.model.GameModelObserver;
import ru.cft.shift.model.GameState;
import ru.cft.shift.model.component.Cell;
import ru.cft.shift.score.RecordController;
import ru.cft.shift.util.Timer;
import ru.cft.shift.view.GameImage;
import ru.cft.shift.view.window.LoseWindow;
import ru.cft.shift.view.window.MainWindow;
import ru.cft.shift.view.window.RecordsWindow;
import ru.cft.shift.view.window.WinWindow;

/**
 * @author Dmitrii Taranenko
 */
@Getter
public class MainWindowUpdater implements GameModelObserver {
    private final RecordController recordController;
    private final GameController gameController;
    private final MainWindow mainWindow;
    private final GameModel model;
    private final Timer timer;

    public MainWindowUpdater(GameController gameController, MainWindow mainWindow, GameModel model) {
        this.gameController = gameController;
        this.mainWindow = mainWindow;
        this.model = model;
        this.timer = new Timer(mainWindow::setTimerValue);
        this.recordController = new RecordController(this.model, this.mainWindow);
    }

    @Override
    public void onGameStarted(GameState gameState) {
        if (gameState.equals(GameState.PLAYING))
            timer.start();
        mainWindow.createGameField(model.getField().getHeight(), model.getField().getWidth());
        mainWindow.setBombsCount(model.getField().getTotalMine());
        mainWindow.setTimerValue(0);
        mainWindow.setVisible(true);
        resetAllCells();
    }

    @Override
    public void onCellOpened(int x, int y) {
        Cell cell = model.getField().getCell(x, y);
        updateCellImage(x, y, cell);
    }

    @Override
    public void onCellFlagged(int x, int y) {
        Cell cell = model.getField().getCell(x, y);

        if (cell.isFlagged()) {
            mainWindow.setCellImage(x, y, GameImage.MARKED);
        } else {
            mainWindow.setCellImage(x, y, GameImage.CLOSED);
        }
    }

    @Override
    public void onGameWon() {
        timer.stop();
        showAllMinesAsFlags();
        checkRecordValue();
        showWinMessage();
    }

    @Override
    public void onGameLost() {
        timer.stop();
        showAllMines();
        showLossMessage();
    }

    @Override
    public void onMineCountChanged(int remainingMines) {
        mainWindow.setBombsCount(remainingMines);
    }

    private void resetAllCells() {
        for (int x = 0; x < model.getField().getWidth(); x++) {
            for (int y = 0; y < model.getField().getHeight(); y++) {
                mainWindow.setCellImage(x, y, GameImage.CLOSED);
            }
        }
    }

    private void updateCellImage(int x, int y, Cell cell) {
        if (cell.isMined()) {
            mainWindow.setCellImage(x, y, GameImage.BOMB);
            return;
        }
        int mineCount = cell.getMineCount();
        GameImage image = getImageForMineCount(mineCount);
        mainWindow.setCellImage(x, y, image);
    }

    private GameImage getImageForMineCount(int mineCount) {
        return switch (mineCount) {
            case 1 -> GameImage.NUM_1;
            case 2 -> GameImage.NUM_2;
            case 3 -> GameImage.NUM_3;
            case 4 -> GameImage.NUM_4;
            case 5 -> GameImage.NUM_5;
            case 6 -> GameImage.NUM_6;
            case 7 -> GameImage.NUM_7;
            case 8 -> GameImage.NUM_8;
            default -> GameImage.EMPTY;
        };
    }

    private void showAllMines() {
        for (int x = 0; x < model.getField().getWidth(); x++) {
            for (int y = 0; y < model.getField().getHeight(); y++) {
                Cell cell = model.getField().getCell(x, y);
                if (cell.isMined()) {
                    mainWindow.setCellImage(x, y, GameImage.BOMB);
                }
            }
        }
    }

    private void showAllMinesAsFlags() {
        for (int x = 0; x < model.getField().getWidth(); x++) {
            for (int y = 0; y < model.getField().getHeight(); y++) {
                Cell cell = model.getField().getCell(x, y);
                if (cell.isMined()) {
                    mainWindow.setCellImage(x, y, GameImage.MARKED);
                }
            }
        }
    }

    private void showWinMessage() {
        WinWindow winWindow = new WinWindow(mainWindow);
        winWindow.setExitListener(e -> {
            System.exit(0);
        });
        winWindow.setNewGameListener(e -> {
            gameController.startNewGame(model.getField().getHeight(), model.getField().getWidth(), model.getField().getTotalMine());
        });
        winWindow.setVisible(true);
    }

    private void showLossMessage() {
        LoseWindow loseWindow = new LoseWindow(mainWindow);
        loseWindow.setExitListener(e -> {
            System.exit(0);
        });
        loseWindow.setNewGameListener(e -> {
            gameController.startNewGame(model.getField().getHeight(), model.getField().getWidth(), model.getField().getTotalMine());
        });
        loseWindow.setVisible(true);
    }

    private void checkRecordValue() {
        if (recordController.checkRecords(mainWindow.getTimerValue()))
            showRecordWindow();
    }

    private void showRecordWindow() {
        RecordsWindow recordsWindow = new RecordsWindow(mainWindow);
        recordsWindow.setNameListener(recordController);
        recordsWindow.setVisible(true);
    }
}
