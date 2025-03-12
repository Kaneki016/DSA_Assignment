package boundary;

/**
 * Handles the display of various menus in the system.
 * Provides clear and structured user interfaces for navigation.
 * 
 * @author MAMBA
 */
public class MenuUI {

    public static void displayMainMenu() {
        System.out.println("\n================================");
        System.out.println("           MAIN MENU            ");
        System.out.println("================================");
        System.out.println("Which side are you?");
        System.out.println("1. Client");
        System.out.println("2. Middle");
        System.out.println("3. Company");
        System.out.println("4. Exit");
        System.out.println("================================");
    }

    public static void displayClientMainMenu() {
        System.out.println("\n================================");
        System.out.println("         CLIENT SIDE            ");
        System.out.println("================================");
        System.out.println("1. Register as a New Applicant");
        System.out.println("2. Edit Applicant");
        System.out.println("3. Exit");
        System.out.println("================================");
    }

    public static void displayMiddleMainMenu() {
        System.out.println("\n================================");
        System.out.println("        MIDDLE SIDE MENU        ");
        System.out.println("================================");
        System.out.println("1. Applicant Management");
        System.out.println("2. Exit");
        System.out.println("================================");
    }

    public static void displayCompanyMainMenu() {
        System.out.println("\n================================");
        System.out.println("        COMPANY SIDE MENU       ");
        System.out.println("================================");
        System.out.println("1. Add Job Post");
        System.out.println("2. Edit Job Post");
        System.out.println("3. Remove Job Post");
        System.out.println("4. View Applied Applicants");
        System.out.println("5. Assign and View Interview Slots");
        System.out.println("6. Generate Mock Data");
        System.out.println("7. Job Matcher Suggestion");
        System.out.println("8. Exit");
        System.out.println("================================");
    }

    public static void displayApplicantMenu() {
        System.out.println("\n================================");
        System.out.println("     APPLICANT MANAGEMENT       ");
        System.out.println("================================");
        System.out.println("1. Add Applicant");
        System.out.println("2. Remove Applicant");
        System.out.println("3. View All Applicants");
        System.out.println("4. Search Applicant by ID");
        System.out.println("5. Exit");
        System.out.println("================================");
    }

    public static void displayAppliedApplicantInterviewMenu(String company) {
        System.out.println("\n===============================================");
        System.out.println("  " + company.toUpperCase() + " - LIST OF APPLIED APPLICANTS  ");
        System.out.println("===============================================");
    }

    public static void displayTimeSlotInterviewMenu(String company) {
        System.out.println("\n===============================================");
        System.out.println("    " + company.toUpperCase() + " - INTERVIEW TIME SLOT MANAGEMENT   ");
        System.out.println("===============================================");
        System.out.println("1. View Pending Interviews");
        System.out.println("2. Interview Feedback");
        System.out.println("3. Add Interview Slot for Applied Applicant");
        System.out.println("4. Recruitment Options");
        System.out.println("===============================================");
    }
    
    public static void displayJobMatchingMenu(String company) {
        System.out.println("\n===============================================");
        System.out.println("    " + company.toUpperCase() + " - MATCHING CATEGORIES   ");
        System.out.println("===============================================");
        System.out.println("1. Proficiency Levels");
        System.out.println("2. Skill's Importance");
        System.out.println("3. Experience Levels and Job Requirements");
        System.out.println("4. Location Preferences");
        System.out.println("5. Overall Score");
        System.out.println("===============================================");
    }

    public static void exitSystem() {
        System.out.println("\n================================");
        System.out.println("       EXITING SYSTEM...        ");
        System.out.println("================================");
    }
}
