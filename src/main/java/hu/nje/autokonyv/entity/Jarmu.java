package hu.nje.autokonyv.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "jarmu")
public class Jarmu {

    @Id
    @Column(name = "rndszm", length = 6)
    @NotBlank(message = "A rendszám megadása kötelező")
    @Pattern(regexp = "[A-Z]{3}[0-9]{3}", message = "A rendszám formátuma: ABC123")
    private String rndszm;

    @Column(name = "marka", length = 20)
    @NotBlank(message = "A márka megadása kötelező")
    @Size(max = 20, message = "A márka legfeljebb 20 karakter lehet")
    private String marka;

    @Column(name = "szin", length = 20)
    @NotBlank(message = "A szín megadása kötelező")
    @Size(max = 20, message = "A szín legfeljebb 20 karakter lehet")
    private String szin;

    public Jarmu() {
    }

    public Jarmu(String rndszm, String marka, String szin) {
        this.rndszm = rndszm;
        this.marka = marka;
        this.szin = szin;
    }

    public String getRndszm() {
        return rndszm;
    }

    public void setRndszm(String rndszm) {
        this.rndszm = rndszm;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getSzin() {
        return szin;
    }

    public void setSzin(String szin) {
        this.szin = szin;
    }
}
