package ru.cft.shift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Dmitrii Taranenko
 */
public class ConcurrencyService {
    private static final Logger LOG = LoggerFactory.getLogger(ConcurrencyService.class);

    private final Function function;
    private final long multiplicity;

    public ConcurrencyService(Function function, long multiplicity) {
        this.function = function;
        this.multiplicity = multiplicity;
    }

    public double calculateFunction(long N) {
        LOG.debug("Calculation for N is started = {}", N);

        try {
            int availableProcessors = Runtime.getRuntime().availableProcessors();
            int threadCount = Math.max(1, Math.min(availableProcessors, (int) (N / multiplicity)));
            LOG.debug("{} threads are used", threadCount);

            ExecutorService executor = Executors.newFixedThreadPool(threadCount);
            try {

                List<Future<Double>> futures = new ArrayList<>();
                long rangeSize = N / threadCount;

                for (int i = 0; i < threadCount; i++) {
                    long start = i * rangeSize + 1;
                    long end = (i == threadCount - 1) ? N : (i + 1) * rangeSize;

                    Callable<Double> task = () -> function.calculateRange(start, end);
                    futures.add(executor.submit(task));

                    LOG.info("Task has been created for the range [{}, {}]", start, end);
                }

                double totalSum = 0.0;
                for (Future<Double> future : futures) {
                    totalSum += future.get();
                }

                LOG.info("Calculations completed. Result: {}", totalSum);
                return totalSum;

            } finally {
                executor.shutdown();
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
                LOG.info("All threads are shutdown");
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
