package entities;

public class Interview {

    // Static counter for automatically assigning unique IDs, starting at 1000
    private static int nextId = 1000;

    // Fields for the Interview
    private int interviewId;
    private int applicantId;
    private int jobPostId;
    private String time;      // Could be a date/time string (e.g., "2025-03-15 14:30")
    private String location;  // Where the interview takes place (e.g., "Office", "Zoom")
    private String mode;      // How the interview is conducted (e.g., "in-person", "virtual")
    private String status;    // Status of the interview (e.g., "Scheduled", "Completed", "Cancelled")
    private String feedback;  // Interviewer's feedback or notes after the interview

    public Interview() {
        this.interviewId = nextId++;
    }

    public Interview(int applicantId, int jobPostId, String time, String location, String mode, String status, String feedback) {
        this.interviewId = nextId++;
        this.applicantId = applicantId;
        this.jobPostId = jobPostId;
        this.time = time;
        this.location = location;
        this.mode = mode;
        this.status = status;
        this.feedback = feedback;
    }


    public int getInterviewId() {
        return interviewId;
    }

    public int getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(int applicantId) {
        this.applicantId = applicantId;
    }

    public int getJobPostId() {
        return jobPostId;
    }

    public void setJobPostId(int companyId) {
        this.jobPostId = companyId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    // Override toString() to return a string representation of the Interview details.
    @Override
    public String toString() {
        return "Interview [ID=" + interviewId + 
               ", ApplicantID=" + applicantId + 
               ", JobPostId=" + jobPostId + 
               ", Time=" + time + 
               ", Location=" + location + 
               ", Mode=" + mode + 
               ", Status=" + status + 
               ", Feedback=" + feedback + "]";
    }
}
