package com.cr.repository;

import com.cr.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    Branch findByBranchName(String branchName);
}
