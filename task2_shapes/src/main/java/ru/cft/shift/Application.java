package ru.cft.shift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.core.IShape;
import ru.cft.shift.core.dto.ShapeDto;
import ru.cft.shift.core.services.ShapeFactory;
import ru.cft.shift.utils.dto.CliDto;
import ru.cft.shift.utils.io.InputFileReader;
import ru.cft.shift.utils.io.OutputFileWriter;

/**
 * @author Dmitrii Taranenko
 */
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private static final String UNITS = "мм";

    public void run(CliDto cliDto) {
        try {
            AppConfig appConfig = new AppConfig(cliDto);

            logger.info("Starting application processing");
            logger.debug("Input: {}, Output: {}",
                    appConfig.getInputFile(),
                    appConfig.getOutputFile() != null ? appConfig.getOutputFile() : "console");

            ShapeDto shapeDto = InputFileReader.readShapeData(appConfig.getInputFile());

            IShape shape = ShapeFactory.createShape(shapeDto.type(), shapeDto.parameters());

            OutputFileWriter.writeResult(shape, appConfig.getOutputFile(), UNITS);

            logger.info("Application completed successfully");

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
