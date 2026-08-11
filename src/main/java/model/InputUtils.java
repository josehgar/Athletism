package model;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputUtils {
    public static int typeIntBounded(int lowerIncluded, int upperIncluded) {
        Scanner sc = new Scanner(System.in);
        int option = 0;
        boolean invalid = true;
        do {
            System.out.print("Enter a number between " + lowerIncluded + " and " + upperIncluded + ": ");
            String input = sc.nextLine();
            try {
                option = Integer.parseInt(input);
                if (option >= lowerIncluded && option <= upperIncluded) {
                    invalid = false;
                }
            } catch (NumberFormatException ignored) {}
        } while (invalid);
        return option;
    }

    public static int typeInt() {
        Scanner sc = new Scanner(System.in);
        int n = 0;
        boolean end = false;
        do {
            end = true;
            System.out.print("Enter a number: ");
            String input = sc.nextLine();
            try {
                n = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                end = false;
            }
        } while (!end);
        return n;
    }

    public static String input(String message) {
        Scanner sc = new Scanner(System.in);
        System.out.print(message);
        return sc.nextLine();
    }
}
