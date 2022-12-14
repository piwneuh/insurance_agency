package com.synechron.authservice.repository;

import com.synechron.authservice.model.UserActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserActivationTokenRepository extends JpaRepository<UserActivationToken, Long> {
    Optional<UserActivationToken> findFirstByTokenOrderByExpiresAtDesc(String token);
    void deleteAllByUserId(Long userId);
}
