package boundary;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import adt.DoublyLinkedListInterface;
import entities.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles the display of various menus in the system. Provides clear and
 * structured user interfaces for navigation.
 *
 * @author MAMBA
 */
public class MenuUI {

    private InputUI inputUI = new InputUI();
    private static final int MENU_WIDTH = 50; // Standard width for all menus
    private static final String HORIZONTAL_LINE = "-";
    private static final String VERTICAL_LINE = "|";
    private static final String TOP_LEFT = "+";
    private static final String TOP_RIGHT = "+";
    private static final String BOTTOM_LEFT = "+";
    private static final String BOTTOM_RIGHT = "+";
    private static final String MIDDLE_LEFT = "+";
    private static final String MIDDLE_RIGHT = "+";
    private static final String TABLE_HORIZONTAL = "-";
    private static final String TABLE_VERTICAL = "|";
    private static final String TABLE_CROSS = "+";

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";  // You can change this to RED, GREEN, etc.

    // Helper method to create a border line
    private String createBorderLine(String left, String right, String fill) {
        return left + repeat(fill, MENU_WIDTH - 2) + right;
    }

    // Helper method to center text within a given width
    private String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        int leftPadding = padding;
        int rightPadding = width - text.length() - leftPadding;
        return " ".repeat(Math.max(0, leftPadding)) + text + " ".repeat(Math.max(0, rightPadding));
    }

    // Helper method to format menu options with consistent padding and icons
    private String formatMenuOption(int number, String option) {
        return VERTICAL_LINE + " > " + number + ". " + option + " ".repeat(MENU_WIDTH - 8 - option.length()) + VERTICAL_LINE;
    }

    // Display Main Menu
    public void displayMainMenu() {

        System.out.println("                             ,----,                                                                                                                            ,--,                                            ,----,                                    ");
        System.out.println("                 ,--.      ,/   .`|                             ,--.                    ,--,        ,-.----.                          ,-.----.   ,-.----.   ,---.'|                                          ,/   .`|            ,----..            ,--. ");
        System.out.println("   ,---,       ,--.'|    ,`   .'  :   ,---,.,-.----.          ,--.'|  .--.--.         ,--.'|   ,---,\\    /  \\             ,---,       \\    /  \\  \\    /  \\  |   | :      ,---,  ,----..     ,---,          ,`   .'  :   ,---,   /   /   \\         ,--.'| ");
        System.out.println(",`--.' |   ,--,:  : |  ;    ;     / ,'  .' |\\    /  \\     ,--,:  : | /  /    '.    ,--,  | :,`--.' ||   :    \\           '  .' \\      |   :    \\ |   :    \\ :   : |   ,`--.' | /   /   \\   '  .' \\       ;    ;     /,`--.' |  /   .     :    ,--,:  : | ");
        System.out.println("|   :  :,`--.'`|  ' :.'___,/    ,',---.'   |;   :    \\ ,`--.'`|  ' :|  :  /`. / ,---.'|  : '|   :  :|   |  .\\ :         /  ;    '.    |   |  .\\ :|   |  .\\ :|   ' :   |   :  :|   :     : /  ;    '.   .'___,/    ,' |   :  : .   /   ;.  \\,`--.'`|  ' : ");
        System.out.println(":   |  '|   :  :  | ||    :     | |   |   .'|   | .\\ : |   :  :  | |;  |  |--`  |   | : _' |:   |  '.   :  |: |        :  :       \\   .   :  |: |.   :  |: |;   ; '   :   |  '.   ; /--` :  |   /\\   \\ ;    :     |  :   |  '.   ;   /  ` ;|   :  :  | | ");
        System.out.println("|   :  |:   |   \\ | :;    |.';  ; :   :  |-,.   : |: | :   |   \\ | :|  :  ;_    :   : |.'  ||   :  ||   |   \\ :        :  |   /\\   \\  |   |   \\ :|   |   \\ :'   | |__ |   :  |.   ; /--` :  |   /\\   \\ ;    |.';  ;  |   :  |;   |  ; \\ ; |:   |   \\ | : ");
        System.out.println("'   '  ;|   : '  '; |`----'  |  | :   |  ;/||   |  \\ : |   : '  '; | \\  \\    `. |   ' '  ; :'   '  ;|   : .   /        |  :  ' ;.   : |   : .   /|   : .   /|   | :.'|'   '  ;;   | ;    |  :  ' ;.   :`----'  |  |  '   '  ;|   :  | ; | '|   : '  '; | ");
        System.out.println("|   |  |'   ' ;.    ;    '   :  ; |   :   .'|   : .  / '   ' ;.    ;  `----.   \\|   |  .'. ||   |  |;   | |`-'         |  |  ;/  \\   \\;   | |`-' ;   | |`-' '   :    ;|   |  ||   : |    |  |  ;/  \\   \\   '   :  ;  |   |  |.   |  ' ' ' :'   ' ;.    ; ");
        System.out.println("'   :  ;|   | | \\   |    |   |  ' |   |  |-,;   | |  \\ |   | | \\   |  __ \\  \\  ||   | :  | ''   :  ;|   | ;            '  :  | \\  \\ ,'|   | ;    |   | ;    |   |  ./ '   :  ;.   | '___ '  :  | \\  \\ ,'   |   |  '  '   :  ;'   ;  \\; /  ||   | | \\   | ");
        System.out.println("|   |  ''   : |  ; .'    '   :  | '   :  ;/||   | ;\\  \\'   : |  ; .' /  /`--'  /'   : |  : ;|   |  ':   ' |            |  |  '  '--'  :   ' |    :   ' |    ;   : ;   |   |  ''   ; : .'||  |  '  '--'     '   :  |  |   |  ' \\   \\  ',  / '   : |  ; .' ");
        System.out.println("'   :  ||   | '`--'      ;   |.'  |   |    \\:   ' | \\'|   | '`--'  '--'.     / |   | '  ,/ '   :  |:   : :            |  :  :        :   : :    :   : :    |   ,/    '   :  |'   | '/  :|  :  :           ;   |.'   '   :  |  ;   :    /  |   | '`--'   ");
        System.out.println(";   |.' '   : |          '---'    |   :   .':   : :-'  '   : |        `--'---'  ;   : ;--'  ;   |.' |   | :            |  | ,'        |   | :    |   | :    '---'     ;   |.' |   :    / |  | ,'           '---'     ;   |.'    \\   \\ .'   '   : |       ");
        System.out.println("'---'   ;   |.'                   |   | ,'  |   |.'    ;   |.'                  |   ,/      '---'   `---'.|            `--''          `---'.|    `---'.|              '---'    \\   \\ .'  `--''                       '---'       `---`     ;   |.'       ");
        System.out.println("        '---'                     `----'    `---'      '---'                    '---'                 `---`                             `---`      `---`                        `---`                                                      '---'         ");

        System.out.println("\n" + TOP_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + TOP_RIGHT);
        System.out.println(VERTICAL_LINE + centerText("MAIN MENU", MENU_WIDTH - 2) + VERTICAL_LINE);
        System.out.println(MIDDLE_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + MIDDLE_RIGHT);
        System.out.println(formatMenuOption(1, "Client"));
        System.out.println(formatMenuOption(2, "Agency"));
        System.out.println(formatMenuOption(3, "Company"));
        System.out.println(formatMenuOption(4, "Exit"));
        System.out.println(BOTTOM_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + BOTTOM_RIGHT);
    }

    // Display Client Main Menu
    public void displayClientMainMenu() {
        System.out.println("\n" + TOP_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + TOP_RIGHT);
        System.out.println(VERTICAL_LINE + centerText("CLIENT MENU", MENU_WIDTH - 2) + VERTICAL_LINE);
        System.out.println(MIDDLE_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + MIDDLE_RIGHT);
        System.out.println(formatMenuOption(1, "Register as a New Applicant"));
        System.out.println(formatMenuOption(2, "Edit Applicant"));
        System.out.println(formatMenuOption(3, "Job Application"));
        System.out.println(formatMenuOption(4, "Matching Management"));
        System.out.println(formatMenuOption(5, "Exit"));
        System.out.println(BOTTOM_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + BOTTOM_RIGHT);
    }

    // Display Applicant Applied Menu
    public void displayApplicantAppliedMenu() {

        System.out.println("\n" + TOP_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + TOP_RIGHT);
        System.out.println(VERTICAL_LINE + centerText("APPLICANT JOB APPLICATION", MENU_WIDTH - 2) + VERTICAL_LINE);
        System.out.println(MIDDLE_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + MIDDLE_RIGHT);
        System.out.println(formatMenuOption(1, "View Available Jobs"));
        System.out.println(formatMenuOption(2, "Apply for a Job"));
        System.out.println(formatMenuOption(3, "View My Applications"));
        System.out.println(formatMenuOption(4, "Recommend Jobs"));
        System.out.println(formatMenuOption(5, "Exit"));
        System.out.println(BOTTOM_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + BOTTOM_RIGHT);
    }

    // Display Middle Main Menu
    public void displayMiddleMainMenu() {
        System.out.println("\n" + TOP_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + TOP_RIGHT);
        System.out.println(VERTICAL_LINE + centerText("MIDDLE SIDE MENU", MENU_WIDTH - 2) + VERTICAL_LINE);
        System.out.println(MIDDLE_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + MIDDLE_RIGHT);
        System.out.println(formatMenuOption(1, "Applicant Management"));
        System.out.println(formatMenuOption(2, "Time Slot Management"));
        System.out.println(formatMenuOption(3, "Company Management"));
        System.out.println(formatMenuOption(4, "Exit"));
        System.out.println(BOTTOM_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + BOTTOM_RIGHT);
    }

    // Display Company Main Menu
    public void displayCompanyMainMenu() {
        System.out.println("\n" + TOP_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + TOP_RIGHT);
        System.out.println(VERTICAL_LINE + centerText("COMPANY SIDE MENU", MENU_WIDTH - 2) + VERTICAL_LINE);
        System.out.println(MIDDLE_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + MIDDLE_RIGHT);
        System.out.println(formatMenuOption(1, "Job Post Management"));
        System.out.println(formatMenuOption(2, "Job Management"));
        System.out.println(formatMenuOption(3, "Job Requirement Management"));
        System.out.println(formatMenuOption(4, "Interview Management"));
        System.out.println(formatMenuOption(5, "Matching Management"));
        System.out.println(formatMenuOption(6, "Exit"));
        System.out.println(BOTTOM_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + BOTTOM_RIGHT);
    }

    // Display Applicant Management Menu
    public void displayApplicantMenu() {
        System.out.println("\n" + TOP_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + TOP_RIGHT);
        System.out.println(VERTICAL_LINE + centerText("APPLICANT MANAGEMENT", MENU_WIDTH - 2) + VERTICAL_LINE);
        System.out.println(MIDDLE_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + MIDDLE_RIGHT);
        System.out.println(formatMenuOption(1, "Add Applicant"));
        System.out.println(formatMenuOption(2, "Remove Applicant"));
        System.out.println(formatMenuOption(3, "View All Applicants"));
        System.out.println(formatMenuOption(4, "Search Applicant by ID"));
        System.out.println(formatMenuOption(5, "Filter Applicants"));
        System.out.println(formatMenuOption(6, "Exit"));
        System.out.println(BOTTOM_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + BOTTOM_RIGHT);
    }

    // Display Filter Applicant Menu
    public void filterApplicantMenu() {
        System.out.println("\n" + TOP_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + TOP_RIGHT);
        System.out.println(VERTICAL_LINE + centerText("FILTER APPLICANTS MENU", MENU_WIDTH - 2) + VERTICAL_LINE);
        System.out.println(MIDDLE_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + MIDDLE_RIGHT);
        System.out.println(formatMenuOption(1, "Filter by Age"));
        System.out.println(formatMenuOption(2, "Filter by Location"));
        System.out.println(formatMenuOption(3, "Filter by Years of Experience"));
        System.out.println(formatMenuOption(4, "Filter by Education Level"));
        System.out.println(formatMenuOption(5, "Filter by Skill"));
        System.out.println(formatMenuOption(6, "Exit"));
        System.out.println(BOTTOM_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + BOTTOM_RIGHT);
    }

    // Display Interview Management Menu
    public void displayInterviewMenu() {
        System.out.println("\n" + TOP_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + TOP_RIGHT);
        System.out.println(VERTICAL_LINE + centerText("INTERVIEW MANAGEMENT", MENU_WIDTH - 2) + VERTICAL_LINE);
        System.out.println(MIDDLE_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + MIDDLE_RIGHT);
        System.out.println(formatMenuOption(1, "Assign and View Interview Slots"));
        System.out.println(formatMenuOption(2, "Recruitment Table"));
        System.out.println(formatMenuOption(3, "Interview Overall Report"));
        System.out.println(formatMenuOption(4, "Exit"));
        System.out.println(BOTTOM_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + BOTTOM_RIGHT);
    }

    // Display Time Slot Interview Menu
    public void displayTimeSlotInterviewMenu(String company) {
        String title = company.toUpperCase() + " - INTERVIEW TIME SLOT MANAGEMENT";
        int dynamicWidth = Math.max(MENU_WIDTH, title.length() + 6);
        System.out.println("\n" + TOP_LEFT + repeat(HORIZONTAL_LINE, dynamicWidth - 2) + TOP_RIGHT);
        System.out.println(VERTICAL_LINE + centerText(title, dynamicWidth - 2) + VERTICAL_LINE);
        System.out.println(MIDDLE_LEFT + repeat(HORIZONTAL_LINE, dynamicWidth - 2) + MIDDLE_RIGHT);
        System.out.println(VERTICAL_LINE + " >  1. Assign Interview" + " ".repeat(dynamicWidth - 26) + VERTICAL_LINE);
        System.out.println(VERTICAL_LINE + " >  2. View Waiting Interviews" + " ".repeat(dynamicWidth - 33) + VERTICAL_LINE);
        System.out.println(VERTICAL_LINE + " >  3. View Completed Interviews" + " ".repeat(dynamicWidth - 35) + VERTICAL_LINE);
        System.out.println(VERTICAL_LINE + " >  4. View Interviews Based on Skills" + " ".repeat(dynamicWidth - 41) + VERTICAL_LINE);
        System.out.println(VERTICAL_LINE + " >  5. View Interviews Based on Experience" + " ".repeat(dynamicWidth - 45) + VERTICAL_LINE);
        System.out.println(VERTICAL_LINE + " >  6. Suggest Time Slot to Middle Side" + " ".repeat(dynamicWidth - 42) + VERTICAL_LINE);
        System.out.println(VERTICAL_LINE + " >  7. Give Interview Feedback" + " ".repeat(dynamicWidth - 33) + VERTICAL_LINE);
        System.out.println(VERTICAL_LINE + " >  8. View Time Slot Table" + " ".repeat(dynamicWidth - 30) + VERTICAL_LINE);
        System.out.println(VERTICAL_LINE + " >  9. Search" + " ".repeat(dynamicWidth - 16) + VERTICAL_LINE);
        System.out.println(VERTICAL_LINE + " > 10. Back to Previous Menu" + " ".repeat(dynamicWidth - 31) + VERTICAL_LINE);
        System.out.println(BOTTOM_LEFT + repeat(HORIZONTAL_LINE, dynamicWidth - 2) + BOTTOM_RIGHT);
    }

    // Display Recruitment Menu
    public void displayRecruitmentMenu() {
        System.out.println("\n" + TOP_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + TOP_RIGHT);
        System.out.println(VERTICAL_LINE + centerText("RECRUITMENT OPTIONS", MENU_WIDTH - 2) + VERTICAL_LINE);
        System.out.println(MIDDLE_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + MIDDLE_RIGHT);
        System.out.println(formatMenuOption(1, "Filter Applicants by Rating"));
        System.out.println(formatMenuOption(2, "Interview Feedback"));
        System.out.println(formatMenuOption(3, "Accept or Reject Applicants"));
        System.out.println(formatMenuOption(4, "Back to Previous Menu"));
        System.out.println(BOTTOM_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + BOTTOM_RIGHT);
    }

    // Display Time Slot Table Header
    public void printTimeSlotTableHeader() {
        System.out.println("\n+" + repeat(TABLE_HORIZONTAL, 14) + "+" + repeat(TABLE_HORIZONTAL, 23) + "+" + repeat(TABLE_HORIZONTAL, 23) + "+" + repeat(TABLE_HORIZONTAL, 15) + "+");
        System.out.println(TABLE_VERTICAL + " Time Slot ID " + " " + TABLE_VERTICAL + "     Start Time        " + " " + TABLE_VERTICAL + "      End Time         " + " " + TABLE_VERTICAL + " Availability  " + " " + TABLE_VERTICAL);
        System.out.println("+" + repeat(TABLE_HORIZONTAL, 14) + "+" + repeat(TABLE_HORIZONTAL, 23) + "+" + repeat(TABLE_HORIZONTAL, 23) + "+" + repeat(TABLE_HORIZONTAL, 15) + "+");
    }

    // Print Time Slot Row
    public void printTimeSlotRow(DoublyLinkedListInterface<TimeSlot> timeSlots) {
        for (TimeSlot timeSlot : timeSlots) {
            System.out.println(timeSlot);
        }
        System.out.println("+" + repeat(TABLE_HORIZONTAL, 14) + "+" + repeat(TABLE_HORIZONTAL, 23) + "+" + repeat(TABLE_HORIZONTAL, 23) + "+" + repeat(TABLE_HORIZONTAL, 15) + "+");
    }

    // Display Interview Report Menu
    public void displayInterviewReport() {
        System.out.println("\n" + TOP_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + TOP_RIGHT);
        System.out.println(VERTICAL_LINE + centerText("INTERVIEW OVERALL REPORT", MENU_WIDTH - 2) + VERTICAL_LINE);
        System.out.println(MIDDLE_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + MIDDLE_RIGHT);
        System.out.println(formatMenuOption(1, "Overall Report"));
        System.out.println(formatMenuOption(2, "Back to Previous Menu"));
        System.out.println(BOTTOM_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + BOTTOM_RIGHT);
    }

    // Display Accept or Reject Applicants Menu
    public void acceptOrRejectApplicantsMenu() {
        System.out.println("\n" + TOP_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + TOP_RIGHT);
        System.out.println(VERTICAL_LINE + centerText("ACCEPT OR REJECT APPLICANTS", MENU_WIDTH - 2) + VERTICAL_LINE);
        System.out.println(MIDDLE_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + MIDDLE_RIGHT);
        System.out.println(formatMenuOption(1, "Accept Applicant"));
        System.out.println(formatMenuOption(2, "Reject Applicant"));
        System.out.println(formatMenuOption(3, "Back to Previous Menu"));
        System.out.println(BOTTOM_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + BOTTOM_RIGHT);
    }

    // Display Search Interview Menu
    public void displaySearchInterview() {
        System.out.println("\n" + TOP_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + TOP_RIGHT);
        System.out.println(VERTICAL_LINE + centerText("CONTINUE TO DIG?", MENU_WIDTH - 2) + VERTICAL_LINE);
        System.out.println(MIDDLE_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + MIDDLE_RIGHT);
        System.out.println(formatMenuOption(1, "Applicant's Details"));
        System.out.println(formatMenuOption(2, "Job Post Details"));
        System.out.println(formatMenuOption(3, "Exit"));
        System.out.println(BOTTOM_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + BOTTOM_RIGHT);
    }

    // Display Search Interview Applicant Details
    public void displaySearchInterviewApplicantDetails() {
        System.out.println("\n" + TOP_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + TOP_RIGHT);
        System.out.println(VERTICAL_LINE + centerText("SEARCH APPLICANT DETAILS", MENU_WIDTH - 2) + VERTICAL_LINE);
        System.out.println(BOTTOM_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + BOTTOM_RIGHT);
    }

    // Display Search Interview Job Details
    public void displaySearchInterviewJobDetails() {
        System.out.println("\n" + TOP_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + TOP_RIGHT);
        System.out.println(VERTICAL_LINE + centerText("SEARCH JOB DETAILS", MENU_WIDTH - 2) + VERTICAL_LINE);
        System.out.println(BOTTOM_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + BOTTOM_RIGHT);
    }

    // Print Interview Report
    public void printInterviewReport(Company company,
            DoublyLinkedListInterface<Interview> acceptedInterviews,
            DoublyLinkedListInterface<Interview> rejectedInterviews) {
        final int width = 125;
        final String separator = repeat("=", width);

        System.out.println(separator);
        System.out.println(ANSI_CYAN + centerText("TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY", width) + ANSI_RESET);
        System.out.println(ANSI_CYAN + centerText("INTERVIEW STATUS ANALYSIS REPORT", width) + ANSI_RESET);
        System.out.println(centerText("Generated at: " + java.time.LocalDateTime.now(), width));
        System.out.println(separator);
        System.out.println(centerText("Company: " + company.getCompanyName(), width));
        System.out.println();

        int acceptedCount = 0;
        int rejectedCount = 0;

        Map<String, Integer> applicantInterviewCount = new LinkedHashMap<>();

        System.out.println(centerText(">> ACCEPTED INTERVIEWS <<", width));
        System.out.println();

        boolean hasAccepted = false;
        for (Interview interview : acceptedInterviews) {
            if (interview.getApplicantAppliedJob().getJobPost().getCompany().equals(company)) {
                printInterviewDetails(interview, width);
                acceptedCount++;
                hasAccepted = true;

                String applicant = interview.getApplicantAppliedJob().getApplicant().getName();
                applicantInterviewCount.put(applicant, applicantInterviewCount.getOrDefault(applicant, 0) + 1);
            }
        }
        if (!hasAccepted) {
            System.out.println(centerText("X No accepted interviews found for this company.", width));
        }

        System.out.println();
        System.out.println(centerText(">> REJECTED INTERVIEWS <<", width));
        System.out.println();

        boolean hasRejected = false;
        for (Interview interview : rejectedInterviews) {
            if (interview.getApplicantAppliedJob().getJobPost().getCompany().equals(company)) {
                printInterviewDetails(interview, width);
                rejectedCount++;
                hasRejected = true;

                String applicant = interview.getApplicantAppliedJob().getApplicant().getName();
                applicantInterviewCount.put(applicant, applicantInterviewCount.getOrDefault(applicant, 0) + 1);
            }
        }
        if (!hasRejected) {
            System.out.println(centerText("X No rejected interviews found for this company.", width));
        }

        // Asterisk visualization
        System.out.println();
        System.out.println(repeat("-", width));
        System.out.println("No of interviews");
        for (int i = 5; i >= 1; i--) {
            System.out.printf("%2d | ", i);
            for (String applicant : applicantInterviewCount.keySet()) {
                System.out.print((applicantInterviewCount.get(applicant) == i ? "*   " : "    "));
            }
            System.out.println();
        }

        // Horizontal applicant list
        System.out.print("    ");
        for (String applicant : applicantInterviewCount.keySet()) {
            System.out.printf("|---> %-8s", applicant);
        }
        System.out.println("\n");

        // Summary
        System.out.println(separator);
        System.out.println(centerText("SUMMARY", width));
        System.out.println(separator);
        System.out.printf("%-30s : %d%n", "Total Accepted Interviews", acceptedCount);
        System.out.printf("%-30s : %d%n", "Total Rejected Interviews", rejectedCount);
        System.out.printf("%-30s : %d%n", "Total Interviews", acceptedCount + rejectedCount);

        // Applicants with most/least interviews
        int max = applicantInterviewCount.values().stream().max(Integer::compare).orElse(0);
        int min = applicantInterviewCount.values().stream().min(Integer::compare).orElse(0);

        List<String> most = new ArrayList<>();
        List<String> least = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : applicantInterviewCount.entrySet()) {
            if (entry.getValue() == max) {
                most.add(entry.getKey());
            }
            if (entry.getValue() == min) {
                least.add(entry.getKey());
            }
        }

        System.out.printf("%-30s : %s (%d)%n", "Applicants with most interviews", String.join(", ", most), max);
        System.out.printf("%-30s : %s (%d)%n", "Applicants with least interviews", String.join(", ", least), min);

        System.out.println(separator);
        System.out.println(centerText("END OF REPORT", width));
        System.out.println(separator);
    }

    // Print Interview Details
    private void printInterviewDetails(Interview interview, int width) {
        System.out.printf("%-20s : %s%n", "Interview ID", interview.getInterviewId());
        System.out.printf("%-20s : %s%n", "Applicant ID",
                interview.getApplicantAppliedJob().getApplicant().getApplicantId());
        System.out.printf("%-20s : %s%n", "Applicant Name",
                interview.getApplicantAppliedJob().getApplicant().getName());
        System.out.printf("%-20s : %s%n", "Time Slot", interview.getTimeslot().getTime());
        System.out.printf("%-20s : %s%n", "Mode", interview.getMode());
        System.out.printf("%-20s : %s%n", "Status", interview.getStatus());
        System.out.printf("%-20s : %s%n", "Feedback", interview.getFeedback());
        System.out.printf("%-20s : %d%n", "Favour Rate", interview.getFavourRate());
        System.out.println(repeat("-", width));
    }

    // Display Job Matching Menu
    public void displayJobMatchingMenu() {
        System.out.println("\n" + TOP_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + TOP_RIGHT);
        System.out.println(VERTICAL_LINE + centerText("MATCHING CATEGORIES", MENU_WIDTH - 2) + VERTICAL_LINE);
        System.out.println(MIDDLE_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + MIDDLE_RIGHT);
        System.out.println(formatMenuOption(1, "Proficiency Levels"));
        System.out.println(formatMenuOption(2, "Experience Levels and Job Requirements"));
        System.out.println(formatMenuOption(3, "Location Preferences"));
        System.out.println(formatMenuOption(4, "Summary Report"));
        System.out.println(formatMenuOption(5, "Exit"));
        System.out.println(BOTTOM_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + BOTTOM_RIGHT);
    }

    // Exit System Message
    public void exitSystem() {
        System.out.println("\n" + TOP_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + TOP_RIGHT);
        System.out.println(VERTICAL_LINE + centerText("EXITING SYSTEM...", MENU_WIDTH - 2) + VERTICAL_LINE);
        System.out.println(BOTTOM_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + BOTTOM_RIGHT);
    }

    // Print Applicant Table Header
    public void printApplicantTableHeader() {
        System.out.println("\n+" + repeat(TABLE_HORIZONTAL, 14) + "+" + repeat(TABLE_HORIZONTAL, 22) + "+" + repeat(TABLE_HORIZONTAL, 7) + "+" + repeat(TABLE_HORIZONTAL, 18) + "+" + repeat(TABLE_HORIZONTAL, 12) + "+" + repeat(TABLE_HORIZONTAL, 27) + "+" + repeat(TABLE_HORIZONTAL, 30) + "+" + repeat(TABLE_HORIZONTAL, 22) + "+");
        System.out.println(TABLE_VERTICAL + " Applicant ID " + " " + TABLE_VERTICAL + " Name                 " + " " + TABLE_VERTICAL + " Age   " + " " + TABLE_VERTICAL + " Location         " + " " + TABLE_VERTICAL + " Exp (Yr)   " + " " + TABLE_VERTICAL + " Education                 " + " " + TABLE_VERTICAL + " Skills                       " + " " + TABLE_VERTICAL + " Registration Date    " + " " + TABLE_VERTICAL);
        System.out.println("+" + repeat(TABLE_HORIZONTAL, 14) + "+" + repeat(TABLE_HORIZONTAL, 22) + "+" + repeat(TABLE_HORIZONTAL, 7) + "+" + repeat(TABLE_HORIZONTAL, 18) + "+" + repeat(TABLE_HORIZONTAL, 12) + "+" + repeat(TABLE_HORIZONTAL, 27) + "+" + repeat(TABLE_HORIZONTAL, 30) + "+" + repeat(TABLE_HORIZONTAL, 22) + "+");
    }

    // Print a Single Applicant Row
    public void printApplicantRow(Applicant applicant) {
        String skills = formatSkills(applicant.getSkills());
        String registrationTime = applicant.getDateAdded();

        System.out.printf(TABLE_VERTICAL + " %-12s " + TABLE_VERTICAL + " %-20s " + TABLE_VERTICAL + " %-5d " + TABLE_VERTICAL + " %-16s " + TABLE_VERTICAL + " %-10d " + TABLE_VERTICAL + " %-25s " + TABLE_VERTICAL + " %-28s " + TABLE_VERTICAL + " %-20s " + TABLE_VERTICAL + "\n",
                applicant.getApplicantId(), truncate(applicant.getName(), 20), applicant.getAge(),
                truncate(applicant.getLocation(), 16), applicant.getYearsOfExperience(),
                truncate(applicant.getEducationLevel(), 25), truncate(skills, 28), registrationTime);
    }

    // Print Applicants Table
    public void printApplicants(DoublyLinkedListInterface<Applicant> applicants) {
        if (applicants.isEmpty()) {
            System.out.println("No applicants found.");
            return;
        }

        printApplicantTableHeader();
        for (Applicant applicant : applicants) {
            printApplicantRow(applicant);
        }
        System.out.println("+" + repeat(TABLE_HORIZONTAL, 14) + "+" + repeat(TABLE_HORIZONTAL, 22) + "+" + repeat(TABLE_HORIZONTAL, 7) + "+" + repeat(TABLE_HORIZONTAL, 18) + "+" + repeat(TABLE_HORIZONTAL, 12) + "+" + repeat(TABLE_HORIZONTAL, 27) + "+" + repeat(TABLE_HORIZONTAL, 30) + "+" + repeat(TABLE_HORIZONTAL, 22) + "+");
    }

    public void printApplicantsTableFooter() {
        System.out.println("+" + repeat(TABLE_HORIZONTAL, 14) + "+" + repeat(TABLE_HORIZONTAL, 22) + "+" + repeat(TABLE_HORIZONTAL, 7) + "+" + repeat(TABLE_HORIZONTAL, 18) + "+" + repeat(TABLE_HORIZONTAL, 12) + "+" + repeat(TABLE_HORIZONTAL, 27) + "+" + repeat(TABLE_HORIZONTAL, 30) + "+" + repeat(TABLE_HORIZONTAL, 22) + "+");

    }

    // Format Skills for Display
    private String formatSkills(DoublyLinkedListInterface<Skill> skills) {
        if (skills.isEmpty()) {
            return "None";
        }

        StringBuilder skillNames = new StringBuilder();
        for (Skill skill : skills) {
            if (skillNames.length() > 0) {
                skillNames.append(", ");
            }
            skillNames.append(skill.getName());
        }

        String formattedSkills = skillNames.toString();
        return formattedSkills.length() > 28 ? formattedSkills.substring(0, 25) + "..." : formattedSkills;
    }

    // Print Skill Table Header
    public void printSkillTableHeader() {
        System.out.println("\n+" + repeat(TABLE_HORIZONTAL, 18) + "+" + repeat(TABLE_HORIZONTAL, 15) + "+" + repeat(TABLE_HORIZONTAL, 15) + "+");
        System.out.println(TABLE_VERTICAL + " Skill Name       " + " " + TABLE_VERTICAL + " Category      " + " " + TABLE_VERTICAL + " Proficiency   " + " " + TABLE_VERTICAL);
        System.out.println("+" + repeat(TABLE_HORIZONTAL, 18) + "+" + repeat(TABLE_HORIZONTAL, 15) + "+" + repeat(TABLE_HORIZONTAL, 15) + "+");
    }

    // Print a Single Skill Row
    public void printSkillRow(Skill skill) {
        System.out.printf(TABLE_VERTICAL + " %-16s " + TABLE_VERTICAL + " %-13s " + TABLE_VERTICAL + " %-13d " + TABLE_VERTICAL + "\n",
                truncate(skill.getName(), 16), truncate(skill.getCategory(), 13), skill.getProficiency_level());
    }

    // Print Skills Table
    public void printSkills(DoublyLinkedListInterface<Skill> skills) {
        if (skills.isEmpty()) {
            System.out.println("No skills found.");
            return;
        }

        printSkillTableHeader();
        for (Skill skill : skills) {
            printSkillRow(skill);
        }
        System.out.println("+" + repeat(TABLE_HORIZONTAL, 18) + "+" + repeat(TABLE_HORIZONTAL, 15) + "+" + repeat(TABLE_HORIZONTAL, 15) + "+");
    }

    public void printApplicantApplicationsTable(DoublyLinkedListInterface<ApplicantAppliedJob> apps, DoublyLinkedListInterface<Interview> interviews) {
        System.out.println("=====================================================================================================================");
        System.out.printf("| %-3s | %-30s | %-20s | %-12s | %-30s |\n", "No.", "Job Title", "Company", "Status", "Interview TimeSlot");
        System.out.println("---------------------------------------------------------------------------------------------------------------------");

        int index = 1;
        for (ApplicantAppliedJob app : apps) {
            String jobTitle = app.getJobPost().getJob().getTitle();
            String company = app.getJobPost().getCompany().getCompanyName();
            String status = "Pending";
            String timeSlot = "Not Scheduled";

            for (Interview interview : interviews) {
                if (interview.getApplicantAppliedJob().equals(app)) {
                    status = interview.getStatus();
                    TimeSlot ts = interview.getTimeslot();
                    timeSlot = ts.getTime() + ", " + ts.getDate() + " at " + ts.getLocation();
                    break;
                }
            }

            System.out.printf("| %-3d | %-30s | %-20s | %-12s | %-30s |\n", index++, jobTitle, company, status, timeSlot);
        }

        System.out.println("=====================================================================================================================");
    }

    // Helper method to repeat a string
    public String repeat(String s, int count) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append(s);
        }
        return builder.toString();
    }

    // Helper method to truncate strings to fit within column widths
    private String truncate(String text, int maxLength) {
        if (text == null) {
            return "N/A";
        }
        return text.length() > maxLength ? text.substring(0, maxLength - 3) + "..." : text;

    }

    // Helper method to print timestamp
    public void printTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("🕒 Generated On: " + now.format(formatter));
    }

    // Helper method to print end of report
    public void printEndOfReport(int width) {
        System.out.println(repeat("=", width));
        System.out.println(String.format("%" + (width + "END OF REPORT".length()) / 2 + "s", "END OF REPORT"));
        System.out.println(repeat("=", width));
    }

    // Yoke Yau - Report Printing ===============================================
    // =====================================================================
    public void printApplicantMatchReportHeader(Applicant applicant, int totalJobsApplied, String topJobTitle, double highestScore, int width) {
        String separator = repeat("=", width);
        // Center-align headers and other text
        System.out.println(String.format("%" + (width + separator.length()) / 2 + "s", separator));
        System.out.println(String.format("%" + (width + "TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY".length()) / 2 + "s", "TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY"));
        System.out.println(String.format("%" + (width + applicant.getName().length()) / 2 + "s", applicant.getName()));
        System.out.println(String.format("%" + (width + "INTERNSHIP APPLICATION REPORT".length()) / 2 + "s", "INTERNSHIP APPLICATION REPORT"));
        System.out.println();
        System.out.println(String.format("%" + (width + "OVERALL JOB MATCHING REPORT FOR APPLICANT".length()) / 2 + "s", "OVERALL JOB MATCHING REPORT FOR APPLICANT"));
        System.out.println(String.format("%" + (width + separator.length()) / 2 + "s", separator));
        System.out.println();
        System.out.println(String.format("%" + (width + ("Total Jobs Applied: " + totalJobsApplied).length()) / 2 + "s", "Total Jobs Applied: " + totalJobsApplied));
        System.out.println(String.format("%" + (width + ("Top Job: " + topJobTitle + " (Score: " + String.format("%.2f%%", highestScore) + ")").length()) / 2 + "s", "Top Job: " + topJobTitle + " (Score: " + String.format("%.2f%%", highestScore) + ")"));
        System.out.println();
        System.out.println(String.format("%" + (width + separator.length()) / 2 + "s", separator));

        // Header for job details including Level
        String header = String.format("%-20s | %-20s | %-20s | %-15s | %-10s", "🏢 Company", "📌 Job Title", "📍 Job Location", "⭐ Match Score", "📊 Level");
        System.out.println(header);
        System.out.println(String.format("%" + (width + separator.length()) / 2 + "s", separator));

    }

    public void printCompanyMatchReportHeader(String companyId, int totalApplicants, String topApplicant, double highestScore, int width) {
        String separator = repeat("=", width);
        String header = String.format("%-20s | %-20s | %-20s | %-15s | %-10s", "👤 Applicant Name", "📌 Job Title", "📍 Job Location", "⭐ Match Score", "📊 Level");

        // Center-align headers and other text
        System.out.println(String.format("%" + (width + separator.length()) / 2 + "s", separator));
        System.out.println(String.format("%" + (width + "TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY".length()) / 2 + "s", "TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY"));
        System.out.println(String.format("%" + (width + "INTERNSHIP APPLICATION PROGRAM".length()) / 2 + "s", "INTERNSHIP APPLICATION PROGRAM"));
        System.out.println();
        System.out.println(String.format("%" + (width + "OVERALL SCORE MATCHING RANKING REPORT".length()) / 2 + "s", "OVERALL SCORE MATCHING RANKING REPORT"));
        System.out.println(String.format("%" + (width + separator.length()) / 2 + "s", separator));
        System.out.println();
        System.out.println(String.format("%" + (width + ("📊 Job-Applicant Overall Match Report for Company: " + companyId).length()) / 2 + "s", "📊 Job-Applicant Overall Match Report for Company: " + companyId));
        System.out.println(String.format("%" + (width + ("Total Applicants Considered: " + totalApplicants).length()) / 2 + "s", "Total Applicants Considered: " + totalApplicants));
        System.out.println(String.format("%" + (width + ("Top Applicant: " + topApplicant + " (Score: " + String.format("%.2f%%", highestScore) + ")").length()) / 2 + "s", "Top Applicant: " + topApplicant + " (Score: " + String.format("%.2f%%", highestScore) + ")"));
        System.out.println();
        System.out.println(String.format("%" + (width + separator.length()) / 2 + "s", separator));
        System.out.println(header);
        System.out.println(String.format("%" + (width + separator.length()) / 2 + "s", separator));
    }

    // Helper method to format job requirements nicely
    private String formatJobRequirements(DoublyLinkedListInterface<JobRequirements> requirements) {
        if (requirements.isEmpty()) {
            return "No specific requirements listed.";
        }

        StringBuilder formatted = new StringBuilder();
        for (JobRequirements req : requirements) {
            if (formatted.length() > 0) {
                formatted.append(", ");
            }
            formatted.append(req.getName())
                    .append(" (")
                    .append(req.getProficiencyLevel())
                    .append(")");
        }
        return formatted.toString();
    }

    // Helper method to format applicant skills as inline string for table display
    private String formatApplicantSkills(DoublyLinkedListInterface<Skill> skills) {
        if (skills.isEmpty()) {
            return "No skills";
        }

        StringBuilder formatted = new StringBuilder();
        for (Skill skill : skills) {
            if (formatted.length() > 0) {
                formatted.append(", ");
            }
            formatted.append(skill.getName())
                    .append(" (")
                    .append(skill.getProficiency_level())
                    .append(")");
        }

        return formatted.toString();
    }

    // Print out the suitable job post with a better format
    public void listJobPosts(Applicant applicant, DoublyLinkedListInterface<JobPost> jobPosts, String status) {
        System.out.println("\n📄 " + status + " Job Matches for: " + applicant.getName());

        if (jobPosts.isEmpty()) {
            System.out.println("❌ No " + status.toLowerCase() + " jobs found for you. Keep learning and check back later!");
        } else {
            System.out.println("✅ " + jobPosts.size() + " " + status + " job(s) found:\n");

            // Table header
            String header = String.format(
                    "%-4s %-25s %-20s %-20s %-12s %-40s",
                    "No.", "Company", "Job Title", "Location", "Experience", "Requirements"
            );
            System.out.println(header);
            System.out.println(repeat("=", header.length()));

            int count = 1;
            for (JobPost jobPost : jobPosts) {
                String company = jobPost.getCompany().getCompanyName() + " (" + jobPost.getCompany().getCompanyLocation() + ")";
                String title = jobPost.getJob().getTitle();
                String location = jobPost.getJob().getLocation();
                String experience = jobPost.getJob().getRequired_experience() + " yrs";
                String requirements = formatJobRequirements(jobPost.getJob().getJobRequirements());

                // Truncate if too long
                if (requirements.length() > 38) {
                    requirements = requirements.substring(0, 37) + "...";
                }

                System.out.printf(
                        "%-4d %-25s %-20s %-20s %-12s %-40s\n",
                        count, company, title, location, experience, requirements
                );
                count++;
            }
        }
    }

    // Print out the suitable applicants with enhanced formatting
    public void listApplicants(DoublyLinkedListInterface<Applicant> applicants, JobPost jobPost, String status) {
        System.out.println("\n👥 " + status + " Applicants for " + jobPost.getJob().getTitle() + " at " + jobPost.getCompany().getCompanyName());

        if (applicants.isEmpty()) {
            System.out.println("❌ No " + status.toLowerCase() + " applicants found.");
        } else {
            System.out.println("✅ " + applicants.size() + " " + status + " applicant(s) found:\n");

            // Print table header
            String header = String.format(
                    "%-4s %-20s %-15s %-15s %-12s %-30s",
                    "No.", "Name", "Education", "Location", "Experience", "Skills"
            );
            System.out.println(header);
            System.out.println(repeat("=", header.length()));

            // Print each applicant's details in a row
            int count = 1;
            for (Applicant applicant : applicants) {
                String skills = formatApplicantSkills(applicant.getSkills());
                if (skills.length() > 28) {
                    skills = skills.substring(0, 27) + "..."; // Truncate long skills for clean layout
                }
                System.out.printf(
                        "%-4d %-20s %-15s %-15s %-12s %-30s\n",
                        count,
                        applicant.getName(),
                        applicant.getEducationLevel(),
                        applicant.getLocation(),
                        applicant.getYearsOfExperience() + " yr",
                        skills
                );
                count++;
            }
        }
    }

