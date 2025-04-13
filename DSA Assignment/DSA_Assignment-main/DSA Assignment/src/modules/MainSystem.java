package modules;

import java.util.Scanner;
import controller.ApplicantManager;
import boundary.MenuUI;
import boundary.InputUI;
import dao.MockDataGenerator;

public class MainSystem {

    // Boundary
    private static final MenuUI menuUI = new MenuUI();
    private static final InputUI inputUI = new InputUI();
    
    // DAO
    private static MockDataGenerator mockDataGenerator = MockDataGenerator.getInstance();

    // Controller
    private static final ApplicantManager applicantManager = ApplicantManager.getInstance();

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int choice;
            mockDataGenerator.addMockData();
            do {
                inputUI.clearScreen();
                menuUI.displayMainMenu();
                choice = inputUI.getValidIntInput("Enter your choice: ", 1, 4);
                inputUI.handleMainMenuChoice(choice);
            } while (choice != 4);
        }
    }
}
