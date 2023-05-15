package com.khadim.githubuser.model;

import jakarta.persistence.*;

@Entity
@Table(name = "github_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String surName;
    private String position;

    private String gitHubUrl;

    public User() {

    }

    public User(Long id, String firstName, String surName, String position, String gitHubUrl) {
        this.id = id;
        this.firstName = firstName;
        this.surName = surName;
        this.position = position;
        this.gitHubUrl = gitHubUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getGitHubUrl() {
        return gitHubUrl;
    }

    public void setGitHubUrl(String gitHubUrl) {
        this.gitHubUrl = gitHubUrl;
    }
}
