package gamecode.boardbuilding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class SudokuBoardCell {
    private int cellValue;
    private final List<Integer> possibleValues = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

    SudokuBoardCell() {
        this.cellValue = 0;
    }

    public int getCellValue() {
        return cellValue;
    }

    public void setCellValue(int cellValue) {
        this.cellValue = cellValue;
    }

    public List<Integer> getPossibleValues() {
        return possibleValues;
    }

    @Override
    public String toString() {
        if (cellValue != 0) {
            return "" + cellValue;
        } else {
            return ".";
        }
    }
}
