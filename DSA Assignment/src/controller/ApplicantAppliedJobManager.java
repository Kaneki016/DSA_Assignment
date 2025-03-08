package controller;

import adt.*;
import entities.ApplicantAppliedJob;
import boundary.InputUI;

public class ApplicantAppliedJobManager {

    private static ApplicantAppliedJobManager instance;
    private DoublyLinkedListInterface<ApplicantAppliedJob> applicantAppliedJob; 
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

    public void addApplicantAppliedJob(ApplicantAppliedJob newAppliedJob) {
        applicantAppliedJob.add(newAppliedJob);
        inputUI.displayMessage("A new applicant applied job added!\n");
    }
    
    // New getter method to expose the list
    public DoublyLinkedListInterface<ApplicantAppliedJob> getApplicantAppliedJobs() {
        return applicantAppliedJob;
    }
}
