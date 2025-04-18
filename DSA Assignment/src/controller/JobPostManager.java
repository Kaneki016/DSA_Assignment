/*Author: Wong Shu Han*/
package controller;

import adt.DoublyLinkedList;
import adt.DoublyLinkedListInterface;
import boundary.InputUI;
import boundary.MenuUI;
import entities.*;
import controller.*;
import java.time.LocalDateTime;


public class JobPostManager {
    private String currentCompanyId = null;

    private static JobPostManager instance;
    private static ApplicantAppliedJobManager applicantAppliedJobManager = ApplicantAppliedJobManager.getInstance();

    private DoublyLinkedListInterface<JobPost> jobPostList;
    private DoublyLinkedListInterface<JobPost> removedJobPosts;
    private DoublyLinkedListInterface<ApplicantAppliedJob> appliedJobs;
    
    private static InputUI inputUI = new InputUI();
    private static MenuUI menuUI = new MenuUI();

    //constructor
    private JobPostManager() {
        jobPostList = new DoublyLinkedList<>();
        removedJobPosts = new DoublyLinkedList<>();
        appliedJobs = new DoublyLinkedList<>();
    }

    public static JobPostManager getInstance() {
        if (instance == null) {
            instance = new JobPostManager();
        }
        return instance;
    }
    
    public void setCurrentCompanyId(String id) {
        this.currentCompanyId = id;
    }

    public String getCurrentCompanyId() {
        return this.currentCompanyId;
    }

    // ----------------- CREATE -----------------
    public void addJobPost() {
        inputUI.displayMessage("\n===== Add New Job Post =====");

        // Display Current Company ID 
        inputUI.displayMessage("Your Company ID: " + currentCompanyId);

        // ---------- Job Selection / Creation ----------
        JobManager jobManager = JobManager.getInstance();

        if (jobManager.isEmpty()) {
            inputUI.displayMessage("No jobs found. You need to add a job before creating a job post.");
            jobManager.addJob();  // Optionally allow the user to add a new job if none exists
        }
        
        // Display available jobs
        inputUI.displayMessage("\n===== Available Jobs =====");
        jobManager.displayJobs();  // This displays the list of jobs to the user

        Job selectedJob = null;
        while (selectedJob == null) {
            String jobId = inputUI.getInput("Enter Job ID to link this job post, or 'X' to cancel: ");

            if (jobId.equalsIgnoreCase("x")) {
                inputUI.displayMessage("Job post creation cancelled.\n");
                return;
            }

            // Find the job by ID
            selectedJob = jobManager.findJobById(jobId);

        }

        // ---------- Create and Add JobPost ----------
        CompanyManager companyManager = CompanyManager.getInstance();
        Company selectedCompany = companyManager.findCompanyById(currentCompanyId); // Use the current company ID

        if (selectedCompany == null) {
            inputUI.displayMessage("The selected company with ID " + currentCompanyId + " was not found.");
            return;
        }

        // Create the new JobPost
        JobPost newJobPost = new JobPost(selectedJob, selectedCompany);
        jobPostList.add(newJobPost);

        inputUI.displayMessage("Job Post created successfully! Job Post ID is: " + newJobPost.getJobPostId() + "\n");
    }


    public void addJobPost(JobPost jobPost) {
        jobPostList.add(jobPost);
    }

    // ----------------- READ -----------------
    public void displayJobPosts() {
        inputUI.displayMessage("\n===== Job Post List =====");

        if (jobPostList.isEmpty()) {
            inputUI.displayMessage("No job posts available.\n");
            return;
        }

        boolean foundValidJobPosts = false;  // Flag to check if there are any valid job posts
        int index = 0;  // Starting index
        DoublyLinkedList<JobPost> companyJobPosts = new DoublyLinkedList<>();  // New list for valid job posts

        // Iterate over the doubly linked list to filter job posts for the current company
        while (true) {
            JobPost currentPost = jobPostList.get(index);  // Get the job post at the current index

            if (currentPost == null) {
                break;  // If no job post is found, exit the loop
            }

            if (currentPost.getCompany().getCompanyId().equalsIgnoreCase(currentCompanyId) && currentPost.getRemovedAt() == null) {
                // Add the valid job post to the filtered list
                companyJobPosts.add(currentPost);
                foundValidJobPosts = true;
            }

            index++;  // Move to the next index
        }

        if (!foundValidJobPosts) {
            inputUI.displayMessage("No job posts available for your company.\n");
            return;
        }

        // Print the valid job posts using menuUI.printJobPosts
        menuUI.printJobPosts(companyJobPosts);

        // Allow user to view job post details
        while (true) {
            String jobPostId = inputUI.getInput("Enter Job Post ID to view details (or 'X' to finish viewing): ");
            if (jobPostId.equalsIgnoreCase("x")) {
                break;
            }

            // Search for the job post by ID in the doubly linked list
            JobPost jobPost = findJobPostById(jobPostId);
            if (jobPost != null && jobPost.getCompany().getCompanyId().equalsIgnoreCase(currentCompanyId)) {
                inputUI.displayMessage(jobPost.toString());
            } else {
                inputUI.displayMessage("Job post not found or not associated with your company.\n");
            }
        }

        // Prompt the user to continue
        inputUI.getInput("Press Enter to continue...");
    }

