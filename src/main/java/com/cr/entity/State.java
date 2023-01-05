package com.cr.entity;

import javax.persistence.*;
import java.util.List;

@Table(name = "STATES_T")
@Entity
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "state_name", nullable = false)
    private String stateName;

    @OneToMany(mappedBy = "state")
    private List<City> cityList;

    @OneToOne(orphanRemoval = true)
    @JoinTable(name = "states_t_user",
            joinColumns = @JoinColumn(name = "state_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
