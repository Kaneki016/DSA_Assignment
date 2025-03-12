/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import adt.*;

/**
 *
 * @author MAMBA
 */
public class Job {

    private int jobId;
    private String title;
    private String location;
    private int required_experience;
    private DoublyLinkedListInterface<JobRequirements> jobRequirements;
    private float salary;

    private static int nextId = 1000;

    public Job(String title, String location, int required_experience, DoublyLinkedListInterface<JobRequirements> jobRequirements, float salary) {
        this.jobId = nextId++;
        this.title = title;
        this.location = location;
        this.required_experience = required_experience;
        this.jobRequirements = jobRequirements;
        this.salary = salary;
    }

    public int getJobId() {
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

    public void setJobId(int jobId) {
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

}
