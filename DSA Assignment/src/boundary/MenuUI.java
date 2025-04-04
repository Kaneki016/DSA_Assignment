package boundary;

import adt.DoublyLinkedListInterface;
import entities.Applicant;
import entities.Company;
import entities.Interview;
import entities.Skill;
import entities.TimeSlot;

/**
 * Handles the display of various menus in the system. Provides clear and
 * structured user interfaces for navigation.
 *
 * @author MAMBA
 */
public class MenuUI {

    private InputUI inputUI = new InputUI();

    public void displayMainMenu() {
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║           MAIN MENU              ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.println("║ 1. Client                        ║");
        System.out.println("║ 2. Agency                        ║");
        System.out.println("║ 3. Company                       ║");
        System.out.println("║ 4. Exit                          ║");
        System.out.println("╚══════════════════════════════════╝");
    }

    public void displayClientMainMenu() {
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║          CLIENT MENU             ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.println("║ 1. Register as a New Applicant   ║");
        System.out.println("║ 2. Edit Applicant                ║");
        System.out.println("║ 3. Job Application               ║");
        System.out.println("║ 4. Matching Management           ║");
        System.out.println("║ 5. Exit                          ║");
        System.out.println("╚══════════════════════════════════╝");
    }

    public void displayApplicantAppliedMenu() {
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║   APPLICANT JOB APPLICATION      ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.println("║ 1. View Available Jobs           ║");
        System.out.println("║ 2. Apply for a Job               ║");
        System.out.println("║ 3. View My Applications          ║");
        System.out.println("║ 4. Recommend Jobs                ║");
        System.out.println("║ 5. Exit                          ║");
        System.out.println("╚══════════════════════════════════╝");
    }

    public void displayMiddleMainMenu() {
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║       MIDDLE SIDE MENU           ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.println("║ 1. Applicant Management          ║");
        System.out.println("║ 2. Time Slot Management          ║");
        System.out.println("║ 3. Company Management            ║");
        System.out.println("║ 4. Exit                          ║");
        System.out.println("╚══════════════════════════════════╝");
    }

    public void displayCompanyMainMenu() {
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║      COMPANY SIDE MENU           ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.println("║ 1. Add Job Post                  ║");
        System.out.println("║ 2. Edit Job Post                 ║");
        System.out.println("║ 3. Remove Job Post               ║");
        System.out.println("║ 4. View Job Post                 ║");
        System.out.println("║ 5. View Job                      ║");
        System.out.println("║ 6. Interview Management          ║");
        System.out.println("║ 7. Generate Mock Data            ║");
        System.out.println("║ 8. Matching Management           ║");
        System.out.println("║ 9. Exit                          ║");
        System.out.println("╚══════════════════════════════════╝");
    }

    public void displayCompanyManagement() {
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║      COMPANY MANAGEMENT          ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.println("║ 1. Add Company                   ║");
        System.out.println("║ 2. Edit Company                  ║");
        System.out.println("║ 3. View Company                  ║");
        System.out.println("║ 4. Remove Company                ║");
        System.out.println("║ 5. Exit                          ║");
        System.out.println("╚══════════════════════════════════╝");
    }

    public void displayApplicantMenu() {
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║     APPLICANT MANAGEMENT         ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.println("║ 1. Add Applicant                 ║");
        System.out.println("║ 2. Remove Applicant              ║");
        System.out.println("║ 3. View All Applicants           ║");
        System.out.println("║ 4. Search Applicant by ID        ║");
        System.out.println("║ 5. Filter Applicants             ║");
        System.out.println("║ 6. Exit                          ║");
        System.out.println("╚══════════════════════════════════╝");
    }

    public void filterApplicantMenu() {
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║    FILTER APPLICANTS MENU        ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.println("║ 1. Filter by Age                 ║");
        System.out.println("║ 2. Filter by Location            ║");
        System.out.println("║ 3. Filter by Years of Experience ║");
        System.out.println("║ 4. Filter by Education Level     ║");
        System.out.println("║ 5. Filter by Skill               ║");
        System.out.println("║ 6. Exit                          ║");
        System.out.println("╚══════════════════════════════════╝");
    }

