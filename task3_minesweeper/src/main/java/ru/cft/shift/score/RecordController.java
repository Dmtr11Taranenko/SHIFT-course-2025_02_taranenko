package ru.cft.shift.score;

import lombok.Getter;
import ru.cft.shift.model.GameModel;
import ru.cft.shift.util.io.InputFileReader;
import ru.cft.shift.util.io.OutputFileWriter;
import ru.cft.shift.view.GameType;
import ru.cft.shift.view.RecordNameListener;
import ru.cft.shift.view.window.HighScoresWindow;
import ru.cft.shift.view.window.MainWindow;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

/**
 * @author Dmitrii Taranenko
 */
@Getter
public class RecordController implements RecordNameListener {
    private final GameModel gameModel;
    private final Properties properties;
    private final HighScoresWindow scoresWindow;
    private long currentTime;
    private GameType currentGameType;

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public RecordController(GameModel gameModel, MainWindow mainWindow) {
        this.gameModel = gameModel;
        this.properties = new Properties();
        this.scoresWindow = new HighScoresWindow(mainWindow);
        loadHighScores();
    }

    @Override
    public void onRecordNameEntered(String name) {
        if (currentGameType == null) return;

        String recordValue = String.format("%s,%d,%s",
                name.trim(),
                currentTime,
                LocalDateTime.now().format(DATE_FORMATTER)
        );

        properties.setProperty(currentGameType.name(), recordValue);
        saveHighScores();
    }

    public boolean checkRecords(long time) {
        this.currentTime = time;
        this.currentGameType = GameType.getTypeByMines(gameModel.getField().getTotalMine());
        return currentGameType != null && isHighScore(currentGameType, time);
    }

    private boolean isHighScore(GameType gameType, long time) {
        String currentRecord = properties.getProperty(gameType.name());

        if (currentRecord == null || currentRecord.isEmpty()) {
            return true;
        }

        try {
            String[] parts = currentRecord.split(",", 3);
            return parts.length < 2 || time < Long.parseLong(parts[1]);
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private void loadHighScores() {
        Path scoresFile = getScoresFile();

        if (!Files.exists(scoresFile)) {
            initializeEmptyProperties();
            saveHighScores();
            return;
        }

        InputFileReader.readHighScoreFile(scoresFile, properties);
        initializeEmptyProperties();

        loadRecordsIntoScoresWindow();
    }

    private void saveHighScores() {
        Path scoresFile = getScoresFile();

        OutputFileWriter.writeHighScoreFile(scoresFile, properties);

        loadRecordsIntoScoresWindow();
    }

    private Path getScoresFile() {
        return Path.of("task3_minesweeper/src/main/resources/highScores.properties");
    }

    private void initializeEmptyProperties() {
        for (GameType gameType : GameType.values()) {
            properties.putIfAbsent(gameType.name(), "");
        }
    }

    private HighScoreRecord getHighScore(GameType gameType) {
        String recordValue = properties.getProperty(gameType.name());

        if (recordValue == null || recordValue.isEmpty()) {
            return null;
        }

        try {
            String[] parts = recordValue.split(",", 3);
            if (parts.length < 3) return null;

            return new HighScoreRecord(parts[0], Long.parseLong(parts[1]), parts[2]);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void loadRecordsIntoScoresWindow() {
        HighScoreRecord hsr;

        for (GameType type : GameType.values()) {
            hsr = getHighScore(type);
            if (hsr != null)
                switch (type) {
                    case NOVICE -> scoresWindow.setNoviceRecord(hsr.getPlayerName(), (int) hsr.getTimeInSeconds());
                    case MEDIUM -> scoresWindow.setMediumRecord(hsr.getPlayerName(), (int) hsr.getTimeInSeconds());
                    case EXPERT -> scoresWindow.setExpertRecord(hsr.getPlayerName(), (int) hsr.getTimeInSeconds());
                }
        }
    }
}