package ru.cft.shift.model.component;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Dmitrii Taranenko
 */
@Getter
@Setter
public class Cell {
    private final int xCoords;
    private final int yCoords;
    private int mineCount;
    private boolean isOpen;
    private boolean isMined;
    private boolean isFlagged;

    Cell(int xCoords, int yCoords) {
        this.xCoords = xCoords;
        this.yCoords = yCoords;
    }

    void incrementMineCount() {
        mineCount++;
    }
}
