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

    // Instance Definer
    private static MockDataGenerator instance;

    // Singleton accessor
    public static MockDataGenerator getInstance() {
        if (instance == null) {
            instance = new MockDataGenerator();
        }
        return instance;
    }

    // Controller instances
    private static ApplicantManager applicantManager = ApplicantManager.getInstance();
    private static ApplicantAppliedJobManager applicantAppliedJobManager = ApplicantAppliedJobManager.getInstance();
    private static CompanyManager companyManager = CompanyManager.getInstance();
    private static JobManager jobManager = JobManager.getInstance();
    private static JobPostManager jobPostManager = JobPostManager.getInstance();
    private static JobRequirementsManager jobRequirementsManager = JobRequirementsManager.getInstance();
    private static InterviewManager interviewManager = InterviewManager.getInstance();
    private static TimeSlotManager timeSlotManager = TimeSlotManager.getInstance();

    public void addMockData() {
        // 1. Companies
        Company companyABC = new Company("ABC Tech", "Kuala Lumpur", 150, "Innovative Tech Solutions");
        Company companyXYZ = new Company("XYZ Corp", "Penang", 80, "Reliable IT Partner");
        Company companyDBC = new Company("DBC Systems", "Johor", 60, "Digital Business Consultancy");
        Company companyLMN = new Company("LMN Global", "Sabah", 120, "Global Logistics and Tech");

        companyManager.addCompany(companyABC);
        companyManager.addCompany(companyXYZ);
        companyManager.addCompany(companyDBC);
        companyManager.addCompany(companyLMN);

        // 2. Applicants & Skills
        DoublyLinkedListInterface<Skill> skillSet1 = applicantManager.addApplicantSkill(new DoublyLinkedList<>(),
                "Leadership", "Mental", 3);
        skillSet1 = applicantManager.addApplicantSkill(skillSet1, "C++", "Programming", 5); // Matches Software Engineer
        Applicant applicant1 = new Applicant("Lim", 18, "KL", 3, "Degree", skillSet1);
        applicantManager.addApplicant(applicant1);

        DoublyLinkedListInterface<Skill> skillSet2 = applicantManager.addApplicantSkill(new DoublyLinkedList<>(), "C++",
                "Programming", 5);
        Applicant applicant2 = new Applicant("DeDek", 50, "KLTT", 3, "Degree", skillSet2); // Only C++ match
        applicantManager.addApplicant(applicant2);

        DoublyLinkedListInterface<Skill> skillSet3 = applicantManager.addApplicantSkill(new DoublyLinkedList<>(),
                "Leadership", "Mental", 3);
        skillSet3 = applicantManager.addApplicantSkill(skillSet3, "C++", "Programming", 5);
        Applicant applicant3 = new Applicant("Tetej", 30, "KLDF", 3, "Master", skillSet3); // Leadership match
        applicantManager.addApplicant(applicant3);

        DoublyLinkedListInterface<Skill> skillSet4 = applicantManager.addApplicantSkill(new DoublyLinkedList<>(),
                "Java", "Programming", 4);
        skillSet4 = applicantManager.addApplicantSkill(skillSet4, "Communication", "Soft Skill", 5);
        Applicant applicant4 = new Applicant("Siti", 25, "SG", 2, "Bachelor", skillSet4); // Matches System Admin
        applicantManager.addApplicant(applicant4);

        DoublyLinkedListInterface<Skill> skillSet5 = applicantManager.addApplicantSkill(new DoublyLinkedList<>(),
                "Java", "Programming", 3);
        skillSet5 = applicantManager.addApplicantSkill(skillSet5, "Communication", "Soft Skill", 5);
        Applicant applicant5 = new Applicant("Lina", 24, "Melaka", 1, "Degree", skillSet5); // Similar to Siti
        applicantManager.addApplicant(applicant5);

        DoublyLinkedListInterface<Skill> skillSet6 = applicantManager.addApplicantSkill(new DoublyLinkedList<>(), "AWS",
                "Cloud", 5);
        skillSet6 = applicantManager.addApplicantSkill(skillSet6, "Communication", "Soft Skill", 5);
        Applicant applicant6 = new Applicant("Eric", 35, "Sabah", 8, "PhD", skillSet6); // Matches Cloud Architect +
                                                                                        // DevOps
        applicantManager.addApplicant(applicant6);

        DoublyLinkedListInterface<Skill> skillSet7 = applicantManager.addApplicantSkill(new DoublyLinkedList<>(),
                "Python", "Programming", 4);
        skillSet7 = applicantManager.addApplicantSkill(skillSet7, "Leadership", "Mental", 2);
        Applicant applicant7 = new Applicant("Zara", 27, "Negeri Sembilan", 4, "Degree", skillSet7); // Matches Data
                                                                                                     // Scientist
        applicantManager.addApplicant(applicant7);

        DoublyLinkedListInterface<Skill> skillSet8 = applicantManager.addApplicantSkill(new DoublyLinkedList<>(),
                "Python", "Programming", 3);
        skillSet8 = applicantManager.addApplicantSkill(skillSet8, "Data Analysis", "Analytics", 4);
        Applicant applicant8 = new Applicant("Hafiz", 26, "Penang", 2, "Bachelor", skillSet8); // Matches Data Scientist
        applicantManager.addApplicant(applicant8);

        DoublyLinkedListInterface<Skill> skillSet9 = applicantManager.addApplicantSkill(new DoublyLinkedList<>(), "AWS",
                "Cloud", 4);
        skillSet9 = applicantManager.addApplicantSkill(skillSet9, "Docker", "DevOps", 3);
        Applicant applicant9 = new Applicant("Mei Ling", 29, "Johor", 5, "Master", skillSet9); // Matches Cloud DevOps
        applicantManager.addApplicant(applicant9);

        DoublyLinkedListInterface<Skill> skillSet10 = applicantManager.addApplicantSkill(new DoublyLinkedList<>(),
                "Excel", "Analytics", 5);
        skillSet10 = applicantManager.addApplicantSkill(skillSet10, "Communication", "Soft Skill", 4);
        Applicant applicant10 = new Applicant("John", 32, "Melaka", 7, "Diploma", skillSet10); // Matches Business
                                                                                               // Analyst
        applicantManager.addApplicant(applicant10);

        // 3. Job Requirements
        JobRequirements req1 = new JobRequirements("Leadership", "3", "Mental");
        JobRequirements req2 = new JobRequirements("Python", "3", "Programming");
        JobRequirements req3 = new JobRequirements("Data Analysis", "4", "Analytics");
        JobRequirements req4 = new JobRequirements("Docker", "3", "DevOps");
        JobRequirements req5 = new JobRequirements("Excel", "4", "Analytics");
        JobRequirements req6 = new JobRequirements("AWS", "4", "Cloud");

        jobRequirementsManager.addJobRequirement(req1);
        jobRequirementsManager.addJobRequirement(req2);
        jobRequirementsManager.addJobRequirement(req3);
        jobRequirementsManager.addJobRequirement(req4);
        jobRequirementsManager.addJobRequirement(req5);
        jobRequirementsManager.addJobRequirement(req6);

        // 4. Jobs 

        // Software Engineer (C++, Leadership, Communication)
        DoublyLinkedListInterface<JobRequirements> jrBase = new DoublyLinkedList<>();
        jrBase.add(req1); // Leadership
        jrBase.add(new JobRequirements("C++", "4", "Programming"));
        jrBase.add(new JobRequirements("Communication", "3", "Soft Skill"));
        Job job1 = new Job("Software Engineer", "KL", 3, jrBase, 3000);

        // Data Engineer (C++, Python, Leadership)
        DoublyLinkedListInterface<JobRequirements> jrDE = new DoublyLinkedList<>();
        jrDE.add(new JobRequirements("C++", "3", "Programming"));
        jrDE.add(req2); // Python
        jrDE.add(req1); // Leadership
        Job job2 = new Job("Data Engineer", "KL", 3, jrDE, 3000);

        // Data Analyst (Data Analysis, Excel, Communication)
        DoublyLinkedListInterface<JobRequirements> jrDA = new DoublyLinkedList<>();
        jrDA.add(req3); // Data Analysis
        jrDA.add(req5); // Excel
        jrDA.add(new JobRequirements("Communication", "3", "Soft Skill"));
        Job job3 = new Job("Data Analyst", "Penang", 2, jrDA, 3200);

        // DevOps Engineer (Docker, Leadership, Communication)
        DoublyLinkedListInterface<JobRequirements> jrDevOps = new DoublyLinkedList<>();
        jrDevOps.add(req4); // Docker
        jrDevOps.add(req1); // Leadership
        jrDevOps.add(new JobRequirements("Communication", "4", "Soft Skill"));
        Job job4 = new Job("DevOps Engineer", "Johor", 4, jrDevOps, 4000);

        // Cloud Architect (AWS, Docker, Communication)
        DoublyLinkedListInterface<JobRequirements> jrCloud = new DoublyLinkedList<>();
        jrCloud.add(req6); // AWS
        jrCloud.add(req4); // Docker
        jrCloud.add(new JobRequirements("Communication", "4", "Soft Skill"));
        Job job5 = new Job("Cloud Architect", "Sabah", 5, jrCloud, 4500);

        // System Admin (Java, Communication, Leadership)
        DoublyLinkedListInterface<JobRequirements> jrSysAdmin = new DoublyLinkedList<>();
        jrSysAdmin.add(new JobRequirements("Java", "3", "Programming"));
        jrSysAdmin.add(new JobRequirements("Communication", "3", "Soft Skill"));
        jrSysAdmin.add(req1); // Leadership
        Job job6 = new Job("System Admin", "Melaka", 2, jrSysAdmin, 3000);

        // Data Scientist (Python, Data Analysis, Leadership)
        DoublyLinkedListInterface<JobRequirements> jr1 = new DoublyLinkedList<>();
        jr1.add(req2); // Python
        jr1.add(req3); // Data Analysis
        jr1.add(req1); // Leadership
        Job job7 = new Job("Data Scientist", "Penang", 2, jr1, 5000);

        // Cloud DevOps (Docker, AWS, Python, Communication)
        DoublyLinkedListInterface<JobRequirements> jr2 = new DoublyLinkedList<>();
        jr2.add(req4); // Docker
        jr2.add(req6); // AWS
        jr2.add(req2); // Python
        jr2.add(new JobRequirements("Communication", "3", "Soft Skill"));
        Job job8 = new Job("Cloud DevOps", "Johor", 5, jr2, 6000);

        // Business Analyst (Excel, Communication, Data Analysis)
        DoublyLinkedListInterface<JobRequirements> jr3 = new DoublyLinkedList<>();
        jr3.add(req5); // Excel
        jr3.add(new JobRequirements("Communication", "4", "Soft Skill"));
        jr3.add(req3); // Data Analysis
        Job job9 = new Job("Business Analyst", "Melaka", 3, jr3, 4000);

        // Add all to job manager
        jobManager.addJob(job1);
        jobManager.addJob(job2);
        jobManager.addJob(job3);
        jobManager.addJob(job4);
        jobManager.addJob(job5);
        jobManager.addJob(job6);
        jobManager.addJob(job7);
        jobManager.addJob(job8);
        jobManager.addJob(job9);

        // 5. Job Posts
        JobPost jobPost1 = new JobPost(job1, companyABC);
        JobPost jobPost2 = new JobPost(job2, companyXYZ);
        JobPost jobPost3 = new JobPost(job3, companyDBC);
        JobPost jobPost4 = new JobPost(job4, companyLMN);
        JobPost jobPost5 = new JobPost(job5, companyXYZ);
        JobPost jobPost6 = new JobPost(job6, companyXYZ);
        JobPost jobPost7 = new JobPost(job1, companyXYZ);
        JobPost jobPost8 = new JobPost(job7, companyDBC);
        JobPost jobPost9 = new JobPost(job8, companyLMN);
        JobPost jobPost10 = new JobPost(job9, companyABC);

        jobPostManager.addJobPost(jobPost1);
        jobPostManager.addJobPost(jobPost2);
        jobPostManager.addJobPost(jobPost3);
        jobPostManager.addJobPost(jobPost4);
        jobPostManager.addJobPost(jobPost5);
        jobPostManager.addJobPost(jobPost6);
        jobPostManager.addJobPost(jobPost7);
        jobPostManager.addJobPost(jobPost8);
        jobPostManager.addJobPost(jobPost9);
        jobPostManager.addJobPost(jobPost10);

        // 6. Applications
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
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant8, jobPost8));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant9, jobPost9));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant10, jobPost10));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant8, jobPost3));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant9, jobPost5));

        // 7. Time Slots
        timeSlotManager.addTimeSlot(new TimeSlot("5.00pm", "6/3/2025", "Bukit Bintang"));
        timeSlotManager.addTimeSlot(new TimeSlot("7.00pm", "6/3/2025", "Bukit Bintang"));
        timeSlotManager.addTimeSlot(new TimeSlot("9.00pm", "6/3/2025", "Bukit Bintang"));
        timeSlotManager.addTimeSlot(new TimeSlot("10.00am", "7/3/2025", "Marina Bay"));
        timeSlotManager.addTimeSlot(new TimeSlot("2.00pm", "7/3/2025", "Marina Bay"));

        System.out.println("Mock Data Generated Successfully!");
    }
}
