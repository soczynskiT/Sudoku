package gamecode.databases.databasedisplay;

import gamecode.databases.BoardsDatabase;

import java.util.Random;

public class RandomDeadEndDisplay implements DatabaseDisplay {
    @Override
    public void showRecord(final BoardsDatabase boardsDatabase) {
        if (!boardsDatabase.getDeadEndBoards().isEmpty()) {
            Random random = new Random();
            final int solutionNo = random.nextInt(boardsDatabase.getDeadEndBoards().size());
            boardsDatabase.getDeadEndBoards().get(solutionNo).displayBoard();
            System.out.println("Required computer calculation for this solution: " +
                    boardsDatabase.getDeadEndBoards().get(solutionNo).getAlgorithmMovesID() + "\n");
        } else {
            System.out.println("No possible dead-ends for this board !\n");
        }
    }
}
