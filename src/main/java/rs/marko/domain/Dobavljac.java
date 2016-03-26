/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.marko.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "dobavljac")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dobavljac.findAll", query = "SELECT d FROM Dobavljac d"),
    @NamedQuery(name = "Dobavljac.findByJmbg", query = "SELECT d FROM Dobavljac d WHERE d.jmbg = :jmbg"),
    @NamedQuery(name = "Dobavljac.findByBrojgazdinstva", query = "SELECT d FROM Dobavljac d WHERE d.brojgazdinstva = :brojgazdinstva"),
    @NamedQuery(name = "Dobavljac.findByBrojlicnekarte", query = "SELECT d FROM Dobavljac d WHERE d.brojlicnekarte = :brojlicnekarte"),
    @NamedQuery(name = "Dobavljac.findByIme", query = "SELECT d FROM Dobavljac d WHERE d.ime = :ime"),
    @NamedQuery(name = "Dobavljac.findByPrezime", query = "SELECT d FROM Dobavljac d WHERE d.prezime = :prezime"),
    @NamedQuery(name = "Dobavljac.findByTekuciracun", query = "SELECT d FROM Dobavljac d WHERE d.tekuciracun = :tekuciracun"),
    @NamedQuery(name = "Dobavljac.findByUlica", query = "SELECT d FROM Dobavljac d WHERE d.ulica = :ulica"),
    @NamedQuery(name = "Dobavljac.findByBroj", query = "SELECT d FROM Dobavljac d WHERE d.broj = :broj")})
public class Dobavljac implements Serializable, Validation {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "jmbg")
    private String jmbg;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "brojgazdinstva")
    private String brojgazdinstva;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "brojlicnekarte")
    private String brojlicnekarte;
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
    @Column(name = "tekuciracun")
    private String tekuciracun;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ulica")
    private String ulica;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "broj")
    private String broj;
    @JoinColumn(name = "mesto", referencedColumnName = "ptt")
    @ManyToOne
    private Mesto mesto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dobavljac")
    private List<Zaduzenje> zaduzenjeList;

    public Dobavljac() {
    }

    public Dobavljac(String jmbg) {
        this.jmbg = jmbg;
    }

    public Dobavljac(String jmbg, String brojgazdinstva, String brojlicnekarte, String ime, String prezime, String tekuciracun, String ulica, String broj) {
        this.jmbg = jmbg;
        this.brojgazdinstva = brojgazdinstva;
        this.brojlicnekarte = brojlicnekarte;
        this.ime = ime;
        this.prezime = prezime;
        this.tekuciracun = tekuciracun;
        this.ulica = ulica;
        this.broj = broj;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getBrojgazdinstva() {
        return brojgazdinstva;
    }

    public void setBrojgazdinstva(String brojgazdinstva) {
        this.brojgazdinstva = brojgazdinstva;
    }

    public String getBrojlicnekarte() {
        return brojlicnekarte;
    }

    public void setBrojlicnekarte(String brojlicnekarte) {
        this.brojlicnekarte = brojlicnekarte;
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

    public String getTekuciracun() {
        return tekuciracun;
    }

    public void setTekuciracun(String tekuciracun) {
        this.tekuciracun = tekuciracun;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getBroj() {
        return broj;
    }

    public void setBroj(String broj) {
        this.broj = broj;
    }

    public Mesto getMesto() {
        return mesto;
    }

    public void setMesto(Mesto mesto) {
        this.mesto = mesto;
    }

    @XmlTransient
    @JsonIgnore
    public List<Zaduzenje> getZaduzenjeList() {
        return zaduzenjeList;
    }

    public void setZaduzenjeList(List<Zaduzenje> zaduzenjeList) {
        this.zaduzenjeList = zaduzenjeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jmbg != null ? jmbg.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dobavljac)) {
            return false;
        }
        Dobavljac other = (Dobavljac) object;
        if ((this.jmbg == null && other.jmbg != null) || (this.jmbg != null && !this.jmbg.equals(other.jmbg))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.diplomski.mare.domain.Dobavljac[ jmbg=" + jmbg + " ]";
    }

    @XmlTransient
    @JsonIgnore
    @Override
    public boolean isValid() {
        if (!jmbg.matches(Validation.JMBG_REGEX) ||
                !brojlicnekarte.matches(Validation.INT_REGEX) ||
                !brojgazdinstva.matches(Validation.INT_REGEX) ||
                !tekuciracun.matches(Validation.TEKUCI_RACUN_REGEX) ||
                ulica.equals("") ||
                broj.equals("") ||
                mesto == null ||
                ime.equals("") ||
                prezime.equals("")){
            return false;
        }
        return true;
    }
    
}
