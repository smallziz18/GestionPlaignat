package org.smallziz.gestionplaignat.services;

import jakarta.validation.constraints.NotNull;
import org.smallziz.gestionplaignat.model.Plaignant;
import org.smallziz.gestionplaignat.repository.PlaignantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaignantService {


    private PlaignantRepository plaignantRepository;

    public PlaignantService(PlaignantRepository plaignantRepository) {
        this.plaignantRepository = plaignantRepository;
    }


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

    // Méthode pour authentifier un utilisateur
    public boolean authenticatePlaignant(String pseudo, String password) {
        Optional<Plaignant> plaignant = plaignantRepository.findByPlaignantPseudo(pseudo);
        // Vérifiez si le mot de passe est correct (comparaison directe ici mais vous devriez hacher les mots de passe)
        return plaignant.map(value -> value.getMotDePasse().equals(password)).orElse(false);// Retourner false si l'utilisateur n'existe pas ou si le mot de passe est incorrect
    }


    public boolean existsByPseudo(@NotNull String plaignantPseudo) {
        return plaignantRepository.findByPlaignantPseudo(plaignantPseudo).isPresent();
    }
}
