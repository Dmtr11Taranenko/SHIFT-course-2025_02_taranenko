package ru.cft.shift.core.dto;

import java.util.List;

/**
 * @author Dmitrii Taranenko
 */
public record ShapeDto(String type, List<Double> parameters) {
    public ShapeDto {
        parameters = List.copyOf(parameters);
    }

    public void validate() {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Shape type cannot be null or empty");
        }
        if (parameters == null || parameters.isEmpty()) {
            throw new IllegalArgumentException("Shape parameters cannot be null or empty");
        }
    }
}