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
public class MaterialepraticaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "pratica")
    private int pratica;
    @Basic(optional = false)
    @NotNull
    @Column(name = "articolo")
    private int articolo;

    public MaterialepraticaPK() {
    }

    public MaterialepraticaPK(int pratica, int articolo) {
        this.pratica = pratica;
        this.articolo = articolo;
    }

    public int getPratica() {
        return pratica;
    }

    public void setPratica(int pratica) {
        this.pratica = pratica;
    }

    public int getArticolo() {
        return articolo;
    }

    public void setArticolo(int articolo) {
        this.articolo = articolo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) pratica;
        hash += (int) articolo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MaterialepraticaPK)) {
            return false;
        }
        MaterialepraticaPK other = (MaterialepraticaPK) object;
        if (this.pratica != other.pratica) {
            return false;
        }
        if (this.articolo != other.articolo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.javasoft81.pratichemanager.entities.MaterialepraticaPK[ pratica=" + pratica + ", articolo=" + articolo + " ]";
    }
    
}
