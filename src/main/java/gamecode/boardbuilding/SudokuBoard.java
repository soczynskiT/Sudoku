package gamecode.boardbuilding;

public final class SudokuBoard {
    private final SudokuBoardCell[][] sudokuBoard;

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


    public SudokuBoardCell[][] getSudokuBoard() {
        return sudokuBoard;
    }
}
