package ru.cft.shift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            Config config = PropertiesReader.getConfig();

            final int storageSize = config.getStorageSize();
            final int producerCount = config.getProducerCount();
            final long producerTime = config.getProducerTime();
            final int consumerCount = config.getConsumerCount();
            final long consumerTime = config.getConsumerTime();

            ResourceStorage resourceStorage = new ResourceStorage(storageSize);

            for (int i = 0; i < producerCount; i++) {
                new Thread(new ResourceProducer(resourceStorage, producerTime)).start();
            }

            for (int i = 0; i < consumerCount; i++) {
                new Thread(new ResourceConsumer(resourceStorage, consumerTime)).start();
            }

        } catch (Exception e) {
            LOG.error("Fatal error: {}", e.getMessage(), e);
            System.exit(1);
        }
    }
}