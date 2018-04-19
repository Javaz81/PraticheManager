/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.jsf.beans.utils;

import com.javasoft81.pratichemanager.entities.Cliente;
import com.javasoft81.pratichemanager.entities.Veicolo;
import com.javasoft81.pratichemanager.jsf.beans.VeicoliSearchView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author andrea
 */
public class VeicoloNewDialog implements Serializable {

    private Cliente cliente;

    private Veicolo veicolo;

    private String[] tipiVeicolo;

    private VeicoliSearchView v;

    private List<Cliente> clienti;

    private List<Cliente> filtered;

    //Proprieta veicolo
    private String anno;
    private String marca;
    private String modello;
    private String matricola;
    private String tipo;
    private String targa;
    private Integer portataMax;

    //Proprietà cliente
    private String nome;
    private String cognome;
    private String localita;
    private String cellulare;

    /**
     * Creates a new instance of VeicoloNewDialog
     */
    public VeicoloNewDialog() {
    }

    public static enum ResponseParameter {
        CLIENTE_ASSEGNATO("assegnato"),
        CLIENTE_MODIFICATO("modificato"),
        CLIENTE_NUOVO("nuovo"),
        VEICOLO("veicolo");
        
        private String parName;

        ResponseParameter(String pN) {
            this.parName = pN;
        }

        @Override
        public String toString() {
            return this.parName;
        }
    }

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        v = context.getApplication().evaluateExpressionGet(context, "#{veicoliSearchView}", VeicoliSearchView.class);
        veicolo = new Veicolo();
        cliente = new Cliente();
        clienti = v.getClienti();
        tipiVeicolo = PraticheUtils.TIPI_VEICOLO;
        filtered = new ArrayList<>();
    }

    public List<Cliente> getFiltered() {
        return filtered;
    }

    public void setFiltered(List<Cliente> filtered) {
        this.filtered = filtered;
    }

    public List<Cliente> getClienti() {
        return clienti;
    }

    public String[] getTipiVeicolo() {
        return tipiVeicolo;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModello() {
        return modello;
    }

    public void setModello(String modello) {
        this.modello = modello;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }

    public Integer getPortataMax() {
        return portataMax;
    }

    public void setPortataMax(Integer portataMax) {
        this.portataMax = portataMax;
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

    public String getLocalita() {
        return localita;
    }

    public void setLocalita(String localita) {
        this.localita = localita;
    }

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public void assignCliente(Cliente c) {
        RequestContext.getCurrentInstance().closeDialog(c);
    }

    public void openClienteDialog() {
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("draggable", true);
        options.put("modal", true);
        options.put("contentWidth", 800);
        RequestContext.getCurrentInstance().openDialog("menuAssignCliente", options, null);
    }

    public void onClienteAssigned(SelectEvent event) {
        Cliente c = (Cliente) event.getObject();
        this.cliente = c;
        this.nome = c.getNome();
        this.cognome = c.getCognome();
        this.localita = c.getLocalita();
        this.cellulare = c.getCellulare();
    }

    public void endEditDialog() {
        //Validate veicolo and client
        boolean validate = false;
        HashMap<String, Object> response = new HashMap<>();
        //Vediamo se il cliente è stato scelto dalla lista
        boolean edit = false;
        if (cliente != null) {
            //se è stato modificato, allora modificalo
            if(!cliente.getNome().equals(nome)){
                cliente.setNome(nome);
                edit = true;
            }
            if(!cliente.getCognome().equals(cognome)){
                cliente.setCognome(cognome);
                edit=true;
            }
            if(!cliente.getLocalita().equals(localita)){
                cliente.setLocalita(localita);
                edit=true;
            }
            if(!cliente.getCellulare().equals(cellulare)){
                cliente.setCellulare(cellulare);
                edit=true;
            }
           if(edit)
               response.put(ResponseParameter.CLIENTE_MODIFICATO.toString(), cliente);
           else
               response.put(ResponseParameter.CLIENTE_ASSEGNATO.toString(), cliente);           
        }else{
            //cliente nuovo,
            cliente = new Cliente();
            cliente.setCellulare(cellulare);
            cliente.setCognome(cognome);
            cliente.setLocalita(localita);
            cliente.setNome(nome);            
            response.put(ResponseParameter.CLIENTE_NUOVO.toString(), cliente);
        }
        this.veicolo = new Veicolo();
        veicolo.setAnno(Integer.parseInt(anno));
        this.veicolo.setMarca(marca);
        this.veicolo.setMatricola(matricola);
        this.veicolo.setPortataMax(portataMax);
        this.veicolo.setModello(modello);
        this.veicolo.setTipo(tipo);
        this.veicolo.setTarga(targa);
        //il cliente del veicolo lo setterà durante la persistenza in modo tale che se c'è un errore di persistenza stesso, non venga
        //creato niente . . . 
        RequestContext.getCurrentInstance().closeDialog(veicolo);
    }
}
