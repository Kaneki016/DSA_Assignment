package controller;

import adt.DoublyLinkedList;
import adt.DoublyLinkedListInterface;
import entities.*;
import boundary.*;

public class JobManager {
    
    // Menu UI
    private static InputUI inputUI = new InputUI();
    
    // Singleton instance
    private static JobManager instance;
    
    // Data structure to hold Jobs
    private DoublyLinkedListInterface<Job> jobList;
    // Private constructor for Singleton
    private JobManager() {
        jobList = new DoublyLinkedList<>();
    }
    
    // Singleton accessor
    public static JobManager getInstance() {
        if (instance == null) {
            instance = new JobManager();
        }
        return instance;
    }
    
    // ----------------- CREATE -----------------
    public void addJob() {
        inputUI.displayMessage("\n===== Add New Job =====");

        String title = inputUI.getInput("Enter Job Title: ");
        String location = inputUI.getInput("Enter Job Location: ");
        int requiredExperience = inputUI.getIntInput("Enter Required Experience (years): ", 0, 100);
        float salary = inputUI.getValidFloatInput("Enter Salary (min: 0): ", 0, Float.MAX_VALUE);

        // Create job requirements list (empty initially, or you could build here)
        DoublyLinkedListInterface<JobRequirements> jobRequirements = new DoublyLinkedList<>();

        Job newJob = new Job(title, location, requiredExperience, jobRequirements, salary);
        jobList.add(newJob);

        inputUI.displayMessage("Job added successfully! Job ID is: " + newJob.getJobId() + "\n");
    }

    // Add job directly
    public void addJob(Job job) {
        jobList.add(job);
    }

    // ----------------- READ -----------------
    public void displayJobs() {
        inputUI.displayMessage("\n===== Job List =====");

        if (jobList.isEmpty()) {
            inputUI.displayMessage("No jobs found.\n");
            return;
        }

        int index = 1;
        for (Job job : jobList) {
            inputUI.displayMessage(index + ". " + job.toString() + "\n");
            index++;
        }
    }

    public Job findJobById(String jobId) {
        for (Job job : jobList) {
            if (job.getJobId().equalsIgnoreCase(jobId)) {
                return job;
            }
        }
        inputUI.displayMessage("Job with ID " + jobId + " not found.\n");
        return null;
    }
    
    // ----------------- UPDATE -----------------
    public void editJob() {
        inputUI.displayMessage("\n===== Edit Job =====");

        String jobId = inputUI.getInput("Enter Job ID to edit: ");
        Job job = findJobById(jobId);

        if (job == null) {
            inputUI.displayMessage("Job not found!");
            return;
        }

        inputUI.displayMessage("Editing job: " + job.getTitle());

        // Title
        String title = inputUI.getInput("Enter New Title (or press Enter to keep: " + job.getTitle() + "): ");
        if (!title.isEmpty()) job.setTitle(title);

        // Location
        String location = inputUI.getInput("Enter New Location (or press Enter to keep: " + job.getLocation() + "): ");
        if (!location.isEmpty()) job.setLocation(location);

        // Required Experience
        String expInput = inputUI.getInput("Enter New Required Experience (or press Enter to keep: " + job.getRequired_experience() + "): ");
        if (!expInput.isEmpty()) {
            try {
                int exp = Integer.parseInt(expInput);
                job.setRequired_experience(exp);
            } catch (NumberFormatException e) {
                inputUI.displayMessage("Invalid experience input. Keeping existing value.");
            }
        }

        // Salary
        String salaryInput = inputUI.getInput("Enter New Salary (or press Enter to keep: " + job.getSalary() + "): ");
        if (!salaryInput.isEmpty()) {
            try {
                float salary = Float.parseFloat(salaryInput);
                job.setSalary(salary);
            } catch (NumberFormatException e) {
                inputUI.displayMessage("Invalid salary input. Keeping existing value.");
            }
        }

        inputUI.displayMessage("Job updated successfully!");
    }
    
    // ----------------- DELETE -----------------
    public void removeJob() {
        inputUI.displayMessage("Enter Job ID to remove:");
        String jobId = inputUI.getInput("Job ID: ");

        Job job = findJobById(jobId);
        if (job == null) return;

        int index = getJobIndex(job);
        if (index == -1) {
            inputUI.displayMessage("Job not found in the list.\n");
            return;
        }

        jobList.remove(index);
        inputUI.displayMessage("Job " + jobId + " removed successfully.\n");
    }

    // ----------------- HELPER -----------------
    private int getJobIndex(Job job) {
        int index = 0;
        for (Job j : jobList) {
            if (j.getJobId().equals(job.getJobId())) {
                return index;
            }
            index++;
        }
        return -1; // Not found
    }

    public boolean isEmpty() {
        return jobList.isEmpty();
    }
    
    public void addJobWithRequirements() {
        inputUI.displayMessage("\n===== Add New Job (with Requirements) =====");

        String title = inputUI.getInput("Enter Job Title: ");
        String location = inputUI.getInput("Enter Job Location: ");
        int requiredExperience = inputUI.getIntInput("Enter Required Experience (years): ", 0, 100);
        float salary = inputUI.getValidFloatInput("Enter Salary (min: 0): ", 0, Float.MAX_VALUE);

        // Create job requirements list
        DoublyLinkedListInterface<JobRequirements> jobRequirements = new DoublyLinkedList<>();

        boolean addingRequirements = true;
        while (addingRequirements) {
            inputUI.displayMessage("\n--- Add Job Requirement ---");

            String reqName = inputUI.getInput("Requirement Name: ");
            String proficiencyLevel = inputUI.getInput("Proficiency Level: ");
            String category = inputUI.getInput("Category: ");

            jobRequirements.add(new JobRequirements(reqName, proficiencyLevel, category));
            inputUI.displayMessage("Requirement added successfully!\n");

            String more = inputUI.getInput("Add another requirement? (y/n): ");
            if (!more.equalsIgnoreCase("y")) {
                addingRequirements = false;
            }
        }

        Job newJob = new Job(title, location, requiredExperience, jobRequirements, salary);
        jobList.add(newJob);

        inputUI.displayMessage("Job added successfully! Job ID is: " + newJob.getJobId() + "\n");
    }
}

