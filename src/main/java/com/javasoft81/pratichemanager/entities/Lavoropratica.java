/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andrea
 */
@Entity
@Table(name = "lavoropratica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lavoropratica.findAll", query = "SELECT l FROM Lavoropratica l")
    , @NamedQuery(name = "Lavoropratica.findByPratica", query = "SELECT l FROM Lavoropratica l WHERE l.lavoropraticaPK.pratica = :pratica")
    , @NamedQuery(name = "Lavoropratica.findByTipolavoro", query = "SELECT l FROM Lavoropratica l WHERE l.lavoropraticaPK.tipolavoro = :tipolavoro")
    , @NamedQuery(name = "Lavoropratica.findByDescrizioneAltro", query = "SELECT l FROM Lavoropratica l WHERE l.descrizioneAltro = :descrizioneAltro")})
public class Lavoropratica implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LavoropraticaPK lavoropraticaPK;
    @Size(max = 255)
    @Column(name = "descrizione_altro")
    private String descrizioneAltro;

    public Lavoropratica() {
    }

    public Lavoropratica(LavoropraticaPK lavoropraticaPK) {
        this.lavoropraticaPK = lavoropraticaPK;
    }

    public Lavoropratica(int pratica, int tipolavoro) {
        this.lavoropraticaPK = new LavoropraticaPK(pratica, tipolavoro);
    }

    public LavoropraticaPK getLavoropraticaPK() {
        return lavoropraticaPK;
    }

    public void setLavoropraticaPK(LavoropraticaPK lavoropraticaPK) {
        this.lavoropraticaPK = lavoropraticaPK;
    }

    public String getDescrizioneAltro() {
        return descrizioneAltro;
    }

    public void setDescrizioneAltro(String descrizioneAltro) {
        this.descrizioneAltro = descrizioneAltro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lavoropraticaPK != null ? lavoropraticaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lavoropratica)) {
            return false;
        }
        Lavoropratica other = (Lavoropratica) object;
        if ((this.lavoropraticaPK == null && other.lavoropraticaPK != null) || (this.lavoropraticaPK != null && !this.lavoropraticaPK.equals(other.lavoropraticaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.javasoft81.pratichemanager.entities.Lavoropratica[ lavoropraticaPK=" + lavoropraticaPK + " ]";
    }
    
}
