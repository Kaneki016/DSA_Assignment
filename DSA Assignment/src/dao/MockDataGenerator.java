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

    //Doubly Linked List ADT
    private DoublyLinkedListInterface<Skill> skill = new DoublyLinkedList<>();
    private DoublyLinkedListInterface<Skill> skill2 = new DoublyLinkedList<>();
    private DoublyLinkedListInterface<JobRequirements> jobRequirements = new DoublyLinkedList<>();
    private DoublyLinkedListInterface<Job> job = new DoublyLinkedList<>();
    private DoublyLinkedListInterface<JobPost> jobpost = new DoublyLinkedList<>();
    private DoublyLinkedListInterface<TimeSlot> timeSlot = new DoublyLinkedList<>();
    private DoublyLinkedListInterface<Interview> interview = new DoublyLinkedList<>();

    //Controller 
    private static ApplicantManager applicantManager = ApplicantManager.getInstance();
    private static ApplicantAppliedJobManager applicantAppliedJobManager = ApplicantAppliedJobManager.getInstance();
    private static CompanyManager companyManager = CompanyManager.getInstance();

    //Test function for mock data
    public void addMockData() {
        //Mock Company
        Company newCompany = new Company("ABC", "KL", 100, "Good Company", 123123123);
        Company newCompany2 = new Company("DBC", "TH", 10, "Good Company", 123123123);

        //Mock Skill
        Skill newSkill = new Skill("Leadership", "Mental", 3);
        Skill newSkill2 = new Skill("C++", "Programming", 5);
        skill.add(newSkill);
        skill.add(newSkill2);
        
        skill2.add(newSkill2);

        //Mock Applicant
        Applicant applicant1 = new Applicant("Lim", 18, "KL", 3, "Degree", skill);
        Applicant applicant2 = new Applicant("DeDek", 50, "KLTT", 3, "Degree", skill2);
        Applicant applicant3 = new Applicant("Tetej", 30, "KLDF", 3, "Master", skill);
        
        //Job Requirements Mock
        JobRequirements jobRequirements1 = new JobRequirements("Leadership", "Mental", 3);
        jobRequirements.add(jobRequirements1);
        
        //Mock Job
        Job newJob = new Job("Software Engineer", newCompany, "KL", 3, jobRequirements, 3000);
        Job newJob2 = new Job("Data Engineer", newCompany2, "KL", 3, jobRequirements, 3000);

        //Mock Job Post
        JobPost jp1 = new JobPost(newJob, newCompany);
        JobPost jp2 = new JobPost(newJob2, newCompany2);

        //Mock ApplicantAppliedJob
        ApplicantAppliedJob aaj1 = new ApplicantAppliedJob(applicant1, jp1);
        ApplicantAppliedJob aaj2 = new ApplicantAppliedJob(applicant2, jp2);
        ApplicantAppliedJob aaj3 = new ApplicantAppliedJob(applicant3, jp1);

        //Mock Time Slot
        TimeSlot timeslot1 = new TimeSlot("5.00pm", "6/3/2025", "Bukit Bintang");
        TimeSlot timeslot2 = new TimeSlot("7.00pm", "6/3/2025", "Bukit Bintang");

        //Mock Interview
        Interview interview1 = new Interview(aaj1, timeslot1, "Zoom", "Waiting", "none", 0);
        Interview interview2 = new Interview(aaj2, timeslot2, "Google Meet", "Waiting", "none", 0);
        Interview interview3 = new Interview(aaj3, timeslot1, "Google Meet", "Waiting", "none", 0);

        applicantManager.addApplicant(applicant1);
        applicantManager.addApplicant(applicant2);
        applicantManager.addApplicant(applicant3);

        companyManager.addCompany(newCompany);
        companyManager.addCompany(newCompany2);

        skill.add(newSkill);

        job.add(newJob);
        job.add(newJob2);

        jobpost.add(jp1);
        jobpost.add(jp2);

        applicantAppliedJobManager.addApplicantAppliedJob(aaj1);
        applicantAppliedJobManager.addApplicantAppliedJob(aaj2);
        applicantAppliedJobManager.addApplicantAppliedJob(aaj3);

        timeSlot.add(timeslot1);
        timeSlot.add(timeslot2);

        interview.add(interview1);
        interview.add(interview2);
        interview.add(interview3);
        System.out.println("Mock Data Generated!\n");

    }

}
