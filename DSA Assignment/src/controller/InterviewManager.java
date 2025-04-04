package controller;

import adt.*;
import boundary.InputUI;
import boundary.MenuUI;
import entities.*;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InterviewManager {

    private static InterviewManager instance;

    // ADT
    private DoublyLinkedListInterface<Interview> interview; // Existing interview list (if needed)
    private DoublyLinkedListInterface<Interview> acceptedInterview; // Accepted interview list
    private DoublyLinkedListInterface<Interview> rejectedInterview; // Rejected interview list
    private DoublyLinkedListInterface<Interview> allCompletedInterview; // Waiting interview list

    // Controllers
    private static ApplicantAppliedJobManager applicantAppliedJobManager = ApplicantAppliedJobManager.getInstance();
    private static TimeSlotManager timeSlotManager = TimeSlotManager.getInstance();
    private static ApplicantManager applicantManager = ApplicantManager.getInstance();

    // Boundary
    private static InputUI inputUI = new InputUI();
    private static MenuUI menuUI = new MenuUI();

    // Instance --START
    private InterviewManager() {
        interview = new DoublyLinkedList<>();
        acceptedInterview = new DoublyLinkedList<>();
        rejectedInterview = new DoublyLinkedList<>();
        allCompletedInterview = new DoublyLinkedList<>();
    }

    public static InterviewManager getInstance() {
        if (instance == null) {
            instance = new InterviewManager();
        }
        return instance;
    }
    // Instance --END

    // Add Interview
    public void addInterview(Interview interview) {
        this.interview.add(interview);
    }

    // // // Display all the applicant applied to the company
    // public void displayAppliedApplicantInterviewMenu(Company company) {
    // // Company not found
    // if (company == null) {
    // inputUI.displayMessage("Company not found!");
    // return;
    // }
    // // Display Interview Menu
    // menuUI.displayAppliedApplicantInterviewMenu(company.getCompanyName());
    // // View all applicant applied to this company.
    // viewCompanyAppliedApplicant(company);
    // }
    // Assign and Manipulate Time Slot
    public void displayAssignInterviewTimeSlot(Company company) {
        // Company not found
        if (company == null) {
            inputUI.displayMessage("Company not found!");
            return;
        }

        // Display Interview Menu
        menuUI.displayTimeSlotInterviewMenu(company.getCompanyName());
        inputUI.handleInterviewTimeSlotMenu(company);
    }

    // This method finds and prints the applicants who applied to the specified
    // company
    public void viewCompanyAppliedApplicant(Company company) {
        DoublyLinkedListInterface<ApplicantAppliedJob> appliedJobs = applicantAppliedJobManager
                .getApplicantAppliedJobs();

        System.out.println("\n===== Applicants Applied to " + company.getCompanyName() + " =====");
        boolean found = false;
        int i = 1; // Move counter initialization outside the loop

        menuUI.printApplicantTableHeader();
        for (ApplicantAppliedJob aaj : appliedJobs) {
            if (aaj.getJobPost().getCompany().getCompanyName().equalsIgnoreCase(company.getCompanyName())) {
                menuUI.printApplicantRow(aaj.getApplicant());
                found = true;
                i++; // Increment counter for next applicant
            }
        }

        if (!found) {
            System.out.println("No applicants found for " + company.getCompanyName() + ".");
        }
    }

    public void viewWaitingInterviews(Company company) {
        System.out.println("\n===== Waiting Interviews =====");
        boolean found = false;
        for (Interview i : interview) {
            if (i.getStatus().equalsIgnoreCase("Waiting") && i.getApplicantAppliedJob().getJobPost().getCompany()
                    .getCompanyName().equalsIgnoreCase(company.getCompanyName())) {
                System.out.println(i);
                System.out.println("========================================");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No waiting interviews found.");
        }
    }

    // View all completed interviews
    public void viewCompletedInterviews(Company company) {
        System.out.println("\n===== Completed Interviews =====");
        boolean found = false;
        for (Interview i : interview) {
            if (i.getStatus().equalsIgnoreCase("Completed") && i.getApplicantAppliedJob().getJobPost().getCompany()
                    .getCompanyName().equalsIgnoreCase(company.getCompanyName())) {
                System.out.println(i);
                System.out.println("========================================");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No completed interviews found.");
        }
    }

    // Filter Applicant based on skill
    public void filterInterviewsSkill(Company company) {
        DoublyLinkedListInterface<Applicant> applicants = new DoublyLinkedList<>();
        DoublyLinkedListInterface<ApplicantAppliedJob> appliedJobs = applicantAppliedJobManager
                .getApplicantAppliedJobs();
        System.out.println("\n===== Filter Interviews Based On Skills =====");
        boolean found = false;
        String skillNames = inputUI.getInput("Enter skills (comma separated): ");
        String[] skillArray = skillNames.split(",");

        for (ApplicantAppliedJob aaj : appliedJobs) {
            if (aaj.getJobPost().getCompany()
                    .getCompanyName().equalsIgnoreCase(company.getCompanyName())) {
                boolean applicantAdded = false;
                for (Skill skill : aaj.getApplicant().getSkills()) {
                    for (String skillName : skillArray) {
                        if (skill.getName().equalsIgnoreCase(skillName.trim())) {
                            if (!found) {
                                inputUI.displayMessage("\nApplicants with the skills '" + skillNames + "':");
                            }
                            if (!applicantAdded) {
                                applicants.add(aaj.getApplicant());
                                applicantAdded = true;
                            }
                            found = true;
                            break;
                        }
                    }
                    if (applicantAdded) {
                        break;
                    }
                }
            }
        }
        menuUI.printApplicants(applicants);
        if (!found) {
            System.out.println("No applicants found with the specified skills.");
        }
    }

    // Filter applicant based on years of experience
    public void filterApplicantsByExperience(Company company) {
        DoublyLinkedListInterface<Applicant> applicants = new DoublyLinkedList<>();
        DoublyLinkedListInterface<ApplicantAppliedJob> appliedJobs = applicantAppliedJobManager
                .getApplicantAppliedJobs();
        System.out.println("\n===== Filter Applicants Based On Years of Experience =====");
        boolean found = false;
        int minYears = inputUI.getIntInput("Enter minimum years of experience: ", 0, 50);

        for (ApplicantAppliedJob aaj : appliedJobs) {
            if (aaj.getJobPost().getCompany().getCompanyName().equalsIgnoreCase(company.getCompanyName())) {
                if (aaj.getApplicant().getYearsOfExperience() >= minYears) {
                    if (!found) {
                        inputUI.displayMessage("\nApplicants with at least " + minYears + " years of experience:");
                    }
                    applicants.add(aaj.getApplicant());
                    found = true;
                }
            }
        }
        menuUI.printApplicants(applicants);
        if (!found) {
            System.out.println("No applicants found with the specified years of experience.");
        }
    }

    // Suggest time slot to middle man
    public void suggestTimeSlotToMiddleMan() {

        DoublyLinkedListInterface<TimeSlot> suggestedTimeSlots = timeSlotManager.getSuggestedTimeSlots();
        System.out.println("\n===== Suggest Time Slot To Middle Man =====");

        String timeSuggested = inputUI.getInput("Enter time suggested (HH:mm(am/pm)): ");
        String dateSuggested = inputUI.getInput("Enter date suggested (DD/MM/YYY5): ");
        String locationSuggested = inputUI.getInput("Enter location suggested: ");

        TimeSlot timeSlot = new TimeSlot(timeSuggested, dateSuggested, locationSuggested);
        suggestedTimeSlots.add(timeSlot);

        inputUI.displayMessage("Time slot suggested successfully!");
    }

    // Middle man accept time slot
    public void acceptTimeSlotMiddleMan() {
        DoublyLinkedListInterface<TimeSlot> suggestedTimeSlots = timeSlotManager.getSuggestedTimeSlots();
        System.out.println("\n===== Accept Time Slot From Middle Man =====");

        if (suggestedTimeSlots.isEmpty()) {
            inputUI.displayMessage("No suggested time slots available.");
            return;
        }

        // Display suggested time slots
        int slotNumber = 1;
        for (TimeSlot timeSlot : suggestedTimeSlots) {
            System.out.println(slotNumber + ". " + timeSlot);
            slotNumber++;
        }

        // Let user select a time slot
        int selectedSlot = inputUI.getIntInput("Select a time slot number to accept: ", 1, suggestedTimeSlots.size());
        TimeSlot acceptedTimeSlot = suggestedTimeSlots.get(selectedSlot - 1);

        // Remove the accepted time slot from the suggested list
        suggestedTimeSlots.removeSpecific(acceptedTimeSlot);

        // Add the accepted time slot to the main time slot list
        timeSlotManager.getTimeSlots().add(acceptedTimeSlot);

        inputUI.displayMessage("Time slot accepted successfully!");
    }

    // Handle interview feedback
    public void handleInterviewFeedback(Company company) {
        inputUI.displayMessage("\n===== Interview Feedback =====");
        String applicantId = inputUI.getInput("Enter Applicant ID: ");

        // Find the interview for this applicant
        Interview targetInterview = null;
        for (Interview i : interview) {
            if (i.getApplicantAppliedJob().getApplicant().getApplicantId().equals(applicantId)
                    && i.getApplicantAppliedJob().getJobPost().getCompany().getCompanyName()
                            .equalsIgnoreCase(company.getCompanyName())) {
                targetInterview = i;
                break;
            }
        }

        if (targetInterview == null) {
            inputUI.displayMessage("No interview found for this applicant.");
            return;
        }

        String feedback = inputUI.getInput("Enter feedback: ");
        int rating = inputUI.getIntInput("Enter rating (1-5): ", 1, 5);

        targetInterview.setFeedback(feedback);
        targetInterview.setFavourRate(rating);
        targetInterview.setStatus("Completed");

        inputUI.displayMessage("Feedback recorded successfully!");
    }

    // Add interview slot for an applicant
    public void addInterviewSlot(Company company) {
        viewCompanyAppliedApplicant(company);
        inputUI.displayMessage("\n===== Add Interview Slot =====");
        String applicantId = inputUI.getInput("Enter Applicant ID: ");

        // Find the matching ApplicantAppliedJob
        ApplicantAppliedJob matchingApplication = null;
        for (ApplicantAppliedJob aaj : applicantAppliedJobManager.getApplicantAppliedJobs()) {
            if (aaj.getApplicant().getApplicantId().equals(applicantId)
                    && aaj.getJobPost().getCompany().getCompanyName().equalsIgnoreCase(company.getCompanyName())) {
                matchingApplication = aaj;
                break;
            }
        }

        if (matchingApplication == null) {
            inputUI.displayMessage("No job application found for this applicant in this company.");
            return;
        }

        // Check if interview already exists for this application
        for (Interview i : interview) {
            if (i.getApplicantAppliedJob().equals(matchingApplication)) {
                inputUI.displayMessage("An interview already exists for this application.");
                return;
            }
        }

        // Retrieve time slot details
        DoublyLinkedListInterface<TimeSlot> timeSlots = timeSlotManager.getTimeSlots();
        if (timeSlots.isEmpty()) {
            inputUI.displayMessage("No time slots available.");
            return;
        }

        // Display available time slots
        System.out.println("\n===== Available Time Slots =====");
        int slotNumber = 1;
        DoublyLinkedListInterface<TimeSlot> availableTimeSlots = new DoublyLinkedList<>();

        for (TimeSlot timeSlot : timeSlots) {
            boolean isOccupied = false;
            // Check if this time slot is already used by another interview for the same
            // company
            for (Interview i : interview) {
                if (i.getApplicantAppliedJob().getJobPost().getCompany().getCompanyName()
                        .equalsIgnoreCase(company.getCompanyName())
                        && i.getTimeslot().getTime().equals(timeSlot.getTime())
                        && i.getTimeslot().getDate().equals(timeSlot.getDate())) {
                    isOccupied = true;
                    break;
                }
            }

            if (!isOccupied) {
                System.out.println(slotNumber + ". " + timeSlot);
                availableTimeSlots.add(timeSlot);
                slotNumber++;
            }
        }

        if (availableTimeSlots.isEmpty()) {
            inputUI.displayMessage("No available time slots for this company.");
            return;
        }

        // Let user select a time slot
        int selectedSlot = inputUI.getIntInput("Select a time slot number: ", 1, availableTimeSlots.size());
        TimeSlot selectedTimeSlot = availableTimeSlots.get(selectedSlot - 1);

        String platform = inputUI.getInput("Enter interview platform (e.g., Zoom, Google Meet): ");

        // Create new interview
        Interview newInterview = new Interview(matchingApplication, selectedTimeSlot, platform, "Waiting", "", 0);
        interview.add(newInterview);

        inputUI.displayMessage("Interview created successfully!");
    }

    // Filter Applicant by Interview Rating
    public void filterApplicantBasedOnInterview(Company company) {
        DoublyLinkedListInterface<Applicant> applicants = new DoublyLinkedList<>();
        DoublyLinkedListInterface<ApplicantAppliedJob> appliedJobs = applicantAppliedJobManager
                .getApplicantAppliedJobs();
        System.out.println("\n===== Filter Applicants Based On Interview Rating =====");
        boolean found = false;
        int minRating = inputUI.getIntInput("Enter minimum rating: ", 0, 5);

        for (ApplicantAppliedJob aaj : appliedJobs) {
            if (aaj.getJobPost().getCompany().getCompanyName().equalsIgnoreCase(company.getCompanyName())) {
                for (Interview i : interview) {
                    if (i.getApplicantAppliedJob().getApplicant().getApplicantId()
                            .equals(aaj.getApplicant().getApplicantId())
                            && i.getFavourRate() >= minRating) {
                        if (!found) {
                            inputUI.displayMessage("\nApplicants with at least " + minRating + " rating:");
                        }
                        applicants.add(aaj.getApplicant());
                        found = true;
                        break;
                    }
                }
            }
        }
        menuUI.printApplicants(applicants);
        if (!found) {
            System.out.println("No applicants found with the specified rating.");
        }
    }

    // View Time SLot Table
    public void viewTimeSlotTable(Company company) {
        DoublyLinkedListInterface<TimeSlot> timeSlots = timeSlotManager.getTimeSlots();
        System.out.println("\n===== Time Slot Table =====");
        if (timeSlots.isEmpty()) {
            System.out.println("No time slots available.");
            return;
        }

        DoublyLinkedListInterface<TimeSlot> availableTimeSlots = new DoublyLinkedList<>();
        int slotNumber = 1;
        for (TimeSlot timeSlot : timeSlots) {
            boolean isOccupied = false;
            // Check if this time slot is already used by another interview for the same
            // company
            for (Interview i : interview) {
                if (i.getApplicantAppliedJob().getJobPost().getCompany().getCompanyName()
                        .equalsIgnoreCase(company.getCompanyName())
                        && i.getTimeslot().getTime().equals(timeSlot.getTime())
                        && i.getTimeslot().getDate().equals(timeSlot.getDate())) {
                    isOccupied = true;
                    break;
                }
            }

            if (!isOccupied) {
                availableTimeSlots.add(timeSlot);
                slotNumber++;
            }
        }

        menuUI.printTimeSlotTableHeader();
        menuUI.printTimeSlotRow(availableTimeSlots);
        System.out.println("+----------------+---------------------+---------------------+----------------+");

    }

    // Interview Feedback of Completed Interviews
    public void viewInterviewFeedback(Company company) {
        System.out.println("\n===== Interview Feedback =====");
        boolean found = false;
        for (Interview i : interview) {
            if (i.getStatus().equalsIgnoreCase("Completed") && i.getApplicantAppliedJob().getJobPost().getCompany()
                    .getCompanyName().equalsIgnoreCase(company.getCompanyName())) {
                System.out
                        .println(i.getApplicantAppliedJob().getApplicant().getApplicantId() + " - " + i.getFeedback());
                System.out.println("========================================");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No interview feedback found.");
        }
    }

    // Accept or Reject Applicant
    public void acceptRejectInterviewApplicant(Company company) {
        viewCompletedInterviews(company);
        inputUI.displayMessage("\n===== Accept/Reject Interview Applicant =====");
        String applicantId = inputUI.getInput("Enter Applicant ID: ");

        // Find the interview for this applicant
        Interview targetInterview = null;
        for (Interview i : interview) {
            if (i.getApplicantAppliedJob().getApplicant().getApplicantId().equals(applicantId)
                    && i.getApplicantAppliedJob().getJobPost().getCompany().getCompanyName()
                            .equalsIgnoreCase(company.getCompanyName())) {
                targetInterview = i;
                break;
            }
        }

        if (targetInterview == null) {
            inputUI.displayMessage("No interview found for this applicant.");
            return;
        }

        String decision = inputUI.getInput("Enter decision (Accept/Reject): ");
        if (decision.equalsIgnoreCase("Accept")) {
            targetInterview.setStatus("Accepted");
            inputUI.displayMessage("Applicant accepted successfully!");

            acceptedInterview.add(targetInterview);
            allCompletedInterview.add(targetInterview);
            interview.removeSpecific(targetInterview);
            applicantAppliedJobManager.removeApplicantAppliedJob(targetInterview.getApplicantAppliedJob());

        } else if (decision.equalsIgnoreCase("Reject")) {
            targetInterview.setStatus("Rejected");
            inputUI.displayMessage("Applicant rejected successfully!");

            rejectedInterview.add(targetInterview);
            allCompletedInterview.add(targetInterview);
            interview.removeSpecific(targetInterview);
            applicantAppliedJobManager.removeApplicantAppliedJob(targetInterview.getApplicantAppliedJob());
        } else {
            inputUI.displayMessage("Invalid decision. Please enter 'Accept' or 'Reject'.");
        }
    }

    public void viewAcceptedInterview(Company company) {
        menuUI.printAcceptedInterviewReport(company, acceptedInterview);
    }

    public void viewRejectedInterviews(Company company) {
        menuUI.printRejectedInterviewReport(company, rejectedInterview);
    }

    public void searchInterview(Company company) {
        System.out.println("\n===== Search Interview =====");

        String inputApplicantId = inputUI.getInput("Enter Applicant ID: ");
        if (inputApplicantId == null || inputApplicantId.trim().isEmpty()) {
            inputUI.displayMessage("Applicant ID cannot be empty.");
            return;
        }

        int maxAllowedDistance = 1; 

        List<Interview> matchedInterviews = new ArrayList<>();
        for (Interview interviewObj : interview) {
            if (interviewObj != null
                    && interviewObj.getApplicantAppliedJob() != null
                    && interviewObj.getApplicantAppliedJob().getApplicant() != null
                    && interviewObj.getApplicantAppliedJob().getJobPost() != null
                    && interviewObj.getApplicantAppliedJob().getJobPost().getCompany() != null) {

                String storedApplicantId = interviewObj.getApplicantAppliedJob().getApplicant().getApplicantId();
                String storedCompanyName = interviewObj.getApplicantAppliedJob().getJobPost().getCompany()
                        .getCompanyName();

                int distance = calculateLevenshteinDistance(inputApplicantId, storedApplicantId);
                boolean applicantMatches = distance <= maxAllowedDistance;
                boolean companyMatches = company.getCompanyName().equalsIgnoreCase(storedCompanyName);

                if (applicantMatches && companyMatches) {
                    matchedInterviews.add(interviewObj);
                }
            }
        }

        if (matchedInterviews.isEmpty()) {
            System.out.println("No interview found for this applicant.");
            return;
        }

        // Let the user confirm the best match
        for (Interview foundInterview : matchedInterviews) {
            System.out.println("\nPossible Match Found:");
            System.out.println(
                    "Applicant ID: " + foundInterview.getApplicantAppliedJob().getApplicant().getApplicantId());
            System.out.println(
                    "Company: " + foundInterview.getApplicantAppliedJob().getJobPost().getCompany().getCompanyName());
            System.out.println("========================================");

            // Ask user if this is the correct match
            String confirmation = inputUI.getInput("Is this the correct applicant? (yes/no): ").trim().toLowerCase();
            if (confirmation.equals("yes")) {
                inputUI.displayMessage("Proceeding with selected applicant.");
                inputUI.handleSearchInterview(foundInterview.getApplicantAppliedJob());
                return; // Stop after finding a confirmed match
            }
        }

        System.out.println("No confirmed matches found.");
    }


    /*
        OLD SEARCH INTERVIEW METHOD
        public void searchInterview(Company company) {
        System.out.println("\n===== Search Interview =====");

        // Validate input for applicant ID
        String applicantId = inputUI.getInput("Enter Applicant ID: ");
        if (applicantId == null || applicantId.trim().isEmpty()) {
            inputUI.displayMessage("Applicant ID cannot be empty.");
            return;
        }

        // Collect interviews that match both the applicant ID and the company name
        List<Interview> matchedInterviews = new ArrayList<>();
        for (Interview interviewObj : interview) {
            // Ensure that the chain of objects is not null to prevent NullPointerException
            if (interviewObj != null &&
                    interviewObj.getApplicantAppliedJob() != null &&
                    interviewObj.getApplicantAppliedJob().getApplicant() != null &&
                    interviewObj.getApplicantAppliedJob().getJobPost() != null &&
                    interviewObj.getApplicantAppliedJob().getJobPost().getCompany() != null) {

                boolean applicantMatches = applicantId.equals(
                        interviewObj.getApplicantAppliedJob().getApplicant().getApplicantId());
                boolean companyMatches = company.getCompanyName().equalsIgnoreCase(
                        interviewObj.getApplicantAppliedJob().getJobPost().getCompany().getCompanyName());
                if (applicantMatches && companyMatches) {
                    matchedInterviews.add(interviewObj);
                }
            }
        }

        if (matchedInterviews.isEmpty()) {
            System.out.println("No interview found for this applicant.");
            return;
        }

        // Display all matched interviews
        for (Interview foundInterview : matchedInterviews) {
            System.out.println(foundInterview);
            System.out.println("========================================");
        }

        if (!matchedInterviews.isEmpty()) {
            Interview interviewToProcess = matchedInterviews.get(0);
            inputUI.displayMessage("Do you wish to further dig into their information?");
            inputUI.handleSearchInterview(interviewToProcess.getApplicantAppliedJob());
        } else {
            System.out.println("No interview found for this applicant.");
        }
     */




    public void searchInterviewApplicantDetails(ApplicantAppliedJob applicantAppliedJob) {
        if (applicantAppliedJob == null) {
            System.out.println("Applicant details are not available.");
            return;
        }
        menuUI.displaySearchInterviewApplicantDetails();
        menuUI.printApplicantTableHeader();
        if (applicantAppliedJob.getApplicant() != null) {
            menuUI.printApplicantRow(applicantAppliedJob.getApplicant());
        } else {
            System.out.println("No Available Applicant.");
        }
    }

    public void searchInterviewJobDetails(ApplicantAppliedJob applicantAppliedJob) {
        if (applicantAppliedJob == null) {
            System.out.println("Job details are not available.");
            return;
        }
        menuUI.displaySearchInterviewJobDetails();
        if (applicantAppliedJob.getJobPost() != null) {
            System.out.println(applicantAppliedJob.getJobPost().getJob());
        } else {
            System.out.println("No Available Job.");
        }
    }

    public DoublyLinkedListInterface<Interview> getInterviews() {
        return interview;
    }

    /**
     * Calculates the Levenshtein distance between two strings.
     */
    private int calculateLevenshteinDistance(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            for (int j = 0; j <= len2; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                    dp[i][j] = Math.min(Math.min(
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1),
                            dp[i - 1][j - 1] + cost 
                    );
                }
            }
        }
        return dp[len1][len2];
    }

}
