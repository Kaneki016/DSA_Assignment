/*Author: Wong Shu Han*/
package controller;

import adt.DoublyLinkedList;
import adt.DoublyLinkedListInterface;
import boundary.*;
import entities.*;
import controller.*;
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
            proficiencyLevel = inputUI.getInput("Enter Proficiency Level (1-5) or X to cancel): ");
            if (proficiencyLevel.equalsIgnoreCase("x")) {
                inputUI.displayMessage("Job requirement creation cancelled.\n");
                return;
            }
            if (proficiencyLevel.matches("[1-5]")) break;
            inputUI.displayMessage("Invalid value. Please enter between '1' and '5'.");
        }

        String category;
        while (true) {
            category = inputUI.getInput("Enter Category (or X to cancel): ");
            if (category.equalsIgnoreCase("x")) {
                inputUI.displayMessage("Job requirement creation cancelled.\n");
                return;
            }
            if (!category.trim().isEmpty()) break;
            inputUI.displayMessage("Category cannot be empty. Please try again.");
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
        inputUI.displayMessage("Job Requirement with ID " + requirementId + " not found.");
        return null;
    }

    // -------------------- EDIT --------------------
    public void editJobRequirement() {
        inputUI.displayMessage("\n===== Edit Job Requirement =====");
        menuUI.printJobRequirements(jobRequirementsList); 
        JobRequirements requirement = null;

        while (true) {
            String requirementId = inputUI.getInput("Enter Job Requirement ID to edit (or X to cancel): ");
            if (requirementId.equalsIgnoreCase("x")) {
                inputUI.displayMessage("Job requirement editing cancelled.\n");
                return;
            }

            requirement = findJobRequirementById(requirementId);
            if (requirement == null) {
               inputUI.displayMessage("Please try again.\n");

            } else {
                break;
            }
        }

        inputUI.displayMessage("Editing Job Requirement: " + requirement.getName());

        String newName = inputUI.getInput("Enter New Requirement Name (or press Enter to keep: " + requirement.getName() + "): ");
        if (!newName.trim().isEmpty()) requirement.setName(newName);

        while (true) {
            String newProficiencyLevel = inputUI.getInput("Enter New Proficiency Level (1-5 as string or press Enter to keep: " + requirement.getProficiencyLevel() + "): ");
            if (newProficiencyLevel.isEmpty()) break;
            if (newProficiencyLevel.matches("[1-5]")) {
                requirement.setProficiencyLevel(newProficiencyLevel);
                break;
            } else {
                inputUI.displayMessage("Invalid value. Please enter between '1' and '5', or press Enter to keep the current value.");
            }
        }

        String newCategory = inputUI.getInput("Enter New Category (or press Enter to keep: " + requirement.getCategory() + "): ");
        if (!newCategory.trim().isEmpty()) requirement.setCategory(newCategory);

        inputUI.displayMessage("Job Requirement updated successfully!\n");
    }

    // -------------------- UTILITY METHODS --------------------

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

