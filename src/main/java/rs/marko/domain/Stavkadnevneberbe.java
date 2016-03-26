/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.marko.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author marko
 */
@Entity
@Table(name = "stavkadnevneberbe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stavkadnevneberbe.findAll", query = "SELECT s FROM Stavkadnevneberbe s"),
    @NamedQuery(name = "Stavkadnevneberbe.findByJmbg", query = "SELECT s FROM Stavkadnevneberbe s WHERE s.stavkadnevneberbePK.jmbg = :jmbg"),
    @NamedQuery(name = "Stavkadnevneberbe.findByDnevnaberbaid", query = "SELECT s FROM Stavkadnevneberbe s WHERE s.stavkadnevneberbePK.dnevnaberbaid = :dnevnaberbaid"),
    @NamedQuery(name = "Stavkadnevneberbe.findByStavkaid", query = "SELECT s FROM Stavkadnevneberbe s WHERE s.stavkadnevneberbePK.stavkaid = :stavkaid"),
    @NamedQuery(name = "Stavkadnevneberbe.findByTacne", query = "SELECT s FROM Stavkadnevneberbe s WHERE s.tacne = :tacne"),
    @NamedQuery(name = "Stavkadnevneberbe.findByPrvaklasa", query = "SELECT s FROM Stavkadnevneberbe s WHERE s.prvaklasa = :prvaklasa"),
    @NamedQuery(name = "Stavkadnevneberbe.findByDrugaklasa", query = "SELECT s FROM Stavkadnevneberbe s WHERE s.drugaklasa = :drugaklasa"),
    @NamedQuery(name = "Stavkadnevneberbe.findByTrecaklasa", query = "SELECT s FROM Stavkadnevneberbe s WHERE s.trecaklasa = :trecaklasa"),
    @NamedQuery(name = "Stavkadnevneberbe.findByCenatacne", query = "SELECT s FROM Stavkadnevneberbe s WHERE s.cenatacne = :cenatacne"),
    @NamedQuery(name = "Stavkadnevneberbe.findByCenaprvaklasa", query = "SELECT s FROM Stavkadnevneberbe s WHERE s.cenaprvaklasa = :cenaprvaklasa"),
    @NamedQuery(name = "Stavkadnevneberbe.findByCenadrugaklasa", query = "SELECT s FROM Stavkadnevneberbe s WHERE s.cenadrugaklasa = :cenadrugaklasa"),
    @NamedQuery(name = "Stavkadnevneberbe.findByCenatrecaklasa", query = "SELECT s FROM Stavkadnevneberbe s WHERE s.cenatrecaklasa = :cenatrecaklasa")})
