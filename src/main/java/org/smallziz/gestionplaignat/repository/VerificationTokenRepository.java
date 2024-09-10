package org.smallziz.gestionplaignat.repository;

import org.smallziz.gestionplaignat.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByPlaignantIdAndVerificationCode(String plaignantId, String verificationCode);
}
