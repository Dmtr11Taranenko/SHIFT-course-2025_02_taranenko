package ru.cft.shift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * @author Dmitrii Taranenko
 */
public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOG.debug("Program has started working. Enter the input value in range less than 4,000,000,000");
        try {
            Scanner scanner = new Scanner(System.in);
            if (!scanner.hasNextLong()) {
                return;
            }

            long N = scanner.nextLong();

            if (N > 4_000_000_000L) {
                throw new RuntimeException("Range of expected input number: 0 - 4,000,000,000");
            }

            Function function = new InverseSquaresFunc();
            ConcurrencyService concurrencyService = new ConcurrencyService(function, 1_000_000);
            double result = concurrencyService.calculateFunction(N);

            System.out.println(result);
            LOG.info("Program has successfully completed");
        } catch (RuntimeException e) {
            LOG.error("Fatal error: {}", e.getMessage(), e);
            System.exit(1);
        }

    }
}
