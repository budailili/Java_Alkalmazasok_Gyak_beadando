package hu.nje.autokonyv.config;

import hu.nje.autokonyv.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class WebSecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public WebSecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // Sikeres belépés után: admin -> CRUD oldal, regisztrált látogató -> Üzenetek oldal.
    // A context path-ot (pl. /gvlrpx) hozzá kell tenni, különben a Tomcat-en rossz helyre irányítana.
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            boolean admin = false;
            for (GrantedAuthority jog : authentication.getAuthorities()) {
                if (jog.getAuthority().equals("ROLE_ADMIN")) {
                    admin = true;
                }
            }
            String cel = admin ? "/crud" : "/uzenetek";
            response.sendRedirect(request.getContextPath() + cel);
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        // ezeket a nem bejelentkezett látogató is eléri
                        .requestMatchers("/", "/adatbazis", "/kapcsolat", "/diagram", "/rest",
                                "/login", "/register", "/error",
                                "/css/**", "/js/**", "/assets/**").permitAll()
                        .requestMatchers("/api/**").permitAll()
                        // csak bejelentkezett felhasználó (regisztrált látogató vagy admin)
                        .requestMatchers("/uzenetek").hasAnyRole("USER", "ADMIN")
                        // csak admin
                        .requestMatchers("/crud/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                // a REST API-t cURL-ből és Postmanből hívjuk, ott nincs CSRF token
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(authenticationSuccessHandler())
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/?kilepett")
                        .permitAll()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler((request, response, e) ->
                                response.sendRedirect(request.getContextPath() + "/?nincsjog"))
                );
        return http.build();
    }
}
