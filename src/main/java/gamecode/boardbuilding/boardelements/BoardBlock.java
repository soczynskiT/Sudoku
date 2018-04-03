package gamecode.boardbuilding.boardelements;

import java.util.ArrayList;
import java.util.List;

public final class BoardBlock {
    private final List<Integer> rowsIndicators;
    private final List<Integer> colsIndicators;

    public BoardBlock() {
        this.rowsIndicators = new ArrayList<>();
        this.colsIndicators = new ArrayList<>();
    }

    public void addRowIndicator(final int rowIndicator) {
        rowsIndicators.add(rowIndicator);
    }

    public void addColIndicator(final int colIndicator) {
        colsIndicators.add(colIndicator);
    }

    public List<Integer> getRowsIndicators() {
        return rowsIndicators;
    }

    public List<Integer> getColsIndicators() {
        return colsIndicators;
    }
}
