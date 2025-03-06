/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

public class Applicant {
    private int applicantId;
    private String name;
    private String location;
    private int yearsOfExperience;
    private String educationLevel;
    
    // Static counter starting at 1000
    private static int nextId = 1000;

    public Applicant(String name, String location, int yearsOfExperience, String educationLevel) {
        this.applicantId = nextId++;  // Assign current id and increment for next Applicant
        this.name = name;
        this.location = location;
        this.yearsOfExperience = yearsOfExperience;
        this.educationLevel = educationLevel;
    }

    public int getApplicantId() {
        return applicantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    @Override
    public String toString() {
        return "Applicant{" +
               "applicantId=" + applicantId +
               ", name='" + name + '\'' +
               ", location='" + location + '\'' +
               ", yearsOfExperience=" + yearsOfExperience +
               ", educationLevel='" + educationLevel + '\'' +
               '}';
    }
}
