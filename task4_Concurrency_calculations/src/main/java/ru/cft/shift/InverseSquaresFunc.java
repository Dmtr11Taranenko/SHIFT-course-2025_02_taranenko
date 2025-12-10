package ru.cft.shift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dmitrii Taranenko
 */
public class InverseSquaresFunc implements Function {
    private static final Logger LOG = LoggerFactory.getLogger(InverseSquaresFunc.class);
    private static final double SOLUTION = Math.PI * Math.PI / 6.0;

    @Override
    public double calculateRange(long start, long end) {
        double sum = 0.0;

        for (long i = start; i <= end; i++) {
            double term = 1.0 / (i * i);

            sum += term;
        }

        LOG.info("Calculated range [{}, {}]: {}", start, end, sum);
        return sum;
    }

    public static double calculateDifference(double result) {
        double difference = SOLUTION - result;
        LOG.info("Difference with exact result: {}", difference);
        return difference;
    }
}