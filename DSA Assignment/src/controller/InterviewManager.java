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
    //     // Company not found
    //     if (company == null) {
    //         inputUI.displayMessage("Company not found!");
    //         return;
    //     }

    //     // Display Interview Menu
    //     menuUI.displayAppliedApplicantInterviewMenu(company.getCompanyName());

    //     // View all applicant applied to this company.
    //     viewCompanyAppliedApplicant(company);
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

        for (ApplicantAppliedJob aaj : appliedJobs) {
            if (aaj.getJobPost().getCompany().getCompanyName().equalsIgnoreCase(company.getCompanyName())) {
                System.out.println("Applicant " + i + ":");
                System.out.println(aaj.getApplicant());
                System.out.println("========================================");
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
            // Check if this time slot is already used by another interview for the same company
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
}
