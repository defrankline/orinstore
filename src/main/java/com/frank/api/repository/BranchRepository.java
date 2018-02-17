package com.frank.api.repository;

import com.frank.api.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    public List<Branch> findAllByShopId(Long shopId);
    public Branch findOneByCode(String code);
}