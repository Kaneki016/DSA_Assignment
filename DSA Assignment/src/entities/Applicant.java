package entities;

import adt.*;
import controller.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Applicant {

    private static int nextId = 1;  // Auto-increment ID counter
    
    //Controller
    SkillManager skillManager = new SkillManager();
    
    private String applicantId;
    private String name;
    private int age;
    private String location;
    private int yearsOfExperience;
    private String educationLevel;
    private DoublyLinkedListInterface<Skill> skills;
    private String dateAdded; // Store date when applicant is created

    // Constructor
    public Applicant(String name, int age, String location, int yearsOfExperience, String educationLevel, DoublyLinkedListInterface<Skill> skills) {
        this.applicantId = String.format("A%03d", nextId++);
        this.name = name;
        this.location = location;
        this.age = age;
        this.educationLevel = educationLevel;
        this.yearsOfExperience = yearsOfExperience;
        this.dateAdded = getCurrentDateTime();
        this.skills = skills;
    }

    public static void setNextId(int nextId) {
        Applicant.nextId = nextId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setSkills(DoublyLinkedListInterface<Skill> skills) {
        this.skills = skills;
    }

   
    private String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    // Getters and 
    public String getApplicantId() {
        return applicantId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getLocation() {
        return location;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public DoublyLinkedListInterface<Skill> getSkills() {
        return skills;
    }

    
    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Age: %d years old, Location: %s, Experience: %d years, Education: %s, " + "Date Added: %s"  + ", \n\nSkill: %s",
                applicantId, name, age, location, yearsOfExperience, educationLevel, dateAdded, skillManager.getSkillsAsString(skills));
    }
}
