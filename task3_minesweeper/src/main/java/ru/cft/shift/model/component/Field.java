package ru.cft.shift.model.component;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Dmitrii Taranenko
 */
@Getter
@Setter
public class Field {
    private int height;
    private int width;
    private int totalMine;
    private List<List<Cell>> cells = new ArrayList<>();

    public Field(int height, int width, int totalMine) {
        this.height = height;
        this.width = width;
        this.totalMine = totalMine;
        fillFieldWithCells();
    }

    public Cell getCell(int x, int y) {
        return cells.get(x).get(y);
    }

    public void checkCellsNeighbors(int x, int y, Consumer<Cell> action) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;

                int neighborX = x + dx;
                int neighborY = y + dy;

                if (neighborX >= 0 && neighborX < width &&
                        neighborY >= 0 && neighborY < height) {

                    Cell neighbor = getCell(neighborX, neighborY);
                    action.accept(neighbor);
                }
            }
        }
    }

    Cell getOpenedCell() {
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                Cell cell = getCell(row, col);
                if (cell.isOpen()) return cell;
            }
        }
        return null;
    }

    private void fillFieldWithCells() {
        for (int row = 0; row < width; row++) {
            cells.add(new ArrayList<>());

            for (int col = 0; col < height; col++) {
                cells.get(row).add(new Cell(row, col));
            }
        }
    }
}