    public void displayMatchedJobPosts(Applicant applicant) {
        inputUI.displayMessage("\n===== Matched Job Posts =====");

        if (jobPostList.isEmpty()) {
            inputUI.displayMessage("No job posts available.\n");
            return;
        }

        int index = 1;
        boolean hasMatched = false;

        System.out.println("\nMatched Jobs (Based on Skills):");
        for (JobPost jobPost : jobPostList) {
            if (isSkillMatched(jobPost, applicant)) {
                System.out.println("\u001B[32m" + index + ". " + jobPost.toString() + "\u001B[0m"); // Green
                hasMatched = true;
            }
            index++;
        }

        if (!hasMatched) {
            inputUI.displayMessage("No matched job posts found based on your skills.\n");
        }

        System.out.println("\nOther Available Jobs:");
        index = 1;
        for (JobPost jobPost : jobPostList) {
            if (!isSkillMatched(jobPost, applicant)) {
                System.out.println(index + ". " + jobPost.toString()); // Default color
            }
            index++;
        }
    }

    public boolean isSkillMatched(JobPost jobPost, Applicant applicant) {
        for (JobRequirements required : jobPost.getJob().getJobRequirements()) {
            for (Skill skill : applicant.getSkills()) {
                if (required.getName().equalsIgnoreCase(skill.getName())
                    && skill.getProficiency_level() >= Integer.parseInt(required.getProficiencyLevel())) {
                    return true;
                }
            }
        }
        return false;
    }  

    public JobPost findJobPostById(String jobPostId) {
        for (JobPost jobPost : jobPostList) {
            if (jobPost.getJobPostId().equalsIgnoreCase(jobPostId)) {
                return jobPost;
            }
        }
        inputUI.displayMessage("Job Post with ID " + jobPostId + " not found.\nPlease try again.\n");
        return null;
    }

    // ----------------- UPDATE -----------------
    public void editJobPost() {
        inputUI.displayMessage("\n===== Edit Job Post =====");

        if (jobPostList.isEmpty()) {
            inputUI.displayMessage("No job posts to edit.\n");
            return;
        }

        inputUI.displayMessage("Current Company ID: " + currentCompanyId);

        // Filter job posts for current company
        DoublyLinkedList<JobPost> companyJobPosts = new DoublyLinkedList<>();
        for (int i = 0; i < jobPostList.size(); i++) {
            JobPost jp = jobPostList.get(i);
            if (jp.getCompany().getCompanyId().equalsIgnoreCase(currentCompanyId)) {
                companyJobPosts.add(jp);
            }
        }

        if (companyJobPosts.isEmpty()) {
            inputUI.displayMessage("No job posts available for your company to edit.\n");
            return;
        }

        // Show job posts for this company
        menuUI.printJobPosts(companyJobPosts);

        // Prepare applied jobs once
        ApplicantAppliedJobManager applicantAppliedJobManager = ApplicantAppliedJobManager.getInstance();
        DoublyLinkedListInterface<ApplicantAppliedJob> appliedJobs = applicantAppliedJobManager.getApplicantAppliedJobs();

        while (true) {
            String jobPostId = inputUI.getInput("Enter Job Post ID to edit (or 'X' to cancel): ");
            if (jobPostId.equalsIgnoreCase("X")) {
                inputUI.displayMessage("Edit cancelled.\n");
                break;
            }

            JobPost jobPost = findJobPostById(jobPostId);
            if (jobPost == null || !jobPost.getCompany().getCompanyId().equalsIgnoreCase(currentCompanyId)) {
                inputUI.displayMessage("Invalid Job Post ID or not associated with your company.\n");
                continue;
            }

            boolean isApplied = false;
            for (int i = 0; i < appliedJobs.size(); i++) {
                ApplicantAppliedJob appliedJob = appliedJobs.get(i);
                JobPost appliedJobPost = appliedJob.getJobPost();
                if (appliedJobPost != null && appliedJobPost.getJobPostId().equalsIgnoreCase(jobPostId)) {
                    isApplied = true;
                    break;
                }
            }

            if (isApplied) {
                inputUI.displayMessage("Cannot edit this job post. It has already been assigned to an interview timeslot.\n");
                continue;
            }

            inputUI.displayMessage("Editing Job Post: " + jobPost.toString());

            // Option to update the Job
            if (inputUI.getInput("Change Job? (y/n): ").equalsIgnoreCase("y")) {
                JobManager jobManager = JobManager.getInstance();
                jobManager.displayJobs();

                while (true) {
                    String newJobId = inputUI.getInput("Enter New Job ID (or 'X' to cancel): ");
                    if (newJobId.equalsIgnoreCase("X")) break;

                    Job newJob = jobManager.findJobById(newJobId);
                    if (newJob != null) {
                        jobPost.setJob(newJob);
                        break;
                    } else {
                        inputUI.displayMessage("Invalid Job ID.\n");
                    }
                }
            }

            int index = getJobPostIndex(jobPostId);
            if (index != -1) {
                jobPostList.replace(index, jobPost);
                inputUI.displayMessage("Job Post updated successfully.\n");
                return;
            } else {
                inputUI.displayMessage("Unexpected error: Unable to update job post.\n");
            }
        }
    }


