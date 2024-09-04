package org.smallziz.gestionplaignat.security;


import org.smallziz.gestionplaignat.model.Plaignant;
import org.smallziz.gestionplaignat.repository.PlaignantRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private PlaignantRepository plaignantRepository;
    public CustomUserDetailsService(PlaignantRepository plaignantRepository) {
        this.plaignantRepository = plaignantRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Plaignant plaignant = plaignantRepository.findPlaignantByPlaignantPseudo(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec le psedo: " + username));

        return new CustomUserDetails(plaignant.getPlaignantPseudo(), plaignant.getPlaignantId(), true);
    }
}
