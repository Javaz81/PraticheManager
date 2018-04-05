/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.jsf.beans;

import com.javasoft81.pratichemanager.entities.Categoriatipolavoro;
import com.javasoft81.pratichemanager.entities.Lavoripratichecustom;
import com.javasoft81.pratichemanager.entities.Lavoripratichestandard;
import com.javasoft81.pratichemanager.entities.Materialepratica;
import com.javasoft81.pratichemanager.entities.Pratica;
import com.javasoft81.pratichemanager.jsf.beans.utils.PraticheUtils;
import com.javasoft81.pratichemanager.entities.Veicolo;
import com.javasoft81.pratichemanager.entities.beans.PraticaFacade;
import com.javasoft81.pratichemanager.entities.beans.VeicoloFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author andrea
 */
public class VeicoliSearchView implements Serializable {
    
    private static final long serialVersionUID = 4798826631018877624L;

    @ManagedProperty(value="lavoriManagerBean")
    private LavoriManagerBean lavoriManagerBean;
    
    @ManagedProperty(value="materialiManagerBean")
    private MaterialiManagerBean materialiManagerBean;

    @EJB
    private VeicoloFacade veicoliService;

    @EJB
    private PraticaFacade praticheService;

    //private PraticheUtils currentPratiche;
    private List<Veicolo> veicoli;

    private List<Pratica> pratiche;

    private Veicolo selectedCar;

    private Pratica selectedPratica;
    
    private HashMap<Categoriatipolavoro,List<Lavoripratichestandard>> selectedPraticaLavoriStandard;
    
    private HashMap<Categoriatipolavoro,List<Lavoripratichecustom>> selectedPraticaLavoriCustom;
    
    private List<Materialepratica> selectedMaterialePratica;

    private Integer activeIndexTab;

    private final List<String> statiArrivo;

    /**
     * Creates a new instance of VeicoliSearchView
     */
    public VeicoliSearchView() {
        statiArrivo = PraticheUtils.getStatiArrivo();
    }

    @PostConstruct
    public void init() {
        veicoli = veicoliService.findAll();
        selectedCar = null;        
    }

    public LavoriManagerBean getLavoriManagerBean() {
        return lavoriManagerBean;
    }

    public void setLavoriManagerBean(LavoriManagerBean lavoriManagerBean) {
        this.lavoriManagerBean = lavoriManagerBean;
    }

    public MaterialiManagerBean getMaterialiManagerBean() {
        return materialiManagerBean;
    }

    public void setMaterialiManagerBean(MaterialiManagerBean materialiManagerBean) {
        this.materialiManagerBean = materialiManagerBean;
    }

    public List<Materialepratica> getSelectedMaterialePratica() {
        return selectedMaterialePratica;
    }

    public void setSelectedMaterialePratica(List<Materialepratica> selectedMaterialePratica) {
        this.selectedMaterialePratica = selectedMaterialePratica;
    }
    
    

