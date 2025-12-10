package ru.cft.shift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.utils.cli.CliService;
import ru.cft.shift.utils.dto.CliDto;

/**
 * @author Dmitrii Taranenko
 */
public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    private final String inputFile;
    private final String outputFile;

    public AppConfig(CliDto cliDto) {
        if (cliDto.help()) {
            logger.info("Print help message");
            CliService.printHelp();
        }

        if (cliDto.inputFile() == null || cliDto.inputFile().trim().isEmpty()) {
            throw new IllegalArgumentException("Input file is required");
        }

        this.inputFile = cliDto.inputFile().trim();
        this.outputFile = cliDto.hasOutputFile() ? cliDto.outputFile().trim() : null;
    }

    public String getInputFile() {
        return inputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }
}

