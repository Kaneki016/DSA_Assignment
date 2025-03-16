package controller;

import adt.*;
import entities.*;
import java.util.List;
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
    private static ApplicantAppliedJobManager applicantAppliedJobManager = getInstance();
    private static SkillManager skillManager = SkillManager.getInstance();
    private static CompanyManager companyManager = CompanyManager.getInstance();

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

    // Skill Matching with Proficiency Levels - Returns Match Score
    public double skillMatch(Applicant applicant, Job job) {
        double totalWeight = 0;
        double matchScore = 0;

        for (JobRequirements requirement : job.getJobRequirements()) {
            String requiredSkillName = requirement.getName();
            int requiredProficiencyInt = Integer.parseInt(requirement.getProficiencyLevel());

            // Get applicant's proficiency level for this skill
            int applicantProficiency = skillManager.getApplicantProficiencyLevel(applicant, requiredSkillName);

            if (applicantProficiency > 0) {
                double match = (double) applicantProficiency / requiredProficiencyInt; // Normalize
                matchScore += Math.min(match, 1.0); // Cap at 1.0
            }

            totalWeight++; // Count total skills considered
        }

        double finalScore = (totalWeight > 0) ? matchScore / totalWeight : 0;

        // Print matching result
        if (finalScore == 1.0) {
            System.out.println("✅ " + applicant.getName() + " FULLY matches skill requirements for " + job.getTitle());
        } else if (finalScore >= 0.5) {
            System.out.println("⚠️ " + applicant.getName() + " PARTIALLY matches skill requirements for " + job.getTitle());
        } else {
            System.out.println("❌ " + applicant.getName() + " does NOT match skill requirements for " + job.getTitle());
        }

        return finalScore;
    }


    // Experience Level Matching
    public double experienceMatch(Applicant applicant, Job job) {
        int jobExperienceRequired = job.getRequired_experience(); // Required years
        int applicantExperience = applicant.getYearsOfExperience(); // Applicant's years

        double matchScore = Math.min((double) applicantExperience / jobExperienceRequired, 1.0);

        // Print matching result
        if (matchScore == 1.0) {
            System.out.println("✅ " + applicant.getName() + " is a FULL match for " + job.getTitle() + 
                               " (Required: " + jobExperienceRequired + " years, Applicant: " + applicantExperience + " years)");
        } else if (matchScore >= 0.5) {
            System.out.println("⚠️ " + applicant.getName() + " is a PARTIAL match for " + job.getTitle() + 
                               " (Required: " + jobExperienceRequired + " years, Applicant: " + applicantExperience + " years)");
        } else {
            System.out.println("❌ " + applicant.getName() + " does NOT match " + job.getTitle() + 
                               " (Required: " + jobExperienceRequired + " years, Applicant: " + applicantExperience + " years)");
        }

        return matchScore;
    }

    // Location Preference Matching
    public double locationMatch(Applicant applicant, Job job) {
        String applicantLocation = applicant.getLocation();
        String jobLocation = job.getLocation();

        double matchScore;

        if (applicantLocation.equalsIgnoreCase(jobLocation)) {
            matchScore = 1.0; // Perfect match
            System.out.println("✅ " + applicant.getName() + " is a FULL location match for " + job.getTitle() + 
                               " (Job Location: " + jobLocation + ", Applicant Location: " + applicantLocation + ")");
        } else {
            matchScore = 0.0; // No match
            System.out.println("❌ " + applicant.getName() + " is NOT a location match for " + job.getTitle() + 
                               " (Job Location: " + jobLocation + ", Applicant Location: " + applicantLocation + ")");
        }

        return matchScore;
    }


    // Overall Match Score
    public double overallMatchScore(Applicant applicant, Job job) {
        double skillScore = skillMatch(applicant, job);
        double experienceScore = experienceMatch(applicant, job);
        double locationScore = locationMatch(applicant, job);

        // Assigning weights to different factors
        double skillWeight = 0.5;
        double experienceWeight = 0.3;
        double locationWeight = 0.2;

        double overallScore = (skillScore * skillWeight) + (experienceScore * experienceWeight) + (locationScore * locationWeight);

        // Printing the matching details
        System.out.println("\n🔎 Matching Results for Applicant: " + applicant.getName() + " & Job: " + job.getTitle());
        System.out.println("----------------------------------------------------");
        System.out.printf("🛠  Skill Match Score: %.2f (Weight: %.1f)\n", skillScore, skillWeight);
        System.out.printf("📅  Experience Match Score: %.2f (Weight: %.1f)\n", experienceScore, experienceWeight);
        System.out.printf("📍  Location Match Score: %.2f (Weight: %.1f)\n", locationScore, locationWeight);
        System.out.println("----------------------------------------------------");
        System.out.printf("🏆 Overall Match Score: %.2f\n\n", overallScore);

        return overallScore;
    }


    // Finding the Best Matching Job for an Applicant
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

        if (bestJob == null) {
            inputUI.displayMessage("No suitable job found for this applicant.");
        }
        return bestJob;
    }
}
