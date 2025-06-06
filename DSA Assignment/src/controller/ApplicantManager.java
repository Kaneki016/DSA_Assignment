package controller;

import adt.*;
import entities.Applicant;
import entities.ApplicantAppliedJob;

import java.util.Scanner;
import boundary.*;
import entities.Skill;
import java.util.Comparator;

public class ApplicantManager {

    private static ApplicantManager instance;
    
    //Controller
    ApplicantAppliedJobManager applicantAppliedJobManager = ApplicantAppliedJobManager.getInstance();

    // ADT
    private DoublyLinkedListInterface<Applicant> applicants; // Custom DoublyLinkedList to store applicants

    // Boundary
    private Scanner scanner = new Scanner(System.in);
    private InputUI inputUI = new InputUI();
    private MenuUI menuUI = new MenuUI();

    public ApplicantManager() {
        applicants = new DoublyLinkedList<>();
    }

    // Singleton accessor
    public static ApplicantManager getInstance() {
        if (instance == null) {
            instance = new ApplicantManager();
        }
        return instance;
    }

    // Add an applicant
    public void addApplicant() {
        inputUI.displayMessage("\n ADD NEW APPLICANT");
        inputUI.displayMessage("(You can type 'back' or -1 at any time to cancel this entry.)");

        String name = inputUI.getInput("Enter Name: ");
        if (name.equalsIgnoreCase("back")) {
            return;
        }

        int age = inputUI.getIntInputWithBackOption("Enter Age", 18, 100, -1);
        if (age == -1) {
            return;
        }

        String location = inputUI.getInput("Enter Location: ");
        if (location.equalsIgnoreCase("back")) {
            return;
        }

        int yearsOfExperience = inputUI.getIntInputWithBackOption("Enter Years of Experience", 0, 50, -1);
        if (yearsOfExperience == -1) {
            return;
        }

        String educationLevel = inputUI.getInput("Enter Education Level: ");
        if (educationLevel.equalsIgnoreCase("back")) {
            return;
        }

        // Add Skill - First skill is mandatory
        DoublyLinkedListInterface<Skill> skills = new DoublyLinkedList<>();
        inputUI.displayMessage("\nEnter at least one skill:");

        String skill = inputUI.getInput("Your skill: ");
        if (skill.equalsIgnoreCase("back")) {
            return;
        }

        String category = inputUI.getInput("The category of it: ");
        if (category.equalsIgnoreCase("back")) {
            return;
        }

        int proficiency_level = inputUI.getIntInputWithBackOption("Your proficiency level of it (1-5)", 1, 5, -1);
        if (proficiency_level == -1) {
            return;
        }

        skills = addApplicantSkill(skills, skill, category, proficiency_level);
        inputUI.displayMessage(" First skill added successfully!");

        // Additional skills
        int choice = 1;
        while (choice == 1) {
            choice = inputUI.getIntInput("\nAdd another skill? (1 = yes, 0 = no): ", 0, 1);

            if (choice == 1) {
                skill = inputUI.getInput("Your skill: ");
                if (skill.equalsIgnoreCase("back")) {
                    break;
                }

                category = inputUI.getInput("The category of it: ");
                if (category.equalsIgnoreCase("back")) {
                    break;
                }

                proficiency_level = inputUI.getIntInput("Your proficiency level of it (1-5): (or -1 to cancel)", 1, 5);
                if (proficiency_level == -1) {
                    break;
                }

                skills = addApplicantSkill(skills, skill, category, proficiency_level);
                inputUI.displayMessage(" Skill added successfully!");
            }
        }

        // Final confirmation before adding
        inputUI.displayMessage("\nDo you want to save this applicant? (yes/no)");
        String confirm = inputUI.getInput("Confirm: ");
        if (!confirm.equalsIgnoreCase("yes")) {
            inputUI.displayMessage("Applicant entry cancelled.");
            return;
        }

        // Create and add the applicant
        Applicant newApplicant = new Applicant(name, age, location, yearsOfExperience, educationLevel, skills);
        applicants.add(newApplicant);
        inputUI.displayMessage("\n Applicant added successfully!");

        // Display the added applicant
        menuUI.printApplicantTableHeader();
        menuUI.printApplicantRow(newApplicant);
        menuUI.printApplicantsTableFooter();
        inputUI.getInput("<< Press \"Enter\" to continue >>");
    }

