package gamecode.boardbuilding;

import java.util.ArrayList;
import java.util.List;

public final class SudokuBoard implements Cloneable {
    private SudokuBoardCell[][] sudokuBoard;

    public SudokuBoard() {
        this.sudokuBoard = createNewBoard();
    }

    private SudokuBoardCell[][] createNewBoard() {
        final SudokuBoardCell[][] sudokuBoard = new SudokuBoardCell[9][9];
        for (int n = 0; n < 9; n++) {
            for (int m = 0; m < 9; m++) {
                sudokuBoard[n][m] = new SudokuBoardCell();
            }
        }
        return sudokuBoard;
    }

    private String rowSeparator() {
        return "- - - | - - - | - - -";
    }

    public void displayBoard() {
        for (int n = 0; n < 9; n++) {
            if (n == 3 || n == 6) {
                System.out.println(rowSeparator());
            }
            for (int m = 0; m < 9; m++) {
                if (m == 3 || m == 6) {
                    System.out.print("| ");
                }
                System.out.print(sudokuBoard[n][m] + " ");
            }
            System.out.println();
        }
    }

    public SudokuBoard deepCopy() throws CloneNotSupportedException {
        SudokuBoard clonedSudokuBoard = (SudokuBoard) super.clone();

        SudokuBoardCell[][] clonedList = new SudokuBoardCell[9][9];
        for (int n = 0; n < 9; n++) {
            for (int m = 0; m < 9; m++) {
                clonedList[n][m] = new SudokuBoardCell();
            }
        }
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                clonedList[row][col].setCellValue(sudokuBoard[row][col].getCellValue());
                List<Integer> possibleValuesClone = new ArrayList<>();
                for (Integer value : sudokuBoard[row][col].getPossibleValues()) {
                    possibleValuesClone.add(value);
                }
                clonedList[row][col].setPossibleValues(possibleValuesClone);
            }
        }
        clonedSudokuBoard.setSudokuBoard(clonedList);

        return clonedSudokuBoard;
    }


    public SudokuBoardCell[][] getSudokuBoard() {
        return sudokuBoard;
    }

    private void setSudokuBoard(SudokuBoardCell[][] sudokuBoard) {
        this.sudokuBoard = sudokuBoard;
    }
}
