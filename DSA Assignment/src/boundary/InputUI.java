package boundary;

import controller.ApplicantManager;
import controller.InterviewManager;
import controller.ApplicantAppliedJobManager;
import controller.JobPostManager;
import dao.MockDataGenerator;
import java.util.Scanner;

public class InputUI {

    private static Scanner scanner = new Scanner(System.in);

    // DAO
    private static MockDataGenerator mockDataGenerator = MockDataGenerator.getInstance();

    //Controller
    private static ApplicantManager applicantManager = ApplicantManager.getInstance();
    private static InterviewManager interviewManager = InterviewManager.getInstance();
    private static ApplicantAppliedJobManager applicantAppliedJobManager = ApplicantAppliedJobManager.getInstance();
    private static JobPostManager jobPostManager = JobPostManager.getInstance();


    // Boundary
    private static MenuUI menuUI = new MenuUI();
    private static InputUI inputUI = new InputUI();

    //Get Input From User
    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    //Get Interget Input From User
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

    //Display Message
    public <T> void displayMessage(T element) {
        System.out.println(element);
    }

    //Error Message Invalid Input
    public void invalidMenuSelection(int min, int max) {
        System.out.printf("Invalid input! Enter a number (%d-%d): ", min, max);
    }

    //Get Valid Input
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

    //Main Menu 
    public static void handleMainMenuChoice(int choice) {
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
                choice = inputUI.getValidIntInput("Enter your choice: ", 1, 7);
                switch (choice) {
                    case 1:
                        // Add Job functionality can be implemented here.
                        break;
                    case 4:
                        interviewManager.displayAppliedApplicantInterviewMenu();
                        break;
                    case 5:
                        interviewManager.displayAssignInterviewTimeSlot();
                        break;
                    case 6:
                        mockDataGenerator.addMockData();
                        break;
                    case 7:
                        menuUI.exitSystem();
                    default:
                        System.out.println("Invalid option. Please try again.");
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

    //Middle Side Menu
    public static void handleMiddleSideMenuChoice() {
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
                applicantManager.findApplicantById();
                break;

            case 5:
                menuUI.exitSystem();
                break;
            default:
                inputUI.invalidMenuSelection(1, 5);
        }
    }

    //Handle Client Menu
    public static void handleClinetMenu() {
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
    
    public static void handleMatchingCategory() {
       while (true) {
           inputUI.displayMessage("Enter the number of the matching category (or 0 to exit): ");
           String choice = scanner.nextLine().trim();

           // Retrieve the applicant and job before switch statement
           ApplicantManager applicant = applicantManager;
           JobPostManager job = jobPostManager;

           if (applicant == null || job == null) {
               inputUI.displayMessage("Invalid Applicant or Job. Please try again.");
               continue;
           }

           switch (choice) {
               case "1":
                   inputUI.displayMessage("Matching based on Proficiency Levels...");
                   double skillMatchScore = applicantAppliedJobManager.calculateSkillMatch(applicant, job);
                   inputUI.displayMessage("Skill Match Score: " + skillMatchScore);
                   break;

               case "2":
                   inputUI.displayMessage("Matching based on Skill's Importance...");
                   double weightedMatch = applicantAppliedJobManager.weightedSkillMatch(applicant, job);
                   inputUI.displayMessage("Weighted Skill Match Score: " + weightedMatch);
                   break;

               case "3":
                   inputUI.displayMessage("Matching based on Experience Levels...");
                   double experienceScore = applicantAppliedJobManager.experienceMatch(applicant, job);
                   inputUI.displayMessage("Experience Match Score: " + experienceScore);
                   break;

               case "4":
                   inputUI.displayMessage("Matching based on Location Preferences...");
                   double locationScore = applicantAppliedJobManager.locationMatch(applicant, job);
                   inputUI.displayMessage("Location Match Score: " + locationScore);
                   break;

               case "5":
                   inputUI.displayMessage("Calculating Overall Score...");
                   double overallScore = applicantAppliedJobManager.overallMatchScore(applicant, job);
                   inputUI.displayMessage("Overall Match Score: " + overallScore);
                   break;

               case "0":
                   inputUI.displayMessage("Exiting job matching menu.");
                   return;

               default:
                   inputUI.invalidMenuSelection(0, 5);
                   break;
           }
       }
   }
}
