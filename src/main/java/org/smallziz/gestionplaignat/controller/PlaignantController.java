package org.smallziz.gestionplaignat.controller;

import org.smallziz.gestionplaignat.model.LoginRequest;
import org.smallziz.gestionplaignat.model.Plaignant;
import org.smallziz.gestionplaignat.services.PlaignantService;
import org.smallziz.gestionplaignat.services.UserVerificationService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus; // Import HttpStatus
import org.springframework.http.ResponseEntity; // Import ResponseEntity

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/plaignants")
public class PlaignantController {
    private final PlaignantService plaignantService;
    private final UserVerificationService userVerificationService;

    public PlaignantController(PlaignantService plaignantService, UserVerificationService userVerificationService) {
        this.plaignantService = plaignantService;
        this.userVerificationService = userVerificationService;
    }

    // Endpoint pour inscrire un nouvel utilisateur et envoyer le code de vérification par SMS et email
    @PostMapping("/register")
    public ResponseEntity<String> registerPlaignant(@RequestBody Plaignant plaignant) {
        // Vérifier si le plaignant existe déjà
        if (plaignantService.existsByPseudo(plaignant.getPlaignantPseudo())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Un utilisateur avec ce pseudo existe déjà.");
        }

        // Sauvegarde du plaignant
        plaignantService.savePlaignant(plaignant);

        // Envoi du code de vérification par SMS et par email
        userVerificationService.sendVerificationCode(plaignant.getPlaignantId(), plaignant.getPlaignantTel1(), plaignant.getPlaignantEmail());

        return ResponseEntity.ok("Un code de vérification a été envoyé par SMS et par email.");
    }

    // Récupérer tous les plaignants
    @GetMapping
    public List<Plaignant> getAllPlaignants() {
        return plaignantService.findAllPlaignants();
    }

    // Récupérer un plaignant par ID
    @GetMapping("/{id}")
    public Optional<Plaignant> getPlaignantById(@PathVariable String id) {
        return plaignantService.findPlaignantById(id);
    }

    // Mettre à jour un plaignant par ID
    @PutMapping("/update/{id}")
    public Plaignant updatePlaignant(@PathVariable String id, @RequestBody Plaignant updatedPlaignant) {
        return plaignantService.updatePlaignant(id, updatedPlaignant);
    }

    // Supprimer un plaignant par ID
    @DeleteMapping("/delete/{id}")
    public void deletePlaignant(@PathVariable String id) {
        plaignantService.deletePlaignant(id);
    }

    // Endpoint pour vérifier le code de vérification
    @PostMapping("/verify")
    public ResponseEntity<String> verifyPlaignant(@RequestParam String plaignantId, @RequestParam String code) {
        boolean isVerified = userVerificationService.verifyCode(plaignantId, code);
        if (isVerified) {
            // Mettre à jour l'état de vérification du plaignant
            Plaignant plaignant = plaignantService.findPlaignantById(plaignantId).orElse(null);
            if (plaignant != null) {
                plaignant.setVerified(true);
                plaignantService.savePlaignant(plaignant); // Sauvegarder le statut vérifié
                return ResponseEntity.ok("Vérification réussie.");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Code de vérification incorrect ou expiré.");
        }
    }

    // Endpoint pour la connexion de l'utilisateur (login)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        Optional<Plaignant> plaignantOptional = plaignantService.findPlaignantByPseudo(loginRequest.getPseudo());

        if (plaignantOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Pseudo ou mot de passe incorrect.");
        }

        Plaignant plaignant = plaignantOptional.get();

        if (!plaignant.isVerified()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Veuillez vérifier votre compte avant de vous connecter.");
        }

        boolean isAuthenticated = plaignantService.authenticatePlaignant(loginRequest.getPseudo(), loginRequest.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok("Connexion réussie");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Pseudo ou mot de passe incorrect");
        }
    }
}
