package gamecode.databases.databasedisplay;

import gamecode.boardbuilding.SudokuBoard;
import gamecode.databases.BoardsDatabase;

import java.util.stream.Collectors;

public class EasiestSolutionDisplay implements DatabaseDisplay {
    @Override
    public void showRecord(final BoardsDatabase boardsDatabase) {
        if (!boardsDatabase.getSolvedBoards().isEmpty()) {
            int lowestValue = boardsDatabase.getSolvedBoards().stream()
                    .map(SudokuBoard::getAlgorithmMovesID)
                    .sorted()
                    .collect(Collectors.toList()).get(0);
            for (SudokuBoard board : boardsDatabase.getSolvedBoards()) {
                if (board.getAlgorithmMovesID() == lowestValue) {
                    board.displayBoard();
                    System.out.println("Required computer calculations: " + board.getAlgorithmMovesID() + "\n");
                }
            }
        } else {
            System.out.println("No possible solutions for this board !\n");
        }
    }
}
