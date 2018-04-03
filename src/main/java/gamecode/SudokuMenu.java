package gamecode;

import gamecode.databases.BoardsDatabase;
import gamecode.databases.databasedisplay.EasiestSolutionDisplay;
import gamecode.databases.databasedisplay.RandomDeadEndDisplay;
import gamecode.databases.databasedisplay.RandomSolutionDisplay;
import usermoveslogic.UserMoveReader;

public final class SudokuMenu {
    private final UserMoveReader userMoveReader;
    private final SudokuLogic sudokuLogic = SudokuLogic.getInstance();
    private final BoardsDatabase boardsDatabase = BoardsDatabase.getInstance();
    private static SudokuMenu sudokuMenuInstance = null;

    private SudokuMenu() {
        this.userMoveReader = new UserMoveReader();
    }

    public void mainMenu() {
        System.out.println("[1] ~~ Solve new, standard sized board (9x9).");
        System.out.println("[2] ~~ Solve new, custom sized board.");
        System.out.println("[X] ~~ Exit.");

        final String mainMenuChoice = userMoveReader.readString().toUpperCase();
        switch (mainMenuChoice) {
            case "1":
                sudokuLogic.setDefaultSudokuBoardSize();
                startNewSudoku();
                break;
            case "2":
                sudokuLogic.setCustomSudokuBoardSize();
                startNewSudoku();
                break;
            case "X":
                System.out.println("Exit game. Are you sure [Y]/[N] ?");
                gameExit();
                break;
            default:
                System.out.println("! Wrong choice, please try again !\n");
                mainMenu();
        }
    }

    private void gameExit() {
        final String exitMenuChoice = userMoveReader.readString().toUpperCase();
        switch (exitMenuChoice) {
            case "Y":
                break;
            case "N":
                mainMenu();
                break;
            default:
                System.out.println("! Wrong choice, please try again !");
                gameExit();
        }
    }

    private void startNewSudoku() {
        System.out.println("[1] ~~ Build new board by entering cells values (one by one)");
        System.out.println("[2] ~~ Build new board by entering cells values (by number stream)");
        System.out.println("[3] ~~ Build new random board (by entering no of cells to fulfil)");
        System.out.println("[4] ~~ Get program test board (9x9)");
        System.out.println("[x] ~~ Back to main menu");

        final String startNewSudokuMenuChoice = userMoveReader.readString().toUpperCase();
        switch (startNewSudokuMenuChoice) {
            case "1":
                sudokuLogic.prepareNewBoardFillingCellsOneByOne();
                System.out.println();
                inGameMenu();
                break;
            case "2":
                sudokuLogic.prepareNewBoardFillingCellsByStream();
                System.out.println();
                inGameMenu();
                break;
            case "3":
                sudokuLogic.generateRandomBoard();
                System.out.println();
                inGameMenu();
                break;
            case "4":
                if (sudokuLogic.setTestBoard()) {
                    System.out.println();
                    inGameMenu();
                } else {
                    startNewSudoku();
                }
                break;
            case "X":
                mainMenu();
                break;
            default:
                System.out.println("! Wrong choice, please try again !\n");
                startNewSudoku();
        }
    }

    private void inGameMenu() {
        System.out.println("[1] ~~ Solve current board");
        System.out.println("[2] ~~ Back to game menu");
        System.out.println("[x] ~~ Back to main menu");

        final String inGameMenuChoice = userMoveReader.readString().toUpperCase();
        switch (inGameMenuChoice) {
            case "1":
                boardsDatabase.clearDatabases();
                sudokuLogic.getAllSolutionsOfCurrentBoard();
                System.out.println();
                afterGameMEnu();
                break;
            case "2":
                startNewSudoku();
                break;
            case "X":
                mainMenu();
                break;
            default:
                System.out.println("! Wrong choice, please try again !\n");
                inGameMenu();
        }
    }

    private void afterGameMEnu() {
        System.out.println("[1] ~~ Show random solution");
        System.out.println("[2] ~~ Show random deadEnd board");
        System.out.println("[3] ~~ Show less complicated solution");
        System.out.println("[x] ~~ Back to main menu");

        final String afterGameMenuChoice = userMoveReader.readString().toUpperCase();
        switch (afterGameMenuChoice) {
            case "1":
                boardsDatabase.databaseRecordsDisplay(new RandomSolutionDisplay());
                afterGameMEnu();
                break;
            case "2":
                boardsDatabase.databaseRecordsDisplay(new RandomDeadEndDisplay());
                afterGameMEnu();
                break;
            case "3":
                boardsDatabase.databaseRecordsDisplay(new EasiestSolutionDisplay());
                afterGameMEnu();
                break;
            case "X":
                mainMenu();
                break;
            default:
                System.out.println("! Wrong choice, please try again !\n");
                afterGameMEnu();
        }
    }

    public static SudokuMenu getInstance() {
        if (sudokuMenuInstance == null) {
            synchronized (SudokuMenu.class) {
                if (sudokuMenuInstance == null) {
                    sudokuMenuInstance = new SudokuMenu();
                }
            }
        }
        return sudokuMenuInstance;
    }
}

