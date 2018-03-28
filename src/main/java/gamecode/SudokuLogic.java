package gamecode;

import gamecode.boardbuilding.*;
import gamecode.boardsolving.SolverByGuess;
import gamecode.boardsolving.SolverByLogic;
import gamecode.databases.BoardsDatabase;
import usermoveslogic.UserMoveReader;

public final class SudokuLogic {
    private SudokuBoard sudokuBoard = null;
    private final UserMoveReader userMoveReader;
    private final CellsFiller cellsFiller;
    private final CellEntryValidator cellEntryValidator;
    private final SolverByLogic solverByLogic;
    private final SolverByGuess solverByGuess;
    private final BoardsDatabase boardsDatabase;
    private final RandomBoardBuilder randomBoardBuilder;

    private static SudokuLogic sudokuLogicInstance = null;

    private SudokuLogic() {
        this.userMoveReader = new UserMoveReader();
        this.cellsFiller = CellsFiller.getInstance();
        this.cellEntryValidator = CellEntryValidator.getInstance();
        this.solverByLogic = SolverByLogic.getInstance();
        this.solverByGuess = SolverByGuess.getInstance();
        this.boardsDatabase = BoardsDatabase.getInstance();
        this.randomBoardBuilder = RandomBoardBuilder.getInstance();

    }

    public void solveNewSudoku() {
        System.out.println("\nBoard to solve");
        sudokuBoard.displayBoard();
        System.out.println("\nCalculating solutions...");

        boolean areAllMovesDone = false;
        while (!areAllMovesDone) {
            final boolean logicOne = solverByLogic.reduceNoOfPossibleMovesInCells(sudokuBoard);
            final boolean logicTwo = solverByLogic.fulfillCellsWithOnePossibility(sudokuBoard);

            if (!logicOne && !logicTwo) {
                final boolean guessOne = solverByGuess.guessFirstPossibleValue(sudokuBoard, boardsDatabase);
                if (!guessOne && checkIfGameIsSolved()) {
                    boardsDatabase.saveSolvedBoard(sudokuBoard);
                    areAllMovesDone = goBackToPreviousLog();
                } else if (!guessOne && !checkIfGameIsSolved()) {
                    boardsDatabase.saveDeadEndBoard(sudokuBoard);
                    areAllMovesDone = goBackToPreviousLog();
                }
            }
        }
        System.out.println("Possible solved boards: " + boardsDatabase.getSolvedBoards().size());
        System.out.println("Possible dead-end boards: " + boardsDatabase.getDeadEndBoards().size());
    }

    private boolean goBackToPreviousLog() {
        if (boardsDatabase.getBoardStepLog().size() > 0) {
            this.sudokuBoard = boardsDatabase.getBoardStepLog().get(boardsDatabase.getBoardStepLog().size() - 1);
            boardsDatabase.getBoardStepLog().remove(boardsDatabase.getBoardStepLog().size() - 1);
            return false;
        } else {
            System.out.println("All possible moves calculated.");
            return true;
        }
    }

    private boolean checkIfGameIsSolved() {
        boolean isGameSolved = true;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                SudokuBoardCell underCheckCell = sudokuBoard.getSudokuBoard()[row][col];
                if (underCheckCell.getCellValue() == 0) {
                    isGameSolved = false;
                }
            }
        }
        return isGameSolved;
    }

    public void prepareNewBoard() {
        createNewBoard();
        cellsFiller.fillUpCells(userMoveReader, cellEntryValidator, sudokuBoard);
    }

    private void createNewBoard() {
        this.sudokuBoard = new SudokuBoard();
    }

    private void clearBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                sudokuBoard.getSudokuBoard()[row][col].setCellValue(0);
            }
        }
    }

    public void generateRandomBoard() {
        createNewBoard();
        randomBoardBuilder.generateRandomBoard(userMoveReader, cellEntryValidator, sudokuBoard);
        sudokuBoard.displayBoard();
    }

    public void setTestBoard() {
        createNewBoard();
        sudokuBoard.getSudokuBoard()[0][0].setCellValue(5);
        sudokuBoard.getSudokuBoard()[0][1].setCellValue(3);
        sudokuBoard.getSudokuBoard()[0][4].setCellValue(7);
        sudokuBoard.getSudokuBoard()[1][0].setCellValue(6);
        sudokuBoard.getSudokuBoard()[1][3].setCellValue(1);
        sudokuBoard.getSudokuBoard()[1][4].setCellValue(9);
        sudokuBoard.getSudokuBoard()[1][5].setCellValue(5);
        sudokuBoard.getSudokuBoard()[2][1].setCellValue(9);
        sudokuBoard.getSudokuBoard()[2][2].setCellValue(8);
        sudokuBoard.getSudokuBoard()[2][7].setCellValue(6);
        sudokuBoard.getSudokuBoard()[3][0].setCellValue(8);
        sudokuBoard.getSudokuBoard()[3][4].setCellValue(6);
        sudokuBoard.getSudokuBoard()[3][8].setCellValue(3);
        sudokuBoard.getSudokuBoard()[4][0].setCellValue(4);
        sudokuBoard.getSudokuBoard()[4][3].setCellValue(8);
        sudokuBoard.getSudokuBoard()[4][5].setCellValue(3);
        sudokuBoard.getSudokuBoard()[5][8].setCellValue(6);
        sudokuBoard.getSudokuBoard()[6][1].setCellValue(6);
        sudokuBoard.getSudokuBoard()[6][6].setCellValue(2);
        sudokuBoard.getSudokuBoard()[6][7].setCellValue(8);
        sudokuBoard.getSudokuBoard()[7][3].setCellValue(4);
        sudokuBoard.getSudokuBoard()[7][4].setCellValue(1);
        sudokuBoard.getSudokuBoard()[7][5].setCellValue(9);
        sudokuBoard.displayBoard();
    }

    public static SudokuLogic getInstance() {
        if (sudokuLogicInstance == null) {
            synchronized (SudokuMenu.class) {
                if (sudokuLogicInstance == null) {
                    sudokuLogicInstance = new SudokuLogic();
                }
            }
        }
        return sudokuLogicInstance;
    }
}
