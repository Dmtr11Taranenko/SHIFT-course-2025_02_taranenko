package ru.cft.shift.utils.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.core.dto.ShapeDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * @author Dmitrii Taranenko
 */
public class InputFileReader {
    private static final Logger logger = LoggerFactory.getLogger(InputFileReader.class);

    public static ShapeDto readShapeData(String filePath) throws IOException {
        logger.debug("Reading shape data from: {}", filePath);

        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            throw new IOException("Input file does not exist: " + filePath);
        }

        List<String> lines = Files.readAllLines(path);

        if (lines.stream()
                .filter(line -> !line.trim().isEmpty())
                .count() != 2) {
            throw new IllegalArgumentException("Input file must contain at least 2 lines");
        }

        String figureType = lines.get(0).trim();
        List<String> paramStrings = List.of(lines.get(1).trim().split("\\s+"));

        List<Double> parameters = parseParameters(paramStrings);
        ShapeDto shapeDto = new ShapeDto(figureType, parameters);
        shapeDto.validate();

        logger.info("Successfully read shape data - Type: {}, Parameters: {}",
                figureType, parameters.size());

        return shapeDto;
    }

    private static List<Double> parseParameters(List<String> paramStrings) {
        try {
            return paramStrings.stream()
                    .map(Double::parseDouble)
                    .filter(param -> {

                        if (param <= 0) {
                            throw new IllegalArgumentException("Parameter must be positive: " + param);
                        }

                        return true;
                    })
                    .toList();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid parameters: " + Arrays.toString(paramStrings.toArray()), e);
        }
    }
}