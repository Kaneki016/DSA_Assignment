package controller;
// Author: Lai Yoke Yau, Lim Wai Ming and Ma Jian Chun

import adt.*;
import entities.*;
import boundary.*;
import java.util.Comparator;

public class ApplicantAppliedJobManager {

    private static ApplicantAppliedJobManager instance;
    private static DoublyLinkedListInterface<ApplicantAppliedJob> applicantAppliedJob;
    private static DoublyLinkedListInterface<ApplicantAppliedJob> matchRecords;
    private static DoublyLinkedListInterface<Interview> interviews;

    // Boundary
    private static InputUI inputUI = new InputUI();
    private static MenuUI menuUI = new MenuUI();

    private ApplicantAppliedJobManager() {
        applicantAppliedJob = new DoublyLinkedList<>();
    }

    public static ApplicantAppliedJobManager getInstance() {
        if (instance == null) {
            instance = new ApplicantAppliedJobManager();
        }
        return instance;
    }

    // Controllers
    private static ApplicantManager applicantManager = ApplicantManager.getInstance();
    private static JobPostManager jobPostManager = JobPostManager.getInstance();
    private static InterviewManager interviewManager = InterviewManager.getInstance();
    private static CompanyManager companyManager = CompanyManager.getInstance();

    public void viewAvailableJobs() {

        jobPostManager.displayJobPosts();
        inputUI.getInput("Press Enter to continue...");

    }

    public void addApplicantAppliedJob(ApplicantAppliedJob newApplication) {
        applicantAppliedJob.add(newApplication);
    }

    public void applyJob() {
        System.out.println("\n======= Apply for a Job =======");

        // Step 1: Get Applicant ID
        String applicantId = inputUI.getInput("Enter your Applicant ID: ");
        Applicant applicant = applicantManager.findApplicantById(applicantId);

        // Step 2: Validate Applicant
        if (applicant == null) {
            inputUI.displayMessage("❌ Applicant not found! Please check your ID.");
            inputUI.getInput("Press Enter to continue...");
            return;
        }

        // Step 3: Retrieve available jobs
        DoublyLinkedListInterface<JobPost> jobPosts = jobPostManager.getJobPostList();
        if (jobPosts.isEmpty()) {
            inputUI.displayMessage("❌ No available jobs at the moment.");
            return;
        }

        // Step 4: Display available jobs
       // jobPostManager.displayJobPosts();

        //Step 4: TESTING MATCHED JOB
        jobPostManager.displayMatchedJobPosts(applicant);

        // Step 5: Get user choice
        int choice = inputUI.getValidIntInput("\nEnter the job number to apply for: ", 1, jobPosts.getSize());

        // Step 6: Validate choice and get the selected job
        JobPost selectedJobPost = jobPosts.get(choice - 1);

        boolean applicationExists = false;
        for (ApplicantAppliedJob aaj : applicantAppliedJob) {
            if (aaj.getApplicant().equals(applicant) && aaj.getJobPost().equals(selectedJobPost)) {
                applicationExists = true;
                break;
            }
        }

        if (applicationExists) {
            inputUI.displayMessage("❌ Error: Application already exists!");
            return;
        } else {
            ApplicantAppliedJob newApplication = new ApplicantAppliedJob(applicant, selectedJobPost);
            addApplicantAppliedJob(newApplication);
        }

        inputUI.displayMessage("\n✅ Application submitted successfully!");
        inputUI.displayMessage("You applied for: " + selectedJobPost.getJob().getTitle()
                + " at " + selectedJobPost.getCompany().getCompanyName());
        inputUI.getInput("Press Enter to continue...");
      
    }

    // remove
    public void removeApplicantAppliedJob(ApplicantAppliedJob appliedJob) {
        if (applicantAppliedJob.contains(appliedJob)) {
            applicantAppliedJob.removeSpecific(appliedJob);
            inputUI.displayMessage("Applicant applied job removed!\n");
        } else {
            inputUI.displayMessage("Error: Job application not found!\n");
        }
    }