// SHU HAN USE===============================================================================================================================================
    // Display Company Management Menu
    public void displayCompanyManagement() {
        System.out.println("\n" + TOP_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + TOP_RIGHT);
        System.out.println(VERTICAL_LINE + centerText("COMPANY MANAGEMENT", MENU_WIDTH - 2) + VERTICAL_LINE);
        System.out.println(MIDDLE_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + MIDDLE_RIGHT);
        System.out.println(formatMenuOption(1, "Add Company"));
        System.out.println(formatMenuOption(2, "Edit Company"));
        System.out.println(formatMenuOption(3, "View Company"));
        System.out.println(formatMenuOption(4, "Remove Company"));
        System.out.println(formatMenuOption(5, "View Removed Company"));
        System.out.println(formatMenuOption(6, "Exit"));
        System.out.println(BOTTOM_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + BOTTOM_RIGHT);
    }
    // Print Company Table Header
    public void printCompanyTableHeader() {
        System.out.println("\n+" + repeat(TABLE_HORIZONTAL, 14) + "+" + repeat(TABLE_HORIZONTAL, 22) + "+" + repeat(TABLE_HORIZONTAL, 18) + "+" + repeat(TABLE_HORIZONTAL, 12) + "+" + repeat(TABLE_HORIZONTAL, 30) + "+");
        System.out.println(TABLE_VERTICAL + " Company ID " + " " + TABLE_VERTICAL + " Company Name           " + " " + TABLE_VERTICAL + " Location         " + " " + TABLE_VERTICAL + " Company Size " + " " + TABLE_VERTICAL + " Description                      " + TABLE_VERTICAL);
        System.out.println("+" + repeat(TABLE_HORIZONTAL, 14) + "+" + repeat(TABLE_HORIZONTAL, 22) + "+" + repeat(TABLE_HORIZONTAL, 18) + "+" + repeat(TABLE_HORIZONTAL, 12) + "+" + repeat(TABLE_HORIZONTAL, 30) + "+");
    }
    // Print a Single Company Row
    public void printCompanyRow(Company company) {
        String description = truncate(company.getCompanyDescription(), 30);  // Truncate description to fit the table width

        System.out.printf(TABLE_VERTICAL + " %-12s " + TABLE_VERTICAL + " %-20s " + TABLE_VERTICAL + " %-16s " + TABLE_VERTICAL + " %-10d " + TABLE_VERTICAL + " %-28s " + TABLE_VERTICAL + "\n",
                company.getCompanyId(), truncate(company.getCompanyName(), 20), truncate(company.getCompanyLocation(), 16),
                company.getCompanySize(), description);
    }
    // Print Companies Table
    public void printCompanies(DoublyLinkedListInterface<Company> companies) {
        if (companies.isEmpty()) {
            System.out.println("No companies found.");
            return;
        }

        printCompanyTableHeader();
        for (Company company : companies) {
            printCompanyRow(company);
        }
        System.out.println("+" + repeat(TABLE_HORIZONTAL, 14) + "+" + repeat(TABLE_HORIZONTAL, 22) + "+" + repeat(TABLE_HORIZONTAL, 18) + "+" + repeat(TABLE_HORIZONTAL, 12) + "+" + repeat(TABLE_HORIZONTAL, 30) + "+");
    }
    // Print Companies Footer
    public void printCompaniesTableFooter() {
        System.out.println("+" + repeat(TABLE_HORIZONTAL, 14) + "+" + repeat(TABLE_HORIZONTAL, 22) + "+" + repeat(TABLE_HORIZONTAL, 18) + "+" + repeat(TABLE_HORIZONTAL, 12) + "+" + repeat(TABLE_HORIZONTAL, 30) + "+");
    }

    public void printRemovedCompanyTableHeader() {
        System.out.println("\n+" + repeat(TABLE_HORIZONTAL, 12) + "+" 
                             + repeat(TABLE_HORIZONTAL, 22) + "+" 
                             + repeat(TABLE_HORIZONTAL, 18) + "+" 
                             + repeat(TABLE_HORIZONTAL, 14) + "+" 
                             + repeat(TABLE_HORIZONTAL, 30) + "+" 
                             + repeat(TABLE_HORIZONTAL, 21) + "+");
        System.out.println(TABLE_VERTICAL + " Company ID  " + TABLE_VERTICAL 
                             + " Name                 " + TABLE_VERTICAL 
                             + " Location          " + TABLE_VERTICAL 
                             + " Size (Emp)   " + TABLE_VERTICAL 
                             + " Description                  " + TABLE_VERTICAL 
                             + " Removed At         " + TABLE_VERTICAL);
        System.out.println("+" + repeat(TABLE_HORIZONTAL, 12) + "+" 
                             + repeat(TABLE_HORIZONTAL, 22) + "+" 
                             + repeat(TABLE_HORIZONTAL, 18) + "+" 
                             + repeat(TABLE_HORIZONTAL, 14) + "+" 
                             + repeat(TABLE_HORIZONTAL, 30) + "+" 
                             + repeat(TABLE_HORIZONTAL, 21) + "+");
    }
    public void printRemovedCompanyRow(Company company) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.printf(TABLE_VERTICAL + " %-10s " + TABLE_VERTICAL + " %-20s " + TABLE_VERTICAL
                + " %-16s " + TABLE_VERTICAL + " %-12d " + TABLE_VERTICAL + " %-28s " + TABLE_VERTICAL
                + " %-19s " + TABLE_VERTICAL + "\n",
                company.getCompanyId(),
                truncate(company.getCompanyName(), 20),
                truncate(company.getCompanyLocation(), 16),
                company.getCompanySize(),
                truncate(company.getCompanyDescription(), 28),
                company.getRemovedAt().format(formatter));
    }
    public void printRemovedCompanyTableFooter() {
        System.out.println("+" + repeat(TABLE_HORIZONTAL, 12) + "+" 
                             + repeat(TABLE_HORIZONTAL, 22) + "+" 
                             + repeat(TABLE_HORIZONTAL, 18) + "+" 
                             + repeat(TABLE_HORIZONTAL, 14) + "+" 
                             + repeat(TABLE_HORIZONTAL, 30) + "+" 
                             + repeat(TABLE_HORIZONTAL, 21) + "+");
    }
    public void printRemovedCompanies(DoublyLinkedListInterface<Company> removedCompanies) {
        if (removedCompanies.isEmpty()) {
            System.out.println("No removed companies found.");
            return;
        }

        printRemovedCompanyTableHeader();
        for (Company company : removedCompanies) {
            printRemovedCompanyRow(company);
        }
        printRemovedCompanyTableFooter();
    }

    
    // Display JobPost Management Menu
    public void displayJobPostManagement() {
        System.out.println("\n" + TOP_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + TOP_RIGHT);
        System.out.println(VERTICAL_LINE + centerText("JOB POST MANAGEMENT", MENU_WIDTH - 2) + VERTICAL_LINE);
        System.out.println(MIDDLE_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + MIDDLE_RIGHT);
        System.out.println(formatMenuOption(1, "Add Job Post"));
        System.out.println(formatMenuOption(2, "Edit Job Post"));
        System.out.println(formatMenuOption(3, "View Job Post"));
        System.out.println(formatMenuOption(4, "Remove Job Post"));
        System.out.println(formatMenuOption(5, "View Removed Post"));
        System.out.println(formatMenuOption(6, "Search Job Post"));
        System.out.println(formatMenuOption(7, "Job Post Report"));
        System.out.println(formatMenuOption(8, "Exit"));
        System.out.println(BOTTOM_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + BOTTOM_RIGHT);
    }
    // Print JobPost Table Header
    public void printJobPostTableHeader() {
        System.out.println("\n+" + repeat(TABLE_HORIZONTAL, 14) + "+" + repeat(TABLE_HORIZONTAL, 22) + "+" + repeat(TABLE_HORIZONTAL, 18) + "+" + repeat(TABLE_HORIZONTAL, 16) + "+" + repeat(TABLE_HORIZONTAL, 12) + "+" + repeat(TABLE_HORIZONTAL, 30) + "+");
        System.out.println(TABLE_VERTICAL + " Job Post ID " + " " + TABLE_VERTICAL + " Job Title             " + " " + TABLE_VERTICAL + " Company Name       " + " " + TABLE_VERTICAL + " Location        " + " " + TABLE_VERTICAL + " Exp (Yr)      " + " " + TABLE_VERTICAL + " Salary ($)               " + TABLE_VERTICAL);
        System.out.println("+" + repeat(TABLE_HORIZONTAL, 14) + "+" + repeat(TABLE_HORIZONTAL, 22) + "+" + repeat(TABLE_HORIZONTAL, 18) + "+" + repeat(TABLE_HORIZONTAL, 16) + "+" + repeat(TABLE_HORIZONTAL, 12) + "+" + repeat(TABLE_HORIZONTAL, 30) + "+");
    }
    // Print a Single JobPost Row
    public void printJobPostRow(JobPost jobPost) {
        System.out.printf(TABLE_VERTICAL + " %-12s " + TABLE_VERTICAL + " %-20s " + TABLE_VERTICAL + " %-16s " + TABLE_VERTICAL + " %-14s " + TABLE_VERTICAL + " %-10d " + TABLE_VERTICAL + " $%-28.2f " + TABLE_VERTICAL + "\n",
                jobPost.getJobPostId(), truncate(jobPost.getJob().getTitle(), 20), truncate(jobPost.getCompany().getCompanyName(), 16),
                truncate(jobPost.getJob().getLocation(), 14), jobPost.getJob().getRequired_experience(), jobPost.getJob().getSalary());
    }
    // Print JobPosts Table
    public void printJobPosts(DoublyLinkedListInterface<JobPost> jobPosts) {
        if (jobPosts.isEmpty()) {
            System.out.println("No job posts found.");
            return;
        }

        printJobPostTableHeader();
        for (JobPost jobPost : jobPosts) {
            printJobPostRow(jobPost);
        }
        System.out.println("+" + repeat(TABLE_HORIZONTAL, 14) + "+" + repeat(TABLE_HORIZONTAL, 22) + "+" + repeat(TABLE_HORIZONTAL, 18) + "+" + repeat(TABLE_HORIZONTAL, 16) + "+" + repeat(TABLE_HORIZONTAL, 12) + "+" + repeat(TABLE_HORIZONTAL, 30) + "+");
    }
    // Print JobPost Table Footer
    public void printJobPostsTableFooter() {
        System.out.println("+" + repeat(TABLE_HORIZONTAL, 14) + "+" + repeat(TABLE_HORIZONTAL, 22) + "+" + repeat(TABLE_HORIZONTAL, 18) + "+" + repeat(TABLE_HORIZONTAL, 16) + "+" + repeat(TABLE_HORIZONTAL, 12) + "+" + repeat(TABLE_HORIZONTAL, 30) + "+");
    }
    // Print Removed JobPost Table Header
    public void printRemovedJobPostTableHeader() {
        System.out.println("\n+" + repeat(TABLE_HORIZONTAL, 14) + "+" 
                             + repeat(TABLE_HORIZONTAL, 20) + "+" 
                             + repeat(TABLE_HORIZONTAL, 20) + "+" 
                             + repeat(TABLE_HORIZONTAL, 16) + "+" 
                             + repeat(TABLE_HORIZONTAL, 12) + "+" 
                             + repeat(TABLE_HORIZONTAL, 16) + "+" 
                             + repeat(TABLE_HORIZONTAL, 21) + "+");
        System.out.println(TABLE_VERTICAL + " JobPost ID    " + TABLE_VERTICAL 
                             + " Job Title           " + TABLE_VERTICAL 
                             + " Company Name       " + TABLE_VERTICAL 
                             + " Location        " + TABLE_VERTICAL 
                             + " Exp (Yr)    " + TABLE_VERTICAL 
                             + " Salary ($)      " + TABLE_VERTICAL 
                             + " Removed At         " + TABLE_VERTICAL);
        System.out.println("+" + repeat(TABLE_HORIZONTAL, 14) + "+" 
                             + repeat(TABLE_HORIZONTAL, 20) + "+" 
                             + repeat(TABLE_HORIZONTAL, 20) + "+" 
                             + repeat(TABLE_HORIZONTAL, 16) + "+" 
                             + repeat(TABLE_HORIZONTAL, 12) + "+" 
                             + repeat(TABLE_HORIZONTAL, 16) + "+" 
                             + repeat(TABLE_HORIZONTAL, 21) + "+");
    }
    // Print a single Removed JobPost row
    public void printRemovedJobPostRow(JobPost jobPost) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Job job = jobPost.getJob();
        Company company = jobPost.getCompany();

        System.out.printf(TABLE_VERTICAL + " %-12s " + TABLE_VERTICAL + " %-18s " + TABLE_VERTICAL
                + " %-18s " + TABLE_VERTICAL + " %-14s " + TABLE_VERTICAL + " %-10d " + TABLE_VERTICAL 
                + " $%-14.2f " + TABLE_VERTICAL + " %-19s " + TABLE_VERTICAL + "\n",
                jobPost.getJobPostId(),
                truncate(job.getTitle(), 18),
                truncate(company.getCompanyName(), 18),
                truncate(job.getLocation(), 14),
                job.getRequired_experience(),
                job.getSalary(),
                jobPost.getRemovedAt().format(formatter));
    }
    // Print Removed JobPost Table Footer
    public void printRemovedJobPostTableFooter() {
        System.out.println("+" + repeat(TABLE_HORIZONTAL, 14) + "+" 
                             + repeat(TABLE_HORIZONTAL, 20) + "+" 
                             + repeat(TABLE_HORIZONTAL, 20) + "+" 
                             + repeat(TABLE_HORIZONTAL, 16) + "+" 
                             + repeat(TABLE_HORIZONTAL, 12) + "+" 
                             + repeat(TABLE_HORIZONTAL, 16) + "+" 
                             + repeat(TABLE_HORIZONTAL, 21) + "+");
    }
    // Print all Removed JobPosts
    public void printRemovedJobPosts(DoublyLinkedListInterface<JobPost> removedJobPosts) {
        if (removedJobPosts.isEmpty()) {
            System.out.println("No removed job posts found.");
            return;
        }

        printRemovedJobPostTableHeader();
        for (JobPost jobPost : removedJobPosts) {
            printRemovedJobPostRow(jobPost);
        }
        printRemovedJobPostTableFooter();
    }

    
    
    // Display Job Management Menu
    public void displayJobManagement() {
        System.out.println("\n" + TOP_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + TOP_RIGHT);
        System.out.println(VERTICAL_LINE + centerText("JOB MANAGEMENT", MENU_WIDTH - 2) + VERTICAL_LINE);
        System.out.println(MIDDLE_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + MIDDLE_RIGHT);
        System.out.println(formatMenuOption(1, "Add Job "));
        System.out.println(formatMenuOption(2, "Edit Job "));
        System.out.println(formatMenuOption(3, "View Job "));
        System.out.println(formatMenuOption(4, "Remove Job "));
        System.out.println(formatMenuOption(5, "View Removed Job"));
        System.out.println(formatMenuOption(6, "Search Job "));
        System.out.println(formatMenuOption(7, "Exit"));
        System.out.println(BOTTOM_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + BOTTOM_RIGHT);
    }
    // Print Job Table Header
    public void printJobTableHeader() {
        System.out.println("\n+" + repeat(TABLE_HORIZONTAL, 12) + "+" + repeat(TABLE_HORIZONTAL, 20) + "+"
                + repeat(TABLE_HORIZONTAL, 16) + "+" + repeat(TABLE_HORIZONTAL, 12) + "+"
                + repeat(TABLE_HORIZONTAL, 16) + "+");
        System.out.println(TABLE_VERTICAL + " Job ID      " + TABLE_VERTICAL + " Title               "
                + TABLE_VERTICAL + " Location        " + TABLE_VERTICAL + " Exp (Yr)    "
                + TABLE_VERTICAL + " Salary ($)      " + TABLE_VERTICAL);
        System.out.println("+" + repeat(TABLE_HORIZONTAL, 12) + "+" + repeat(TABLE_HORIZONTAL, 20) + "+"
                + repeat(TABLE_HORIZONTAL, 16) + "+" + repeat(TABLE_HORIZONTAL, 12) + "+"
                + repeat(TABLE_HORIZONTAL, 16) + "+");
    }
    // Print a single Job row
    public void printJobRow(Job job) {
        System.out.printf(TABLE_VERTICAL + " %-10s " + TABLE_VERTICAL + " %-18s " + TABLE_VERTICAL
                + " %-14s " + TABLE_VERTICAL + " %-10d " + TABLE_VERTICAL + " $%-14.2f " + TABLE_VERTICAL + "\n",
                job.getJobId(),
                truncate(job.getTitle(), 18),
                truncate(job.getLocation(), 14),
                job.getRequired_experience(),
                job.getSalary());

        // Print associated requirements if available
        if (job.getJobRequirements() != null && !job.getJobRequirements().isEmpty()) {
            int count = 1;
            for (JobRequirements req : job.getJobRequirements()) {
                System.out.printf("    -> Req %d: %-22s | Level: %-15s | Category: %-15s\n",
                        count++,
                        truncate(req.getName(), 22),
                        truncate(req.getProficiencyLevel(), 15),
                        truncate(req.getCategory(), 15));
            }
        } else {
            System.out.println("    -> No requirements listed.");
        }
    }
    // Print all Job records
    public void printJobs(DoublyLinkedListInterface<Job> jobList) {
        if (jobList.isEmpty()) {
            System.out.println("No jobs found.");
            return;
        }

        printJobTableHeader();
        for (Job job : jobList) {
            printJobRow(job);
        }
        printJobTableFooter();
    }
    // Print Job Table Footer
    public void printJobTableFooter() {
        System.out.println("+" + repeat(TABLE_HORIZONTAL, 12) + "+" + repeat(TABLE_HORIZONTAL, 20) + "+"
                + repeat(TABLE_HORIZONTAL, 16) + "+" + repeat(TABLE_HORIZONTAL, 12) + "+"
                + repeat(TABLE_HORIZONTAL, 16) + "+");
    }
    // Print Removed Job Table Header
    public void printRemovedJobTableHeader() {
        System.out.println("\n+" + repeat(TABLE_HORIZONTAL, 12) + "+" 
                             + repeat(TABLE_HORIZONTAL, 20) + "+" 
                             + repeat(TABLE_HORIZONTAL, 16) + "+" 
                             + repeat(TABLE_HORIZONTAL, 12) + "+" 
                             + repeat(TABLE_HORIZONTAL, 16) + "+" 
                             + repeat(TABLE_HORIZONTAL, 21) + "+");
        System.out.println(TABLE_VERTICAL + " Removed ID  " + TABLE_VERTICAL 
                             + " Title               " + TABLE_VERTICAL 
                             + " Location        " + TABLE_VERTICAL 
                             + " Exp (Yr)    " + TABLE_VERTICAL 
                             + " Salary ($)      " + TABLE_VERTICAL 
                             + " Removed At         " + TABLE_VERTICAL);
        System.out.println("+" + repeat(TABLE_HORIZONTAL, 12) + "+" 
                             + repeat(TABLE_HORIZONTAL, 20) + "+" 
                             + repeat(TABLE_HORIZONTAL, 16) + "+" 
                             + repeat(TABLE_HORIZONTAL, 12) + "+" 
                             + repeat(TABLE_HORIZONTAL, 16) + "+" 
                             + repeat(TABLE_HORIZONTAL, 21) + "+");
    }
    // Print a single Removed Job row
    public void printRemovedJobRow(Job job) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.printf(TABLE_VERTICAL + " %-10s " + TABLE_VERTICAL + " %-18s " + TABLE_VERTICAL
                + " %-14s " + TABLE_VERTICAL + " %-10d " + TABLE_VERTICAL + " $%-14.2f "
                + TABLE_VERTICAL + " %-19s " + TABLE_VERTICAL + "\n",
                job.getJobId(),
                truncate(job.getTitle(), 18),
                truncate(job.getLocation(), 14),
                job.getRequired_experience(),
                job.getSalary(),
                job.getRemovedAt().format(formatter));
    }
    // Print Removed Job Table Footer
    public void printRemovedJobTableFooter() {
        System.out.println("+" + repeat(TABLE_HORIZONTAL, 12) + "+" 
                             + repeat(TABLE_HORIZONTAL, 20) + "+" 
                             + repeat(TABLE_HORIZONTAL, 16) + "+" 
                             + repeat(TABLE_HORIZONTAL, 12) + "+" 
                             + repeat(TABLE_HORIZONTAL, 16) + "+" 
                             + repeat(TABLE_HORIZONTAL, 21) + "+");
    }
    // Print all Removed Jobs
    public void printRemovedJobs(DoublyLinkedListInterface<Job> removedJobs) {
        if (removedJobs.isEmpty()) {
            System.out.println("No removed jobs found.");
            return;
        }

        printRemovedJobTableHeader();
        for (Job job : removedJobs) {
            printRemovedJobRow(job); // This should be the updated version showing all job info
        }
        printRemovedJobTableFooter();
    }


    // Display JobRequirement Management Menu
    public void displayJobRequirementManagement() {
        System.out.println("\n" + TOP_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + TOP_RIGHT);
        System.out.println(VERTICAL_LINE + centerText("JOB REQUIREMENT MANAGEMENT", MENU_WIDTH - 2) + VERTICAL_LINE);
        System.out.println(MIDDLE_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + MIDDLE_RIGHT);
        System.out.println(formatMenuOption(1, "Add Job Requirement"));
        System.out.println(formatMenuOption(2, "Edit Job Requirement"));
        System.out.println(formatMenuOption(3, "View Job Requirement"));
        System.out.println(formatMenuOption(4, "Remove Job Requirement"));
        System.out.println(formatMenuOption(5, "Display Removed Requirement"));
        System.out.println(formatMenuOption(6, "Search Job Requirement"));
        System.out.println(formatMenuOption(7, "Exit"));
        System.out.println(BOTTOM_LEFT + repeat(HORIZONTAL_LINE, MENU_WIDTH - 2) + BOTTOM_RIGHT);
    }
    // Print JobRequirement Table Header
    public void printJobRequirementTableHeader() {
        System.out.println("\n+" + repeat(TABLE_HORIZONTAL, 16) + "+" + repeat(TABLE_HORIZONTAL, 24) + "+" + repeat(TABLE_HORIZONTAL, 24) + "+" + repeat(TABLE_HORIZONTAL, 24) + "+");
        System.out.println(TABLE_VERTICAL + " Requirement ID  " + TABLE_VERTICAL + " Name                   " + TABLE_VERTICAL + " Proficiency Level     " + TABLE_VERTICAL + " Category               " + TABLE_VERTICAL);
        System.out.println("+" + repeat(TABLE_HORIZONTAL, 16) + "+" + repeat(TABLE_HORIZONTAL, 24) + "+" + repeat(TABLE_HORIZONTAL, 24) + "+" + repeat(TABLE_HORIZONTAL, 24) + "+");
    }
    // Print a single JobRequirement row
    public void printJobRequirementRow(JobRequirements req) {
        System.out.printf(TABLE_VERTICAL + " %-14s " + TABLE_VERTICAL + " %-22s " + TABLE_VERTICAL + " %-22s " + TABLE_VERTICAL + " %-22s " + TABLE_VERTICAL + "\n",
                req.getJobRequirementId(),
                truncate(req.getName(), 22),
                truncate(req.getProficiencyLevel(), 22),
                truncate(req.getCategory(), 22));
    }
    // Print the entire list of JobRequirements
    public void printJobRequirements(DoublyLinkedListInterface<JobRequirements> requirementsList) {
        if (requirementsList.isEmpty()) {
            System.out.println("No job requirements found.");
            return;
        }

        printJobRequirementTableHeader();
        for (JobRequirements req : requirementsList) {
            printJobRequirementRow(req);
        }
        printJobRequirementTableFooter();
    }
    // Print JobRequirement Table Footer
    public void printJobRequirementTableFooter() {
        System.out.println("+" + repeat(TABLE_HORIZONTAL, 16) + "+" + repeat(TABLE_HORIZONTAL, 24) + "+" + repeat(TABLE_HORIZONTAL, 24) + "+" + repeat(TABLE_HORIZONTAL, 24) + "+");
    }
    public void printRemovedJRTableHeader() {
    System.out.println("\n+" + repeat(TABLE_HORIZONTAL, 12) + "+"
                         + repeat(TABLE_HORIZONTAL, 20) + "+"
                         + repeat(TABLE_HORIZONTAL, 22) + "+"
                         + repeat(TABLE_HORIZONTAL, 18) + "+"
                         + repeat(TABLE_HORIZONTAL, 21) + "+");

    System.out.println(TABLE_VERTICAL + " Removed ID  " + TABLE_VERTICAL
                         + " Name               " + TABLE_VERTICAL
                         + " Proficiency Level     " + TABLE_VERTICAL
                         + " Category         " + TABLE_VERTICAL
                         + " Removed At         " + TABLE_VERTICAL);

    System.out.println("+" + repeat(TABLE_HORIZONTAL, 12) + "+"
                         + repeat(TABLE_HORIZONTAL, 20) + "+"
                         + repeat(TABLE_HORIZONTAL, 22) + "+"
                         + repeat(TABLE_HORIZONTAL, 18) + "+"
                         + repeat(TABLE_HORIZONTAL, 21) + "+");
}
    public void printRemovedJRRow(JobRequirements req) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.printf(TABLE_VERTICAL + " %-10s " + TABLE_VERTICAL 
                        + " %-18s " + TABLE_VERTICAL 
                        + " %-20s " + TABLE_VERTICAL 
                        + " %-16s " + TABLE_VERTICAL 
                        + " %-19s " + TABLE_VERTICAL + "\n",
                req.getJobRequirementId(),
                truncate(req.getName(), 18),
                truncate(req.getProficiencyLevel(), 20),
                truncate(req.getCategory(), 16),
                req.getRemovedAt().format(formatter));
    }
    public void printRemovedJRTableFooter() {
        System.out.println("+" + repeat(TABLE_HORIZONTAL, 12) + "+"
                             + repeat(TABLE_HORIZONTAL, 20) + "+"
                             + repeat(TABLE_HORIZONTAL, 22) + "+"
                             + repeat(TABLE_HORIZONTAL, 18) + "+"
                             + repeat(TABLE_HORIZONTAL, 21) + "+");
    }
    public void printRemovedJobRequirements(DoublyLinkedListInterface<JobRequirements> removedRequirements) {
        if (removedRequirements.isEmpty()) {
            System.out.println("No removed job requirements found.");
            return;
        }

        printRemovedJRTableHeader();
        for (JobRequirements req : removedRequirements) {
            printRemovedJRRow(req);
        }
        printRemovedJRTableFooter();
    }

    public void printJobPostReports(DoublyLinkedListInterface<JobPost> jobPosts) {
        final int width = 125;
        final String separator = repeat("=", width);

        // Display the overall statistics for active and inactive job posts
        List<Company> companies = new ArrayList<>();
        int totalActivePosts = 0;
        int totalInactivePosts = 0;

        // Collect company information and count posts
        for (JobPost jp : jobPosts) {
            if (jp.getRemovedAt() != null) {
                totalInactivePosts++;
                continue;
            }
            totalActivePosts++;

            // Add the company to the list (if not already there)
            Company company = jp.getCompany();
            if (!companies.contains(company)) {
                companies.add(company);
            }
        }

        // Display total active and inactive job posts
        System.out.println(separator);
        System.out.println(ANSI_CYAN + centerText("TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY", width) + ANSI_RESET);
        System.out.println(ANSI_CYAN + centerText("JOB POST SUMMARY REPORTS", width) + ANSI_RESET);
        System.out.println(centerText("Generated at: " + java.time.LocalDateTime.now(), width));
        System.out.println(separator);

        System.out.println(centerText(">> TOTAL JOB POSTS ACROSS ALL COMPANIES <<", width));
        System.out.printf("Active Job Posts           : %d%n", totalActivePosts);
        System.out.printf("Inactive Job Posts         : %d%n", totalInactivePosts);
        System.out.println(separator);

        // Display the list of companies with their IDs
        System.out.println("Available Companies:");
        for (Company company : companies) {
            System.out.println("ID: " + company.getCompanyId() + " - " + company.getCompanyName());
        }

        // Prompt user to input the company ID
        String input = inputUI.getInput("Enter the company ID to view its report (or 'X' to exit): ").toUpperCase();  // Convert input to uppercase to make it case-insensitive

        if (input.equalsIgnoreCase("X")) {
            System.out.println("Exiting the report view.");
            return;
        }

        // Try to find the company by the entered ID
        Company selectedCompany = null;
        for (Company company : companies) {
            if (company.getCompanyId().equalsIgnoreCase(input)) {  // Convert companyId to uppercase for case-insensitive comparison
                selectedCompany = company;
                break;
            }
        }

        if (selectedCompany == null) {
            System.out.println("Company ID not found. Please try again.");
            return;
        }

        // Display report for the selected company
        String selectedCompanyName = selectedCompany.getCompanyName();

        System.out.println(separator);
        System.out.println(centerText(">> JOB POST REPORT FOR: " + selectedCompanyName + " <<", width));

        // Active and Inactive Job Posts for selected company
        int activePosts = 0;
        int inactivePosts = 0;
        Map<String, Integer> locationMap = new LinkedHashMap<>();
        Map<String, Integer> salaryRangeMap = new LinkedHashMap<>();

        for (JobPost jp : jobPosts) {
            if (jp.getCompany().getCompanyId().equalsIgnoreCase(selectedCompany.getCompanyId())) {  // Case-insensitive comparison
                if (jp.getRemovedAt() != null) {
                    inactivePosts++;
                    continue;
                }
                activePosts++;

                // Location
                String location = jp.getJob().getLocation();
                locationMap.put(location, locationMap.getOrDefault(location, 0) + 1);

                // Salary Range
                double salary = jp.getJob().getSalary();
                String range;
                if (salary < 3000) {
                    range = "< RM3000";
                } else if (salary <= 5000) {
                    range = "RM3000 - RM5000";
                } else {
                    range = "> RM5000";
                }
                salaryRangeMap.put(range, salaryRangeMap.getOrDefault(range, 0) + 1);
            }
        }

        // Print the report
        System.out.printf("Active Job Posts           : %d%n", activePosts);
        System.out.printf("Inactive Job Posts         : %d%n", inactivePosts);
        System.out.println();

        System.out.println(centerText(">> JOB POSTS BY LOCATION <<", width));
        for (Map.Entry<String, Integer> entry : locationMap.entrySet()) {
            System.out.printf("%-40s : %d%n", entry.getKey(), entry.getValue());
        }
        System.out.println();

        System.out.println(centerText(">> JOB POSTS BY SALARY RANGE <<", width));
        for (Map.Entry<String, Integer> entry : salaryRangeMap.entrySet()) {
            System.out.printf("%-40s : %d%n", entry.getKey(), entry.getValue());
        }
        System.out.println(separator);
        System.out.println(centerText("END OF JOB POST REPORTS", width));
        System.out.println(separator);
    }

}
