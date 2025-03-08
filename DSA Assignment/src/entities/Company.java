/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;
    
public class Company {
    private static int nextId = 1;  // Auto-increment ID counter

    private String companyId;
    private String companyName;
    private String companyLocation;
    private int companySize;
    private String companyDescription;
    private int password;

    public Company(String companyName, String companyLocation, int companySize, String companyDescription, int password) {
        this.companyId = String.format("C%03d", nextId++); // Assign current ID and increment for the next Company
        this.companyName = companyName;
        this.companyLocation = companyLocation;
        this.companySize = companySize;
        this.companyDescription = companyDescription;
        this.password = password;
    }

    // Getters and setters

    public String getCompanyId() {
        return companyId;
    }
    
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }

    public int getCompanySize() {
        return companySize;
    }

    public void setCompanySize(int companySize) {
        this.companySize = companySize;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    @Override
    public String toString() {
        return "Company{" +
               "companyId=" + companyId +
               ", companyName='" + companyName + '\'' +
               ", companyLocation='" + companyLocation + '\'' +
               ", companySize=" + companySize +
               ", companyDescription='" + companyDescription + '\'' +
               '}';
    }
}

