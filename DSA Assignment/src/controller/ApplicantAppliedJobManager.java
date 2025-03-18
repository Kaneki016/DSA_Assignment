package controller;

import adt.*;
import entities.*;
import boundary.*;

public class ApplicantAppliedJobManager {

    private static ApplicantAppliedJobManager instance;
    private static DoublyLinkedListInterface<ApplicantAppliedJob> applicantAppliedJob;

    // Boundary
    private  static InputUI inputUI = new InputUI();

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

    public void addApplicantAppliedJob(ApplicantAppliedJob newAppliedJob) {
        applicantAppliedJob.add(newAppliedJob);
        inputUI.displayMessage("A new applicant applied job added!\n");
    }

    public void removeApplicantAppliedJob(ApplicantAppliedJob appliedJob) {
        applicantAppliedJob.removeSpecific(appliedJob);
        inputUI.displayMessage("Applicant applied job removed!\n");
    }

    public DoublyLinkedListInterface<ApplicantAppliedJob> getApplicantAppliedJobs() {
        return applicantAppliedJob;
    }

    //====================== For Company =============================================================
    
    // Skill Matching with Proficiency Levels - Returns Match Score
    public DoublyLinkedListInterface<Applicant> skillMatch(JobPost jobPost, DoublyLinkedListInterface<Applicant> applicants) {
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

                if (requiredProficiency == 0) continue; // Avoid division by zero


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
    public DoublyLinkedListInterface<Applicant> experienceMatch(JobPost jobPost, DoublyLinkedListInterface<Applicant> applicants) {
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
    public DoublyLinkedListInterface<Applicant> locationMatch(JobPost jobPost, DoublyLinkedListInterface<Applicant> applicants) {
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
    
    // ======================== For InputUI ===========================================
    
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
    
    
    //====================== For Applicant ==========================================
    
    
    //==================== Helper Method ===========================================
    public DoublyLinkedListInterface<Applicant> getAllApplicants() {
        return applicantManager.getApplicants();
    }
    
    //Print out the suitable applicant
    public void listOfSuitableApplicant(DoublyLinkedListInterface<Applicant> suitableApplicants){
        System.out.println("Qualified Applicants:");
        for (Applicant result : suitableApplicants) {
            System.out.println(result);
        }
    }
}