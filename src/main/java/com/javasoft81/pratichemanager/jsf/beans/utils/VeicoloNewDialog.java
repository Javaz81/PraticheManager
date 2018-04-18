/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.jsf.beans.utils;

import com.javasoft81.pratichemanager.entities.Cliente;
import com.javasoft81.pratichemanager.entities.Veicolo;
import com.javasoft81.pratichemanager.entities.beans.ClienteFacade;
import com.javasoft81.pratichemanager.entities.beans.VeicoloFacade;
import com.javasoft81.pratichemanager.jsf.beans.VeicoliSearchView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author andrea
 */
public class VeicoloNewDialog implements Serializable{

    private Cliente cliente;
    
    private Veicolo veicolo;
    
    private String[] tipiVeicolo;
    
    private VeicoliSearchView v;
    
    private List<Cliente> clienti;
    
    private List<Cliente> filteredListChangingCliente;
    
    /**
     * Creates a new instance of VeicoloNewDialog
     */
    public VeicoloNewDialog() {
    }
    
    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();     
        v = context.getApplication().evaluateExpressionGet(context, "#{veicoliSearchView}", VeicoliSearchView.class);
        veicolo = new Veicolo();
        cliente = new Cliente();
        clienti = v.getClienti();
        tipiVeicolo = PraticheUtils.TIPI_VEICOLO;
        filteredListChangingCliente = new ArrayList<>();
    }

    public List<Cliente> getFilteredListChangingCliente() {
        return filteredListChangingCliente;
    }

    public void setFilteredListChangingCliente(List<Cliente> filteredListChangingCliente) {
        this.filteredListChangingCliente = filteredListChangingCliente;
    }

    public List<Cliente> getClienti() {
        return clienti;
    }        

    public String[] getTipiVeicolo() {
        return tipiVeicolo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veicolo getVeicolo() {
        return veicolo;
    }

    public void setVeicolo(Veicolo veicolo) {
        this.veicolo = veicolo;
    }       
    public void assignCliente(Cliente c){
        this.cliente = c;
    }
    public void endEditDialog(){
        RequestContext.getCurrentInstance().closeDialog(veicolo);
    }
}