    // add applicant ( mock data)
    public void addApplicant(Applicant applicant) {
        applicants.add(applicant);
    }

    // Add Applicant Skill
    public DoublyLinkedListInterface<Skill> addApplicantSkill(DoublyLinkedListInterface<Skill> skills, String skill,
            String category, int proficiency_level) {
        Skill newSkill = new Skill(skill, category, proficiency_level);
        skills.add(newSkill);
        return skills;
    }

    // Remove an applicant by ID
    public void removeApplicant() {
        String id = inputUI.getInput("Enter the Applicant ID to remove (or type 'back' to return):").trim();

        if (id.equalsIgnoreCase("back")) {
            inputUI.displayMessage("Returning to previous menu...");
            return;
        }

        if (id.isEmpty()) {
            inputUI.displayMessage("Applicant ID cannot be empty!");
            return;
        }

        Applicant toRemove = findApplicantById(id);

        if (toRemove == null) {
            inputUI.displayMessage("Applicant not found!\n");
            return;
        }

        // Check if this applicant is associated with any applied jobs
       
        DoublyLinkedListInterface<ApplicantAppliedJob> appliedJobs = applicantAppliedJobManager
                .getApplicantAppliedJobs();

        for (int i = 0; i < appliedJobs.size(); i++) {
            ApplicantAppliedJob appliedJob = appliedJobs.get(i);
            if (appliedJob.getApplicant().getApplicantId().equalsIgnoreCase(id)) {
                inputUI.displayMessage("Cannot remove this applicant. He has applied to a job.\n");
                return;
            }
        }

        // Show applicant details before deletion
        menuUI.printApplicantTableHeader();
        menuUI.printApplicantRow(toRemove);
        menuUI.printApplicantsTableFooter();

        String confirm = inputUI.getInput("Are you sure you want to remove this applicant? (yes/no): ").trim();
        if (confirm.equalsIgnoreCase("yes")) {
            applicants.removeSpecific(toRemove);
            inputUI.displayMessage("Applicant " + id + " has been removed.\n");
        } else {
            inputUI.displayMessage("Removal cancelled.");
        }
    }

