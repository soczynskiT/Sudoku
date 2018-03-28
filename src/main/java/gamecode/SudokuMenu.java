package gamecode;

import gamecode.databases.BoardsDatabase;
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
        System.out.println("[S] ~~ Solve new board.");
        System.out.println("[X] ~~ Exit.");

        final String mainMenuChoice = userMoveReader.readString().toUpperCase();
        switch (mainMenuChoice) {
            case "S":
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
        System.out.println("[4] ~~ Get program test board");
        System.out.println("[x] ~~ Back to main menu");

        final String startNewSudokuMenuChoice = userMoveReader.readString().toUpperCase();
        switch (startNewSudokuMenuChoice) {
            case "1":
                sudokuLogic.prepareNewBoard();
                System.out.println();
                inGameMenu();
                break;
            case "2":
                System.out.println("Under construction");
                startNewSudoku();
                break;
            case "3":
                sudokuLogic.generateRandomBoard();
                System.out.println();
                inGameMenu();
                break;
            case "4":
                sudokuLogic.setTestBoard();
                System.out.println();
                inGameMenu();
                break;
            case "X":
                mainMenu();
                break;
            default:
                System.out.println("! Wrong choice, please try again !\n");
                mainMenu();
        }
    }
    private void inGameMenu() {
        System.out.println("[1] ~~ Solve current board");
        System.out.println("[2] ~~ Back to game menu");
        System.out.println("[x] ~~ Back to main menu");

        final String inGameMenuChoice = userMoveReader.readString().toUpperCase();
        switch (inGameMenuChoice) {
            case "1":
                sudokuLogic.solveNewSudoku();
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
                mainMenu();
        }
    }

    private void afterGameMEnu() {
        System.out.println("[1] ~~ Show random solution");
        System.out.println("[2] ~~ Show less complicated solution");
        System.out.println("[3] ~~ Show most complicated solution");
        System.out.println("[x] ~~ Back to main menu");

        final String inGameMenuChoice = userMoveReader.readString().toUpperCase();
        switch (inGameMenuChoice) {
            case "1":
                boardsDatabase.showRandomSolution();
                afterGameMEnu();
                break;
            case "2":
                System.out.println("Under construction");
                afterGameMEnu();
                break;
            case "3":
                System.out.println("under construction");
                afterGameMEnu();
                break;
            case "X":
                mainMenu();
                break;
            default:
                System.out.println("! Wrong choice, please try again !\n");
                mainMenu();
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

