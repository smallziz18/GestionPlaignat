package org.smallziz.gestionplaignat.services;

import java.util.Random;

public class VerificationCodeGenerator {
    public static String generateVerificationCode() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000)); // Génère un code à 4 chiffres
    }
}
