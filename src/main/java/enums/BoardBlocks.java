package enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum BoardBlocks {
    BLOCK_ONE(0, 1, 2, 0, 1, 2),
    BLOCK_TWO(0, 1, 2, 3, 4, 5),
    BLOCK_THREE(0, 1, 2, 6, 7, 8),

    BLOCK_FOUR(3, 4, 5, 0, 1, 2),
    BLOCK_FIVE(3, 4, 5, 3, 4, 5),
    BLOCK_SIX(3, 4, 5, 6, 7, 8),

    BLOCK_SEVEN(6, 7, 8, 0, 1, 2),
    BLOCK_EIGHT(6, 7, 8, 3, 4, 5),
    BLOCK_NINE(6, 7, 8, 6, 7, 8);

    private final List<Integer> rowsIndicators;
    private final List<Integer> columnsIndicators;

    BoardBlocks(final int row1, final int row2, final int row3, final int col1, final int col2, final int col3) {
        this.rowsIndicators = new ArrayList<>(Arrays.asList(row1, row2, row3));
        this.columnsIndicators = new ArrayList<>(Arrays.asList(col1, col2, col3));
    }

    public List<Integer> getRowsIndicators() {
        return rowsIndicators;
    }

    public List<Integer> getColumnsIndicators() {
        return columnsIndicators;
    }
}
