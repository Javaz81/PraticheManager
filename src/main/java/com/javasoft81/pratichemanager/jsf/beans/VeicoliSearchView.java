/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.jsf.beans;

import com.javasoft81.pratichemanager.entities.Veicolo;
import com.javasoft81.pratichemanager.entities.beans.VeicoloFacade;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author andrea
 */
public class VeicoliSearchView implements Serializable {
    @EJB
    private VeicoloFacade service;
    
    private List<Veicolo> veicoli;
    
    /**
     * Creates a new instance of VeicoliSearchView
     */
    public VeicoliSearchView() {
    }
    
    @PostConstruct
    public void init() {
        veicoli = service.findAll();
    }
    
    public List<Veicolo> getVeicoli() {
        return veicoli;
    }
   
    public void chooseVeicoli() {
        Map<String,Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("modal", true);
        RequestContext.getCurrentInstance().openDialog("searchVeicoli", options, null);
    }
     
     public void onVeicoliChosen() {        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO.toString(), "Autoarticolato selezionato");         
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
     
    public void onVeicoliChosen_old(SelectEvent event) {
        Veicolo car = (Veicolo) event.getObject();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Autoarticolato selezionato", "Id:" + car.toString());         
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void selectCarFromDialog(Veicolo veicolo) {
        RequestContext.getCurrentInstance().closeDialog(veicolo);
    }
}
