package controller;

import adt.*;
import entities.*;
import boundary.*;

public class ApplicantAppliedJobManager {

    private static ApplicantAppliedJobManager instance;
    private static DoublyLinkedListInterface<ApplicantAppliedJob> applicantAppliedJob;
    private DoublyLinkedListInterface<ApplicantAppliedJob> matchRecords = new DoublyLinkedList<>();
    private DoublyLinkedListInterface<Applicant> suitableApplicants = new DoublyLinkedList<>();
    
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
    public void skillMatch(JobPost jobPost, DoublyLinkedListInterface<Applicant> applicants) {
        suitableApplicants.clear();
        
        for (Applicant applicant : applicants) {
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

            double finalScore = (totalWeight > 0) ? matchScore / totalWeight : 0;

                if (finalScore >= 0.5 && !suitableApplicants.contains(applicant)) { // ✅ Prevent duplicates
                    suitableApplicants.add(applicant);
                }
            }
        }


    // Experience Level Matching
    public void experienceMatch(JobPost jobPost, DoublyLinkedListInterface<Applicant> applicants) {
        suitableApplicants.clear();
        
        int jobExperienceRequired = jobPost.getJob().getRequired_experience(); // Required years

        for (Applicant applicant : applicants) {
            int applicantExperience = applicant.getYearsOfExperience(); // Applicant's years

            double matchScore;
            if (applicantExperience >= jobExperienceRequired) {
                matchScore = 1.0;
            } else {
                matchScore = (double) applicantExperience /jobExperienceRequired;
            }
            
            if (matchScore >= 0.5 && !suitableApplicants.contains(applicant)) {
                suitableApplicants.add(applicant);
            }
        }
    }

    // Location Preference Matching
    public void locationMatch(JobPost jobPost, DoublyLinkedListInterface<Applicant> applicants) {
        suitableApplicants.clear();
        
        String jobLocation = jobPost.getJob().getLocation();

        for (Applicant applicant : applicants) {
            String applicantLocation = applicant.getLocation();

            double matchScore = applicantLocation.equalsIgnoreCase(jobLocation) ? 1.0 : 0.0;

            // Add applicant to suitable list if they match the job location
            if (matchScore == 1.0) {
                suitableApplicants.add(applicant);
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

        // Check each job post to see if the applicant qualifies
        for (JobPost jobPost : allJobPosts) {
            DoublyLinkedListInterface<Applicant> applicants = new DoublyLinkedList<>();
            applicants.add(applicant); // Only checking this applicant

            if (!getSuitableApplicants().isEmpty()) { // If the applicant matches this job
                suitableJobs.add(jobPost);
            }
        }
        // Display suitable jobs
        listOfSuitableJobPost(applicant, suitableJobs);
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

        // Find suitable job posts for the applicant
        for (JobPost jobPost : allJobPosts) {
            DoublyLinkedListInterface<Applicant> applicants = new DoublyLinkedList<>();
            applicants.add(applicant); // Only checking this applicant

            if (!getSuitableApplicants().isEmpty()) { // If the applicant matches this job
                suitableJobs.add(jobPost);
            }
        }

        // Display suitable jobs
        listOfSuitableJobPost(applicant, suitableJobs);
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

        // Find suitable job posts for the applicant
        for (JobPost jobPost : allJobPosts) {
            DoublyLinkedListInterface<Applicant> applicants = new DoublyLinkedList<>();
            applicants.add(applicant); // Only checking this applicant

            if (!getSuitableApplicants().isEmpty()) { // If the applicant matches this job
                suitableJobs.add(jobPost);
            }
        }
        // Display suitable jobs
        listOfSuitableJobPost(applicant, suitableJobs);
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

        skillMatch(selectedJobPost, getAllApplicants());
        listOfSuitableApplicant(getSuitableApplicants(), selectedJobPost);
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
        listOfSuitableApplicant(getSuitableApplicants(), selectedJobPost);
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
        listOfSuitableApplicant(getSuitableApplicants(), selectedJobPost);
    }
    
    // ==================== Report ================
    // ===========================================
    public void generateMatchReport() {
        if (matchRecords.isEmpty()) {
            System.out.println("📋 No matches found to generate a report.");
            return;
        }

        System.out.println("\n📊 Job-Applicant Match Report");
        System.out.println("====================================");

        for (ApplicantAppliedJob record : matchRecords) {
            System.out.println("👤 Applicant: " + record.getApplicant().getName());
            System.out.println("🏢 Company: " + record.getJobPost().getCompany().getCompanyName());
            System.out.println("📌 Job Title: " + record.getJobPost().getJob().getTitle());
            System.out.println("⭐ Match Score: " + String.format("%.2f", record.getMatchScore() * 100) + "%");
            System.out.println("------------------------------------");
        }
    }


    // ==================== Helper Method
    // ===========================================
    public DoublyLinkedListInterface<Applicant> getAllApplicants() {
        return applicantManager.getApplicants();
    }

    // Print out the suitable applicants with enhanced formatting
    public void listOfSuitableApplicant(DoublyLinkedListInterface<Applicant> suitableApplicants, JobPost jobPost) {
        System.out.println("\n👥 Suitable Applicants for " + jobPost.getJob().getTitle() + " at " + jobPost.getCompany().getCompanyName());

        if (suitableApplicants.isEmpty()) {
            System.out.println("❌ No qualified applicants found. Consider adjusting the job requirements.");
        } else {
            System.out.println("✅ " + suitableApplicants.size() + " applicant(s) found that match the job requirements:\n");
            int count = 1;
            for (Applicant applicant : suitableApplicants) {
                System.out.println(count + ". 👤 Name: " + applicant.getName());
                System.out.println("   🎓 Education: " + applicant.getEducationLevel());
                System.out.println("   🏠 Location: " + applicant.getLocation());
                System.out.println("   🛠 Skills: " + formatApplicantSkills(applicant.getSkills()));
                System.out.println("--------------------------------------------------");
                count++;
            }
        }
    }

    // Print out the suitable job post with a better format
    public void listOfSuitableJobPost(Applicant applicant, DoublyLinkedListInterface<JobPost> suitableJobPost) {
        System.out.println("\n📄 Job Match Results for: " + applicant.getName());

        if (suitableJobPost.isEmpty()) {
            System.out.println("❌ No suitable jobs found for you. Keep learning and check back later!");
        } else {
            System.out.println("✅ Congratulations! We found " + suitableJobPost.size() + " job(s) that suitable for you:\n");
            int count = 1;
            for (JobPost jobPost : suitableJobPost) {
                System.out.println(count + ". 🏢 " + jobPost.getCompany().getCompanyName() + "(" + jobPost.getCompany().getCompanyLocation() + ")");
                System.out.println("   📌 Job Title: " + jobPost.getJob().getTitle());
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
    
    // get suitable applicants
    public DoublyLinkedListInterface<Applicant> getSuitableApplicants() {
        return suitableApplicants;
    }
    
}
