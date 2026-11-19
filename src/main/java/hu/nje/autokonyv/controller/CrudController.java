package hu.nje.autokonyv.controller;

import hu.nje.autokonyv.entity.Szemely;
import hu.nje.autokonyv.entity.Telefon;
import hu.nje.autokonyv.repository.JarmuRepository;
import hu.nje.autokonyv.repository.SzemelyRepository;
import hu.nje.autokonyv.repository.TelefonRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

// CRUD menü a szemely táblához - csak ADMIN szerepkörrel érhető el
@Controller
@RequestMapping("/crud")
public class CrudController {

    private final SzemelyRepository szemelyRepo;
    private final JarmuRepository jarmuRepo;
    private final TelefonRepository telefonRepo;

    public CrudController(SzemelyRepository szemelyRepo, JarmuRepository jarmuRepo, TelefonRepository telefonRepo) {
        this.szemelyRepo = szemelyRepo;
        this.jarmuRepo = jarmuRepo;
        this.telefonRepo = telefonRepo;
    }

    // Read
    @GetMapping
    public String lista(Model model) {
        model.addAttribute("szemelyek", szemelyRepo.findAllByOrderByNevAsc());
        return "crud/lista";
    }

    // Create - űrlap
    @GetMapping("/uj")
    public String ujSzemely(Model model) {
        model.addAttribute("szemely", new Szemely());
        model.addAttribute("jarmuvek", jarmuRepo.findAllByOrderByMarkaAscRndszmAsc());
        return "crud/urlap";
    }

    // Update - űrlap a meglévő adatokkal
    @GetMapping("/modosit/{id}")
    public String modosit(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        Optional<Szemely> talalat = szemelyRepo.findById(id);
        if (talalat.isEmpty()) {
            ra.addFlashAttribute("hiba", "Nincs ilyen azonosítójú személy: " + id);
            return "redirect:/crud";
        }
        model.addAttribute("szemely", talalat.get());
        model.addAttribute("jarmuvek", jarmuRepo.findAllByOrderByMarkaAscRndszmAsc());
        return "crud/urlap";
    }

    // Create és Update mentése (ha van id, akkor módosítás)
    @PostMapping("/ment")
    public String ment(@Valid @ModelAttribute("szemely") Szemely szemely, BindingResult hibak,
                       Model model, RedirectAttributes ra) {
        if (szemely.getRendszam() != null && !szemely.getRendszam().isEmpty()
                && !jarmuRepo.existsById(szemely.getRendszam())) {
            hibak.rejectValue("rendszam", "nincs", "Nincs ilyen rendszámú jármű");
        }
        if (hibak.hasErrors()) {
            model.addAttribute("jarmuvek", jarmuRepo.findAllByOrderByMarkaAscRndszmAsc());
            return "crud/urlap";
        }
        boolean uj = (szemely.getId() == null);
        szemely.setNev(szemely.getNev().trim());
        szemelyRepo.save(szemely);
        ra.addFlashAttribute("siker", uj ? "Új személy felvéve: " + szemely.getNev()
                                         : "Módosítva: " + szemely.getNev());
        return "redirect:/crud";
    }

    // Delete - POST kéréssel, hogy linkre kattintva véletlenül se lehessen törölni
    @PostMapping("/torol/{id}")
    public String torol(@PathVariable("id") Integer id, RedirectAttributes ra) {
        Optional<Szemely> talalat = szemelyRepo.findById(id);
        if (talalat.isEmpty()) {
            ra.addFlashAttribute("hiba", "Nincs ilyen azonosítójú személy: " + id);
            return "redirect:/crud";
        }
        // a személy telefonszámait is töröljük, hogy ne maradjanak "gazdátlan" sorok
        for (Telefon t : telefonRepo.findBySzemelyid(id)) {
            telefonRepo.delete(t);
        }
        szemelyRepo.delete(talalat.get());
        ra.addFlashAttribute("siker", "Törölve: " + talalat.get().getNev());
        return "redirect:/crud";
    }
}
