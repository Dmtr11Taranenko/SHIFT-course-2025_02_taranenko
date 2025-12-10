package ru.cft.shift.utils.formatters;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * @author Dmitrii Taranenko
 */
public class NumsFormatter {
    private static final DecimalFormatSymbols symbol = new DecimalFormatSymbols();

    public static String formatNum(double num) {
        symbol.setDecimalSeparator('.');
        return new DecimalFormat("0.##", symbol).format(num);
    }

}
