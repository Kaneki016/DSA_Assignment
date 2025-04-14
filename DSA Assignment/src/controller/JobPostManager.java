package controller;

import adt.DoublyLinkedList;
import adt.DoublyLinkedListInterface;
import boundary.InputUI;
import entities.Applicant;
import entities.Company;
import entities.Job;
import entities.JobPost;
import entities.JobRequirements;
import entities.Skill;

public class JobPostManager {
    private static JobPostManager instance;

    private DoublyLinkedListInterface<JobPost> jobPostList;

    private static InputUI inputUI = new InputUI();

    private JobPostManager() {
        jobPostList = new DoublyLinkedList<>();
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

        companyManager.displayCompanies();
        String companyId = inputUI.getInput("Enter Company ID to link this job post: ");
        Company selectedCompany = companyManager.findCompanyById(companyId);
        if (selectedCompany == null) {
            inputUI.displayMessage("Invalid Company ID. Aborting job post creation.\n");
            return;
        }

        // ---------- Job Selection / Creation ----------
        JobManager jobManager = JobManager.getInstance();

        // If no jobs exist, force job creation first
        if (jobManager.isEmpty()) {
            inputUI.displayMessage("No jobs found. You need to add a job before creating a job post.");
            jobManager.addJobWithRequirements();
        }

        boolean jobSelected = false;
        Job selectedJob = null;

        while (!jobSelected) {
            jobManager.displayJobs();
            String jobId = inputUI.getInput("Enter Job ID to link this job post, or type 'new' to add a new job: ");

            if (jobId.equalsIgnoreCase("new")) {
                jobManager.addJobWithRequirements();
                continue; // after adding, show the jobs again
            }

            selectedJob = jobManager.findJobById(jobId);
            if (selectedJob != null) {
                jobSelected = true;
            } else {
                inputUI.displayMessage("Invalid Job ID. Try again.\n");
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

    // ----------------- READ -----------------
    public void displayJobPosts() {
        inputUI.displayMessage("\n===== Job Post List =====");

        if (jobPostList.isEmpty()) {
            inputUI.displayMessage("No job posts available.\n");
            return;
        }

        int index = 1;
        for (JobPost jobPost : jobPostList) {
            inputUI.displayMessage(index + ". " + jobPost.toString());
            index++;
        }
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

        displayJobPosts();
        String jobPostId = inputUI.getInput("Enter Job Post ID to edit: ");
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

    // ----------------- DELETE -----------------
    public void removeJobPost() {
        inputUI.displayMessage("\n===== Remove Job Post =====");

        if (jobPostList.isEmpty()) {
            inputUI.displayMessage("No job posts to remove.\n");
            return;
        }

        displayJobPosts();
        String jobPostId = inputUI.getInput("Enter Job Post ID to remove: ");
        int index = getJobPostIndex(jobPostId);

        if (index == -1) {
            inputUI.displayMessage("Job Post not found!\n");
            return;
        }

        jobPostList.remove(index);
        inputUI.displayMessage("Job Post removed successfully.\n");
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
