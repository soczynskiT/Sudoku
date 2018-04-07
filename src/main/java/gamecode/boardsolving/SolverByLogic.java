package gamecode.boardsolving;

import gamecode.SudokuMenu;
import gamecode.boardbuilding.boardelements.BoardBlock;
import gamecode.boardbuilding.SudokuBoard;
import gamecode.boardbuilding.boardelements.SudokuBoardCell;

import java.util.List;
import java.util.stream.Collectors;

public final class SolverByLogic {
    private static SolverByLogic solverByLogicInstance = null;

    private SolverByLogic() {
    }

    public boolean fulfillCellsWithOnePossibility(final SudokuBoard sudokuBoard) {
        boolean isAnyCellChanged = false;
        for (int row = 0; row < sudokuBoard.getBoardSideSize(); row++) {
            for (int col = 0; col < sudokuBoard.getBoardSideSize(); col++) {
                sudokuBoard.setAlgorithmMovesID(sudokuBoard.getAlgorithmMovesID() + 1);

                SudokuBoardCell underCheckCell = sudokuBoard.getSudokuBoard()[row][col];
                if (underCheckCell.getCellValue() == 0 && underCheckCell.getPossibleValuesList().size() == 1) {
                    final int newValue = sudokuBoard.getSudokuBoard()[row][col].getPossibleValuesList().get(0);
                    underCheckCell.setCellValue(newValue);
                    reduceNoOfPossibleMovesInCells(sudokuBoard);
                    isAnyCellChanged = true;
                }
            }
        }
        return isAnyCellChanged;
    }

    public boolean reduceNoOfPossibleMovesInCells(final SudokuBoard sudokuBoard) {
        int changeIdentifier = 0;
        for (int row = 0; row < sudokuBoard.getBoardSideSize(); row++) {
            for (int col = 0; col < sudokuBoard.getBoardSideSize(); col++) {
                SudokuBoardCell underCheckCell = sudokuBoard.getSudokuBoard()[row][col];
                sudokuBoard.setAlgorithmMovesID(sudokuBoard.getAlgorithmMovesID() + 1);

                if (underCheckCell.getCellValue() != 0) {
                    final Integer value = underCheckCell.getCellValue();
                    final int logic1Identifier = removeAllPossibleMovesFromFulfilledCells(row, col, sudokuBoard);
                    final int logic2Identifier = removeUnavailableMovesFromRow(row, value, sudokuBoard);
                    final int logic3Identifier = removeUnavailableMovesFromColumn(col, value, sudokuBoard);
                    final int logic4Identifier = removeUnavailableMovesFromBlock(row, col, value, sudokuBoard);
                    changeIdentifier = logic1Identifier + logic2Identifier + logic3Identifier + logic4Identifier;
                }
            }
        }
        return changeIdentifier != 0;
    }

    private int removeAllPossibleMovesFromFulfilledCells(final int row, final int col, final SudokuBoard sudokuBoard) {
        int changeIdentifier = 0;
        sudokuBoard.setAlgorithmMovesID(sudokuBoard.getAlgorithmMovesID() + 1);

        if (sudokuBoard.getSudokuBoard()[row][col].getPossibleValuesList().size() != 0) {
            sudokuBoard.getSudokuBoard()[row][col].getPossibleValuesList().clear();
            changeIdentifier++;
        }
        return changeIdentifier;
    }

    private int removeUnavailableMovesFromRow(final int row, final Integer value, final SudokuBoard sudokuBoard) {
        int changeIdentifier = 0;
        for (int col = 0; col < sudokuBoard.getBoardSideSize(); col++) {
            sudokuBoard.setAlgorithmMovesID(sudokuBoard.getAlgorithmMovesID() + 1);

            if (sudokuBoard.getSudokuBoard()[row][col].getPossibleValuesList().contains(value)) {
                sudokuBoard.getSudokuBoard()[row][col].getPossibleValuesList().remove(value);
                changeIdentifier++;
            }
        }
        return changeIdentifier;
    }

    private int removeUnavailableMovesFromColumn(final int col, final Integer value, final SudokuBoard sudokuBoard) {
        int changeIdentifier = 0;
        for (int row = 0; row < sudokuBoard.getBoardSideSize(); row++) {
            sudokuBoard.setAlgorithmMovesID(sudokuBoard.getAlgorithmMovesID() + 1);

            if (sudokuBoard.getSudokuBoard()[row][col].getPossibleValuesList().contains(value)) {
                sudokuBoard.getSudokuBoard()[row][col].getPossibleValuesList().remove(value);
                changeIdentifier++;
            }
        }
        return changeIdentifier;
    }

    private BoardBlock checkBlockNo(final int row, final int col, final SudokuBoard sudokuBoard) {
        return sudokuBoard.getBoardBlocksList().stream()
                .filter(r -> r.getRowsIndicators().contains(row))
                .filter(c -> c.getColsIndicators().contains(col))
                .collect(Collectors.toList()).get(0);
    }

    private int removeUnavailableMovesFromBlock(final int row, final int col, final Integer value,
                                                final SudokuBoard sudokuBoard) {
        final BoardBlock block = checkBlockNo(row, col, sudokuBoard);
        int changeIdentifier = 0;

        final List<Integer> rowsToCheck = block.getRowsIndicators();
        final List<Integer> colsToCheck = block.getColsIndicators();

        for (int r = rowsToCheck.get(0); r < rowsToCheck.get(rowsToCheck.size() - 1) + 1; r++) {
            for (int c = colsToCheck.get(0); c < colsToCheck.get(colsToCheck.size() - 1) + 1; c++) {
                sudokuBoard.setAlgorithmMovesID(sudokuBoard.getAlgorithmMovesID() + 1);

                if (sudokuBoard.getSudokuBoard()[r][c].getPossibleValuesList().contains(value)) {
                    sudokuBoard.getSudokuBoard()[r][c].getPossibleValuesList().remove(value);
                    changeIdentifier++;
                }
            }
        }
        return changeIdentifier;
    }

    public static SolverByLogic getInstance() {
        if (solverByLogicInstance == null) {
            synchronized (SudokuMenu.class) {
                if (solverByLogicInstance == null) {
                    solverByLogicInstance = new SolverByLogic();
                }
            }
        }
        return solverByLogicInstance;
    }
}
