package ru.cft.shift.utils.dto;

/**
 * @author Dmitrii Taranenko
 */
public record CliDto(String inputFile, String outputFile, boolean help) {
    public boolean hasOutputFile() {
        return outputFile != null && !outputFile.trim().isEmpty();
    }
}
