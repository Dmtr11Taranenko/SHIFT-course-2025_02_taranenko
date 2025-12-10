package ru.cft.shift.controller;

import ru.cft.shift.model.GameModel;
import ru.cft.shift.view.GameType;
import ru.cft.shift.view.GameTypeListener;

import static ru.cft.shift.view.GameType.*;

/**
 * @author Dmitrii Taranenko
 */
public class SettingsController implements GameTypeListener {
    private final GameModel model;

    public SettingsController(GameModel model) {
        this.model = model;
    }

    @Override
    public void onGameTypeChanged(GameType gameType) {
        switch (gameType) {
            case NOVICE -> model.restartGame(NOVICE.getHeight(), NOVICE.getWidth(), NOVICE.getMines());
            case MEDIUM -> model.restartGame(MEDIUM.getHeight(), MEDIUM.getWidth(), MEDIUM.getMines());
            case EXPERT -> model.restartGame(EXPERT.getHeight(), EXPERT.getWidth(), EXPERT.getMines());
        }
    }
}
