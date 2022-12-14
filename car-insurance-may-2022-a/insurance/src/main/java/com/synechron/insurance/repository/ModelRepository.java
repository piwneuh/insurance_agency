package com.synechron.insurance.repository;

import com.synechron.insurance.model.car.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
    List<Model> findAllByBrandIdAndIsDeleted(Long id, boolean isDeleted);
}
