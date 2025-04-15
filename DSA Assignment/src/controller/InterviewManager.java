package controller;

import adt.*;
import boundary.InputUI;
import boundary.MenuUI;
import entities.*;

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
    public boolean viewCompanyAppliedApplicant(Company company) {
        DoublyLinkedListInterface<ApplicantAppliedJob> appliedJobs = applicantAppliedJobManager
                .getApplicantAppliedJobs();

        System.out.println("\n===== Applicants Applied to " + company.getCompanyName() + " =====");
        boolean found = false;

        menuUI.printApplicantTableHeaderAAJ();
        for (ApplicantAppliedJob aaj : appliedJobs) {
            // Skip applicants not applied to this company
            if (!aaj.getJobPost().getCompany().getCompanyName().equalsIgnoreCase(company.getCompanyName())) {
                continue;
            }

            // Check if this applicant has already been assigned an interview
            boolean hasInterview = false;
            for (Interview interviews : interview) {
                if (interviews.getApplicantAppliedJob().getApplicationId().equals(aaj.getApplicationId())) {
                    hasInterview = true;
                    break;
                }
            }

            // Only print if no interview is assigned
            if (!hasInterview) {
                menuUI.printApplicantRow(aaj);
                found = true;
            }
        }

        menuUI.printApplicantsTableFooterAAJ();

        if (!found) {
            System.out.println("No unassigned applicants found for " + company.getCompanyName() + ".");
            found = false;
        }

        return found;
    }

    public void viewWaitingInterviews(Company company) {
        System.out.println("\n=============== Waiting Interviews ===============");
        boolean found = false;
        for (Interview i : interview) {
            if (i.getStatus().equalsIgnoreCase("Waiting") && i.getApplicantAppliedJob().getJobPost().getCompany()
                    .getCompanyName().equalsIgnoreCase(company.getCompanyName())) {
                System.out.println(i);
                System.out.println("==================================================");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No waiting interviews found.");
            inputUI.getInput("Press Enter to continue...");
        }
    }

    // View all completed interviews
    public void viewCompletedInterviews(Company company) {
        System.out.println("\n============== Completed Interviews ===============");
        boolean found = false;
        for (Interview i : interview) {
            if (i.getStatus().equalsIgnoreCase("Completed") && i.getApplicantAppliedJob().getJobPost().getCompany()
                    .getCompanyName().equalsIgnoreCase(company.getCompanyName())) {
                System.out.println(i);
                System.out.println("==================================================");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No completed interviews found.");
            inputUI.getInput("Press Enter to continue...");
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

        String timeSuggested, dateSuggested;

        while (true) {
            timeSuggested = inputUI.getInput("Enter time suggested (e.g., 5.00pm): ");
            if (timeSlotManager.isValidTimeFormat(timeSuggested)) {
                break;
            }
            System.out.println("Invalid time format! Please use format like 5.00pm or 10.30am.");
        }

        while (true) {
            dateSuggested = inputUI.getInput("Enter date suggested (e.g., 6/3/2025): ");
            if (timeSlotManager.isValidDateFormat(dateSuggested)) {
                break;
            }
            System.out.println("Invalid date format! Please use format like 6/3/2025.");
        }

        String locationSuggested = inputUI.getInput("Enter location suggested: ");
        TimeSlot timeSlot = new TimeSlot(timeSuggested, dateSuggested, locationSuggested);
        suggestedTimeSlots.add(timeSlot);

        inputUI.displayMessage("Time slot suggested successfully!");
        inputUI.getInput("Press Enter to continue...");
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
        viewWaitingInterviews(company);
        String applicationID = inputUI.getInput("Enter Applicantion ID: ");

        // Find the interview for this applicant
        Interview targetInterview = null;
        for (Interview i : interview) {
            if (i.getApplicantAppliedJob().getApplicationId().equals(applicationID)
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
        inputUI.getInput("Press Enter to continue...");
    }

    // Add interview slot for an applicant
    public void addInterviewSlot(Company company) {
        boolean determinator = viewCompanyAppliedApplicant(company);
        if (determinator == false) {
            inputUI.displayMessage("No applicants available for this company.");
            return;
        }

        inputUI.displayMessage("\n===== Add Interview Slot =====");

        String applicantAppliedJobID = inputUI.getInput("Enter Application ID: ");

        // Find the matching ApplicantAppliedJob
        ApplicantAppliedJob matchingApplication = null;
        for (ApplicantAppliedJob aaj : applicantAppliedJobManager.getApplicantAppliedJobs()) {
            if (aaj.getApplicationId().equals(applicantAppliedJobID)
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

        // Filter available time slots
        DoublyLinkedListInterface<TimeSlot> availableTimeSlots = new DoublyLinkedList<>();
        for (TimeSlot timeSlot : timeSlots) {
            boolean isOccupied = false;

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
            }
        }

        if (availableTimeSlots.isEmpty()) {
            inputUI.displayMessage("No available time slots for this company.");
            return;
        }

        // Display using formatted table
        System.out.println("\n===== Available Time Slots =====");
        menuUI.printNumberedTimeSlotTable(availableTimeSlots);

        // Let user select a time slot
        int selectedSlot = inputUI.getIntInput("Select a time slot number: ", 1, availableTimeSlots.size());
        TimeSlot selectedTimeSlot = availableTimeSlots.get(selectedSlot - 1);

        String platform = inputUI.getInput("Enter interview platform (e.g., Zoom, Google Meet): ");

        // Create new interview
        Interview newInterview = new Interview(matchingApplication, selectedTimeSlot, platform, "Waiting", "", 0);
        interview.add(newInterview);

        inputUI.displayMessage("Interview created successfully!");
        inputUI.getInput("Press Enter to continue...");
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
        timeSlotManager.sortTheTimeTable();
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
        inputUI.getInput("Press Enter to continue...");
    }

    public void rescheduleInterviewSlot(Company company) {
        System.out.println("\n===== Reschedule Interview =====");

        DoublyLinkedListInterface<Interview> reschedulable = new DoublyLinkedList<>();

        // Collect matching interviews
        for (Interview i : interview) {
            if ((i.getStatus().equalsIgnoreCase("Waiting") || i.getStatus().equalsIgnoreCase("Accepted"))
                    && i.getApplicantAppliedJob().getJobPost().getCompany().equals(company)) {
                reschedulable.add(i);
            }
        }

        if (reschedulable.isEmpty()) {
            System.out.println("No interviews available for rescheduling.");
            return;
        }

        // Use menuUI to print nicely formatted table
        menuUI.printReschedulableInterviewTable(reschedulable, company);

        // Choose interview
        int choice = inputUI.getIntInput("Select an interview to reschedule: ", 1, reschedulable.size());
        Interview selectedInterview = reschedulable.get(choice - 1);

        // Show available time slots
        DoublyLinkedListInterface<TimeSlot> availableTimeSlots = new DoublyLinkedList<>();
        for (TimeSlot slot : timeSlotManager.getTimeSlots()) {
            boolean isOccupied = false;
            for (Interview i : interview) {
                if (i.getTimeslot().getTime().equals(slot.getTime())
                        && i.getTimeslot().getDate().equals(slot.getDate())
                        && i.getApplicantAppliedJob().getJobPost().getCompany().equals(company)) {
                    isOccupied = true;
                    break;
                }
            }
            if (!isOccupied) {
                availableTimeSlots.add(slot);
            }
        }

        if (availableTimeSlots.isEmpty()) {
            System.out.println("No available time slots.");
            inputUI.getInput("Press Enter to continue...");
            return;
        }

        System.out.println("\nAvailable Time Slots:");
        // menuUI.printTimeSlotTableHeader();
        // menuUI.printTimeSlotRow(availableTimeSlots);
        menuUI.printNumberedTimeSlotTable(availableTimeSlots);

        int newSlotChoice = inputUI.getIntInput("Select a new time slot: ", 1, availableTimeSlots.size());
        TimeSlot newSlot = availableTimeSlots.get(newSlotChoice - 1);

        selectedInterview.setTimeslot(newSlot);

        System.out.println("\nInterview successfully rescheduled!");
        System.out.printf("New Time Slot: %s | %s @ %s%n",
                newSlot.getTime(), newSlot.getDate(), newSlot.getLocation());
        inputUI.getInput("Press Enter to continue...");
    }

    public void rescheduleInterviewSlot(Company company) {
        System.out.println("\n===== Reschedule Interview =====");

        DoublyLinkedListInterface<Interview> reschedulable = new DoublyLinkedList<>();

        // Collect matching interviews
        for (Interview i : interview) {
            if ((i.getStatus().equalsIgnoreCase("Waiting") || i.getStatus().equalsIgnoreCase("Accepted"))
                    && i.getApplicantAppliedJob().getJobPost().getCompany().equals(company)) {
                reschedulable.add(i);
            }
        }

        if (reschedulable.isEmpty()) {
            System.out.println("No interviews available for rescheduling.");
            return;
        }

        // Use menuUI to print nicely formatted table
        menuUI.printReschedulableInterviewTable(reschedulable, company);

        // Choose interview
        int choice = inputUI.getIntInput("Select an interview to reschedule: ", 1, reschedulable.size());
        Interview selectedInterview = reschedulable.get(choice - 1);

        // Show available time slots
        DoublyLinkedListInterface<TimeSlot> availableTimeSlots = new DoublyLinkedList<>();
        for (TimeSlot slot : timeSlotManager.getTimeSlots()) {
            boolean isOccupied = false;
            for (Interview i : interview) {
                if (i.getTimeslot().getTime().equals(slot.getTime())
                        && i.getTimeslot().getDate().equals(slot.getDate())
                        && i.getApplicantAppliedJob().getJobPost().getCompany().equals(company)) {
                    isOccupied = true;
                    break;
                }
            }
            if (!isOccupied) {
                availableTimeSlots.add(slot);
            }
        }

        if (availableTimeSlots.isEmpty()) {
            System.out.println("No available time slots.");
            return;
        }

        System.out.println("\nAvailable Time Slots:");
        // menuUI.printTimeSlotTableHeader();
        // menuUI.printTimeSlotRow(availableTimeSlots);
        menuUI.printNumberedTimeSlotTable(availableTimeSlots);

        int newSlotChoice = inputUI.getIntInput("Select a new time slot: ", 1, availableTimeSlots.size());
        TimeSlot newSlot = availableTimeSlots.get(newSlotChoice - 1);

        selectedInterview.setTimeslot(newSlot);

        System.out.println("\nInterview successfully rescheduled!");
        System.out.printf("New Time Slot: %s | %s @ %s%n",
                newSlot.getTime(), newSlot.getDate(), newSlot.getLocation());
    }

    // Interview Feedback of Completed Interviews
    public void viewInterviewFeedback(Company company) {
        System.out.println("\n===== Interview Feedback =====");
        boolean found = false;
        for (Interview i : interview) {
            if (i.getStatus().equalsIgnoreCase("Completed") && i.getApplicantAppliedJob().getJobPost().getCompany()
                    .getCompanyName().equalsIgnoreCase(company.getCompanyName())) {
                System.out
                        .println(i.getApplicantAppliedJob().getApplicationId() + " - " + i.getFeedback());
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
        String applicantionId = inputUI.getInput("Enter Applicantion ID: ");

        // Find the interview for this applicant
        Interview targetInterview = null;
        for (Interview i : interview) {
            if (i.getApplicantAppliedJob().getApplicationId().equals(applicantionId)
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

    public void viewInterview(Company company) {
        menuUI.printInterviewReport(company, acceptedInterview, rejectedInterview);
    }

    public void searchInterview(Company company) {
        System.out.println("\n===== Search Interview =====");

        String inputApplicantId = inputUI.getInput("Enter Applicant ID: ");
        if (inputApplicantId == null || inputApplicantId.trim().isEmpty()) {
            inputUI.displayMessage("Applicant ID cannot be empty.");
            return;
        }

        int maxAllowedDistance = 1;

        DoublyLinkedListInterface<Interview> matchedInterviews = new DoublyLinkedList<>();

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

        for (Interview foundInterview : matchedInterviews) {
            System.out.println("\nPossible Match Found:");
            System.out.println(
                    "Applicant ID: " + foundInterview.getApplicantAppliedJob().getApplicant().getApplicantId());
            System.out.println(
                    "Company: " + foundInterview.getApplicantAppliedJob().getJobPost().getCompany().getCompanyName());
            System.out.println("========================================");

            String confirmation = inputUI.getInput("Is this the correct applicant? (yes/no): ").trim().toLowerCase();
            if (confirmation.equals("yes")) {
                inputUI.displayMessage("Proceeding with selected applicant.");
                inputUI.handleSearchInterview(foundInterview.getApplicantAppliedJob());
                return;
            }
        }

        System.out.println("No confirmed matches found.");
    }

    public void searchInterviewApplicantDetails(ApplicantAppliedJob applicantAppliedJob) {
        if (applicantAppliedJob == null) {
            System.out.println("Applicant details are not available.");
            return;
        }
        menuUI.displaySearchInterviewApplicantDetails();
        menuUI.printApplicantTableHeaderAAJ();
        if (applicantAppliedJob.getApplicant() != null) {
            menuUI.printApplicantRow(applicantAppliedJob);
        } else {
            System.out.println("No Available Applicant.");
        }
        menuUI.printApplicantsTableFooterAAJ();
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

    public void displayAllInterivew(Company company) {
        System.out.println("\n===== All Interviews =====");
        boolean found = false;
        for (Interview i : interview) {
            if (i.getApplicantAppliedJob().getJobPost().getCompany().getCompanyName()
                    .equalsIgnoreCase(company.getCompanyName())) {
                System.out.println(i);
                System.out.println("==================================================");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No interviews found for this company.");
        }
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
                            dp[i - 1][j - 1] + cost);
                }
            }
        }
        return dp[len1][len2];
    }

}
