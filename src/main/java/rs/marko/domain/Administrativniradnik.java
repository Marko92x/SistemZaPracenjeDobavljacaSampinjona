/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.marko.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author marko
 */
@Entity
@Table(name = "administrativniradnik")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Administrativniradnik.findAll", query = "SELECT a FROM Administrativniradnik a"),
    @NamedQuery(name = "Administrativniradnik.findByAdministrativniradnikid", query = "SELECT a FROM Administrativniradnik a WHERE a.administrativniradnikid = :administrativniradnikid"),
    @NamedQuery(name = "Administrativniradnik.findByIme", query = "SELECT a FROM Administrativniradnik a WHERE a.ime = :ime"),
    @NamedQuery(name = "Administrativniradnik.findByPrezime", query = "SELECT a FROM Administrativniradnik a WHERE a.prezime = :prezime"),
    @NamedQuery(name = "Administrativniradnik.findByKorisnickoime", query = "SELECT a FROM Administrativniradnik a WHERE a.korisnickoime = :korisnickoime"),
    @NamedQuery(name = "Administrativniradnik.findByKorisnickasifra", query = "SELECT a FROM Administrativniradnik a WHERE a.korisnickasifra = :korisnickasifra"),
    @NamedQuery(name = "Administrativniradnik.findByToken", query = "SELECT a FROM Administrativniradnik a WHERE a.token = :token")})
public class Administrativniradnik implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "administrativniradnikid")
    private Integer administrativniradnikid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ime")
    private String ime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "prezime")
    private String prezime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "korisnickoime")
    private String korisnickoime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "korisnickasifra")
    private String korisnickasifra;
    @Size(max = 255)
    @Column(name = "token")
    private String token;
    @OneToMany(mappedBy = "zaduzio")
    private List<Zaduzenje> zaduzenjeList;
    @OneToMany(mappedBy = "razduzio")
    private List<Zaduzenje> zaduzenjeList1;

    public Administrativniradnik() {
    }

    public Administrativniradnik(Integer administrativniradnikid) {
        this.administrativniradnikid = administrativniradnikid;
    }

    public Administrativniradnik(Integer administrativniradnikid, String ime, String prezime, String korisnickoime, String korisnickasifra) {
        this.administrativniradnikid = administrativniradnikid;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoime = korisnickoime;
        this.korisnickasifra = korisnickasifra;
    }

    public Integer getAdministrativniradnikid() {
        return administrativniradnikid;
    }

    public void setAdministrativniradnikid(Integer administrativniradnikid) {
        this.administrativniradnikid = administrativniradnikid;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getKorisnickoime() {
        return korisnickoime;
    }

    public void setKorisnickoime(String korisnickoime) {
        this.korisnickoime = korisnickoime;
    }

    public String getKorisnickasifra() {
        return korisnickasifra;
    }

    public void setKorisnickasifra(String korisnickasifra) {
        this.korisnickasifra = korisnickasifra;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @XmlTransient
    @JsonIgnore
    public List<Zaduzenje> getZaduzenjeList() {
        return zaduzenjeList;
    }

    public void setZaduzenjeList(List<Zaduzenje> zaduzenjeList) {
        this.zaduzenjeList = zaduzenjeList;
    }

    @XmlTransient
    @JsonIgnore
    public List<Zaduzenje> getZaduzenjeList1() {
        return zaduzenjeList1;
    }

    public void setZaduzenjeList1(List<Zaduzenje> zaduzenjeList1) {
        this.zaduzenjeList1 = zaduzenjeList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (administrativniradnikid != null ? administrativniradnikid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Administrativniradnik)) {
            return false;
        }
        Administrativniradnik other = (Administrativniradnik) object;
        if ((this.administrativniradnikid == null && other.administrativniradnikid != null) || (this.administrativniradnikid != null && !this.administrativniradnikid.equals(other.administrativniradnikid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.diplomski.mare.domain.Administrativniradnik[ administrativniradnikid=" + administrativniradnikid + " ]";
    }
    
}
