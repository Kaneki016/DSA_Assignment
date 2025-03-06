/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

public class Skill {
    private int skillId;
    private String name;
    private String category;
    private int proficiency_level;
    
    private static int nextId = 1000;

    public Skill(String name, String category, int proficiency_level) {
        this.skillId = nextId++;
        this.name = name;
        this.category = category;
        this.proficiency_level = proficiency_level;
    }

    public int getSkillId() {
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

    public void setSkillId(int skillId) {
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
      
    
}
