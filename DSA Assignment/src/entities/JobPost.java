
package entities;

public class JobPost {
    
    private JobPost jobPost;
    private Job job;
    private Company company;

    public JobPost(JobPost jobPost, Job job, Company company) {
        this.jobPost = jobPost;
        this.job = job;
        this.company = company;
    }

    public JobPost getJobPost() {
        return jobPost;
    }

    public Job getJob() {
        return job;
    }

    public Company getCompany() {
        return company;
    }

    public void setJobPost(JobPost jobPost) {
        this.jobPost = jobPost;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    
    
    
}
