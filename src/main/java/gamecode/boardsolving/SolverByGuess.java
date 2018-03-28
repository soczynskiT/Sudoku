package gamecode.boardsolving;

import gamecode.SudokuMenu;
import gamecode.boardbuilding.SudokuBoard;
import gamecode.boardbuilding.SudokuBoardCell;
import gamecode.databases.BoardsDatabase;

import java.util.ArrayList;
import java.util.List;

public final class SolverByGuess {

    private static SolverByGuess solverByGuessInstance = null;

    private SolverByGuess() {
    }

    public boolean guessFirstPossibleValue(SudokuBoard sudokuBoard, BoardsDatabase boardsDatabase) {
        boolean isAnyCellChanged = false;
        if (!checkForDeadEndMove(sudokuBoard)) {
            final SudokuBoardCell underCheckCell = getFirstCellWithPossibleMoves(sudokuBoard);
            if (underCheckCell.getPossibleValues().size() > 0) {
                final Integer value = underCheckCell.getPossibleValues().get(0);

                underCheckCell.getPossibleValues().remove(value);
                boardsDatabase.saveBoardState(sudokuBoard);
                underCheckCell.setCellValue(value);

                isAnyCellChanged = true;
            }
        }
        return isAnyCellChanged;
    }

    private boolean checkForDeadEndMove(SudokuBoard sudokuBoard) {
        boolean isAnyCellWithLogicError = false;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (sudokuBoard.getSudokuBoard()[row][col].getPossibleValues().size() == 0 &&
                        sudokuBoard.getSudokuBoard()[row][col].getCellValue() == 0) {
                    isAnyCellWithLogicError = true;
                }
            }
        }
        return isAnyCellWithLogicError;
    }

    private List<SudokuBoardCell> getListOfCellsWithPossibleMoves(SudokuBoard sudokuBoard) {
        final List<SudokuBoardCell> foundedCellsList = new ArrayList<>();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (sudokuBoard.getSudokuBoard()[row][col].getPossibleValues().size() > 0) {
                    foundedCellsList.add(sudokuBoard.getSudokuBoard()[row][col]);
                }
            }
        }
        return foundedCellsList;
    }

    private SudokuBoardCell getFirstCellWithPossibleMoves(SudokuBoard sudokuBoard) {
        List<SudokuBoardCell> foundedCellsList = getListOfCellsWithPossibleMoves(sudokuBoard);
        if (foundedCellsList.size() > 0) {
            return foundedCellsList.get(0);

        } else {
            SudokuBoardCell emptyCell = new SudokuBoardCell();
            emptyCell.getPossibleValues().clear();
            return emptyCell;
        }
    }
    public static SolverByGuess getInstance() {
        if (solverByGuessInstance == null) {
            synchronized (SudokuMenu.class) {
                if (solverByGuessInstance == null) {
                    solverByGuessInstance = new SolverByGuess();
                }
            }
        }
        return solverByGuessInstance;
    }
}
