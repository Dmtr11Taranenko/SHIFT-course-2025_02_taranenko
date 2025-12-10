package ru.cft.shift.model.component;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author Dmitrii Taranenko
 */
public class Miner {
    private final Random random = new Random();
    private final Set<Cell> safeZone = new HashSet<>();

    public void miningField(Field field) {
        int count = 0;
        var cells = field.getCells();
        var openedCell = field.getOpenedCell();

        safeZone.clear();
        safeZone.add(openedCell);
        field.checkCellsNeighbors(openedCell.getXCoords(), openedCell.getYCoords(), (safeZone::add));

        while (count < field.getTotalMine()) {
            Cell mine = field.getCell(
                    random.nextInt(cells.size()),
                    random.nextInt(cells.getFirst().size()));

            if (mine.isMined() || safeZone.contains(mine)) continue;

            mine.setMined(true);
            count++;
            calculateMines(field, mine);
        }
    }

    private void calculateMines(Field field, Cell cell) {
        int mineX = cell.getXCoords();
        int mineY = cell.getYCoords();

        field.checkCellsNeighbors(mineX, mineY, (Cell::incrementMineCount));
    }


}
