/*shu han*/
package controller;

import adt.DoublyLinkedList;
import adt.DoublyLinkedListInterface;
import entities.*;
import boundary.*;


public class CompanyManager3 {

    // Menu UI
    private static InputUI inputUI = new InputUI();

    // Singleton instance
    private static CompanyManager3 instance;

    // Doubly Linked List to store companies
    private DoublyLinkedListInterface<Company> companyList;
    
    // Private constructor for singleton
    private CompanyManager3() {
        companyList = new DoublyLinkedList<>();
    }

    // Singleton accessor
    public static CompanyManager3 getInstance() {
        if (instance == null) {
            instance = new CompanyManager3();
        }
        return instance;
    }
    
 // ----------------- CREATE -----------------
    public void addCompany() {
        inputUI.displayMessage("\n===== Add New Company =====");
        String name = inputUI.getInput("Enter Company Name: ");
        String location = inputUI.getInput("Enter Company Location: ");
        int size = inputUI.getIntInput("Enter Company Size: ", 1, 999999999);
        String description = inputUI.getInput("Enter Company Description: ");

        String password;
        do {
            password = inputUI.getInput("Enter Password (at least 6 characters, must include letters and numbers): ");
            if (!isValidPassword(password)) {
                inputUI.displayMessage("Password does not meet the criteria. Try again.");
            }
        } while (!isValidPassword(password));

        // Create and add the company
        Company newCompany = new Company(name, location, size, description, password);
        companyList.add(newCompany);

        inputUI.displayMessage("Company added successfully! Company ID is: " + newCompany.getCompanyId() + "\n");
    }
    
        //Add company
    public void addCompany(Company newCompany) {
        companyList.add(newCompany);
    }

    // Password validation helper
    private boolean isValidPassword(String password) {
        if (password.length() < 6) return false;

        boolean hasLetter = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) hasLetter = true;
            if (Character.isDigit(c)) hasDigit = true;
        }

        return hasLetter && hasDigit;
    }

    // ----------------- READ -----------------
    public Company findCompanyById(String companyId) {
        for (Company company : companyList) {
            if (company.getCompanyId().equalsIgnoreCase(companyId)) {
                return company;
            }
        }
        inputUI.displayMessage("Company with ID " + companyId + " not found.\n");
        return null;
    }

    public void displayCompanies() {
        inputUI.displayMessage("\n===== Company List =====");
        if (companyList.isEmpty()) {
            inputUI.displayMessage("No companies found.\n");
            return;
        }

        int index = 1;
        for (Company company : companyList) {
            inputUI.displayMessage(index + ". " + company);
            index++;
        }
    }

    // ----------------- EDIT -----------------
public void editCompany() {
    inputUI.displayMessage("\n===== Edit Company =====");

    String companyId = inputUI.getInput("Enter Company ID to edit: ");
    Company company = findCompanyById(companyId);

    if (company == null) {
        inputUI.displayMessage("Company not found!");
        return;
    }

    inputUI.displayMessage("Editing company: " + company.getCompanyName());

    // Name
    String name = inputUI.getInput("Enter New Name (or press Enter to keep: " + company.getCompanyName() + "): ");
    if (!name.isEmpty()) company.setCompanyName(name);

    // Location
    String location = inputUI.getInput("Enter New Location (or press Enter to keep: " + company.getCompanyLocation() + "): ");
    if (!location.isEmpty()) company.setCompanyLocation(location);

    // Size
    String sizeInput = inputUI.getInput("Enter New Size (or press existing size to keep: " + company.getCompanySize() + "): ");
    if (!sizeInput.isEmpty()) {
        try {
            int size = Integer.parseInt(sizeInput);
            company.setCompanySize(size);
        } catch (NumberFormatException e) {
            inputUI.displayMessage("Invalid size input. Keeping existing size.");
        }
    }

    // Description
    String description = inputUI.getInput("Enter New Description (or press Enter to keep: " + company.getCompanyDescription() + "): ");
    if (!description.isEmpty()) company.setCompanyDescription(description);

    // Password update section
    String updatePassword = inputUI.getInput("Do you want to update the password? (yes/no): ").trim().toLowerCase();
    if (updatePassword.equals("y") || updatePassword.equals("yes")) {
        String newPassword;
        while (true) {
            newPassword = inputUI.getInput("Enter new password (at least 6 characters): ");
            String confirmPassword = inputUI.getInput("Confirm new password: ");

            if (!newPassword.equals(confirmPassword)) {
                inputUI.displayMessage("Passwords do not match. Try again.");
                continue;
            }

            if (newPassword.length() >= 6) {
                company.setPassword(newPassword);
                inputUI.displayMessage("Password updated successfully!");
                break;
            } else {
                inputUI.displayMessage("Password too short! Must be at least 6 characters.");
            }
        }
    }

    inputUI.displayMessage("Company profile updated successfully!");
}

    // ----------------- DELETE -----------------
    public void removeCompany() {
        inputUI.displayMessage("Enter Company ID to remove:");
        String companyId = inputUI.getInput("Company ID: ");

        // Find the company
        Company company = findCompanyById(companyId);
        if (company == null) return;

        // Find its index
        int index = getCompanyIndex(company);
        if (index == -1) {
            inputUI.displayMessage("Company not found in the list.\n");
            return;
        }

        // Remove by index
        companyList.remove(index);
        inputUI.displayMessage("Company " + companyId + " removed successfully.\n");
    }
    
    private int getCompanyIndex(Company company) {
    int index = 0;
    for (Company c : companyList) {
        if (c.getCompanyId().equals(company.getCompanyId())) {
            return index;
        }
        index++;
    }
    return -1; // not found
}
    
    public boolean isEmpty() {
    return companyList.isEmpty();  // Delegates to the list
}
    
    
}