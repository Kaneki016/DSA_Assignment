/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import entities.*;
import adt.*;

/**
 *
 * @author laiyo
 */
public class JobMatcher {
    
    
        // Skill Matching with proficiency levels
        public double calculateSkillMatch(Applicant applicant, Job job) {
        SkillManager skillManager = SkillManager.getInstance(); // Get the singleton instance of SkillManager

        double totalWeight = 0;
        double matchScore = 0;

        for (JobRequirements req : job.getJobRequirements()) {
            String requiredSkill = req.getName();
            int requiredProficiency = req.getProficiency_level();
            double weight = req.getWeight(); // Ensure JobRequirement has a weight attribute

            // Retrieve applicant's proficiency using SkillManager
            int applicantProficiency = skillManager.getApplicantProficiencyLevel(applicant, requiredSkill);

            if (applicantProficiency > 0) {
                double score = (double) applicantProficiency / requiredProficiency;
                score = Math.min(score, 1.0); // Cap at 1.0
                matchScore += score * weight;
            }
            totalWeight += weight;
        }

        return totalWeight > 0 ? matchScore / totalWeight : 0;
    }



        // Weighted skill match
        public double weightedSkillMatch(Applicant applicant, Job job) {
        double weightedScore = 0;
        double totalWeight = 0;

        for (JobRequirement req : job.getRequirements()) {
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
