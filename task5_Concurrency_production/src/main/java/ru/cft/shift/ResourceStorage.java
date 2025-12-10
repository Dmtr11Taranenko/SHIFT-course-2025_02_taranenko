package ru.cft.shift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Dmitrii Taranenko
 */
public class ResourceStorage {
    private static final Logger LOG = LoggerFactory.getLogger(ResourceStorage.class);

    private final Queue<Resource> storage = new LinkedList<>();
    private final int storageSize;

    public ResourceStorage(int storageSize) {
        this.storageSize = storageSize;
    }

    public synchronized void add(Resource resource, long threadId) throws InterruptedException {

        while (storage.size() >= storageSize) {
            LOG.debug("Producer thread {} is waiting", threadId);
            this.wait();
            LOG.debug("Producer thread {} is working", threadId);
        }
        storage.add(resource);
        LOG.info("Resource-{} has been delivered to the warehouse. {} resources in warehouse",
                resource.getId(), storage.size());

        this.notifyAll();

    }

    public synchronized Resource take(long threadId) throws InterruptedException {

        while (storage.isEmpty()) {
            LOG.debug("Consumer thread {} is waiting", threadId);
            this.wait();
            LOG.debug("Consumer thread {} is working", threadId);
        }

        this.notifyAll();

        Resource resource = storage.poll();

        if (resource != null) {
            LOG.info("Resource-{} was taken from warehouse. {} resources left in warehouse",
                    resource.getId(), storage.size());
        }

        return resource;
    }
}
