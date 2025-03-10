package boundary;

import controller.ApplicantManager;
import controller.InterviewManager;
import controller.CompanyManager;
import controller.TimeSlotManager;
import dao.MockDataGenerator;
import java.util.Scanner;
import entities.Applicant;
import entities.Company;

public class InputUI {

    private Scanner scanner = new Scanner(System.in);

    // DAO
    private static MockDataGenerator mockDataGenerator = MockDataGenerator.getInstance();

    // Controller
    private static CompanyManager companyManager = CompanyManager.getInstance();
    private static ApplicantManager applicantManager = ApplicantManager.getInstance();
    private static InterviewManager interviewManager = InterviewManager.getInstance();
    private static TimeSlotManager timeSlotManager = TimeSlotManager.getInstance();

    // Boundary
    private static MenuUI menuUI = new MenuUI();
    private static InputUI inputUI = new InputUI();

    // Get Input From User
    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // Get Interget Input From User
    public int getIntInput(String prompt, int min, int max) {
        int value;
        while (true) {
            System.out.print(prompt);
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input! Enter a valid number: ");
                scanner.next();
            }
            value = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (value >= min && value <= max) {
                break;
            }
            System.out.printf("❌ Please enter a value between %d and %d.%n", min, max);
        }
        return value;
    }

    // Display Message
    public <T> void displayMessage(T element) {
        System.out.println(element);
    }

    // Error Message Invalid Input
    public void invalidMenuSelection(int min, int max) {
        System.out.printf("Invalid input! Enter a number (%d-%d): ", min, max);
    }

    // Get Valid Input
    public int getValidIntInput(String prompt, int min, int max) {
        int value;
        while (true) {
            System.out.print(prompt);
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input! Enter a valid number: ");
                scanner.next();
            }
            value = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (value >= min && value <= max) {
                break;
            }
            System.out.printf("Please enter a value between %d and %d.%n", min, max);
        }
        return value;
    }

    // Main Menu
    public void handleMainMenuChoice(int choice) {
        switch (choice) {
            case 1:
                // Applicant Side
                handleClinetMenu();
                break;
            case 2:
                // Middle side
                menuUI.displayMiddleMainMenu();
                choice = inputUI.getValidIntInput("Enter your choice: ", 1, 2);
                switch (choice) {
                    case 1:
                        menuUI.displayApplicantMenu();
                        handleMiddleSideMenuChoice();
                        break;
                    case 2:
                        menuUI.exitSystem();
                        break;
                    default:
                        inputUI.invalidMenuSelection(1, 2);
                        break;
                }
                break;

            case 3:
                // Company side
                menuUI.displayCompanyMainMenu();
                choice = inputUI.getValidIntInput("Enter your choice: ", 1, 6);
                switch (choice) {
                    case 1:
                        // Add Job functionality can be implemented here.
                        break;
                    case 4:

                        handleInterviewMenu();
                        break;
                    case 5:
                        mockDataGenerator.addMockData();
                        break;
                    case 6:
                        menuUI.exitSystem();
                    default:
                        inputUI.invalidMenuSelection(1, 6);
                        break;
                }
                break;

            case 4:
                menuUI.exitSystem();
                break;
            default:
                inputUI.invalidMenuSelection(1, 4);
                break;
        }
    }

    // Middle Side Menu
    public void handleMiddleSideMenuChoice() {
        int choice;
        choice = inputUI.getValidIntInput("Enter your choice: ", 1, 5);
        switch (choice) {
            case 1:
                applicantManager.addApplicant();
                break;

            case 2:
                applicantManager.removeApplicant();
                break;

            case 3:
                applicantManager.displayAllApplicants();
                break;

            case 4:
                Applicant applicant = applicantManager.findApplicantById();
                System.out.println(applicant);
                break;

            case 5:
                menuUI.exitSystem();
                break;
            default:
                inputUI.invalidMenuSelection(1, 5);
        }
    }

    // Handle Client Menu
    public void handleClinetMenu() {
        menuUI.displayClientMainMenu();
        int choice;
        choice = inputUI.getValidIntInput("Enter your choice: ", 1, 5);
        switch (choice) {
            case 1:
                applicantManager.addApplicant();
                break;
            case 2:
                applicantManager.editApplicantProfile();
                break;
            case 3:
                menuUI.exitSystem();
                break; // Add break here to prevent fall-through
            default:
                inputUI.invalidMenuSelection(1, 3);
                break; // Add break here to prevent fall-through
        }
    }

    // Handle Interview Main Menu
    public void handleInterviewMenu() {
        inputUI.displayMessage("Which company you belongs to?: ");
        String companyId = scanner.nextLine().trim();
        Company company = companyManager.findCompanyById(companyId);

        if (company == null) {
            inputUI.displayMessage("Company not found!");
            return;
        }

        menuUI.displayInterviewMenu();

        int choice;
        choice = inputUI.getValidIntInput("Enter your choice: ", 1, 3);
        switch (choice) {
            case 1:
                interviewManager.displayAssignInterviewTimeSlot(company);
                break;
            case 2:
                // Recruitment Table

                break;
            case 3:
                menuUI.exitSystem();
                break; // Add break here to prevent fall-through
            default:
                inputUI.invalidMenuSelection(1, 3);
                break; // Add break here to prevent fall-through
        }
    }

    // Handle Interview Time Slot Menu
    public void handleInterviewTimeSlotMenu(Company company) {
        boolean running = true;
        while (running) {
            menuUI.displayTimeSlotInterviewMenu(company.getCompanyName());
            int choice;
            choice = inputUI.getValidIntInput("Enter your choice: ", 1, 11);
            switch (choice) {
                case 1:
                    // Assign Interview
                    interviewManager.addInterviewSlot(company);
                    break;
                case 2:
                    // View Waiting Interviews
                    interviewManager.viewWaitingInterviews(company);
                    break;
                case 3:
                    // View Completed Interviews
                    interviewManager.viewCompletedInterviews(company);
                    break;
                case 4:
                    // View Pending Interviews Based on Skills

                    break;
                case 5:
                    // View Pending Interviews Based on Years of Experience
                    break;
                case 6:
                    // Suggest Time Slot To Middle Side

                    break;
                case 7:
                    // Give Interview Feedback
                    interviewManager.handleInterviewFeedback(company);
                    break;
                case 8:
                    // View Time Slot Table
                    break;
                case 9:
                    // Recruitment Table
                    menuUI.displayRecruitmentMenu();
                    break;
                case 10:
                    // Return to previous menu
                    running = false;
                    break;
                default:
                    inputUI.invalidMenuSelection(1, 11);
                    break;
            }
        }
    }

}
