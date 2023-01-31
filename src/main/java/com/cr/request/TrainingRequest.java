package com.cr.request;


public class TrainingRequest {
    private Long userId;
    private Long id;
    private String training1;
    private String training2;
    private String training3;
    private String experience1;
    private String experience2;
    private String experience3;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTraining1() {
        return training1;
    }

    public void setTraining1(String training1) {
        this.training1 = training1;
    }

    public String getTraining2() {
        return training2;
    }

    public void setTraining2(String training2) {
        this.training2 = training2;
    }

    public String getTraining3() {
        return training3;
    }

    public void setTraining3(String training3) {
        this.training3 = training3;
    }

    public String getExperience1() {
        return experience1;
    }

    public void setExperience1(String experience1) {
        this.experience1 = experience1;
    }

    public String getExperience2() {
        return experience2;
    }

    public void setExperience2(String experience2) {
        this.experience2 = experience2;
    }

    public String getExperience3() {
        return experience3;
    }

    public void setExperience3(String experience3) {
        this.experience3 = experience3;
    }
}
