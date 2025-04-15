package controller;

import adt.DoublyLinkedList;
import adt.DoublyLinkedListInterface;
import boundary.InputUI;
import boundary.MenuUI;
import entities.Applicant;
import entities.Company;
import entities.Job;
import entities.JobPost;
import entities.JobRequirements;
import entities.Skill;
import java.time.LocalDateTime;


public class JobPostManager {
    private static JobPostManager instance;

    private DoublyLinkedListInterface<JobPost> jobPostList;
    private DoublyLinkedListInterface<JobPost> removedJobPosts;

    private static InputUI inputUI = new InputUI();
    private static MenuUI menuUI = new MenuUI();

    //constructor
    private JobPostManager() {
        jobPostList = new DoublyLinkedList<>();
        removedJobPosts = new DoublyLinkedList<>();
    }

    public static JobPostManager getInstance() {
        if (instance == null) {
            instance = new JobPostManager();
        }
        return instance;
    }

    // ----------------- CREATE -----------------
    public void addJobPost() {
        inputUI.displayMessage("\n===== Add New Job Post =====");

        // ---------- Company Selection ----------
        CompanyManager companyManager = CompanyManager.getInstance();

        if (companyManager.isEmpty()) {
            inputUI.displayMessage("No companies available. Please add a company first!\n");
            return;
        }

        Company selectedCompany = null;
        companyManager.displayCompanies();
        while (selectedCompany == null) {
            String companyId = inputUI.getInput("Enter Company ID to link this job post (or 'X' to cancel): ");

            if (companyId.equalsIgnoreCase("x")) {
                inputUI.displayMessage("Job post creation cancelled.\n");
                return;
            }

            selectedCompany = companyManager.findCompanyById(companyId);

        }

        // ---------- Job Selection / Creation ----------
        JobManager jobManager = JobManager.getInstance();

        if (jobManager.isEmpty()) {
            inputUI.displayMessage("No jobs found. You need to add a job before creating a job post.");
            jobManager.addJob();
        }

        Job selectedJob = null;
        jobManager.displayJobs();
        while (selectedJob == null) {
            String jobId = inputUI.getInput("Enter Job ID to link this job post, 'new' to add a new job, or 'X' to cancel: ");

            if (jobId.equalsIgnoreCase("x")) {
                inputUI.displayMessage("Job post creation cancelled.\n");
                return;
            } else if (jobId.equalsIgnoreCase("new")) {
                jobManager.addJob();
                continue;
            }

            selectedJob = jobManager.findJobById(jobId);
        }

        // ---------- Create and Add JobPost ----------
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

        // Print the job posts using menuUI (consistent with applicants)
        menuUI.printJobPosts(jobPostList);

        while (true) {
            String jobPostId = inputUI.getInput("Enter Job Post ID to view details (or 'X' to finish viewing): ");
            if (jobPostId.equalsIgnoreCase("x")) {
                break;
            }

            JobPost jobPost = findJobPostById(jobPostId);
            if (jobPost != null) {
                inputUI.displayMessage(jobPost.toString());
            } else {
                System.out.println("Invalid Job Post ID. Please try again.");
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
        return null;
    }

    // ----------------- UPDATE -----------------
    public void editJobPost() {
        inputUI.displayMessage("\n===== Edit Job Post =====");

        if (jobPostList.isEmpty()) {
            inputUI.displayMessage("No job posts to edit.\n");
            return;
        }

        menuUI.printJobPosts(jobPostList);

        while (true) {
            String jobPostId = inputUI.getInput("Enter Job Post ID to edit (Enter 'x' to cancel): ");
            if (jobPostId.equalsIgnoreCase("x")) {
                inputUI.displayMessage("Edit cancelled.\n");
                return;
            }

            JobPost jobPost = findJobPostById(jobPostId);
            if (jobPost == null) {
                inputUI.displayMessage("Invalid Job Post ID. Please try again.\n");
                continue; // Prompt the user to re-enter the Job Post ID
            }

            inputUI.displayMessage("Editing Job Post: " + jobPost.toString());

            // Option to update the Company
            if (inputUI.getInput("Change Company? (y/n): ").equalsIgnoreCase("y")) {
                CompanyManager companyManager = CompanyManager.getInstance();
                companyManager.displayCompanies();
                String newCompanyId = inputUI.getInput("Enter New Company ID: ");
                Company newCompany = companyManager.findCompanyById(newCompanyId);
                if (newCompany != null) {
                    jobPost.setCompany(newCompany); // Update the company directly
                    inputUI.displayMessage("Company updated.\n");
                } else {
                    inputUI.displayMessage("Invalid Company ID. Company not updated.\n");
                }
            }

            // Option to update the Job
            if (inputUI.getInput("Change Job? (y/n): ").equalsIgnoreCase("y")) {
                JobManager jobManager = JobManager.getInstance();
                jobManager.displayJobs();
                String newJobId = inputUI.getInput("Enter New Job ID: ");
                Job newJob = jobManager.findJobById(newJobId);
                if (newJob != null) {
                    jobPost.setJob(newJob); // Update the job directly
                    inputUI.displayMessage("Job updated.\n");
                } else {
                    inputUI.displayMessage("Invalid Job ID. Job not updated.\n");
                }
            }

            // Replace the job post at its index
            int index = getJobPostIndex(jobPostId);
            if (index != -1) {
                jobPostList.replace(index, jobPost);
                inputUI.displayMessage("Job Post updated successfully!\n");
                break; // Exit the loop once the job post is successfully updated
            } else {
                inputUI.displayMessage("Unexpected error: Unable to update job post.\n");
                break; // Exit the loop if an unexpected error occurs
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

        menuUI.printJobPosts(jobPostList);

        while (true) {
            String jobPostId = inputUI.getInput("Enter Job Post ID to remove (or 'X' to cancel): ");

            if (jobPostId.equalsIgnoreCase("X")) {
                inputUI.displayMessage("Removal cancelled.\n");
                break;
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
                inputUI.displayMessage("Job Post not found! Please try again or type 'X' to cancel.\n");
            }
        }
    }

    // New method to remove job posts by associated job ID
    public void removeJobPostsByJob(String jobId) {
        for (int i = 0; i < jobPostList.size(); i++) {
            JobPost jobPost = jobPostList.get(i);
            if (jobPost.getJob().getJobId().equals(jobId)) {
                jobPost.setRemovedAt(LocalDateTime.now());
                jobPostList.remove(i);
                removedJobPosts.add(jobPost);
                i--;  // Adjust the index as the list shrinks
            }
        }
    }
    
    public void displayRemovedPosts() {
        inputUI.displayMessage("\n===== Removed Job Posts =====");

        if (removedJobPosts.isEmpty()) {
            inputUI.displayMessage("No removed job posts.\n");
            return;
        }

        // Use the new table display method
        menuUI.printRemovedJobPosts(removedJobPosts);

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
    
    // ----------------- Search -----------------
    public void searchJobPost() {
        inputUI.displayMessage("\n===== Search Job Posts =====");
        this.displayJobPosts();
        if (jobPostList.isEmpty()) {
            inputUI.displayMessage("No job posts available.\n");
            return;
        }

        String criteriaInput = inputUI.getInput("Enter search criteria (e.g., location(KL),salary(5000),company(Google),title(Engineer)): ");
        String[] criteriaPairs = criteriaInput.split(",");

        String location = null;
        Double minSalary  = null;
        String companyName = null;
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
            } else if (pair.toLowerCase().startsWith("company(")) {
                companyName = pair.substring(8, pair.length() - 1).trim();
            } else if (pair.toLowerCase().startsWith("title(")) {
                title = pair.substring(6, pair.length() - 1).trim();
            }
        }

        DoublyLinkedListInterface<JobPost> filteredResults = new DoublyLinkedList<>();
        for (JobPost jp : jobPostList) {
            boolean matches = true;
            if (location != null && !jp.getJob().getLocation().toLowerCase().equalsIgnoreCase(location.toLowerCase())) {
                matches = false;
            }
            if (minSalary != null && jp.getJob().getSalary() < minSalary) {
                matches = false;
            }
            if (companyName != null && !jp.getCompany().getCompanyName().equalsIgnoreCase(companyName)) {
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
                String jobPostId = inputUI.getInput("Enter Job Post ID to view details (or 'X' to skip): ");
                if (jobPostId.equalsIgnoreCase("x")) {
                    break;
                }

                JobPost jobPost = findJobPostById(jobPostId);
                if (jobPost != null) {
                    System.out.println(jobPost);
                } else {
                    System.out.println("Invalid Job Post ID. Please try again.");
                }
            }
        }

        inputUI.getInput("Press Enter to continue...");
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
