package org.smallziz.gestionplaignat.repository;

import org.smallziz.gestionplaignat.model.Plaignant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaignantRepository extends JpaRepository<Plaignant, String> {

}
