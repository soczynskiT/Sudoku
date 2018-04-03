package gamecode.boardbuilding.boardelements;

import gamecode.boardbuilding.SudokuBoard;

import java.util.stream.Collectors;

public final class CellEntry {
    private final int rowNo;
    private final int columnNo;
    private final int value;
    private final BoardBlock boardBlock;

    public CellEntry(final int rowNo, final int columnNo, final int value, final SudokuBoard sudokuBoard) {
        this.rowNo = rowNo;
        this.columnNo = columnNo;
        this.value = value;
        this.boardBlock = checkBlock(sudokuBoard);
    }

    private BoardBlock checkBlock(final SudokuBoard sudokuBoard) {
        return sudokuBoard.getBoardBlocksList().stream()
                .filter(r -> r.getRowsIndicators().contains(rowNo))
                .filter(c -> c.getColsIndicators().contains(columnNo))
                .collect(Collectors.toList()).get(0);
    }

    public int getRowNo() {
        return rowNo;
    }

    public int getColumnNo() {
        return columnNo;
    }

    public int getValue() {
        return value;
    }

    public BoardBlock getBlockNo() {
        return boardBlock;
    }
}