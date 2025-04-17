package entities;
import boundary.*;

public class Interview {

    // Static counter for automatically assigning unique IDs, starting at 1000
    private static int nextId = 1;  // Auto-increment ID counter

    // Fields for the Interview
    private String interviewId;
    private ApplicantAppliedJob applicantAppliedJob;
    private TimeSlot timeslot;
    private String mode;
    private String status;
    private String feedback;
    private int favourRate;
    
    //InputUI
    MenuUI menuUI = new MenuUI();

    public Interview(ApplicantAppliedJob applicantAppliedJob, TimeSlot timeslot, String mode, String status, String feedback, int favourRate) {
        this.interviewId = String.format("I%03d", nextId++);
        this.applicantAppliedJob = applicantAppliedJob;
        this.timeslot = timeslot;
        this.mode = mode;
        this.status = status;
        this.feedback = feedback;
        this.favourRate = favourRate;

    }

    public String getInterviewId() {
        return interviewId;
    }

    public ApplicantAppliedJob getApplicantAppliedJob() {
        return applicantAppliedJob;
    }

    public TimeSlot getTimeslot() {
        return timeslot;
    }

    public String getMode() {
        return mode;
    }

    public String getStatus() {
        return status;
    }

    public String getFeedback() {
        return feedback;
    }

    public int getFavourRate() {
        return favourRate;
    }

    public void setInterviewId(String interviewId) {
        this.interviewId = interviewId;
    }

    public void setApplicantAppliedJob(ApplicantAppliedJob applicantAppliedJob) {
        this.applicantAppliedJob = applicantAppliedJob;
    }

    public void setTimeslot(TimeSlot timeslot) {
        this.timeslot = timeslot;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setFavourRate(int favourRate) {
        this.favourRate = favourRate;
    }

    @Override
    public String toString() {
        String line = menuUI.repeat("-", 160) + "\n";
        return line
                + String.format("| %-12s | %-14s | %-12s | %-19s | %-8s | %-10s | %-30s | %-11s |\n",
                        "Interview ID", "Application ID", "Applicant ID", "Time Slot", "Mode", "Status", "Feedback", "Favour Rate")
                + line
                + String.format("| %-12s | %-14s | %-12s | %-19s | %-8s | %-10s | %-30s | %-11s |\n",
                        interviewId,
                        applicantAppliedJob.getApplicationId(),
                        applicantAppliedJob.getApplicant().getApplicantId(),
                        timeslot.getTime(),
                        mode,
                        status,
                        feedback,
                        favourRate)
                + line;
    }


}