    public List<String> getStatiArrivo() {
        return statiArrivo;
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

    public HashMap<Categoriatipolavoro, List<Lavoripratichestandard>> getSelectedPraticaLavoriStandard() {
        return selectedPraticaLavoriStandard;
    }

    public HashMap<Categoriatipolavoro, List<Lavoripratichecustom>> getSelectedPraticaLavoriCustom() {
        return selectedPraticaLavoriCustom;
    }

    public void onTabChange(TabChangeEvent event) {
        this.selectedPratica = (Pratica) event.getData();
        this.activeIndexTab = this.pratiche.indexOf(this.selectedPratica);
         if (this.selectedPraticaLavoriStandard == null){
            this.selectedPraticaLavoriStandard = new HashMap<>();
        }else{
            this.selectedPraticaLavoriStandard.clear();
        }
         if (this.selectedPraticaLavoriCustom == null){
            this.selectedPraticaLavoriCustom = new HashMap<>();
        }else{
            this.selectedPraticaLavoriCustom.clear();
        }
        if(this.selectedMaterialePratica == null){
            this.selectedMaterialePratica = new ArrayList<>();
        }else{
            this.selectedMaterialePratica.clear();
        }
        if (!this.pratiche.isEmpty()){
            for(Categoriatipolavoro cat: this.lavoriManagerBean.getCategorie()){
                this.selectedPraticaLavoriStandard.put(cat, this.lavoriManagerBean.getLavoriStandardByCategoria(cat, selectedPratica));
                this.selectedPraticaLavoriCustom.put(cat, this.lavoriManagerBean.getLavoriCustomByCategoria(cat, selectedPratica));
            }
            this.selectedMaterialePratica=this.materialiManagerBean.getMaterialePratica(selectedPratica);
        }
        
    }

    public String getDate(Pratica p) {
        return PraticheUtils.getDate(p);
    }

    public void newPratica() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Pratica Creata con successo", "Creazione avvenuta");
        FacesContext.getCurrentInstance().addMessage(null, message);
        Pratica p = new Pratica();
        this.praticheService.create(p);
        this.pratiche = null; //re-trigger visualization of all tabs        
        this.pratiche = this.praticheService.findPraticaByVeicolo(selectedCar, PraticheUtils.MAX_PRATICHE_ESTRAIBILI);
        this.selectedPratica = this.pratiche.isEmpty() ? null : this.pratiche.get(0);
         if (!this.pratiche.isEmpty()){
            for(Categoriatipolavoro cat: this.lavoriManagerBean.getCategorie()){
                this.selectedPraticaLavoriStandard.put(cat, null);
                this.selectedPraticaLavoriCustom.put(cat, null);
            }
            if(this.selectedMaterialePratica == null){
            this.selectedMaterialePratica = new ArrayList<>();
        }else{
            this.selectedMaterialePratica.clear();
        }
        }
        /*********************       QUI VANNO CREATI  ANCHE TUTTI I MATERIALI ASSOCIATI !!!!!  **********************/
    }

    public void deletePratica() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Pratica cancellata!", "ID pratica:" + selectedPratica.getIdPratica());
        FacesContext.getCurrentInstance().addMessage(null, message);
        for(Categoriatipolavoro c: this.lavoriManagerBean.getCategorie()){
            this.lavoriManagerBean.cancellaTuttiLavoriDiCategoria(this.selectedPraticaLavoriStandard.get(c), this.selectedPraticaLavoriCustom.get(c));
        }
        /*********************       QUI VANNO CANCELLATI ANCHE TUTTI I MATERIALI ASSOCIATI !!!!!  **********************/
        this.praticheService.remove(this.selectedPratica);
        this.pratiche = null; //re-trigger visualization of all tabs
        this.pratiche = this.praticheService.findPraticaByVeicolo(selectedCar, PraticheUtils.MAX_PRATICHE_ESTRAIBILI);
        this.selectedPratica = this.pratiche.isEmpty() ? null : this.pratiche.get(0);
    }
    
    public void savePratica() {
        this.praticheService.edit(this.selectedPratica);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Pratica salvata correttamente", "ID pratica:" + selectedPratica.getIdPratica());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void removeMateriale(Materialepratica m){
        this.materialiManagerBean.removeMateriale(m);
        this.selectedMaterialePratica.remove(m);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Materiale rimosso","Rimozione materiale processata con successo.");
        FacesContext.getCurrentInstance().addMessage(null, message);
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
        if (this.selectedPraticaLavoriStandard == null){
            this.selectedPraticaLavoriStandard = new HashMap<>();
        }else{
            this.selectedPraticaLavoriStandard.clear();
        }
         if (this.selectedPraticaLavoriCustom == null){
            this.selectedPraticaLavoriCustom = new HashMap<>();
        }else{
            this.selectedPraticaLavoriCustom.clear();
        }
         if(this.selectedMaterialePratica == null){
            this.selectedMaterialePratica = new ArrayList<>();
        }else{
            this.selectedMaterialePratica.clear();
        }
         /******************* MANCA DI REINIZIALIZZARE ANCHE IL MATERIALE ASSOCIATO ****************/
        if (!this.pratiche.isEmpty()){
            for(Categoriatipolavoro cat: this.lavoriManagerBean.getCategorie()){
                this.selectedPraticaLavoriStandard.put(cat, this.lavoriManagerBean.getLavoriStandardByCategoria(cat, selectedPratica));
                this.selectedPraticaLavoriCustom.put(cat, this.lavoriManagerBean.getLavoriCustomByCategoria(cat, selectedPratica));
            }
            this.selectedMaterialePratica = this.materialiManagerBean.getMaterialePratica(selectedPratica);
        }
        //closing dialog trigger the <p:ajax> event dialogReturn and its 
        //listener 'onVeicoliChosen'        
        RequestContext.getCurrentInstance().closeDialog(veicolo);
    }
}
