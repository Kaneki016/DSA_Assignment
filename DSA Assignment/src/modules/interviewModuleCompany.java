package modules;

import java.util.Scanner;
import entities.*;
import adt.*;

public class interviewModuleCompany {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean authenticated = false;
        String companyLogin = "";
        DoublyLinkedList<Company> companyList = new DoublyLinkedList<>();
        DoublyLinkedList<Skill> skillList = new DoublyLinkedList<>();

        //Constructor for mock data - START
        //Company Constructor
        Company company1 = new Company("ABC", "Kuala Lumpur", 100, "A Good Company!", 123123123);
        Company company2 = new Company("Touch&Go", "Kuala Lumpur", 1000, "A Advanced Virtual Wallet Company!", 123321);
        Company company3 = new Company("Maybank", "Kuala Lumpur", 2500, "A Good Banking Company!", 232323);
        companyList.add(company1);
        companyList.add(company2);
        companyList.add(company3);
        //Applicant Constructor
        Applicant applicant1 = new Applicant("Lim Wai Ming", "Kuala Lumpur", 2, "Degree");
        Applicant applicant2 = new Applicant("Alvin Lim ", "Pulau Pinang", 8, "Master");
        Applicant applicant3 = new Applicant("Korester", "Kuala Lumpur", 12, "Diploma");       
         //Constructor for data entries - END

        //Login System - START      
        while (!authenticated) {
            System.out.print("What's your Company ID? : ");
            int companyIdInput = scanner.nextInt();
            System.out.print("Enter your password: ");
            String passwordInput = scanner.next();

            // Try to parse the password input into an integer.
            int passwordInputInt;
            try {
                passwordInputInt = Integer.parseInt(passwordInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric password.");
                continue;
            }

            boolean companyFound = false;

            // Iterate over the doubly linked list to search for the company by its ID.
            for (Company company : companyList) {
                if (company.getCompanyId() == companyIdInput) { 
                    companyFound = true;
                    if (company.getPassword() == passwordInputInt) {
                        System.out.println("Company " + company.getCompanyName() + " identified!");
                        companyLogin = company.getCompanyName();
                        authenticated = true; 
                    } else {
                        System.out.println("Incorrect password for Company ID: " + companyIdInput);
                    }
                    break; 
                }
            }

            if (!companyFound) {
                System.out.println("Company ID not found.");
            }
        }
        //Login System - END
        
        //Choice Selection - START
        int choice = 0;
        do {
            System.out.println("==========================================");
            System.out.println("         Company Interview Module         ");
            System.out.println("==========================================");
            System.out.println("Welcome to the Interview Scheduling Module!");
            System.out.println("Please select one of the following options:");
            System.out.println("1. View and select potential candidates.");
            System.out.println("2. Arrange interview time slots for selected candidates.");
            System.out.println("3. Filter successful candidates for recruitment.");
            System.out.println("4. Rank top candidates based on interview results.");
            System.out.println("5. Insert my skill. (Applicant)");
            System.out.println("6. Exit");
            System.out.print("Enter your choice (1-5): ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println(">> Viewing and selecting potential candidates...");  
                    break;
                case 2:
                    System.out.println(">> Arranging interview time slots for selected candidates...");
                    break;
                case 3:
                    System.out.println(">> Filtering successful candidates for recruitment...");
                    break;
                case 4:
                    System.out.println(">> Ranking top candidates based on interview results...");
                    break;
                case 5:
                    System.out.println(">> Inserting candidates skill...");

                 
                    break;
                case 6:
                    System.out.println("Exiting the Interview Module. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println();
        } while (choice != 5);

        scanner.close();
    }
}