    // ----------------- DELETE -----------------
    public void removeJobPost() {
        inputUI.displayMessage("\n===== Remove Job Post =====");

        if (jobPostList.isEmpty()) {
            inputUI.displayMessage("No job posts to remove.\n");
            return;
        }

        inputUI.displayMessage("Current Company ID: " + currentCompanyId);

        // Filter job posts for current company
        DoublyLinkedList<JobPost> companyJobPosts = new DoublyLinkedList<>();
        for (int i = 0; i < jobPostList.size(); i++) {
            JobPost jp = jobPostList.get(i);
            if (jp.getCompany().getCompanyId().equalsIgnoreCase(currentCompanyId)) {
                companyJobPosts.add(jp);
            }
        }

        if (companyJobPosts.isEmpty()) {
            inputUI.displayMessage("No job posts available for your company to remove.\n");
            return;
        }

        // Show job posts for this company
        menuUI.printJobPosts(companyJobPosts);

        while (true) {
            String jobPostId = inputUI.getInput("Enter Job Post ID to remove (or 'X' to cancel): ");
            if (jobPostId.equalsIgnoreCase("X")) {
                inputUI.displayMessage("Removal cancelled.\n");
                break;
            }

            JobPost jobPost = findJobPostById(jobPostId);
            if (jobPost == null || !jobPost.getCompany().getCompanyId().equalsIgnoreCase(currentCompanyId)) {
                inputUI.displayMessage("Invalid Job Post ID or not associated with your company.\n");
                continue;
            }

            // Check if job post has been applied to
            ApplicantAppliedJobManager applicantAppliedJobManager = ApplicantAppliedJobManager.getInstance();
            DoublyLinkedListInterface<ApplicantAppliedJob> appliedJobs = applicantAppliedJobManager.getApplicantAppliedJobs();

            boolean isApplied = false;
            for (int i = 0; i < appliedJobs.size(); i++) {
                ApplicantAppliedJob appliedJob = appliedJobs.get(i);
                JobPost appliedJobPost = appliedJob.getJobPost();
                if (appliedJobPost != null && appliedJobPost.getJobPostId().equalsIgnoreCase(jobPostId)) {
                    isApplied = true;
                    break;
                }
            }

            if (isApplied) {
                inputUI.displayMessage("Cannot remove this job post. It has already been assigned to an interview timeslot.\n");
                continue;
            }

            int index = getJobPostIndex(jobPostId);
            if (index != -1) {
                JobPost removed = jobPostList.get(index);
                removed.setRemovedAt(LocalDateTime.now());
                jobPostList.remove(index);
                removedJobPosts.add(removed);
                inputUI.displayMessage("Job Post removed successfully.\n");
                return;
            } else {
                inputUI.displayMessage("Unexpected error: Unable to remove job post.\n");
            }
        }
    }


