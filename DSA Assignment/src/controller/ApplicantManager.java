package controller;

import adt.*;
import entities.Applicant;
import java.util.Scanner;
import boundary.*;
import entities.Skill;
import java.util.Comparator;

public class ApplicantManager {

    private static ApplicantManager instance;

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
        DoublyLinkedListInterface<Skill> skills = new DoublyLinkedList<>();
        inputUI.displayMessage("\nADD NEW APPLICANT");
        String name = inputUI.getInput("Enter Name: ");
        int age = inputUI.getIntInput("Enter Age: ", 18, 100);
        String location = inputUI.getInput("Enter Location: ");
        int yearsOfExperience = inputUI.getIntInput("Enter Years of Experience: ", 0, 50);
        String educationLevel = inputUI.getInput("Enter Education Level: ");

        // Add Skill - First skill is mandatory
        inputUI.displayMessage("\nEnter at least one skill:");
        String skill = inputUI.getInput("Your skill: ");
        String category = inputUI.getInput("The category of it: ");
        int proficiency_level = inputUI.getIntInput("Your proficiency level of it (1-5): ", 1, 5);
        skills = addApplicantSkill(skills, skill, category, proficiency_level);
        inputUI.displayMessage("First skill added successfully!");

        int choice = 1;
        while (choice == 1) {
            choice = inputUI.getIntInput("\nAdd another skill? (1 = yes, 0 = no): ", 0, 1);

            if (choice == 1) {
                skill = inputUI.getInput("Your skill: ");
                category = inputUI.getInput("The category of it: ");
                proficiency_level = inputUI.getIntInput("Your proficiency level of it (1-5): ", 1, 5);
                skills = addApplicantSkill(skills, skill, category, proficiency_level);
                inputUI.displayMessage("Skill added successfully!");
            }
        }
        // Add Skill END
        Applicant newApplicant = new Applicant(name, age, location, yearsOfExperience, educationLevel, skills);
        applicants.add(newApplicant);
        inputUI.displayMessage("Applicant added successfully!\n");
    }

    // add applicant ( mock data)
    public void addApplicant(Applicant newApplicant) {
        applicants.add(newApplicant);
        inputUI.displayMessage("Applicant added successfully!\n");
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
        inputUI.displayMessage("Enter the Applicant ID to remove!");
        String id = scanner.nextLine().trim();

        if (id.isEmpty()) {
            inputUI.displayMessage("Applicant ID cannot be empty!");
            return;
        }

        Applicant toRemove = findApplicantById(id);

        if (toRemove != null) {
            applicants.removeSpecific(toRemove);
            inputUI.displayMessage("Applicant " + id + " removed.\n");
        } else {
            inputUI.displayMessage("Applicant not found!\n");
        }
    }

    // Find an applicant
    public Applicant findApplicantById() {
        inputUI.displayMessage("Enter Applicant ID to search: ");
        String id = scanner.nextLine().trim();

        if (id.isEmpty()) {
            inputUI.displayMessage("Applicant ID cannot be empty!");
            return null;
        }

        for (Applicant applicant : applicants) {
            if (applicant.getApplicantId().equals(id)) {
                inputUI.displayMessage(id != null ? "\nApplicant Found:\n" + id : "Applicant not found.");
                return applicant;
            }

        }

        return null;
    }

    // Find an applicant by ID
    public Applicant findApplicantById(String id) {
        if (id.isEmpty()) {
            inputUI.displayMessage("Applicant ID cannot be empty!");
            return null;
        }

        for (Applicant applicant : applicants) {
            if (applicant.getApplicantId().equals(id)) {
                inputUI.displayMessage(id != null ? "\nApplicant Found:\n" + id : "Applicant not found.");
                return applicant;
            }

        }

        return null;
    }

    // Display all applicants
    public void displayAllApplicants() {
        inputUI.displayMessage("\n=============== List of Applicants ===============");
        menuUI.printApplicants(applicants);
        inputUI.getInput("Press Enter to continue...");
    }

    // Check if an applicant exists
    public boolean isValidApplicant(String applicantId) {
        return findApplicantById(applicantId) != null;
    }

    // View applicant profile
    public void viewApplicantProfile(String applicantId) {
        Applicant applicant = findApplicantById(applicantId);
        if (applicant != null) {
            inputUI.displayMessage("\n===== Applicant Profile =====");
            menuUI.printApplicantTableHeader();
            menuUI.printApplicantRow(applicant);
            System.out.println("+------------+----------------+-----+------------+---------+----------------------+\n");

            inputUI.displayMessage("\n===== Skills =====");
            menuUI.printSkills(applicant.getSkills());
        } else {
            inputUI.displayMessage("❌ Applicant not found!\n");
        }

        inputUI.getInput("Press Enter to continue...");
    }

    // Edit applicant profile
    public void editApplicantProfile() {
        inputUI.displayMessage("Enter your Applicant ID:");
        String applicantId = scanner.nextLine().trim();

        Applicant applicant = findApplicantById(applicantId);
        if (applicant == null) {
            inputUI.displayMessage("Applicant not found!\n");
            return;
        }

        inputUI.displayMessage("\n=============== Edit Applicant Profile ===============");

        String name = inputUI.getInput("Enter New Name (or press Enter to keep: " + applicant.getName() + "): ");
        if (!name.isEmpty()) {
            applicant.setName(name);
        }

        int age = inputUI.getIntInput("Enter New Age (or press existing age to keep: " + applicant.getAge() + "): ", 18,
                100);
        applicant.setAge(age);

        String location = inputUI
                .getInput("Enter new Location (or press Enter to keep: " + applicant.getLocation() + "): ");
        if (!location.isEmpty()) {
            applicant.setLocation(location);
        }

        int yearsOfExperience = inputUI.getIntInput(
                "Enter New Years Of Experience (or press existing years of experience to keep: "
                        + applicant.getYearsOfExperience() + "):  ",
                0, 50);
        if (yearsOfExperience != -1) {
            applicant.setYearsOfExperience(yearsOfExperience);
        }

        String educationLevel = inputUI.getInput(
                "Enter new Education Level (or press Enter to keep: " + applicant.getEducationLevel() + "): ");
        if (!educationLevel.isEmpty()) {
            applicant.setEducationLevel(educationLevel);
        }

        // Skill Editing Section --START
        DoublyLinkedListInterface<Skill> skills = applicant.getSkills();
        inputUI.displayMessage("\n========== Edit Skills ==========");
        inputUI.displayMessage("Current Skills:");
        if (skills.isEmpty()) {
            inputUI.displayMessage("No skills found.");
        } else {
            int index = 1;
            for (Skill skill : skills) {
                inputUI.displayMessage(index + ". " + skill);
                index++;
            }
        }

        int skillChoice;
        do {
            inputUI.displayMessage("\n1. Add a new skill");
            inputUI.displayMessage("2. Edit an existing skill");
            inputUI.displayMessage("3. Remove a skill");
            inputUI.displayMessage("0. Finish editing skills");
            skillChoice = inputUI.getIntInput("Choose an option: ", 0, 3);

            switch (skillChoice) {
                case 1: // Add a new skill
                    String newSkill = inputUI.getInput("Enter new skill: ");
                    String newCategory = inputUI.getInput("Enter skill category: ");
                    int newProficiency = inputUI.getIntInput("Enter proficiency level (1-5): ", 1, 5);
                    skills = addApplicantSkill(skills, newSkill, newCategory, newProficiency);
                    inputUI.displayMessage("New skill added successfully!");

                    inputUI.displayMessage("==========================");
                    inputUI.displayMessage("Current Skills:");
                    if (skills.isEmpty()) {
                        inputUI.displayMessage("No skills found.");
                    } else {
                        int index = 1;
                        for (Skill skill : skills) {
                            inputUI.displayMessage(index + ". " + skill);
                            index++;
                        }
                    }
                    break;

                case 2: // Edit an existing skill
                    if (skills.isEmpty()) {
                        inputUI.displayMessage("No skills to edit.");
                        break;
                    }
                    int skillIndex = inputUI.getIntInput("Enter the skill number to edit: ", 1, skills.size());
                    Skill selectedSkill = skills.get(skillIndex - 1);

                    String updatedSkill = inputUI.getInput(
                            "Enter new skill name (or press Enter to keep: " + selectedSkill.getName() + "): ");
                    if (!updatedSkill.isEmpty()) {
                        selectedSkill.setName(updatedSkill);
                    }

                    String updatedCategory = inputUI.getInput(
                            "Enter new category (or press Enter to keep: " + selectedSkill.getCategory() + "): ");
                    if (!updatedCategory.isEmpty()) {
                        selectedSkill.setCategory(updatedCategory);
                    }

                    int updatedProficiency = inputUI.getIntInput(
                            "Enter new proficiency (or -1 to keep: " + selectedSkill.getProficiency_level() + "): ", -1,
                            5);
                    if (updatedProficiency != -1) {
                        selectedSkill.setProficiency_level(updatedProficiency);
                    }

                    inputUI.displayMessage("Skill updated successfully!");

                    inputUI.displayMessage("==========================");
                    inputUI.displayMessage("Current Skills:");
                    if (skills.isEmpty()) {
                        inputUI.displayMessage("No skills found.");
                    } else {
                        int index = 1;
                        for (Skill skill : skills) {
                            inputUI.displayMessage(index + ". " + skill);
                            index++;
                        }
                    }
                    break;

                case 3: // Remove a skill
                    if (skills.isEmpty()) {
                        inputUI.displayMessage("No skills to remove.");
                        break;
                    }
                    int removeIndex = inputUI.getIntInput("Enter the skill number to remove: ", 1, skills.size());
                    skills.remove(removeIndex - 1);
                    inputUI.displayMessage("Skill removed successfully!");

                    inputUI.displayMessage("==========================");
                    inputUI.displayMessage("Current Skills:");
                    if (skills.isEmpty()) {
                        inputUI.displayMessage("No skills found.");
                    } else {
                        int index = 1;
                        for (Skill skill : skills) {
                            inputUI.displayMessage(index + ". " + skill);
                            index++;
                        }
                    }
                    break;
            }
        } while (skillChoice != 0);
        // Skill Editing Section --END
        inputUI.displayMessage("\nProfile updated successfully!");
        inputUI.getInput("Press Enter to continue...");

    }

    public void filterApplicants() {
        menuUI.filterApplicantMenu();
        int choice = inputUI.getIntInput("Enter your choice: ", 1, 7);
        DoublyLinkedListInterface<Applicant> filteredApplicants = new DoublyLinkedList<>();

        switch (choice) {
            case 1: { // Filter by Age Range
                int minAge = inputUI.getValidIntInput("Enter minimum age: ", 18, 100);
                int maxAge = inputUI.getValidIntInput("Enter maximum age: ", minAge, 100);
                for (Applicant a : applicants) {
                    if (a.getAge() >= minAge && a.getAge() <= maxAge) {
                        filteredApplicants.add(a);
                    }
                }
                break;
            }
            case 2: { // Filter by Location
                String location = inputUI.getInput("Enter location: ");
                for (Applicant a : applicants) {
                    if (a.getLocation().equalsIgnoreCase(location)) {
                        filteredApplicants.add(a);
                    }
                }
                break;
            }
            case 3: { // Filter by Experience Range
                int minExp = inputUI.getValidIntInput("Enter minimum years of experience: ", 0, 50);
                int maxExp = inputUI.getValidIntInput("Enter maximum years of experience: ", minExp, 50);
                for (Applicant a : applicants) {
                    if (a.getYearsOfExperience() >= minExp && a.getYearsOfExperience() <= maxExp) {
                        filteredApplicants.add(a);
                    }
                }
                break;
            }
            case 4: { // Filter by Education Level
                String education = inputUI.getInput("Enter education level: ");
                for (Applicant a : applicants) {
                    if (a.getEducationLevel().equalsIgnoreCase(education)) {
                        filteredApplicants.add(a);
                    }
                }
                break;
            }
            case 5: { // Filter by Skills
                String skillName = inputUI.getInput("Enter skill name: ");
                boolean found = false;

                for (Applicant a : applicants) {
                    for (Skill skill : a.getSkills()) {
                        if (skill.getName().equalsIgnoreCase(skillName)) {
                            if (!found) {
                                inputUI.displayMessage("\n🔹 Applicants with the skill '" + skillName + "':");
                            }
                            filteredApplicants.add(a);
                            found = true;
                            break;
                        }
                    }
                }

                if (!found) {
                    inputUI.displayMessage("No applicants found with the skill: " + skillName);
                }
                break;
            }
            case 6: {
                menuUI.exitSystem();
                break;
            }
            default:
                inputUI.displayMessage("Invalid choice! Try again.");
        }

        // Display filtered results
        if (!filteredApplicants.isEmpty()) {
            menuUI.printApplicants(filteredApplicants); // Display results in table format
        }

        // Pause before returning to the menu
        inputUI.getInput("Press Enter to continue...");
    }
    
    public DoublyLinkedListInterface<Applicant> getApplicants() {
        return applicants;
    }

}
