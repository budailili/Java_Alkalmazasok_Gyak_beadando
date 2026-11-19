package hu.nje.autokonyv.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "telefon")
public class Telefon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "szemelyid")
    private Integer szemelyid;

    @Column(name = "szam", length = 20)
    private String szam;

    @ManyToOne
    @JoinColumn(name = "szemelyid", insertable = false, updatable = false)
    private Szemely szemely;

    public Telefon() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSzemelyid() {
        return szemelyid;
    }

    public void setSzemelyid(Integer szemelyid) {
        this.szemelyid = szemelyid;
    }

    public String getSzam() {
        return szam;
    }

    public void setSzam(String szam) {
        this.szam = szam;
    }

    public Szemely getSzemely() {
        return szemely;
    }

    public void setSzemely(Szemely szemely) {
        this.szemely = szemely;
    }
}
