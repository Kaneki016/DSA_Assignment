/*shu han*/
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

    
    // Getters
    public String getJobPostId() {
        return jobPostId;
    }

    public Job getJob() {
        return job;
    }

    public Company getCompany() {
        return company;
    }
    
    //setter
    public void setCompany(Company company) {
        this.company = company;
    }

    public void setJob(Job job) {
        this.job = job;
    }
    
        @Override
    public String toString() {
        return "JobPost {" +
               "ID='" + jobPostId + '\'' +
               ", Job=" + job.getTitle() +
               ", Company=" + company.getCompanyName() +
               '}';
    }
    
}
