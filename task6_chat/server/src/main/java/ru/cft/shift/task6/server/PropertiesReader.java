package ru.cft.shift.task6.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Dmitrii Taranenko
 */
public class PropertiesReader {
    private static final Logger LOG = LoggerFactory.getLogger(PropertiesReader.class);

    private final Properties PROPERTIES = new Properties();

    public PropertiesReader(String propertiesFile) {
        try (InputStream inputStream = PropertiesReader.class.getClassLoader()
                .getResourceAsStream(propertiesFile)) {
            if (inputStream == null) {
                LOG.error("Setup file not found");
                throw new RuntimeException("Setup file not found");
            }
            PROPERTIES.load(inputStream);

        } catch (IOException e) {
            LOG.error("Error loading settings", e);
        }
    }

    public Properties getProperties() {
        return PROPERTIES;
    }
}
