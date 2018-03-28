package gamecode.boardbuilding;

import gamecode.SudokuMenu;
import usermoveslogic.UserMoveReader;

import java.util.Random;

public final class RandomBoardBuilder {
    private final Random random = new Random();
    private static RandomBoardBuilder randomBoardBuilderInstance = null;

    private RandomBoardBuilder() {
    }

    public void generateRandomBoard(UserMoveReader userMoveReader, CellEntryValidator cellEntryValidator,
                                    SudokuBoard sudokuBoard) {

        System.out.println("Please enter no of cells to fulfill (1-50)");
        final int noOfCellsToFill = userMoveReader.readNumberOfBounds(1, 50);
        int fulfilledCells = 0;

        while (fulfilledCells < noOfCellsToFill) {
            final int row = random.nextInt(9);
            final int col = random.nextInt(9);
            final int value = random.nextInt(9) + 1;
            final CellEntry cellEntry = new CellEntry(row, col, value);
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
