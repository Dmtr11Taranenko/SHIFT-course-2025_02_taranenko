package ru.cft.shift.controller;

import ru.cft.shift.model.GameModel;
import ru.cft.shift.model.GameState;
import ru.cft.shift.view.ButtonType;
import ru.cft.shift.view.CellEventListener;

/**
 * @author Dmitrii Taranenko
 */
public class GameController implements CellEventListener {
    private final GameModel model;
    private boolean isGameActive;

    public GameController(GameModel model) {
        this.model = model;
        this.isGameActive = false;
    }

    public void startNewGame(int height, int width, int mines) {
        model.restartGame(height, width, mines);
        isGameActive = true;
    }

    @Override
    public void onMouseClick(int x, int y, ButtonType buttonType) {
        if (!isGameActive && model.getGameState() == GameState.STOP) {
            return;
        }

        switch (buttonType) {
            case LEFT_BUTTON -> model.openCell(x, y);
            case RIGHT_BUTTON -> model.flaggedCell(x, y);
            case MIDDLE_BUTTON -> model.openAccorDatedCells(x, y);
        }
    }
}
