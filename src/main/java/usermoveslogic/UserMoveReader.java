package usermoveslogic;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserMoveReader implements MoveReader {
    private final Scanner scanner;

    public UserMoveReader() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String readString() {
        return scanner.nextLine();
    }

    @Override
    public int readNumberOfBounds(int boundMin, int boundMax) {
        Scanner scan = new Scanner(System.in);
        boolean isEntryWrong = true;
        int number = 0;
        do try {
            number = scan.nextInt();
            if (number > boundMin - 1 && number < boundMax + 1) {
                isEntryWrong = false;
            } else {
                System.out.println("Possible digits: " + boundMin + " - " + boundMax);
            }

        } catch (InputMismatchException e) {
            System.out.println("Wrong digit format, please try again.");
            scan.next();
        }

        while (isEntryWrong);
        return number;
    }
}
