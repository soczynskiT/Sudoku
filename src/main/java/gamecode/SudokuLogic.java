package gamecode;

import gamecode.boardbuilding.CellEntryValidator;
import gamecode.boardbuilding.CellsFiller;
import gamecode.boardbuilding.SudokuBoard;
import usermoveslogic.UserMoveReader;

public final class SudokuLogic {
    private SudokuBoard sudokuBoard = null;
    private final UserMoveReader userMoveReader;
    private final CellsFiller cellsFiller;
    private final CellEntryValidator cellEntryValidator;
    private static SudokuLogic sudokuLogicInstance = null;

    private SudokuLogic() {
        this.userMoveReader = new UserMoveReader();
        this.cellsFiller = CellsFiller.getInstance();
        this.cellEntryValidator = CellEntryValidator.getInstance();
    }

    public void solveNewSudoku() {
        prepareNewBoard();
        System.out.println("Board to solve:");
        sudokuBoard.displayBoard();
        System.out.println("Solve logic under construction :)");
    }

    private void prepareNewBoard() {
        createNewBoard();
        cellsFiller.fillUpCells(userMoveReader, cellEntryValidator, sudokuBoard);
    }

    private void createNewBoard() {
        this.sudokuBoard = new SudokuBoard();
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
