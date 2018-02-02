/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author andrea
 */
@Embeddable
public class LavoropraticaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "pratica")
    private int pratica;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipolavoro")
    private int tipolavoro;

    public LavoropraticaPK() {
    }

    public LavoropraticaPK(int pratica, int tipolavoro) {
        this.pratica = pratica;
        this.tipolavoro = tipolavoro;
    }

    public int getPratica() {
        return pratica;
    }

    public void setPratica(int pratica) {
        this.pratica = pratica;
    }

    public int getTipolavoro() {
        return tipolavoro;
    }

    public void setTipolavoro(int tipolavoro) {
        this.tipolavoro = tipolavoro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) pratica;
        hash += (int) tipolavoro;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LavoropraticaPK)) {
            return false;
        }
        LavoropraticaPK other = (LavoropraticaPK) object;
        if (this.pratica != other.pratica) {
            return false;
        }
        if (this.tipolavoro != other.tipolavoro) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.javasoft81.pratichemanager.entities.LavoropraticaPK[ pratica=" + pratica + ", tipolavoro=" + tipolavoro + " ]";
    }
    
}
