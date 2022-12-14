package com.synechron.insurance.repository;

import com.synechron.insurance.model.proposal.InsurancePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsurancePlanRepository extends JpaRepository<InsurancePlan, Long> {
    List<InsurancePlan> findAllByIsDeleted(boolean b);
}
