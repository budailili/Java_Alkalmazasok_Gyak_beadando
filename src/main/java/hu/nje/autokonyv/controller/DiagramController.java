package hu.nje.autokonyv.controller;

import hu.nje.autokonyv.entity.Jarmu;
import hu.nje.autokonyv.entity.Szemely;
import hu.nje.autokonyv.repository.JarmuRepository;
import hu.nje.autokonyv.repository.SzemelyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

// A diagramok adatait itt számoljuk ki, a Chart.js csak megjeleníti őket
@Controller
public class DiagramController {

    private final JarmuRepository jarmuRepo;
    private final SzemelyRepository szemelyRepo;

    public DiagramController(JarmuRepository jarmuRepo, SzemelyRepository szemelyRepo) {
        this.jarmuRepo = jarmuRepo;
        this.szemelyRepo = szemelyRepo;
    }

    @GetMapping("/diagram")
    public String diagram(Model model) {
        // 1. járművek száma márkánként, 2. járművek száma színenként
        List<String> markak = new ArrayList<>();
        List<Integer> markaDb = new ArrayList<>();
        List<String> szinek = new ArrayList<>();
        List<Integer> szinDb = new ArrayList<>();
        for (Jarmu j : jarmuRepo.findAllByOrderByMarkaAscRndszmAsc()) {
            szamol(markak, markaDb, j.getMarka());
            szamol(szinek, szinDb, j.getSzin());
        }
        List<String> szinKodok = new ArrayList<>();
        for (String szin : szinek) {
            szinKodok.add(szinKod(szin));
        }

        // 3. személyek magassága és az átlag
        List<String> nevek = new ArrayList<>();
        List<Integer> magassagok = new ArrayList<>();
        List<Integer> telefonDb = new ArrayList<>();
        int osszeg = 0;
        for (Szemely sz : szemelyRepo.findAllByOrderByNevAsc()) {
            nevek.add(sz.getNev());
            magassagok.add(sz.getMagassag());
            osszeg += sz.getMagassag();
            telefonDb.add(sz.getTelefonok() == null ? 0 : sz.getTelefonok().size());
        }
        double atlag = 0;
        if (!nevek.isEmpty()) {
            atlag = Math.round(osszeg * 10.0 / nevek.size()) / 10.0;
        }
        List<Double> atlagVonal = new ArrayList<>();
        for (int i = 0; i < nevek.size(); i++) {
            atlagVonal.add(atlag);
        }

        model.addAttribute("markak", markak);
        model.addAttribute("markaDb", markaDb);
        model.addAttribute("szinek", szinek);
        model.addAttribute("szinDb", szinDb);
        model.addAttribute("szinKodok", szinKodok);
        model.addAttribute("nevek", nevek);
        model.addAttribute("magassagok", magassagok);
        model.addAttribute("telefonDb", telefonDb);
        model.addAttribute("atlagVonal", atlagVonal);
        model.addAttribute("atlag", atlag);
        return "diagram";
    }

    // ha a kulcs már szerepel, növeljük a darabszámát, különben felvesszük 1-gyel
    private void szamol(List<String> kulcsok, List<Integer> darabok, String kulcs) {
        int index = kulcsok.indexOf(kulcs);
        if (index == -1) {
            kulcsok.add(kulcs);
            darabok.add(1);
        } else {
            darabok.set(index, darabok.get(index) + 1);
        }
    }

    // a diagram szeletei az autó valódi színét kapják
    private String szinKod(String szin) {
        switch (szin.toLowerCase()) {
            case "piros": return "#c0392b";
            case "kék": return "#2c6fbb";
            case "fehér": return "#e8e8e8";
            case "fekete": return "#222222";
            case "zöld": return "#3a8f5c";
            case "sárga": return "#e5b622";
            case "szürke": return "#9aa3ab";
            case "ezüst": return "#c4cace";
            default: return "#64a19d";
        }
    }
}
