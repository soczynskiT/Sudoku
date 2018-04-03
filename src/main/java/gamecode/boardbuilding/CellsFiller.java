package gamecode.boardbuilding;

import gamecode.SudokuMenu;
import gamecode.boardbuilding.boardelements.CellEntry;
import usermoveslogic.UserMoveReader;

public final class CellsFiller {
    private static CellsFiller cellsFillerInstance = null;

    private CellsFiller() {
    }

    public void fillUpCells(final UserMoveReader userMoveReader, final CellEntryValidator cellEntryValidator,
                            final SudokuBoard sudokuBoard) {
        final int cellsToFillByUser = setNumberOfCellsToFillByUser(userMoveReader, sudokuBoard);
        if (cellsToFillByUser != 0) {
            System.out.println("Enter cell address and new value:");
            int currentCell = 1;
            for (int i = 0; i < cellsToFillByUser; i++) {
                System.out.println("Setting value for cell no " + currentCell);
                fillUpOneCell(userMoveReader, cellEntryValidator, sudokuBoard);
                currentCell++;
            }
        }
    }

    private int setNumberOfCellsToFillByUser(final UserMoveReader userMoveReader, final SudokuBoard sudokuBoard) {
        final Double minimumCellsToFillUp = (sudokuBoard.getBoardSideSize() * sudokuBoard.getBoardSideSize()) * 0.2;
        final int minBound = minimumCellsToFillUp.intValue();
        final int maxBound = minBound * 4;
        System.out.println("Enter number of cells would you like to fill ups" +
                " (" + minBound + " - " + maxBound + ")");
        return userMoveReader.readNumberOfBounds(minBound, maxBound);
    }

    private void fillUpOneCell(final UserMoveReader userMoveReader, final CellEntryValidator cellEntryValidator,
                               final SudokuBoard sudokuBoard) {
        boolean incorrectEntry;
        do {
            System.out.println("ROW: ");
            final int row = userMoveReader.readNumberOfBounds(1, sudokuBoard.getBoardSideSize()) - 1;
            System.out.print("COLUMN: ");
            final int column = userMoveReader.readNumberOfBounds(1, sudokuBoard.getBoardSideSize()) - 1;
            System.out.print("VALUE: ");
            final int value = userMoveReader.readNumberOfBounds(1, sudokuBoard.getBoardSideSize());

            final CellEntry cellEntry = new CellEntry(row, column, value, sudokuBoard);
            final boolean entryCorrect = cellEntryValidator.validateEntry(cellEntry, sudokuBoard);

            if (entryCorrect) {
                sudokuBoard.getSudokuBoard()[row][column].setCellValue(value);
                sudokuBoard.displayBoard();
                incorrectEntry = false;
            } else {
                System.out.println("You cannot put this number here ! Try again.");
                incorrectEntry = true;
            }
        }
        while (incorrectEntry);
    }

    public static CellsFiller getInstance() {
        if (cellsFillerInstance == null) {
            synchronized (SudokuMenu.class) {
                if (cellsFillerInstance == null) {
                    cellsFillerInstance = new CellsFiller();
                }
            }
        }
        return cellsFillerInstance;
    }
}