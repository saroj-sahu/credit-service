package com.cr.repository;

import com.cr.entity.Rank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RankRepository extends JpaRepository<Rank, Long> {
    List<Rank> findAllByBranchId(Long branchId);
}
