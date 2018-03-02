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
import java.util.Random;
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
    
    private Veicolo selected;
    
    /**
     * Test string
     */
    private String ts;
    /**
     * Creates a new instance of VeicoliSearchView
     */
    public VeicoliSearchView() {
    }
    
    @PostConstruct
    public void init() {
        veicoli = service.findAll();
        selected=null;
        ts="Ciao";
    }
    
    public List<Veicolo> getVeicoli() {
        return veicoli;
    }

    public Veicolo getSelected() {
        return selected;
    }

    public String getTs() {
        ts=Integer.toString(new Random().nextInt(32));
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
        System.err.println(ts);
    }
   
    public void chooseVeicoli() {
        Map<String,Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("draggable", true);
        options.put("modal", true);
        RequestContext.getCurrentInstance().openDialog("searchVeicoli", options, null);
    }
     
    public void onVeicoliChosen(SelectEvent event) {
        Veicolo car = (Veicolo) event.getObject();
        this.selected = car;
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Autoarticolato selezionato", "Id:" + car.getMarca());         
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void setSelected(Veicolo selected) {
        this.selected = selected;
    }
    
    public void selectCarFromDialog(Veicolo veicolo) {
        this.selected = veicolo;
        //closing dialog trigger the <p:ajax> event dialogReturn and its 
        //listener 'onVeicoliChosen'
        RequestContext.getCurrentInstance().closeDialog(veicolo);
    }
    public void gane(){
        System.out.println("dakjsdlkjaldkjk");
    }
}
