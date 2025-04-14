package entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeSlot implements Comparable<TimeSlot> {

    private static int nextId = 1; // Auto-increment ID counter

    private String timeSlotId;
    private String time;   // e.g., "5.00pm"
    private String date;   // e.g., "6/3/2025"
    private String location;

    public TimeSlot(String time, String date, String location) {
        this.timeSlotId = String.format("TS%03d", nextId++);
        this.time = time;
        this.date = date;
        this.location = location;
    }

    public String getTimeSlotId() {
        return timeSlotId;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public void setTimeSlotId(String timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Custom parsing method for sorting
    public LocalDateTime getDateTime() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy h.mma");
            return LocalDateTime.parse(date + " " + normalizeTime(time), formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Failed to parse datetime: " + date + " " + time);
            return LocalDateTime.MIN;
        }
    }

    private String normalizeTime(String rawTime) {
        rawTime = rawTime.toLowerCase().replaceAll("\\s+", "");
        if (!rawTime.endsWith("am") && !rawTime.endsWith("pm")) {
            rawTime += "am"; // Default fallback
        }
        return rawTime;
    }

    @Override
    public int compareTo(TimeSlot other) {
        return this.getDateTime().compareTo(other.getDateTime());
    }

    @Override
    public String toString() {
        return String.format("| %-14s | %-19s | %-19s | %-14s |", timeSlotId, time, date, location);
    }
}
