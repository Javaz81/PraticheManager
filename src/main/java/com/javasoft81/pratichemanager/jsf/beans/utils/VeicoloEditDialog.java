/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.jsf.beans.utils;

import com.javasoft81.pratichemanager.entities.Veicolo;
import com.javasoft81.pratichemanager.jsf.beans.VeicoliSearchView;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Andrea Iavazzo
 */
public class VeicoloEditDialog implements Serializable {
    
    private Veicolo veicolo;
    
    private VeicoliSearchView v;
    
    private String[] tipiVeicolo;

    /**
     * Creates a new instance of VeicoloEditDialog
     */
    public VeicoloEditDialog() {
    }

    @PostConstruct
    public void init (){
        FacesContext context = FacesContext.getCurrentInstance();     
        v = context.getApplication().evaluateExpressionGet(context, "#{veicoliSearchView}", VeicoliSearchView.class);
        veicolo = v.getSelectedCar();
        tipiVeicolo=PraticheUtils.TIPI_VEICOLO;
    }

    public String[] getTipiVeicolo() {
        return tipiVeicolo;
    }
    
    
    public Veicolo getVeicolo() {
        return veicolo;
    }

    public void setVeicolo(Veicolo veicolo) {
        this.veicolo = veicolo;
    }

    public VeicoliSearchView getV() {
        return v;
    }

    public void setV(VeicoliSearchView v) {
        this.v = v;
    }
    
    public void endEditDialog(){
        RequestContext.getCurrentInstance().closeDialog(veicolo);
    }
}
