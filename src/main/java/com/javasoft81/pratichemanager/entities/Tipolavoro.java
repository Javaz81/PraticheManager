/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author andrea
 */
@Entity
@Table(name = "tipolavoro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipolavoro.findAll", query = "SELECT t FROM Tipolavoro t")
    , @NamedQuery(name = "Tipolavoro.findByIdTipoLavoro", query = "SELECT t FROM Tipolavoro t WHERE t.idTipoLavoro = :idTipoLavoro")
    , @NamedQuery(name = "Tipolavoro.findByCodice", query = "SELECT t FROM Tipolavoro t WHERE t.codice = :codice")
    , @NamedQuery(name = "Tipolavoro.findByDescrizione", query = "SELECT t FROM Tipolavoro t WHERE t.descrizione = :descrizione")
    , @NamedQuery(name = "Tipolavoro.findByCategoria", query ="SELECT t FROM Tipolavoro t WHERE t.categoria = :categoria")})
public class Tipolavoro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTipoLavoro")
    private Integer idTipoLavoro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "codice")
    private String codice;
    @Size(max = 120)
    @Column(name = "descrizione")
    private String descrizione;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipolavoro")
    private Collection<Lavoripratichestandard> lavoripratichestandardCollection;
    @JoinColumn(name = "categoria", referencedColumnName = "idCategoriaTipoLavoro")
    @ManyToOne(optional = false)
    private Categoriatipolavoro categoria;

    public Tipolavoro() {
    }

    public Tipolavoro(Integer idTipoLavoro) {
        this.idTipoLavoro = idTipoLavoro;
    }

    public Tipolavoro(Integer idTipoLavoro, String codice) {
        this.idTipoLavoro = idTipoLavoro;
        this.codice = codice;
    }

    public Integer getIdTipoLavoro() {
        return idTipoLavoro;
    }

    public void setIdTipoLavoro(Integer idTipoLavoro) {
        this.idTipoLavoro = idTipoLavoro;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @XmlTransient
    public Collection<Lavoripratichestandard> getLavoripratichestandardCollection() {
        return lavoripratichestandardCollection;
    }

    public void setLavoripratichestandardCollection(Collection<Lavoripratichestandard> lavoripratichestandardCollection) {
        this.lavoripratichestandardCollection = lavoripratichestandardCollection;
    }

    public Categoriatipolavoro getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoriatipolavoro categoria) {
        this.categoria = categoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoLavoro != null ? idTipoLavoro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipolavoro)) {
            return false;
        }
        Tipolavoro other = (Tipolavoro) object;
        if ((this.idTipoLavoro == null && other.idTipoLavoro != null) || (this.idTipoLavoro != null && !this.idTipoLavoro.equals(other.idTipoLavoro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.javasoft81.pratichemanager.entities.Tipolavoro[ idTipoLavoro=" + idTipoLavoro + " ]";
    }
    
}
