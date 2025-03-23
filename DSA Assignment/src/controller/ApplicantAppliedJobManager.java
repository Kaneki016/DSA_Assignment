package controller;

import adt.*;
import entities.*;
import boundary.*;

public class ApplicantAppliedJobManager {

    private static ApplicantAppliedJobManager instance;
    private static DoublyLinkedListInterface<ApplicantAppliedJob> applicantAppliedJob;
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
    public DoublyLinkedListInterface<Applicant> skillMatch(JobPost jobPost,
            DoublyLinkedListInterface<Applicant> applicants) {
        DoublyLinkedListInterface<Applicant> suitableApplicants = new DoublyLinkedList<>();

        // Get job requirements
        Job job = jobPost.getJob();
        DoublyLinkedListInterface<JobRequirements> requiredSkills = job.getJobRequirements();

        for (Applicant applicant : applicants) {
            double totalWeight = 0;
            double matchScore = 0;

            for (JobRequirements requiredSkill : requiredSkills) {
                String requiredSkillName = requiredSkill.getName();
                String proficiencyLevelStr = requiredSkill.getProficiencyLevel();

                int requiredProficiency = Integer.parseInt(proficiencyLevelStr);

                if (requiredProficiency == 0) {
                    continue; // Avoid division by zero
                }

                // Find applicant's proficiency level for this skill
                int applicantProficiency = 0;
                for (Skill skill : applicant.getSkills()) {
                    if (skill.getName().equalsIgnoreCase(requiredSkillName)) {
                        applicantProficiency = skill.getProficiency_level();
                        break;
                    }
                }

                if (applicantProficiency >= requiredProficiency) {
                    double match = (double) applicantProficiency / requiredProficiency;
                    matchScore += Math.min(match, 1.0); // Cap at 1.0
                }

                totalWeight++; // Count total skills considered
            }

            double finalScore = (totalWeight > 0) ? matchScore / totalWeight : 0;

            // Define match threshold (e.g., 50% match required)
            if (finalScore >= 0.5) {
                suitableApplicants.add(applicant);
            }
        }

        return suitableApplicants;
    }

    // Experience Level Matching
    public DoublyLinkedListInterface<Applicant> experienceMatch(JobPost jobPost,
            DoublyLinkedListInterface<Applicant> applicants) {
        DoublyLinkedListInterface<Applicant> suitableApplicants = new DoublyLinkedList<>();

        int jobExperienceRequired = jobPost.getJob().getRequired_experience(); // Required years

        for (Applicant applicant : applicants) {
            int applicantExperience = applicant.getYearsOfExperience(); // Applicant's years

            if (applicantExperience >= jobExperienceRequired) {
                suitableApplicants.add(applicant);
            }
        }

        return suitableApplicants;
    }

    // Location Preference Matching
    public DoublyLinkedListInterface<Applicant> locationMatch(JobPost jobPost,
            DoublyLinkedListInterface<Applicant> applicants) {
        DoublyLinkedListInterface<Applicant> suitableApplicants = new DoublyLinkedList<>();

        String jobLocation = jobPost.getJob().getLocation();

        for (Applicant applicant : applicants) {
            String applicantLocation = applicant.getLocation();

            double matchScore = applicantLocation.equalsIgnoreCase(jobLocation) ? 1.0 : 0.0;

            // Add applicant to suitable list if they match the job location
            if (matchScore == 1.0) {
                suitableApplicants.add(applicant);
            }
        }

        return suitableApplicants;
    }

    // ====================== For Applicant
    // ==========================================
    // Call method for finding suitable jobs for an applicant based on skills
    public void ApplicantSkillMatch() {
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

        // Check each job post to see if the applicant qualifies
        for (JobPost jobPost : allJobPosts) {
            DoublyLinkedListInterface<Applicant> applicants = new DoublyLinkedList<>();
            applicants.add(applicant); // Only checking this applicant

            if (!skillMatch(jobPost, applicants).isEmpty()) { // If the applicant matches this job
                suitableJobs.add(jobPost);
            }
        }

        // Display suitable jobs
        listOfSuitableJobPost(suitableJobs);
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

            if (!experienceMatch(jobPost, applicants).isEmpty()) { // If the applicant matches this job
                suitableJobs.add(jobPost);
            }
        }

        // Display suitable jobs
        listOfSuitableJobPost(suitableJobs);
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

            if (!locationMatch(jobPost, applicants).isEmpty()) { // If the applicant matches this job
                suitableJobs.add(jobPost);
            }
        }

        listOfSuitableJobPost(suitableJobs);
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

        DoublyLinkedListInterface<Applicant> suitableApplicants = skillMatch(selectedJobPost, getAllApplicants());
        listOfSuitableApplicant(suitableApplicants);
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

        DoublyLinkedListInterface<Applicant> suitableApplicants = experienceMatch(selectedJobPost, getAllApplicants());
        listOfSuitableApplicant(suitableApplicants);
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

        DoublyLinkedListInterface<Applicant> suitableApplicants = locationMatch(selectedJobPost, getAllApplicants());
        listOfSuitableApplicant(suitableApplicants);
    }

    // ==================== Helper Method
    // ===========================================
    public DoublyLinkedListInterface<Applicant> getAllApplicants() {
        return applicantManager.getApplicants();
    }

    // Print out the suitable applicant
    public void listOfSuitableApplicant(DoublyLinkedListInterface<Applicant> suitableApplicants) {
        System.out.println("Qualified Applicants:");
        for (Applicant result : suitableApplicants) {
            System.out.println(result);
        }
    }

    // Print out the suitable job post
    public void listOfSuitableJobPost(DoublyLinkedListInterface<JobPost> suitableJobPost) {
        // Display suitable jobs
        if (suitableJobPost.isEmpty()) {
            System.out.println("❌ No suitable jobs found for you.");
        } else {
            System.out.println("✅ Suitable Jobs for You:");
            for (JobPost jobPost : suitableJobPost) {
                System.out.println("- " + jobPost);
            }
        }
    }
}
