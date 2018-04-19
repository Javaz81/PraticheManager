/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c")
    , @NamedQuery(name = "Cliente.findByIdCliente", query = "SELECT c FROM Cliente c WHERE c.idCliente = :idCliente")
    , @NamedQuery(name = "Cliente.findByNome", query = "SELECT c FROM Cliente c WHERE c.nome = :nome")
    , @NamedQuery(name = "Cliente.findByCognome", query = "SELECT c FROM Cliente c WHERE c.cognome = :cognome")
    , @NamedQuery(name = "Cliente.findByCellulare", query = "SELECT c FROM Cliente c WHERE c.cellulare = :cellulare")
    , @NamedQuery(name = "Cliente.findByLocalita", query = "SELECT c FROM Cliente c WHERE c.localita = :localita")
    , @NamedQuery(name = "Cliente.findByCliente", query = "SELECT c FROM Cliente c WHERE c.nome = :nome AND c.cognome = :cognome AND c.localita = :localita AND c.cellulare = :cellulare ORDER BY c.idCliente DESC")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCliente")
    private Integer idCliente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nome")
    private String nome;
    @Size(max = 45)
    @Column(name = "cognome")
    private String cognome;
    @Size(max = 45)
    @Column(name = "cellulare")
    private String cellulare;
    @Size(max = 45)
    @Column(name = "localita")
    private String localita;
    @OneToMany(mappedBy = "cliente")
    private Collection<Veicolo> veicoloCollection;
    @OneToMany(mappedBy = "clienteidCliente")
    private Collection<Pratica> praticaCollection;

    public Cliente() {
    }

    public Cliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Cliente(Integer idCliente, String nome) {
        this.idCliente = idCliente;
        this.nome = nome;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public String getLocalita() {
        return localita;
    }

    public void setLocalita(String localita) {
        this.localita = localita;
    }

    @XmlTransient
    public Collection<Veicolo> getVeicoloCollection() {
        return veicoloCollection;
    }

    public void setVeicoloCollection(Collection<Veicolo> veicoloCollection) {
        this.veicoloCollection = veicoloCollection;
    }

    @XmlTransient
    public Collection<Pratica> getPraticaCollection() {
        return praticaCollection;
    }

    public void setPraticaCollection(Collection<Pratica> praticaCollection) {
        this.praticaCollection = praticaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.javasoft81.pratichemanager.entities.Cliente[ idCliente=" + idCliente + " ]";
    }
    
}
