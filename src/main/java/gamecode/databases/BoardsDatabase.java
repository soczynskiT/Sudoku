package gamecode.databases;

import gamecode.SudokuMenu;
import gamecode.boardbuilding.SudokuBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoardsDatabase {
    private final List<SudokuBoard> boardStepLog;
    private final List<SudokuBoard> solvedBoards;
    private final List<SudokuBoard> deadEndBoards;
    private static BoardsDatabase boardsDatabaseInstance = null;

    private BoardsDatabase() {
        this.boardStepLog = new ArrayList<>();
        this.solvedBoards = new ArrayList<>();
        this.deadEndBoards = new ArrayList<>();

    }

    public void saveBoardState(SudokuBoard sudokuBoard) {
        try {
            SudokuBoard logBoard = sudokuBoard.deepCopy();
            boardStepLog.add(logBoard);
        } catch (CloneNotSupportedException e) {
            System.out.println(e);
        }
    }

    public void saveSolvedBoard(SudokuBoard sudokuBoard) {
        try {
            SudokuBoard solvedBoardLog = sudokuBoard.deepCopy();
            solvedBoards.add(solvedBoardLog);
        } catch (CloneNotSupportedException e) {
            System.out.println(e);
        }
    }

    public void saveDeadEndBoard(SudokuBoard sudokuBoard) {
        try {
            SudokuBoard deadEndBoardLog = sudokuBoard.deepCopy();
            deadEndBoards.add(deadEndBoardLog);
        } catch (CloneNotSupportedException e) {
            System.out.println(e);
        }
    }

    public void showRandomSolution() {
        Random random = new Random();
        final int solutionNo = random.nextInt(solvedBoards.size());
        solvedBoards.get(solutionNo).displayBoard();
    }

    public List<SudokuBoard> getBoardStepLog() {
        return boardStepLog;
    }

    public List<SudokuBoard> getSolvedBoards() {
        return solvedBoards;
    }

    public List<SudokuBoard> getDeadEndBoards() {
        return deadEndBoards;
    }

    public static BoardsDatabase getInstance() {
        if (boardsDatabaseInstance == null) {
            synchronized (SudokuMenu.class) {
                if (boardsDatabaseInstance == null) {
                    boardsDatabaseInstance = new BoardsDatabase();
                }
            }
        }
        return boardsDatabaseInstance;
    }
}
