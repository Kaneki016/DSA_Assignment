package controller;

import java.util.Scanner;
import adt.*;
import entities.Company;
import boundary.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class CompanyManager2 {

    private DoublyLinkedListInterface<Company> companyList;
    private DoublyLinkedListInterface<DeletedCompany> deletedCompaniesList;
    private InputUI inputUI;

    public CompanyManager2(InputUI inputUI,
                          DoublyLinkedListInterface<Company> companyList,
                          DoublyLinkedListInterface<DeletedCompany> deletedCompaniesList) {
        this.inputUI = inputUI;
        this.companyList = companyList;
        this.deletedCompaniesList = deletedCompaniesList;
    }

    // ========================= ADD COMPANY ==========================
    public void addCompany() {
        inputUI.displayMessage("\n===== Add Company =====");

        String name = inputUI.getInput("Enter Company Name: ");
        String location = inputUI.getInput("Enter Company Location: ");
        int size = inputUI.getIntInput("Enter Company Size: ", 1, Integer.MAX_VALUE);
        String description = inputUI.getInput("Enter Company Description: ");

        String password;
        while (true) {
            password = inputUI.getInput("Enter Password (at least 6 characters): ");
            String confirmPassword = inputUI.getInput("Confirm Password: ");

            if (!password.equals(confirmPassword)) {
                inputUI.displayMessage("Passwords do not match. Try again.");
                continue;
            }

            if (password.length() < 6) {
                inputUI.displayMessage("Password must be at least 6 characters. Try again.");
                continue;
            }

            break;
        }

        Company newCompany = new Company(name, location, size, description, password);
        companyList.add(newCompany);

        inputUI.displayMessage("Company added successfully! Company ID: " + newCompany.getCompanyId());
    }

    // ========================= EDIT COMPANY ==========================
    public void editCompany() {
        inputUI.displayMessage("\n===== Edit Company =====");

        String companyId = inputUI.getInput("Enter Company ID to edit: ");
        Company company = findCompanyById(companyId);

        if (company == null) {
            inputUI.displayMessage("Company not found!");
            return;
        }

        inputUI.displayMessage("Editing company: " + company.getCompanyName());

        String name = inputUI.getInput("Enter New Name (or press Enter to keep: " + company.getCompanyName() + "): ");
        if (!name.isEmpty()) company.setCompanyName(name);

        String location = inputUI.getInput("Enter New Location (or press Enter to keep: " + company.getCompanyLocation() + "): ");
        if (!location.isEmpty()) company.setCompanyLocation(location);

        String sizeInput = inputUI.getInput("Enter New Size (or press existing size to keep: " + company.getCompanySize() + "): ");
        if (!sizeInput.isEmpty()) {
            try {
                int size = Integer.parseInt(sizeInput);
                company.setCompanySize(size);
            } catch (NumberFormatException e) {
                inputUI.displayMessage("Invalid size input. Keeping existing size.");
            }
        }

        String description = inputUI.getInput("Enter New Description (or press Enter to keep: " + company.getCompanyDescription() + "): ");
        if (!description.isEmpty()) company.setCompanyDescription(description);

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

    // ========================= DELETE COMPANY ==========================
    public void deleteCompany() {
        inputUI.displayMessage("\n===== Delete Company =====");

        String companyId = inputUI.getInput("Enter Company ID to delete: ");
        Company company = findCompanyById(companyId);

        if (company == null) {
            inputUI.displayMessage("Company not found!");
            return;
        }

        String confirm = inputUI.getInput("Are you sure you want to delete " + company.getCompanyName() + "? (yes/no): ").trim().toLowerCase();
        if (!confirm.equals("y") && !confirm.equals("yes")) {
            inputUI.displayMessage("Deletion cancelled.");
            return;
        }

        boolean removed = companyList.removeSpecific(company);
        if (removed) {
            deletedCompaniesList.add(new DeletedCompany(company, LocalDateTime.now()));
            inputUI.displayMessage("Company deleted successfully!");
        } else {
            inputUI.displayMessage("Failed to delete company.");
        }
    }

    // ========================= DISPLAY ACTIVE COMPANIES ==========================
    public void displayCompanies() {
        inputUI.displayMessage("\n===== List of Companies =====");

        if (companyList.isEmpty()) {
            inputUI.displayMessage("No companies found.");
            return;
        }

        Iterator<Company> iterator = companyList.iterator();
        while (iterator.hasNext()) {
            Company company = iterator.next();
            inputUI.displayMessage(company.toString());
        }
    }

    // ========================= DISPLAY DELETED COMPANIES ==========================
    public void displayDeletedCompanies() {
        inputUI.displayMessage("\n===== Deleted Companies =====");

        if (deletedCompaniesList.isEmpty()) {
            inputUI.displayMessage("No deleted companies.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Iterator<DeletedCompany> iterator = deletedCompaniesList.iterator();
        while (iterator.hasNext()) {
            DeletedCompany dc = iterator.next();
            inputUI.displayMessage(dc.company.toString());
            inputUI.displayMessage("Deleted At: " + dc.deletedAt.format(formatter));
            inputUI.displayMessage("-------------------------------");
        }
    }

    // ========================= FIND COMPANY BY ID ==========================
    private Company findCompanyById(String companyId) {
        Iterator<Company> iterator = companyList.iterator();
        while (iterator.hasNext()) {
            Company company = iterator.next();
            if (company.getCompanyId().equalsIgnoreCase(companyId)) {
                return company;
            }
        }
        return null;
    }

    // ========================= INNER CLASS FOR DELETED COMPANY ==========================
    private class DeletedCompany {
        Company company;
        LocalDateTime deletedAt;

        DeletedCompany(Company company, LocalDateTime deletedAt) {
            this.company = company;
            this.deletedAt = deletedAt;
        }
    }
}