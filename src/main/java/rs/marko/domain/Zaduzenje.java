/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.marko.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author marko
 */
@Entity
@Table(name = "zaduzenje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zaduzenje.findAll", query = "SELECT z FROM Zaduzenje z"),
    @NamedQuery(name = "Zaduzenje.findByJmbg", query = "SELECT z FROM Zaduzenje z WHERE z.zaduzenjePK.jmbg = :jmbg"),
    @NamedQuery(name = "Zaduzenje.findByZaduzenjeid", query = "SELECT z FROM Zaduzenje z WHERE z.zaduzenjePK.zaduzenjeid = :zaduzenjeid"),
    @NamedQuery(name = "Zaduzenje.findByDatumzaduzenja", query = "SELECT z FROM Zaduzenje z WHERE z.datumzaduzenja = :datumzaduzenja"),
    @NamedQuery(name = "Zaduzenje.findByDatumrazduzenja", query = "SELECT z FROM Zaduzenje z WHERE z.datumrazduzenja = :datumrazduzenja"),
    @NamedQuery(name = "Zaduzenje.findByKompost", query = "SELECT z FROM Zaduzenje z WHERE z.kompost = :kompost"),
    @NamedQuery(name = "Zaduzenje.findByPrevoz", query = "SELECT z FROM Zaduzenje z WHERE z.prevoz = :prevoz"),
    @NamedQuery(name = "Zaduzenje.findByBrojvreca", query = "SELECT z FROM Zaduzenje z WHERE z.brojvreca = :brojvreca")})
public class Zaduzenje implements Serializable, Validation {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ZaduzenjePK zaduzenjePK;
    @Column(name = "datumzaduzenja")
    @Temporal(TemporalType.DATE)
    private Date datumzaduzenja;
    @Column(name = "datumrazduzenja")
    @Temporal(TemporalType.DATE)
    private Date datumrazduzenja;
    @Column(name = "kompost")
    private boolean kompost;
    @Column(name = "prevoz")
    private boolean prevoz;
    @Column(name = "brojvreca")
    private int brojvreca;
    @JoinColumn(name = "zaduzio", referencedColumnName = "administrativniradnikid")
    @ManyToOne
    private Administrativniradnik zaduzio;
    @JoinColumn(name = "razduzio", referencedColumnName = "administrativniradnikid")
    @ManyToOne
    private Administrativniradnik razduzio;
    @JoinColumn(name = "jmbg", referencedColumnName = "jmbg", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Dobavljac dobavljac;

    public Zaduzenje() {
    }

    public Zaduzenje(ZaduzenjePK zaduzenjePK) {
        this.zaduzenjePK = zaduzenjePK;
    }

    public Zaduzenje(String jmbg, int zaduzenjeid) {
        this.zaduzenjePK = new ZaduzenjePK(jmbg, zaduzenjeid);
    }

    public ZaduzenjePK getZaduzenjePK() {
        return zaduzenjePK;
    }

    public void setZaduzenjePK(ZaduzenjePK zaduzenjePK) {
        this.zaduzenjePK = zaduzenjePK;
    }

    public Date getDatumzaduzenja() {
        return datumzaduzenja;
    }

    public void setDatumzaduzenja(Date datumzaduzenja) {
        this.datumzaduzenja = datumzaduzenja;
    }

    public Date getDatumrazduzenja() {
        return datumrazduzenja;
    }

    public void setDatumrazduzenja(Date datumrazduzenja) {
        this.datumrazduzenja = datumrazduzenja;
    }

    public boolean getKompost() {
        return kompost;
    }

    public void setKompost(boolean kompost) {
        this.kompost = kompost;
    }

    public boolean getPrevoz() {
        return prevoz;
    }

    public void setPrevoz(boolean prevoz) {
        this.prevoz = prevoz;
    }

    public int getBrojvreca() {
        return brojvreca;
    }

    public void setBrojvreca(int brojvreca) {
        this.brojvreca = brojvreca;
    }

    public Administrativniradnik getZaduzio() {
        return zaduzio;
    }

    public void setZaduzio(Administrativniradnik zaduzio) {
        this.zaduzio = zaduzio;
    }

    public Administrativniradnik getRazduzio() {
        return razduzio;
    }

    public void setRazduzio(Administrativniradnik razduzio) {
        this.razduzio = razduzio;
    }

    public Dobavljac getDobavljac() {
        return dobavljac;
    }

    public void setDobavljac(Dobavljac dobavljac) {
        this.dobavljac = dobavljac;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (zaduzenjePK != null ? zaduzenjePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zaduzenje)) {
            return false;
        }
        Zaduzenje other = (Zaduzenje) object;
        if ((this.zaduzenjePK == null && other.zaduzenjePK != null) || (this.zaduzenjePK != null && !this.zaduzenjePK.equals(other.zaduzenjePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.diplomski.mare.domain.Zaduzenje[ zaduzenjePK=" + zaduzenjePK + " ]";
    }

    @XmlTransient
    @JsonIgnore
    @Override
    public boolean isValid() {
        if (zaduzio != null) {
            if (dobavljac.getJmbg() == null || datumzaduzenja == null || brojvreca < 0 || (!kompost && !prevoz)) {
                return false;
            }
            if (kompost && brojvreca < 1) {
                return false;
            }
            if (razduzio != null){
                return false;
            }
            return true;
        } else if (razduzio != null) {
            if (datumrazduzenja == null) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

}
