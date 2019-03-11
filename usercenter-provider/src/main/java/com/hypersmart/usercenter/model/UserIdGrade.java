package com.hypersmart.usercenter.model;



public class UserIdGrade {
    private String userId;
    private String grade;

    public UserIdGrade() {
    }

    public UserIdGrade(String userId, String grade) {
        this.userId = userId;
        this.grade = grade;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
