package gamecode.boardsolving;

import enums.BoardBlocks;
import gamecode.SudokuMenu;
import gamecode.boardbuilding.SudokuBoard;
import gamecode.boardbuilding.SudokuBoardCell;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class SolverByLogic {
    private static SolverByLogic solverByLogicInstance = null;

    private SolverByLogic() {
    }

    public boolean fulfillCellsWithOnePossibility(SudokuBoard sudokuBoard) {
        boolean isAnyCellChanged = false;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                SudokuBoardCell underCheckCell = sudokuBoard.getSudokuBoard()[row][col];

                if (underCheckCell.getCellValue() == 0 && underCheckCell.getPossibleValues().size() == 1) {
                    final int newValue = sudokuBoard.getSudokuBoard()[row][col].getPossibleValues().get(0);
                    underCheckCell.setCellValue(newValue);
                    reduceNoOfPossibleMovesInCells(sudokuBoard);
                    isAnyCellChanged = true;
                }
            }
        }
        return isAnyCellChanged;
    }

    public boolean reduceNoOfPossibleMovesInCells(SudokuBoard sudokuBoard) {
        int changeIDentifier = 0;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                SudokuBoardCell underCheckCell = sudokuBoard.getSudokuBoard()[row][col];
                if (underCheckCell.getCellValue() != 0) {
                    final Integer value = underCheckCell.getCellValue();
                    final int logic1 = removeAllPossibleMovesFromFulfilledCells(row, col, sudokuBoard);
                    final int logic2 = removeUnavailableMovesFromRow(row, value, sudokuBoard);
                    final int logic3 = removeUnavailableMovesFromColumn(col, value, sudokuBoard);
                    final int logic4 = removeUnavailableMovesFromBlock(row, col, value, sudokuBoard);
                    changeIDentifier = logic1 + logic2 + logic3 + logic4;
                }
            }
        }
        return changeIDentifier != 0;
    }

    private int removeAllPossibleMovesFromFulfilledCells(int row, int col, SudokuBoard sudokuBoard) {
        int changeIdentifier = 0;
        if (sudokuBoard.getSudokuBoard()[row][col].getPossibleValues().size() != 0) {
            sudokuBoard.getSudokuBoard()[row][col].getPossibleValues().clear();
            changeIdentifier++;
        }
        return changeIdentifier;
    }

    private int removeUnavailableMovesFromRow(int row, Integer value, SudokuBoard sudokuBoard) {
        int changeIdentifier = 0;
        for (int col = 0; col < 9; col++) {
            if (sudokuBoard.getSudokuBoard()[row][col].getPossibleValues().contains(value)) {
                sudokuBoard.getSudokuBoard()[row][col].getPossibleValues().remove(value);
                changeIdentifier++;
            }
        }
        return changeIdentifier;
    }

    private int removeUnavailableMovesFromColumn(int col, Integer value, SudokuBoard sudokuBoard) {
        int changeIdentifier = 0;
        for (int row = 0; row < 9; row++) {
            if (sudokuBoard.getSudokuBoard()[row][col].getPossibleValues().contains(value)) {
                sudokuBoard.getSudokuBoard()[row][col].getPossibleValues().remove(value);
                changeIdentifier++;
            }
        }
        return changeIdentifier;
    }

    private BoardBlocks checkBlockNo(int row, int col) {
        return Arrays.stream(BoardBlocks.values())
                .filter(r -> r.getRowsIndicators().contains(row))
                .filter(c -> c.getColumnsIndicators().contains(col))
                .collect(Collectors.toList()).get(0);
    }

    private int removeUnavailableMovesFromBlock(int row, int col, Integer value, SudokuBoard sudokuBoard) {
        final BoardBlocks block = checkBlockNo(row, col);
        int changeIdentifier = 0;

        final int firstRowToCheck = block.getRowsIndicators().get(0);
        final int firstColToCheck = block.getColumnsIndicators().get(0);

        for (int r = firstRowToCheck; r < firstRowToCheck + 3; r++) {
            for (int c = firstColToCheck; c < firstColToCheck + 3; c++) {
                if (sudokuBoard.getSudokuBoard()[r][c].getPossibleValues().contains(value)) {
                    sudokuBoard.getSudokuBoard()[r][c].getPossibleValues().remove(value);
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
