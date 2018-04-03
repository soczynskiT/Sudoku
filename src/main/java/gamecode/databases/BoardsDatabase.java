package gamecode.databases;

import gamecode.SudokuMenu;
import gamecode.boardbuilding.SudokuBoard;
import gamecode.databases.databasedisplay.DatabaseDisplay;

import java.util.ArrayList;
import java.util.List;

public final class BoardsDatabase {
    private final List<SudokuBoard> boardStepLog;
    private final List<SudokuBoard> solvedBoards;
    private final List<SudokuBoard> deadEndBoards;
    private static BoardsDatabase boardsDatabaseInstance = null;

    private BoardsDatabase() {
        this.boardStepLog = new ArrayList<>();
        this.solvedBoards = new ArrayList<>();
        this.deadEndBoards = new ArrayList<>();

    }

    public void saveBoardState(final SudokuBoard sudokuBoard) {
        try {
            SudokuBoard logBoard = sudokuBoard.deepCopy();
            boardStepLog.add(logBoard);
        } catch (CloneNotSupportedException e) {
            System.out.println("Clone not supported !");
        }
    }

    public void saveSolvedBoard(final SudokuBoard sudokuBoard) {
        try {
            SudokuBoard solvedBoardLog = sudokuBoard.deepCopy();
            solvedBoards.add(solvedBoardLog);
        } catch (CloneNotSupportedException e) {
            System.out.println("Clone not supported !");
        }
    }

    public void saveDeadEndBoard(final SudokuBoard sudokuBoard) {
        try {
            SudokuBoard deadEndBoardLog = sudokuBoard.deepCopy();
            deadEndBoards.add(deadEndBoardLog);
        } catch (CloneNotSupportedException e) {
            System.out.println("Clone not supported !");
        }
    }

    public void clearDatabases() {
        boardStepLog.clear();
        deadEndBoards.clear();
        solvedBoards.clear();
    }

    public void databaseRecordsDisplay(DatabaseDisplay databaseDisplay) {
        databaseDisplay.showRecord(boardsDatabaseInstance);
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