    public void displayInterviewMenu() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║       INTERVIEW MANAGEMENT         ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║ 1. Assign and View Interview Slots ║");
        System.out.println("║ 2. Recruitment Table               ║");
        System.out.println("║ 3. Interview Overall Report        ║");
        System.out.println("║ 4. Exit                            ║");
        System.out.println("╚════════════════════════════════════╝");
    }

    public void displayTimeSlotInterviewMenu(String company) {
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║   " + company.toUpperCase() + " - INTERVIEW TIME SLOT MANAGEMENT        ║");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.println("║ 1. Assign Interview                           ║");
        System.out.println("║ 2. View Waiting Interviews                    ║");
        System.out.println("║ 3. View Completed Interviews                  ║");
        System.out.println("║ 4. View Interviews Based on Skills            ║");
        System.out.println("║ 5. View Interviews Based on Experience        ║");
        System.out.println("║ 6. Suggest Time Slot to Middle Side           ║");
        System.out.println("║ 7. Give Interview Feedback                    ║");
        System.out.println("║ 8. View Time Slot Table                       ║");
        System.out.println("║ 9. Search                                     ║");
        System.out.println("║ 10. Back to Previous Menu                     ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
    }

    public void displayRecruitmentMenu() {
        System.out.println("\n╔═════════════════════════════════════╗");
        System.out.println("║         RECRUITMENT OPTIONS         ║");
        System.out.println("╠═════════════════════════════════════╣");
        System.out.println("║ 1. Filter Applicants by Rating      ║");
        System.out.println("║ 2. Interview Feedback               ║");
        System.out.println("║ 3. Accept or Reject Applicants      ║");
        System.out.println("║ 4. Back to Previous Menu            ║");
        System.out.println("╚═════════════════════════════════════╝");
    }

    public void printTimeSlotTableHeader() {
        System.out.println("\n╔══════════════╦═══════════════════════╦═══════════════════════╦═══════════════╗");
        System.out.println("║ Time Slot ID ║     Start Time        ║      End Time         ║ Availability  ║");
        System.out.println("╠══════════════╬═══════════════════════╬═══════════════════════╬═══════════════╣");
    }

    public void printTimeSlotRow(DoublyLinkedListInterface<TimeSlot> timeSlots) {
        for (TimeSlot timeSlot : timeSlots) {
            System.out.println(timeSlot);
        }
        System.out.println("╚══════════════╩═══════════════════════╩═══════════════════════╩═══════════════╝");
    }

    public void displayInterviewReport() {
        System.out.println("\n╔═════════════════════════════════════╗");
        System.out.println("║     INTERVIEW OVERALL REPORT        ║");
        System.out.println("╠═════════════════════════════════════╣");
        System.out.println("║ 1. View Accepted Interviews         ║");
        System.out.println("║ 2. View Rejected Interviews         ║");
        System.out.println("║ 3. Back to Previous Menu            ║");
        System.out.println("╚═════════════════════════════════════╝");
    }

    public void acceptOrRejectApplicantsMenu() {
        System.out.println("\n╔═════════════════════════════════════╗");
        System.out.println("║   ACCEPT OR REJECT APPLICANTS       ║");
        System.out.println("╠═════════════════════════════════════╣");
        System.out.println("║ 1. Accept Applicant                 ║");
        System.out.println("║ 2. Reject Applicant                 ║");
        System.out.println("║ 3. Back to Previous Menu            ║");
        System.out.println("╚═════════════════════════════════════╝");
    }

    public void displaySearchInterview() {
        System.out.println("\n╔═════════════════════════════════════╗");
        System.out.println("║       CONTINUE TO DIG?              ║");
        System.out.println("╠═════════════════════════════════════╣");
        System.out.println("║ 1. Applicant's Details              ║");
        System.out.println("║ 2. Job Post Details                 ║");
        System.out.println("║ 3. Exit                             ║");
        System.out.println("╚═════════════════════════════════════╝");
    }

    public void displaySearchInterviewApplicantDetails() {
        System.out.println("\n╔═════════════════════════════════════╗");
        System.out.println("║      SEARCH APPLICANT DETAILS       ║");
        System.out.println("╚═════════════════════════════════════╝");
    }

    public void displaySearchInterviewJobDetails() {
        System.out.println("\n╔═════════════════════════════════════╗");
        System.out.println("║         SEARCH JOB DETAILS          ║");
        System.out.println("╚═════════════════════════════════════╝");
    }

    public void printAcceptedInterviewReport(Company company, DoublyLinkedListInterface<Interview> acceptedInterview) {
        final int width = 100;
        final String separator = repeat("=", width);

        System.out.println(String.format("%" + (width + separator.length()) / 2 + "s", separator));
        System.out.println(String.format(
                "%" + (width + "TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY".length()) / 2 + "s",
                "TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY"));
        System.out.println(String.format("%" + (width + "ACCEPTED INTERVIEW REPORT".length()) / 2 + "s",
                "ACCEPTED INTERVIEW REPORT"));
        System.out.println(String.format("%" + (width + company.getCompanyName().length()) / 2 + "s",
                "Company: " + company.getCompanyName()));
        System.out.println(String.format("%" + (width + separator.length()) / 2 + "s", separator));
        System.out.println();

        boolean found = false;

        for (Interview interview : acceptedInterview) {
            if (interview.getApplicantAppliedJob().getJobPost().getCompany().equals(company)) {
                System.out.println(String.format("%-20s : %s", "Interview ID", interview.getInterviewId()));
                System.out.println(String.format("%-20s : %s", "Applicant ID",
                        interview.getApplicantAppliedJob().getApplicant().getApplicantId()));
                System.out.println(String.format("%-20s : %s", "Applicant Name",
                        interview.getApplicantAppliedJob().getApplicant().getName()));
                System.out.println(String.format("%-20s : %s", "Time Slot", interview.getTimeslot().getTime()));
                System.out.println(String.format("%-20s : %s", "Mode", interview.getMode()));
                System.out.println(String.format("%-20s : %s", "Status", interview.getStatus()));
                System.out.println(String.format("%-20s : %s", "Feedback", interview.getFeedback()));
                System.out.println(String.format("%-20s : %d", "Favour Rate", interview.getFavourRate()));

                System.out.println(repeat("-", width));
                System.out.println();
                found = true;
            }
        }

        if (!found) {
            System.out.println(
                    String.format("%" + (width + "No accepted interviews found for this company.".length()) / 2 + "s",
                            "❌ No accepted interviews found for this company."));
        }

        System.out.println(separator);
        System.out.println(String.format("%" + (width + "END OF REPORT".length()) / 2 + "s", "END OF REPORT"));
        System.out.println(separator);
    }

    public void printRejectedInterviewReport(Company company, DoublyLinkedListInterface<Interview> rejectedInterview) {
        final int width = 100;
        final String separator = repeat("=", width);

        System.out.println(String.format("%" + (width + separator.length()) / 2 + "s", separator));
        System.out.println(String.format(
                "%" + (width + "TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY".length()) / 2 + "s",
                "TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY"));
        System.out.println(String.format("%" + (width + "REJECTED INTERVIEW REPORT".length()) / 2 + "s",
                "REJECTED INTERVIEW REPORT"));
        System.out.println(String.format("%" + (width + company.getCompanyName().length()) / 2 + "s",
                "Company: " + company.getCompanyName()));
        System.out.println(String.format("%" + (width + separator.length()) / 2 + "s", separator));
        System.out.println();

        boolean found = false;

        for (Interview interview : rejectedInterview) {
            if (interview.getApplicantAppliedJob().getJobPost().getCompany().equals(company)) {
                System.out.println(String.format("%-20s : %s", "Interview ID", interview.getInterviewId()));
                System.out.println(String.format("%-20s : %s", "Applicant ID",
                        interview.getApplicantAppliedJob().getApplicant().getApplicantId()));
                System.out.println(String.format("%-20s : %s", "Applicant Name",
                        interview.getApplicantAppliedJob().getApplicant().getName()));
                System.out.println(String.format("%-20s : %s", "Time Slot", interview.getTimeslot().getTime()));
                System.out.println(String.format("%-20s : %s", "Mode", interview.getMode()));
                System.out.println(String.format("%-20s : %s", "Status", interview.getStatus()));
                System.out.println(String.format("%-20s : %s", "Feedback", interview.getFeedback()));
                System.out.println(String.format("%-20s : %d", "Favour Rate", interview.getFavourRate()));

                System.out.println(repeat("-", width));
                System.out.println();
                found = true;
            }
        }

        if (!found) {
            System.out.println(
                    String.format("%" + (width + "No rejected interviews found for this company.".length()) / 2 + "s",
                            "❌ No rejected interviews found for this company."));
        }

        System.out.println(separator);
        System.out.println(String.format("%" + (width + "END OF REPORT".length()) / 2 + "s", "END OF REPORT"));
        System.out.println(separator);
    }

    /**
     * Helper method to repeat a given string 's' for 'count' times.
     */
    public String repeat(String s, int count) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append(s);
        }
        return builder.toString();
    }

    public void displayJobMatchingMenu() {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║             MATCHING CATEGORIES              ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.println("║ 1. Proficiency Levels                        ║");
        System.out.println("║ 2. Experience Levels and Job Requirements    ║");
        System.out.println("║ 3. Location Preferences                      ║");
        System.out.println("║ 4. Summary Report                            ║");
        System.out.println("║ 5. Exit                                      ║");
        System.out.println("╚══════════════════════════════════════════════╝");
    }

    public void exitSystem() {
        System.out.println("\n╔═════════════════════════════════════╗");
        System.out.println("║          EXITING SYSTEM...          ║");
        System.out.println("╚═════════════════════════════════════╝");
    }

    // Print the applicant table header
    public void printApplicantTableHeader() {
        System.out.println(
                "╔══════════════╦════════════════════╦═══════╦═════════════════╦════════════╦═══════════════════════════╦════════════════════════════════╦══════════════════════╗");
        System.out.println(
                "║ Applicant ID ║ Name               ║ Age   ║ Location        ║ Exp (Yr)   ║ Education                 ║ Skills                         ║ Registration Date    ║");
        System.out.println(
                "╠══════════════╬════════════════════╬═══════╬═════════════════╬════════════╬═══════════════════════════╬════════════════════════════════╬══════════════════════╣");
    }

    // Print a single row for an applicant
    public void printApplicantRow(Applicant applicant) {
        String skills = formatSkills(applicant.getSkills());
        String registrationTime = applicant.getDateAdded(); // Get stored date/time

        System.out.printf("║ %-12s ║ %-20s ║ %-5d ║ %-15s ║ %-10d ║ %-25s ║ %-30s ║ %-20s ║\n",
                applicant.getApplicantId(), applicant.getName(), applicant.getAge(),
                applicant.getLocation(), applicant.getYearsOfExperience(), applicant.getEducationLevel(), skills,
                registrationTime);

        System.out.println(
                "╠══════════════╬════════════════════╬═══════╬═════════════════╬════════════╬═══════════════════════════╬════════════════════════════════╬══════════════════════╣");
    }

    // Print a table of applicants
    public void printApplicants(DoublyLinkedListInterface<Applicant> applicants) {
        if (applicants.isEmpty()) {
            System.out.println("No applicants found.");
            return;
        }

        printApplicantTableHeader();
        for (Applicant applicant : applicants) {
            printApplicantRow(applicant);
        }
        System.out.println();
    }

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

        // Truncate long skill lists to fit within 30 characters
        String formattedSkills = skillNames.toString();
        return formattedSkills.length() > 30 ? formattedSkills.substring(0, 27) + "..." : formattedSkills;
    }

    // Print skill table header
    public void printSkillTableHeader() {
        System.out.println("╔════════════════╦═════════════╦═════════════╗");
        System.out.println("║ Skill Name     ║ Category    ║ Proficiency ║");
        System.out.println("╠════════════════╬═════════════╬═════════════╣");
    }

    // Print a single skill row
    public void printSkillRow(Skill skill) {
        System.out.printf("| %-14s | %-12s | %-11d |\n",
                skill.getName(), skill.getCategory(), skill.getProficiency_level());
        System.out.println("+----------------+--------------+-------------+");
    }

    // Print a table of skills
    public void printSkills(DoublyLinkedListInterface<Skill> skills) {
        if (skills.isEmpty()) {
            System.out.println("No skills found.");
            return;
        }

        printSkillTableHeader();
        for (Skill skill : skills) {
            printSkillRow(skill);
        }
        System.out.println();
    }
}
