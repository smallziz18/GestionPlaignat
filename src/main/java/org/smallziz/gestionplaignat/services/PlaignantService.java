package org.smallziz.gestionplaignat.services;

import org.smallziz.gestionplaignat.model.Plaignant;
import org.smallziz.gestionplaignat.repository.PlaignantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaignantService {

    private final PlaignantRepository plaignantRepository;


    @Autowired
    public PlaignantService(PlaignantRepository plaignantRepository) {
        this.plaignantRepository = plaignantRepository;

    }

    // Vérifier si un utilisateur existe par pseudo
    public boolean existsByPseudo(String pseudo) {
        return plaignantRepository.findPlaignantByPlaignantPseudo(pseudo).isPresent();
    }

    // Enregistrer un nouveau plaignant
    public Plaignant savePlaignant(Plaignant plaignant) {
        // Hashage du mot de passe avant de sauvegarder
        plaignant.setMotDePasse((plaignant.getMotDePasse()));
        return plaignantRepository.save(plaignant);
    }

    // Récupérer tous les plaignants
    public List<Plaignant> findAllPlaignants() {
        return plaignantRepository.findAll();
    }

    // Récupérer un plaignant par ID
    public Optional<Plaignant> findPlaignantById(String id) {
        return plaignantRepository.findById(id);
    }

    // Récupérer un plaignant par pseudo
    public Optional<Plaignant> findPlaignantByPseudo(String pseudo) {
        return plaignantRepository.findPlaignantByPlaignantPseudo(pseudo);
    }

    // Mettre à jour un plaignant par ID
    public Plaignant updatePlaignant(String id, Plaignant updatedPlaignant) {
        return plaignantRepository.findById(id)
                .map(plaignant -> {
                    plaignant.setPlaignantPseudo(updatedPlaignant.getPlaignantPseudo());
                    plaignant.setPlaignantPrenom(updatedPlaignant.getPlaignantPrenom());
                    plaignant.setPlaignantNom(updatedPlaignant.getPlaignantNom());
                    plaignant.setPlaignantTel1(updatedPlaignant.getPlaignantTel1());
                    plaignant.setPlaignantTel2(updatedPlaignant.getPlaignantTel2());
                    plaignant.setPlaignantEmail(updatedPlaignant.getPlaignantEmail());
                    plaignant.setPlaignantSexe(updatedPlaignant.getPlaignantSexe());
                    plaignant.setPlaignantAge(updatedPlaignant.getPlaignantAge());
                    plaignant.setPlaignantAvatar(updatedPlaignant.getPlaignantAvatar());
                    // Mise à jour du mot de passe (hashé)
                    plaignant.setMotDePasse((updatedPlaignant.getMotDePasse()));
                    return plaignantRepository.save(plaignant);
                })
                .orElseGet(() -> {
                    updatedPlaignant.setPlaignantId(id);
                    updatedPlaignant.setMotDePasse((updatedPlaignant.getMotDePasse()));
                    return plaignantRepository.save(updatedPlaignant);
                });
    }

    // Supprimer un plaignant par ID
    public void deletePlaignant(String id) {
        plaignantRepository.deleteById(id);
    }

    // Authentifier un utilisateur
    public boolean authenticatePlaignant(String pseudo, String password) {
        Optional<Plaignant> plaignantOptional = findPlaignantByPseudo(pseudo);
        return plaignantOptional.isPresent() && password.equals(plaignantOptional.get().getMotDePasse());
    }
    // Modifier le mot de passe en passant l'ancien mot de passe et le nouveau
    public String updatePassword(String pseudo, String oldPassword, String newPassword) {
        Optional<Plaignant> plaignantOptional = plaignantRepository.findPlaignantByPlaignantPseudo(pseudo);
        if (plaignantOptional.isPresent()) {
            Plaignant plaignant = plaignantOptional.get();
            if (oldPassword.equals(plaignant.getMotDePasse()))  {


                plaignantRepository.save(plaignant);
                return "Le mot de passe a été mis à jour avec succès.";
            } else {
                return "L'ancien mot de passe est incorrect.";
            }
        }
        return "Utilisateur non trouvé.";
    }

}
