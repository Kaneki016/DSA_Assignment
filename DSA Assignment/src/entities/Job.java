/*shu han*/
package entities;

import adt.*;
import java.time.LocalDateTime;

public class Job {

    private String jobId;
    private String title;
    private String location;
    private int required_experience;
    private DoublyLinkedListInterface<JobRequirements> jobRequirements;
    private float salary;
    private LocalDateTime removedAt;


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
    
    public LocalDateTime getRemovedAt() {
        return removedAt;
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
    
    public void setRemovedAt(LocalDateTime removedAt) {
       this.removedAt = removedAt;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n===========================================================\n")
          .append(String.format("| %-20s | %-30s |\n", "Job ID", jobId))
          .append("-----------------------------------------------------------\n")
          .append(String.format("| %-20s | %-30s |\n", "Job Title", title))
          .append(String.format("| %-20s | %-30s |\n", "Location", location))
          .append(String.format("| %-20s | %-30d |\n", "Experience (yrs)", required_experience))
          .append(String.format("| %-20s | $%-29.2f |\n", "Salary", salary))
          .append("===========================================================\n")
          .append(String.format("| %-20s | %-30s |\n", "Job Requirements", ""))
          .append("-----------------------------------------------------------\n");

        if (jobRequirements == null || jobRequirements.isEmpty()) {
            sb.append(String.format("| %-20s | %-30s |\n", "", "None"));
        } else {
            int count = 1;
            for (JobRequirements req : jobRequirements) {
                String name = req.getName();
                String details = req.getCategory() + " (Level " + req.getProficiencyLevel() + ")";
                sb.append(String.format("| %-3d. %-16s | %-30s |\n", count++, name, details));
            }
        }

        sb.append("===========================================================\n");
        return sb.toString();
    }
}
