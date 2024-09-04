package org.smallziz.gestionplaignat.services;

import org.smallziz.gestionplaignat.model.Plaignant;
import org.smallziz.gestionplaignat.repository.PlaignantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaignantService {

    @Autowired
    private PlaignantRepository plaignantRepository;


    public List<Plaignant> findAllPlaignants() {
        return plaignantRepository.findAll();
    }

    public Optional<Plaignant> findPlaignantById(String id) {
        return plaignantRepository.findById(id);
    }

    public Plaignant savePlaignant(Plaignant plaignant) {
        return plaignantRepository.save(plaignant);
    }

    public void deletePlaignant(String id) {
        plaignantRepository.deleteById(id);
    }

    public Plaignant updatePlaignant(String id, Plaignant updatedPlaignant) {
        return plaignantRepository.findById(id)
                .map(plaignant -> {
                    // mise à jour des champs du plaignant
                    plaignant.setPlaignantPseudo(updatedPlaignant.getPlaignantPseudo());
                    plaignant.setPlaignantPrenom(updatedPlaignant.getPlaignantPrenom());
                    plaignant.setPlaignantNom(updatedPlaignant.getPlaignantNom());
                    plaignant.setPlaignantTel1(updatedPlaignant.getPlaignantTel1());
                    plaignant.setPlaignantTel2(updatedPlaignant.getPlaignantTel2());
                    plaignant.setPlaignantEmail(updatedPlaignant.getPlaignantEmail());
                    plaignant.setPlaignantSexe(updatedPlaignant.getPlaignantSexe());
                    plaignant.setPlaignantAge(updatedPlaignant.getPlaignantAge());
                    plaignant.setPlaignantAvatar(updatedPlaignant.getPlaignantAvatar());
                    return plaignantRepository.save(plaignant);
                })
                .orElseGet(() -> {
                    updatedPlaignant.setPlaignantId(id);
                    return plaignantRepository.save(updatedPlaignant);
                });
    }
}
