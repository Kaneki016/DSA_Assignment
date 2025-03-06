package controller;

import adt.*;
import boundary.InputUI;
import boundary.MenuUI;
import entities.*;
import java.util.Scanner;

public class InterviewManager {

    private static InterviewManager instance;
    private static Scanner scanner = new Scanner(System.in);
    private static Company company;
    
    //ADT
    private DoublyLinkedListInterface<Interview> interview; // Existing interview list (if needed)

    //Controllers
    private static ApplicantManager applicantManager = ApplicantManager.getInstance();
    private static ApplicantAppliedJobManager applicantAppliedJobManager = ApplicantAppliedJobManager.getInstance();
    private static CompanyManager companyManager = CompanyManager.getInstance();
    
    //Boundary
    private static InputUI inputUI = new InputUI();
    private static MenuUI menuUI = new MenuUI();

    //Instance --START
    private InterviewManager() {
        interview = new DoublyLinkedList<>();
    }

    public static InterviewManager getInstance() {
        if (instance == null) {
            instance = new InterviewManager();
        }
        return instance;
    }
     //Instance --END

    //Display all the applicant applied to the company
    public static void displayAppliedApplicantInterviewMenu() {
        inputUI.displayMessage("Which company you belongs to?: ");
        String companyId = scanner.nextLine().trim();
        company = companyManager.findCompanyById(companyId);

        //Company not found
        if (company == null) {
            inputUI.displayMessage("Company not found!");
        }

        //Display Interview Menu
        menuUI.displayAppliedApplicantInterviewMenu(company.getCompanyName());

        //View all applicant applied to this company.
        viewCompanyAppliedApplicant(company);
    }

    //Assign and Manipulate Time Slot
    public static void displayAssignInterviewTimeSlot() {
        inputUI.displayMessage("Which company you belongs to?: ");
        String companyId = scanner.nextLine().trim();
        company = companyManager.findCompanyById(companyId);

        //Company not found
        if (company == null) {
            inputUI.displayMessage("Company not found!");
        }

        //Display Interview Menu
        menuUI.displayTimeSlotInterviewMenu(company.getCompanyName());
    }

    // This method finds and prints the applicants who applied to the specified company
    public static void viewCompanyAppliedApplicant(Company company) {
        DoublyLinkedListInterface<ApplicantAppliedJob> appliedJobs = applicantAppliedJobManager.getApplicantAppliedJobs();

        System.out.println("\n===== Applicants Applied to " + company.getCompanyName() + " =====");
        boolean found = false;

        for (ApplicantAppliedJob aaj : appliedJobs) {
            if (aaj.getJobPost().getCompany().getCompanyName().equalsIgnoreCase(company.getCompanyName())) {
                System.out.println(aaj.getApplicant());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No applicants found for " + company.getCompanyName() + ".");
        }
    }
}
