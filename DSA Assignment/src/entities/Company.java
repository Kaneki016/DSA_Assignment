/*shu han*/
package entities;

import java.time.LocalDateTime;

public class Company {
    private static int nextId = 1;  // Auto-increment ID counter

    private String companyId;
    private String companyName;
    private String companyLocation;
    private int companySize;
    private String companyDescription;
    private LocalDateTime removedAt;

    // Constructor
    public Company(String companyName, String companyLocation, int companySize, String companyDescription) {
        this.companyId = String.format("C%03d", nextId++); // e.g., C001, C002, etc.
        this.companyName = companyName;
        this.companyLocation = companyLocation;
        this.companySize = companySize;
        this.companyDescription = companyDescription;
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
    
    public LocalDateTime getRemovedAt() {
        return removedAt;
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
    
    public void setRemovedAt(LocalDateTime removedAt) {
        this.removedAt = removedAt;
    }

    // display of company details
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n===========================================================\n")
          .append(String.format("| %-20s | %-30s |\n", "Company ID", companyId))
          .append("-----------------------------------------------------------\n")
          .append(String.format("| %-20s | %-30s |\n", "Company Name", companyName))
          .append(String.format("| %-20s | %-30s |\n", "Location", companyLocation))
          .append(String.format("| %-20s | %-30d |\n", "Company Size", companySize))
          .append("-----------------------------------------------------------\n")
          .append(String.format("| %-20s | %-30s |\n", "Description", ""));

        // Inline wrapping logic (30 char width)
        int wrapWidth = 30;
        for (int i = 0; i < companyDescription.length(); i += wrapWidth) {
            int end = Math.min(i + wrapWidth, companyDescription.length());
            String line = companyDescription.substring(i, end);
            sb.append(String.format("| %-20s | %-30s |\n", "", line));
        }

        sb.append("===========================================================\n");
        return sb.toString();
    }
}
