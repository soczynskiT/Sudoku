package gamecode.boardbuilding;

import gamecode.boardbuilding.boardelements.BoardBlock;
import gamecode.boardbuilding.boardelements.SudokuBoardCell;

import java.util.ArrayList;
import java.util.List;

public final class SudokuBoard implements Cloneable {
    private SudokuBoardCell[][] sudokuBoard;
    private List<BoardBlock> boardBlocksList;
    private int algorithmMovesID;
    private int boardSideSize;
    private int boardSideBlocksNo;

    public SudokuBoard(int boardSideBlocksNo) {
        this.boardSideBlocksNo = boardSideBlocksNo;
        this.boardSideSize = boardSideBlocksNo * boardSideBlocksNo;
        this.sudokuBoard = createNewBoard();
        this.boardBlocksList = createBoardBlocksList();

    }

    private SudokuBoardCell[][] createNewBoard() {
        final SudokuBoardCell[][] sudokuBoardCells = new SudokuBoardCell[boardSideSize][boardSideSize];
        for (int n = 0; n < boardSideSize; n++) {
            for (int m = 0; m < boardSideSize; m++) {
                sudokuBoardCells[n][m] = new SudokuBoardCell(boardSideSize);
            }
        }
        return sudokuBoardCells;
    }

    private List<BoardBlock> createBoardBlocksList() {
        List<BoardBlock> boardBlocksList = new ArrayList<>();

        for (int row = 0; row < boardSideSize; row++) {
            for (int col = 0; col < boardSideSize; col++) {
                if (row % boardSideBlocksNo == 0 && col % boardSideBlocksNo == 0) {
                    BoardBlock boardBlock = new BoardBlock();
                    int rowIndicator = row;
                    int colIndicator = col;
                    for (int x = 0; x < boardSideBlocksNo; x++) {
                        boardBlock.addRowIndicator(rowIndicator);
                        boardBlock.addColIndicator(colIndicator);
                        rowIndicator++;
                        colIndicator++;
                    }
                    boardBlocksList.add(boardBlock);
                }
            }
        }
        return boardBlocksList;
    }

    private String rowSeparator() {
        StringBuilder rowSeparator = new StringBuilder();
        if (getBoardSideSize() < 4) {
            for (int col = 0; col < boardSideSize; col++) {
                if (col % boardSideBlocksNo == 0 && col != 0) {
                    rowSeparator.append("| - ");
                } else {
                    rowSeparator.append("- ");
                }
            }
        } else {
            for (int col = 0; col < boardSideSize; col++) {
                if (col % boardSideBlocksNo == 0 && col != 0) {
                    rowSeparator.append("| -- ");
                } else {
                    rowSeparator.append("-- ");
                }
            }
        }

        return rowSeparator.toString();
    }

    public void displayBoard() {
        if (boardSideSize >= 4) {
            for (int row = 0; row < boardSideSize; row++) {
                if (row % boardSideBlocksNo == 0 && row != 0) {
                    System.out.println(rowSeparator());
                }
                for (int col = 0; col < boardSideSize; col++) {
                    if (col % boardSideBlocksNo == 0 && col != 0) {
                        System.out.print("| ");
                    }
                    System.out.print(sudokuBoard[row][col] + " ");
                }
                System.out.println();
            }
        }
    }

    public SudokuBoard deepCopy() throws CloneNotSupportedException {
        SudokuBoard clonedSudokuBoard = (SudokuBoard) super.clone();

        clonedSudokuBoard.setAlgorithmMovesID(algorithmMovesID);

        SudokuBoardCell[][] clonedList = new SudokuBoardCell[boardSideSize][boardSideSize];
        for (int n = 0; n < boardSideSize; n++) {
            for (int m = 0; m < boardSideSize; m++) {
                clonedList[n][m] = new SudokuBoardCell(boardSideSize);
            }
        }
        for (int row = 0; row < boardSideSize; row++) {
            for (int col = 0; col < boardSideSize; col++) {
                clonedList[row][col].setCellValue(sudokuBoard[row][col].getCellValue());
                List<Integer> possibleValuesClone = new ArrayList<>();
                for (Integer value : sudokuBoard[row][col].getPossibleValuesList()) {
                    possibleValuesClone.add(value);
                }
                clonedList[row][col].setPossibleValuesList(possibleValuesClone);
            }
        }
        clonedSudokuBoard.setSudokuBoard(clonedList);

        return clonedSudokuBoard;
    }

    public SudokuBoardCell[][] getSudokuBoard() {
        return sudokuBoard;
    }

    private void setSudokuBoard(final SudokuBoardCell[][] sudokuBoard) {
        this.sudokuBoard = sudokuBoard;
    }

    public int getAlgorithmMovesID() {
        return algorithmMovesID;
    }

    public void setAlgorithmMovesID(int algorithmMovesID) {
        this.algorithmMovesID = algorithmMovesID;
    }

    public int getBoardSideSize() {
        return boardSideSize;
    }

    public List<BoardBlock> getBoardBlocksList() {
        return boardBlocksList;
    }
}
