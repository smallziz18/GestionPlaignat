package org.smallziz.gestionplaignat.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("testuser")
                .password(passwordEncoder().encode("testpassword"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/plaignants/register", "/plaignants/login")) // Ignorer CSRF pour les endpoints d'inscription et de connexion
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/plaignants/register", "/plaignants/login").permitAll()
                        .anyRequest().authenticated()) // Nécessite une authentification pour tous les autres endpoints
                .formLogin(formLogin -> formLogin
                        .loginPage("/plaignants/login") // Page de connexion personnalisée
                        .permitAll() // Permet l'accès à la page de connexion sans authentification
                        .defaultSuccessUrl("/home", true) // Redirection après une connexion réussie
                        .failureUrl("/plaignants/login?error=true")) // Redirection après un échec de connexion
                .logout(logout -> logout
                        .logoutUrl("/logout") // Endpoint pour la déconnexion
                        .logoutSuccessUrl("/plaignants/login?logout=true") // Redirection après une déconnexion réussie
                        .permitAll()); // Permet l'accès à l'endpoint de déconnexion sans authentification
        return http.build();
    }
}
