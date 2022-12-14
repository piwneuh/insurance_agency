package com.synechron.insurance.repository;

import com.synechron.insurance.model.car.Risk;
import com.synechron.insurance.model.users.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findByJmbg(String jmbg);
    List<Driver> findDriversByRisksContaining(Risk risk);
}
