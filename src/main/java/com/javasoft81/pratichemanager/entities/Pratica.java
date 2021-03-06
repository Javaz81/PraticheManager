/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author andrea
 */
@Entity
@Table(name = "pratica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pratica.findAll", query = "SELECT p FROM Pratica p")
    , @NamedQuery(name = "Pratica.findByIdPratica", query = "SELECT p FROM Pratica p WHERE p.idPratica = :idPratica")
    , @NamedQuery(name = "Pratica.findByArrivo", query = "SELECT p FROM Pratica p WHERE p.arrivo = :arrivo")
    , @NamedQuery(name = "Pratica.findByDataArrivo", query = "SELECT p FROM Pratica p WHERE p.dataArrivo = :dataArrivo")
    , @NamedQuery(name = "Pratica.findByUscita", query = "SELECT p FROM Pratica p WHERE p.uscita = :uscita")
    , @NamedQuery(name = "Pratica.findByDataUscita", query = "SELECT p FROM Pratica p WHERE p.dataUscita = :dataUscita")
    , @NamedQuery(name = "Pratica.findByNumeroFattura", query = "SELECT p FROM Pratica p WHERE p.numeroFattura = :numeroFattura")
    , @NamedQuery(name = "Pratica.findByDataFattura", query = "SELECT p FROM Pratica p WHERE p.dataFattura = :dataFattura")
    , @NamedQuery(name = "Pratica.findByPreventivoLavori", query = "SELECT p FROM Pratica p WHERE p.preventivoLavori = :preventivoLavori")
    , @NamedQuery(name = "Pratica.findByRevisioneMctc", query = "SELECT p FROM Pratica p WHERE p.revisioneMctc = :revisioneMctc")
    , @NamedQuery(name = "Pratica.findByCollaudoUsl", query = "SELECT p FROM Pratica p WHERE p.collaudoUsl = :collaudoUsl")
    , @NamedQuery(name = "Pratica.findByRegistroDiControllo", query = "SELECT p FROM Pratica p WHERE p.registroDiControllo = :registroDiControllo")
    , @NamedQuery(name = "Pratica.findByPreventivoLavoriData", query = "SELECT p FROM Pratica p WHERE p.preventivoLavoriData = :preventivoLavoriData")
    , @NamedQuery(name = "Pratica.findByRevisioneMctcData", query = "SELECT p FROM Pratica p WHERE p.revisioneMctcData = :revisioneMctcData")
    , @NamedQuery(name = "Pratica.findByCollaudoUslData", query = "SELECT p FROM Pratica p WHERE p.collaudoUslData = :collaudoUslData")
    , @NamedQuery(name = "Pratica.findByRegistroDiControlloData", query = "SELECT p FROM Pratica p WHERE p.registroDiControlloData = :registroDiControlloData")
    , @NamedQuery(name = "Pratica.findByOre", query = "SELECT p FROM Pratica p WHERE p.ore = :ore")
    , @NamedQuery(name = "Pratica.findByKilometraggio", query = "SELECT p FROM Pratica p WHERE p.kilometraggio = :kilometraggio")
    , @NamedQuery(name = "Pratica.findByClienteTemporaneo", query = "SELECT p FROM Pratica p WHERE p.clienteTemporaneo = :clienteTemporaneo")
    , @NamedQuery(name = "Pratica.findByVeicoloTemporaneo", query = "SELECT p FROM Pratica p WHERE p.veicoloTemporaneo = :veicoloTemporaneo")
    , @NamedQuery(name = "Pratica.findByDife", query = "SELECT p FROM Pratica p WHERE p.dife = :dife")
    , @NamedQuery(name = "Pratica.findByInterventoData", query = "SELECT p FROM Pratica p WHERE p.interventoData = :interventoData")})
