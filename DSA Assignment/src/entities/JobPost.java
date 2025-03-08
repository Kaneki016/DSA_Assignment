
package entities;

public class JobPost {
    
    private static int nextId = 1;  // Auto-increment ID counter
    
    private String jobPostId;
    private Job job;
    private Company company;

    public JobPost(Job job, Company company) {
        this.jobPostId = String.format("JP%03d", nextId++); // Assign current ID and increment for the next Company
        this.job = job;
        this.company = company;
    }

    public String getJobPostId() {
        return jobPostId;
    }

    public Job getJob() {
        return job;
    }

    public Company getCompany() {
        return company;
    }

    public void setJobPostId(String jobPostId) {
        this.jobPostId = jobPostId;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    
    
    
}
