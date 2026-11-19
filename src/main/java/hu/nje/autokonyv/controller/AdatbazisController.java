package hu.nje.autokonyv.controller;

import hu.nje.autokonyv.entity.Jarmu;
import hu.nje.autokonyv.entity.Szemely;
import hu.nje.autokonyv.repository.JarmuRepository;
import hu.nje.autokonyv.repository.SzemelyRepository;
import hu.nje.autokonyv.repository.TelefonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

// Adatbázis menü: a szemely, jarmu és telefon tábla adatainak megjelenítése
@Controller
public class AdatbazisController {

    private final SzemelyRepository szemelyRepo;
    private final JarmuRepository jarmuRepo;
    private final TelefonRepository telefonRepo;

    public AdatbazisController(SzemelyRepository szemelyRepo, JarmuRepository jarmuRepo, TelefonRepository telefonRepo) {
        this.szemelyRepo = szemelyRepo;
        this.jarmuRepo = jarmuRepo;
        this.telefonRepo = telefonRepo;
    }

    @GetMapping("/adatbazis")
    public String adatbazis(@RequestParam(name = "marka", required = false) String marka, Model model) {
        List<Szemely> osszes = szemelyRepo.findAllByOrderByNevAsc();

        // márkák listája a szűrőhöz (minden márka csak egyszer szerepeljen)
        List<String> markak = new ArrayList<>();
        for (Jarmu j : jarmuRepo.findAllByOrderByMarkaAscRndszmAsc()) {
            if (!markak.contains(j.getMarka())) {
                markak.add(j.getMarka());
            }
        }

        // szűrés márka szerint, ha választottak
        List<Szemely> szemelyek = new ArrayList<>();
        for (Szemely sz : osszes) {
            if (marka == null || marka.isEmpty()
                    || (sz.getJarmu() != null && sz.getJarmu().getMarka().equals(marka))) {
                szemelyek.add(sz);
            }
        }

        // járművek a tulajdonosaikkal együtt
        List<String[]> jarmuSorok = new ArrayList<>();
        for (Jarmu j : jarmuRepo.findAllByOrderByMarkaAscRndszmAsc()) {
            String tulajdonosok = "";
            for (Szemely sz : osszes) {
                if (j.getRndszm().equals(sz.getRendszam())) {
                    if (!tulajdonosok.isEmpty()) {
                        tulajdonosok += ", ";
                    }
                    tulajdonosok += sz.getNev();
                }
            }
            if (tulajdonosok.isEmpty()) {
                tulajdonosok = "nincs tulajdonos";
            }
            jarmuSorok.add(new String[]{j.getRndszm(), j.getMarka(), j.getSzin(), tulajdonosok});
        }

        model.addAttribute("szemelyek", szemelyek);
        model.addAttribute("markak", markak);
        model.addAttribute("valasztottMarka", marka);
        model.addAttribute("jarmuSorok", jarmuSorok);
        model.addAttribute("telefonok", telefonRepo.findAll());
        return "adatbazis";
    }
}
