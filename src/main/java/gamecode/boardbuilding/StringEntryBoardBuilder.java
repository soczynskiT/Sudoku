package gamecode.boardbuilding;

import gamecode.SudokuMenu;
import gamecode.boardbuilding.boardelements.CellEntry;
import usermoveslogic.UserMoveReader;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public final class StringEntryBoardBuilder {
    private static StringEntryBoardBuilder stringEntryBoardBuilderInstance = null;

    private StringEntryBoardBuilder() {
    }

    public void buildSudokuBoardByStream(final UserMoveReader userMoveReader, final CellEntryValidator cellEntryValidator,
                                         final SudokuBoard sudokuBoard) {
        int cellsToFillUp = setNumberOfCellsToFillByUser(userMoveReader, sudokuBoard);
        int correctEntries = 0;
        while (cellsToFillUp > 0) {
            System.out.println("Please enter stream with numbers separate by spaces (eg. 1 5 8 9 3)");
            final String stream = receiveValidatedString(userMoveReader, sudokuBoard);
            final ArrayList<CellEntry> cellEntriesList = transformStreamForEntries(stream, sudokuBoard);
            for (CellEntry cellEntry : cellEntriesList) {
                final boolean isEntryCorrect = cellEntryValidator.validateEntry(cellEntry, sudokuBoard);
                if (isEntryCorrect && cellsToFillUp > 0) {
                    sudokuBoard.getSudokuBoard()[cellEntry.getRowNo()][cellEntry.getColumnNo()].setCellValue(cellEntry.getValue());
                    cellsToFillUp--;
                    correctEntries++;
                }
            }
            System.out.println("Number of correct entries: " + correctEntries);
            sudokuBoard.displayBoard();
        }
    }

    private String receiveValidatedString(final UserMoveReader userMoveReader, final SudokuBoard sudokuBoard) {
        boolean streamIsCorrect = false;
        String stream = "";
        while (!streamIsCorrect) {
            final String newStream = userMoveReader.readString().trim();
            if (validateStringForNumbersOnly(newStream) && validateValuesBound(newStream, sudokuBoard)) {
                streamIsCorrect = true;
                stream = newStream;
            }
        }
        return stream;
    }

    private boolean validateStringForNumbersOnly(final String string) {
        try {
            final String testString = string.replaceAll("\\s+", "");
            final BigInteger testInt = new BigInteger(testString);
            return true;
        } catch (NumberFormatException ne) {
            System.out.println("Incorrect stream format, only digits allowed. Try again:");
            return false;
        }
    }

    private boolean validateValuesBound(final String string, final SudokuBoard sudokuBoard) {
        final ArrayList<String> list = new ArrayList<>(Arrays.asList(string.split("\\s+")));

        boolean isValueCorrect = true;
        for (String value : list) {
            if (Integer.parseInt(value) <= 0 || Integer.parseInt(value) > sudokuBoard.getBoardSideSize()) {
                isValueCorrect = false;
            }
        }
        if (!isValueCorrect) {
            System.out.println("One of the numbers in stream are out of possible bounds. Try again:");
        }
        return isValueCorrect;
    }

    private ArrayList<CellEntry> transformStreamForEntries(final String stream, final SudokuBoard sudokuBoard) {
        final ArrayList<String> list = new ArrayList<>(Arrays.asList(stream.split("\\s+")));
        final ArrayList<CellEntry> cellEntriesList = new ArrayList<>();
        while (list.size() >= 3) {
            final int rowNo = Integer.parseInt(list.get(0)) - 1;
            list.remove(0);
            final int colNo = Integer.parseInt(list.get(0)) - 1;
            list.remove(0);
            final int value = Integer.parseInt(list.get(0));
            list.remove(0);

            CellEntry cellEntry = new CellEntry(rowNo, colNo, value, sudokuBoard);
            cellEntriesList.add(cellEntry);
        }
        if (list.size() > 0) {
            System.out.println("Last digits could not be transformed into move: " + list.toString());
        }
        return cellEntriesList;
    }

    private int setNumberOfCellsToFillByUser(final UserMoveReader userMoveReader, final SudokuBoard sudokuBoard) {
        final Double minimumCellsToFillUp = (sudokuBoard.getBoardSideSize() * sudokuBoard.getBoardSideSize()) * 0.2;
        final int minBound = minimumCellsToFillUp.intValue();
        final int maxBound = minBound * 4;
        System.out.println("Enter number of cells would you like to fill up" +
                " (" + minBound + " - " + maxBound + ")");
        return userMoveReader.readNumberOfBounds(minBound, maxBound);
    }

    public static StringEntryBoardBuilder getInstance() {
        if (stringEntryBoardBuilderInstance == null) {
            synchronized (SudokuMenu.class) {
                if (stringEntryBoardBuilderInstance == null) {
                    stringEntryBoardBuilderInstance = new StringEntryBoardBuilder();
                }
            }
        }
        return stringEntryBoardBuilderInstance;
    }
}
