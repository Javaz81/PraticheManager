/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.jsf.beans;

import com.javasoft81.pratichemanager.entities.Pratica;
import com.javasoft81.pratichemanager.jsf.beans.utils.PraticheUtils;
import com.javasoft81.pratichemanager.entities.Veicolo;
import com.javasoft81.pratichemanager.entities.beans.PraticaFacade;
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
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author andrea
 */
public class VeicoliSearchView implements Serializable {

    @EJB
    private VeicoloFacade veicoliService;

    @EJB
    private PraticaFacade praticheService;

    //private PraticheUtils currentPratiche;
    private List<Veicolo> veicoli;

    private List<Pratica> pratiche;

    private Veicolo selectedCar;

    private Pratica selectedPratica;

    private Integer activeIndexTab;

    /**
     * Creates a new instance of VeicoliSearchView
     */
    public VeicoliSearchView() {
    }

    @PostConstruct
    public void init() {
        veicoli = veicoliService.findAll();
        selectedCar = null;
    }

    public List<Veicolo> getVeicoli() {
        return veicoli;
    }

    public Veicolo getSelectedCar() {
        return selectedCar;
    }

    public Pratica getSelectedPratica() {
        return selectedPratica;
    }

    public void setSelectedPratica(Pratica selectedPratica) {
        this.selectedPratica = selectedPratica;
    }

    public List<Pratica> getPratiche() {
        return pratiche;
    }

    public void setPratiche(List<Pratica> pratiche) {
        this.pratiche = pratiche;
    }

    public Integer getActiveIndexTab() {
        return activeIndexTab;
    }

    public void setActiveIndexTab(Integer activeIndexTab) {
        this.activeIndexTab = activeIndexTab;
    }

    /**
     * ************************BUSINEES METHOD***********************
     * @param event
     */
    /*
    public void onTabChange(TabChangeEvent event) {
        //FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.getTab().getTitle());
        this.currentPratiche.setSelected(event.getData();
    }         
     */
    /*
    public void onTabChange(Pratica p) {
        this.selectedPratica = p;
    }
    */
    public void onTabChange(TabChangeEvent event) {
        System.out.println(event.getTab().getTitle());
        this.selectedPratica = (Pratica)event.getData();
        this.activeIndexTab = this.pratiche.indexOf(this.selectedPratica);
    }

    public String getDate(Pratica p) {
        return PraticheUtils.getDate(p);
    }

    public void savePratica(Pratica p) {
        this.praticheService.edit(p);
    }

    public void chooseVeicoli() {
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("draggable", true);
        options.put("modal", true);
        RequestContext.getCurrentInstance().openDialog("searchVeicoli", options, null);
    }

    public void onVeicoliChosen(SelectEvent event) {
        Veicolo car = (Veicolo) event.getObject();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Autoarticolato selezionato", "Id:" + car.getMarca());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void setSelectedCar(Veicolo selectedCar) {
        this.selectedCar = selectedCar;
    }

    public void selectCarFromDialog(Veicolo veicolo) {
        this.selectedCar = veicolo;
        this.pratiche = this.praticheService.findPraticaByVeicolo(selectedCar, PraticheUtils.MAX_PRATICHE_ESTRAIBILI);
        this.selectedPratica = this.pratiche.isEmpty() ? null : this.pratiche.get(0);
        //closing dialog trigger the <p:ajax> event dialogReturn and its 
        //listener 'onVeicoliChosen'        
        RequestContext.getCurrentInstance().closeDialog(veicolo);
    }
}
