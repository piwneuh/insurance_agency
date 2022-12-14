package com.synechron.insurance.repository;

import com.synechron.insurance.model.users.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    List<Country> findAllByIsDeleted(boolean b);
}
