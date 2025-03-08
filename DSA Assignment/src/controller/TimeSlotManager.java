package controller;

import entities.TimeSlot;
import adt.DoublyLinkedList;
import adt.DoublyLinkedListInterface;

public class TimeSlotManager {
    private static TimeSlotManager instance;

    //ADT
    private static DoublyLinkedListInterface<TimeSlot> timeSlots = new DoublyLinkedList<>();

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

    public void removeTimeSlot(TimeSlot timeSlot) {
        timeSlots.removeSpecific(timeSlot);
    }

    public DoublyLinkedListInterface<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

}
