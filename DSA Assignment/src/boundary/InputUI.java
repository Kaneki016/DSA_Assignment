package boundary;

import controller.*;
import dao.MockDataGenerator;
import java.util.Scanner;
import entities.*;

public class InputUI {

    private Scanner scanner = new Scanner(System.in);

    // DAO
    private static MockDataGenerator mockDataGenerator = MockDataGenerator.getInstance();

    // Controller
    private static CompanyManager companyManager = CompanyManager.getInstance();
    private static JobManager jobManager = JobManager.getInstance();
    private static JobPostManager jobPostManager = JobPostManager.getInstance();
    private static JobRequirementsManager jobRequirementsManager = JobRequirementsManager.getInstance();
    private static ApplicantManager applicantManager = ApplicantManager.getInstance();
    private static InterviewManager interviewManager = InterviewManager.getInstance();
    private static ApplicantAppliedJobManager applicantAppliedJobManager = ApplicantAppliedJobManager.getInstance();

    // Boundary
    private static MenuUI menuUI = new MenuUI();
    private static InputUI inputUI = new InputUI();

    //Clear Screen
    public static void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

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

    // Get Valid Float Input
    public float getValidFloatInput(String prompt, float min, float max) {
        float value;
        while (true) {
            System.out.print(prompt);
            while (!scanner.hasNextFloat()) {
                System.out.print("Invalid input! Enter a valid number: ");
                scanner.next(); // discard invalid input
            }
            value = scanner.nextFloat();
            scanner.nextLine(); // Consume newline
            if (value >= min && value <= max) {
                break;
            }
            System.out.printf("Please enter a value between %.2f and %.2f.%n", min, max);
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
                // Middle Side
                boolean running1 = true;
                while (running1) {
                    menuUI.displayMiddleMainMenu();
                    choice = inputUI.getValidIntInput("Enter your choice: ", 1, 4);
                    switch (choice) {
                        case 1:
                            menuUI.displayApplicantMenu();
                            handleMiddleSideMenuChoice();
                            break;
                        case 2:
                            interviewManager.acceptTimeSlotMiddleMan();
                            break;
                        case 3:
                            inputUI.handleCompanyManagement();
                            break;
                        case 4:
                            running1 = false;
                            menuUI.exitSystem();
                            break;
                        default:
                            inputUI.invalidMenuSelection(1, 4); // ✅ Fixed incorrect range
                            break;
                    }
                }
                break;

            case 3:
                // Company Side
                boolean running2 = true;
                while (running2) {
                    menuUI.displayCompanyMainMenu();
                    choice = inputUI.getValidIntInput("Enter your choice: ", 1, 6);
                    switch (choice) {
                        case 1:
                            handleJobPostManagement();
                            break;
                        case 2:
                            handleJobManagement();
                            break;
                        case 3:
                            handleJobReqManagement();
                            break;
                        case 4:
                            handleInterviewMenu();
                            break;
                        case 5:
                            handleCompanyMatchingCategory();
                            break;
                        case 6:
                            running2 = false;
                            menuUI.exitSystem();
                            break;
                        default:
                            inputUI.invalidMenuSelection(1, 6);
                            break;
                    }
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
        choice = inputUI.getValidIntInput("Enter your choice: ", 1, 7);
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
                Applicant applicant = applicantManager.searchApplicantById();

                break;

            case 5:
                applicantManager.filterApplicants();
                break;

            case 6:
                applicantAppliedJobManager.displayCompanyApplicantReport();
                break;

            case 7:
                menuUI.exitSystem();
                break;
            default:
                inputUI.invalidMenuSelection(1, 7);
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
                displayApplicantAppliedMenu();
                break;

            case 4:
                handleApplicantMatchingCategory();
                break;

            case 5:
                menuUI.exitSystem();
                break; // Add break here to prevent fall-through
            default:
                inputUI.invalidMenuSelection(1, 5);
                break; // Add break here to prevent fall-through
        }
    }

    //Applicant apply job
    public void displayApplicantAppliedMenu() {

        menuUI.displayApplicantAppliedMenu();
        int choice = getIntInput("Enter your choice: ", 1, 5);

        switch (choice) {
            case 1:
                applicantAppliedJobManager.viewAvailableJobs();
                break;
            case 2:
                applicantAppliedJobManager.applyJob();
                break;
            case 3:
                applicantAppliedJobManager.handleCheckMyApplications();
                break;
            case 4:
                inputUI.displayMessage("Exiting menu. Thank you!");
                return; // Exit the menu loop
            default:
                System.out.println("❌ Invalid selection. Try again.");
        }

    }

    // Handle Company Management Menu in middle side
    public void handleCompanyManagement() {
        int choice;
        do {
            menuUI.displayCompanyManagement();
            choice = inputUI.getValidIntInput("Enter your choice: ", 1, 6);
            switch (choice) {
                case 1:
                    companyManager.addCompany();
                    break;
                case 2:
                    companyManager.editCompany();
                    break;
                case 3:
                    companyManager.displayCompanies();
                    break;
                case 4:
                    companyManager.removeCompany();
                    break;
                case 5:
                    companyManager.displayRemovedCompanies();
                    break;
                case 6:
                    menuUI.exitSystem();
                    break; // Add break here to prevent fall-through
                default:
                    inputUI.invalidMenuSelection(1, 6);
                    break; // Add break here to prevent fall-through
            }
        } while (choice != 6);
    }
    
    // Handle Job Post Management Menu in Compnay side side
    public void handleJobPostManagement() {
        int choice;
        do {
            menuUI.displayJobPostManagement();
            choice = inputUI.getValidIntInput("Enter your choice: ", 1, 8);
            switch (choice) {
                case 1:
                    jobPostManager.addJobPost();
                    break;
                case 2:
                    jobPostManager.editJobPost();
                    break;
                case 3:
                    jobPostManager.displayJobPosts();
                    break;
                case 4:
                    jobPostManager.removeJobPost();
                    break;
                case 5:
                    jobPostManager.displayRemovedPosts();
                    break;
                case 6:
                    jobPostManager.searchJobPost();
                    break;
                case 7:
                    jobPostManager.printJobPostReports();
                    break;
                case 8:
                    menuUI.exitSystem();
                    break; // Add break here to prevent fall-through
                default:
                    inputUI.invalidMenuSelection(1, 8);
                    break; // Add break here to prevent fall-through
            }
        } while (choice != 8);

    }
    
    // Handle Job Post Management Menu in Compnay side side
    public void handleJobManagement() {
        int choice;
        do{
            menuUI.displayJobManagement();
            choice = inputUI.getValidIntInput("Enter your choice: ", 1, 6);
            switch (choice) {
                case 1:
                    jobManager.addJob();
                    break;
                case 2:
                    jobManager.editJob();
                    break;
                case 3:
                    jobManager.displayJobs();
                    break;
                case 4:
                    jobManager.removeJob();
                    break;
                case 5:
                    jobManager.displayRemovedJobs();
                    break;
                case 6:
                    menuUI.exitSystem();
                    break; // Add break here to prevent fall-through
                default:
                    inputUI.invalidMenuSelection(1, 6);
                    break; // Add break here to prevent fall-through
            }
        }while (choice != 6);
    }
    
    // Handle Job Post Management Menu in Compnay side side
    public void handleJobReqManagement() {
        int choice;
        do{
            menuUI.displayJobRequirementManagement();
            choice = inputUI.getValidIntInput("Enter your choice: ", 1, 6);
            switch (choice) {
                case 1:
                    jobRequirementsManager.addJobRequirement();
                    break;
                case 2:
                    jobRequirementsManager.editJobRequirement();
                    break;
                case 3:
                    jobRequirementsManager.displayJobRequirements();
                    break;
                case 4:
                    jobRequirementsManager.removeJobRequirement();
                    break;
                case 5:
                    jobRequirementsManager.displayRemovedReq();
                break;
                case 6:
                    menuUI.exitSystem();
                    break; // Add break here to prevent fall-through
                default:
                    inputUI.invalidMenuSelection(1, 6);
                    break; // Add break here to prevent fall-through
            }
        }while (choice != 6);
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

        boolean running = true;
        while (running) {
            menuUI.displayInterviewMenu();
            int choice = inputUI.getValidIntInput("Enter your choice: ", 1, 4);
            switch (choice) {
                case 1:
                    interviewManager.displayAssignInterviewTimeSlot(company);
                    break;
                case 2:
                    handleRecruitmentTableMenu(company);
                    break;
                case 3:
                    handleInterviewReport(company);
                    break;
                case 4:
                    running = false; // Only exit this menu when exit option is selected
                    break;
                default:
                    inputUI.invalidMenuSelection(1, 4);
                    break;
            }
        }
    }

    // Handle Interview Time Slot Menu
    public void handleInterviewTimeSlotMenu(Company company) {
        boolean running = true;
        while (running) {
            menuUI.displayTimeSlotInterviewMenu(company.getCompanyName());
            int choice;
            choice = inputUI.getValidIntInput("Enter your choice: ", 1, 10);
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
                    interviewManager.filterInterviewsSkill(company);
                    break;
                case 5:
                    // View Pending Interviews Based on Years of Experience
                    interviewManager.filterApplicantsByExperience(company);
                    break;
                case 6:
                    // Suggest Time Slot To Middle Side
                    interviewManager.suggestTimeSlotToMiddleMan();
                    break;
                case 7:
                    // Give Interview Feedback
                    interviewManager.handleInterviewFeedback(company);
                    break;
                case 8:
                    // View Time Slot Table
                    interviewManager.viewTimeSlotTable(company);
                    break;
                case 9:
                    // Search
                    interviewManager.searchInterview(company);
                    break;
                case 10:
                    // Return to previous menu
                    running = false;
                    break;
                default:
                    inputUI.invalidMenuSelection(1, 10);
                    break;
            }
        }
    }

