package gamecode.databases.databasedisplay;

import gamecode.databases.BoardsDatabase;
import gamecode.databases.databasedisplay.DatabaseDisplay;

import java.util.Random;

public class RandomSolutionDisplay implements DatabaseDisplay {
    @Override
    public void showRecord(final BoardsDatabase boardsDatabase) {
        if (!boardsDatabase.getSolvedBoards().isEmpty()) {
            Random random = new Random();
            final int solutionNo = random.nextInt(boardsDatabase.getSolvedBoards().size());
            boardsDatabase.getSolvedBoards().get(solutionNo).displayBoard();
            System.out.println("Required computer calculation for this solution: " +
                    boardsDatabase.getSolvedBoards().get(solutionNo).getAlgorithmMovesID() + "\n");
        } else {
            System.out.println("No possible solutions for this board !\n");
        }
    }
}
