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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andrea
 */
@Entity
@Table(name = "lavoripratichecustom")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lavoripratichecustom.findAll", query = "SELECT l FROM Lavoripratichecustom l")
    , @NamedQuery(name = "Lavoripratichecustom.findById", query = "SELECT l FROM Lavoripratichecustom l WHERE l.id = :id")
    , @NamedQuery(name = "Lavoripratichecustom.findByDescrizione", query = "SELECT l FROM Lavoripratichecustom l WHERE l.descrizione = :descrizione")
    , @NamedQuery(name = "Lavoripratichecustom.findByPratica", query = "SELECT l FROM Lavoripratichecustom l WHERE l.pratica = :pratica ORDER BY l.categoria ASC")})
public class Lavoripratichecustom implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "descrizione")
    private String descrizione;
    @JoinColumn(name = "categoria", referencedColumnName = "idCategoriaTipoLavoro")
    @ManyToOne(optional = false)
    private Categoriatipolavoro categoria;
    @JoinColumn(name = "pratica", referencedColumnName = "idPratica")
    @ManyToOne(optional = false)
    private Pratica pratica;

    public Lavoripratichecustom() {
    }

    public Lavoripratichecustom(Integer id) {
        this.id = id;
    }

    public Lavoripratichecustom(Integer id, String descrizione) {
        this.id = id;
        this.descrizione = descrizione;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Categoriatipolavoro getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoriatipolavoro categoria) {
        this.categoria = categoria;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lavoripratichecustom)) {
            return false;
        }
        Lavoripratichecustom other = (Lavoripratichecustom) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.javasoft81.pratichemanager.entities.Lavoripratichecustom[ id=" + id + " ]";
    }
    
}
