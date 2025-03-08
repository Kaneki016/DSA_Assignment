package boundary;

/**
 * Handles the display of various menus in the system.
 * Provides clear and structured user interfaces for navigation.
 * 
 * @author MAMBA
 */
public class MenuUI {

    public void displayMainMenu() {
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

    public void displayClientMainMenu() {
        System.out.println("\n================================");
        System.out.println("         CLIENT SIDE            ");
        System.out.println("================================");
        System.out.println("1. Register as a New Applicant");
        System.out.println("2. Edit Applicant");
        System.out.println("3. Exit");
        System.out.println("================================");
    }

    public void displayMiddleMainMenu() {
        System.out.println("\n================================");
        System.out.println("        MIDDLE SIDE MENU        ");
        System.out.println("================================");
        System.out.println("1. Applicant Management");
        System.out.println("2. Exit");
        System.out.println("================================");
    }

    public void displayCompanyMainMenu() {
        System.out.println("\n================================");
        System.out.println("        COMPANY SIDE MENU       ");
        System.out.println("================================");
        System.out.println("1. Add Job Post");
        System.out.println("2. Edit Job Post");
        System.out.println("3. Remove Job Post");
        System.out.println("4. Interview Management");
        System.out.println("5. Generate Mock Data");
        System.out.println("6. Exit");
        System.out.println("================================");
    }

    public void displayApplicantMenu() {
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

    public void displayInterviewMenu() {
        System.out.println("\n================================");
        System.out.println("     INTERVIEW MANAGEMENT       ");
        System.out.println("================================");
        System.out.println("1. Assign and View Interview Slots");
        System.out.println("2. Recruitment Table");
        System.out.println("3. Exit");
        System.out.println("================================");
    }

    //INTERVIEW
    public void displayTimeSlotInterviewMenu(String company) {
        System.out.println("\n===============================================");
        System.out.println("    " + company.toUpperCase() + " - INTERVIEW TIME SLOT MANAGEMENT   ");
        System.out.println("===============================================");
        System.out.println("1. Assign Interview");
        System.out.println("2. View Waiting Interviews");
        System.out.println("3. View Completed Interviews");
        System.out.println("4. View Pending Interviews Based on Skills");
        System.out.println("5. View Pending Interviews Based on Years of Experience");
        System.out.println("6. Suggest Time Slot To Middle Side");
        System.out.println("7. Give Interview Feedback");
        System.out.println("8. View Time Slot Table");
        System.out.println("9. Recruitment Table");
        System.out.println("10. Back to Previous Menu");
        System.out.println("===============================================");
    }

    public void displayRecruitmentMenu() {
        System.out.println("\n===============================================");
        System.out.println("           RECRUITMENT OPTIONS                ");
        System.out.println("===============================================");
        System.out.println("1. Filter Applicants by Interview Rating");
        System.out.println("2. Interview Feedback of Completed Interviews");
        System.out.println("3. Accept or Reject Applicants");
        System.out.println("3. Back to Previous Menu");
        System.out.println("===============================================");
    }

    public void acceptOrRejectApplicantsMenu() {
        System.out.println("\n===============================================");
        System.out.println("           ACCEPT OR REJECT APPLICANTS        ");
        System.out.println("===============================================");
        System.out.println("1. Accept Applicant");
        System.out.println("2. Reject Applicant");
        System.out.println("3. Back to Previous Menu");
        System.out.println("===============================================");
    }

    public void exitSystem() {
        System.out.println("\n================================");
        System.out.println("       EXITING SYSTEM...        ");
        System.out.println("================================");
    }
}
