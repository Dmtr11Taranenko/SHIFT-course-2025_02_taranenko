package ru.cft.shift.core;

import static ru.cft.shift.utils.formatters.NumsFormatter.formatNum;

/**
 * @author Dmitrii Taranenko
 */
public interface IShape {
    String getName();

    double calculateArea();

    double calculatePerimeter();

    StringBuilder getInfo(StringBuilder outSb, String units);

    default void getCommonInfo(StringBuilder outSb, String units) {
        outSb.append("Тип фигуры: ").append(getName()).append("\n");
        outSb.append("Площадь: ").append(formatNum(calculateArea())).append(" кв. ").append(units).append("\n");
        outSb.append("Периметр: ").append(formatNum(calculatePerimeter())).append(" ").append(units).append("\n");
    }


}

