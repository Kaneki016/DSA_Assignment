package controller;

import adt.DoublyLinkedList;
import adt.DoublyLinkedListInterface;
import boundary.*;
import entities.JobRequirements;
import java.time.LocalDateTime;

public class JobRequirementsManager {

    private static JobRequirementsManager instance;
    private DoublyLinkedListInterface<JobRequirements> jobRequirementsList;
    private DoublyLinkedListInterface<JobRequirements> removedRequirements;
    private InputUI inputUI;
    private MenuUI menuUI;

    // Singleton pattern
    private JobRequirementsManager() {
        jobRequirementsList = new DoublyLinkedList<>();
        removedRequirements = new DoublyLinkedList<>();
        inputUI = new InputUI();
        menuUI = new MenuUI();
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
        resetNextIdFromList();

        String name;
        while (true) {
            name = inputUI.getInput("Enter Requirement Name (or X to cancel): ");
            if (name.equalsIgnoreCase("x")) {
                inputUI.displayMessage("Job requirement creation cancelled.\n");
                return;
            }
            if (!name.trim().isEmpty()) break;
            inputUI.displayMessage("Requirement Name cannot be empty. Please try again.");
        }

        String proficiencyLevel;
        while (true) {
            proficiencyLevel = inputUI.getInput("Enter Proficiency Level (Beginner, Intermediate, Expert or X to cancel): ");
            if (proficiencyLevel.equalsIgnoreCase("x")) {
                inputUI.displayMessage("Job requirement creation cancelled.\n");
                return;
            }
            if (proficiencyLevel.equalsIgnoreCase("Beginner") ||
                proficiencyLevel.equalsIgnoreCase("Intermediate") ||
                proficiencyLevel.equalsIgnoreCase("Expert")) {
                break;
            }
            inputUI.displayMessage("Invalid proficiency level. Please enter Beginner, Intermediate, or Expert.");
        }

        String category;
        while (true) {
            category = inputUI.getInput("Enter Category (Technical, Soft Skill or X to cancel): ");
            if (category.equalsIgnoreCase("x")) {
                inputUI.displayMessage("Job requirement creation cancelled.\n");
                return;
            }
            if (category.equalsIgnoreCase("Technical") || category.equalsIgnoreCase("Soft Skill")) {
                break;
            }
            inputUI.displayMessage("Invalid category. Please enter Technical or Soft Skill.");
        }

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

        if (jobRequirementsList.isEmpty()) {
            inputUI.displayMessage("No job requirements available.\n");
            return;
        }

        menuUI.printJobRequirements(jobRequirementsList); // Calls the table-based view
        inputUI.getInput("Press Enter to continue...");
    }
  
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
        menuUI.printJobRequirements(jobRequirementsList); // Calls the table-based view
        JobRequirements requirement = null;

        while (true) {
            String requirementId = inputUI.getInput("Enter Job Requirement ID to edit (or X to cancel): ");
            if (requirementId.equalsIgnoreCase("x")) {
                inputUI.displayMessage("Job requirement editing cancelled.\n");
                return;
            }

            requirement = findJobRequirementById(requirementId);
            if (requirement != null) break;
        }

        inputUI.displayMessage("Editing Job Requirement: " + requirement.getName());

        String newName = inputUI.getInput("Enter New Requirement Name (or press Enter to keep: " + requirement.getName() + "): ");
        if (!newName.isEmpty()) requirement.setName(newName);

        String newProficiencyLevel = inputUI.getInput("Enter New Proficiency Level (Beginner, Intermediate, Expert or press Enter to keep: " + requirement.getProficiencyLevel() + "): ");
        if (!newProficiencyLevel.isEmpty()) requirement.setProficiencyLevel(newProficiencyLevel);

        String newCategory = inputUI.getInput("Enter New Category (Technical, Soft Skill or press Enter to keep: " + requirement.getCategory() + "): ");
        if (!newCategory.isEmpty()) requirement.setCategory(newCategory);

        inputUI.displayMessage("Job Requirement updated successfully!\n");
    }
    
    // -------------------- REMOVE --------------------
    public void removeJobRequirement() {
        inputUI.displayMessage("\n===== Remove Job Requirement =====");
        menuUI.printJobRequirements(jobRequirementsList); // Calls the table-based view

        JobRequirements requirement = null;

        while (true) {
            String requirementId = inputUI.getInput("Enter Job Requirement ID to remove (or X to cancel): ");
            if (requirementId.equalsIgnoreCase("x")) {
                inputUI.displayMessage("Job requirement removal cancelled.\n");
                return;
            }

            requirement = findJobRequirementById(requirementId);
            if (requirement != null) break;
        }

        int index = getRequirementIndex(requirement);
        if (index == -1) {
            inputUI.displayMessage("Job Requirement not found in the list.\n");
            return;
        }

        // Mark requirement as removed
        requirement.setRemovedAt(LocalDateTime.now());
        jobRequirementsList.remove(index);
        removedRequirements.add(requirement);

        inputUI.displayMessage("Job Requirement " + requirement.getJobRequirementId() + " removed successfully.\n");
    }

    
    public void displayRemovedReq() {
        inputUI.displayMessage("\n===== Removed Job Requirements =====");

        if (removedRequirements.isEmpty()) {
            inputUI.displayMessage("No removed job requirements found.\n");
            return;
        }

        // Use MenuUI to print in tabular format
        menuUI.printRemovedJobRequirements(removedRequirements);

        // Prompt user to continue
        inputUI.getInput("Press Enter to continue...");
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
    
    public void resetNextIdFromList() {
        int maxId = 0;
        for (JobRequirements req : jobRequirementsList) {
            String id = req.getJobRequirementId(); // e.g., JR015
            try {
                int numeric = Integer.parseInt(id.substring(2));
                if (numeric > maxId) maxId = numeric;
            } catch (NumberFormatException e) {
                // Skip invalid IDs
            }
        }
        JobRequirements.setNextId(maxId + 1);
    }
}

