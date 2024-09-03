package org.smallziz.gestionplaignat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "plaignant")
public class Plaignant {
    @Id
    @Size(max = 255)
    @Column(name = "PLAIGNANT_ID", nullable = false)
    private String plaignantId = UUID.randomUUID().toString();

    @Size(max = 255)
    @NotNull
    @UniqueElements
    @Column(name = "PLAIGNANT_PSEUDO", nullable = false)
    private String plaignantPseudo;

    @Size(max = 255)
    @NotNull
    @Column(name = "PLAIGNANT_PRENOM", nullable = false)
    private String plaignantPrenom;

    @Size(max = 255)
    @NotNull
    @Column(name = "PLAIGNANT_NOM", nullable = false)
    private String plaignantNom;

    @Size(max = 30)
    @NotNull
    @Column(name = "PLAIGNANT_TEL1", nullable = false, length = 30)
    private String plaignantTel1;

    @Size(max = 30)
    @Column(name = "PLAIGNANT_TEL2", length = 30)
    private String plaignantTel2;

    @Size(max = 50)
    @NotNull
    @Column(name = "PLAIGNANT_EMAIL", nullable = false, length = 50)
    private String plaignantEmail;

    @Column(name = "PLAIGNANT_SEXE")
    private Integer plaignantSexe;

    @Column(name = "PLAIGNANT_AGE")
    private Integer plaignantAge;

    @Size(max = 255)
    @Column(name = "PLAIGNANT_AVATAR")
    private String plaignantAvatar;

    /*@OneToMany(mappedBy = "plaignant")
    private Set<Complainte> complaintes = new LinkedHashSet<>();*/

}