/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.marko.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author marko
 */
@Embeddable
public class DnevnaberbaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "jmbg")
    private String jmbg;
    @Basic(optional = false)
    @Column(name = "dnevnaberbaid")
    private int dnevnaberbaid;

    public DnevnaberbaPK() {
    }

    public DnevnaberbaPK(String jmbg, int dnevnaberbaid) {
        this.jmbg = jmbg;
        this.dnevnaberbaid = dnevnaberbaid;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public int getDnevnaberbaid() {
        return dnevnaberbaid;
    }

    public void setDnevnaberbaid(int dnevnaberbaid) {
        this.dnevnaberbaid = dnevnaberbaid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jmbg != null ? jmbg.hashCode() : 0);
        hash += (int) dnevnaberbaid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DnevnaberbaPK)) {
            return false;
        }
        DnevnaberbaPK other = (DnevnaberbaPK) object;
        if ((this.jmbg == null && other.jmbg != null) || (this.jmbg != null && !this.jmbg.equals(other.jmbg))) {
            return false;
        }
        if (this.dnevnaberbaid != other.dnevnaberbaid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.diplomski.mare.domain.DnevnaberbaPK[ jmbg=" + jmbg + ", dnevnaberbaid=" + dnevnaberbaid + " ]";
    }
    
}
