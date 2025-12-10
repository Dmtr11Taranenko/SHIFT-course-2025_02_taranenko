package ru.cft.shift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.utils.dto.CliDto;
import ru.cft.shift.utils.cli.CliService;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            CliDto cliDto = CliService.parseCmdArgs(args);

            Application app = new Application();
            app.run(cliDto);

        } catch (Exception e) {
            logger.error("Fatal error: {}", e.getMessage(), e);
            System.exit(1);
        }
    }
}