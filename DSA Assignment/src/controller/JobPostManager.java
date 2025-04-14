package controller;

import adt.DoublyLinkedList;
import adt.DoublyLinkedListInterface;
import boundary.InputUI;
import boundary.MenuUI;
import entities.Company;
import entities.Job;
import entities.JobPost;
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
    
    // ----------------- CREATE ------------------------------------------------------------------------------------------------------
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
            // ❌ Removed duplicate error message here
        }

        // ---------- Job Selection / Creation ----------
        JobManager jobManager = JobManager.getInstance();

        if (jobManager.isEmpty()) {
            inputUI.displayMessage("No jobs found. You need to add a job before creating a job post.");
            jobManager.addJob();
        }

        Job selectedJob = null;
        while (selectedJob == null) {
            jobManager.displayJobs();
            String jobId = inputUI.getInput("Enter Job ID to link this job post, 'new' to add a new job, or 'X' to cancel: ");

            if (jobId.equalsIgnoreCase("x")) {
                inputUI.displayMessage("Job post creation cancelled.\n");
                return;
            } else if (jobId.equalsIgnoreCase("new")) {
                jobManager.addJob();
                continue;
            }

            selectedJob = jobManager.findJobById(jobId);
            if (selectedJob == null) {
                inputUI.displayMessage("Invalid Job ID. Please try again.\n");
            }
        }

        // ---------- Create and Add JobPost ----------
        JobPost newJobPost = new JobPost(selectedJob, selectedCompany);
        jobPostList.add(newJobPost);

        inputUI.displayMessage("Job Post created successfully! Job Post ID is: " + newJobPost.getJobPostId() + "\n");
    }

    public void addJobPost(JobPost jobPost) {
        jobPostList.add(jobPost);
    }


    
    // ----------------- READ --------------------------------------------------------------------------------------------------------
    public void displayJobPosts() {
        inputUI.displayMessage("\n===== Job Post List =====");

        if (jobPostList.isEmpty()) {
            inputUI.displayMessage("No job posts available.\n");
            return;
        }

        // Print the job posts using menuUI (consistent with applicants)
        menuUI.printJobPosts(jobPostList);

        // Prompt the user to continue
        inputUI.getInput("Press Enter to continue...");
    }

    public JobPost findJobPostById(String jobPostId) {
        for (JobPost jobPost : jobPostList) {
            if (jobPost.getJobPostId().equalsIgnoreCase(jobPostId)) {
                return jobPost;
            }
        }
        return null;
    }
    
    
    // ----------------- UPDATE ------------------------------------------------------------------------------------------------------
    public void editJobPost() {
        inputUI.displayMessage("\n===== Edit Job Post =====");

        if (jobPostList.isEmpty()) {
            inputUI.displayMessage("No job posts to edit.\n");
            return;
        }

        displayJobPosts();
        String jobPostId = inputUI.getInput("Enter Job Post ID to edit(Enter x to cancel): ");
        if (jobPostId.equalsIgnoreCase("x")) {
            inputUI.displayMessage("Edit cancelled.\n");
            return;
        }
        
        JobPost jobPost = findJobPostById(jobPostId);
        if (jobPost == null) {
            inputUI.displayMessage("Job Post not found!\n");
            return;
        }

        inputUI.displayMessage("Editing Job Post: " + jobPost.toString());

        // Option to update the Company
        if (inputUI.getInput("Change Company? (y/n): ").equalsIgnoreCase("y")) {
            CompanyManager companyManager = CompanyManager.getInstance();

            companyManager.displayCompanies();
            String newCompanyId = inputUI.getInput("Enter New Company ID: ");
            Company newCompany = companyManager.findCompanyById(newCompanyId);
            if (newCompany != null) {
                jobPost.setCompany(newCompany); // ✅ Update the company directly
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
                jobPost.setJob(newJob); // ✅ Update the job directly
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
        } else {
            inputUI.displayMessage("Unexpected error: Unable to update job post.\n");
        }
    }

    
    // ----------------- DELETE ------------------------------------------------------------------------------------------------------
    public void removeJobPost() {
        inputUI.displayMessage("\n===== Remove Job Post =====");

        if (jobPostList.isEmpty()) {
            inputUI.displayMessage("No job posts to remove.\n");
            return;
        }

        displayJobPosts(); // Show once initially

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
        menuUI.printJobPostReports(jobPostList);
    }


    // ----------------- Helper Methods ------------------------------------------------------------------------------------------------------
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
