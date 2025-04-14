/*shu han*/
package entities;

public class JobRequirements {

    private static int nextId = 1;  // Auto-increment ID counter
     private String jobRequirementId;
    private String name;
    private String proficiencyLevel;
    private String category;

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

    @Override
    public String toString() {
        return "JobRequirements {" +
               "ID='" + jobRequirementId + '\'' +
               ", Name='" + name + '\'' +
               ", Proficiency Level='" + proficiencyLevel + '\'' +
               ", Category='" + category + '\'' +
               '}';
    }
}