    // Find and return applicant by ID
    public Applicant findApplicantById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }

        for (Applicant applicant : applicants) {
            if (applicant.getApplicantId().equalsIgnoreCase(id.trim())) {
                return applicant;
            }
        }

        return null;
    }

    // Prompt and search for applicant by ID (includes printing logic)
    public Applicant searchApplicantById() {
        while (true) {
            String id = inputUI.getInput("Enter Applicant ID to search (or type 'back' to return): ").trim();

            if (id.equalsIgnoreCase("back")) {
                inputUI.displayMessage("Returning to previous menu...");
                return null;
            }

            if (id.isEmpty()) {
                inputUI.displayMessage(" Applicant ID cannot be empty!");
                continue;
            }

            Applicant foundApplicant = findApplicantById(id);

            if (foundApplicant != null) {
                inputUI.displayMessage("\n Applicant Found:");
                menuUI.printApplicantTableHeader();
                menuUI.printApplicantRow(foundApplicant);
                menuUI.printApplicantsTableFooter();
                inputUI.getInput("<< Press \"Enter\" to continue >>");
                return foundApplicant;
            } else {
                inputUI.displayMessage(" Applicant not found. Please try again.");
                inputUI.getInput("<< Press \"Enter\" to retry >>");
            }
        }
    }

    // Check if an applicant exists
    public boolean isValidApplicant(String applicantId) {
        return findApplicantById(applicantId) != null;
    }

    // Display all applicants
    public void displayAllApplicants() {
        inputUI.displayMessage("\n=============== List of Applicants ===============");
        menuUI.printApplicants(applicants);
        inputUI.getInput("<< Press \"Enter\" to continue >>");
    }

    // Edit applicant profile
    public void editApplicantProfile() {
        inputUI.displayMessage("\n=== Edit Applicant Profile ===");
        String applicantId = inputUI.getInput("Enter your Applicant ID (or type 'back' to return):").trim();

        // Allow user to return to previous menu
        if (applicantId.equalsIgnoreCase("back")) {
            return;
        }

        Applicant applicant = findApplicantById(applicantId);
        if (applicant == null) {
            inputUI.displayMessage(" Applicant not found!\n");
            return;
        }

        updateApplicantDetails(applicant);

    }

    private void updateApplicantDetails(Applicant applicant) {
        int choice;
        boolean isUpdated = false; // Track if any change is made

        do {
            inputUI.displayMessage("\nSelect the field to edit:");
            inputUI.displayMessage("1. Edit Name");
            inputUI.displayMessage("2. Edit Age");
            inputUI.displayMessage("3. Edit Location");
            inputUI.displayMessage("4. Edit Years of Experience");
            inputUI.displayMessage("5. Edit Education Level");
            inputUI.displayMessage("6. Edit Skill");
            inputUI.displayMessage("7. Back");

            choice = inputUI.getIntInput("Select an option: ", 1, 7);

            switch (choice) {
                case 1:
                    String name = inputUI.getInput("Enter new name [Current: " + applicant.getName() + "]: ").trim();
                    if (!name.isEmpty() && !name.equals(applicant.getName())) {
                        applicant.setName(name);
                        inputUI.displayMessage(" Name updated.");
                        isUpdated = true;
                    }
                    break;

                case 2:
                    int age = inputUI.getIntInput("Enter new age [Current: " + applicant.getAge() + "]: ", 18, 100);
                    if (age != applicant.getAge()) {
                        applicant.setAge(age);
                        inputUI.displayMessage(" Age updated.");
                        isUpdated = true;
                    }
                    break;

                case 3:
                    String location = inputUI
                            .getInput("Enter new location [Current: " + applicant.getLocation() + "]: ").trim();
                    if (!location.isEmpty() && !location.equalsIgnoreCase(applicant.getLocation())) {
                        applicant.setLocation(location);
                        inputUI.displayMessage(" Location updated.");
                        isUpdated = true;
                    }
                    break;

                case 4:
                    int exp = inputUI.getIntInput(
                            "Enter new years of experience [Current: " + applicant.getYearsOfExperience() + "]: ", 0,
                            50);
                    if (exp != applicant.getYearsOfExperience()) {
                        applicant.setYearsOfExperience(exp);
                        inputUI.displayMessage(" Experience updated.");
                        isUpdated = true;
                    }
                    break;

                case 5:
                    String edu = inputUI
                            .getInput("Enter new education level [Current: " + applicant.getEducationLevel() + "]: ")
                            .trim();
                    if (!edu.isEmpty() && !edu.equalsIgnoreCase(applicant.getEducationLevel())) {
                        applicant.setEducationLevel(edu);
                        inputUI.displayMessage(" Education level updated.");
                        isUpdated = true;
                    }
                    break;

                case 6:
                    int prevSkillSize = applicant.getSkills().size();
                    handleSkillEditing(applicant.getSkills());
                    int newSkillSize = applicant.getSkills().size();
                    if (newSkillSize != prevSkillSize) {
                        isUpdated = true;
                    }
                    break;

                case 7:
                    // Exit and display success if any change happened
                    if (isUpdated) {
                        inputUI.displayMessage("\n Profile updated successfully!");
                        menuUI.printApplicantTableHeader();
                        menuUI.printApplicantRow(applicant);
                        menuUI.printApplicantsTableFooter();
                        inputUI.getInput("<< Press Enter to continue >>");
                    }
                    return;

                default:
                    inputUI.displayMessage(" Invalid option. Please try again.");
            }

        } while (true);
    }

    private void handleSkillEditing(DoublyLinkedListInterface<Skill> skills) {
        inputUI.displayMessage("\n========== Edit Skills ==========");
        displaySkills(skills);

        int skillChoice;
        do {
            inputUI.displayMessage("\n1. Add a new skill");
            inputUI.displayMessage("2. Edit an existing skill");
            inputUI.displayMessage("3. Remove a skill");
            inputUI.displayMessage("4. Finish editing skills");
            skillChoice = inputUI.getIntInput("Choose an option: ", 0, 4);

            switch (skillChoice) {
                case 1:
                    addSkill(skills);
                    break;
                case 2:
                    editSkill(skills);
                    break;
                case 3:
                    removeSkill(skills);
                    break;
                case 4:
                    // return back to Client main menu
                    return;
                default:
                    inputUI.displayMessage("Invalid option. Please choose again.");
            }
        } while (skillChoice != 0);
    }

    private void displaySkills(DoublyLinkedListInterface<Skill> skills) {
        if (skills.isEmpty()) {
            inputUI.displayMessage("No skills found.");
        } else {
            menuUI.printSkills(skills);
        }
    }

    private void addSkill(DoublyLinkedListInterface<Skill> skills) {
        inputUI.displayMessage("Enter new skill (or type 'back' to cancel):");
        String newSkill = inputUI.getInput("Skill: ");
        if (newSkill.equalsIgnoreCase("back")) {
            return;
        }

        String newCategory = inputUI.getInput("Category: ");
        if (newCategory.equalsIgnoreCase("back")) {
            return;
        }

        int newProficiency = inputUI.getIntInput("Proficiency level (1-5, or -1 to cancel): ", -1, 5);
        if (newProficiency == -1) {
            return;
        }

        skills = addApplicantSkill(skills, newSkill, newCategory, newProficiency);
        inputUI.displayMessage("New skill added successfully!");
        inputUI.displayMessage("Current Skills:");
        displaySkills(skills);
    }

    private void editSkill(DoublyLinkedListInterface<Skill> skills) {
        if (skills.isEmpty()) {
            inputUI.displayMessage("No skills to edit.");
            return;
        }

        int skillIndex = inputUI.getIntInput("Enter the skill number to edit (or -1 to cancel): ", -1, skills.size());
        if (skillIndex == -1) {
            return;
        }

        Skill selectedSkill = skills.get(skillIndex - 1);

        String updatedSkill = inputUI
                .getInput("New skill name (or press Enter to keep: " + selectedSkill.getName() + "): ");
        if (updatedSkill.equalsIgnoreCase("back")) {
            return;
        }
        if (!updatedSkill.isEmpty()) {
            selectedSkill.setName(updatedSkill);
        }

        String updatedCategory = inputUI
                .getInput("New category (or press Enter to keep: " + selectedSkill.getCategory() + "): ");
        if (updatedCategory.equalsIgnoreCase("back")) {
            return;
        }
        if (!updatedCategory.isEmpty()) {
            selectedSkill.setCategory(updatedCategory);
        }

        int updatedProficiency = inputUI.getIntInput(
                "New proficiency level (1-5 or -1 to keep: " + selectedSkill.getProficiency_level() + "): ", -1, 5);
        if (updatedProficiency == -1) {
            inputUI.displayMessage("Proficiency unchanged.");
        } else {
            selectedSkill.setProficiency_level(updatedProficiency);
        }

        inputUI.displayMessage(" Skill updated successfully!");
        inputUI.displayMessage("Current Skills:");
        displaySkills(skills);
    }

    private void removeSkill(DoublyLinkedListInterface<Skill> skills) {
        if (skills.isEmpty()) {
            inputUI.displayMessage("No skills to remove.");
            return;
        }

        int removeIndex = inputUI.getIntInput("Enter the skill number to remove (or -1 to cancel): ", -1,
                skills.size());
        if (removeIndex == -1) {
            return;
        }

        Skill removedSkill = skills.get(removeIndex - 1);
        skills.remove(removeIndex - 1);
        inputUI.displayMessage("Skill '" + removedSkill.getName() + "' removed.");

        inputUI.displayMessage("Current Skills:");
        displaySkills(skills);
    }

    public void filterApplicants() {
        while (true) {
            menuUI.filterApplicantMenu();
            int choice = inputUI.getIntInput("Enter your choice: ", 1, 6);

            DoublyLinkedListInterface<Applicant> filteredApplicants = new DoublyLinkedList<>();

            switch (choice) {
                case 1:
                    filteredApplicants = filterByAgeRange();
                    break;

                case 2:
                    filteredApplicants = filterByLocation();
                    break;

                case 3:
                    filteredApplicants = filterByExperience();
                    break;

                case 4:
                    filteredApplicants = filterByEducationLevel();
                    break;

                case 5:
                    filteredApplicants = filterBySkill();
                    break;

                case 6:
                    inputUI.displayMessage("Returning to previous menu...");
                    return;

                default:
                    inputUI.displayMessage("❌ Invalid option. Please select again.");
                    break;
            }

            if (!filteredApplicants.isEmpty()) {
                menuUI.printApplicants(filteredApplicants);
            }

            inputUI.getInput("<< Press \"Enter\" to continue >>");
        }
    }

    private DoublyLinkedListInterface<Applicant> filterByAgeRange() {
        DoublyLinkedListInterface<Applicant> result = new DoublyLinkedList<>();
        int minAge = inputUI.getValidIntInput("Enter minimum age: ", 18, 100);
        int maxAge = inputUI.getValidIntInput("Enter maximum age: ", minAge, 100);

        for (Applicant a : applicants) {
            if (a.getAge() >= minAge && a.getAge() <= maxAge && !result.contains(a)) {
                result.add(a);
            }
        }
        return result;
    }

    private DoublyLinkedListInterface<Applicant> filterByLocation() {
        DoublyLinkedListInterface<Applicant> result = new DoublyLinkedList<>();
        String location = inputUI.getInput("Enter location: ");

        for (Applicant a : applicants) {
            if (a.getLocation().equalsIgnoreCase(location) && !result.contains(a)) {
                result.add(a);
            }
        }
        return result;
    }

    private DoublyLinkedListInterface<Applicant> filterByExperience() {
        DoublyLinkedListInterface<Applicant> result = new DoublyLinkedList<>();
        int minExp = inputUI.getValidIntInput("Enter minimum years of experience: ", 0, 50);
        int maxExp = inputUI.getValidIntInput("Enter maximum years of experience: ", minExp, 50);

        for (Applicant a : applicants) {
            if (a.getYearsOfExperience() >= minExp && a.getYearsOfExperience() <= maxExp && !result.contains(a)) {
                result.add(a);
            }
        }
        return result;
    }

    private DoublyLinkedListInterface<Applicant> filterByEducationLevel() {
        DoublyLinkedListInterface<Applicant> result = new DoublyLinkedList<>();
        String education = inputUI.getInput("Enter education level: ");

        for (Applicant a : applicants) {
            if (a.getEducationLevel().equalsIgnoreCase(education) && !result.contains(a)) {
                result.add(a);
            }
        }
        return result;
    }

    private DoublyLinkedListInterface<Applicant> filterBySkill() {
        DoublyLinkedListInterface<Applicant> result = new DoublyLinkedList<>();
        String skillName = inputUI.getInput("Enter skill name: ");
        boolean found = false;

        for (Applicant a : applicants) {
            for (Skill skill : a.getSkills()) {
                if (skill.getName().equalsIgnoreCase(skillName)) {
                    if (!result.contains(a)) {
                        result.add(a);
                    }
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            inputUI.displayMessage("No applicants found with the skill: " + skillName);
        }

        return result;
    }

    public DoublyLinkedListInterface<Applicant> getApplicants() {
        return applicants;
    }

}