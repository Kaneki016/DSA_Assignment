/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author MAMBA
 */
public class ApplicantAppliedJob {

    private Applicant applicant;
    private JobPost jobPost;
    private double matchScore;

    private static int nextApplicationId = 1;
    private String applicationId;

    public ApplicantAppliedJob(Applicant applicant, JobPost jobPost, double matchScore) {
        this.applicationId = String.format("APP%03d", nextApplicationId++);
        this.applicant = applicant;
        this.jobPost = jobPost;
        this.matchScore = matchScore; // Default match score
    }
    
    public ApplicantAppliedJob(Applicant applicant, JobPost jobPost) {
        this.applicationId = String.format("APP%03d", nextApplicationId++);
        this.applicant = applicant;
        this.jobPost = jobPost;
        this.matchScore = 0.0; // Default match score
    }

    

    public String getApplicationId() {
        return applicationId;
    }
    
    public Applicant getApplicant() {
        return applicant;
    }

    public JobPost getJobPost() {
        return jobPost;
    }

    public double getMatchScore() {
        return matchScore;
    }

    @Override
    public String toString() {
        return "Applicant: " + applicant.getName()
                + " | Job: " + jobPost.getJob().getTitle()
                + " | Match Score: " + matchScore;
    }
}
