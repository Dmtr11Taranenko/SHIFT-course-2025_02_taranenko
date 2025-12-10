package ru.cft.shift.app;

import ru.cft.shift.controller.GameController;
import ru.cft.shift.controller.SettingsController;
import ru.cft.shift.model.GameModel;
import ru.cft.shift.view.GameType;
import ru.cft.shift.view.updater.MainWindowUpdater;
import ru.cft.shift.view.window.HighScoresWindow;
import ru.cft.shift.view.window.MainWindow;
import ru.cft.shift.view.window.SettingsWindow;

public class Application {
    public static void main(String[] args) {
        int initialWidth = GameType.NOVICE.getWidth();
        int initialHeight = GameType.NOVICE.getHeight();
        int initialMines = GameType.NOVICE.getMines();

        GameModel model = new GameModel(initialHeight, initialWidth, initialMines);

        GameController gameController = new GameController(model);
        SettingsController settingsController = new SettingsController(model);

        MainWindow mainWindow = new MainWindow();
        SettingsWindow settingsWindow = new SettingsWindow(mainWindow);
        MainWindowUpdater mainWindowUpdater = new MainWindowUpdater(gameController, mainWindow, model);
        HighScoresWindow highScoresWindow = mainWindowUpdater.getRecordController().getScoresWindow();

        model.addListener(mainWindowUpdater);
        mainWindow.setCellListener(gameController);
        settingsWindow.setGameTypeListener(settingsController);

        mainWindow.setNewGameMenuAction(e -> {
            gameController.startNewGame(model.getField().getHeight(), model.getField().getWidth(), model.getField().getTotalMine());
        });

        mainWindow.setSettingsMenuAction(e -> {
            settingsWindow.setVisible(true);
        });

        mainWindow.setHighScoresMenuAction(e -> {
            highScoresWindow.setVisible(true);
        });

        mainWindow.setExitMenuAction(e -> {
            System.exit(0);
        });

        gameController.startNewGame(initialHeight, initialWidth, initialMines);
    }
}