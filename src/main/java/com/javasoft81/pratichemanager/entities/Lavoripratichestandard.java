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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "lavoripratichestandard")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lavoripratichestandard.findAll", query = "SELECT l FROM Lavoripratichestandard l")
    , @NamedQuery(name = "Lavoripratichestandard.findById", query = "SELECT l FROM Lavoripratichestandard l WHERE l.id = :id")})
public class Lavoripratichestandard implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "pratica", referencedColumnName = "idPratica")
    @ManyToOne(optional = false)
    private Pratica pratica;
    @JoinColumn(name = "tipolavoro", referencedColumnName = "idTipoLavoro")
    @ManyToOne(optional = false)
    private Tipolavoro tipolavoro;

    public Lavoripratichestandard() {
    }

    public Lavoripratichestandard(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pratica getPratica() {
        return pratica;
    }

    public void setPratica(Pratica pratica) {
        this.pratica = pratica;
    }

    public Tipolavoro getTipolavoro() {
        return tipolavoro;
    }

    public void setTipolavoro(Tipolavoro tipolavoro) {
        this.tipolavoro = tipolavoro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lavoripratichestandard)) {
            return false;
        }
        Lavoripratichestandard other = (Lavoripratichestandard) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.javasoft81.pratichemanager.entities.Lavoripratichestandard[ id=" + id + " ]";
    }
    
}
