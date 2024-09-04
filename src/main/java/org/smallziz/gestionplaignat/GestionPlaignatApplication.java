package org.smallziz.gestionplaignat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class GestionPlaignatApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionPlaignatApplication.class, args);
    }

}
