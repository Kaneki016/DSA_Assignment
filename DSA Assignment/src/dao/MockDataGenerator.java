/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.*;
import controller.*;
import entities.*;
import java.util.Scanner;

/**
 *
 * @author MAMBA
 */
public class MockDataGenerator {

    private Scanner scanner = new Scanner(System.in);

    //Instance Definer
    private static MockDataGenerator instance;

    // Singleton accessor
    public static MockDataGenerator getInstance() {
        if (instance == null) {
            instance = new MockDataGenerator();
        }
        return instance;
    }

    //Controller instances
    private static ApplicantManager applicantManager = ApplicantManager.getInstance();
    private static ApplicantAppliedJobManager applicantAppliedJobManager = ApplicantAppliedJobManager.getInstance();
    private static CompanyManager companyManager = CompanyManager.getInstance();
    private static InterviewManager interviewManager = InterviewManager.getInstance();
    private static TimeSlotManager timeSlotManager = TimeSlotManager.getInstance();

    public void addMockData() {
        // 1. Create and store companies first
        Company companyABC = new Company("ABC", "KL", 100, "Good Company", 123123123);
        Company companyDBC = new Company("DBC", "TH", 10, "Good Company", 123123123);
        
        companyManager.addCompany(companyABC);
        companyManager.addCompany(companyDBC);

        // 2. Create skills and applicants using ApplicantManager methods
        // Applicant 1: Lim
        DoublyLinkedListInterface<Skill> skillSet1 = new DoublyLinkedList<>();
        skillSet1 = applicantManager.addApplicantSkill(skillSet1, "Leadership", "Mental", 3);
        skillSet1 = applicantManager.addApplicantSkill(skillSet1, "C++", "Programming", 5);
        Applicant applicant1 = new Applicant("Lim", 18, "KL", 3, "Degree", skillSet1);
        applicantManager.addApplicant(applicant1);

        // Applicant 2: DeDek
        DoublyLinkedListInterface<Skill> skillSet2 = new DoublyLinkedList<>();
        skillSet2 = applicantManager.addApplicantSkill(skillSet2, "C++", "Programming", 5);
        Applicant applicant2 = new Applicant("DeDek", 50, "KLTT", 3, "Degree", skillSet2);
        applicantManager.addApplicant(applicant2);

        // Applicant 3: Tetej
        DoublyLinkedListInterface<Skill> skillSet3 = new DoublyLinkedList<>();
        skillSet3 = applicantManager.addApplicantSkill(skillSet3, "Leadership", "Mental", 3);
        skillSet3 = applicantManager.addApplicantSkill(skillSet3, "C++", "Programming", 5);
        Applicant applicant3 = new Applicant("Tetej", 30, "KLDF", 3, "Master", skillSet3);
        applicantManager.addApplicant(applicant3);

        // 3. Create job requirements
        DoublyLinkedListInterface<JobRequirements> jobRequirements = new DoublyLinkedList<>();
        JobRequirements requirement1 = new JobRequirements("Leadership", "Mental", 3);
        jobRequirements.add(requirement1);

        // 4. Create jobs
        Job softwareEngineerJob = new Job("Software Engineer", "KL", 3, jobRequirements, 3000);
        Job dataEngineerJob = new Job("Data Engineer", "KL", 3, jobRequirements, 3000);

        // 5. Create job posts
        JobPost jobPost1 = new JobPost(softwareEngineerJob, companyABC);
        JobPost jobPost2 = new JobPost(dataEngineerJob, companyDBC);

        // 6. Create applicant applied jobs
        ApplicantAppliedJob application1 = new ApplicantAppliedJob(applicant1, jobPost1);
        ApplicantAppliedJob application2 = new ApplicantAppliedJob(applicant2, jobPost2);
        ApplicantAppliedJob application3 = new ApplicantAppliedJob(applicant3, jobPost1);

        applicantAppliedJobManager.addApplicantAppliedJob(application1);
        applicantAppliedJobManager.addApplicantAppliedJob(application2);
        applicantAppliedJobManager.addApplicantAppliedJob(application3);

        // 7. Create time slots
        TimeSlot timeSlot1 = new TimeSlot("5.00pm", "6/3/2025", "Bukit Bintang");
        TimeSlot timeSlot2 = new TimeSlot("7.00pm", "6/3/2025", "Bukit Bintang");
        TimeSlot timeSlot3 = new TimeSlot("9.00pm", "6/3/2025", "Bukit Bintang");
        timeSlotManager.addTimeSlot(timeSlot1);
        timeSlotManager.addTimeSlot(timeSlot2);
        timeSlotManager.addTimeSlot(timeSlot3);

        System.out.println("Mock Data Generated Successfully!\n");
    }

}
