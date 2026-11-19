package hu.nje.autokonyv.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "szemely")
public class Szemely {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nev", length = 50)
    @NotBlank(message = "A név megadása kötelező")
    @Size(min = 3, max = 50, message = "A név 3 és 50 karakter között legyen")
    private String nev;

    @Column(name = "rendszam", length = 6)
    @NotBlank(message = "Válasszon járművet")
    private String rendszam;

    @Column(name = "magassag")
    @NotNull(message = "A magasság megadása kötelező")
    @Min(value = 100, message = "A magasság legalább 100 cm")
    @Max(value = 230, message = "A magasság legfeljebb 230 cm")
    private Integer magassag;

    // a személyhez tartozó jármű (csak olvasásra, a rendszam mezőn keresztül kötjük)
    @ManyToOne
    @JoinColumn(name = "rendszam", insertable = false, updatable = false)
    private Jarmu jarmu;

    // a személy telefonszámai (a telefon tábla szemelyid oszlopa alapján)
    @OneToMany(mappedBy = "szemely")
    private List<Telefon> telefonok;

    public Szemely() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getRendszam() {
        return rendszam;
    }

    public void setRendszam(String rendszam) {
        this.rendszam = rendszam;
    }

    public Integer getMagassag() {
        return magassag;
    }

    public void setMagassag(Integer magassag) {
        this.magassag = magassag;
    }

    public Jarmu getJarmu() {
        return jarmu;
    }

    public void setJarmu(Jarmu jarmu) {
        this.jarmu = jarmu;
    }

    public List<Telefon> getTelefonok() {
        return telefonok;
    }

    public void setTelefonok(List<Telefon> telefonok) {
        this.telefonok = telefonok;
    }
}
