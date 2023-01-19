package com.cr.repository;

import com.cr.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    public List<Authority> findAllByRoleCode(String roleCode);
}
