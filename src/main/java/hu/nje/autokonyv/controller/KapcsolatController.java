package hu.nje.autokonyv.controller;

import hu.nje.autokonyv.entity.Uzenet;
import hu.nje.autokonyv.repository.UzenetRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class KapcsolatController {

    private final UzenetRepository uzenetRepo;

    public KapcsolatController(UzenetRepository uzenetRepo) {
        this.uzenetRepo = uzenetRepo;
    }

    @GetMapping("/kapcsolat")
    public String kapcsolat(Model model) {
        model.addAttribute("uzenet", new Uzenet());
        return "kapcsolat";
    }

    @PostMapping("/kapcsolat")
    public String kuldes(@Valid @ModelAttribute("uzenet") Uzenet uzenet, BindingResult hibak,
                         Principal principal, RedirectAttributes ra) {
        // szerver oldali validáció: hiba esetén visszaadjuk az űrlapot a hibaüzenetekkel
        if (hibak.hasErrors()) {
            return "kapcsolat";
        }
        uzenet.setId(null);
        uzenet.setNev(uzenet.getNev().trim());
        uzenet.setKuldesIdeje(LocalDateTime.now());
        if (principal != null) {
            uzenet.setKuldo(principal.getName());
        }
        uzenetRepo.save(uzenet);
        ra.addFlashAttribute("siker", "Köszönjük, üzenetét megkaptuk!");
        return "redirect:/kapcsolat";
    }

    // csak bejelentkezett felhasználó érheti el (WebSecurityConfig)
    @GetMapping("/uzenetek")
    public String uzenetek(Model model) {
        model.addAttribute("uzenetek", uzenetRepo.findAllByOrderByKuldesIdejeDesc());
        return "uzenetek";
    }
}
