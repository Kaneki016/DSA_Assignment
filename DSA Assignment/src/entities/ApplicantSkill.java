/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author MAMBA
 */
public class ApplicantSkill {
    private Applicant applicant;
    private Skill skill;
    private int proficiency_level;

    public ApplicantSkill(Applicant applicant, Skill skill, int proficiency_level) {
        this.applicant = applicant;
        this.skill = skill;
        this.proficiency_level = proficiency_level;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public Skill getSkill() {
        return skill;
    }

    public int getProficiency_level() {
        return proficiency_level;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public void setProficiency_level(int proficiency_level) {
        this.proficiency_level = proficiency_level;
    }
    
    

    
    
    
    
}
