package ru.cft.shift.core.exceptions;

/**
 * @author Dmitrii Taranenko
 */
public class ShapeCreationException extends RuntimeException {

    public ShapeCreationException(String message) {
        super(message);
    }

    public ShapeCreationException(String shapeType, int expectedParams, int actualParams) {
        super(String.format(
                "Shape '%s' requires %d parameters, but got %d",
                shapeType, expectedParams, actualParams
        ));
    }
}
