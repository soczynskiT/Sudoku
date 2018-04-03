package gamecode.boardbuilding;

import exceptions.InvalidCellEntryException;
import gamecode.SudokuMenu;
import gamecode.boardbuilding.boardelements.CellEntry;

import java.util.List;

public final class CellEntryValidator {
    private static CellEntryValidator cellEntryValidatorInstance = null;

    private CellEntryValidator() {
    }

    boolean validateEntry(final CellEntry cellEntry, final SudokuBoard sudokuBoard) {
        boolean isEntryCorrect = true;
        try {
            validateCell(cellEntry, sudokuBoard);
            validateRow(cellEntry, sudokuBoard);
            validateColumn(cellEntry, sudokuBoard);
            validateBlock(cellEntry, sudokuBoard);
        } catch (InvalidCellEntryException e) {
            isEntryCorrect = false;
        }
        return isEntryCorrect;
    }

    private void validateCell(final CellEntry cellEntry, final SudokuBoard sudokuBoard) throws InvalidCellEntryException {
        if (sudokuBoard.getSudokuBoard()[cellEntry.getRowNo()][cellEntry.getColumnNo()].getCellValue() != 0) {
            throw new InvalidCellEntryException("Cell occupied");
        }
    }

    private void validateRow(final CellEntry cellEntry, final SudokuBoard sudokuBoard) throws InvalidCellEntryException {
        for (int col = 0; col < sudokuBoard.getBoardSideSize(); col++) {
            if (col != cellEntry.getColumnNo()) {
                if (sudokuBoard.getSudokuBoard()[cellEntry.getRowNo()][col].getCellValue() == cellEntry.getValue()) {
                    throw new InvalidCellEntryException("Row occupied");
                }
            }
        }
    }

    private void validateColumn(final CellEntry cellEntry, final SudokuBoard sudokuBoard) throws InvalidCellEntryException {
        for (int row = 0; row < sudokuBoard.getBoardSideSize(); row++) {
            if (row != cellEntry.getRowNo()) {
                if (sudokuBoard.getSudokuBoard()[row][cellEntry.getColumnNo()].getCellValue() == cellEntry.getValue()) {
                    throw new InvalidCellEntryException("Column occupied");
                }
            }
        }
    }

    private void validateBlock(final CellEntry cellEntry, final SudokuBoard sudokuBoard) throws InvalidCellEntryException {
        final List<Integer> rowsToCheck = cellEntry.getBlockNo().getRowsIndicators();
        final List<Integer> colsToCheck = cellEntry.getBlockNo().getColsIndicators();

        for (int row = rowsToCheck.get(0); row < rowsToCheck.get(rowsToCheck.size() - 1) + 1; row++) {
            for (int col = colsToCheck.get(0); col < colsToCheck.get(colsToCheck.size() - 1) + 1; col++) {
                if (row != cellEntry.getRowNo() && col != cellEntry.getColumnNo()) {
                    if (sudokuBoard.getSudokuBoard()[row][col].getCellValue() == cellEntry.getValue()) {
                        throw new InvalidCellEntryException("Block occupied");
                    }
                }
            }
        }
    }

    public static CellEntryValidator getInstance() {
        if (cellEntryValidatorInstance == null) {
            synchronized (SudokuMenu.class) {
                if (cellEntryValidatorInstance == null) {
                    cellEntryValidatorInstance = new CellEntryValidator();
                }
            }
        }
        return cellEntryValidatorInstance;
    }
}