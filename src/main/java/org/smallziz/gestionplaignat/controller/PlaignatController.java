package org.smallziz.gestionplaignat.controller;

import org.smallziz.gestionplaignat.model.Plaignant;
import org.smallziz.gestionplaignat.services.PlaignantService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus; // Import HttpStatus
import org.springframework.http.ResponseEntity; // Import ResponseEntity

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/plaignants")
public class PlaignatController {
    private final PlaignantService plaignantService;

    public PlaignatController(PlaignantService plaignantService) {
        this.plaignantService = plaignantService;
    }

    // Ajouter un nouveau plaignant
    @PostMapping("/add")
    public Plaignant addPlaignant(@RequestBody Plaignant plaignant) {
        return plaignantService.savePlaignant(plaignant);
    }

    // Récupérer tous les plaignants
    @GetMapping("/plaignants")
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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String pseudo, @RequestParam String password) {
        boolean isAuthenticated = plaignantService.authenticatePlaignant(pseudo, password);
        if (isAuthenticated) {
            return ResponseEntity.ok("Connexion réussie");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Pseudo ou mot de passe incorrect");
        }
    }
}


