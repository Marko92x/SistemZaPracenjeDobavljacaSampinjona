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
public class ZaduzenjePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "jmbg")
    private String jmbg;
    @Basic(optional = false)
    @Column(name = "zaduzenjeid")
    private int zaduzenjeid;

    public ZaduzenjePK() {
    }

    public ZaduzenjePK(String jmbg, int zaduzenjeid) {
        this.jmbg = jmbg;
        this.zaduzenjeid = zaduzenjeid;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public int getZaduzenjeid() {
        return zaduzenjeid;
    }

    public void setZaduzenjeid(int zaduzenjeid) {
        this.zaduzenjeid = zaduzenjeid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jmbg != null ? jmbg.hashCode() : 0);
        hash += (int) zaduzenjeid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ZaduzenjePK)) {
            return false;
        }
        ZaduzenjePK other = (ZaduzenjePK) object;
        if ((this.jmbg == null && other.jmbg != null) || (this.jmbg != null && !this.jmbg.equals(other.jmbg))) {
            return false;
        }
        if (this.zaduzenjeid != other.zaduzenjeid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.diplomski.mare.domain.ZaduzenjePK[ jmbg=" + jmbg + ", zaduzenjeid=" + zaduzenjeid + " ]";
    }
    
}