    public void displayJobApplicationSummaryTable() {
        DoublyLinkedListInterface<ApplicantAppliedJob> appliedJobs = getApplicantAppliedJobs();

        final int REPORT_WIDTH = 125;

        inputUI.displayMessage("=".repeat(REPORT_WIDTH));
        inputUI.displayMessage(menuUI.centerText("JOB APPLICATION SUMMARY TABLE", REPORT_WIDTH));
        inputUI.displayMessage("=".repeat(REPORT_WIDTH));
        inputUI.displayMessage(String.format("| %-50s | %-40s | %-15s |", "Job Title", "Company", "Applicants"));
        inputUI.displayMessage("-".repeat(REPORT_WIDTH));

        DoublyLinkedListInterface<String> trackedKeys = new DoublyLinkedList<>();
        DoublyLinkedListInterface<Integer> applicantCounts = new DoublyLinkedList<>();

        for (ApplicantAppliedJob app : appliedJobs) {
            String jobTitle = app.getJobPost().getJob().getTitle();
            String companyName = app.getJobPost().getCompany().getCompanyName();
            String key = jobTitle + "@" + companyName;

            boolean found = false;
            for (int i = 0; i < trackedKeys.getSize(); i++) {
                if (trackedKeys.get(i).equalsIgnoreCase(key)) {
                    applicantCounts.replace(i, applicantCounts.get(i) + 1);
                    found = true;
                    break;
                }
            }

            if (!found) {
                trackedKeys.add(key);
                applicantCounts.add(1);
            }
        }

        for (int i = 0; i < trackedKeys.getSize(); i++) {
            String[] parts = trackedKeys.get(i).split("@");
            String jobTitle = parts[0];
            String company = parts[1];
            int count = applicantCounts.get(i);

            inputUI.displayMessage(String.format("| %-50s | %-40s | %-15d |", jobTitle, company, count));
        }

        inputUI.displayMessage("=".repeat(REPORT_WIDTH));
    }

    public void displayCompanyApplicantReport() {
        menuUI.printReportHeader("Applicant Summary Report");
        displayJobApplicationSummaryTable(); // Table first

        DoublyLinkedListInterface<Company> companies = companyManager.getCompanies();
        DoublyLinkedListInterface<ApplicantAppliedJob> appliedJobs = getApplicantAppliedJobs();

        DoublyLinkedListInterface<String> companyNames = new DoublyLinkedList<>();
        DoublyLinkedListInterface<Integer> applicantCounts = countApplicantsPerCompany(companies, appliedJobs, companyNames);
        DoublyLinkedListInterface<String> mostAppliedCompanies = findMostAppliedCompanies(companyNames, applicantCounts);

        DoublyLinkedListInterface<String> jobTitles = new DoublyLinkedList<>();
        DoublyLinkedListInterface<Integer> jobCounts = new DoublyLinkedList<>();
        int highestJobCount = countApplicantsPerJob(appliedJobs, jobTitles, jobCounts);
        DoublyLinkedListInterface<String> mostAppliedJobs = findMostAppliedJobs(jobTitles, jobCounts, highestJobCount);

        printBarChart(companyNames, applicantCounts);
        printSummary(companyNames.getSize(), appliedJobs.getSize(), mostAppliedCompanies, mostAppliedJobs, applicantCounts, jobCounts);
        inputUI.getInput("<< Press Enter to continue >>");
    }

    private DoublyLinkedListInterface<Integer> countApplicantsPerCompany(
            DoublyLinkedListInterface<Company> companies,
            DoublyLinkedListInterface<ApplicantAppliedJob> appliedJobs,
            DoublyLinkedListInterface<String> companyNames) {

        DoublyLinkedListInterface<Integer> counts = new DoublyLinkedList<>();

        for (Company company : companies) {
            int count = 0;
            for (ApplicantAppliedJob app : appliedJobs) {
                if (app.getJobPost().getCompany().equals(company)) {
                    count++;
                }
            }
            companyNames.add(company.getCompanyName());
            counts.add(count);
        }
        return counts;
    }

    private DoublyLinkedListInterface<String> findMostAppliedCompanies(
            DoublyLinkedListInterface<String> companyNames,
            DoublyLinkedListInterface<Integer> counts) {

        DoublyLinkedListInterface<String> result = new DoublyLinkedList<>();
        int max = 0;

        for (int i = 0; i < counts.getSize(); i++) {
            int count = counts.get(i);
            if (count > max) {
                max = count;
                result.clear();
                result.add(companyNames.get(i));
            } else if (count == max && count > 0) {
                result.add(companyNames.get(i));
            }
        }
        return result;
    }

    private int countApplicantsPerJob(
            DoublyLinkedListInterface<ApplicantAppliedJob> appliedJobs,
            DoublyLinkedListInterface<String> jobTitles,
            DoublyLinkedListInterface<Integer> jobCounts) {

        int max = 0;

        for (ApplicantAppliedJob app : appliedJobs) {
            String title = app.getJobPost().getJob().getTitle();
            boolean found = false;
            for (int i = 0; i < jobTitles.getSize(); i++) {
                if (jobTitles.get(i).equalsIgnoreCase(title)) {
                    jobCounts.replace(i, jobCounts.get(i) + 1);
                    found = true;
                    break;
                }
            }
            if (!found) {
                jobTitles.add(title);
                jobCounts.add(1);
            }
        }

        for (int count : jobCounts) {
            if (count > max) {
                max = count;
            }
        }

        return max;
    }

