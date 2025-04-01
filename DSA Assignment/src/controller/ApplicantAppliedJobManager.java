package controller;

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

        for (JobPost jobPost : jobPosts) {  // ✅ Loop through all job posts
            for (Applicant applicant : applicants) {  // ✅ Loop through all applicants
                double skillScore = skillMatch(jobPost, applicant);
                double experienceScore = experienceMatch(jobPost, applicant);
                double locationScore = locationMatch(jobPost, applicant);

                // ✅ Define weightage (adjustable)
                double skillWeight = 0.5;
                double experienceWeight = 0.3;
                double locationWeight = 0.2;

                // ✅ Weighted total match score
                double totalScore = (skillScore * skillWeight) + 
                                    (experienceScore * experienceWeight) + 
                                    (locationScore * locationWeight);

                if (!containsMatch(applicant, jobPost)) {
                    matchRecords.add(new ApplicantAppliedJob(applicant, jobPost, totalScore));
                }
            }
        }
    }



    // ====================== For Applicant
    // ==========================================
    // Call method for finding suitable jobs for an applicant based on skills
    public void ApplicantSkillMatch() {
        System.out.println("\n🔍 Job Search Based on Your Skills 🔍");

        String applicantId = inputUI.getInput("Enter Applicant ID: ");
        Applicant applicant = applicantManager.findApplicantById(applicantId);

        if (applicant == null) {
            System.out.println("❌ Applicant not found. Please check the ID and try again.");
            return;
        }

        DoublyLinkedListInterface<JobPost> allJobPosts = jobPostManager.getJobPostList();

        if (allJobPosts.isEmpty()) {
            System.out.println("⚠️ No job postings available at the moment.");
            return;
        }

        DoublyLinkedListInterface<JobPost> suitableJobs = new DoublyLinkedList<>();
        DoublyLinkedListInterface<JobPost> notSuitableJobs = new DoublyLinkedList<>();

        // Check each job post to see if the applicant qualifies
        for (JobPost jobPost : allJobPosts) {
            
            double skillScore = skillMatch(jobPost, applicant); // ✅ Returns match score

            if (skillScore >= 0.5) { // ✅ Only store if above threshold
                suitableJobs.add(jobPost);
            } else {
                notSuitableJobs.add(jobPost);
            }
        }
        // Display jobs
        listJobPosts(applicant, suitableJobs, "Suitable");
        listJobPosts(applicant, notSuitableJobs, "Not Suitable");
    }


    // Call method for finding suitable jobs for an applicant based on experience
    public void ApplicantExperienceMatch() {
        String applicantId = inputUI.getInput("Enter Applicant ID: ");
        Applicant applicant = applicantManager.findApplicantById(applicantId);

        if (applicant == null) {
            System.out.println("Applicant not found.");
            return;
        }

        DoublyLinkedListInterface<JobPost> allJobPosts = jobPostManager.getJobPostList();

        if (allJobPosts.isEmpty()) {
            System.out.println("No job posts available.");
            return;
        }

        DoublyLinkedListInterface<JobPost> suitableJobs = new DoublyLinkedList<>();
        DoublyLinkedListInterface<JobPost> notSuitableJobs = new DoublyLinkedList<>();

        // Find suitable job posts for the applicant
        for (JobPost jobPost : allJobPosts) {
            DoublyLinkedListInterface<Applicant> applicants = new DoublyLinkedList<>();
            applicants.add(applicant); // Only checking this applicant

            double skillScore = experienceMatch(jobPost, applicant); // ✅ Returns match score

            if (skillScore == 1) { // ✅ Only store if above threshold
                suitableJobs.add(jobPost);
            } else {
                notSuitableJobs.add(jobPost);
            }
        }

        // Display jobs
        listJobPosts(applicant, suitableJobs, "Suitable");
        listJobPosts(applicant, notSuitableJobs, "Not Suitable");
    }

    
    ///Call method for finding suitable jobs for an applicant based on applicant location
    public void ApplicantLocationMatch() {
        String applicantId = inputUI.getInput("Enter Applicant ID: ");
        Applicant applicant = applicantManager.findApplicantById(applicantId);

        if (applicant == null) {
            System.out.println("Applicant not found.");
            return;
        }

        DoublyLinkedListInterface<JobPost> allJobPosts = jobPostManager.getJobPostList();

        if (allJobPosts.isEmpty()) {
            System.out.println("No job posts available.");
            return;
        }

        DoublyLinkedListInterface<JobPost> suitableJobs = new DoublyLinkedList<>();
        DoublyLinkedListInterface<JobPost> notSuitableJobs = new DoublyLinkedList<>();


        // Find suitable job posts for the applicant
        for (JobPost jobPost : allJobPosts) {
            DoublyLinkedListInterface<Applicant> applicants = new DoublyLinkedList<>();
            applicants.add(applicant); // Only checking this applicant

            double skillScore = locationMatch(jobPost, applicant); // ✅ Returns match score

            if (skillScore == 1) { // ✅ Only store if above threshold
                suitableJobs.add(jobPost);
            } else {
                notSuitableJobs.add(jobPost);
            }
        }
        
        // Display jobs
        listJobPosts(applicant, suitableJobs, "Suitable");
        listJobPosts(applicant, notSuitableJobs, "Not Suitable");
    }

    
    // ======================== For Company
    // ===========================================
    // Call method for skill matching
    public void CompanySkillMatch() {
        String companyId = inputUI.getInput("Enter Company ID: ");
        DoublyLinkedListInterface<JobPost> jobPosts = jobPostManager.getJobPostsByCompanyId(companyId);

        if (jobPosts.isEmpty()) {
            System.out.println("No job posts found for the given company ID.");
            return;
        }

        JobPost selectedJobPost = jobPostManager.selectJobPost(jobPosts);
        if (selectedJobPost == null) {
            System.out.println("No job post selected.");
            return;
        }

        DoublyLinkedListInterface<Applicant> suitableApplicants = new DoublyLinkedList<>();
        DoublyLinkedListInterface<Applicant> nonQualifiedApplicants = new DoublyLinkedList<>();

        // ✅ Loop through all applicants and calculate skill match score
        for (Applicant applicant : applicantManager.getApplicants()) {
            double skillScore = skillMatch(selectedJobPost, applicant); // ✅ Returns match score

            if (skillScore >= 0.5) { // ✅ Only store if above threshold
                suitableApplicants.add(applicant);
            } else {
                nonQualifiedApplicants.add(applicant);
            }
        }

        listApplicants(suitableApplicants, selectedJobPost, "Qualified");
        listApplicants(nonQualifiedApplicants, selectedJobPost, "Non-Qualified");

    }

    
    // Call method for experience matching
    public void CompanyExperienceMatch() {
        String companyId = inputUI.getInput("Enter Company ID: ");
        DoublyLinkedListInterface<JobPost> jobPosts = jobPostManager.getJobPostsByCompanyId(companyId);

        if (jobPosts.isEmpty()) {
            System.out.println("No job posts found for the given company ID.");
            return;
        }

        JobPost selectedJobPost = jobPostManager.selectJobPost(jobPosts);
        if (selectedJobPost == null) {
            System.out.println("No job post selected.");
            return;
        }

        DoublyLinkedListInterface<Applicant> nonQualifiedApplicants = new DoublyLinkedList<>();
        DoublyLinkedListInterface<Applicant> suitableApplicants = new DoublyLinkedList<>();

        // ✅ Loop through all applicants and calculate experience match score
        for (Applicant applicant : applicantManager.getApplicants()) {
            double experienceScore = experienceMatch(selectedJobPost, applicant); // ✅ Returns match score

            if (experienceScore == 1) { // ✅ Only store if above threshold
                suitableApplicants.add(applicant); // ✅ Extract applicant
            } else {
                nonQualifiedApplicants.add(applicant);
            }
        }

        listApplicants(suitableApplicants, selectedJobPost, "Qualified");
        listApplicants(nonQualifiedApplicants, selectedJobPost, "Non-Qualified");
    }


    // Call method for location match
    public void CompanyLocationMatch() {
        String companyId = inputUI.getInput("Enter Company ID: ");
        DoublyLinkedListInterface<JobPost> jobPosts = jobPostManager.getJobPostsByCompanyId(companyId);

        if (jobPosts.isEmpty()) {
            System.out.println("No job posts found for the given company ID.");
            return;
        }

        JobPost selectedJobPost = jobPostManager.selectJobPost(jobPosts);
        if (selectedJobPost == null) {
            System.out.println("No job post selected.");
            return;
        }

        DoublyLinkedListInterface<Applicant> nonQualifiedApplicants = new DoublyLinkedList<>();
        DoublyLinkedListInterface<Applicant> suitableApplicants = new DoublyLinkedList<>();

        // ✅ Loop through all applicants and calculate location match score
        for (Applicant applicant : applicantManager.getApplicants()) {
            double locationScore = locationMatch(selectedJobPost, applicant); // ✅ Returns match score

            if (locationScore == 1) { // ✅ Only store if above threshold
                suitableApplicants.add(applicant); // ✅ Extract applicant
            } else {
                nonQualifiedApplicants.add(applicant);
            }
        }

        listApplicants(suitableApplicants, selectedJobPost, "Qualified");
        listApplicants(nonQualifiedApplicants, selectedJobPost, "Non-Qualified");
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

        System.out.println("\n📊 Job-Applicant Overall Match Report for Company ID: " + companyId);
        System.out.println("====================================");

        for (ApplicantAppliedJob record : companyMatches) {
            System.out.println("👤 Applicant: " + record.getApplicant().getName());
            System.out.println("🏢 Company: " + record.getJobPost().getCompany().getCompanyName());
            System.out.println("📌 Job Title: " + record.getJobPost().getJob().getTitle());
            System.out.println("📍 Job Location: " + record.getJobPost().getJob().getLocation());
            System.out.println("⭐ Match Score: " + String.format("%.2f", record.getMatchScore() * 100) + "%");
            System.out.println("------------------------------------");
        }
    }

    
    public void generateApplicantMatchReport() {
        totalMatchScore(jobPostManager.getJobPostList(), applicantManager.getApplicants());

        String applicantId = inputUI.getInput("Enter Applicant ID: ");
        Applicant applicant = applicantManager.findApplicantById(applicantId);
        DoublyLinkedListInterface<ApplicantAppliedJob> applicantMatches = new DoublyLinkedList<>();

        if (applicant == null) {
            System.out.println("❌ Applicant not found. Please check the ID and try again.");
            return;
        }

        for (ApplicantAppliedJob record : matchRecords) {
            if (record.getApplicant().getApplicantId().equals(applicantId)) {
                applicantMatches.add(record);
            }
        }
        
        if (applicantMatches.isEmpty()) {
            System.out.println("📋 No matches found for the selected company.");
            return;
        }

        // ✅ Sort using Merge Sort (Descending order by match score)
        applicantMatches.mergeSort(Comparator.comparingDouble(ApplicantAppliedJob::getMatchScore).reversed());

        System.out.println("\n📊 Job-Applicant Overall Match Report for Applicant: " + applicant.getName());
        System.out.println("====================================");

        for (ApplicantAppliedJob record : applicantMatches) {
            System.out.println("🏢 Company: " + record.getJobPost().getCompany().getCompanyName());
            System.out.println("📌 Job Title: " + record.getJobPost().getJob().getTitle());
            System.out.println("📍 Job Location: " + record.getJobPost().getJob().getLocation());
            System.out.println("⭐ Match Score: " + String.format("%.2f", record.getMatchScore() * 100) + "%");
            System.out.println("------------------------------------");
        }
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
