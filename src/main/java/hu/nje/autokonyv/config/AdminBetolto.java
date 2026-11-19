package hu.nje.autokonyv.config;

import hu.nje.autokonyv.entity.User;
import hu.nje.autokonyv.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

// Induláskor létrehozza az admin felhasználót, ha még nincs az adatbázisban.
// Regisztrációval csak USER szerepkörű felhasználó jöhet létre, ezért kell ez.
@Configuration
public class AdminBetolto {

    @Bean
    CommandLineRunner adminLetrehozasa(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                userRepository.save(new User("admin", passwordEncoder.encode("Admin2026!"), "ADMIN"));
            }
        };
    }
}
