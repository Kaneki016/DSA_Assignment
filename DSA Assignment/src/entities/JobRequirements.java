/*shu han*/
package entities;

import java.time.LocalDateTime;

public class JobRequirements {

    private static int nextId = 1;  // Auto-increment ID counter
     private String jobRequirementId;
    private String name;
    private String proficiencyLevel;
    private String category;
    private LocalDateTime removedAt;

    public JobRequirements(String name, String proficiencyLevel, String category) {
        this.jobRequirementId = String.format("JR%03d", nextId++);
        this.name = name;
        this.proficiencyLevel = proficiencyLevel;
        this.category = category;
    }

    // Getters
    public String getJobRequirementId() {
        return jobRequirementId;
    }

    public String getName() {
        return name;
    }

    public String getProficiencyLevel() {
        return proficiencyLevel;
    }

    public String getCategory() {
        return category;
    }
    
    public LocalDateTime getRemovedAt() {
        return removedAt;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setProficiencyLevel(String proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }

    public void setCategory(String category) {
        this.category = category;
    }
   
    public void setRemovedAt(LocalDateTime removedAt) {
        this.removedAt = removedAt;
    }
    
    public static void setNextId(int id) {
        nextId = id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Display Job Requirement data in a table format
        sb.append("\n===========================================================\n")
          .append(String.format("| %-20s | %-30s |\n", "Job Requirement ID", "Job Requirement Data"))
          .append("-----------------------------------------------------------\n");

        // Add job requirement details in table format
        sb.append(String.format("| %-20s | %-30s |\n", "ID", this.getJobRequirementId()))
          .append(String.format("| %-20s | %-30s |\n", "Name", this.getName()))
          .append(String.format("| %-20s | %-30s |\n", "Proficiency Level", this.getProficiencyLevel()))
          .append(String.format("| %-20s | %-30s |\n", "Category", this.getCategory()));

        sb.append("===========================================================\n");
        return sb.toString();

    }
}
