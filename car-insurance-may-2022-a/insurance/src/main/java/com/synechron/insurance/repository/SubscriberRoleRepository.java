package com.synechron.insurance.repository;

import com.synechron.insurance.model.users.SubscriberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriberRoleRepository extends JpaRepository<SubscriberRole, Long> {
    Optional<SubscriberRole> findByName(String name);
}
