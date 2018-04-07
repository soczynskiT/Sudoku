package gamecode.boarbuilding;

import gamecode.boardbuilding.*;
import gamecode.boardbuilding.boardelements.BoardBlock;
import gamecode.boardbuilding.boardelements.CellEntry;
import gamecode.boardbuilding.boardelements.SudokuBoardCell;
import org.junit.Assert;
import org.junit.Test;
import usermoveslogic.UserMoveReader;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BoardBuildingTestSuite {
    private final CellEntryValidator cellEntryValidator = CellEntryValidator.getInstance();
    private final CellsFiller cellsFiller = CellsFiller.getInstance();
    private final StringEntryBoardBuilder stringEntryBoardBuilder = StringEntryBoardBuilder.getInstance();
    private final RandomBoardBuilder randomBoardBuilder = RandomBoardBuilder.getInstance();
    private final static UserMoveReader MOVES_READER_MOCK = mock(UserMoveReader.class);

    @Test
    public void testForCorrectBoardBlockAssignmentInCellEntry() {
        //Given
        final SudokuBoard testBoard = new SudokuBoard(3);
        final BoardBlock testBlock = new BoardBlock();
        testBlock.addColIndicator(6);
        testBlock.addColIndicator(7);
        testBlock.addColIndicator(8);
        testBlock.addRowIndicator(0);
        testBlock.addRowIndicator(1);
        testBlock.addRowIndicator(2);
        //When
        final CellEntry testCellEntry = new CellEntry(1, 8, 5, testBoard);
        //Then
        Assert.assertEquals(testCellEntry.getBlockNo().getRowsIndicators(), testBlock.getRowsIndicators());
        Assert.assertEquals(testCellEntry.getBlockNo().getColsIndicators(), testBlock.getColsIndicators());
        Assert.assertTrue(testBoard.getBoardBlocksList().contains(testCellEntry.getBlockNo()));
    }

    @Test
    public void testCreatePossibleValuesForSudokuBoardCell() {
        //Given
        final int maxPossibleValue = 5;
        //When
        final SudokuBoardCell sudokuBoardCell = new SudokuBoardCell(maxPossibleValue);
        //Then
        Assert.assertEquals(5, sudokuBoardCell.getPossibleValuesList().size());
    }

    @Test
    public void testSudokuBoardForCorrectBoardCreation() {
        //Given
        final int blocksOnBoardSideNo = 5;
        //When
        final SudokuBoard testSudokuBoard = new SudokuBoard(blocksOnBoardSideNo);
        int cellsNumber = 0;
        for (int r = 0; r < testSudokuBoard.getSudokuBoard().length; r++) {
            for (int c = 0; c < testSudokuBoard.getSudokuBoard()[r].length; c++) {
                cellsNumber++;
            }
        }
        //Then
        Assert.assertEquals(25, testSudokuBoard.getBoardBlocksList().size());
        Assert.assertEquals(25, testSudokuBoard.getBoardSideSize());
        Assert.assertEquals(625, cellsNumber);
    }

    @Test
    public void testSudokuBoardDeepCopy() throws CloneNotSupportedException{
        //Given
        final SudokuBoard sudokuBoard = new SudokuBoard(3);
        //When
        SudokuBoard deepCopiedSudokuBoard = sudokuBoard.deepCopy();

        sudokuBoard.setAlgorithmMovesID(1);
        sudokuBoard.getSudokuBoard()[1][1].setCellValue(5);
        sudokuBoard.getSudokuBoard()[1][1].setPossibleValuesList(new ArrayList<>());
        //Then
        Assert.assertNotEquals(sudokuBoard, deepCopiedSudokuBoard);
        Assert.assertNotEquals(5, deepCopiedSudokuBoard.getSudokuBoard()[1][1].getCellValue());
        Assert.assertFalse(deepCopiedSudokuBoard.getSudokuBoard()[1][1].getPossibleValuesList().isEmpty());
    }

    @Test
    public void testCellEntryValidatorForCellValidateWithOccupiedBoardCell() {
        //Given
        final SudokuBoard sudokuBoard = new SudokuBoard(3);
        final CellEntry cellEntry = new CellEntry(1, 1, 5, sudokuBoard);
        sudokuBoard.getSudokuBoard()[1][1].setCellValue(5);
        //When
        final boolean isCellEntryCorrect = cellEntryValidator.validateEntry(cellEntry, sudokuBoard);
        //Then
        Assert.assertFalse(isCellEntryCorrect);
    }

    @Test
    public void testCellEntryValidatorForRowValidateWithOccupiedBoardRow() {
        //Given
        final SudokuBoard sudokuBoard = new SudokuBoard(3);
        final CellEntry cellEntry = new CellEntry(1, 1, 5, sudokuBoard);
        sudokuBoard.getSudokuBoard()[1][8].setCellValue(5);
        //When
        final boolean isCellEntryCorrect = cellEntryValidator.validateEntry(cellEntry, sudokuBoard);
        //Then
        Assert.assertFalse(isCellEntryCorrect);
    }

    @Test
    public void testCellEntryValidatorForColValidateWithOccupiedBoardCol() {
        //Given
        final SudokuBoard sudokuBoard = new SudokuBoard(3);
        final CellEntry cellEntry = new CellEntry(1, 1, 5, sudokuBoard);
        sudokuBoard.getSudokuBoard()[8][1].setCellValue(5);
        //When
        final boolean isCellEntryCorrect = cellEntryValidator.validateEntry(cellEntry, sudokuBoard);
        //Then
        Assert.assertFalse(isCellEntryCorrect);
    }

    @Test
    public void testCellEntryValidatorForBlockValidateWithOccupiedBoardBlock() {
        //Given
        final SudokuBoard sudokuBoard = new SudokuBoard(3);
        final CellEntry cellEntry = new CellEntry(1, 1, 5, sudokuBoard);
        sudokuBoard.getSudokuBoard()[2][2].setCellValue(5);
        //When
        final boolean isCellEntryCorrect = cellEntryValidator.validateEntry(cellEntry, sudokuBoard);
        //Then
        Assert.assertFalse(isCellEntryCorrect);
    }

    @Test
    public void testCellEntryValidatorForCorrectCellEntry() {
        //Given
        final SudokuBoard sudokuBoard = new SudokuBoard(3);
        final CellEntry cellEntry = new CellEntry(1, 1, 5, sudokuBoard);
        //When
        final boolean isCellEntryCorrect = cellEntryValidator.validateEntry(cellEntry, sudokuBoard);
        //Then
        Assert.assertTrue(isCellEntryCorrect);
    }

    @Test
    public void testCellsFillerForCellFillUp() {
        //Given
        final SudokuBoard sudokuBoard = new SudokuBoard(3);
        final int cellEntryRow = 1;
        final int cellEntryCol = 2;
        final int cellEntryValue = 3;

        when(MOVES_READER_MOCK.readNumberOfBounds(16, 64)).thenReturn(1);
        when(MOVES_READER_MOCK.readNumberOfBounds(1, 9)).
                thenReturn(cellEntryRow).
                thenReturn(cellEntryCol).
                thenReturn(cellEntryValue);
        //When
        cellsFiller.fillUpCells(MOVES_READER_MOCK, cellEntryValidator, sudokuBoard);
        //Then
        Assert.assertEquals(cellEntryValue, sudokuBoard.getSudokuBoard()[cellEntryRow - 1][cellEntryCol - 1].getCellValue());
    }

    @Test
    public void testStringEntryBoardBuilderForCellFillUp() {
        //Given
        final SudokuBoard sudokuBoard = new SudokuBoard(3);
        final int cellEntryRow = 1;
        final int cellEntryCol = 2;
        final int cellEntryValue = 3;
        final String entryString = "1    2    3";

        when(MOVES_READER_MOCK.readNumberOfBounds(16, 64)).thenReturn(1);
        when(MOVES_READER_MOCK.readString()).thenReturn(entryString);
        //When
        stringEntryBoardBuilder.buildSudokuBoardByStream(MOVES_READER_MOCK, cellEntryValidator, sudokuBoard);
        //Then
        Assert.assertEquals(cellEntryValue, sudokuBoard.getSudokuBoard()[cellEntryRow - 1][cellEntryCol - 1].getCellValue());
    }

    @Test
    public void testRandomBoardBuilderForCellFillUp() {
        //Given
        final SudokuBoard sudokuBoard = new SudokuBoard(3);

        when(MOVES_READER_MOCK.readNumberOfBounds(16, 64)).thenReturn(5);
        //When
        randomBoardBuilder.generateRandomBoard(MOVES_READER_MOCK, cellEntryValidator, sudokuBoard);
        int fulfilledCellsNumber = 0;
        for (int r = 0; r < sudokuBoard.getSudokuBoard().length; r++) {
            for (int c = 0; c < sudokuBoard.getSudokuBoard()[r].length; c++) {
                if (sudokuBoard.getSudokuBoard()[r][c].getCellValue() != 0) {
                    fulfilledCellsNumber++;
                }
            }
        }
        //Then
        Assert.assertEquals(5, fulfilledCellsNumber);
    }
}
