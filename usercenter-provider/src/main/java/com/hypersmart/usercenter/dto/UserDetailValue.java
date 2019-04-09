package com.hypersmart.usercenter.dto;

import com.hypersmart.framework.model.GenericDTO;

import java.io.Serializable;
import java.util.List;

public class UserDetailValue extends GenericDTO<String> implements Serializable {
    private String projectName;
    private String devideName;
    private List<String> jobs;
    private Integer stars = 2;
    private Integer rank = 90;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDevideName() {
        return devideName;
    }

    public void setDevideName(String devideName) {
        this.devideName = devideName;
    }

    public List<String> getJobs() {
        return jobs;
    }

    public void setJobs(List<String> jobs) {
        this.jobs = jobs;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