    private DoublyLinkedListInterface<String> findMostAppliedJobs(DoublyLinkedListInterface<String> jobTitles, DoublyLinkedListInterface<Integer> jobCounts, int highestCount) {

        DoublyLinkedListInterface<String> result = new DoublyLinkedList<>();

        for (int i = 0; i < jobCounts.getSize(); i++) {
            if (jobCounts.get(i) == highestCount) {
                result.add(jobTitles.get(i));
            }
        }
        return result;
    }

    private void printBarChart(DoublyLinkedListInterface<String> companyNames,
            DoublyLinkedListInterface<Integer> applicantCounts) {

        final int COLUMN_WIDTH = 8;
        int max = 0;
        for (int count : applicantCounts) {
            if (count > max) {
                max = count;
            }
        }

        inputUI.displayMessage("No. of Applicants");

        for (int level = max; level >= 1; level--) {
            StringBuilder row = new StringBuilder();
            row.append(String.format("%2d |", level));
            for (int count : applicantCounts) {
                String bar = count >= level ? "*" : " ";
                row.append(String.format("   %-2s   ", bar));
            }
            inputUI.displayMessage(row.toString());
        }

        StringBuilder separator = new StringBuilder("   +");
        for (int i = 0; i < companyNames.getSize(); i++) {
            separator.append("-".repeat(COLUMN_WIDTH - 1)).append("+");
        }
        inputUI.displayMessage(separator.append("-> Companies").toString());

        StringBuilder labels = new StringBuilder("    ");  // Start after Y-axis space
        for (int i = 0; i < companyNames.getSize(); i++) {
            String name = companyNames.get(i);
            name = name.length() > 3 ? name.substring(0, 3) : name;

            int totalPadding = COLUMN_WIDTH;                            // COLUMN_WIDTH = 8 and name is "ABC" (3 chars):
            int leftPadding = (totalPadding - name.length()) / 2;       //   Left Padding = (8 - 3) / 2 = 2
            int rightPadding = totalPadding - name.length() - leftPadding; /// Right Padding = 8 - 3 - 2 = 3

            labels.append(" ".repeat(leftPadding)).append(name).append(" ".repeat(rightPadding));
        }
        inputUI.displayMessage(labels.toString());

    }

    private void printSummary(int totalCompanies, int totalApplicants,
            DoublyLinkedListInterface<String> mostCompanies,
            DoublyLinkedListInterface<String> mostJobs,
            DoublyLinkedListInterface<Integer> companyCounts,
            DoublyLinkedListInterface<Integer> jobCounts) {

        int maxApplicants = 0, maxJobs = 0;
        for (int c : companyCounts) {
            if (c > maxApplicants) {
                maxApplicants = c;
            }
        }
        for (int j : jobCounts) {
            if (j > maxJobs) {
                maxJobs = j;
            }
        }

        inputUI.displayMessage("\nSummary:");
        inputUI.displayMessage("Total Companies       : " + totalCompanies);
        inputUI.displayMessage("Total Applicants      : " + totalApplicants);

        StringBuilder companyLine = new StringBuilder("Most Applied Company  : ");
        for (int i = 0; i < mostCompanies.getSize(); i++) {
            if (i > 0) {
                companyLine.append(", ");
            }
            companyLine.append(mostCompanies.get(i));
        }
        companyLine.append(" (" + maxApplicants + " applicants)");
        inputUI.displayMessage(companyLine.toString());

        StringBuilder jobLine = new StringBuilder("Most Applied Job Title: ");
        for (int i = 0; i < mostJobs.getSize(); i++) {
            if (i > 0) {
                jobLine.append(", ");
            }
            jobLine.append(mostJobs.get(i));
        }
        jobLine.append(" (" + maxJobs + " applicants)");
        inputUI.displayMessage(jobLine.toString());

        menuUI.printEndOfReport(125);
    }

    //Accept or rejct 
    public void handleCheckMyApplications() {
        String applicantId = inputUI.getInput("\nEnter your Applicant ID (or type 'back' to return): ");
        if (applicantId.equalsIgnoreCase("back")) {
            inputUI.displayMessage("Returning to main menu...");
            return;
        }

        Applicant applicant = applicantManager.findApplicantById(applicantId);
        if (applicant == null) {
            inputUI.displayMessage("❌ Applicant not found! Please check your ID.");
            return;
        }

        DoublyLinkedListInterface<Interview> interviews = interviewManager.getInterviews();
        DoublyLinkedListInterface<ApplicantAppliedJob> userApplications = new DoublyLinkedList<>();
        int index = 1;

        for (ApplicantAppliedJob app : applicantAppliedJob) {
            if (app.getApplicant().equals(applicant)) {
                userApplications.add(app);
            }
        }

        if (userApplications.isEmpty()) {
            inputUI.displayMessage("❌ You have not applied for any jobs yet.");
            return;
        }

        // ✅ Print nicely formatted table using MenuUI
        menuUI.printApplicantApplicationsTable(userApplications, interviews);
        inputUI.getInput("Press Enter to continue...");
    }

