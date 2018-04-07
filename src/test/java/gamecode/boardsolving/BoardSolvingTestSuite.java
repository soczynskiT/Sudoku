package gamecode.boardsolving;

import gamecode.boardbuilding.SudokuBoard;
import gamecode.databases.BoardsDatabase;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BoardSolvingTestSuite {
    private final SolverByLogic solverByLogic = SolverByLogic.getInstance();
    private final SolverByGuess solverByGuess = SolverByGuess.getInstance();
    private final BoardsDatabase boardsDatabase = BoardsDatabase.getInstance();

    @Test
    public void testReduceNoOfPossibleMovesInCells() {
        //Given
        final SudokuBoard sudokuBoard = new SudokuBoard(2);
        sudokuBoard.getSudokuBoard()[1][1].setCellValue(5);
        //When
        solverByLogic.reduceNoOfPossibleMovesInCells(sudokuBoard);
        //Then
        Assert.assertFalse(sudokuBoard.getSudokuBoard()[0][0].getPossibleValuesList().contains(5));
        Assert.assertFalse(sudokuBoard.getSudokuBoard()[0][1].getPossibleValuesList().contains(5));
        Assert.assertFalse(sudokuBoard.getSudokuBoard()[1][0].getPossibleValuesList().contains(5));
        Assert.assertFalse(sudokuBoard.getSudokuBoard()[1][1].getPossibleValuesList().contains(5));
        Assert.assertFalse(sudokuBoard.getSudokuBoard()[1][2].getPossibleValuesList().contains(5));
        Assert.assertFalse(sudokuBoard.getSudokuBoard()[1][3].getPossibleValuesList().contains(5));
        Assert.assertFalse(sudokuBoard.getSudokuBoard()[2][1].getPossibleValuesList().contains(5));
        Assert.assertFalse(sudokuBoard.getSudokuBoard()[3][1].getPossibleValuesList().contains(5));
    }

    @Test
    public void testFulfillCellsWithOnePossibility() {
        //Given
        final SudokuBoard sudokuBoard = new SudokuBoard(3);
        final List<Integer> testPossibleValuesList = new ArrayList<>(Collections.singletonList(9));
        sudokuBoard.getSudokuBoard()[1][1].setPossibleValuesList(testPossibleValuesList);
        //When
        solverByLogic.fulfillCellsWithOnePossibility(sudokuBoard);
        //Then
        Assert.assertEquals(9, sudokuBoard.getSudokuBoard()[1][1].getCellValue());
    }

    @Test
    public void testSudokuBoardAlgorithmMovesIdWIthFulfillCellsWithOnePossibility() {
        //Given
        final SudokuBoard sudokuBoard = new SudokuBoard(3);
        //When
        solverByLogic.fulfillCellsWithOnePossibility(sudokuBoard);
        final int expectedValue = 81;
        final int result = sudokuBoard.getAlgorithmMovesID();
        //Then
        Assert.assertEquals(expectedValue, result);
    }

    @Test
    public void testSudokuBoardAlgorithmMovesIdWIthReduceCellsValuesPossibility() {
        //Given
        final SudokuBoard sudokuBoard = new SudokuBoard(2);
        sudokuBoard.getSudokuBoard()[1][1].setCellValue(1);

        /*Algorithm value for each logic with one cell filled up*/
        final int expectedValue = 16 + 1 + 4 + 4 + 4;
        //When
        solverByLogic.reduceNoOfPossibleMovesInCells(sudokuBoard);
        final int result = sudokuBoard.getAlgorithmMovesID();
        //Then
        Assert.assertEquals(expectedValue, result);
    }

    @Test
    public void testGuessFirstPossibleValue() {
        //Given
        final SudokuBoard sudokuBoard = new SudokuBoard(2);
        //When
        solverByGuess.guessFirstPossibleValue(sudokuBoard, boardsDatabase);
        //Then
        Assert.assertTrue(sudokuBoard.getSudokuBoard()[0][0].getCellValue() != 0);
    }

    @Test
    public void testSudokuBoardAlgorithmIdWithGuessFirstPossibleValue() {
        //Given
        final SudokuBoard sudokuBoard = new SudokuBoard(2);

        /*Algorithm value for each logic with one cell filled up*/
        final int expectedValue = 1 + 16 + 16 + 1 + 1;
        //When
        solverByGuess.guessFirstPossibleValue(sudokuBoard, boardsDatabase);
        //Then
        Assert.assertEquals(expectedValue, sudokuBoard.getAlgorithmMovesID());
    }
}
