package org.smallziz.gestionplaignat.services;

import org.smallziz.gestionplaignat.model.VerificationToken;
import org.smallziz.gestionplaignat.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserVerificationService {

    public UserVerificationService(SmsVerificationService smsVerificationService, EmailVerificationService emailVerificationService, VerificationTokenRepository verificationTokenRepository) {
        this.smsVerificationService = smsVerificationService;
        this.emailVerificationService = emailVerificationService;
        this.verificationTokenRepository = verificationTokenRepository;
    }


    private SmsVerificationService smsVerificationService;


    private EmailVerificationService emailVerificationService;


    private VerificationTokenRepository verificationTokenRepository;

    public void sendVerificationCode(String plaignantId, String phoneNumber, String email) {
        String verificationCode = VerificationCodeGenerator.generateVerificationCode();
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(10); // Expire dans 10 minutes
        VerificationToken token = new VerificationToken(plaignantId, verificationCode, expirationDate);

        // Sauvegarde le token dans la base de données
        verificationTokenRepository.save(token);

        // Envoi du code par SMS et par email
       // smsVerificationService.sendVerificationCodeBySms(phoneNumber, verificationCode);
        emailVerificationService.sendVerificationCodeByEmail(email, verificationCode);
    }

    public boolean verifyCode(String plaignantId, String code) {
        Optional<VerificationToken> optionalToken = verificationTokenRepository.findByPlaignantIdAndVerificationCode(plaignantId, code);

        if (optionalToken.isPresent()) {
            VerificationToken token = optionalToken.get();
            if (token.getExpirationDate().isAfter(LocalDateTime.now())) {
                // Code valide
                return true;
            }
        }
        // Code invalide ou expiré
        return false;
    }
}
