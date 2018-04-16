/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.jsf.beans;

import com.javasoft81.pratichemanager.entities.Articolo;
import com.javasoft81.pratichemanager.entities.Categoriatipolavoro;
import com.javasoft81.pratichemanager.entities.Cliente;
import com.javasoft81.pratichemanager.entities.Lavoripratichecustom;
import com.javasoft81.pratichemanager.entities.Lavoripratichestandard;
import com.javasoft81.pratichemanager.entities.Materialepratica;
import com.javasoft81.pratichemanager.entities.MaterialepraticaPK;
import com.javasoft81.pratichemanager.entities.Pratica;
import com.javasoft81.pratichemanager.entities.Tipolavoro;
import com.javasoft81.pratichemanager.jsf.beans.utils.PraticheUtils;
import com.javasoft81.pratichemanager.entities.Veicolo;
import com.javasoft81.pratichemanager.entities.beans.PraticaFacade;
import com.javasoft81.pratichemanager.entities.beans.VeicoloFacade;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;
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

    @ManagedProperty(value = "lavoriManagerBean")
    private LavoriManagerBean lavoriManagerBean;

    @ManagedProperty(value = "materialiManagerBean")
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

    private HashMap<Categoriatipolavoro, List<Lavoripratichestandard>> selectedPraticaLavoriStandard;

    private HashMap<Categoriatipolavoro, List<Lavoripratichecustom>> selectedPraticaLavoriCustom;

    private List<Materialepratica> selectedMaterialePratica;

    private Integer activeIndexTab;

    private final List<String> statiArrivo;

    private Materialepratica selectedMaterialePraticaDialog;

    private Lavoripratichecustom selectedLavoroCustomDialog;

    private Categoriatipolavoro selectedCategoriaDialog;
    
    private List<Veicolo> filteredVeicoli;


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

    public Materialepratica getSelectedMaterialePraticaDialog() {
        return selectedMaterialePraticaDialog;
    }

    public void setSelectedMaterialePraticaDialog(Materialepratica selectedMaterialePraticaDialog) {
        this.selectedMaterialePraticaDialog = selectedMaterialePraticaDialog;
    }

    public Lavoripratichecustom getSelectedLavoroCustomDialog() {
        return selectedLavoroCustomDialog;
    }

    public void setSelectedLavoroCustomDialog(Lavoripratichecustom selectedLavoroCustomDialog) {
        this.selectedLavoroCustomDialog = selectedLavoroCustomDialog;
    }

    public Categoriatipolavoro getSelectedCategoriaDialog() {
        return selectedCategoriaDialog;
    }

    public void setSelectedCategoriaDialog(Categoriatipolavoro selectedCategoriaDialog) {
        this.selectedCategoriaDialog = selectedCategoriaDialog;
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

    public List<Veicolo> getFilteredVeicoli() {
        return filteredVeicoli;
    }

    public void setFilteredVeicoli(List<Veicolo> filteredVeicoli) {
        this.filteredVeicoli = filteredVeicoli;
    }       
    
    public boolean filterVeicoloByCliente(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if(filterText == null||filterText.equals("")) {
            return true;
        }         
        if(value == null) {
            return false;
        }
        String nome = ((Cliente)value).getNome()==null?"":((Cliente)value).getNome();
        String cognome = ((Cliente)value).getCognome()==null?"":((Cliente)value).getCognome();
        boolean result;
        result = (nome.contains(filterText) || cognome.contains(filterText));
        return result;
    }

    public void onTabChange(TabChangeEvent event) {
        this.selectedPratica = (Pratica) event.getData();
        this.activeIndexTab = this.pratiche.indexOf(this.selectedPratica);
        if (this.selectedPraticaLavoriStandard == null) {
            this.selectedPraticaLavoriStandard = new HashMap<>();
        } else {
            this.selectedPraticaLavoriStandard.clear();
        }
        if (this.selectedPraticaLavoriCustom == null) {
            this.selectedPraticaLavoriCustom = new HashMap<>();
        } else {
            this.selectedPraticaLavoriCustom.clear();
        }
        if (this.selectedMaterialePratica == null) {
            this.selectedMaterialePratica = new ArrayList<>();
        } else {
            this.selectedMaterialePratica.clear();
        }
        if (!this.pratiche.isEmpty()) {
            for (Categoriatipolavoro cat : this.lavoriManagerBean.getCategorie()) {
                this.selectedPraticaLavoriStandard.put(cat, this.lavoriManagerBean.getLavoriStandardByCategoria(cat, selectedPratica));
                this.selectedPraticaLavoriCustom.put(cat, this.lavoriManagerBean.getLavoriCustomByCategoria(cat, selectedPratica));
            }
            this.selectedMaterialePratica = this.materialiManagerBean.getMaterialePratica(selectedPratica);
        }

    }

    public String getDate(Pratica p) {
        return PraticheUtils.getDate(p);
    }

    public String format_IT_Date(Date d) {
        return PraticheUtils.getFormattedITDate(d);
    }

    public String format_IT_Boolean(Boolean b) {
        return PraticheUtils.getFormattedBoolean(b);
    }

    public void newPratica() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Pratica Creata con successo", "Creazione avvenuta");
        FacesContext.getCurrentInstance().addMessage(null, message);
        Pratica p = new Pratica();
        if (this.selectedPratica == null) {
            p.setVeicolo(selectedCar);
            p.setClienteidCliente(selectedCar.getCliente());
        } else {
            p.setVeicolo(this.selectedPratica.getVeicolo());
            p.setClienteidCliente(this.selectedPratica.getClienteidCliente());
        }
        p.setDataArrivo(Calendar.getInstance().getTime());
        this.praticheService.create(p);
        this.pratiche = this.praticheService.findPraticaByVeicolo(selectedCar, PraticheUtils.MAX_PRATICHE_ESTRAIBILI);
        this.selectedMaterialePratica = new ArrayList<>();
        this.selectedPraticaLavoriCustom = new HashMap<>();
        for (Categoriatipolavoro c : this.lavoriManagerBean.getCategorie()) {
            this.selectedPraticaLavoriCustom.put(c, new ArrayList());
            this.selectedPraticaLavoriStandard.put(c, new ArrayList<>());
        }
        this.selectedPratica = this.pratiche.get(0);
        this.activeIndexTab = 0;
    }

    public void deletePratica() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Pratica cancellata!", "ID pratica:" + selectedPratica.getIdPratica());
        FacesContext.getCurrentInstance().addMessage(null, message);
        for (Categoriatipolavoro c : this.lavoriManagerBean.getCategorie()) {
            this.lavoriManagerBean.cancellaTuttiLavoriDiCategoria(this.selectedPraticaLavoriStandard.get(c), this.selectedPraticaLavoriCustom.get(c));
        }
        for (Materialepratica mat : this.selectedMaterialePratica) {
            this.materialiManagerBean.removeMateriale(mat);
        }
        this.praticheService.remove(this.selectedPratica);
        this.pratiche = null; //re-trigger visualization of all tabs
        this.pratiche = this.praticheService.findPraticaByVeicolo(selectedCar, PraticheUtils.MAX_PRATICHE_ESTRAIBILI);
        this.selectedPratica = this.pratiche.isEmpty() ? null : this.pratiche.get(0);
    }

    public void removeLavoroStandard(Lavoripratichestandard lav) {
        this.lavoriManagerBean.cancellaLavoroStandard(lav);
        this.selectedPraticaLavoriStandard.get(lav.getTipolavoro().getCategoria()).removeIf((Lavoripratichestandard i) -> i.getId().equals(lav.getId()));
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Lavoro rimosso correttamente", "Successo");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void removeLavoroCustom(Lavoripratichecustom lav) {
        this.lavoriManagerBean.cancellaLavoroCustom(lav);
        this.selectedPraticaLavoriCustom.get(lav.getCategoria()).removeIf((Lavoripratichecustom i) -> i.getId().equals(lav.getId()));
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Lavoro rimosso correttamente", "Successo");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }


    public void removeMateriale(Materialepratica m) {
        this.materialiManagerBean.removeMateriale(m);
        this.selectedMaterialePratica.remove(m);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Materiale rimosso", "Rimozione materiale processata con successo.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void chooseVeicoli() {
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("draggable", true);
        options.put("modal", true);
        RequestContext.getCurrentInstance().openDialog("searchVeicoli", options, null);
    }

    public void chooseLavoriStandard(Categoriatipolavoro cat) {
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("draggable", true);
        options.put("modal", true);
        this.selectedCategoriaDialog = cat;
        RequestContext.getCurrentInstance().openDialog("lavori/selectionLavoriStandard", options, null);
    }

    public void chooseLavoriCustom(Categoriatipolavoro cat) {
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("draggable", true);
        options.put("modal", true);
        this.selectedCategoriaDialog = cat;
        RequestContext.getCurrentInstance().openDialog("lavori/addLavoriCustom", options, null);
    }

    public void chooseMaterialePratica() {
        Map<String, Object> options = new HashMap<>();
        //options.put("resizable", false);
        options.put("draggable", true);
        options.put("modal", false);
        options.put("contentWidth", 1000);
        options.put("contentHeight", 380);
        RequestContext.getCurrentInstance().openDialog("materiale/menuAddMaterialePratica", options, null);
    }

    public void menuMateriale(Materialepratica mat) {
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("draggable", true);
        options.put("modal", true);
        this.selectedMaterialePraticaDialog = mat;
        RequestContext.getCurrentInstance().openDialog("materiale/menuEditQuantitaMateriale", options, null);
    }

    public void editLavoroCustom(Lavoripratichecustom lav) {
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("draggable", true);
        options.put("modal", true);
        this.selectedLavoroCustomDialog = lav;
        RequestContext.getCurrentInstance().openDialog("lavori/menuLavori", options, null);
    }

    public void openMenuGenerale() {
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("draggable", true);
        options.put("modal", true);
        options.put("contentWidth", 900);
        RequestContext.getCurrentInstance().openDialog("generale/menuGenerale", options, null);
    }

    public void onMenuGenerale(SelectEvent event) {
        //Pratica retPratica = (Pratica) event.getObject();
        //Trim all blank field and set null
        nullifyAllBlankField(selectedPratica);
        this.praticheService.edit(selectedPratica);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Pratica aggiornata", "Successo");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onLavoriCustomEdit(SelectEvent event) {
        this.lavoriManagerBean.editLavoroCustom(this.selectedLavoroCustomDialog);
        this.selectedPraticaLavoriCustom.get(this.selectedLavoroCustomDialog.getCategoria()).forEach((Lavoripratichecustom i) -> {
            if (i.getId().equals(VeicoliSearchView.this.selectedLavoroCustomDialog.getId())) {
                i.setDescrizione(selectedLavoroCustomDialog.getDescrizione());
            }
        });
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Descrizione modificato", "Successo");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onLavoriCustomChosen(SelectEvent event) {
        List<Lavoripratichecustom> selezionati = (List<Lavoripratichecustom>) event.getObject();
        //aggiunge i lavori selezionati... 
        Lavoripratichecustom temp;
        List<Lavoripratichecustom> ls = this.selectedPraticaLavoriCustom.get(selectedCategoriaDialog);
        for (Lavoripratichecustom lav : selezionati) {
            temp = this.lavoriManagerBean.creaNuovoLavoroCustom(this.selectedPratica, lav);
            ls.add(temp);
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Lavori aggiornati",
                "Categoria ".concat(this.selectedCategoriaDialog.getNome()).concat(" aggiornata con successo!"));
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onLavoriStandardChosen(SelectEvent event) {
        List<Tipolavoro> selezionati = (List<Tipolavoro>) event.getObject();
        //rimuove i lavori deselezionati...
        this.selectedPraticaLavoriStandard.get(this.selectedCategoriaDialog).removeIf((Lavoripratichestandard i) -> {
            if (!selezionati.stream().noneMatch((l) -> (l.getIdTipoLavoro().equals(i.getId())))) {
                return false;
            }
            VeicoliSearchView.this.lavoriManagerBean.cancellaLavoroStandard(i);
            return true;
        });
        //aggiunge i nuovi selezionati
        selezionati.forEach((Tipolavoro i) -> {
            Lavoripratichestandard found = null;
            for (Lavoripratichestandard l : VeicoliSearchView.this.selectedPraticaLavoriStandard.get(VeicoliSearchView.this.selectedCategoriaDialog)) {
                if (l.getTipolavoro().getIdTipoLavoro().equals(i.getIdTipoLavoro())) {
                    found = l;
                    break;
                }
            }
            if (found == null) {
                found = VeicoliSearchView.this.lavoriManagerBean.creaNuovoLavoroStandard(VeicoliSearchView.this.selectedPratica, i);
                VeicoliSearchView.this.selectedPraticaLavoriStandard.get(VeicoliSearchView.this.selectedCategoriaDialog).add(found);
            }
        });
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Lavori aggiornati", "Categoria ".concat(this.selectedCategoriaDialog.getNome()).concat(" aggiornata con successo!"));
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onMaterialePraticaEdit(SelectEvent event) {
        this.materialiManagerBean.editQty(this.selectedMaterialePraticaDialog);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Materiale modificato", "Id:" + this.selectedMaterialePraticaDialog.getArticolo1().getDescrizione());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onMaterialePraticaAdd(SelectEvent event) {
        List<String> selezionati = (List<String>) event.getObject();
        //rimuove tutti quelli che non fanno parte della selezione
        this.selectedMaterialePratica.removeIf(new Predicate<Materialepratica>() {
            @Override
            public boolean test(Materialepratica i) {
                if (!selezionati.stream().noneMatch(
                        (art) -> (art.equals(i.getArticolo1().getDescrizione())))) {
                    return false;
                } //se c'è non rimuovere niente.
                VeicoliSearchView.this.materialiManagerBean.removeMateriale(i);
                return true;
            }
        });
        selezionati.forEach((String art) -> {
            Articolo temp = null;
            for (Materialepratica mat : this.selectedMaterialePratica) {
                if (mat.getArticolo1().getDescrizione().equals(art)) {
                    temp = mat.getArticolo1();
                    break;
                }
            }
            if (temp == null) {
                //aggiungi materiale
                Materialepratica newMat = new Materialepratica();
                MaterialepraticaPK newMatPK = new MaterialepraticaPK();
                Articolo articolo = this.materialiManagerBean.getArticoloByDescrizione(art);
                newMatPK.setArticolo(articolo.getIdArticolo());
                newMatPK.setPratica(this.selectedPratica.getIdPratica());
                newMat.setArticolo1(articolo);
                newMat.setMaterialepraticaPK(newMatPK);
                newMat.setPratica1(this.selectedPratica);
                newMat.setQuantitaConsumata(BigDecimal.ONE);
                newMat = this.materialiManagerBean.create(newMat);
                this.selectedMaterialePratica.add(newMat);
            }
        });
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Materiali associati aggiornati", "Aggiornamento eseguito con successo!");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onVeicoliChosen(SelectEvent event) {
        Veicolo car = (Veicolo) event.getObject();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Autoarticolato selezionato", "Id:" + car.getMarca());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void setSelectedCar(Veicolo selectedCar) {
        this.selectedCar = selectedCar;
    }

    public void closeDialogAddLavoriStandardMenu(List<Tipolavoro> selezionati) {
        RequestContext.getCurrentInstance().closeDialog(selezionati);
    }

    public void closeDialogMenu() {
        RequestContext.getCurrentInstance().closeDialog(null);
    }

    public void closeMenuMaterialeDialog() {
        RequestContext.getCurrentInstance().closeDialog(null);
    }

    public void selectCarFromDialog(Veicolo veicolo) {
        this.selectedCar = veicolo;
        this.pratiche = this.praticheService.findPraticaByVeicolo(selectedCar, PraticheUtils.MAX_PRATICHE_ESTRAIBILI);
        this.selectedPratica = this.pratiche.isEmpty() ? null : this.pratiche.get(0);
        if (this.selectedPraticaLavoriStandard == null) {
            this.selectedPraticaLavoriStandard = new HashMap<>();
        } else {
            this.selectedPraticaLavoriStandard.clear();
        }
        if (this.selectedPraticaLavoriCustom == null) {
            this.selectedPraticaLavoriCustom = new HashMap<>();
        } else {
            this.selectedPraticaLavoriCustom.clear();
        }
        if (this.selectedMaterialePratica == null) {
            this.selectedMaterialePratica = new ArrayList<>();
        } else {
            this.selectedMaterialePratica.clear();
        }
        /**
         * ***************** MANCA DI REINIZIALIZZARE ANCHE IL MATERIALE
         * ASSOCIATO ***************
         */
        if (!this.pratiche.isEmpty()) {
            for (Categoriatipolavoro cat : this.lavoriManagerBean.getCategorie()) {
                this.selectedPraticaLavoriStandard.put(cat, this.lavoriManagerBean.getLavoriStandardByCategoria(cat, selectedPratica));
                this.selectedPraticaLavoriCustom.put(cat, this.lavoriManagerBean.getLavoriCustomByCategoria(cat, selectedPratica));
            }
            this.selectedMaterialePratica = this.materialiManagerBean.getMaterialePratica(selectedPratica);
        }
        //closing dialog trigger the <p:ajax> event dialogReturn and its 
        //listener 'onVeicoliChosen'        
        RequestContext.getCurrentInstance().closeDialog(veicolo);
    }

    private void nullifyAllBlankField(Pratica pratica) {
        if (pratica.getArrivo() != null && pratica.getArrivo().trim().isEmpty()) {
            pratica.setArrivo(null);
        }
        if (pratica.getUscita() != null && pratica.getUscita().trim().isEmpty()) {
            pratica.setUscita(null);
        }
        if (pratica.getNumeroFattura() != null && pratica.getNumeroFattura().trim().isEmpty()) {
            pratica.setNumeroFattura(null);
        }
        if (pratica.getLavoriSegnalati() != null && pratica.getLavoriSegnalati().trim().isEmpty()) {
            pratica.setLavoriSegnalati(null);
        }
        if (pratica.getStatoVeicoloArrivo() != null && pratica.getStatoVeicoloArrivo().trim().isEmpty()) {
            pratica.setStatoVeicoloArrivo(null);
        }

    }
}
