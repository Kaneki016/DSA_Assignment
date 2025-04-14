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
        StringBuilder sb = new StringBuilder();

        sb.append("\n===========================================================\n")
                .append(String.format("| %-20s | %-30s |\n", "Job Post ID", jobPostId))
                .append("-----------------------------------------------------------\n")
                .append(String.format("| %-20s | %-30s |\n", "Job Title", job.getTitle()))
                .append(String.format("| %-20s | %-30s |\n", "Company Name", company.getCompanyName()))
                .append(String.format("| %-20s | %-30s |\n", "Location", job.getLocation()))
                .append(String.format("| %-20s | %-30d |\n", "Required Exp (yrs)", job.getRequired_experience()))
                .append(String.format("| %-20s | $%-29.2f |\n", "Salary", job.getSalary()))
                .append("===========================================================\n")
                .append(String.format("| %-20s | %-30s |\n", "Job Requirements", ""))
                .append("-----------------------------------------------------------\n");

        // Add job requirements
        if (job.getJobRequirements() == null || job.getJobRequirements().isEmpty()) {
            sb.append(String.format("| %-20s | %-30s |\n", "", "No specific requirements"));
        } else {
            int count = 1;
            for (JobRequirements req : job.getJobRequirements()) {
                sb.append(String.format("| %-3d. %-16s | %-30s |\n", count++, req.getName(),
                        req.getCategory() + " (Level " + req.getProficiencyLevel()+ ")"));
            }
        }

        sb.append("===========================================================\n");
        return sb.toString();
    }

}
