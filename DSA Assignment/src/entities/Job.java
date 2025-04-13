/*shu han*/
package entities;

import adt.*;

public class Job {

    private String jobId;
    private String title;
    private String location;
    private int required_experience;
    private DoublyLinkedListInterface<JobRequirements> jobRequirements;
    private float salary;

    private static int nextId = 1000;

    public Job(String title, String location, int required_experience, DoublyLinkedListInterface<JobRequirements> jobRequirements, float salary) {
        this.jobId = String.format("J%04d", nextId++); // e.g., J1000, J1001
        this.title = title;
        this.location = location;
        this.required_experience = required_experience;
        this.jobRequirements = jobRequirements;
        this.salary = salary;
    }

    public String getJobId() {
        return jobId;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public int getRequired_experience() {
        return required_experience;
    }

    public DoublyLinkedListInterface<JobRequirements> getJobRequirements() {
        return jobRequirements;
    }

    public float getSalary() {
        return salary;
    }

    public static int getNextId() {
        return nextId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRequired_experience(int required_experience) {
        this.required_experience = required_experience;
    }

    public void setJobRequirements(DoublyLinkedListInterface<JobRequirements> jobRequirements) {
        this.jobRequirements = jobRequirements;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public static void setNextId(int nextId) {
        Job.nextId = nextId;
    }
    
    @Override
public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("Job ID: ").append(jobId).append("\n");
    sb.append("Title: ").append(title).append("\n");
    sb.append("Location: ").append(location).append("\n");
    sb.append("Required Experience: ").append(required_experience).append(" years\n");
    sb.append("Salary: $").append(String.format("%.2f", salary)).append("\n");

    sb.append("Requirements:\n");

    if (jobRequirements == null || jobRequirements.isEmpty()) {
        sb.append("  None\n");
    } else {
        int index = 1;
        for (JobRequirements req : jobRequirements) {
            sb.append("  ").append(index++).append(". ").append(req.toString()).append("\n");
        }
    }

    return sb.toString();
}

}
