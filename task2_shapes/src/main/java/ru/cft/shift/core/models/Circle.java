package ru.cft.shift.core.models;

import ru.cft.shift.core.IShape;

import static ru.cft.shift.utils.formatters.NumsFormatter.formatNum;

/**
 * @author Dmitrii Taranenko
 */
public class Circle implements IShape {
    private final double radius;

    public Circle(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }

        this.radius = radius;
    }

    @Override
    public String getName() {
        return "Круг";
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }

    public double calculateDiameter() {
        return radius * 2;
    }

    @Override
    public StringBuilder getInfo(StringBuilder outSb, String units) {
        getCommonInfo(outSb, units);

        outSb.append("Радиус: ").append(formatNum(radius)).append(" ").append(units).append("\n");
        outSb.append("Диаметр: ").append(formatNum(calculateDiameter())).append(" ").append(units);

        return outSb;
    }
}
