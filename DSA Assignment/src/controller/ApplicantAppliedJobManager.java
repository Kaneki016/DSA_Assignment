package controller;
// Author: Lai Yoke Yau and Lim Wai Ming

import adt.*;
import entities.*;
import boundary.*;
import java.util.Comparator;

public class ApplicantAppliedJobManager {

    private static ApplicantAppliedJobManager instance;
    private static DoublyLinkedListInterface<ApplicantAppliedJob> applicantAppliedJob;
    private DoublyLinkedListInterface<ApplicantAppliedJob> matchRecords;
    private static DoublyLinkedListInterface<Interview> interviews;

    // Boundary
    private static InputUI inputUI = new InputUI();

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
            return;
        }

        // Step 3: Retrieve available jobs
        DoublyLinkedListInterface<JobPost> jobPosts = jobPostManager.getJobPostList();
        if (jobPosts.isEmpty()) {
            inputUI.displayMessage("❌ No available jobs at the moment.");
            return;
        }

        // Step 4: Display available jobs
        jobPostManager.displayJobPosts();

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

    public void handleCheckMyApplications() {
        // Step 1: Get Applicant ID using InputUI
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

        // Step 2: Retrieve interview data
        DoublyLinkedListInterface<Interview> interviews = interviewManager.getInterviews();

        // Step 3: Display the user's job applications
        int index = 1;
        int totalApplications = 0;
        DoublyLinkedListInterface<ApplicantAppliedJob> userApplications = new DoublyLinkedList<>();

        inputUI.displayMessage("\n📌 Your Submitted Job Applications:");
        for (ApplicantAppliedJob application : applicantAppliedJob) {
            if (application.getApplicant().equals(applicant)) {
                // Default values if no interview is scheduled
                String interviewStatus = "Pending";
                String interviewTimeSlot = "Not Scheduled";

                // Step 4: Check if an interview exists for this application
                for (Interview interview : interviews) {
                    if (interview.getApplicantAppliedJob().equals(application)) {
                        interviewStatus = interview.getStatus();
                        TimeSlot timeslot = interview.getTimeslot();
                        interviewTimeSlot = timeslot.getTime() + ", " + timeslot.getDate() + " at "
                                + timeslot.getLocation();
                        break;
                    }
                }

                // Step 5: Display application details with interview info
                inputUI.displayMessage(index + ". " + application.getJobPost().getJob().getTitle()
                        + " at " + application.getJobPost().getCompany().getCompanyName()
                        + " | Status: " + interviewStatus
                        + " | Interview TimeSlot: " + interviewTimeSlot);

                userApplications.add(application); // Store indexed applications
                index++;
                totalApplications++;
            }
        }

        if (totalApplications == 0) {
            inputUI.displayMessage("❌ You have not applied for any jobs yet.");
            return;
        }

        // Step 6: Ask user to select an application to take action
        int choice = inputUI.getValidIntInput(
                "\nEnter the number of the application to take action (or type 0 to go back): ", 0, totalApplications);

        if (choice == 0) {
            inputUI.displayMessage("Returning to previous menu...");
            return;
        }

        ApplicantAppliedJob selectedApplication = userApplications.get(choice - 1);

        // Step 7: Ask user to Accept or Reject
        inputUI.displayMessage("\nYou selected: " + selectedApplication.getJobPost().getJob().getTitle()
                + " at " + selectedApplication.getJobPost().getCompany().getCompanyName());

        String action;
        do {
            action = inputUI.getInput("Do you want to ACCEPT or REJECT this application? (accept/reject/back): ")
                    .toLowerCase();

            if (action.equals("back")) {
                inputUI.displayMessage("Returning to application selection...");
                return;
            } else if (action.equals("accept")) {
                // Step 8: Accept the application (Confirm the interview if scheduled)
                boolean interviewFound = false;
                for (Interview interview : interviews) {
                    if (interview.getApplicantAppliedJob().equals(selectedApplication)) {
                        interview.setStatus("Accepted");
                        inputUI.displayMessage("\n✅ You have accepted the interview for: "
                                + selectedApplication.getJobPost().getJob().getTitle());
                        interviewFound = true;
                        break;
                    }
                }
                if (!interviewFound) {
                    inputUI.displayMessage("\n⚠ No interview scheduled yet. Your application remains in process.");
                }
            } else if (action.equals("reject")) {
                // Step 9: Reject the application
                removeApplicantAppliedJob(selectedApplication);
                inputUI.displayMessage("\n❌ You have rejected the job application for: "
                        + selectedApplication.getJobPost().getJob().getTitle());
            } else {
                inputUI.displayMessage("Invalid choice. Please enter 'accept', 'reject', or 'back'.");
            }
        } while (!action.equals("accept") && !action.equals("reject"));
    }

    // New getter method to expose the list
    public DoublyLinkedListInterface<ApplicantAppliedJob> getApplicantAppliedJobs() {
        return applicantAppliedJob;
    }

    // ====================== Matching Method
    // =============================================================
    // Skill Matching with Proficiency Levels - Returns Match Score
    public double skillMatch(JobPost jobPost, Applicant applicant) {
        double totalWeight = 0, matchScore = 0;

        for (JobRequirements requiredSkill : jobPost.getJob().getJobRequirements()) {
            int requiredProficiency = Integer.parseInt(requiredSkill.getProficiencyLevel());
            if (requiredProficiency == 0) continue;

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
                    double totalScore = (skillScore * skillWeight) + 
                                        (experienceScore * experienceWeight) + 
                                        (locationScore * locationWeight);

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
        listJobPosts(applicant, suitableJobs, "Suitable");
        listJobPosts(applicant, notSuitableJobs, "Unsuitable");
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
        listJobPosts(applicant, suitableJobs, "Suitable");
        listJobPosts(applicant, notSuitableJobs, "Unsuitable");
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
        listJobPosts(applicant, suitableJobs, "Suitable");
        listJobPosts(applicant, notSuitableJobs, "Unsuitable");
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
        listApplicants(suitableApplicants, selectedJobPost, "Qualified");
        listApplicants(nonQualifiedApplicants, selectedJobPost, "Unqualified");
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
        listApplicants(suitableApplicants, selectedJobPost, "Qualified");
        listApplicants(nonQualifiedApplicants, selectedJobPost, "Unqualified");
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
        listApplicants(suitableApplicants, selectedJobPost, "Qualified");
        listApplicants(nonQualifiedApplicants, selectedJobPost, "Unqualified");
    }

    
    // ==================== Report ================
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
        String separator = "=".repeat(100);
        String header = String.format("%-20s | %-20s | %-20s | %-15s | %-10s", "👤 Applicant Name", "📌 Job Title", "📍 Job Location", "⭐ Match Score", "📊 Level");

        // Center-align headers and other text
        System.out.println(String.format("%" + (width + separator.length()) / 2 + "s", separator));
        System.out.println(String.format("%" + (width + "TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY".length()) / 2 + "s", "TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY"));
        System.out.println(String.format("%" + (width + "INTERNSHIP APPLICATION PROGRAM".length()) / 2 + "s", "INTERNSHIP APPLICATION PROGRAM"));
        System.out.println();
        System.out.println(String.format("%" + (width + "OVERALL SCORE MATCHING RANKING REPORT".length()) / 2 + "s", "OVERALL SCORE MATCHING RANKING REPORT"));
        System.out.println(String.format("%" + (width + separator.length()) / 2 + "s", separator));
        System.out.println();
        System.out.println(String.format("%" + (width + ("📊 Job-Applicant Overall Match Report for Company: " + companyId).length()) / 2 + "s", "📊 Job-Applicant Overall Match Report for Company: " + companyId));
        System.out.println(String.format("%" + (width + ("Total Applicants Considered: " + totalApplicants).length()) / 2 + "s", "Total Applicants Considered: " + totalApplicants));
        System.out.println(String.format("%" + (width + ("Top Applicant: " + topApplicant + " (Score: " + String.format("%.2f%%", highestScore) + ")").length()) / 2 + "s", "Top Applicant: " + topApplicant + " (Score: " + String.format("%.2f%%", highestScore) + ")"));
        System.out.println(String.format("%" + (width + separator.length()) / 2 + "s", separator));
        System.out.println(header);
        System.out.println(String.format("%" + (width + separator.length()) / 2 + "s", separator));


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
        final int maxBarLength = 50;  // Maximum bar length (can be adjusted)
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
            String bar = "█".repeat(barLength);
            String jobTitle = jobPost.getJob().getTitle();
            System.out.println(String.format("%-20s | %s (%d applicants)", jobTitle, bar, applicantCount));
        }

        System.out.println(separator);
        System.out.println(String.format("%" + (width + "END OF REPORT".length()) / 2 + "s", "END OF REPORT"));
        System.out.println(separator);
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
        String separator = "=".repeat(100);

        // Center-align headers and other text
        System.out.println(String.format("%" + (width + separator.length()) / 2 + "s", separator));
        System.out.println(String.format("%" + (width + "TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY".length()) / 2 + "s", "TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY"));
        System.out.println(String.format("%" + (width + applicant.getName().length()) / 2 + "s", applicant.getName()));
        System.out.println(String.format("%" + (width + "INTERNSHIP APPLICATION REPORT".length()) / 2 + "s", "INTERNSHIP APPLICATION REPORT"));
        System.out.println();
        System.out.println(String.format("%" + (width + "OVERALL JOB MATCHING REPORT FOR APPLICANT".length()) / 2 + "s", "OVERALL JOB MATCHING REPORT FOR APPLICANT"));
        System.out.println(String.format("%" + (width + separator.length()) / 2 + "s", separator));
        System.out.println();
        System.out.println(String.format("%" + (width + ("Total Jobs Applied: " + totalJobsApplied).length()) / 2 + "s", "Total Jobs Applied: " + totalJobsApplied));
        System.out.println(String.format("%" + (width + ("Top Job: " + topJobTitle + " (Score: " + String.format("%.2f%%", highestScore) + ")").length()) / 2 + "s", "Top Job: " + topJobTitle + " (Score: " + String.format("%.2f%%", highestScore) + ")"));
        System.out.println(String.format("%" + (width + separator.length()) / 2 + "s", separator));

        // Header for job details including Level
        String header = String.format("%-20s | %-20s | %-20s | %-15s | %-10s", "🏢 Company", "📌 Job Title", "📍 Job Location", "⭐ Match Score", "📊 Level");
        System.out.println(header);
        System.out.println(String.format("%" + (width + separator.length()) / 2 + "s", separator));

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
    final int maxBarLength = 50;  // Maximum bar length (can be adjusted)
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
        String bar = "█".repeat(barLength);
        System.out.println(String.format("%-20s | %s (%d jobs)", company.getCompanyName(), bar, jobCount));
    }

        System.out.println(separator);
        System.out.println(String.format("%" + (width + "END OF REPORT".length()) / 2 + "s", "END OF REPORT"));
        System.out.println(separator);
    }



    // ==================== Helper Method
    // ===========================================

    // Print out the suitable applicants with enhanced formatting
    public void listApplicants(DoublyLinkedListInterface<Applicant> applicants, JobPost jobPost, String status) {
        System.out.println("\n👥 " + status + " Applicants for " + jobPost.getJob().getTitle() + " at " + jobPost.getCompany().getCompanyName());

        if (applicants.isEmpty()) {
            System.out.println("❌ No " + status.toLowerCase() + " applicants found.");
        } else {
            System.out.println("✅ " + applicants.size() + " " + status + " applicant(s) found:\n");
            int count = 1;
            for (Applicant applicant : applicants) {
                System.out.println(count + ". 👤 Name: " + applicant.getName());
                System.out.println("   🎓 Education: " + applicant.getEducationLevel());
                System.out.println("   🏠 Location: " + applicant.getLocation());
                System.out.println("   📅 Experience: " + applicant.getYearsOfExperience() + " year(s)");
                System.out.println("   🛠 Skills: " + formatApplicantSkills(applicant.getSkills()));
                System.out.println("--------------------------------------------------");
                count++;
            }
        }
    }


    // Print out the suitable job post with a better format
    public void listJobPosts(Applicant applicant, DoublyLinkedListInterface<JobPost> jobPosts, String status) {
        System.out.println("\n📄 " + status + " Job Matches for: " + applicant.getName());

        if (jobPosts.isEmpty()) {
            System.out.println("❌ No " + status.toLowerCase() + " jobs found for you. Keep learning and check back later!");
        } else {
            System.out.println("✅ " + jobPosts.size() + " " + status + " job(s) found:\n");
            int count = 1;
            for (JobPost jobPost : jobPosts) {
                System.out.println(count + ". 🏢 " + jobPost.getCompany().getCompanyName() + " (" + jobPost.getCompany().getCompanyLocation() + ")");
                System.out.println("   📌 Job Title: " + jobPost.getJob().getTitle());
                System.out.println("   📍 Job Location: " + jobPost.getJob().getLocation()); // Added job location
                System.out.println("   🏆 Required Experience: " + jobPost.getJob().getRequired_experience() + " years");
                System.out.println("   📜 Requirements: " + formatJobRequirements(jobPost.getJob().getJobRequirements()));
                System.out.println("--------------------------------------------------");
                count++;
            }
        }
    }

    
    // Helper method to format job requirements nicely
    private String formatJobRequirements(DoublyLinkedListInterface<JobRequirements> requirements) {
        if (requirements.isEmpty()) return "No specific requirements listed.";

        StringBuilder formatted = new StringBuilder();
        for (JobRequirements req : requirements) {
            formatted.append("\n   - ")
                    .append(req.getName())
                    .append(" (Proficiency: ")
                    .append(req.getProficiencyLevel())
                    .append(")");
        }
        return formatted.toString();
    }
    
    // Helper method to format applicant skills nicely
    private String formatApplicantSkills(DoublyLinkedListInterface<Skill> skills) {
        if (skills.isEmpty()) return "No skills listed.";

        StringBuilder formatted = new StringBuilder();
        for (Skill skill : skills) {
            formatted.append("\n   - ")
                    .append(skill.getName())
                    .append(" (Proficiency: ")
                    .append(skill.getProficiency_level())
                    .append(")");
        }
        return formatted.toString();
    }
    
    // Check if an applicant is already matched to this job
    private boolean containsMatch(Applicant applicant, JobPost jobPost) {
        for (ApplicantAppliedJob record : matchRecords) {
            if (record.getApplicant().equals(applicant) && record.getJobPost().equals(jobPost)) {
                return true; // Duplicate found
            }
        }
        return false;
    }


}
