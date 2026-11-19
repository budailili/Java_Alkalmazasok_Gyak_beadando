package hu.nje.autokonyv.controller;

import hu.nje.autokonyv.repository.JarmuRepository;
import hu.nje.autokonyv.repository.SzemelyRepository;
import hu.nje.autokonyv.repository.TelefonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FooldalController {

    private final SzemelyRepository szemelyRepo;
    private final JarmuRepository jarmuRepo;
    private final TelefonRepository telefonRepo;

    public FooldalController(SzemelyRepository szemelyRepo, JarmuRepository jarmuRepo, TelefonRepository telefonRepo) {
        this.szemelyRepo = szemelyRepo;
        this.jarmuRepo = jarmuRepo;
        this.telefonRepo = telefonRepo;
    }

    @GetMapping("/")
    public String fooldal(Model model) {
        model.addAttribute("szemelyDb", szemelyRepo.count());
        model.addAttribute("jarmuDb", jarmuRepo.count());
        model.addAttribute("telefonDb", telefonRepo.count());
        return "index";
    }

    @GetMapping("/rest")
    public String restLeiras() {
        return "rest";
    }
}
