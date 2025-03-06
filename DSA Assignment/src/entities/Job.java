/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author MAMBA
 */
public class Job {
    private int jobId;
    private String title;
    private String company;
    private String location;
    private int required_experience;
    private String required_skills;
    private float salary;
    
    private static int nextId = 1000;

    public Job(String title, String company, String location, int required_experience, String required_skills, float salary) {
        this.jobId = nextId++;
        this.title = title;
        this.company = company;
        this.location = location;
        this.required_experience = required_experience;
        this.required_skills = required_skills;
        this.salary = salary;
    }

    public int getJobId() {
        return jobId;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public int getRequired_experience() {
        return required_experience;
    }

    public String getRequired_skills() {
        return required_skills;
    }

    public float getSalary() {
        return salary;
    }

    public static int getNextId() {
        return nextId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    } 
    
    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRequired_experience(int required_experience) {
        this.required_experience = required_experience;
    }

    public void setRequired_skills(String required_skills) {
        this.required_skills = required_skills;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public static void setNextId(int nextId) {
        Job.nextId = nextId;
    }
    
    
    
    
}
