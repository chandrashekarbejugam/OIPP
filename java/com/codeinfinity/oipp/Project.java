package com.codeinfinity.oipp;

import java.util.List;

public class Project {
    private String projectId;
    private String title;
    private String description;
    private List<String> tags;
    private List<String> teamMembers; // Change from String[] to List<String>
    private String projectDuration;

    private String uploadDate;

    private String domainUsed;

    private String uploadTime;
    private String budget;
    private String ownerId;

    // Default constructor for Firebase serialization
    public Project() {
    }

    // Constructor for creating a new project
    public Project(String projectId, String title, String description, List<String> tags,
                   List<String> teamMembers, String projectDuration, String budget, String ownerId,
                   String uploadDate, String uploadTime, String domainUsed) {
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.domainUsed = domainUsed;
        this.uploadDate = uploadDate;
        this.uploadTime = uploadTime;
        this.teamMembers = teamMembers;
        this.projectDuration = projectDuration;
        this.budget = budget;
        this.ownerId = ownerId;
    }

    // Getters and setters
    // ... (unchanged)


    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getDomainUsed() {
        return domainUsed;
    }

    public void setDomainUsed(String domainUsed) {
        this.domainUsed = domainUsed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getProjectDuration() {
        return projectDuration;
    }

    public void setProjectDuration(String projectDuration) {
        this.projectDuration = projectDuration;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public List<String> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<String> teamMembers) {
        this.teamMembers = teamMembers;
    }
}




