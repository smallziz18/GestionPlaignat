package org.smallziz.gestionplaignat.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smallziz.gestionplaignat.security.CustomUserDetailsService;
import org.smallziz.gestionplaignat.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Slf4j // Utilisation de Lombok pour le logging
public class AuthController {
    public AuthController(AuthenticationManager authenticationManager,JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }
    private AuthenticationManager authenticationManager;


    private JwtUtil jwtUtil;


    private CustomUserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public String createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception e) {
            log.error("Erreur d'authentification pour l'utilisateur : {}", authRequest.getUsername(), e);
            throw new Exception("Nom d'utilisateur ou mot de passe incorrect", e);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authRequest.getUsername());

        return jwtUtil.generateToken(userDetails);
    }
}


@Data
@AllArgsConstructor
@NoArgsConstructor
class AuthRequest {
    private String username;
    private String password;
}
