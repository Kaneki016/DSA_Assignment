package controller;

import entities.TimeSlot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;

import adt.DoublyLinkedList;
import adt.DoublyLinkedListInterface;

public class TimeSlotManager {
    private static TimeSlotManager instance;

    // ADT
    private static DoublyLinkedListInterface<TimeSlot> timeSlots = new DoublyLinkedList<>();
    private static DoublyLinkedListInterface<TimeSlot> suggestedTimeSlots = new DoublyLinkedList<>();

    public TimeSlotManager() {
        timeSlots = new DoublyLinkedList<>();
    }

    public static TimeSlotManager getInstance() {
        if (instance == null) {
            instance = new TimeSlotManager();
        }
        return instance;
    }

    public void addTimeSlot(TimeSlot timeSlot) {
        timeSlots.add(timeSlot);
    }

    public void addSuggestedTimeSlot(TimeSlot timeSlot) {
        suggestedTimeSlots.add(timeSlot);
    }

    public void removeTimeSlot(TimeSlot timeSlot) {
        timeSlots.removeSpecific(timeSlot);
    }

    public DoublyLinkedListInterface<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public DoublyLinkedListInterface<TimeSlot> getSuggestedTimeSlots() {
        return suggestedTimeSlots;
    }

    public void sortTheTimeTable() {
        timeSlots.mergeSort((t1, t2) -> t1.compareTo(t2));
    }

    public boolean isValidTimeFormat(String time) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h.mma");
            time = time.toLowerCase().replaceAll("\\s+", "");
            if (!time.endsWith("am") && !time.endsWith("pm"))
                return false;
            LocalDateTime.parse("1/1/2025 " + time, DateTimeFormatter.ofPattern("d/M/yyyy h.mma"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isValidDateFormat(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            formatter.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
