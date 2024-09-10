package org.smallziz.gestionplaignat.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsVerificationService {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String fromPhoneNumber;

    public SmsVerificationService(@Value("${twilio.account-sid}") String accountSid,
                                  @Value("${twilio.auth-token}") String authToken) {
        Twilio.init(accountSid, authToken);
    }

    public void sendVerificationCodeBySms(String toPhoneNumber, String verificationCode) {
        Message.creator(
                new com.twilio.type.PhoneNumber(toPhoneNumber),
                new com.twilio.type.PhoneNumber(fromPhoneNumber),
                "Votre code de vérification est : " + verificationCode
        ).create();
    }
}
