package hu.nje.autokonyv;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// Erre azért van szükség, hogy a külső Tomcat el tudja indítani a WAR-t
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AutokonyvApplication.class);
    }
}
