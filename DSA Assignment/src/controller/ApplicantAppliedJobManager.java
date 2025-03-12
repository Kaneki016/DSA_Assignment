package controller;

import adt.*;
import entities.*;
import java.util.Scanner;
import boundary.InputUI;
import boundary.MenuUI;

public class ApplicantAppliedJobManager {

    private static ApplicantAppliedJobManager instance;
    private static Scanner scanner = new Scanner(System.in);
    private DoublyLinkedListInterface<ApplicantAppliedJob> applicantAppliedJob;
    private static Company company;
    
     // Boundary
    private static MenuUI menuUI = new MenuUI();
    private static InputUI inputUI = new InputUI();
   
    public ApplicantAppliedJobManager() {
        applicantAppliedJob = new DoublyLinkedList<>();
    }

    public static ApplicantAppliedJobManager getInstance() {
        if (instance == null) {
            instance = new ApplicantAppliedJobManager();
        }
        return instance;
    }
    
    // Controllers
    private static ApplicantAppliedJobManager applicantAppliedJobManager = ApplicantAppliedJobManager.getInstance();
    private static SkillManager skillManager = SkillManager.getInstance();
    private static ApplicantManager applicantManager = ApplicantManager.getInstance();
    private static JobPostManager jobManager = JobPostManager.getInstance();
    private static CompanyManager companyManager = CompanyManager.getInstance();

    public void addApplicantAppliedJob(ApplicantAppliedJob newAppliedJob) {
        applicantAppliedJob.add(newAppliedJob);
        inputUI.displayMessage("A new applicant applied job added!\n");
    }
    
    // New getter method to expose the list
    public DoublyLinkedListInterface<ApplicantAppliedJob> getApplicantAppliedJobs() {
        return applicantAppliedJob;
    }
    
    //choose to match by categories
    public static void displayJobMatching() {
        inputUI.displayMessage("Which company you belongs to?: ");
        String companyId = scanner.nextLine().trim();
        company = companyManager.findCompanyById(companyId);

        //Company not found
        if (company == null) {
            inputUI.displayMessage("Company not found!");
        }

        //Display Matching Menu
        menuUI.displayJobMatchingMenu(company.getCompanyName());
    }
    
        // Skill Matching with proficiency levels
        public double calculateSkillMatch(ApplicantManager applicant, JobPostManager job) {

        double totalWeight = 0;
        double matchScore = 0;

        for (JobRequirements req : job.getJobRequirements()) {
            String requiredSkill = req.getName();
            int requiredProficiency = req.getProficiency_level();

            // Retrieve applicant's proficiency using SkillManager
            int applicantProficiency = skillManager.getApplicantProficiencyLevel(applicant, requiredSkill);

            if (applicantProficiency == requiredProficiency) {
                totalWeight++;
            }
            
            return totalWeight;
        }

        return totalWeight > 0 ? matchScore / totalWeight : 0;
    }

        // Weighted skill match
        public double weightedSkillMatch(ApplicantManager applicant, JobPostManager job) {
        double weightedScore = 0;
        double totalWeight = 0;

        for (JobRequirements req : job.getJobRequirements()) {
            Skill skill = req.getSkill();
            double weight = req.getWeight(); // Higher weight for crucial skills
            int requiredProficiency = req.getProficiency();
            int applicantProficiency = applicant.getProficiencyLevel(skill);

            if (applicantProficiency > 0) {
                double match = (double) applicantProficiency / requiredProficiency;
                weightedScore += Math.min(match, 1.0) * weight;
            }
            totalWeight += weight;
        }

        return totalWeight > 0 ? weightedScore / totalWeight : 0;
    }


        // Experience level matching
        public double experienceMatch(Applicant applicant, Job job) {
        int jobExperienceRequired = job.getRequiredExperience(); // In years
        int applicantExperience = applicant.getExperienceYears();

        double score = (double) applicantExperience / jobExperienceRequired;
        return Math.min(score, 1.0); // Cap at 1.0
    }


        // Location preference matching
        public double locationMatch(Applicant applicant, Job job) {
        if (applicant.getPreferredLocation().equalsIgnoreCase(job.getLocation())) {
            return 1.0; // Perfect match
        } else if (applicant.isWillingToRelocate()) {
            return 0.8; // Willing to relocate
        }
        return 0.0; // No match
    }


        // Overall match score
        public double overallMatchScore(Applicant applicant, Job job) {
        double skillScore = weightedSkillMatch(applicant, job);
        double experienceScore = experienceMatch(applicant, job);
        double locationScore = locationMatch(applicant, job);

        // Weighting different factors
        double skillWeight = 0.5;
        double experienceWeight = 0.3;
        double locationWeight = 0.2;

        return (skillScore * skillWeight) + (experienceScore * experienceWeight) + (locationScore * locationWeight);
    }


        // Finding the best matching job for an applicant
        public Job findBestMatchingJob(Applicant applicant, List<Job> jobList) {
        Job bestJob = null;
        double bestScore = 0;

        for (Job job : jobList) {
            double matchScore = overallMatchScore(applicant, job);
            if (matchScore > bestScore) {
                bestScore = matchScore;
                bestJob = job;
            }
        }

        return bestJob;
    }
}
