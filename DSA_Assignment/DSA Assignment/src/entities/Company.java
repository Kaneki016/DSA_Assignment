/*shu han*/
package entities;
    
public class Company {
    private static int nextId = 1;  // Auto-increment ID counter

    private String companyId;
    private String companyName;
    private String companyLocation;
    private int companySize;
    private String companyDescription;
    private String password; // Changed from int to String!

    // Constructor
    public Company(String companyName, String companyLocation, int companySize, String companyDescription, String password) {
        this.companyId = String.format("C%03d", nextId++); // e.g., C001, C002, etc.
        this.companyName = companyName;
        this.companyLocation = companyLocation;
        this.companySize = companySize;
        this.companyDescription = companyDescription;
        this.password = password;
    }

    // Getters
    public String getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public int getCompanySize() {
        return companySize;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }

    public void setCompanySize(int companySize) {
        this.companySize = companySize;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // For easy display of company details
    @Override
    public String toString() {
        return "Company {" +
               "ID='" + companyId + '\'' +
               ", Name='" + companyName + '\'' +
               ", Location='" + companyLocation + '\'' +
               ", Size=" + companySize +
               ", Description='" + companyDescription + '\'' +
               '}';
    }
}
