package controller;

import adt.*;
import boundary.InputUI;
import boundary.MenuUI;
import entities.*;
import java.util.Scanner;

public class InterviewManager {

    private static InterviewManager instance;

    // ADT
    private DoublyLinkedListInterface<Interview> interview; // Existing interview list (if needed)

    // Controllers
    private static ApplicantAppliedJobManager applicantAppliedJobManager = ApplicantAppliedJobManager.getInstance();
    private static TimeSlotManager timeSlotManager = TimeSlotManager.getInstance();

    // Boundary
    private static InputUI inputUI = new InputUI();
    private static MenuUI menuUI = new MenuUI();

    // Instance --START
    private InterviewManager() {
        interview = new DoublyLinkedList<>();
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
            if (aaj.getApplicant().getApplicantId().equals(applicantId) &&
                    aaj.getJobPost().getCompany().getCompanyName().equalsIgnoreCase(company.getCompanyName())) {
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
                        .equalsIgnoreCase(company.getCompanyName()) &&
                        i.getTimeslot().getTime().equals(timeSlot.getTime()) &&
                        i.getTimeslot().getDate().equals(timeSlot.getDate())) {
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
    public void viewTimeSlotTable() {
        DoublyLinkedListInterface<TimeSlot> timeSlots = timeSlotManager.getTimeSlots();
        System.out.println("\n===== Time Slot Table =====");
        if (timeSlots.isEmpty()) {
            System.out.println("No time slots available.");
            return;
        }
        menuUI.printTimeSlotTableHeader();
        menuUI.printTimeSlotRow(timeSlots);
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
            applicantAppliedJobManager.removeApplicantAppliedJob(targetInterview.getApplicantAppliedJob());
        } else if (decision.equalsIgnoreCase("Reject")) {
            targetInterview.setStatus("Rejected");
            inputUI.displayMessage("Applicant rejected successfully!");
            applicantAppliedJobManager.removeApplicantAppliedJob(targetInterview.getApplicantAppliedJob());
        } else {
            inputUI.displayMessage("Invalid decision. Please enter 'Accept' or 'Reject'.");
        }
    }
}