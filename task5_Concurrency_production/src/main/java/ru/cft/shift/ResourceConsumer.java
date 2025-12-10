package ru.cft.shift;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dmitrii Taranenko
 */
@Getter
public class ResourceConsumer implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(ResourceConsumer.class);

    private final ResourceStorage resourceStorage;
    private final long consumerTime;
    private final long id;

    public ResourceConsumer(ResourceStorage resourceStorage, long consumerTime) {
        this.id = NextThreadID.getAndIncrementNextId();
        this.resourceStorage = resourceStorage;
        this.consumerTime = consumerTime;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(consumerTime);
                Resource resource = resourceStorage.take(this.getId());

                if (resource != null) {
                    LOG.debug("Consumer thread {} took resource-{} from warehouse",
                            this.getId(), resource.getId());
                }

            } catch (InterruptedException ignored) {
                LOG.info("Consumer thread {} is interrupted", this.getId());
                break;
            }
        }
    }
}
