package hu.nje.autokonyv.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "uzenet")
public class Uzenet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Kérjük, adja meg a nevét")
    @Size(min = 3, max = 50, message = "A név 3 és 50 karakter között legyen")
    private String nev;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Kérjük, adja meg az e-mail címét")
    @Email(message = "Az e-mail cím formátuma nem megfelelő")
    @Size(max = 100, message = "Az e-mail cím túl hosszú")
    private String email;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "A tárgy megadása kötelező")
    @Size(min = 3, max = 100, message = "A tárgy 3 és 100 karakter között legyen")
    private String targy;

    @Column(nullable = false, length = 2000)
    @NotBlank(message = "Az üzenet nem lehet üres")
    @Size(min = 10, max = 2000, message = "Az üzenet 10 és 2000 karakter között legyen")
    private String szoveg;

    // ezeket nem az űrlapból kapjuk, hanem a szerver tölti ki
    @Column(name = "kuldes_ideje", nullable = false)
    private LocalDateTime kuldesIdeje;

    @Column(length = 50)
    private String kuldo;

    public Uzenet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTargy() {
        return targy;
    }

    public void setTargy(String targy) {
        this.targy = targy;
    }

    public String getSzoveg() {
        return szoveg;
    }

    public void setSzoveg(String szoveg) {
        this.szoveg = szoveg;
    }

    public LocalDateTime getKuldesIdeje() {
        return kuldesIdeje;
    }

    public void setKuldesIdeje(LocalDateTime kuldesIdeje) {
        this.kuldesIdeje = kuldesIdeje;
    }

    public String getKuldo() {
        return kuldo;
    }

    public void setKuldo(String kuldo) {
        this.kuldo = kuldo;
    }
}
