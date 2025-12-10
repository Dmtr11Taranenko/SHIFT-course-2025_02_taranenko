package ru.cft.shift.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * @author Dmitrii Taranenko
 */
public class InputFileReader {
    public static void readHighScoreFile(Path scoresFile, Properties properties) {

        try (InputStream input = Files.newInputStream(scoresFile)) {
            properties.load(input);
        } catch (IOException ignored) {
        }
    }
}
