package ru.cft.shift.core.models;

import ru.cft.shift.core.IShape;

import static ru.cft.shift.utils.formatters.NumsFormatter.formatNum;

/**
 * @author Dmitrii Taranenko
 */
public class Rectangle implements IShape {
    private final double sideA;
    private final double sideB;

    public Rectangle(double sideA, double sideB) {
        if (sideA <= 0 || sideB <= 0) {
            throw new IllegalArgumentException("Sides must be positive");
        }

        this.sideA = sideA;
        this.sideB = sideB;
    }

    @Override
    public String getName() {
        return "Прямоугольник";
    }

    @Override
    public double calculateArea() {
        return sideA * sideB;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * (sideA + sideB);
    }

    public double calculateDiagonal() {
        return Math.sqrt(sideA * sideA + sideB * sideB);
    }

    @Override
    public StringBuilder getInfo(StringBuilder outSb, String units) {
        getCommonInfo(outSb, units);

        outSb.append("Длина: ").append(formatNum(Math.max(sideA, sideB))).append(" ").append(units).append("\n");
        outSb.append("Ширина: ").append(formatNum(Math.min(sideA, sideB))).append(" ").append(units).append("\n");
        outSb.append("Длина диагонали: ").append(formatNum(calculateDiagonal())).append(" ").append(units);

        return outSb;
    }
}
