package ru.cft.shift;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author Dmitrii Taranenko
 */
@Getter
@Setter
public class Config {

    private Map<String, String> setup;

    private int storageSize;
    private int producerCount;
    private long producerTime;
    private int consumerCount;
    private long consumerTime;

    public void init() {
        for (Map.Entry<String, String> entry : setup.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            switch (key) {
                case "storageSize" -> setStorageSize(Integer.parseInt(value));
                case "producerCount" -> setProducerCount(Integer.parseInt(value));
                case "producerTime" -> setProducerTime(Long.parseLong(value));
                case "consumerCount" -> setConsumerCount(Integer.parseInt(value));
                case "consumerTime" -> setConsumerTime(Long.parseLong(value));
            }
        }
    }
}
