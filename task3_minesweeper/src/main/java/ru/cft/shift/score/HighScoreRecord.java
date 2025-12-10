package ru.cft.shift.score;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ru.cft.shift.score.RecordController.DATE_FORMATTER;

/**
 * @author Dmitrii Taranenko
 */
@Getter
public class HighScoreRecord {
    private static final DateTimeFormatter DISPLAY_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final String playerName;
    private final long timeInSeconds;
    private final String date;

    public HighScoreRecord(String playerName, long timeInSeconds, String date) {
        this.playerName = playerName;
        this.timeInSeconds = timeInSeconds;
        this.date = date;
    }

    public String getFormattedDate() {
        try {
            return LocalDateTime.parse(date, DATE_FORMATTER).format(DISPLAY_DATE_FORMATTER);
        } catch (Exception e) {
            return date;
        }
    }

    @Override
    public String toString() {
        return String.format("%s - %d seconds (%s)", playerName, timeInSeconds, getFormattedDate());
    }
}
