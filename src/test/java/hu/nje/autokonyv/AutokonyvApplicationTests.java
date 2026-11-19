package hu.nje.autokonyv;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// adatbázis nélkül nem indul el a context, ezért a WAR készítésekor kikapcsoljuk
@Disabled
@SpringBootTest
class AutokonyvApplicationTests {

    
    @Test
    void contextLoads() {
    }
}