public class Stavkadnevneberbe implements Serializable, Validation {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StavkadnevneberbePK stavkadnevneberbePK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tacne")
    private double tacne;
    @Column(name = "prvaklasa")
    private double prvaklasa;
    @Column(name = "drugaklasa")
    private double drugaklasa;
    @Column(name = "trecaklasa")
    private double trecaklasa;
    @Column(name = "cenatacne")
    private double cenatacne;
    @Column(name = "cenaprvaklasa")
    private double cenaprvaklasa;
    @Column(name = "cenadrugaklasa")
    private double cenadrugaklasa;
    @Column(name = "cenatrecaklasa")
    private double cenatrecaklasa;
    @JoinColumns({
        @JoinColumn(name = "jmbg", referencedColumnName = "jmbg", insertable = false, updatable = false),
        @JoinColumn(name = "dnevnaberbaid", referencedColumnName = "dnevnaberbaid", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Dnevnaberba dnevnaberba;

    public Stavkadnevneberbe() {
    }

    public Stavkadnevneberbe(StavkadnevneberbePK stavkadnevneberbePK) {
        this.stavkadnevneberbePK = stavkadnevneberbePK;
    }

    public Stavkadnevneberbe(String jmbg, int dnevnaberbaid, int stavkaid) {
        this.stavkadnevneberbePK = new StavkadnevneberbePK(jmbg, dnevnaberbaid, stavkaid);
    }

    public StavkadnevneberbePK getStavkadnevneberbePK() {
        return stavkadnevneberbePK;
    }

    public void setStavkadnevneberbePK(StavkadnevneberbePK stavkadnevneberbePK) {
        this.stavkadnevneberbePK = stavkadnevneberbePK;
    }

    public double getTacne() {
        return tacne;
    }

    public void setTacne(double tacne) {
        this.tacne = tacne;
    }

    public double getPrvaklasa() {
        return prvaklasa;
    }

    public void setPrvaklasa(double prvaklasa) {
        this.prvaklasa = prvaklasa;
    }

    public double getDrugaklasa() {
        return drugaklasa;
    }

    public void setDrugaklasa(double drugaklasa) {
        this.drugaklasa = drugaklasa;
    }

    public double getTrecaklasa() {
        return trecaklasa;
    }

    public void setTrecaklasa(double trecaklasa) {
        this.trecaklasa = trecaklasa;
    }

    public double getCenatacne() {
        return cenatacne;
    }

    public void setCenatacne(double cenatacne) {
        this.cenatacne = cenatacne;
    }

    public double getCenaprvaklasa() {
        return cenaprvaklasa;
    }

    public void setCenaprvaklasa(double cenaprvaklasa) {
        this.cenaprvaklasa = cenaprvaklasa;
    }

    public double getCenadrugaklasa() {
        return cenadrugaklasa;
    }

    public void setCenadrugaklasa(double cenadrugaklasa) {
        this.cenadrugaklasa = cenadrugaklasa;
    }

    public double getCenatrecaklasa() {
        return cenatrecaklasa;
    }

    public void setCenatrecaklasa(double cenatrecaklasa) {
        this.cenatrecaklasa = cenatrecaklasa;
    }

    public Dnevnaberba getDnevnaberba() {
        return dnevnaberba;
    }

    public void setDnevnaberba(Dnevnaberba dnevnaberba) {
        this.dnevnaberba = dnevnaberba;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stavkadnevneberbePK != null ? stavkadnevneberbePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stavkadnevneberbe)) {
            return false;
        }
        Stavkadnevneberbe other = (Stavkadnevneberbe) object;
        if ((this.stavkadnevneberbePK == null && other.stavkadnevneberbePK != null) || (this.stavkadnevneberbePK != null && !this.stavkadnevneberbePK.equals(other.stavkadnevneberbePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.diplomski.mare.domain.Stavkadnevneberbe[ stavkadnevneberbePK=" + stavkadnevneberbePK + " ]";
    }

    @XmlTransient
    @JsonIgnore
    @Override
    public boolean isValid() {
        if (stavkadnevneberbePK.getJmbg() == null || dnevnaberba == null
                || tacne < 0 || prvaklasa < 0 || drugaklasa < 0 || trecaklasa < 0
                || cenatacne < 0 || cenaprvaklasa < 0 || cenadrugaklasa < 0 || cenatrecaklasa < 0) {
            return false;
        }
        if (tacne == 0 && prvaklasa == 0 && drugaklasa == 0 && trecaklasa == 0
                && cenatacne == 0 && cenaprvaklasa == 0 && cenadrugaklasa == 0 && cenatrecaklasa == 0) {
            return false;
        }
        if (((tacne > 0 && cenatacne == 0) || (tacne == 0 && cenatacne > 0))
                || ((prvaklasa > 0 && cenaprvaklasa == 0) || (prvaklasa == 0 && cenaprvaklasa > 0))
                || ((drugaklasa > 0 && cenadrugaklasa == 0) || (drugaklasa == 0 && cenadrugaklasa > 0))
                || ((trecaklasa > 0 && cenatrecaklasa == 0) || (trecaklasa == 0 && cenatrecaklasa > 0))) {
            return false;
        }
        return true;
    }

}
