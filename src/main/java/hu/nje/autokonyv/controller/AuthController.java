package hu.nje.autokonyv.controller;

import hu.nje.autokonyv.entity.User;
import hu.nje.autokonyv.form.RegisztracioForm;
import hu.nje.autokonyv.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerOldal(Model model) {
        model.addAttribute("form", new RegisztracioForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("form") RegisztracioForm form, BindingResult hibak) {
        // a két jelszónak egyeznie kell
        if (form.getPassword() != null && !form.getPassword().equals(form.getPassword2())) {
            hibak.rejectValue("password2", "egyezes", "A két jelszó nem egyezik");
        }
        // foglalt felhasználónév
        if (form.getUsername() != null && userRepository.findByUsername(form.getUsername()).isPresent()) {
            hibak.rejectValue("username", "foglalt", "Ez a felhasználónév már foglalt");
        }
        if (hibak.hasErrors()) {
            return "register";
        }
        // regisztrációval mindig "regisztrált látogató" (USER) jön létre
        User user = new User(form.getUsername(), passwordEncoder.encode(form.getPassword()), "USER");
        userRepository.save(user);
        return "redirect:/login?registered";
    }
}
