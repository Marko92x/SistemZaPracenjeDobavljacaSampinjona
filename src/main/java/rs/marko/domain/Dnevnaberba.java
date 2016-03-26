/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.marko.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "dnevnaberba")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dnevnaberba.findAll", query = "SELECT d FROM Dnevnaberba d"),
    @NamedQuery(name = "Dnevnaberba.findByJmbg", query = "SELECT d FROM Dnevnaberba d WHERE d.dnevnaberbaPK.jmbg = :jmbg"),
    @NamedQuery(name = "Dnevnaberba.findByDnevnaberbaid", query = "SELECT d FROM Dnevnaberba d WHERE d.dnevnaberbaPK.dnevnaberbaid = :dnevnaberbaid"),
    @NamedQuery(name = "Dnevnaberba.findByDatum", query = "SELECT d FROM Dnevnaberba d WHERE d.datum = :datum")})
public class Dnevnaberba implements Serializable, Validation {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DnevnaberbaPK dnevnaberbaPK;
    @Column(name = "datum")
    @Temporal(TemporalType.DATE)
    private Date datum;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dnevnaberba")
    private List<Stavkadnevneberbe> stavkadnevneberbeList;

    public Dnevnaberba() {
    }

    public Dnevnaberba(DnevnaberbaPK dnevnaberbaPK) {
        this.dnevnaberbaPK = dnevnaberbaPK;
    }

    public Dnevnaberba(String jmbg, int dnevnaberbaid) {
        this.dnevnaberbaPK = new DnevnaberbaPK(jmbg, dnevnaberbaid);
    }

    public DnevnaberbaPK getDnevnaberbaPK() {
        return dnevnaberbaPK;
    }

    public void setDnevnaberbaPK(DnevnaberbaPK dnevnaberbaPK) {
        this.dnevnaberbaPK = dnevnaberbaPK;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    @XmlTransient
    @JsonIgnore
    public List<Stavkadnevneberbe> getStavkadnevneberbeList() {
        return stavkadnevneberbeList;
    }

    public void setStavkadnevneberbeList(List<Stavkadnevneberbe> stavkadnevneberbeList) {
        this.stavkadnevneberbeList = stavkadnevneberbeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dnevnaberbaPK != null ? dnevnaberbaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dnevnaberba)) {
            return false;
        }
        Dnevnaberba other = (Dnevnaberba) object;
        if ((this.dnevnaberbaPK == null && other.dnevnaberbaPK != null) || (this.dnevnaberbaPK != null && !this.dnevnaberbaPK.equals(other.dnevnaberbaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.diplomski.mare.domain.Dnevnaberba[ dnevnaberbaPK=" + dnevnaberbaPK + " ]";
    }

    @XmlTransient
    @JsonIgnore
    @Override
    public boolean isValid() {
        if (dnevnaberbaPK.getJmbg() == null || datum == null){
            return false;
        }
        return true;
    }
    
}
