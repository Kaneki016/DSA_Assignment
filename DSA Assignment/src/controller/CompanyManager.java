/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import adt.DoublyLinkedList;
import adt.DoublyLinkedListInterface;
import entities.*;
import boundary.*;

/**
 *
 * @author MAMBA
 */
public class CompanyManager {
    
    //Menu UI
    private static InputUI inputUI = new InputUI();

    //Instance Definer
    private static CompanyManager instance;

    // Singleton accessor
    public static CompanyManager getInstance() {
        if (instance == null) {
            instance = new CompanyManager();
        }
        return instance;
    }

    //Doubly Linked List ADT
    private DoublyLinkedListInterface<Company> company;

    public CompanyManager() {
        company = new DoublyLinkedList<>();
    }

    //Find Company based on companyId
    public Company findCompanyById(String companyId) {
        for (Company company : company) {
            if (company.getCompanyId().equals(companyId)) {
                return company;
            }
        }
        inputUI.displayMessage("Company not existed!");
        return null;
    }

    //Add company
    public void addCompany(Company newCompany) {
        company.add(newCompany);
    }

}