    public void displayRemovedPosts() {
        inputUI.displayMessage("\n===== Removed Job Posts =====");

        if (removedJobPosts.isEmpty()) {
            inputUI.displayMessage("No removed job posts.\n");
            return;
        }

        if (currentCompanyId == null) {
            inputUI.displayMessage("No company is currently selected.\n");
            return;
        }

        // Filter removed job posts that belong to the current company
        DoublyLinkedList<JobPost> filteredRemovedPosts = new DoublyLinkedList<>();
        for (int i = 0; i < removedJobPosts.size(); i++) {
            JobPost jobPost = removedJobPosts.get(i);
            if (jobPost.getCompany().getCompanyId().equalsIgnoreCase(currentCompanyId)) {
                filteredRemovedPosts.add(jobPost);
            }
        }

        if (filteredRemovedPosts.isEmpty()) {
            inputUI.displayMessage("No removed job posts found for your company.\n");
            return;
        }

        // Display the filtered list
        menuUI.printRemovedJobPosts(filteredRemovedPosts);

        // Prompt user to continue
        inputUI.getInput("Press Enter to continue...");
    }


    public void printJobPostReports() {
        DoublyLinkedListInterface<JobPost> allPosts = new DoublyLinkedList<>();
        for (JobPost jp : jobPostList) {
            allPosts.add(jp);
        }
        for (JobPost jp : removedJobPosts) {
            allPosts.add(jp);
        }

        menuUI.printJobPostReports(allPosts);
    }
    
    public void displayJobLocationSummaryTable() {
        DoublyLinkedListInterface<ApplicantAppliedJob> appliedJobs = applicantAppliedJobManager.getApplicantAppliedJobs();

        // Print report header
        menuUI.printReportHeader("APPLICANT SUMMARY REPORT");

        final int REPORT_WIDTH = 125;

        // Display report heading
        inputUI.displayMessage("=".repeat(REPORT_WIDTH));
        inputUI.displayMessage(menuUI.centerText("JOB LOCATION SUMMARY TABLE", REPORT_WIDTH));
        inputUI.displayMessage("=".repeat(REPORT_WIDTH));
        inputUI.displayMessage(String.format("| %-50s | %-40s | %-15s |", "Location", "Company", "Applicants"));
        inputUI.displayMessage("-".repeat(REPORT_WIDTH));

        // Track job posts, companies, and applicant counts
        DoublyLinkedListInterface<String> trackedKeys = new DoublyLinkedList<>();
        DoublyLinkedListInterface<Integer> applicantCounts = new DoublyLinkedList<>();
        DoublyLinkedListInterface<String> locationNames = new DoublyLinkedList<>();
        DoublyLinkedListInterface<Integer> locationApplicantCounts = new DoublyLinkedList<>();
        DoublyLinkedListInterface<String> companyNames = new DoublyLinkedList<>();
        DoublyLinkedListInterface<Integer> companyApplicantCounts = new DoublyLinkedList<>();

        // Track the number of applicants for locations and companies
        for (ApplicantAppliedJob app : appliedJobs) {
            String jobLocation = app.getJobPost().getJob().getLocation();  // Getting job location
            String companyName = app.getJobPost().getCompany().getCompanyName();  // Getting company name
            String key = jobLocation + "@" + companyName;

            // Track the number of applicants per location
            int locationIndex = locationNames.indexOf(jobLocation);
            if (locationIndex != -1) {
                locationApplicantCounts.replace(locationIndex, locationApplicantCounts.get(locationIndex) + 1);
            } else {
                locationNames.add(jobLocation);
                locationApplicantCounts.add(1);
            }

            // Track the number of applicants per company
            int companyIndex = companyNames.indexOf(companyName);
            if (companyIndex != -1) {
                companyApplicantCounts.replace(companyIndex, companyApplicantCounts.get(companyIndex) + 1);
            } else {
                companyNames.add(companyName);
                companyApplicantCounts.add(1);
            }

            // Track the job applications
            boolean found = false;
            for (int i = 0; i < trackedKeys.getSize(); i++) {
                if (trackedKeys.get(i).equalsIgnoreCase(key)) {
                    applicantCounts.replace(i, applicantCounts.get(i) + 1);
                    found = true;
                    break;
                }
            }

            if (!found) {
                trackedKeys.add(key);
                applicantCounts.add(1);
            }
        }

        // Display job application summary
        for (int i = 0; i < trackedKeys.getSize(); i++) {
            String[] parts = trackedKeys.get(i).split("@");
            String jobLocation = parts[0];
            String company = parts[1];
            int count = applicantCounts.get(i);

            inputUI.displayMessage(String.format("| %-50s | %-40s | %-15d |", jobLocation, company, count));
        }

        inputUI.displayMessage("=".repeat(REPORT_WIDTH));

        // Bar chart for location applicants (display in descending order)
        printLocationBarChart(locationNames, locationApplicantCounts);

        // Find the most applied locations
        int maxLocationApplicants = 0;
        DoublyLinkedListInterface<String> mostAppliedLocations = new DoublyLinkedList<>();
        for (int i = 0; i < locationNames.getSize(); i++) {
            int currentApplicants = locationApplicantCounts.get(i);
            if (currentApplicants > maxLocationApplicants) {
                maxLocationApplicants = currentApplicants;
                mostAppliedLocations.clear();  // Clear previous locations with the old max
                mostAppliedLocations.add(locationNames.get(i));  // Add new most applied location
            } else if (currentApplicants == maxLocationApplicants) {
                mostAppliedLocations.add(locationNames.get(i));  // Add this location to the list
            }
        }

        // Display Most Applied Location(s)
        inputUI.displayMessage("Most Applied Location(s): " + String.join(", ", mostAppliedLocations) + " (" + maxLocationApplicants + " applicants)");

        menuUI.printEndOfReport(REPORT_WIDTH);
    }

