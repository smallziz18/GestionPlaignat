package org.smallziz.gestionplaignat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String plaignantId;
    private String verificationCode;
    private LocalDateTime expirationDate;


    public VerificationToken(String plaignantId, String verificationCode, LocalDateTime expirationDate) {
        this.plaignantId = plaignantId;
        this.verificationCode = verificationCode;
        this.expirationDate = expirationDate;
            
    }
}
