/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.jsf.beans;

import com.javasoft81.pratichemanager.entities.Pratica;
import com.javasoft81.pratichemanager.jsf.beans.utils.PraticheVeicoloList;
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

/**
 *
 * @author andrea
 */
public class VeicoliSearchView implements Serializable {
    
    @EJB
    private VeicoloFacade veicoliService;
    
    @EJB
    private PraticaFacade praticheService;
    
    private PraticheVeicoloList currentPratiche;
    
    private List<Veicolo> veicoli;
    
    private Veicolo selected;
    
    /**
     * Creates a new instance of VeicoliSearchView
     */
    public VeicoliSearchView() {
    }
    
    @PostConstruct
    public void init() {
        veicoli = veicoliService.findAll();
        selected=null;
        currentPratiche=new PraticheVeicoloList();
        currentPratiche.setService(praticheService);
    }
    
    public List<Veicolo> getVeicoli() {
        return veicoli;
    }

    public Veicolo getSelected() {
        return selected;
    }

    public PraticheVeicoloList getPraticheBean() {
        return currentPratiche;
    }

    public void setPraticheBean(PraticheVeicoloList praticheBean) {
        this.currentPratiche = praticheBean;
    }
/*
    public void onTabChange(TabChangeEvent event) {
        //FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.getTab().getTitle());
        this.currentPratiche.setSelected(event.getData();
    }         
    */
    public void onTabChange(Pratica p){
        Pratica rp = this.currentPratiche.aggiornaPratica(p);
        p=rp;
        this.currentPratiche.setSelected(p);
    }
    public void savePratica(){
        this.currentPratiche.saveSelectedPratica();
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
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Autoarticolato selezionato", "Id:" + car.getMarca());         
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void setSelected(Veicolo selected) {
        this.selected = selected;
    }
    
    public void selectCarFromDialog(Veicolo veicolo) {
        this.selected = veicolo;
        this.currentPratiche.loadPratiche(veicolo);
        //closing dialog trigger the <p:ajax> event dialogReturn and its 
        //listener 'onVeicoliChosen'        
        RequestContext.getCurrentInstance().closeDialog(veicolo);
    }
}
