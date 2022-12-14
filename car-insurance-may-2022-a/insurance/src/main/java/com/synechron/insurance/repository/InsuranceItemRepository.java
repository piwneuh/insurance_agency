package com.synechron.insurance.repository;

import com.synechron.insurance.model.proposal.InsuranceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsuranceItemRepository extends JpaRepository<InsuranceItem, Long> {
    List<InsuranceItem> findAllByIsDeleted(boolean b);
}
