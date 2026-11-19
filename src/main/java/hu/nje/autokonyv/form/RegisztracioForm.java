package hu.nje.autokonyv.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisztracioForm {

    @NotBlank(message = "A felhasználónév megadása kötelező")
    @Size(min = 3, max = 30, message = "A felhasználónév 3 és 30 karakter között legyen")
    @Pattern(regexp = "[a-zA-Z0-9._]*", message = "Csak betű, szám, pont és aláhúzás használható")
    private String username;

    @NotBlank(message = "A jelszó megadása kötelező")
    @Size(min = 6, max = 64, message = "A jelszó legalább 6 karakter legyen")
    private String password;

    @NotBlank(message = "Írja be újra a jelszót")
    private String password2;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
}
