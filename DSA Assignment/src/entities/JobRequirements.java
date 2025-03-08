/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author MAMBA
 */
public class JobRequirements {

    private static int nextId = 1;  // Auto-increment ID counter
    private String jobRequirementsId;
    private String name;
    private String category;
    private int proficiency_level; //1-5

    public JobRequirements(String name, String category, int proficiency_level) {
        this.jobRequirementsId = String.format("JR%03d", nextId++);  
        this.name = name;
        this.category = category;
        this.proficiency_level = proficiency_level;
    }

    public String getJobRequirementsId() {
        return jobRequirementsId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getProficiency_level() {
        return proficiency_level;
    }

    public void setJobRequirementsId(String jobRequirementsId) {
        this.jobRequirementsId = jobRequirementsId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setProficiency_level(int proficiency_level) {
        this.proficiency_level = proficiency_level;
    }

}
