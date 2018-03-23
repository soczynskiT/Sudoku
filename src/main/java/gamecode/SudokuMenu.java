package gamecode;

import usermoveslogic.UserMoveReader;

public final class SudokuMenu {
    private final UserMoveReader userMoveReader;
    private final SudokuLogic sudokuLogic = SudokuLogic.getInstance();
    private static SudokuMenu sudokuMenuInstance = null;

    private SudokuMenu() {
        this.userMoveReader = new UserMoveReader();
    }

    public void mainMenu() {
        System.out.println("[S] ~~ Build new board.");
        System.out.println("[X] ~~ Exit.");

        final String mainMenuChoice = userMoveReader.readString().toUpperCase();
        switch (mainMenuChoice) {
            case "S":
                sudokuLogic.solveNewSudoku();
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