    private void printLocationBarChart(DoublyLinkedListInterface<String> locationNames,
                                        DoublyLinkedListInterface<Integer> locationApplicantCounts) {

        final int COLUMN_WIDTH = 8;
        int max = 0;
        // Find the max number of applicants for locations
        for (int i = 0; i < locationApplicantCounts.getSize(); i++) {
            if (locationApplicantCounts.get(i) > max) {
                max = locationApplicantCounts.get(i);
            }
        }

        inputUI.displayMessage("No. of Applicants (by Location)");

        // Display the bar chart for location applicants
        for (int level = max; level >= 1; level--) {
            StringBuilder row = new StringBuilder();
            row.append(String.format("%2d |", level));
            for (int i = 0; i < locationApplicantCounts.getSize(); i++) {
                String bar = locationApplicantCounts.get(i) >= level ? "*" : " ";
                row.append(String.format("   %-2s   ", bar));
            }
            inputUI.displayMessage(row.toString());
        }

        // Separator for the X-axis
        StringBuilder separator = new StringBuilder("   +");
        for (int i = 0; i < locationNames.getSize(); i++) {
            separator.append("-".repeat(COLUMN_WIDTH - 1)).append("+");
        }
        inputUI.displayMessage(separator.append("-> Locations").toString());

        // Display location names at the bottom
        StringBuilder labels = new StringBuilder("    ");
        for (int i = 0; i < locationNames.getSize(); i++) {
            String location = locationNames.get(i);
            String locationShort = location.length() > 3 ? location.substring(0, 3) : location;

            int totalPadding = COLUMN_WIDTH;
            int leftPadding = (totalPadding - locationShort.length()) / 2;
            int rightPadding = totalPadding - locationShort.length() - leftPadding;

            labels.append(" ".repeat(leftPadding)).append(locationShort).append(" ".repeat(rightPadding));
        }
        inputUI.displayMessage(labels.toString());
    }

  
    // ----------------- Search -----------------
    public void searchJobPost() {
        inputUI.displayMessage("\n===== Search Job Posts =====");

        if (jobPostList.isEmpty()) {
            inputUI.displayMessage("No job posts available.\n");
            return;
        }

        // Filter job posts by currentCompanyId first
        DoublyLinkedListInterface<JobPost> companyJobPosts = new DoublyLinkedList<>();
        for (JobPost jp : jobPostList) {
            if (jp.getCompany().getCompanyId().equalsIgnoreCase(currentCompanyId)) {
                companyJobPosts.add(jp);
            }
        }

        if (companyJobPosts.isEmpty()) {
            inputUI.displayMessage("No job posts available for your company.\n");
            return;
        }

        while (true) {
            // Display available job posts
            menuUI.printJobPosts(companyJobPosts);

            // Get search criteria from the user
            String criteriaInput = inputUI.getInput(
                "\nEnter search criteria (e.g., location(KL),salary(5000),title(Engineer))\n" +
                "or enter 'X' to stop searching: ");

            // Check if the user wants to quit the search entirely
            if (criteriaInput.equalsIgnoreCase("x")) {
                inputUI.displayMessage("Search cancelled.\n");
                return; // Exit the method entirely
            }

            String[] criteriaPairs = criteriaInput.split(",");

            String location = null;
            Double minSalary = null;
            String title = null;

            for (String pair : criteriaPairs) {
                pair = pair.trim();
                if (pair.toLowerCase().startsWith("location(")) {
                    location = pair.substring(9, pair.length() - 1).trim();
                } else if (pair.toLowerCase().startsWith("salary(")) {
                    try {
                        String raw = pair.substring(pair.indexOf('(') + 1, pair.lastIndexOf(')')).trim();
                        minSalary = Double.parseDouble(raw);
                    } catch (NumberFormatException e) {
                        inputUI.displayMessage("Invalid salary value. Skipping salary filter.");
                    }
                } else if (pair.toLowerCase().startsWith("title(")) {
                    title = pair.substring(6, pair.length() - 1).trim();
                }
            }

            // Filter job posts based on criteria
            DoublyLinkedListInterface<JobPost> filteredResults = new DoublyLinkedList<>();
            for (JobPost jp : companyJobPosts) {
                boolean matches = true;
                if (location != null && !jp.getJob().getLocation().equalsIgnoreCase(location)) {
                    matches = false;
                }
                if (minSalary != null && jp.getJob().getSalary() < minSalary) {
                    matches = false;
                }
                if (title != null && !jp.getJob().getTitle().equalsIgnoreCase(title)) {
                    matches = false;
                }

                if (matches) {
                    filteredResults.add(jp);
                }
            }

            if (filteredResults.isEmpty()) {
                inputUI.displayMessage("No job posts matched the criteria.\n");
            } else {
                menuUI.printJobPosts(filteredResults);

                while (true) {
                    String jobPostId = inputUI.getInput("Enter Job Post ID to view details (or 'X' to go back to search): ");
                    if (jobPostId.equalsIgnoreCase("x")) {
                        break; // Exit job post view and return to search
                    }

                    JobPost jobPost = findJobPostById(jobPostId);
                    if (jobPost != null && jobPost.getCompany().getCompanyId().equalsIgnoreCase(currentCompanyId)) {
                        System.out.println(jobPost);
                    } else {
                        System.out.println("Invalid Job Post ID or not part of your company.");
                    }
                }
            }

            // Prompt to continue or exit the search
            String continueSearch = inputUI.getInput("Press Enter to search again or enter 'X' to exit search: ");
            if (continueSearch.equalsIgnoreCase("x")) {
                inputUI.displayMessage("Search cancelled.\n");
                return; // Exit the method entirely
            }
        }
    }




