package ru.cft.shift;

import java.io.*;
import java.util.Scanner;

/**
 * @author Dmitrii Taranenko
 * @Note: Генератор таблицы умножения с поддержкой разного размера и форматированного вывода
 */
public final class MultiplicationTablePrinter {
    private static final int MIN_SIZE = 1;
    private static final int MAX_SIZE = 32;
    private static final String SIZE_PROMPT = "Укажите размер таблицы (%d-%d): ";
    private static final String SIZE_ERROR = "Размер должен быть от %d до %d.";
    private static final String INPUT_ERROR = "Введите целое число.";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = readTableSize(scanner);

        PrintWriter writer = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(System.out)), false);

        printMultiplicationTable(size, writer);
        writer.flush();
    }

    /**
     * @Note: Читает и валидирует размер таблицы с обработкой некорректного ввода
     */
    private static int readTableSize(Scanner scanner) {
        while (true) {
            System.out.printf(SIZE_PROMPT, MIN_SIZE, MAX_SIZE);

            if (!scanner.hasNextInt()) {
                System.out.println(INPUT_ERROR);
                scanner.next();
                continue;
            }

            int size = scanner.nextInt();
            if (size >= MIN_SIZE && size <= MAX_SIZE) {
                return size;
            }

            System.out.printf(SIZE_ERROR, MIN_SIZE, MAX_SIZE);
        }
    }

    /**
     * @Note: Выводит форматированную таблицу умножения с динамическим выравниванием
     */
    private static void printMultiplicationTable(int size, PrintWriter printWriter) {
        if (size < MIN_SIZE) {
            return;
        }

        TableFormatter formatter = new TableFormatter(size, printWriter);
        formatter.printHeader();
        formatter.printBody();
    }

    /**
     * @Note: Внутренний класс для форматирования таблицы
     */
    private static class TableFormatter {
        private final int size;
        private final int firstColumnWidth;
        private final int cellWidth;
        private final String separator;
        private final String firstCellFormat;
        private final String regularCellFormat;
        private final String lastCellFormat;
        private final PrintWriter writer;

        TableFormatter(int size, PrintWriter writer) {
            this.size = size;
            this.firstColumnWidth = calculateWidth(size);
            this.cellWidth = calculateWidth(size * size);
            this.separator = buildSeparator();
            this.firstCellFormat = "%" + firstColumnWidth + "s|";
            this.regularCellFormat = "%" + cellWidth + "s|";
            this.lastCellFormat = "%" + cellWidth + "s";
            this.writer = writer;
        }

        void printHeader() {
            StringBuilder row = new StringBuilder(calculateRowCapacity());
            row.append(String.format(firstCellFormat, ""));

            for (int i = 1; i < size; i++) {
                row.append(String.format(regularCellFormat, i));
            }

            row.append(String.format(lastCellFormat, size)).append("\n");
            row.append(separator);

            writer.println(row);
        }

        void printBody() {
            StringBuilder row = new StringBuilder(calculateRowCapacity());
            for (int i = 1; i <= size; i++) {
                row.append(String.format(firstCellFormat, i));

                for (int j = 1; j < size; j++) {
                    row.append(String.format(regularCellFormat, i * j));
                }

                row.append(String.format(lastCellFormat, i * size)).append("\n");
                row.append(separator);

                writer.println(row);
                row.setLength(0);
            }
        }

        private String buildSeparator() {
            return buildLine(firstColumnWidth, "-", "+")
                    .append(
                        new StringBuilder()
                                .repeat(buildLine(cellWidth, "-", "+"), size - 1))
                                .append(
                                    buildLine(cellWidth, "-", "")).toString();
        }

        private StringBuilder buildLine(int width, String symbol, String separator) {
            return new StringBuilder().repeat(symbol, width).append(separator);
        }

        private int calculateWidth(int number) {
            return String.valueOf(number).length();
        }

        private int calculateRowCapacity() {
            return firstColumnWidth + 1 +
                    (cellWidth + 1) * (size - 1) +
                    cellWidth +
                    separator.length() + 1;
        }
    }
}