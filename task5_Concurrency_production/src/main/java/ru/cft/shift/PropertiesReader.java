package ru.cft.shift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Dmitrii Taranenko
 */
public class PropertiesReader {
    private static final Logger LOG = LoggerFactory.getLogger(PropertiesReader.class);
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream inputStream = PropertiesReader.class.getClassLoader()
                .getResourceAsStream("setupProd.properties")) {
            if (inputStream == null) {
                LOG.error("Setup file not found");
                throw new RuntimeException("Setup file not found");
            }
            PROPERTIES.load(inputStream);

        } catch (IOException e) {
            LOG.error("Error loading settings", e);
            throw new RuntimeException(e);
        }
    }

    public static Config getConfig() {
        Config config = new Config();
        config.setSetup(getPropertiesAsMap());
        config.init();
        return config;
    }

    private static Map<String, String> getPropertiesAsMap() {
        Map<String, String> map = new HashMap<>();

        for (String key : PROPERTIES.stringPropertyNames()) {
            map.put(key, PROPERTIES.getProperty(key));
        }

        return map;
    }
}