    // ----------------- Helper Methods -----------------
    private int getJobPostIndex(String jobPostId) {
        int index = 0;
        for (JobPost jobPost : jobPostList) {
            if (jobPost.getJobPostId().equalsIgnoreCase(jobPostId)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public boolean isEmpty() {
        return jobPostList.isEmpty();
    }

    public DoublyLinkedListInterface<JobPost> getJobPostList() {
        return jobPostList;
    }

    public DoublyLinkedListInterface<JobPost> getJobPostsByCompanyId(String companyId) {
        DoublyLinkedListInterface<JobPost> filteredJobPosts = new DoublyLinkedList<>();

        for (JobPost jobPost : jobPostList) {
            if (jobPost.getCompany().getCompanyId().equalsIgnoreCase(companyId)) {
                filteredJobPosts.add(jobPost);
            }
        }

        return filteredJobPosts;
    }

    public JobPost selectJobPost(DoublyLinkedListInterface<JobPost> jobPosts) {
        if (jobPosts.isEmpty()) {
            System.out.println("No job posts available.");
            return null;
        }

        System.out.println("Available Job Posts:");
        int index = 1;
        for (JobPost jobPost : jobPosts) {
            System.out.println(index + ". " + jobPost.getJob().getTitle()); // Show job title instead of full object
            index++;
        }

        while (true) {
            String input = inputUI.getInput("Select a job post by number: ").trim();
            System.out.println("User input: " + input); // Debugging output

            // Check if input is a number
            if (!input.matches("\\d+")) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            int choice = Integer.parseInt(input);
            if (choice >= 1 && choice <= jobPosts.size()) {
                return jobPosts.get(choice - 1); // Adjust for zero-based indexing
            } else {
                System.out.println("Invalid choice. Please enter a valid number between 1 and " + jobPosts.size());
            }
        }
    }
 
}
