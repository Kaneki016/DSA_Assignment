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
        Company companyQRS = new Company("QRS Innovations", "Selangor", 200, "AI-Driven Solutions");
        Company companyTUV = new Company("TUV Technologies", "Kedah", 95, "Next-Gen Software Development");
        Company companyOPQ = new Company("OPQ Enterprises", "Malacca", 70, "Enterprise Cloud Services");
        Company companyEFG = new Company("EFG Digital", "Perak", 130, "Digital Transformation Experts");
        Company companyHIJ = new Company("HIJ Ventures", "Sarawak", 50, "Start-Up Incubator and Accelerator");
        Company companyKLM = new Company("KLM IT Solutions", "Terengganu", 110, "Customized IT Solutions Provider");
        Company companyNOP = new Company("NOP Networks", "Kelantan", 85, "Secure Networking Solutions");
        Company companyRST = new Company("RST Software", "Negeri Sembilan", 140, "Agile Development Services");

        companyManager.addCompany(companyABC);
        companyManager.addCompany(companyXYZ);
        companyManager.addCompany(companyDBC);
        companyManager.addCompany(companyLMN);
        companyManager.addCompany(companyQRS);
        companyManager.addCompany(companyTUV);
        companyManager.addCompany(companyOPQ);
        companyManager.addCompany(companyEFG);
        companyManager.addCompany(companyHIJ);
        companyManager.addCompany(companyKLM);
        companyManager.addCompany(companyNOP);
        companyManager.addCompany(companyRST);

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
        applicantManager.addApplicant(applicant10);                                                                                       
        
        DoublyLinkedListInterface<Skill> skillSet11 = applicantManager.addApplicantSkill(new DoublyLinkedList<>(),
        "React", "Frontend", 4);
        skillSet11 = applicantManager.addApplicantSkill(skillSet11, "JavaScript", "Frontend", 5);
        Applicant applicant11 = new Applicant("Farah", 28, "Selangor", 4, "Bachelor", skillSet11); // Matches Frontend Developer
        applicantManager.addApplicant(applicant11);

        DoublyLinkedListInterface<Skill> skillSet12 = applicantManager.addApplicantSkill(new DoublyLinkedList<>(),
                "SQL", "Database", 5);
        skillSet12 = applicantManager.addApplicantSkill(skillSet12, "Data Modeling", "Analytics", 4);
        Applicant applicant12 = new Applicant("Kumar", 34, "Kuala Lumpur", 6, "Master", skillSet12); // Matches Data Engineer
        applicantManager.addApplicant(applicant12);

        DoublyLinkedListInterface<Skill> skillSet13 = applicantManager.addApplicantSkill(new DoublyLinkedList<>(),
                "Project Management", "Management", 5);
        skillSet13 = applicantManager.addApplicantSkill(skillSet13, "Agile", "Methodology", 4);
        Applicant applicant13 = new Applicant("James", 40, "Johor", 10, "MBA", skillSet13); // Matches Project Manager
        applicantManager.addApplicant(applicant13);

        DoublyLinkedListInterface<Skill> skillSet14 = applicantManager.addApplicantSkill(new DoublyLinkedList<>(),
                "TensorFlow", "AI/ML", 3);
        skillSet14 = applicantManager.addApplicantSkill(skillSet14, "Python", "Programming", 5);
        Applicant applicant14 = new Applicant("Aishah", 31, "Penang", 5, "PhD", skillSet14); // Matches ML Engineer
        applicantManager.addApplicant(applicant14);

        DoublyLinkedListInterface<Skill> skillSet15 = applicantManager.addApplicantSkill(new DoublyLinkedList<>(),
                "Network Security", "Cybersecurity", 5);
        skillSet15 = applicantManager.addApplicantSkill(skillSet15, "Firewall Management", "Cybersecurity", 4);
        Applicant applicant15 = new Applicant("Azman", 36, "Terengganu", 7, "Bachelor", skillSet15); // Matches Security Analyst
        applicantManager.addApplicant(applicant15);

        DoublyLinkedListInterface<Skill> skillSet16 = applicantManager.addApplicantSkill(new DoublyLinkedList<>(),
                "HTML", "Frontend", 5);
        skillSet16 = applicantManager.addApplicantSkill(skillSet16, "CSS", "Frontend", 5);
        Applicant applicant16 = new Applicant("Nurul", 22, "Kelantan", 1, "Diploma", skillSet16); // Matches Junior Web Developer
        applicantManager.addApplicant(applicant16);

        DoublyLinkedListInterface<Skill> skillSet17 = applicantManager.addApplicantSkill(new DoublyLinkedList<>(),
                "UI/UX Design", "Design", 5);
        skillSet17 = applicantManager.addApplicantSkill(skillSet17, "Figma", "Design", 4);
        Applicant applicant17 = new Applicant("Anis", 26, "Sarawak", 3, "Bachelor", skillSet17); // Matches UI/UX Designer
        applicantManager.addApplicant(applicant17);

        DoublyLinkedListInterface<Skill> skillSet18 = applicantManager.addApplicantSkill(new DoublyLinkedList<>(),
                "Node.js", "Backend", 4);
        skillSet18 = applicantManager.addApplicantSkill(skillSet18, "MongoDB", "Database", 3);
        Applicant applicant18 = new Applicant("Rayyan", 30, "Sabah", 5, "Bachelor", skillSet18); // Matches Backend Developer
        applicantManager.addApplicant(applicant18);

        DoublyLinkedListInterface<Skill> skillSet19 = applicantManager.addApplicantSkill(new DoublyLinkedList<>(),
                "Photoshop", "Design", 5);
        skillSet19 = applicantManager.addApplicantSkill(skillSet19, "Illustrator", "Design", 4);
        Applicant applicant19 = new Applicant("Sabrina", 29, "Perlis", 4, "Diploma", skillSet19); // Matches Graphic Designer
        applicantManager.addApplicant(applicant19);

        DoublyLinkedListInterface<Skill> skillSet20 = applicantManager.addApplicantSkill(new DoublyLinkedList<>(),
                "Linux", "System", 4);
        skillSet20 = applicantManager.addApplicantSkill(skillSet20, "Shell Scripting", "DevOps", 3);
        Applicant applicant20 = new Applicant("Hakim", 33, "Putrajaya", 6, "Degree", skillSet20); // Matches System Administrator
        applicantManager.addApplicant(applicant20);


        // 3. Job Requirements
        JobRequirements req1 = new JobRequirements("Leadership", "3", "Mental");
        JobRequirements req2 = new JobRequirements("Python", "3", "Programming");
        JobRequirements req3 = new JobRequirements("Data Analysis", "4", "Analytics");
        JobRequirements req4 = new JobRequirements("Docker", "3", "DevOps");
        JobRequirements req5 = new JobRequirements("Excel", "4", "Analytics");
        JobRequirements req6 = new JobRequirements("AWS", "4", "Cloud");
        JobRequirements req7 = new JobRequirements("Java", "4", "Programming");
        JobRequirements req8 = new JobRequirements("Communication", "5", "Soft Skill");
        JobRequirements req9 = new JobRequirements("C++", "4", "Programming");
        JobRequirements req10 = new JobRequirements("SQL", "4", "Database");
        JobRequirements req11 = new JobRequirements("Project Management", "5", "Management");
        JobRequirements req12 = new JobRequirements("Agile", "3", "Methodology");
        JobRequirements req13 = new JobRequirements("HTML", "4", "Frontend");
        JobRequirements req14 = new JobRequirements("CSS", "3", "Frontend");
        JobRequirements req15 = new JobRequirements("JavaScript", "4", "Frontend");
        JobRequirements req16 = new JobRequirements("MongoDB", "3", "Database");
        JobRequirements req17 = new JobRequirements("TensorFlow", "3", "AI/ML");
        JobRequirements req18 = new JobRequirements("UI/UX Design", "4", "Design");
        JobRequirements req19 = new JobRequirements("Linux", "4", "System");
        JobRequirements req20 = new JobRequirements("Figma", "3", "Design");




        jobRequirementsManager.addJobRequirement(req1);
        jobRequirementsManager.addJobRequirement(req2);
        jobRequirementsManager.addJobRequirement(req3);
        jobRequirementsManager.addJobRequirement(req4);
        jobRequirementsManager.addJobRequirement(req5);
        jobRequirementsManager.addJobRequirement(req6);
        jobRequirementsManager.addJobRequirement(req7);
        jobRequirementsManager.addJobRequirement(req8);
        jobRequirementsManager.addJobRequirement(req9);
        jobRequirementsManager.addJobRequirement(req10);
        jobRequirementsManager.addJobRequirement(req11);
        jobRequirementsManager.addJobRequirement(req12);
        jobRequirementsManager.addJobRequirement(req13);
        jobRequirementsManager.addJobRequirement(req14);
        jobRequirementsManager.addJobRequirement(req15);
        jobRequirementsManager.addJobRequirement(req16);
        jobRequirementsManager.addJobRequirement(req17);
        jobRequirementsManager.addJobRequirement(req18);
        jobRequirementsManager.addJobRequirement(req19);
        jobRequirementsManager.addJobRequirement(req20);

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
        
        // Frontend Developer (HTML, CSS, JavaScript)
        DoublyLinkedListInterface<JobRequirements> jrFrontend = new DoublyLinkedList<>();
        jrFrontend.add(new JobRequirements("HTML", "4", "Frontend"));    // req13
        jrFrontend.add(new JobRequirements("CSS", "3", "Frontend"));     // req14
        jrFrontend.add(new JobRequirements("JavaScript", "4", "Frontend")); // req15
        Job job10 = new Job("Frontend Developer", "KL", 2, jrFrontend, 3500);

        // Project Manager (Leadership, Project Management, Agile)
        DoublyLinkedListInterface<JobRequirements> jrPM = new DoublyLinkedList<>();
        jrPM.add(req1); // Leadership
        jrPM.add(new JobRequirements("Project Management", "5", "Management")); // req11
        jrPM.add(new JobRequirements("Agile", "3", "Methodology")); // req12
        Job job11 = new Job("Project Manager", "Selangor", 6, jrPM, 7000);

        // Backend Developer (Java, SQL, Communication)
        DoublyLinkedListInterface<JobRequirements> jrBackend = new DoublyLinkedList<>();
        jrBackend.add(new JobRequirements("Java", "4", "Programming")); // req7
        jrBackend.add(new JobRequirements("SQL", "4", "Database")); // req10
        jrBackend.add(new JobRequirements("Communication", "4", "Soft Skill")); // req8
        Job job12 = new Job("Backend Developer", "KL", 3, jrBackend, 4200);

        // Database Administrator (SQL, MongoDB, Linux)
        DoublyLinkedListInterface<JobRequirements> jrDBA = new DoublyLinkedList<>();
        jrDBA.add(req10); // SQL
        jrDBA.add(new JobRequirements("MongoDB", "3", "Database")); // req16
        jrDBA.add(new JobRequirements("Linux", "4", "System")); // req19
        Job job13 = new Job("Database Administrator", "Penang", 4, jrDBA, 5000);

        // AI Engineer (Python, TensorFlow, Data Analysis)
        DoublyLinkedListInterface<JobRequirements> jrAI = new DoublyLinkedList<>();
        jrAI.add(req2); // Python
        jrAI.add(new JobRequirements("TensorFlow", "3", "AI/ML")); // req17
        jrAI.add(req3); // Data Analysis
        Job job14 = new Job("AI Engineer", "KL", 4, jrAI, 5500);

        // UX Designer (UI/UX Design, Communication, Figma)
        DoublyLinkedListInterface<JobRequirements> jrUX = new DoublyLinkedList<>();
        jrUX.add(new JobRequirements("UI/UX Design", "4", "Design")); // req18
        jrUX.add(req8); // Communication
        jrUX.add(new JobRequirements("Figma", "3", "Design")); // req20
        Job job15 = new Job("UX Designer", "Sabah", 2, jrUX, 3700);

        // IT Support Specialist (Communication, Leadership, Linux)
        DoublyLinkedListInterface<JobRequirements> jrITSupport = new DoublyLinkedList<>();
        jrITSupport.add(req8); // Communication
        jrITSupport.add(req1); // Leadership
        jrITSupport.add(req19); // Linux
        Job job16 = new Job("IT Support Specialist", "Johor", 3, jrITSupport, 3300);

        // Cybersecurity Analyst (Linux, AWS, Leadership)
        DoublyLinkedListInterface<JobRequirements> jrCyber = new DoublyLinkedList<>();
        jrCyber.add(req19); // Linux
        jrCyber.add(req6);  // AWS
        jrCyber.add(req1);  // Leadership
        Job job17 = new Job("Cybersecurity Analyst", "Sarawak", 5, jrCyber, 6000);
        
        // AI Researcher
        DoublyLinkedListInterface<JobRequirements> jrAI2 = new DoublyLinkedList<>();
        jrAI.add(new JobRequirements("Python", "5", "Programming"));
        jrAI.add(new JobRequirements("Machine Learning", "4", "AI"));
        jrAI.add(req1); // Leadership
        Job job18 = new Job("AI Researcher", "KL", 5, jrAI2, 7000);

        // IT Support
        DoublyLinkedListInterface<JobRequirements> jrSupport = new DoublyLinkedList<>();
        jrSupport.add(new JobRequirements("Communication", "3", "Soft Skill"));
        jrSupport.add(new JobRequirements("Troubleshooting", "3", "IT"));
        Job job19 = new Job("IT Support", "Sabah", 1, jrSupport, 2500);

        // Product Manager
        DoublyLinkedListInterface<JobRequirements> jrPM2 = new DoublyLinkedList<>();
        jrPM.add(new JobRequirements("Leadership", "4", "Mental"));
        jrPM.add(new JobRequirements("Communication", "4", "Soft Skill"));
        jrPM.add(req3); // Data Analysis
        Job job20 = new Job("Product Manager", "Penang", 5, jrPM2, 5500);




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
        jobManager.addJob(job10);
        jobManager.addJob(job11);
        jobManager.addJob(job12);
        jobManager.addJob(job13);
        jobManager.addJob(job14);
        jobManager.addJob(job15);
        jobManager.addJob(job16);
        jobManager.addJob(job17);
        jobManager.addJob(job18);
        jobManager.addJob(job19);
        jobManager.addJob(job20);

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
        JobPost jobPost11 = new JobPost(job10, companyABC);      // Frontend Developer @ ABC Tech
        JobPost jobPost12 = new JobPost(job11, companyLMN);      // Project Manager @ LMN Global
        JobPost jobPost13 = new JobPost(job12, companyDBC);      // Backend Developer @ DBC Systems
        JobPost jobPost14 = new JobPost(job13, companyXYZ);      // DBA @ XYZ Corp
        JobPost jobPost15 = new JobPost(job14, companyABC);      // AI Engineer @ ABC Tech
        JobPost jobPost16 = new JobPost(job15, companyLMN);      // UX Designer @ LMN Global
        JobPost jobPost17 = new JobPost(job16, companyDBC);      // IT Support @ DBC Systems
        JobPost jobPost18 = new JobPost(job17, companyXYZ);      // Cybersecurity Analyst @ XYZ Corp
        JobPost jobPost19 = new JobPost(job18, companyABC);     
        JobPost jobPost20 = new JobPost(job19, companyLMN);      
        JobPost jobPost21 = new JobPost(job20, companyDBC);
        JobPost jobPost22 = new JobPost(job2, companyABC);
        JobPost jobPost23 = new JobPost(job4, companyABC);
        JobPost jobPost24 = new JobPost(job6, companyDBC);
        JobPost jobPost25 = new JobPost(job8, companyXYZ);
        JobPost jobPost26 = new JobPost(job9, companyLMN);
        JobPost jobPost27 = new  JobPost(job7, companyLMN);

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
        jobPostManager.addJobPost(jobPost11);
        jobPostManager.addJobPost(jobPost12);
        jobPostManager.addJobPost(jobPost13);
        jobPostManager.addJobPost(jobPost14);
        jobPostManager.addJobPost(jobPost15);
        jobPostManager.addJobPost(jobPost16);
        jobPostManager.addJobPost(jobPost17);
        jobPostManager.addJobPost(jobPost18);
        jobPostManager.addJobPost(jobPost19);
        jobPostManager.addJobPost(jobPost20);
        jobPostManager.addJobPost(jobPost21);
        jobPostManager.addJobPost(jobPost22);
        jobPostManager.addJobPost(jobPost23);
        jobPostManager.addJobPost(jobPost24);
        jobPostManager.addJobPost(jobPost25);
        jobPostManager.addJobPost(jobPost26);
        jobPostManager.addJobPost(jobPost27);



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
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant1, jobPost11));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant5, jobPost11));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant6, jobPost12));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant3, jobPost12));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant2, jobPost13));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant4, jobPost13));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant7, jobPost14));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant8, jobPost14));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant9, jobPost15));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant10, jobPost15));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant1, jobPost16));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant2, jobPost16));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant5, jobPost17));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant6, jobPost17));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant7, jobPost18));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant8, jobPost18));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant1, jobPost2));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant3, jobPost6));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant4, jobPost5));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant7, jobPost3));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant6, jobPost9));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant5, jobPost7));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant10, jobPost14));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant2, jobPost15));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant9, jobPost16));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant1, jobPost11));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant2, jobPost12));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant3, jobPost13));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant4, jobPost14));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant5, jobPost15));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant6, jobPost16));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant7, jobPost17));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant8, jobPost18));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant9, jobPost19));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant10, jobPost11));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant1, jobPost12));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant2, jobPost13));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant3, jobPost14));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant4, jobPost15));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant5, jobPost16));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant6, jobPost17));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant7, jobPost18));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant8, jobPost19));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant9, jobPost10));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant10, jobPost9));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant1, jobPost8));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant2, jobPost7));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant3, jobPost6));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant4, jobPost5));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant5, jobPost4));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant1, jobPost11));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant2, jobPost12));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant3, jobPost13));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant4, jobPost14));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant5, jobPost15));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant6, jobPost16));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant7, jobPost17));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant8, jobPost18));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant9, jobPost19));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant10, jobPost11));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant1, jobPost12));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant2, jobPost13));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant3, jobPost14));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant4, jobPost15));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant5, jobPost16));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant6, jobPost17));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant7, jobPost18));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant8, jobPost19));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant9, jobPost10));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant10, jobPost9));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant1, jobPost8));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant2, jobPost7));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant3, jobPost6));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant4, jobPost5));
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant5, jobPost4));
        // Applicant 1 Applications
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant1, jobPost2)); // Qualified
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant1, jobPost3)); // Unqualified
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant1, jobPost4)); // Unqualified

        // Applicant 2 Applications
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant2, jobPost5)); // Qualified
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant2, jobPost6)); // Unqualified
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant2, jobPost7)); // Unqualified

        // Applicant 3 Applications
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant3, jobPost8)); // Qualified
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant3, jobPost9)); // Unqualified
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant3, jobPost10)); // Unqualified

        // Applicant 4 Applications
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant4, jobPost11)); // Qualified
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant4, jobPost12)); // Unqualified
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant4, jobPost13)); // Unqualified

        // Applicant 5 Applications
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant5, jobPost14)); // Qualified
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant5, jobPost15)); // Unqualified
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant5, jobPost16)); // Unqualified

        // Applicant 6 Applications
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant6, jobPost17)); // Qualified
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant6, jobPost18)); // Unqualified
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant6, jobPost19)); // Unqualified

        // Applicant 7 Applications
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant7, jobPost20)); // Qualified
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant7, jobPost1)); // Unqualified
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant7, jobPost2)); // Unqualified

        // Applicant 8 Applications
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant8, jobPost3)); // Qualified
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant8, jobPost4)); // Unqualified
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant8, jobPost5)); // Unqualified

        // Applicant 9 Applications
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant9, jobPost6)); // Qualified
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant9, jobPost7)); // Unqualified
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant9, jobPost8)); // Unqualified

        // Applicant 10 Applications
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant10, jobPost9)); // Qualified
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant10, jobPost10)); // Unqualified
        applicantAppliedJobManager.addApplicantAppliedJob(new ApplicantAppliedJob(applicant10, jobPost11)); // Unqualified





        // 7. Time Slots
        timeSlotManager.addTimeSlot(new TimeSlot("5.00pm", "6/3/2025", "Bukit Bintang"));
        timeSlotManager.addTimeSlot(new TimeSlot("7.00pm", "6/3/2025", "Bukit Bintang"));
        timeSlotManager.addTimeSlot(new TimeSlot("9.00pm", "6/3/2025", "Bukit Bintang"));
        timeSlotManager.addTimeSlot(new TimeSlot("10.00am", "7/3/2025", "Marina Bay"));
        timeSlotManager.addTimeSlot(new TimeSlot("2.00pm", "7/3/2025", "Marina Bay"));
        timeSlotManager.addTimeSlot(new TimeSlot("11.00am", "7/3/2025", "Marina Bay"));
        timeSlotManager.addTimeSlot(new TimeSlot("3.00pm", "7/3/2025", "Marina Bay"));
        timeSlotManager.addTimeSlot(new TimeSlot("10.00am", "8/3/2025", "KLCC"));
        timeSlotManager.addTimeSlot(new TimeSlot("1.00pm", "8/3/2025", "KLCC"));
        timeSlotManager.addTimeSlot(new TimeSlot("4.00pm", "8/3/2025", "KLCC"));
        timeSlotManager.addTimeSlot(new TimeSlot("9.00am", "9/3/2025", "Cyberjaya"));
        timeSlotManager.addTimeSlot(new TimeSlot("12.00pm", "9/3/2025", "Cyberjaya"));
        timeSlotManager.addTimeSlot(new TimeSlot("3.00pm", "9/3/2025", "Cyberjaya"));
        timeSlotManager.addTimeSlot(new TimeSlot("10.00am", "10/3/2025", "Penang Sentral"));
        timeSlotManager.addTimeSlot(new TimeSlot("2.00pm", "10/3/2025", "Penang Sentral"));
        timeSlotManager.addTimeSlot(new TimeSlot("5.00pm", "10/3/2025", "Penang Sentral"));
        timeSlotManager.addTimeSlot(new TimeSlot("11.00am", "11/3/2025", "Mid Valley"));
        timeSlotManager.addTimeSlot(new TimeSlot("1.00pm", "11/3/2025", "Mid Valley"));
        timeSlotManager.addTimeSlot(new TimeSlot("4.00pm", "11/3/2025", "Mid Valley"));
        timeSlotManager.addTimeSlot(new TimeSlot("9.00am", "12/3/2025", "Sunway Pyramid"));
        timeSlotManager.addTimeSlot(new TimeSlot("12.00pm", "12/3/2025", "Sunway Pyramid"));
        timeSlotManager.addTimeSlot(new TimeSlot("3.00pm", "12/3/2025", "Sunway Pyramid"));
        timeSlotManager.addTimeSlot(new TimeSlot("10.00am", "13/3/2025", "Gurney Plaza"));
        timeSlotManager.addTimeSlot(new TimeSlot("2.00pm", "13/3/2025", "Gurney Plaza"));
        timeSlotManager.addTimeSlot(new TimeSlot("6.00pm", "13/3/2025", "Gurney Plaza"));
        timeSlotManager.addTimeSlot(new TimeSlot("11.00am", "8/3/2025", "Putrajaya"));
        timeSlotManager.addTimeSlot(new TimeSlot("1.00pm", "8/3/2025", "Putrajaya"));
        timeSlotManager.addTimeSlot(new TimeSlot("3.00pm", "8/3/2025", "Cyberjaya"));
        timeSlotManager.addTimeSlot(new TimeSlot("4.00pm", "9/3/2025", "KL Sentral"));
        timeSlotManager.addTimeSlot(new TimeSlot("5.00pm", "9/3/2025", "KL Sentral"));
        timeSlotManager.addTimeSlot(new TimeSlot("6.00pm", "10/3/2025", "George Town"));
        timeSlotManager.addTimeSlot(new TimeSlot("10.00am", "11/3/2025", "Johor Bahru"));
        timeSlotManager.addTimeSlot(new TimeSlot("12.00pm", "11/3/2025", "Johor Bahru"));



        System.out.println("Mock Data Generated Successfully!");
    }
}
