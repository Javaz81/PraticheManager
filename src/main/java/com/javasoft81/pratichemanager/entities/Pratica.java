/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

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
    , @NamedQuery(name = "Pratica.findByInterventoData", query = "SELECT p FROM Pratica p WHERE p.interventoData = :interventoData")
    , @NamedQuery(name = "Pratica.findByStatoVeicoloArrivo", query = "SELECT p FROM Pratica p WHERE p.statoVeicoloArrivo = :statoVeicoloArrivo")
    , @NamedQuery(name = "Pratica.findByVeicolo", query = "SELECT p FROM Pratica p WHERE p.veicolo = :veicolo ORDER BY p.dataArrivo DESC")
    , @NamedQuery(name = "Pratica.countByVeicolo", query = "SELECT count(p) FROM Pratica p WHERE p.veicolo = :veicolo")
    , @NamedQuery(name = "Pratica.findByTempoPartenzaSede", query = "SELECT p FROM Pratica p WHERE p.tempoPartenzaSede = :tempoPartenzaSede")
    , @NamedQuery(name = "Pratica.findByTempoInizioLavoro", query = "SELECT p FROM Pratica p WHERE p.tempoInizioLavoro = :tempoInizioLavoro")
    , @NamedQuery(name = "Pratica.findByTempoFineLavoro", query = "SELECT p FROM Pratica p WHERE p.tempoFineLavoro = :tempoFineLavoro")
    , @NamedQuery(name = "Pratica.findByTempoRientroSede", query = "SELECT p FROM Pratica p WHERE p.tempoRientroSede = :tempoRientroSede")
    , @NamedQuery(name = "Pratica.findByTotKmAndRit", query = "SELECT p FROM Pratica p WHERE p.totKmAndRit = :totKmAndRit")
    , @NamedQuery(name = "Pratica.findByTotOreLavoro", query = "SELECT p FROM Pratica p WHERE p.totOreLavoro = :totOreLavoro")
    , @NamedQuery(name = "Pratica.findByTotOreViaggio", query = "SELECT p FROM Pratica p WHERE p.totOreViaggio = :totOreViaggio")
    , @NamedQuery(name = "Pratica.findPraticheAttive", query = "SELECT p FROM Pratica p WHERE (p.uscita IS NULL AND p.dataUscita IS NULL)")})
public class Pratica implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static final List<String> STATO_ARRIVO_VEICOLO=new ArrayList<String>();
    static{
        STATO_ARRIVO_VEICOLO.add("FUNZIONANTE MA NON OTTIMALE");
        STATO_ARRIVO_VEICOLO.add("BATTERIA SCARICA");
        STATO_ARRIVO_VEICOLO.add("DANNEGGIATA");
        STATO_ARRIVO_VEICOLO.add("UTILIZZO ERRATO");
        STATO_ARRIVO_VEICOLO.add("PERDITE OLIO TELAIO");
        STATO_ARRIVO_VEICOLO.add("PERDITE OLIO COLONNA");
        STATO_ARRIVO_VEICOLO.add("PERDITE OLIO BRACCIO");
        STATO_ARRIVO_VEICOLO.add("IN EMERGENZA");
        STATO_ARRIVO_VEICOLO.add("IN BLOCCO PARZIALE");
        STATO_ARRIVO_VEICOLO.add("IN BLOCCO TOTALE");
        STATO_ARRIVO_VEICOLO.add("FUORI LIMITI DI UTILIZZO");
        STATO_ARRIVO_VEICOLO.add("INCASTRATA IN BLOCCO");
        STATO_ARRIVO_VEICOLO.add("BLOCCO PER SOVRACCARICO");
        STATO_ARRIVO_VEICOLO.add("BLOCCO PER MANOVRA ERRATA");
        STATO_ARRIVO_VEICOLO.add("ALTRO");
    }
    
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
    @Size(max = 45)
    @Column(name = "stato_veicolo_arrivo")
    private String statoVeicoloArrivo;
    @Column(name = "tempoPartenzaSede")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tempoPartenzaSede;
    @Column(name = "tempoInizioLavoro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tempoInizioLavoro;
    @Column(name = "tempoFineLavoro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tempoFineLavoro;
    @Column(name = "tempoRientroSede")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tempoRientroSede;
    @Column(name = "totKmAndRit")
    private Integer totKmAndRit;
    @Column(name = "totOreLavoro")
    private Integer totOreLavoro;
    @Column(name = "totOreViaggio")
    private Integer totOreViaggio;
    @JoinColumn(name = "Cliente_idCliente", referencedColumnName = "idCliente")
    @ManyToOne(fetch = FetchType.EAGER)
    private Cliente clienteidCliente;
    @JoinColumn(name = "Veicolo", referencedColumnName = "idVeicolo")
    @ManyToOne(fetch = FetchType.EAGER)
    private Veicolo veicolo;

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

    public String getStatoVeicoloArrivo() {
        return statoVeicoloArrivo;
    }

    public void setStatoVeicoloArrivo(String statoVeicoloArrivo) {
        this.statoVeicoloArrivo = statoVeicoloArrivo;
    }

    public Date getTempoPartenzaSede() {
        return tempoPartenzaSede;
    }

    public void setTempoPartenzaSede(Date tempoPartenzaSede) {
        this.tempoPartenzaSede = tempoPartenzaSede;
    }

    public Date getTempoInizioLavoro() {
        return tempoInizioLavoro;
    }

    public void setTempoInizioLavoro(Date tempoInizioLavoro) {
        this.tempoInizioLavoro = tempoInizioLavoro;
    }

    public Date getTempoFineLavoro() {
        return tempoFineLavoro;
    }

    public void setTempoFineLavoro(Date tempoFineLavoro) {
        this.tempoFineLavoro = tempoFineLavoro;
    }

    public Date getTempoRientroSede() {
        return tempoRientroSede;
    }

    public void setTempoRientroSede(Date tempoRientroSede) {
        this.tempoRientroSede = tempoRientroSede;
    }

    public Integer getTotKmAndRit() {
        return totKmAndRit;
    }

    public void setTotKmAndRit(Integer totKmAndRit) {
        this.totKmAndRit = totKmAndRit;
    }

    public Integer getTotOreLavoro() {
        return totOreLavoro;
    }

    public void setTotOreLavoro(Integer totOreLavoro) {
        this.totOreLavoro = totOreLavoro;
    }

    public Integer getTotOreViaggio() {
        return totOreViaggio;
    }

    public void setTotOreViaggio(Integer totOreViaggio) {
        this.totOreViaggio = totOreViaggio;
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
