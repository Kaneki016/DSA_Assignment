package controller;

import adt.DoublyLinkedList;
import adt.DoublyLinkedListInterface;
import entities.*;
import boundary.*;
import java.time.LocalDateTime;
import controller.JobRequirementsManager;


public class JobManager {
    
    // Menu UI
    private static InputUI inputUI = new InputUI();
    private static MenuUI menuUI = new MenuUI();
    private static JobRequirementsManager jobRequirementsManager = JobRequirementsManager.getInstance();

    // Singleton instance
    private static JobManager instance;
    
    // Data structure to hold Jobs
    private DoublyLinkedListInterface<Job> jobList;
    private DoublyLinkedListInterface<Job> removedJobs;

    // Private constructor for Singleton
    private JobManager() {
        jobList = new DoublyLinkedList<>();
        removedJobs = new DoublyLinkedList<>();
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

        // ---------- Job Title and Details ----------
        String title;
        while (true) {
            title = inputUI.getInput("Enter Job Title (Enter X to cancel): ");
            if (title.equalsIgnoreCase("x")) {
                inputUI.displayMessage("Job creation cancelled.\n");
                return;
            }
            if (!title.trim().isEmpty()) {
                break;
            }
            inputUI.displayMessage("Title cannot be empty. Please try again.");
        }

        String location;
        while (true) {
            location = inputUI.getInput("Enter Job Location (Enter X to cancel): ");
            if (location.equalsIgnoreCase("x")) {
                inputUI.displayMessage("Job creation cancelled.\n");
                return;
            }
            if (!location.trim().isEmpty()) {
                break;
            }
            inputUI.displayMessage("Location cannot be empty. Please try again.");
        }

        int requiredExperience;
        while (true) {
            requiredExperience = inputUI.getIntInput("Enter Required Experience (years) (Enter -1 to cancel): ", -1, 100);
            if (requiredExperience == -1) {
                inputUI.displayMessage("Job creation cancelled.\n");
                return;
            }
            break;
        }

        float salary;
        while (true) {
            salary = inputUI.getValidFloatInput("Enter Salary (min: 0, or -1 to cancel): ", -1, Float.MAX_VALUE);
            if (salary == -1) {
                inputUI.displayMessage("Job creation cancelled.\n");
                return;
            }
            break;
        }

        // ---------- Job Requirements Selection / Creation ----------
        JobRequirementsManager jrManager = JobRequirementsManager.getInstance();
        DoublyLinkedListInterface<JobRequirements> globalRequirements = jrManager.getAllRequirements();
        DoublyLinkedListInterface<JobRequirements> selectedRequirements = new DoublyLinkedList<>();

        boolean addingRequirements = true;
        while (addingRequirements) {
            inputUI.displayMessage("\n--- Available Job Requirements ---");

            if (globalRequirements.isEmpty()) {
                inputUI.displayMessage("No existing requirements available.\n");
            } else {
                menuUI.printJobRequirements(globalRequirements); // Using the method to print requirements
            }

            String choice = inputUI.getInput("\nSelect a requirement by Job ID, 'new' to add a new requirement, or 'X' to finish: ");

            if (choice.equalsIgnoreCase("x")) {
                addingRequirements = false;
            } else if (choice.equalsIgnoreCase("new")) {
                jobRequirementsManager.resetNextIdFromList();
                
                String name = inputUI.getInput("Enter Requirement Name: ");

                String proficiency;
                while (true) {
                    proficiency = inputUI.getInput("Enter Proficiency Level (1-5): ");
                    if (proficiency.matches("[1-5]")) break;
                    inputUI.displayMessage("Invalid input. Please enter a number from 1 to 5.");
                }

                String category = inputUI.getInput("Enter Category: ");
                JobRequirements newReq = new JobRequirements(name, proficiency, category);
                selectedRequirements.add(newReq);  // Add to this job
                globalRequirements.add(newReq);    // Add globally

                inputUI.displayMessage("Requirement added successfully! Requirement ID: " + newReq.getJobRequirementId() + "\n");

                String continueChoice = inputUI.getInput("Continue to add requirement? (Y/N): ");
                if (continueChoice.equalsIgnoreCase("n")) {
                    addingRequirements = false;
                }
            } else {
                JobRequirements selectedReq = null;
                for (JobRequirements req : globalRequirements) {
                    if (req.getJobRequirementId().equalsIgnoreCase(choice)) {
                        selectedReq = req;
                        break;
                    }
                }

                if (selectedReq != null) {
                    selectedRequirements.add(selectedReq);
                    inputUI.displayMessage("Added requirement: " + selectedReq.getJobRequirementId() + "\n");
                } else {
                    inputUI.displayMessage("Requirement ID not found. Please try again.\n");
                }
            }
        }

        // ---------- Create and Add Job ----------
        Job newJob = new Job(title, location, requiredExperience, selectedRequirements, salary);
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

        // Print the jobs using the existing printJobs method
        menuUI.printJobs(jobList);

        // Prompt the user to continue
        inputUI.getInput("Press Enter to continue...");
    }

    public Job findJobById(String jobId) {
        for (Job job : jobList) {
            if (job.getJobId().equalsIgnoreCase(jobId)) {
                return job;
            }
        }
        inputUI.displayMessage("Job with ID " + jobId + " not found.\nPlease try again.\n");
        return null;
    }
    