public class Pratica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPratica")
    private Integer idPratica;
    @Size(max = 45)
    @Column(name = "arrivo")
    private String arrivo;
    @Column(name = "data_arrivo")
    @Temporal(TemporalType.DATE)
    private Date dataArrivo;
    @Size(max = 45)
    @Column(name = "uscita")
    private String uscita;
    @Column(name = "data_uscita")
    @Temporal(TemporalType.DATE)
    private Date dataUscita;
    @Lob
    @Size(max = 65535)
    @Column(name = "lavori_segnalati")
    private String lavoriSegnalati;
    @Size(max = 45)
    @Column(name = "numero_fattura")
    private String numeroFattura;
    @Column(name = "data_fattura")
    @Temporal(TemporalType.DATE)
    private Date dataFattura;
    @Column(name = "preventivo_lavori")
    private Boolean preventivoLavori;
    @Column(name = "revisione_mctc")
    private Boolean revisioneMctc;
    @Column(name = "collaudo_usl")
    private Boolean collaudoUsl;
    @Column(name = "registro_di_controllo")
    private Boolean registroDiControllo;
    @Column(name = "preventivo_lavori_data")
    @Temporal(TemporalType.DATE)
    private Date preventivoLavoriData;
    @Column(name = "revisione_mctc_data")
    @Temporal(TemporalType.DATE)
    private Date revisioneMctcData;
    @Column(name = "collaudo_usl_data")
    @Temporal(TemporalType.DATE)
    private Date collaudoUslData;
    @Column(name = "registro_di_controllo_data")
    @Temporal(TemporalType.DATE)
    private Date registroDiControlloData;
    @Column(name = "ore")
    private Integer ore;
    @Column(name = "kilometraggio")
    private Integer kilometraggio;
    @Size(max = 150)
    @Column(name = "cliente_temporaneo")
    private String clienteTemporaneo;
    @Size(max = 150)
    @Column(name = "veicolo_temporaneo")
    private String veicoloTemporaneo;
    @Column(name = "dife")
    private Boolean dife;
    @Column(name = "intervento_data")
    @Temporal(TemporalType.DATE)
    private Date interventoData;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pratica")
    private Collection<Lavoripratichestandard> lavoripratichestandardCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pratica1")
    private Collection<Materialepratica> materialepraticaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pratica")
    private Collection<Orelavorate> orelavorateCollection;
    @JoinColumn(name = "Cliente_idCliente", referencedColumnName = "idCliente")
    @ManyToOne
    private Cliente clienteidCliente;
    @JoinColumn(name = "Veicolo", referencedColumnName = "idVeicolo")
    @ManyToOne
    private Veicolo veicolo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pratica")
    private Collection<Lavoripratichecustom> lavoripratichecustomCollection;

    public Pratica() {
    }

    public Pratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public Integer getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public String getArrivo() {
        return arrivo;
    }

    public void setArrivo(String arrivo) {
        this.arrivo = arrivo;
    }

    public Date getDataArrivo() {
        return dataArrivo;
    }

    public void setDataArrivo(Date dataArrivo) {
        this.dataArrivo = dataArrivo;
    }

    public String getUscita() {
        return uscita;
    }

    public void setUscita(String uscita) {
        this.uscita = uscita;
    }

    public Date getDataUscita() {
        return dataUscita;
    }

    public void setDataUscita(Date dataUscita) {
        this.dataUscita = dataUscita;
    }

    public String getLavoriSegnalati() {
        return lavoriSegnalati;
    }

    public void setLavoriSegnalati(String lavoriSegnalati) {
        this.lavoriSegnalati = lavoriSegnalati;
    }

    public String getNumeroFattura() {
        return numeroFattura;
    }

    public void setNumeroFattura(String numeroFattura) {
        this.numeroFattura = numeroFattura;
    }

    public Date getDataFattura() {
        return dataFattura;
    }

    public void setDataFattura(Date dataFattura) {
        this.dataFattura = dataFattura;
    }

    public Boolean getPreventivoLavori() {
        return preventivoLavori;
    }

    public void setPreventivoLavori(Boolean preventivoLavori) {
        this.preventivoLavori = preventivoLavori;
    }

    public Boolean getRevisioneMctc() {
        return revisioneMctc;
    }

    public void setRevisioneMctc(Boolean revisioneMctc) {
        this.revisioneMctc = revisioneMctc;
    }

    public Boolean getCollaudoUsl() {
        return collaudoUsl;
    }

    public void setCollaudoUsl(Boolean collaudoUsl) {
        this.collaudoUsl = collaudoUsl;
    }

    public Boolean getRegistroDiControllo() {
        return registroDiControllo;
    }

    public void setRegistroDiControllo(Boolean registroDiControllo) {
        this.registroDiControllo = registroDiControllo;
    }

    public Date getPreventivoLavoriData() {
        return preventivoLavoriData;
    }

    public void setPreventivoLavoriData(Date preventivoLavoriData) {
        this.preventivoLavoriData = preventivoLavoriData;
    }

    public Date getRevisioneMctcData() {
        return revisioneMctcData;
    }

    public void setRevisioneMctcData(Date revisioneMctcData) {
        this.revisioneMctcData = revisioneMctcData;
    }

    public Date getCollaudoUslData() {
        return collaudoUslData;
    }

    public void setCollaudoUslData(Date collaudoUslData) {
        this.collaudoUslData = collaudoUslData;
    }

    public Date getRegistroDiControlloData() {
        return registroDiControlloData;
    }

    public void setRegistroDiControlloData(Date registroDiControlloData) {
        this.registroDiControlloData = registroDiControlloData;
    }

    public Integer getOre() {
        return ore;
    }

    public void setOre(Integer ore) {
        this.ore = ore;
    }

    public Integer getKilometraggio() {
        return kilometraggio;
    }

    public void setKilometraggio(Integer kilometraggio) {
        this.kilometraggio = kilometraggio;
    }

    public String getClienteTemporaneo() {
        return clienteTemporaneo;
    }

    public void setClienteTemporaneo(String clienteTemporaneo) {
        this.clienteTemporaneo = clienteTemporaneo;
    }

    public String getVeicoloTemporaneo() {
        return veicoloTemporaneo;
    }

    public void setVeicoloTemporaneo(String veicoloTemporaneo) {
        this.veicoloTemporaneo = veicoloTemporaneo;
    }

    public Boolean getDife() {
        return dife;
    }

    public void setDife(Boolean dife) {
        this.dife = dife;
    }

    public Date getInterventoData() {
        return interventoData;
    }

    public void setInterventoData(Date interventoData) {
        this.interventoData = interventoData;
    }

    @XmlTransient
    public Collection<Lavoripratichestandard> getLavoripratichestandardCollection() {
        return lavoripratichestandardCollection;
    }

    public void setLavoripratichestandardCollection(Collection<Lavoripratichestandard> lavoripratichestandardCollection) {
        this.lavoripratichestandardCollection = lavoripratichestandardCollection;
    }

    @XmlTransient
    public Collection<Materialepratica> getMaterialepraticaCollection() {
        return materialepraticaCollection;
    }

    public void setMaterialepraticaCollection(Collection<Materialepratica> materialepraticaCollection) {
        this.materialepraticaCollection = materialepraticaCollection;
    }

    @XmlTransient
    public Collection<Orelavorate> getOrelavorateCollection() {
        return orelavorateCollection;
    }

    public void setOrelavorateCollection(Collection<Orelavorate> orelavorateCollection) {
        this.orelavorateCollection = orelavorateCollection;
    }

    public Cliente getClienteidCliente() {
        return clienteidCliente;
    }

    public void setClienteidCliente(Cliente clienteidCliente) {
        this.clienteidCliente = clienteidCliente;
    }

    public Veicolo getVeicolo() {
        return veicolo;
    }

    public void setVeicolo(Veicolo veicolo) {
        this.veicolo = veicolo;
    }

    @XmlTransient
    public Collection<Lavoripratichecustom> getLavoripratichecustomCollection() {
        return lavoripratichecustomCollection;
    }

    public void setLavoripratichecustomCollection(Collection<Lavoripratichecustom> lavoripratichecustomCollection) {
        this.lavoripratichecustomCollection = lavoripratichecustomCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPratica != null ? idPratica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pratica)) {
            return false;
        }
        Pratica other = (Pratica) object;
        if ((this.idPratica == null && other.idPratica != null) || (this.idPratica != null && !this.idPratica.equals(other.idPratica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.javasoft81.pratichemanager.entities.Pratica[ idPratica=" + idPratica + " ]";
    }
    
}
