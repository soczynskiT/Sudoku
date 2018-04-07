package gamecode.databases;

import gamecode.boardbuilding.SudokuBoard;
import gamecode.databases.databasedisplay.EasiestSolutionDisplay;
import gamecode.databases.databasedisplay.RandomDeadEndDisplay;
import gamecode.databases.databasedisplay.RandomSolutionDisplay;
import org.junit.Assert;
import org.junit.Test;

public class BoardsDatabasesTestSuit {
    private final BoardsDatabase boardsDatabase = BoardsDatabase.getInstance();

    @Test
    public void testSaveBoardState() {
        //Given
        final SudokuBoard sudokuBoard = new SudokuBoard(3);
        //When
        boardsDatabase.saveBoardState(sudokuBoard);
        //Then
        Assert.assertFalse(boardsDatabase.getBoardStepLog().isEmpty());
        Assert.assertNotEquals(sudokuBoard, boardsDatabase.getBoardStepLog().get(0));
    }

    @Test
    public void testSaveSolvedBoard() {
        //Given
        final SudokuBoard sudokuBoard = new SudokuBoard(3);
        //When
        boardsDatabase.saveSolvedBoard(sudokuBoard);
        //Then
        Assert.assertFalse(boardsDatabase.getSolvedBoards().isEmpty());
        Assert.assertNotEquals(sudokuBoard, boardsDatabase.getSolvedBoards().get(0));
    }

    @Test
    public void testSaveDeadEndBBoard() {
        //Given
        final SudokuBoard sudokuBoard = new SudokuBoard(3);
        //When
        boardsDatabase.saveDeadEndBoard(sudokuBoard);
        //Then
        Assert.assertFalse(boardsDatabase.getDeadEndBoards().isEmpty());
        Assert.assertNotEquals(sudokuBoard, boardsDatabase.getDeadEndBoards().get(0));
    }

    @Test
    public void testClearDatabases() {
        //Given
        final SudokuBoard sudokuBoard = new SudokuBoard(3);
        boardsDatabase.saveBoardState(sudokuBoard);
        boardsDatabase.saveSolvedBoard(sudokuBoard);
        boardsDatabase.saveDeadEndBoard(sudokuBoard);
        //When
        boardsDatabase.clearDatabases();
        //Then
        Assert.assertTrue(boardsDatabase.getBoardStepLog().isEmpty()
                == boardsDatabase.getSolvedBoards().isEmpty() == boardsDatabase.getDeadEndBoards().isEmpty());
    }

    @Test
    public void testBoardsDatabasesEasiestSolutionDisplay() {
        //Given
        final SudokuBoard board1 = new SudokuBoard(3);
        board1.setAlgorithmMovesID(5);
        board1.getSudokuBoard()[1][1].setCellValue(5);
        final SudokuBoard board2 = new SudokuBoard(3);
        board2.setAlgorithmMovesID(2);
        board2.getSudokuBoard()[1][1].setCellValue(2);

        boardsDatabase.saveSolvedBoard(board1);
        boardsDatabase.saveSolvedBoard(board2);
        //When
        boardsDatabase.databaseRecordsDisplay(new EasiestSolutionDisplay());
    }

    @Test
    public void testBoardsDatabasesRandomDeadEndDisplay() {
        //Given
        final SudokuBoard board1 = new SudokuBoard(3);
        board1.getSudokuBoard()[1][1].setCellValue(5);
        boardsDatabase.saveDeadEndBoard(board1);
        //When
        boardsDatabase.databaseRecordsDisplay(new RandomDeadEndDisplay());
    }

    @Test
    public void testBoardsDatabasesRandomSolutionDisplay() {
        //Given
        final SudokuBoard board1 = new SudokuBoard(3);
        board1.getSudokuBoard()[1][1].setCellValue(5);
        boardsDatabase.saveSolvedBoard(board1);
        //When
        boardsDatabase.databaseRecordsDisplay(new RandomSolutionDisplay());
    }
}
