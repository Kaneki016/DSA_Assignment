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

    public ApplicantAppliedJob(Applicant applicant, JobPost jobPost) {
        this.applicant = applicant;
        this.jobPost = jobPost;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public JobPost getJobPost() {
        return jobPost;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public void setJobPost(JobPost jobPost) {
        this.jobPost = jobPost;
    }

    

    
    
    
    
}
