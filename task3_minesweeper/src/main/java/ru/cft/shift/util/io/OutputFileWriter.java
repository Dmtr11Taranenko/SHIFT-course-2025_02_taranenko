package ru.cft.shift.util.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * @author Dmitrii Taranenko
 */
public class OutputFileWriter {
    private static final Logger LOG = LoggerFactory.getLogger(OutputFileWriter.class);

    public static void writeHighScoreFile(Path scoresFile, Properties properties) {
        try {
            if (!Files.exists(scoresFile)) {
                Files.createFile(scoresFile);
            }

            try (OutputStream output = Files.newOutputStream(scoresFile)) {
                properties.store(output, "High score table");
            }
        } catch (IOException e) {
            LOG.error("Failed to save high scores: {}", e.getMessage());
        }
    }
}
