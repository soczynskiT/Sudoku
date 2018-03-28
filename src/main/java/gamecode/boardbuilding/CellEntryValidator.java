package gamecode.boardbuilding;

import gamecode.SudokuMenu;

public final class CellEntryValidator {
    private static CellEntryValidator cellEntryValidatorInstance = null;

    private CellEntryValidator() {
    }

    public boolean validateEntry(CellEntry cellEntry, SudokuBoard sudokuBoard) {
        if (sudokuBoard.getSudokuBoard()[cellEntry.getRowNo()][cellEntry.getColumnNo()].getCellValue() == 0) {
            final int cellCheck = validateCell(cellEntry, sudokuBoard);
            final int rowCheck = validateRow(sudokuBoard, cellEntry);
            final int colCheck = validateColumn(sudokuBoard, cellEntry);
            final int blockCheck = validateBlock(sudokuBoard, cellEntry);
            final int result = cellCheck + rowCheck + colCheck + blockCheck;
            return result == 0;
        } else {
            return false;
        }
    }

    private int validateCell(CellEntry cellEntry, SudokuBoard sudokuBoard) {
        if (sudokuBoard.getSudokuBoard()[cellEntry.getRowNo()][cellEntry.getColumnNo()].getCellValue() == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    private int validateRow(SudokuBoard sudokuBoard, CellEntry cellEntry) {
        int validatedValueCorrect = 0;
        for (int col = 0; col < 9; col++) {
            if (col != cellEntry.getColumnNo()) {
                if (sudokuBoard.getSudokuBoard()[cellEntry.getRowNo()][col].getCellValue() == cellEntry.getValue()) {
                    validatedValueCorrect = 1;
                }
            }
        }
        return validatedValueCorrect;
    }

    private int validateColumn(SudokuBoard sudokuBoard, CellEntry cellEntry) {
        int validatedValueCorrect = 0;
        for (int row = 0; row < 9; row++) {
            if (row != cellEntry.getRowNo()) {
                if (sudokuBoard.getSudokuBoard()[row][cellEntry.getColumnNo()].getCellValue() == cellEntry.getValue()) {
                    validatedValueCorrect = 1;
                }
            }
        }
        return validatedValueCorrect;
    }

    private int validateBlock(SudokuBoard sudokuBoard, CellEntry cellEntry) {
        int validatedValueCorrect = 0;
        final int firstRowToCheck = cellEntry.getBlockNo().getRowsIndicators().get(0);
        final int firstColToCheck = cellEntry.getBlockNo().getColumnsIndicators().get(0);
        for (int r = firstRowToCheck; r < firstRowToCheck + 3; r++) {
            for (int c = firstColToCheck; c < firstColToCheck + 3; c++) {
                if (r != cellEntry.getRowNo() && c != cellEntry.getColumnNo()) {
                    if (sudokuBoard.getSudokuBoard()[r][c].getCellValue() == cellEntry.getValue()) {
                        validatedValueCorrect = 1;
                    }
                }
            }
        }
        return validatedValueCorrect;
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