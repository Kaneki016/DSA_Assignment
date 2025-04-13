package controller;

import boundary.InputUI;
import entities.*;
import adt.*;
import java.util.Scanner;

public class SkillManager {

    private static SkillManager instance;
    
    //Boundary
    private Scanner scanner = new Scanner(System.in);
    private InputUI inputUI = new InputUI();

    //ADT
    private static DoublyLinkedListInterface<Skill> skills = new DoublyLinkedList<>();

    public SkillManager() {
        skills = new DoublyLinkedList<>();
    }

    // Singleton accessor
    public static SkillManager getInstance() {
        if (instance == null) {
            instance = new SkillManager();
        }
        return instance;
    }

    // Display all skills
    public String getSkillsAsString(DoublyLinkedListInterface<Skill> skills) {
        if (skills.isEmpty()) {
            return "No skills found";
        }
        StringBuilder sb = new StringBuilder();
        for (Skill skill : skills) {
            sb.append(skill.toString()).append("; ");
        }
        // Remove the last "; " if present
        if (sb.length() > 2) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }

    public DoublyLinkedListInterface<Skill> refreshSkillList(DoublyLinkedListInterface<Skill> skills) {
        skills.removeAll();
        return skills;
    }
    
        public DoublyLinkedListInterface<Skill> getSkillList() {
        return skills;
    }
}