    // New getter method to expose the list
    public DoublyLinkedListInterface<ApplicantAppliedJob> getApplicantAppliedJobs() {
        return applicantAppliedJob;
    }

    // ====================== Matching Method - Lai Yoke Yau
    // =============================================================
    // Skill Matching with Proficiency Levels - Returns Match Score
    public double skillMatch(JobPost jobPost, Applicant applicant) {
        double totalWeight = 0, matchScore = 0;

        for (JobRequirements requiredSkill : jobPost.getJob().getJobRequirements()) {
            int requiredProficiency = Integer.parseInt(requiredSkill.getProficiencyLevel());
            if (requiredProficiency == 0) {
                continue;
            }

            int applicantProficiency = 0;
            for (Skill skill : applicant.getSkills()) {
                if (skill.getName().equalsIgnoreCase(requiredSkill.getName())) {
                    applicantProficiency = skill.getProficiency_level();
                    break;
                }
            }

            if (applicantProficiency >= requiredProficiency) {
                matchScore += Math.min((double) applicantProficiency / requiredProficiency, 1.0);
            }
            totalWeight++;
        }

        return (totalWeight > 0) ? matchScore / totalWeight : 0; // ✅ Return match score instead of storing applicants
    }

    // Experience Level Matching
    public double experienceMatch(JobPost jobPost, Applicant applicant) {
        int jobExperienceRequired = jobPost.getJob().getRequired_experience();
        int applicantExperience = applicant.getYearsOfExperience();

        return (jobExperienceRequired == 0) ? 1.0 : Math.min((double) applicantExperience / jobExperienceRequired, 1.0);
    }

    // Location Preference Matching
    public double locationMatch(JobPost jobPost, Applicant applicant) {
        return applicant.getLocation().equalsIgnoreCase(jobPost.getJob().getLocation()) ? 1.0 : 0.0;
    }

    // Total Match Score Matching
    public void totalMatchScore(DoublyLinkedListInterface<JobPost> jobPosts, DoublyLinkedListInterface<Applicant> applicants) {
        if (matchRecords == null) {
            matchRecords = new DoublyLinkedList<>(); // Initialize if null
        } else {
            matchRecords.clear(); // ✅ Clear previous matches if not null
        }

        // Loop through all job posts
        for (JobPost jobPost : jobPosts) {
            // Loop through matchRecords and check only applicants who applied to the job post
            for (ApplicantAppliedJob record : applicantAppliedJob) {
                // If the applicant has applied for this job
                if (record.getJobPost().equals(jobPost)) {
                    Applicant applicant = record.getApplicant();

                    // Calculate match scores only for applied applicants
                    double skillScore = skillMatch(jobPost, applicant);
                    double experienceScore = experienceMatch(jobPost, applicant);
                    double locationScore = locationMatch(jobPost, applicant);

                    // Define weightage (adjustable)
                    double skillWeight = 0.5;
                    double experienceWeight = 0.3;
                    double locationWeight = 0.2;

                    // Weighted total match score
                    double totalScore = (skillScore * skillWeight)
                            + (experienceScore * experienceWeight)
                            + (locationScore * locationWeight);

                    // Check if this match already exists
                    if (!containsMatch(applicant, jobPost)) {
                        matchRecords.add(new ApplicantAppliedJob(applicant, jobPost, totalScore));
                    }
                }
            }
        }
    }

    // ====================== For Applicant
    // ==========================================
    // Call method for finding suitable jobs for an applicant based on skills
    public void ApplicantSkillMatch() {
        System.out.println("\n🔍 Job Search Based on Your Skills 🔍");

        // Get applicant ID from user input
        String applicantId = inputUI.getInput("Enter Applicant ID: ");
        Applicant applicant = applicantManager.findApplicantById(applicantId);

        // Check if applicant exists
        if (applicant == null) {
            System.out.println("❌ Applicant not found. Please check the ID and try again.");
            return;
        }

        // Get all job applications that the applicant has applied for
        DoublyLinkedListInterface<ApplicantAppliedJob> applicantAppliedJobs = new DoublyLinkedList<>();
        for (ApplicantAppliedJob record : applicantAppliedJob) {
            if (record.getApplicant().getApplicantId().equals(applicantId)) {
                applicantAppliedJobs.add(record);
            }
        }

        // If the applicant has not applied to any jobs, display message and return
        if (applicantAppliedJobs.isEmpty()) {
            System.out.println("📋 No jobs found for the selected applicant.");
            return;
        }

        // Get suitable and not suitable jobs based on skills
        DoublyLinkedListInterface<JobPost> suitableJobs = new DoublyLinkedList<>();
        DoublyLinkedListInterface<JobPost> notSuitableJobs = new DoublyLinkedList<>();

        // Iterate through the applied jobs and calculate skill match score
        for (ApplicantAppliedJob record : applicantAppliedJobs) {
            JobPost jobPost = record.getJobPost(); // Get job post that applicant applied for
            double skillScore = skillMatch(jobPost, applicant); // Calculate skill match score

            if (skillScore >= 0.5) { // Only add jobs where the score is above threshold
                suitableJobs.add(jobPost);
            } else {
                notSuitableJobs.add(jobPost);
            }
        }

        // Display jobs based on suitability
        menuUI.listJobPosts(applicant, suitableJobs, "Suitable");
        menuUI.listJobPosts(applicant, notSuitableJobs, "Unsuitable");
    }

