package gamecode.boardbuilding;

import gamecode.SudokuMenu;
import gamecode.boardbuilding.boardelements.CellEntry;
import usermoveslogic.UserMoveReader;

import java.util.Random;

public final class RandomBoardBuilder {
    private final Random random = new Random();
    private static RandomBoardBuilder randomBoardBuilderInstance = null;

    private RandomBoardBuilder() {
    }

    public void generateRandomBoard(final UserMoveReader userMoveReader, final CellEntryValidator cellEntryValidator,
                                    final SudokuBoard sudokuBoard) {
        final Double minimumCellsToFillUp = (sudokuBoard.getBoardSideSize() * sudokuBoard.getBoardSideSize()) * 0.2;
        final int minBound = minimumCellsToFillUp.intValue();
        final int maxBound = minBound * 4;

        System.out.println("Please enter no of cells to fulfill (" + minBound + " - " + maxBound + ")");
        final int noOfCellsToFill = userMoveReader.readNumberOfBounds(minBound, maxBound);
        int fulfilledCells = 0;

        while (fulfilledCells < noOfCellsToFill) {
            final int row = random.nextInt(sudokuBoard.getBoardSideSize());
            final int col = random.nextInt(sudokuBoard.getBoardSideSize());
            final int value = random.nextInt(sudokuBoard.getBoardSideSize()) + 1;
            final CellEntry cellEntry = new CellEntry(row, col, value, sudokuBoard);
            final boolean isEntryCorrect = cellEntryValidator.validateEntry(cellEntry, sudokuBoard);

            if (isEntryCorrect) {
                sudokuBoard.getSudokuBoard()[row][col].setCellValue(value);
                fulfilledCells++;
            }
        }
    }

    public static RandomBoardBuilder getInstance() {
        if (randomBoardBuilderInstance == null) {
            synchronized (SudokuMenu.class) {
                if (randomBoardBuilderInstance == null) {
                    randomBoardBuilderInstance = new RandomBoardBuilder();
                }
            }
        }
        return randomBoardBuilderInstance;
    }
}
