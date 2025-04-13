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
    private static JobManager jobManager = JobManager.getInstance();
    private static JobPostManager jobPostManager = JobPostManager.getInstance();
    private static JobRequirementsManager jobRequirementsManager = JobRequirementsManager.getInstance();
    private static InterviewManager interviewManager = InterviewManager.getInstance();
    private static TimeSlotManager timeSlotManager = TimeSlotManager.getInstance();

    public void addMockData() {

        Company companyABC = new Company("ABC Tech", "Kuala Lumpur", 150, "Innovative Tech Solutions", "011-2345678");
        Company companyXYZ = new Company("XYZ Corp", "Penang", 80, "Reliable IT Partner", "017-8889988");
        Company companyDBC = new Company("DBC Systems", "Johor", 60, "Digital Business Consultancy", "016-5522331");
        Company companyLMN = new Company("LMN Global", "Sabah", 120, "Global Logistics and Tech", "012-3322110");

        companyManager.addCompany(companyABC);
        companyManager.addCompany(companyXYZ);
        companyManager.addCompany(companyDBC);
        companyManager.addCompany(companyLMN);

        // 2. Create skills and applicants using ApplicantManager methods
        // Applicant 1: Lim
        DoublyLinkedListInterface<Skill> skillSet1 = new DoublyLinkedList<>();
        skillSet1 = applicantManager.addApplicantSkill(skillSet1, "Leadership", "Mental", 2);
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

        // Applicant 4: Siti
        DoublyLinkedListInterface<Skill> skillSet4 = new DoublyLinkedList<>();
        skillSet4 = applicantManager.addApplicantSkill(skillSet4, "Java", "Programming", 4);
        skillSet4 = applicantManager.addApplicantSkill(skillSet4, "Communication", "Soft Skill", 5);
        Applicant applicant4 = new Applicant("Siti", 25, "SG", 2, "Bachelor", skillSet4);
        applicantManager.addApplicant(applicant4);

        //Applicant 5
        DoublyLinkedListInterface<Skill> skillSet5 = new DoublyLinkedList<>();
        skillSet5 = applicantManager.addApplicantSkill(skillSet4, "Java", "Programming", 2);
        skillSet5 = applicantManager.addApplicantSkill(skillSet4, "Communication", "Soft Skill", 5);
        Applicant applicant5 = new Applicant("Lina", 24, "Melaka", 1, "Degree", skillSet5);
        applicantManager.addApplicant(applicant5);

        //Applicant 6
        DoublyLinkedListInterface<Skill> skillSet6 = new DoublyLinkedList<>();
        skillSet6 = applicantManager.addApplicantSkill(skillSet6, "AWS", "Cloud", 5);
        skillSet6 = applicantManager.addApplicantSkill(skillSet6, "Communication", "Soft Skill", 5);
        Applicant applicant6 = new Applicant("Eric", 35, "Sabah", 8, "PhD", skillSet6);
        applicantManager.addApplicant(applicant6);

        //Applicant 7
        DoublyLinkedListInterface<Skill> skillSet7 = new DoublyLinkedList<>();
        skillSet7 = applicantManager.addApplicantSkill(skillSet6, "Python", "Programming", 4);
        skillSet7 = applicantManager.addApplicantSkill(skillSet6, "Leadership,Communication", "Mental", 3);
        Applicant applicant7 = new Applicant("Zara", 27, "Negeri Sembilan", 4, "Degree", skillSet7);
        applicantManager.addApplicant(applicant7);

        applicantManager.addApplicant(applicant1);
        applicantManager.addApplicant(applicant2);
        applicantManager.addApplicant(applicant3);
        applicantManager.addApplicant(applicant4);
        applicantManager.addApplicant(applicant5);
        applicantManager.addApplicant(applicant6);
        applicantManager.addApplicant(applicant7);

        // 3. Create job requirements
        DoublyLinkedListInterface<JobRequirements> jobRequirements = new DoublyLinkedList<>();
        JobRequirements requirement1 = new JobRequirements("Leadership", "3", "Mental");
        jobRequirementsManager.addJobRequirement(requirement1);
        jobRequirements.add(requirement1);

        // 4. Create jobs
        Job softwareEngineerJob = new Job("Software Engineer", "KL", 3, jobRequirements, 3000);
        Job dataEngineerJob = new Job("Data Engineer", "KL", 3, jobRequirements, 3000);

        Job job3 = new Job("Data Analyst", "Penang", 2, jobRequirements, 3200);
        Job job4 = new Job("DevOps Engineer", "Johor", 4, jobRequirements, 4000);
        Job job5 = new Job("Cloud Architect", "Sabah", 5, jobRequirements, 4500);
        Job job6 = new Job("System Admin", "Melaka", 2, jobRequirements, 3000);

        jobManager.addJob(softwareEngineerJob);
        jobManager.addJob(dataEngineerJob);
        jobManager.addJob(job3);
        jobManager.addJob(job4);
        jobManager.addJob(job5);
        jobManager.addJob(job6);

        // 5. Create job posts
        JobPost jobPost1 = new JobPost(softwareEngineerJob, companyABC);
        JobPost jobPost2 = new JobPost(dataEngineerJob, companyXYZ);
        JobPost jobPost3 = new JobPost(job3, companyDBC);
        JobPost jobPost4 = new JobPost(job4, companyLMN);
        JobPost jobPost5 = new JobPost(job5, companyXYZ);
        JobPost jobPost6 = new JobPost(job6, companyXYZ);
        JobPost jobPost7 = new JobPost(softwareEngineerJob, companyXYZ);

        jobPostManager.addJobPost(jobPost1);
        jobPostManager.addJobPost(jobPost2);
        jobPostManager.addJobPost(jobPost3);
        jobPostManager.addJobPost(jobPost4);
        jobPostManager.addJobPost(jobPost5);
        jobPostManager.addJobPost(jobPost6);
        jobPostManager.addJobPost(jobPost7);

        // 6. Create applicant applied jobs
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant1, jobPost1));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant2, jobPost1));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant3, jobPost2));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant4, jobPost3));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant5, jobPost5));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant6, jobPost4));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant7, jobPost6));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant2, jobPost3));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant3, jobPost5));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant4, jobPost7));

        // 7. Create time slots
        timeSlotManager.addTimeSlot(new TimeSlot("5.00pm", "6/3/2025", "Bukit Bintang"));
        timeSlotManager.addTimeSlot(new TimeSlot("7.00pm", "6/3/2025", "Bukit Bintang"));
        timeSlotManager.addTimeSlot(new TimeSlot("9.00pm", "6/3/2025", "Bukit Bintang"));
        timeSlotManager.addTimeSlot(new TimeSlot("10.00am", "7/3/2025", "Marina Bay"));
        timeSlotManager.addTimeSlot(new TimeSlot("2.00pm", "7/3/2025", "Marina Bay"));

        System.out.println("Mock Data Generated Successfully!");
    }
}
