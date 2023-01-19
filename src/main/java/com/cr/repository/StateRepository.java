package com.cr.repository;

import com.cr.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {
    State findByStateName(String stateName);
}