    // Handle Recruitment Table
    public void handleRecruitmentTableMenu(Company company) {
        boolean running = true;
        while (running) {
            menuUI.displayRecruitmentMenu();
            int choice;
            choice = inputUI.getValidIntInput("Enter your choice: ", 1, 4);
            switch (choice) {
                case 1:
                    // Filter Applicant By Interview Rating
                    interviewManager.filterApplicantBasedOnInterview(company);
                    break;
                case 2:
                    // Interview Feedback of Completed Interviews
                    interviewManager.viewInterviewFeedback(company);
                    break;
                case 3:
                    // Accept and Reject Applicant
                    interviewManager.acceptRejectInterviewApplicant(company);
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    inputUI.invalidMenuSelection(1, 4);
                    break;
            }
        }
    }

    public void handleInterviewReport(Company company) {
        boolean running = true;
        while (running) {
            menuUI.displayInterviewReport();
            int choice;
            choice = inputUI.getValidIntInput("Enter your choice: ", 1, 3);
            switch (choice) {
                case 1:
                    // View Accepted Interview Report
                    interviewManager.viewInterview(company);
                    break;
                case 2:
                    running = false;
                    break;
                default:
                    inputUI.invalidMenuSelection(1, 3);
                    break;
            }
        }
    }

    public void handleSearchInterview(ApplicantAppliedJob applicantAppliedJob) {
        boolean running = true;
        while (running) {
            menuUI.displaySearchInterview();
            int choice;
            choice = inputUI.getValidIntInput("Enter your choice: ", 1, 3);
            switch (choice) {
                case 1:
                    // Dig Applicant Details
                    interviewManager.searchInterviewApplicantDetails(applicantAppliedJob);
                    break;
                case 2:
                    interviewManager.searchInterviewJobDetails(applicantAppliedJob);
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    inputUI.invalidMenuSelection(1, 3);
                    break;
            }
        }
    }