    // Call method for finding suitable jobs for an applicant based on experience
    public void ApplicantExperienceMatch() {
        String applicantId = inputUI.getInput("Enter Applicant ID: ");
        Applicant applicant = applicantManager.findApplicantById(applicantId);

        // Check if the applicant exists
        if (applicant == null) {
            System.out.println("❌ Applicant not found.");
            return;
        }

        // Get all job applications that the applicant has applied for
        DoublyLinkedListInterface<ApplicantAppliedJob> applicantAppliedJobs = new DoublyLinkedList<>();
        for (ApplicantAppliedJob record : applicantAppliedJob) {
            if (record.getApplicant().getApplicantId().equals(applicantId)) {
                applicantAppliedJobs.add(record);
            }
        }

        // If the applicant has not applied to any jobs, display message and return
        if (applicantAppliedJobs.isEmpty()) {
            System.out.println("📋 No jobs found for the selected applicant.");
            return;
        }

        // Prepare to categorize jobs as suitable and not suitable
        DoublyLinkedListInterface<JobPost> suitableJobs = new DoublyLinkedList<>();
        DoublyLinkedListInterface<JobPost> notSuitableJobs = new DoublyLinkedList<>();

        // Iterate through the applied jobs and calculate experience match score
        for (ApplicantAppliedJob record : applicantAppliedJobs) {
            JobPost jobPost = record.getJobPost(); // Get job post that applicant applied for
            double experienceScore = experienceMatch(jobPost, applicant); // Calculate experience match score

            // If experience match score is 1 (perfect match), categorize as suitable
            if (experienceScore == 1) {
                suitableJobs.add(jobPost);
            } else {
                notSuitableJobs.add(jobPost);
            }
        }

        // Display jobs based on suitability
        menuUI.listJobPosts(applicant, suitableJobs, "Suitable");
        menuUI.listJobPosts(applicant, notSuitableJobs, "Unsuitable");
    }

    ///Call method for finding suitable jobs for an applicant based on applicant location
    public void ApplicantLocationMatch() {
        String applicantId = inputUI.getInput("Enter Applicant ID: ");
        Applicant applicant = applicantManager.findApplicantById(applicantId);

        // Check if the applicant exists
        if (applicant == null) {
            System.out.println("❌ Applicant not found.");
            return;
        }

        // Get all job applications that the applicant has applied for
        DoublyLinkedListInterface<ApplicantAppliedJob> applicantAppliedJobs = new DoublyLinkedList<>();
        for (ApplicantAppliedJob record : applicantAppliedJob) {
            if (record.getApplicant().getApplicantId().equals(applicantId)) {
                applicantAppliedJobs.add(record);
            }
        }

        // If the applicant has not applied to any jobs, display message and return
        if (applicantAppliedJobs.isEmpty()) {
            System.out.println("📋 No jobs found for the selected applicant.");
            return;
        }

        // Prepare to categorize jobs as suitable and not suitable
        DoublyLinkedListInterface<JobPost> suitableJobs = new DoublyLinkedList<>();
        DoublyLinkedListInterface<JobPost> notSuitableJobs = new DoublyLinkedList<>();

        // Iterate through the applied jobs and calculate location match score
        for (ApplicantAppliedJob record : applicantAppliedJobs) {
            JobPost jobPost = record.getJobPost(); // Get job post that applicant applied for
            double locationScore = locationMatch(jobPost, applicant); // Calculate location match score

            // If location match score is 1 (perfect match), categorize as suitable
            if (locationScore == 1) {
                suitableJobs.add(jobPost);
            } else {
                notSuitableJobs.add(jobPost);
            }
        }

        // Display jobs based on suitability
        menuUI.listJobPosts(applicant, suitableJobs, "Suitable");
        menuUI.listJobPosts(applicant, notSuitableJobs, "Unsuitable");
    }

