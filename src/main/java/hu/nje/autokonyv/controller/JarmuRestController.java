package hu.nje.autokonyv.controller;

import hu.nje.autokonyv.entity.Jarmu;
import hu.nje.autokonyv.repository.JarmuRepository;
import hu.nje.autokonyv.repository.SzemelyRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// RESTful API a jarmu táblához: /api/jarmuvek
@RestController
@RequestMapping("/api/jarmuvek")
public class JarmuRestController {

    private final JarmuRepository jarmuRepo;
    private final SzemelyRepository szemelyRepo;

    public JarmuRestController(JarmuRepository jarmuRepo, SzemelyRepository szemelyRepo) {
        this.jarmuRepo = jarmuRepo;
        this.szemelyRepo = szemelyRepo;
    }

    // GET /api/jarmuvek - összes jármű
    @GetMapping
    public Iterable<Jarmu> osszes() {
        return jarmuRepo.findAllByOrderByMarkaAscRndszmAsc();
    }

    // GET /api/jarmuvek/ABC123 - egy jármű
    @GetMapping("/{rndszm}")
    public ResponseEntity<Object> egy(@PathVariable("rndszm") String rndszm) {
        Optional<Jarmu> talalat = jarmuRepo.findById(rndszm.toUpperCase());
        if (talalat.isEmpty()) {
            return hiba(HttpStatus.NOT_FOUND, "Nincs ilyen rendszámú jármű: " + rndszm);
        }
        return ResponseEntity.ok(talalat.get());
    }

    // POST /api/jarmuvek - új jármű
    @PostMapping
    public ResponseEntity<Object> uj(@Valid @RequestBody Jarmu jarmu, BindingResult hibak) {
        if (hibak.hasErrors()) {
            return validaciosHiba(hibak);
        }
        if (jarmuRepo.existsById(jarmu.getRndszm())) {
            return hiba(HttpStatus.CONFLICT, "Ez a rendszám már szerepel: " + jarmu.getRndszm());
        }
        Jarmu mentett = jarmuRepo.save(jarmu);
        return ResponseEntity.created(URI.create("/api/jarmuvek/" + mentett.getRndszm())).body(mentett);
    }

    // PUT /api/jarmuvek/ABC123 - módosítás (a rendszám nem változtatható, az a kulcs)
    @PutMapping("/{rndszm}")
    public ResponseEntity<Object> modosit(@PathVariable("rndszm") String rndszm, @RequestBody Jarmu adat) {
        Optional<Jarmu> talalat = jarmuRepo.findById(rndszm.toUpperCase());
        if (talalat.isEmpty()) {
            return hiba(HttpStatus.NOT_FOUND, "Nincs ilyen rendszámú jármű: " + rndszm);
        }
        if (ures(adat.getMarka()) || ures(adat.getSzin())) {
            return hiba(HttpStatus.BAD_REQUEST, "A márka és a szín megadása kötelező");
        }
        Jarmu jarmu = talalat.get();
        jarmu.setMarka(adat.getMarka().trim());
        jarmu.setSzin(adat.getSzin().trim());
        return ResponseEntity.ok(jarmuRepo.save(jarmu));
    }

    // DELETE /api/jarmuvek/ABC123 - törlés
    @DeleteMapping("/{rndszm}")
    public ResponseEntity<Object> torol(@PathVariable("rndszm") String rndszm) {
        String kulcs = rndszm.toUpperCase();
        if (!jarmuRepo.existsById(kulcs)) {
            return hiba(HttpStatus.NOT_FOUND, "Nincs ilyen rendszámú jármű: " + rndszm);
        }
        // ha valakinek ez az autója, nem engedjük törölni
        if (!szemelyRepo.findByRendszam(kulcs).isEmpty()) {
            return hiba(HttpStatus.CONFLICT, "A jármű nem törölhető, mert tartozik hozzá személy");
        }
        jarmuRepo.deleteById(kulcs);
        return ResponseEntity.noContent().build();
    }

    private boolean ures(String s) {
        return s == null || s.trim().isEmpty();
    }

    private ResponseEntity<Object> hiba(HttpStatus statusz, String uzenet) {
        Map<String, Object> valasz = new LinkedHashMap<>();
        valasz.put("statusz", statusz.value());
        valasz.put("hiba", uzenet);
        return ResponseEntity.status(statusz).body(valasz);
    }

    private ResponseEntity<Object> validaciosHiba(BindingResult hibak) {
        List<String> lista = new ArrayList<>();
        for (FieldError fe : hibak.getFieldErrors()) {
            lista.add(fe.getField() + ": " + fe.getDefaultMessage());
        }
        Map<String, Object> valasz = new LinkedHashMap<>();
        valasz.put("statusz", 400);
        valasz.put("hiba", "Hibás adatok");
        valasz.put("reszletek", lista);
        return ResponseEntity.badRequest().body(valasz);
    }
}