    public String centerString(String s, int width) {
        if (s == null) {
            s = "";
        }
        int padding = (width - s.length()) / 2;
        if (padding <= 0) {
            return s;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < padding; i++) {
            sb.append(" ");
        }
        sb.append(s);
        return sb.toString();
    }

    public static void handleCompanyMatchingCategory() {

        while (true) {
            // Display job matching menu
            menuUI.displayJobMatchingMenu();

            int choice = inputUI.getValidIntInput("Enter your choice: ", 1, 5);

            switch (choice) {
                case 1:
                    applicantAppliedJobManager.CompanySkillMatch();
                    break;

                case 2:
                    applicantAppliedJobManager.CompanyExperienceMatch();
                    break;

                case 3:
                    applicantAppliedJobManager.CompanyLocationMatch();
                    break;

                case 4:
                    applicantAppliedJobManager.generateCompanyMatchReport();
                    break;
                case 5:
                    menuUI.exitSystem();
                    return;
                default:
                    inputUI.invalidMenuSelection(1, 5);
                    break;
            }
        }
    }

    public static void handleApplicantMatchingCategory() {

        while (true) {
            // Display job matching menu
            menuUI.displayJobMatchingMenu();

            int choice = inputUI.getValidIntInput("Enter your choice: ", 1, 5);

            switch (choice) {
                case 1:
                    applicantAppliedJobManager.ApplicantSkillMatch();
                    break;

                case 2:
                    applicantAppliedJobManager.ApplicantExperienceMatch();
                    break;

                case 3:
                    applicantAppliedJobManager.ApplicantLocationMatch();
                    break;

                case 4:
                    applicantAppliedJobManager.generateApplicantMatchReport();
                    break;
                case 5:
                    menuUI.exitSystem();
                    return;
                default:
                    inputUI.invalidMenuSelection(1, 5);
                    break;
            }
        }
    }
   
}
