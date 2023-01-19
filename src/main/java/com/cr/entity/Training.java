package com.cr.entity;

import javax.persistence.*;

@Table(name = "USER_TRAINING_T")
@Entity
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "training1")
    private String training1;

    @Column(name = "training2")
    private String training2;

    @Column(name = "training3")
    private String training3;

    @Column(name = "experience1")
    private String experience1;

    @Column(name = "experience2")
    private String experience2;

    @Column(name = "experience3")
    private String experience3;

    @OneToOne(mappedBy = "training")
    private  User user;

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
