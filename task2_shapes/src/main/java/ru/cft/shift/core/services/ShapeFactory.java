package ru.cft.shift.core.services;

import ru.cft.shift.core.IShape;
import ru.cft.shift.core.ShapeTypes;
import ru.cft.shift.core.exceptions.ShapeCreationException;
import ru.cft.shift.core.models.Circle;
import ru.cft.shift.core.models.Rectangle;
import ru.cft.shift.core.models.Triangle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Dmitrii Taranenko
 */
public class ShapeFactory {
    private static final Map<String, Function<List<Double>, IShape>> CREATORS = new HashMap<>();

    static {
        initCreator(ShapeTypes.CIRCLE, ShapeFactory::createCircle);
        initCreator(ShapeTypes.RECTANGLE, ShapeFactory::createRectangle);
        initCreator(ShapeTypes.TRIANGLE, ShapeFactory::createTriangle);
    }

    public static IShape createShape(String type, List<Double> params) {
        String normalizedType = type.toUpperCase();
        Function<List<Double>, IShape> creator = CREATORS.get(normalizedType);

        if (creator == null) {
            throw new ShapeCreationException("Unknown shape type: " + type);
        }

        return creator.apply(params);
    }

    private static void initCreator(ShapeTypes type, Function<List<Double>, IShape> creator) {
        CREATORS.put(type.name().toUpperCase(), creator);
    }

    private static IShape createCircle(List<Double> params) {
        validateParams(params, 1, "Circle");
        return new Circle(params.getFirst());
    }

    private static IShape createRectangle(List<Double> params) {
        validateParams(params, 2, "Rectangle");
        return new Rectangle(params.get(0), params.get(1));
    }

    private static IShape createTriangle(List<Double> params) {
        validateParams(params, 3, "Triangle");
        return new Triangle(params.get(0), params.get(1), params.get(2));
    }

    private static void validateParams(List<Double> params, int expectedCount, String shapeName) {
        if (params.size() != expectedCount) {
            throw new ShapeCreationException(shapeName, expectedCount, params.size());
        }
    }
}
