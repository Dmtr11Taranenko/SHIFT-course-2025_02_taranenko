package ru.cft.shift.core.models;

import ru.cft.shift.core.IShape;

import static ru.cft.shift.utils.formatters.NumsFormatter.formatNum;

/**
 * @author Dmitrii Taranenko
 */
public class Triangle implements IShape {
    private final double
            sideA,
            sideB,
            sideC;

    public Triangle(double sideA, double sideB, double sideC) {
        if (sideA <= 0 || sideB <= 0 || sideC <= 0) {
            throw new IllegalArgumentException("All sides must be positive");
        }
        if (sideA + sideB <= sideC || sideA + sideC <= sideB || sideB + sideC <= sideA) {
            throw new IllegalArgumentException("Invalid triangle sides");
        }

        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    @Override
    public String getName() {
        return "Треугольник";
    }

    @Override
    public double calculateArea() {
        double halfPerimeter = calculatePerimeter() / 2;
        return Math.sqrt(halfPerimeter * (halfPerimeter - sideA) * (halfPerimeter - sideB) * (halfPerimeter - sideC));
    }

    @Override
    public double calculatePerimeter() {
        return sideA + sideB + sideC;
    }

    /**
     * @Note: Расчет противолежащего угла с помощью теоремы косинусов
     */
    private double calculateAngleOppositeToSide(double side, double oppositeSide) {
        double cosAngle = (side * side + oppositeSide * oppositeSide
                - getThirdSide(side, oppositeSide) * getThirdSide(side, oppositeSide))
                / (2 * side * oppositeSide);
        return Math.toDegrees(Math.acos(cosAngle));
    }

    /**
     * @Note: Вспомогательный метод для расчета противолежащего угла
     */
    private double getThirdSide(double side1, double side2) {
        if (side1 == sideA && side2 == sideB) return sideC;
        if (side1 == sideA && side2 == sideC) return sideB;
        if (side1 == sideB && side2 == sideC) return sideA;
        return getThirdSide(side2, side1);
    }

    @Override
    public StringBuilder getInfo(StringBuilder outSb, String units) {
        getCommonInfo(outSb, units);

        outSb.append("Сторона A: ").append(formatNum(sideA))
                .append(" ").append(units).append(", противолежащий угол: ")
                .append(formatNum(calculateAngleOppositeToSide(sideB, sideC))).append("°\n");

        outSb.append("Сторона B: ").append(formatNum(sideB))
                .append(" ").append(units).append(", противолежащий угол: ")
                .append(formatNum(calculateAngleOppositeToSide(sideA, sideC))).append("°\n");

        outSb.append("Сторона C: ").append(formatNum(sideC))
                .append(" ").append(units).append(", противолежащий угол: ")
                .append(formatNum(calculateAngleOppositeToSide(sideA, sideB))).append("°");

        return outSb;
    }
}