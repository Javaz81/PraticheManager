/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.jsf.beans.utils;

import com.javasoft81.pratichemanager.entities.Pratica;
import com.javasoft81.pratichemanager.jsf.beans.VeicoliSearchView;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author andrea
 */
public class TemporaneiEditDialog implements Serializable {
    
    private VeicoliSearchView v;
    
    private Pratica pratica;
    
    private String veicolo;
    
    private String cliente;
    
    /**
     * Creates a new instance of TemporaneiEditDialog
     */
    public TemporaneiEditDialog() {
    }
    
    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();     
        v = context.getApplication().evaluateExpressionGet(context, "#{veicoliSearchView}", VeicoliSearchView.class);
        pratica = v.getSelectedPratica();
        veicolo = pratica.getVeicoloTemporaneo();
        cliente = pratica.getClienteTemporaneo();
    }

    public String getVeicolo() {
        return veicolo;
    }

    public void setVeicolo(String veicolo) {
        this.veicolo = veicolo;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    
    public void endDialog(){
        pratica.setVeicoloTemporaneo(veicolo);
        pratica.setClienteTemporaneo(cliente);
        RequestContext.getCurrentInstance().closeDialog(pratica);
    }
    
}