    // ======================== For Company
    // ===========================================
    // Call method for skill matching
    public void CompanySkillMatch() {
        String companyId = inputUI.getInput("Enter Company ID: ");
        DoublyLinkedListInterface<JobPost> jobPosts = jobPostManager.getJobPostsByCompanyId(companyId);

        if (jobPosts.isEmpty()) {
            System.out.println("❌ No job posts found for the given company ID.");
            return;
        }

        // Let the user select a job post from the available job posts
        JobPost selectedJobPost = jobPostManager.selectJobPost(jobPosts);
        if (selectedJobPost == null) {
            System.out.println("❌ No job post selected.");
            return;
        }

        // Filter applicants who have applied to this job
        DoublyLinkedListInterface<ApplicantAppliedJob> appliedApplicants = new DoublyLinkedList<>();
        for (ApplicantAppliedJob record : applicantAppliedJob) {
            if (record.getJobPost().equals(selectedJobPost)) {
                appliedApplicants.add(record);
            }
        }

        // If no applicants have applied for the selected job, display message and return
        if (appliedApplicants.isEmpty()) {
            System.out.println("📋 No applicants have applied for the selected job post.");
            return;
        }

        // Lists to store suitable and non-qualified applicants
        DoublyLinkedListInterface<Applicant> suitableApplicants = new DoublyLinkedList<>();
        DoublyLinkedListInterface<Applicant> nonQualifiedApplicants = new DoublyLinkedList<>();

        // Loop through all applicants who applied for this job and calculate skill match score
        for (ApplicantAppliedJob record : appliedApplicants) {
            Applicant applicant = record.getApplicant();
            double skillScore = skillMatch(selectedJobPost, applicant); // Returns match score

            // Categorize applicants based on skill score
            if (skillScore >= 0.5) {
                suitableApplicants.add(applicant); // Qualified applicants
            } else {
                nonQualifiedApplicants.add(applicant); // Non-qualified applicants
            }
        }

        // Display the qualified and non-qualified applicants
        menuUI.listApplicants(suitableApplicants, selectedJobPost, "Qualified");
        menuUI.listApplicants(nonQualifiedApplicants, selectedJobPost, "Unqualified");
    }

    // Call method for experience matching
    public void CompanyExperienceMatch() {
        String companyId = inputUI.getInput("Enter Company ID: ");
        DoublyLinkedListInterface<JobPost> jobPosts = jobPostManager.getJobPostsByCompanyId(companyId);

        if (jobPosts.isEmpty()) {
            System.out.println("❌ No job posts found for the given company ID.");
            return;
        }

        // Let the user select a job post from the available job posts
        JobPost selectedJobPost = jobPostManager.selectJobPost(jobPosts);
        if (selectedJobPost == null) {
            System.out.println("❌ No job post selected.");
            return;
        }

        // Filter applicants who have applied to this job
        DoublyLinkedListInterface<ApplicantAppliedJob> appliedApplicants = new DoublyLinkedList<>();
        for (ApplicantAppliedJob record : applicantAppliedJob) {
            if (record.getJobPost().equals(selectedJobPost)) {
                appliedApplicants.add(record);
            }
        }

        // If no applicants have applied for the selected job, display message and return
        if (appliedApplicants.isEmpty()) {
            System.out.println("📋 No applicants have applied for the selected job post.");
            return;
        }

        // Lists to store suitable and non-qualified applicants
        DoublyLinkedListInterface<Applicant> suitableApplicants = new DoublyLinkedList<>();
        DoublyLinkedListInterface<Applicant> nonQualifiedApplicants = new DoublyLinkedList<>();

        // Loop through all applicants who applied for this job and calculate experience match score
        for (ApplicantAppliedJob record : appliedApplicants) {
            Applicant applicant = record.getApplicant();
            double experienceScore = experienceMatch(selectedJobPost, applicant); // Returns match score

            // Categorize applicants based on experience score
            if (experienceScore == 1) {
                suitableApplicants.add(applicant); // Qualified applicants
            } else {
                nonQualifiedApplicants.add(applicant); // Non-qualified applicants
            }
        }

        // Display the qualified and non-qualified applicants
        menuUI.listApplicants(suitableApplicants, selectedJobPost, "Qualified");
        menuUI.listApplicants(nonQualifiedApplicants, selectedJobPost, "Unqualified");
    }

