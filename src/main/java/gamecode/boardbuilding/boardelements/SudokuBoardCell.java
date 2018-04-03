package gamecode.boardbuilding.boardelements;

import java.util.ArrayList;
import java.util.List;

public final class SudokuBoardCell {
    private Integer cellValue = 0;
    private int cellMaxValue;
    private List<Integer> possibleValuesList;

    public SudokuBoardCell(int maxPossibleValue) {
        this.cellMaxValue = maxPossibleValue;
        this.possibleValuesList = createPossibleValuesList();

    }

    private List<Integer> createPossibleValuesList() {
        List<Integer> possibleValues = new ArrayList<>();
        for (int value = 1; value < cellMaxValue + 1; value++) {
            possibleValues.add(value);
        }
        return possibleValues;
    }

    public int getCellValue() {
        return cellValue;
    }

    public void setCellValue(final int cellValue) {
        this.cellValue = cellValue;
    }

    public List<Integer> getPossibleValuesList() {
        return possibleValuesList;
    }

    public void setPossibleValuesList(final List<Integer> possibleValuesList) {
        this.possibleValuesList = possibleValuesList;
    }

    @Override
    public String toString() {
        if (cellValue != 0) {
            if (cellValue <= 9) {
                return " " + cellValue;
            } else {
                return "" + cellValue;
            }
        } else {
            return "..";
        }
    }
}
