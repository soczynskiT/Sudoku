package gamecode.boardsolving;

import gamecode.SudokuMenu;
import gamecode.boardbuilding.SudokuBoard;
import gamecode.boardbuilding.boardelements.SudokuBoardCell;
import gamecode.databases.BoardsDatabase;

import java.util.ArrayList;
import java.util.List;

public final class SolverByGuess {

    private static SolverByGuess solverByGuessInstance = null;

    private SolverByGuess() {
    }

    public boolean guessFirstPossibleValue(final SudokuBoard sudokuBoard, final BoardsDatabase boardsDatabase) {
        boolean isAnyCellChanged = false;
        sudokuBoard.setAlgorithmMovesID(sudokuBoard.getAlgorithmMovesID() + 1);

        if (!checkForDeadEndMove(sudokuBoard)) {
            final SudokuBoardCell underCheckCell = getFirstCellWithPossibleMoves(sudokuBoard);
            if (underCheckCell.getPossibleValuesList().size() > 0) {
                final Integer value = underCheckCell.getPossibleValuesList().get(0);
                sudokuBoard.setAlgorithmMovesID(sudokuBoard.getAlgorithmMovesID() + 1);

                underCheckCell.getPossibleValuesList().remove(value);
                boardsDatabase.saveBoardState(sudokuBoard);
                underCheckCell.setCellValue(value);

                isAnyCellChanged = true;
            }
        }
        return isAnyCellChanged;
    }

    private boolean checkForDeadEndMove(final SudokuBoard sudokuBoard) {
        boolean isAnyCellWithLogicError = false;
        for (int row = 0; row < sudokuBoard.getBoardSideSize(); row++) {
            for (int col = 0; col < sudokuBoard.getBoardSideSize(); col++) {
                sudokuBoard.setAlgorithmMovesID(sudokuBoard.getAlgorithmMovesID() + 1);

                if (sudokuBoard.getSudokuBoard()[row][col].getPossibleValuesList().size() == 0 &&
                        sudokuBoard.getSudokuBoard()[row][col].getCellValue() == 0) {
                    isAnyCellWithLogicError = true;
                }
            }
        }
        return isAnyCellWithLogicError;
    }

    private List<SudokuBoardCell> getListOfCellsWithPossibleMoves(final SudokuBoard sudokuBoard) {
        final List<SudokuBoardCell> foundedCellsList = new ArrayList<>();
        for (int row = 0; row < sudokuBoard.getBoardSideSize(); row++) {
            for (int col = 0; col < sudokuBoard.getBoardSideSize(); col++) {
                sudokuBoard.setAlgorithmMovesID(sudokuBoard.getAlgorithmMovesID() + 1);

                if (sudokuBoard.getSudokuBoard()[row][col].getPossibleValuesList().size() > 0) {
                    foundedCellsList.add(sudokuBoard.getSudokuBoard()[row][col]);
                }
            }
        }
        return foundedCellsList;
    }

    private SudokuBoardCell getFirstCellWithPossibleMoves(final SudokuBoard sudokuBoard) {
        List<SudokuBoardCell> foundedCellsList = getListOfCellsWithPossibleMoves(sudokuBoard);
        sudokuBoard.setAlgorithmMovesID(sudokuBoard.getAlgorithmMovesID() + 1);

        if (foundedCellsList.size() > 0) {
            return foundedCellsList.get(0);

        } else {
            SudokuBoardCell emptyCell = new SudokuBoardCell(sudokuBoard.getBoardSideSize());
            emptyCell.getPossibleValuesList().clear();
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