    // Call method for location match
    public void CompanyLocationMatch() {
        String companyId = inputUI.getInput("Enter Company ID: ");
        DoublyLinkedListInterface<JobPost> jobPosts = jobPostManager.getJobPostsByCompanyId(companyId);

        if (jobPosts.isEmpty()) {
            System.out.println("❌ No job posts found for the given company ID.");
            return;
        }

        // Let the user select a job post from the available job posts
        JobPost selectedJobPost = jobPostManager.selectJobPost(jobPosts);
        if (selectedJobPost == null) {
            System.out.println("❌ No job post selected.");
            return;
        }

        // Filter applicants who have applied to this job
        DoublyLinkedListInterface<ApplicantAppliedJob> appliedApplicants = new DoublyLinkedList<>();
        for (ApplicantAppliedJob record : applicantAppliedJob) {
            if (record.getJobPost().equals(selectedJobPost)) {
                appliedApplicants.add(record);
            }
        }

        // If no applicants have applied for the selected job, display message and return
        if (appliedApplicants.isEmpty()) {
            System.out.println("📋 No applicants have applied for the selected job post.");
            return;
        }

        // Lists to store suitable and non-qualified applicants
        DoublyLinkedListInterface<Applicant> suitableApplicants = new DoublyLinkedList<>();
        DoublyLinkedListInterface<Applicant> nonQualifiedApplicants = new DoublyLinkedList<>();

        // Loop through all applicants who applied for this job and calculate location match score
        for (ApplicantAppliedJob record : appliedApplicants) {
            Applicant applicant = record.getApplicant();
            double locationScore = locationMatch(selectedJobPost, applicant); // Returns match score

            // Categorize applicants based on location score
            if (locationScore == 1) {
                suitableApplicants.add(applicant); // Qualified applicants
            } else {
                nonQualifiedApplicants.add(applicant); // Non-qualified applicants
            }
        }

        // Display the qualified and non-qualified applicants
        menuUI.listApplicants(suitableApplicants, selectedJobPost, "Qualified");
        menuUI.listApplicants(nonQualifiedApplicants, selectedJobPost, "Unqualified");
    }

    // ==================== Helper Method
    // ===========================================
    // Check if an applicant is already matched to this job
    private boolean containsMatch(Applicant applicant, JobPost jobPost) {
        for (ApplicantAppliedJob record : matchRecords) {
            if (record.getApplicant().equals(applicant) && record.getJobPost().equals(jobPost)) {
                return true; // Duplicate found
            }
        }
        return false;
    }

    // ==================== Yoke Yau - Report ================
    // ===========================================
    public void generateCompanyMatchReport() {
        totalMatchScore(jobPostManager.getJobPostList(), applicantManager.getApplicants());

        String companyId = inputUI.getInput("Enter Company ID: ");
        DoublyLinkedListInterface<ApplicantAppliedJob> companyMatches = new DoublyLinkedList<>();

        for (ApplicantAppliedJob record : matchRecords) {
            if (record.getJobPost().getCompany().getCompanyId().equals(companyId)) {
                companyMatches.add(record);
            }
        }

        if (companyMatches.isEmpty()) {
            System.out.println("📋 No matches found for the selected company.");
            return;
        }

        // ✅ Sort using Merge Sort (Descending order by match score)
        companyMatches.mergeSort(Comparator.comparingDouble(ApplicantAppliedJob::getMatchScore).reversed());

        // 📊 Calculate Summary Statistics
        int totalApplicants = companyMatches.size();
        double highestScore = 0.0;
        String topApplicant = "";

        for (ApplicantAppliedJob record : companyMatches) {
            double score = record.getMatchScore() * 100;
            if (score > highestScore) {
                highestScore = score;
                topApplicant = record.getApplicant().getName();
            }
        }

        int width = 100; // You can adjust this depending on the console width or your preference
        String separator = menuUI.repeat("=", width);
        menuUI.printCompanyMatchReportHeader(companyId, totalApplicants, topApplicant, highestScore, width);

        for (ApplicantAppliedJob record : companyMatches) {
            String applicantName = record.getApplicant().getName();
            String jobTitle = record.getJobPost().getJob().getTitle();
            String jobLocation = record.getJobPost().getJob().getLocation();
            double score = record.getMatchScore() * 100;
            String matchScore = String.format("%.2f%%", score);

            // Categorizing Scores into Levels
            String level = (score >= 80) ? "🔥 High" : (score >= 50) ? "⚡ Medium" : "❄️ Low";

            System.out.println(String.format("%-20s | %-20s | %-20s | %-15s | %-10s", applicantName, jobTitle, jobLocation, matchScore, level));
        }

        System.out.println(separator);
        System.out.println("📊 Applicant Count Bar Chart by Job:");

        // Step 1: Get the list of unique job posts using DoublyLinkedList
        DoublyLinkedListInterface<JobPost> jobPosts = new DoublyLinkedList<>();
        for (ApplicantAppliedJob record : companyMatches) {
            JobPost jobPost = record.getJobPost();
            if (!jobPosts.contains(jobPost)) {
                jobPosts.add(jobPost);
            }
        }

        // Step 2: Find the maximum number of applicants for a single job
        int maxApplicants = 0;
        for (JobPost jobPost : jobPosts) {
            int count = 0;
            for (ApplicantAppliedJob record : companyMatches) {
                if (record.getJobPost().equals(jobPost)) {
                    count++;
                }
            }
            if (count > maxApplicants) {
                maxApplicants = count;
            }
        }

        // Step 3: Generate bars for each job
        final int maxBarLength = 36;  // Maximum bar length (can be adjusted)
        for (JobPost jobPost : jobPosts) {
            int applicantCount = 0;

            // Count how many applicants applied for this job
            for (ApplicantAppliedJob record : companyMatches) {
                if (record.getJobPost().equals(jobPost)) {
                    applicantCount++;
                }
            }

            // Step 4: Calculate the bar length
            int barLength = (int) ((double) applicantCount / maxApplicants * maxBarLength);

            // Step 5: Generate and print the bar
            String bar = menuUI.repeat("█", (barLength));
            String jobTitle = jobPost.getJob().getTitle();
            System.out.println(String.format("%-20s | %s (%d applicants)", jobTitle, bar, applicantCount));
        }
        System.out.println();
        menuUI.printTimestamp();

        menuUI.printEndOfReport(width);
    }

