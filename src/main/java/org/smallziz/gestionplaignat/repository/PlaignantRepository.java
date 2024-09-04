package org.smallziz.gestionplaignat.repository;

import org.smallziz.gestionplaignat.model.Plaignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaignantRepository extends JpaRepository<Plaignant, String> {

    // Recherche d'un plaignant par son pseudo
    Optional<Plaignant> findPlaignantByPlaignantPseudo(String pseudo);

    // Recherche d'un plaignant par son email
    Optional<Plaignant> findByPlaignantEmail(String email);

    // Recherche d'un plaignant par son numéro de téléphone principal
    Optional<Plaignant> findByPlaignantTel1(String tel1);

    // Recherche d'un plaignant par son numéro de téléphone secondaire
    Optional<Plaignant> findByPlaignantTel2(String tel2);
}
