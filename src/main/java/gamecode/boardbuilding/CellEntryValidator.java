package gamecode.boardbuilding;

import gamecode.SudokuMenu;

public final class CellEntryValidator {
    private static CellEntryValidator cellEntryValidatorInstance = null;

    private CellEntryValidator() {
    }

    public boolean validateEntry(CellEntry cellEntry, SudokuBoard sudokuBoard) {
        if (sudokuBoard.getSudokuBoard()[cellEntry.getRowNo()][cellEntry.getColumnNo()].getCellValue() == 0) {
            final boolean rowCheck = validateRow(sudokuBoard, cellEntry);
            final boolean colCheck = validateColumn(sudokuBoard, cellEntry);
            final boolean blockCheck = validateBlock(sudokuBoard, cellEntry);
            return rowCheck == colCheck == blockCheck == true;
        } else {
            return false;
        }
    }

    private boolean validateRow(SudokuBoard sudokuBoard, CellEntry cellEntry) {
        boolean validatedValueCorrect = true;
        for (int i = 0; i < 9; i++) {
            if (i != cellEntry.getColumnNo()) {
                if (sudokuBoard.getSudokuBoard()[cellEntry.getRowNo()][i].getCellValue() == cellEntry.getValue()) {
                    validatedValueCorrect = false;
                }
            }
        }
        return validatedValueCorrect;
    }

    private boolean validateColumn(SudokuBoard sudokuBoard, CellEntry cellEntry) {
        boolean validatedValueCorrect = true;
        for (int i = 0; i < 9; i++) {
            if (i != cellEntry.getRowNo()) {
                if (sudokuBoard.getSudokuBoard()[i][cellEntry.getColumnNo()].getCellValue() == cellEntry.getValue()) {
                    validatedValueCorrect = false;
                }
            }
        }
        return validatedValueCorrect;
    }

    private boolean validateBlock(SudokuBoard sudokuBoard, CellEntry cellEntry) {
        boolean validatedValueCorrect = true;
        final int firstRowToCheck = cellEntry.getBlockNo().getRowsIndicators().get(0);
        final int firstColToCheck = cellEntry.getBlockNo().getColumnsIndicators().get(0);
        for (int r = firstRowToCheck; r < firstRowToCheck + 3; r++) {
            for (int c = firstColToCheck; c < firstColToCheck + 3; c++) {
                if (r != cellEntry.getRowNo() && c != cellEntry.getColumnNo()) {
                    if (sudokuBoard.getSudokuBoard()[r][c].getCellValue() == cellEntry.getValue()) {
                        validatedValueCorrect = false;
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
