package entities;

public class TimeSlot {

    private static int nextId = 1; // Auto-increment ID counter

    private String timeSlotId;
    private String time;
    private String date;
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

    @Override
    public String toString() {
        return String.format("| %-14s | %-19s | %-19s | %-14s |", timeSlotId, time, date, location);
    }

}
