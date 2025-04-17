/*shu han*/
package controller;

import adt.DoublyLinkedList;
import adt.DoublyLinkedListInterface;
import entities.*;
import boundary.*;
import java.time.LocalDateTime;

public class CompanyManager {

    // Menu UI
    private static InputUI inputUI = new InputUI();
    private static MenuUI menuUI = new MenuUI();


    // Singleton instance
    private static CompanyManager instance;

    // Doubly Linked List to store companies
    private DoublyLinkedListInterface<Company> companyList;

    private DoublyLinkedListInterface<Company> removedCompanies;

    // Private constructor for singleton
    private CompanyManager() {
        companyList = new DoublyLinkedList<>();
        removedCompanies = new DoublyLinkedList<>();
    }

    // Singleton accessor
    public static CompanyManager getInstance() {
        if (instance == null) {
            instance = new CompanyManager();
        }
        return instance;
    }

    public DoublyLinkedListInterface<Company> getCompanies() {
        return companyList;
    }

    // ----------------- CREATE -----------------
    public void addCompany() {
        inputUI.displayMessage("\n===== Add New Company =====");
        String name = inputUI.getInput("Enter Company Name (or 'x' to cancel): ");
        if (name.equalsIgnoreCase("x")) {
            inputUI.displayMessage("Add operation cancelled.\n");
            return;
        }
        String location = inputUI.getInput("Enter Company Location(or 'x' to cancel): ");
        if (location.equalsIgnoreCase("x")) {
            inputUI.displayMessage("Add operation cancelled.\n");
            return;
        }
        int size = inputUI.getIntInput("Enter Company Size(or -1 to cancel): ", 1, 999999999);
        if (size == -1) {
            inputUI.displayMessage("Add operation cancelled.\n");
            return;
        }
        String description = inputUI.getInput("Enter Company Description(or 'x' to cancel): ");
        if (description.equalsIgnoreCase("x")) {
            inputUI.displayMessage("Add operation cancelled.\n");
            return;
        }

        // Create and add the company
        Company newCompany = new Company(name, location, size, description);
        companyList.add(newCompany);

        inputUI.displayMessage("Company added successfully! Company ID is: " + newCompany.getCompanyId() + "\n");
    }

    //Add company
    public void addCompany(Company newCompany) {
        companyList.add(newCompany);
    }

    // ----------------- READ -----------------
    public Company findCompanyById(String companyId) {
        for (Company company : companyList) {
            if (company.getCompanyId().equalsIgnoreCase(companyId)) {
                return company;
            }
        }
        inputUI.displayMessage("Company with ID " + companyId + " not found.");
        inputUI.displayMessage("Please try again.\n");
        return null;
    }

    public void displayCompanies() {
        inputUI.displayMessage("\n===== Company List =====");
        if (companyList.isEmpty()) {
            inputUI.displayMessage("No companies found.\n");
            return;
        }

        // Print company list using menuUI (similar to the applicants)
        menuUI.printCompanies(companyList);

        // Prompt to continue
        inputUI.getInput("Press Enter to continue...");
    }

    // ----------------- EDIT -----------------
    public void editCompany() {
        inputUI.displayMessage("\n===== Edit Company =====");
        menuUI.printCompanies(companyList);
        Company company = null;

        // Loop until a valid company is found
        while (company == null) {
            String companyId = inputUI.getInput("Enter Company ID to edit (or 'x' to cancel): ").trim();

            if (companyId.equalsIgnoreCase("x")) {
                inputUI.displayMessage("Edit operation cancelled.");
                return; // Exit the method
            }

            company = findCompanyById(companyId);

        }

        inputUI.displayMessage("Editing company: " + company.getCompanyName());

        // Name
        String name = inputUI.getInput("Enter New Name (Enter 'x' to cancel or press Enter to keep: " + company.getCompanyName() + "): ");
        if (name.equalsIgnoreCase("x")) {
            inputUI.displayMessage("Add operation cancelled.\n");
            return;
        }
        if (!name.isEmpty()) company.setCompanyName(name);

        // Location
        String location = inputUI.getInput("Enter New Location (Enter 'x' to cancel or press Enter to keep: " + company.getCompanyLocation() + "): ");
        if (location.equalsIgnoreCase("x")) {
            inputUI.displayMessage("Add operation cancelled.\n");
            return;
        }
        if (!location.isEmpty()) company.setCompanyLocation(location);

        // Size
        String sizeInput = inputUI.getInput("Enter New Size (Enter '-1' to cancel or press existing size to keep: " + company.getCompanySize() + "): ");
        if (sizeInput.equalsIgnoreCase("-1")) {
            inputUI.displayMessage("Add operation cancelled.\n");
            return;
        }
        if (!sizeInput.isEmpty()) {
            try {
                int size = Integer.parseInt(sizeInput);
                company.setCompanySize(size);
            } catch (NumberFormatException e) {
                inputUI.displayMessage("Invalid size input. Keeping existing size.");
            }
        }

        // Description
        String description = inputUI.getInput("Enter New Description (Enter 'x' to cancel or press Enter to keep: " + company.getCompanyDescription() + "): ");
        if (description.equalsIgnoreCase("x")) {
            inputUI.displayMessage("Add operation cancelled.\n");
            return;
        }
        if (!description.isEmpty()) company.setCompanyDescription(description);

        inputUI.displayMessage("Company profile updated successfully!");
    }

    // ----------------- HELPER METHODS -----------------
    public boolean isEmpty() {
        return companyList.isEmpty();  // Delegates to the list
    }
    
    public boolean isCompanyExists(String companyId) {
        for (Company c : companyList) {
            if (c.getCompanyId().equalsIgnoreCase(companyId) && c.getRemovedAt() == null) {
                return true;
            }
        }
        return false;
    }

}