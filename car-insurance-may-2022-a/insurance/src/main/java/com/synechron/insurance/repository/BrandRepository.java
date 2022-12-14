package com.synechron.insurance.repository;

import com.synechron.insurance.model.car.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    List<Brand> findAllByIsDeleted(boolean b);
}
