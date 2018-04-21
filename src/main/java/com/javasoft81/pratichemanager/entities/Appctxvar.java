/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andrea
 */
@Entity
@Table(name = "appctxvar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Appctxvar.findAll", query = "SELECT a FROM Appctxvar a")
    , @NamedQuery(name = "Appctxvar.findByIdappvars", query = "SELECT a FROM Appctxvar a WHERE a.idappvars = :idappvars")
    , @NamedQuery(name = "Appctxvar.findByTargaCounter", query = "SELECT a FROM Appctxvar a WHERE a.targaCounter = :targaCounter")
    , @NamedQuery(name = "Appctxvar.findByCodiceCounter", query = "SELECT a FROM Appctxvar a WHERE a.codiceCounter = :codiceCounter")})
public class Appctxvar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idappvars")
    private Integer idappvars;
    @Basic(optional = false)
    @NotNull
    @Column(name = "targaCounter")
    private int targaCounter;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiceCounter")
    private int codiceCounter;

    public Appctxvar() {
    }

    public Appctxvar(Integer idappvars) {
        this.idappvars = idappvars;
    }

    public Appctxvar(Integer idappvars, int targaCounter, int codiceCounter) {
        this.idappvars = idappvars;
        this.targaCounter = targaCounter;
        this.codiceCounter = codiceCounter;
    }

    public Integer getIdappvars() {
        return idappvars;
    }

    public void setIdappvars(Integer idappvars) {
        this.idappvars = idappvars;
    }

    public int getTargaCounter() {
        return targaCounter;
    }

    public void setTargaCounter(int targaCounter) {
        this.targaCounter = targaCounter;
    }

    public int getCodiceCounter() {
        return codiceCounter;
    }

    public void setCodiceCounter(int codiceCounter) {
        this.codiceCounter = codiceCounter;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idappvars != null ? idappvars.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Appctxvar)) {
            return false;
        }
        Appctxvar other = (Appctxvar) object;
        if ((this.idappvars == null && other.idappvars != null) || (this.idappvars != null && !this.idappvars.equals(other.idappvars))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.javasoft81.pratichemanager.entities.Appctxvar[ idappvars=" + idappvars + " ]";
    }
    
}
