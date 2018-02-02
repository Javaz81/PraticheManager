/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andrea
 */
@Entity
@Table(name = "orelavorate")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orelavorate.findAll", query = "SELECT o FROM Orelavorate o")
    , @NamedQuery(name = "Orelavorate.findByIdOreLavorate", query = "SELECT o FROM Orelavorate o WHERE o.idOreLavorate = :idOreLavorate")
    , @NamedQuery(name = "Orelavorate.findByOre", query = "SELECT o FROM Orelavorate o WHERE o.ore = :ore")
    , @NamedQuery(name = "Orelavorate.findByGiornata", query = "SELECT o FROM Orelavorate o WHERE o.giornata = :giornata")})
public class Orelavorate implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idOreLavorate")
    private Integer idOreLavorate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ore")
    private BigDecimal ore;
    @Basic(optional = false)
    @NotNull
    @Column(name = "giornata")
    @Temporal(TemporalType.DATE)
    private Date giornata;
    @JoinColumn(name = "personale", referencedColumnName = "idPersonale")
    @ManyToOne(optional = false)
    private Personale personale;
    @JoinColumn(name = "pratica", referencedColumnName = "idPratica")
    @ManyToOne(optional = false)
    private Pratica pratica;

    public Orelavorate() {
    }

    public Orelavorate(Integer idOreLavorate) {
        this.idOreLavorate = idOreLavorate;
    }

    public Orelavorate(Integer idOreLavorate, Date giornata) {
        this.idOreLavorate = idOreLavorate;
        this.giornata = giornata;
    }

    public Integer getIdOreLavorate() {
        return idOreLavorate;
    }

    public void setIdOreLavorate(Integer idOreLavorate) {
        this.idOreLavorate = idOreLavorate;
    }

    public BigDecimal getOre() {
        return ore;
    }

    public void setOre(BigDecimal ore) {
        this.ore = ore;
    }

    public Date getGiornata() {
        return giornata;
    }

    public void setGiornata(Date giornata) {
        this.giornata = giornata;
    }

    public Personale getPersonale() {
        return personale;
    }

    public void setPersonale(Personale personale) {
        this.personale = personale;
    }

    public Pratica getPratica() {
        return pratica;
    }

    public void setPratica(Pratica pratica) {
        this.pratica = pratica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOreLavorate != null ? idOreLavorate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orelavorate)) {
            return false;
        }
        Orelavorate other = (Orelavorate) object;
        if ((this.idOreLavorate == null && other.idOreLavorate != null) || (this.idOreLavorate != null && !this.idOreLavorate.equals(other.idOreLavorate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.javasoft81.pratichemanager.entities.Orelavorate[ idOreLavorate=" + idOreLavorate + " ]";
    }
    
}
