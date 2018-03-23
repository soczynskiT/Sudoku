package gamecode.boardbuilding;

import enums.BoardBlocks;

import java.util.*;
import java.util.stream.Collectors;

public final class CellEntry {
    private final int rowNo;
    private final int columnNo;
    private final int value;
    private final BoardBlocks blockNo;

    public CellEntry(final int rowNo, final int columnNo, final int value) {
        this.rowNo = rowNo;
        this.columnNo = columnNo;
        this.value = value;
        this.blockNo = checkBlockNo();
    }

    private BoardBlocks checkBlockNo() {
        return Arrays.stream(BoardBlocks.values())
                .filter(r -> r.getRowsIndicators().contains(rowNo))
                .filter(c -> c.getColumnsIndicators().contains(columnNo))
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

    public BoardBlocks getBlockNo() {
        return blockNo;
    }
}