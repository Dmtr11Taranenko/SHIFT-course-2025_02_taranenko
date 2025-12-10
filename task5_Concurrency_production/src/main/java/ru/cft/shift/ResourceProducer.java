package ru.cft.shift;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dmitrii Taranenko
 */
@Getter
public class ResourceProducer implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(ResourceProducer.class);

    private final ResourceStorage resourceStorage;
    private final long producerTime;
    private final long id;

    public ResourceProducer(ResourceStorage resourceStorage, long producerTime) {
        this.id = NextThreadID.getAndIncrementNextId();
        this.resourceStorage = resourceStorage;
        this.producerTime = producerTime;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(producerTime);

                Resource resource = new Resource();
                LOG.debug("Producer thread {} delivered resource-{} to warehouse",
                        this.getId(), resource.getId());
                resourceStorage.add(resource, this.getId());

            } catch (InterruptedException ignored) {
                LOG.info("Producer thread {} is interrupted", this.getId());
                break;
            }
        }
    }
}
