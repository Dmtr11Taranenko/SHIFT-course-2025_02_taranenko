package ru.cft.shift.utils.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.core.IShape;

import java.io.*;

/**
 * @author Dmitrii Taranenko
 */
public class OutputFileWriter {
    private static final Logger logger = LoggerFactory.getLogger(OutputFileWriter.class);

    public static void writeResult(IShape shape, String outputPath, String units) throws IOException {
        logger.debug("Writing result to: {}", outputPath != null ? outputPath : "console");

        String result = shape.getInfo(new StringBuilder(), units).toString();

        PrintWriter writer = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(getOutputStream(outputPath))));

        writer.println(result);
        writer.flush();

        if (outputPath != null) {
            writer.close();
        }

        logger.info("Successfully wrote shape information");
    }

    private static OutputStream getOutputStream(String outputPath) throws FileNotFoundException {
        return outputPath != null ? new FileOutputStream(outputPath) : System.out;
    }
}