    public void generateApplicantMatchReport() {
        totalMatchScore(jobPostManager.getJobPostList(), applicantManager.getApplicants());

        // Get applicant ID from user input
        String applicantId = inputUI.getInput("Enter Applicant ID: ");
        Applicant applicant = applicantManager.findApplicantById(applicantId);
        DoublyLinkedListInterface<ApplicantAppliedJob> applicantMatches = new DoublyLinkedList<>();

        // Check if the applicant exists
        if (applicant == null) {
            System.out.println("❌ Applicant not found. Please check the ID and try again.");
            return;
        }

        // Filter matches by applicant ID
        for (ApplicantAppliedJob record : matchRecords) {
            if (record.getApplicant().getApplicantId().equals(applicantId)) {
                applicantMatches.add(record);
            }
        }

        if (applicantMatches.isEmpty()) {
            System.out.println("📋 No matches found for the selected applicant.");
            return;
        }

        // ✅ Sort using Merge Sort (Descending order by match score)
        applicantMatches.mergeSort(Comparator.comparingDouble(ApplicantAppliedJob::getMatchScore).reversed());

        // 📊 Calculate Summary Statistics
        int totalJobsApplied = applicantMatches.size();
        double highestScore = 0.0;
        String topJobTitle = "";

        for (ApplicantAppliedJob record : applicantMatches) {
            double score = record.getMatchScore() * 100;
            if (score > highestScore) {
                highestScore = score;
                topJobTitle = record.getJobPost().getJob().getTitle();
            }
        }

        int width = 100; // You can adjust this depending on the console width or your preference
        String separator = menuUI.repeat("=", width);

        menuUI.printApplicantMatchReportHeader(applicant, totalJobsApplied, topJobTitle, highestScore, width);

        // Displaying the job details with Level
        for (ApplicantAppliedJob record : applicantMatches) {
            String companyName = record.getJobPost().getCompany().getCompanyName();
            String jobTitle = record.getJobPost().getJob().getTitle();
            String jobLocation = record.getJobPost().getJob().getLocation();
            double score = record.getMatchScore() * 100;
            String matchScore = String.format("%.2f%%", score);

            // Categorizing Scores into Levels
            String level = (score >= 80) ? "🔥 High" : (score >= 50) ? "⚡ Medium" : "❄️ Low";

            // Outputting the job details
            System.out.println(String.format("%-20s | %-20s | %-20s | %-15s | %-10s", companyName, jobTitle, jobLocation, matchScore, level));
        }
        System.out.println(String.format("%" + (width + separator.length()) / 2 + "s", separator));

        // 📊 Generate the bar chart at the bottom for number of jobs applied per company
        System.out.println("📊 Jobs Applied Per Company:");

        // Step 1: Get the list of unique companies using DoublyLinkedList
        DoublyLinkedListInterface<Company> companies = new DoublyLinkedList<>();
        for (ApplicantAppliedJob record : applicantMatches) {
            Company company = record.getJobPost().getCompany();
            if (!companies.contains(company)) {
                companies.add(company);
            }
        }

        // Step 2: Find the max job applications in a single company
        int maxApplications = 0;
        for (Company company : companies) {
            int count = 0;
            for (ApplicantAppliedJob record : applicantMatches) {
                if (record.getJobPost().getCompany().equals(company)) {
                    count++;
                }
            }
            if (count > maxApplications) {
                maxApplications = count;
            }
        }

        // Step 3: Generate bars for each company
        final int maxBarLength = 36;  // Maximum bar length (can be adjusted)
        for (Company company : companies) {
            int jobCount = 0;

            // Count how many jobs the applicant applied for at this company
            for (ApplicantAppliedJob record : applicantMatches) {
                if (record.getJobPost().getCompany().equals(company)) {
                    jobCount++;
                }
            }

            // Step 4: Calculate the bar length
            int barLength = (int) ((double) jobCount / maxApplications * maxBarLength);

            // Step 5: Generate and print the bar
            String bar = menuUI.repeat("█", barLength);
            System.out.println(String.format("%-20s | %s (%d jobs)", company.getCompanyName(), bar, jobCount));
        }
        System.out.println();
        menuUI.printTimestamp();

        menuUI.printEndOfReport(width);
    }

}
