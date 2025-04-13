/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

public class Skill {
    private String skillId;
    private String name;
    private String category;
    private int proficiency_level;
    
    private static int nextId = 1;  // Auto-increment ID counter

    public Skill(String name, String category, int proficiency_level) {
        this.skillId = String.format("S%03d", nextId++); 
        this.name = name;
        this.category = category;
        this.proficiency_level = proficiency_level;
    }

    public String getSkillId() {
        return skillId;
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

    public static int getNextId() {
        return nextId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
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

    public static void setNextId(int nextId) {
        Skill.nextId = nextId;
    }

    @Override
    public String toString() {
        return String.format(
            "Skill {%n" +
            "    skillId: %s,%n" +
            "    name: %s,%n" +
            "    category: %s,%n" +
            "    proficiencyLevel: %d%n" +
            "}", 
            skillId, name, category, proficiency_level
        );
    }     

}
