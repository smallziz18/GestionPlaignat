package org.smallziz.gestionplaignat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "plaignant")
public class Plaignant implements UserDetails, Principal {
    @Id
    @Column(name = "PLAIGNANT_ID", nullable = false)
    private String plaignantId = UUID.randomUUID().toString();


    @NotNull
    @Column(name = "PLAIGNANT_PSEUDO", nullable = false, unique = true)
    private String plaignantPseudo;


    @NotNull
    @Column(name = "PLAIGNANT_PRENOM", nullable = false)
    private String plaignantPrenom;


    @NotNull
    @Column(name = "PLAIGNANT_NOM", nullable = false)
    private String plaignantNom;

    @Size(max = 30)
    @NotNull
    @Column(name = "PLAIGNANT_TEL1", nullable = false, length = 13)
    private String plaignantTel1;

    @Size(max = 30)
    @Column(name = "PLAIGNANT_TEL2", length = 13)
    private String plaignantTel2;

    @Size(max = 50)
    @Email
    @Column(name = "PLAIGNANT_EMAIL", nullable = true, length = 50)
    private String plaignantEmail;

    @Column(name = "PLAIGNANT_SEXE")
    @NotNull
    private Integer plaignantSexe;

    @NotNull
    @Column(name = "PLAIGNANT_AGE")
    private Integer plaignantAge;

    @Size(max = 255)
    @Column(name = "PLAIGNANT_AVATAR")
    private String plaignantAvatar;

    private boolean accountLocked;
    private String motDePasse;



    @Override
    public String getName() {
        return plaignantPseudo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return motDePasse;
    }

    @Override
    public String getUsername() {
        return plaignantPseudo;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /*@OneToMany(mappedBy = "plaignant")
    private Set<Complainte> complaintes = new LinkedHashSet<>();*/

}