    // ----------------- UPDATE -----------------
    public void editJob() {
        inputUI.displayMessage("\n===== Edit Job =====");
        menuUI.printJobs(jobList);
        Job job = null;
        while (true) {
            String jobId = inputUI.getInput("Enter Job ID to edit (Enter X to cancel): ");
            if (jobId.equalsIgnoreCase("x")) {
                inputUI.displayMessage("Edit cancelled.\n");
                return;
            }

            job = findJobById(jobId);
            if (job != null) {
                break;
            }
        }

        inputUI.displayMessage("Editing job: " + job.getTitle());

        // Title
        String title = inputUI.getInput("Enter New Title (or press Enter to keep: " + job.getTitle() + "): ");
        if (!title.trim().isEmpty()) {
            job.setTitle(title);
        }

        // Location
        String location = inputUI.getInput("Enter New Location (or press Enter to keep: " + job.getLocation() + "): ");
        if (!location.trim().isEmpty()) {
            job.setLocation(location);
        }

        // Required Experience
        while (true) {
            String expInput = inputUI.getInput("Enter New Required Experience (or press Enter to keep: " + job.getRequired_experience() + "): ");
            if (expInput.isEmpty()) {
                break;
            }

            try {
                int exp = Integer.parseInt(expInput);
                job.setRequired_experience(exp);
                break;
            } catch (NumberFormatException e) {
                inputUI.displayMessage("Invalid input. Please enter a valid number or leave empty to skip.");
            }
        }

        // Salary
        while (true) {
            String salaryInput = inputUI.getInput("Enter New Salary (or press Enter to keep: " + job.getSalary() + "): ");
            if (salaryInput.isEmpty()) {
                break;
            }

            try {
                float salary = Float.parseFloat(salaryInput);
                job.setSalary(salary);
                break;
            } catch (NumberFormatException e) {
                inputUI.displayMessage("Invalid salary. Please enter a valid number or leave empty to skip.");
            }
        }

        // ----------------- Edit Job Requirements -----------------
        JobRequirementsManager jrManager = JobRequirementsManager.getInstance();
        DoublyLinkedListInterface<JobRequirements> jobRequirements = job.getJobRequirements();
        DoublyLinkedListInterface<JobRequirements> globalRequirements = jrManager.getAllRequirements();

        boolean editingRequirements = true;
        while (editingRequirements) {
            inputUI.displayMessage("\n--- Current Job Requirements ---");
            if (jobRequirements.isEmpty()) {
                inputUI.displayMessage("No requirements assigned.\n");
            } else {
                menuUI.printJobRequirements(jobRequirements); // Use the same method for printing requirements
            }

            String choice = inputUI.getInput("\nEnter 'select(JRxxx)', 'remove(JRxxx)', 'new', or 'X': ");

            if (choice.equalsIgnoreCase("x")) {
                editingRequirements = false;
            } else if (choice.equalsIgnoreCase("new")) {
                String name = inputUI.getInput("Enter Requirement Name: ");

                String proficiency;
                while (true) {
                    proficiency = inputUI.getInput("Enter Proficiency Level (1-5): ");
                    if (proficiency.matches("[1-5]")) break;
                    inputUI.displayMessage("Invalid input. Please enter a number from 1 to 5.");
                }

                String category = inputUI.getInput("Enter Category: ");
                JobRequirements newReq = new JobRequirements(name, proficiency, category);
                jobRequirements.add(newReq);
                globalRequirements.add(newReq);
                inputUI.displayMessage("Requirement added successfully! ID: " + newReq.getJobRequirementId());

            } else if (choice.toLowerCase().startsWith("select(") && choice.endsWith(")")) {
                String reqId = choice.substring(7, choice.length() - 1).trim();
                JobRequirements toAdd = null;
                for (JobRequirements req : globalRequirements) {
                    if (req.getJobRequirementId().equalsIgnoreCase(reqId)) {
                        toAdd = req;
                        break;
                    }
                }

                if (toAdd != null) {
                    boolean alreadyExists = false;
                    for (JobRequirements existing : jobRequirements) {
                        if (existing.getJobRequirementId().equalsIgnoreCase(reqId)) {
                            alreadyExists = true;
                            break;
                        }
                    }

                    if (alreadyExists) {
                        inputUI.displayMessage("Requirement " + reqId + " is already assigned to the job.\n");
                    } else {
                        jobRequirements.add(toAdd);
                        inputUI.displayMessage("Requirement " + reqId + " selected and added to job.\n");
                    }
                } else {
                    inputUI.displayMessage("Requirement ID not found in global list.\n");
                }
                
            } else if (choice.toLowerCase().startsWith("remove(") && choice.endsWith(")")) {
            while (true) {
                String reqId = choice.substring(7, choice.length() - 1).trim();
                JobRequirements toRemove = null;
                int indexToRemove = -1;
                int index = 0;
                for (JobRequirements req : jobRequirements) {
                    if (req.getJobRequirementId().equalsIgnoreCase(reqId)) {
                        toRemove = req;
                        indexToRemove = index;
                        break;
                    }
                    index++;
                }

                if (toRemove != null && indexToRemove != -1) {
                    jobRequirements.remove(indexToRemove);
                    inputUI.displayMessage("Requirement " + reqId + " removed from job.\n");
                    break;
                } else {
                    inputUI.displayMessage("Requirement ID not found in this job. Try again.");
                    choice = inputUI.getInput("Enter 'remove(JRxxx)', or 'X' to cancel removal: ");
                    if (choice.equalsIgnoreCase("x")) break;
                    if (!choice.toLowerCase().startsWith("remove(") || !choice.endsWith(")")) break;
                }
            }

        }else {
                inputUI.displayMessage("Invalid command. Try again.\n");
            }
        }
        inputUI.displayMessage("Job updated successfully!");
    }
    
    // ----------------- HELPER -----------------
    public boolean isEmpty() {
        return jobList.isEmpty();
    }

}

