/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author laiyo
 */

import entities.JobPost;
import entities.Job;
import entities.Company;
import adt.*;
import boundary.InputUI;

public class JobPostManager {

    private static JobPostManager instance;
    private DoublyLinkedListInterface<JobPost> jobPosts;
    private static InputUI inputUI = new InputUI();

    private JobPostManager() {
        jobPosts = new DoublyLinkedList<>();
    }

    public static JobPostManager getInstance() {
        if (instance == null) {
            instance = new JobPostManager();
        }
        return instance;
    }

    // Add a new JobPost instance
    public void addJobPost(Job job, Company company) {
        JobPost newJobPost = new JobPost(job, company);
        jobPosts.add(newJobPost);
        inputUI.displayMessage("New job post added with ID: " + newJobPost.getJobPostId());
    }

    // Get a job post by ID
    public JobPost getJobPostById(String jobPostId) {
        for (JobPost jobPost : jobPosts) {
            if (jobPost.getJobPostId().equals(jobPostId)) {
                return jobPost;
            }
        }
        return null; // Return null if not found
    }

    // Retrieve all job posts
    public DoublyLinkedListInterface<JobPost> getAllJobPosts() {
        return jobPosts;
    }
}

