package modules;

import java.util.Scanner;
import boundary.MenuUI;
import boundary.InputUI;

public class MainSystem {

    // Boundary
    private static final MenuUI menuUI = new MenuUI();
    private static final InputUI inputUI = new InputUI();

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int choice;
            do {
                menuUI.displayMainMenu();
                choice = inputUI.getValidIntInput("Enter your choice: ", 1, 4);
                inputUI.handleMainMenuChoice(choice);
            } while (choice != 4);
        }
    }
}
