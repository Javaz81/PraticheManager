/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andrea
 */
@Entity
@Table(name = "materialepratica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Materialepratica.findAll", query = "SELECT m FROM Materialepratica m")
    , @NamedQuery(name = "Materialepratica.findByPratica", query = "SELECT m FROM Materialepratica m WHERE m.materialepraticaPK.pratica = :pratica")
    , @NamedQuery(name = "Materialepratica.findByArticolo", query = "SELECT m FROM Materialepratica m WHERE m.materialepraticaPK.articolo = :articolo")
    , @NamedQuery(name = "Materialepratica.findByQuantitaConsumata", query = "SELECT m FROM Materialepratica m WHERE m.quantitaConsumata = :quantitaConsumata")})
public class Materialepratica implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MaterialepraticaPK materialepraticaPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "quantita_consumata")
    private BigDecimal quantitaConsumata;
    @JoinColumn(name = "pratica", referencedColumnName = "idPratica", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Pratica pratica1;
    @JoinColumn(name = "articolo", referencedColumnName = "idArticolo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Articolo articolo1;

    public Materialepratica() {
    }

    public Materialepratica(MaterialepraticaPK materialepraticaPK) {
        this.materialepraticaPK = materialepraticaPK;
    }

    public Materialepratica(int pratica, int articolo) {
        this.materialepraticaPK = new MaterialepraticaPK(pratica, articolo);
    }

    public MaterialepraticaPK getMaterialepraticaPK() {
        return materialepraticaPK;
    }

    public void setMaterialepraticaPK(MaterialepraticaPK materialepraticaPK) {
        this.materialepraticaPK = materialepraticaPK;
    }

    public BigDecimal getQuantitaConsumata() {
        return quantitaConsumata;
    }

    public void setQuantitaConsumata(BigDecimal quantitaConsumata) {
        this.quantitaConsumata = quantitaConsumata;
    }

    public Pratica getPratica1() {
        return pratica1;
    }

    public void setPratica1(Pratica pratica1) {
        this.pratica1 = pratica1;
    }

    public Articolo getArticolo1() {
        return articolo1;
    }

    public void setArticolo1(Articolo articolo1) {
        this.articolo1 = articolo1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (materialepraticaPK != null ? materialepraticaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Materialepratica)) {
            return false;
        }
        Materialepratica other = (Materialepratica) object;
        if ((this.materialepraticaPK == null && other.materialepraticaPK != null) || (this.materialepraticaPK != null && !this.materialepraticaPK.equals(other.materialepraticaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.javasoft81.pratichemanager.entities.Materialepratica[ materialepraticaPK=" + materialepraticaPK + " ]";
    }
    
}
