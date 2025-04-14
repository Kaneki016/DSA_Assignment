package controller;

import adt.DoublyLinkedList;
import adt.DoublyLinkedListInterface;
import boundary.InputUI;
import entities.JobRequirements;

public class JobRequirementsManager {

    private static JobRequirementsManager instance;
    private DoublyLinkedListInterface<JobRequirements> jobRequirementsList;
    private InputUI inputUI;

    // Singleton pattern
    private JobRequirementsManager() {
        jobRequirementsList = new DoublyLinkedList<>();
        inputUI = new InputUI();
    }

    public static JobRequirementsManager getInstance() {
        if (instance == null) {
            instance = new JobRequirementsManager();
        }
        return instance;
    }
    
    // -------------------- ADD --------------------
    public void addJobRequirement() {
        inputUI.displayMessage("\n===== Add New Job Requirement =====");

        String name = inputUI.getInput("Enter Requirement Name: ");
        String proficiencyLevel = inputUI.getInput("Enter Proficiency Level (e.g., Beginner, Intermediate, Expert): ");
        String category = inputUI.getInput("Enter Category (e.g., Technical, Soft Skill): ");

        JobRequirements newRequirement = new JobRequirements(name, proficiencyLevel, category);
        jobRequirementsList.add(newRequirement);

        inputUI.displayMessage("Job Requirement added successfully!\n");
    }

    public void addJobRequirement(JobRequirements requirement) {
        jobRequirementsList.add(requirement);
    }
    
    // -------------------- DISPLAY --------------------
    public void displayJobRequirements() {
        inputUI.displayMessage("\n===== Job Requirements List =====");

        if (isEmpty()) {
            inputUI.displayMessage("No job requirements found.\n");
            return;
        }

        int index = 1;
        for (JobRequirements requirement : jobRequirementsList) {
            inputUI.displayMessage(index + ". " + requirement);
            index++;
        }
    }

   /* // -------------------- SELECT MULTIPLE REQUIREMENTS --------------------
    public DoublyLinkedListInterface<JobRequirements> selectMultipleRequirements() {
        DoublyLinkedListInterface<JobRequirements> selectedRequirements = new DoublyLinkedList<>();

        // If there are no job requirements, force the user to add at least one
        if (isEmpty()) {
            inputUI.displayMessage("No job requirements available. Please add at least one requirement.");
            addJobRequirement();
        }

        boolean done = false;
        while (!done) {
            displayJobRequirements();
            int choice = inputUI.getIntInput("Select a requirement by index: ", 1, getSize());

            JobRequirements selectedRequirement = jobRequirementsList.getEntry(choice - 1);
            if (selectedRequirement != null) {
                selectedRequirements.add(selectedRequirement);
                inputUI.displayMessage("Added: " + selectedRequirement.getName());
            }

            String more = inputUI.getInput("Do you want to add another requirement? (Y/N): ");
            if (!more.equalsIgnoreCase("Y")) {
                done = true;
            }

            String addNew = inputUI.getInput("Do you want to create a new job requirement? (Y/N): ");
            if (addNew.equalsIgnoreCase("Y")) {
                addJobRequirement();
            }
        }

        return selectedRequirements;
    }*/
    
    // -------------------- FIND --------------------
    public JobRequirements findJobRequirementById(String requirementId) {
        for (JobRequirements requirement : jobRequirementsList) {
            if (requirement.getJobRequirementId().equalsIgnoreCase(requirementId)) {
                return requirement;
            }
        }
        inputUI.displayMessage("Job Requirement with ID " + requirementId + " not found.\n");
        return null;
    }

    // -------------------- EDIT --------------------
    public void editJobRequirement() {
        inputUI.displayMessage("\n===== Edit Job Requirement =====");

        String requirementId = inputUI.getInput("Enter Job Requirement ID to edit: ");
        JobRequirements requirement = findJobRequirementById(requirementId);

        if (requirement == null) {
            inputUI.displayMessage("Job Requirement not found!");
            return;
        }

        inputUI.displayMessage("Editing Job Requirement: " + requirement.getName());

        String newName = inputUI.getInput("Enter New Requirement Name (or press Enter to keep: " + requirement.getName() + "): ");
        if (!newName.isEmpty()) requirement.setName(newName);

        String newProficiencyLevel = inputUI.getInput("Enter New Proficiency Level (or press Enter to keep: " + requirement.getProficiencyLevel() + "): ");
        if (!newProficiencyLevel.isEmpty()) requirement.setProficiencyLevel(newProficiencyLevel);

        String newCategory = inputUI.getInput("Enter New Category (or press Enter to keep: " + requirement.getCategory() + "): ");
        if (!newCategory.isEmpty()) requirement.setCategory(newCategory);

        inputUI.displayMessage("Job Requirement updated successfully!");
    }
    
    // -------------------- REMOVE --------------------
    public void removeJobRequirement() {
        inputUI.displayMessage("\n===== Remove Job Requirement =====");

        String requirementId = inputUI.getInput("Enter Job Requirement ID to remove: ");
        JobRequirements requirement = findJobRequirementById(requirementId);

        if (requirement == null) return;

        int index = getRequirementIndex(requirement);

        if (index == -1) {
            inputUI.displayMessage("Job Requirement not found in the list.\n");
            return;
        }

        jobRequirementsList.remove(index);
        inputUI.displayMessage("Job Requirement " + requirementId + " removed successfully.\n");
    }

    // -------------------- UTILITY METHODS --------------------
    private int getRequirementIndex(JobRequirements requirement) {
        int index = 0;
        for (JobRequirements r : jobRequirementsList) {
            if (r.getJobRequirementId().equals(requirement.getJobRequirementId())) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public boolean isEmpty() {
        return jobRequirementsList.isEmpty();
    }

    public int getSize() {
        return jobRequirementsList.getSize();
    }

    public DoublyLinkedListInterface<JobRequirements> getAllRequirements() {
        return jobRequirementsList;
    }

    public void replaceRequirement(int index, JobRequirements newRequirement) {
        jobRequirementsList.replace(index, newRequirement);
    }
}

