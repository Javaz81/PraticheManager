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
import com.javasoft81.pratichemanager.entities.beans.CategoriatipolavoroFacade;
import com.javasoft81.pratichemanager.entities.beans.ClienteFacade;
import com.javasoft81.pratichemanager.entities.beans.PraticaFacade;
import com.javasoft81.pratichemanager.entities.beans.VeicoloFacade;
import com.javasoft81.pratichemanager.jsf.beans.utils.VeicoloNewDialog;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

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
    private ClienteFacade clienteService;

    @EJB
    private VeicoloFacade veicoliService;

    @EJB
    private PraticaFacade praticheService;

    @EJB
    private CategoriatipolavoroFacade categoriaService;

    //private PraticheUtils currentPratiche;
    private List<Veicolo> veicoli;

    private List<Pratica> pratiche;

    private List<Cliente> clienti;

    private List<Cliente> filteredListChangingCliente;

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

    private StreamedContent printedPratica;
    private final int MAX_ITEMS_TEMPLATE = 10;

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
        clienti = clienteService.findAll();
    }

    public List<Cliente> getClienti() {
        clienti = clienteService.findAll();
        return clienti;
    }

    public void setClienti(List<Cliente> clienti) {
        this.clienti = clienti;
    }

    public List<Cliente> getFilteredListChangingCliente() {
        return filteredListChangingCliente;
    }

    public void setFilteredListChangingCliente(List<Cliente> filteredListChangingCliente) {
        this.filteredListChangingCliente = filteredListChangingCliente;
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
        veicoli = this.veicoliService.findAll();
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

    /**
     * Stampa una stringa vuota se l'oggetto è nullo.
     *
     * @param o l'oggetto da stampare
     * @return il metodo toString() su o se non null, stringa vuota altrimenti.
     */
    String eStr(Object o) {
        if (o != null) {
            return o.toString();
        } else {
            return ("");
        }
    }

    public StreamedContent getPrintedPratica() {
        try {
            //ripulisci tutti i file piu vecchi di 2 giorni nella cartella predefinita di stampa
            // . . . 
            //genera il file e fallo scaricare...
            XWPFDocument doc = new XWPFDocument(OPCPackage.open(
                    System.getProperty("user.home").concat(System.getProperty("file.separator")).concat("TEMPLATES").concat(System.getProperty("file.separator")).concat("SUPER_ASSISSTENZA _TEMPLATE.docx"))
            );
            //marker dei dati generali della pratica e del veicolo/cliente...
            for (XWPFTable t : doc.getTables()) {
                for (XWPFTableRow row : t.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        this.writeTemplatePraticaMarker(cell);
                    }
                }
            }
            //marker per i materiali...
            for (XWPFTable t : doc.getTables()) {
                int i = 1;
                boolean find = false;
                for (XWPFTableRow row : t.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        find = this.writeTemplateMaterialiMarker(cell, i, this.selectedMaterialePratica);
                        if (!find) {
                            break;
                        }
                    }
                    if (find) {
                        i = i + 1;
                    }
                }
            }
            //marker per le categorie
            List<Categoriatipolavoro> categorie = this.categoriaService.findAll();
            int i = 1;
            for (XWPFParagraph p : doc.getParagraphs()) {
                List<XWPFRun> runs = p.getRuns();
                if (runs != null) {
                    for (XWPFRun r : runs) {
                        if (this.writeTemplateCategoriaMarker(r, i, categorie)) {
                            i = i + 1;
                        }
                    }
                }
            }
            // ********************markers per i lavori standard ********************************
            int rowind;
            int indiceGeneraleLavori = 1;
            int tempRowInd = indiceGeneraleLavori;
            Lavoripratichestandard lps = null;
            XWPFTable lastTable = null;
            boolean rowFound = false;
            //Trova le tabelle lavori
            List<XWPFTable> tblLavori = findLavoriTables(doc);
            indiceGeneraleLavori = 1;
            for (Categoriatipolavoro c : categorie) {
                List<Lavoripratichestandard> lavoriCat
                        = this.selectedPraticaLavoriStandard.get(c)
                                .subList(0,
                                        this.selectedPraticaLavoriStandard.get(c).size() > MAX_ITEMS_TEMPLATE
                                        ? MAX_ITEMS_TEMPLATE
                                        : this.selectedPraticaLavoriStandard.get(c).size()
                                );
                for (XWPFTable t : tblLavori) {
                    rowind = 1;
                    rowFound = false;
                    for (XWPFTableRow r : t.getRows()) {
                        if (!r.getCell(0).getText().startsWith("Cod.")) {
                            lastTable = t;
                            if (rowind - 1 >= lavoriCat.size()) {
                                lps = null;
                            } else {
                                lps = lavoriCat.get(rowind - 1);
                                rowFound = this.writeTemplateLavoriStandardMarker(r.getCell(0), tempRowInd, lps);
                                rowFound = this.writeTemplateLavoriStandardMarker(r.getCell(1), tempRowInd, lps);
                            }
                            tempRowInd++;
                            if (rowFound) {
                                rowind++;
                            }
                        }
                    }
                    indiceGeneraleLavori += 10;
                    tempRowInd = indiceGeneraleLavori;
                    break;
                }
                if (lastTable != null) {
                    tblLavori.remove(lastTable);
                }
            }

            // ********************markers per i lavori custom ********************************
            indiceGeneraleLavori = 1;
            tempRowInd = indiceGeneraleLavori;
            Lavoripratichecustom lpc = null;
            lastTable = null;
            rowFound = false;
            //Trova le tabelle lavori
            tblLavori = findLavoriTables(doc);
            for (Categoriatipolavoro c : categorie) {
                List<Lavoripratichecustom> lavoriCat
                        = this.selectedPraticaLavoriCustom.get(c)
                                .subList(0,
                                        this.selectedPraticaLavoriCustom.get(c).size() > MAX_ITEMS_TEMPLATE
                                        ? MAX_ITEMS_TEMPLATE
                                        : this.selectedPraticaLavoriCustom.get(c).size()
                                );
                for (XWPFTable t : tblLavori) {
                    rowind = 1;
                    rowFound = false;
                    for (XWPFTableRow r : t.getRows()) {
                        if (!r.getCell(0).getText().startsWith("Cod.")) {
                            lastTable = t;
                            if (rowind - 1 >= lavoriCat.size()) {
                                lpc = null;
                            } else {
                                lpc = lavoriCat.get(rowind - 1);
                                rowFound = this.writeTemplateLavoriCustomMarker(r.getCell(0), tempRowInd, lpc);
                                rowFound = this.writeTemplateLavoriCustomMarker(r.getCell(1), tempRowInd, lpc);
                            }
                            tempRowInd++;
                            if (rowFound) {
                                rowind++;
                            }
                        }
                    }
                    indiceGeneraleLavori += 10;
                    tempRowInd = indiceGeneraleLavori;
                    break;
                }
                if (lastTable != null) {
                    tblLavori.remove(lastTable);
                }
            }
            // ********************** marker di default rimasti da ripulire *****************************
            indiceGeneraleLavori = 1;
            tempRowInd = indiceGeneraleLavori;
            lastTable = null;
            //Trova le tabelle lavori
            tblLavori = findLavoriTables(doc);
            for (Categoriatipolavoro c : categorie) {
                for (XWPFTable t : tblLavori) {
                    for (XWPFTableRow r : t.getRows()) {
                        if (!r.getCell(0).getText().startsWith("Cod.")) {
                            lastTable = t;
                            this.writeTemplateDefaultMarker(r.getCell(0), tempRowInd);
                            this.writeTemplateDefaultMarker(r.getCell(1), tempRowInd);
                            tempRowInd++;
                        }
                    }
                    indiceGeneraleLavori += 10;
                    tempRowInd = indiceGeneraleLavori;
                }
            }
           
            //downloading...
            doc.write(
                    new FileOutputStream(
                            eStr(System.getProperty("user.home").concat(System.getProperty("file.separator")).concat("TEMPLATES").concat(System.getProperty("file.separator")).concat(eStr(this.selectedPratica.getIdPratica())).concat("_").concat("_pratica.docx")
                            )
                    )
            );
            InputStream stream = new FileInputStream(System.getProperty("user.home").concat(System.getProperty("file.separator")).concat("TEMPLATES").concat(System.getProperty("file.separator")).concat(eStr(this.selectedPratica.getIdPratica())).concat("_").concat("_pratica.docx"));
            return new DefaultStreamedContent(stream, "application/vnd.openxmlformats-officedocument.wordprocessingml.document", eStr(this.selectedPratica.getIdPratica()).concat("_pratica.docx"));
        } catch (Exception ex) {
            Logger.getLogger(VeicoliSearchView.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public boolean filterVeicoloByCliente(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        if (value == null) {
            return false;
        }
        String nome = ((Cliente) value).getNome() == null ? "" : ((Cliente) value).getNome();
        String cognome = ((Cliente) value).getCognome() == null ? "" : ((Cliente) value).getCognome();
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

    public String format_IT_DateTime(Date d) {
        return PraticheUtils.getFormattedITDateTime(d);
    }

    public String format_IT_Boolean(Boolean b) {
        return PraticheUtils.getFormattedBoolean(b);
    }

    public void changeCliente(Cliente cliente) {
        String messaggio;
        if (this.selectedPratica == null) {
            this.selectedCar.setCliente(cliente);
            this.veicoliService.edit(selectedCar);
            messaggio = "Cliente del Veicolo cambiato";
        } else {
            this.selectedPratica.setClienteidCliente(cliente);
            this.praticheService.edit(selectedPratica);
            messaggio = "Cliente della Pratica cambiato";
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, messaggio, "Creazione avvenuta");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void newPratica() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Pratica creata con successo", "Creazione avvenuta");
        FacesContext.getCurrentInstance().addMessage(null, message);
        Pratica p = new Pratica();
        if (this.selectedPratica == null) {
            p.setVeicolo(selectedCar);
        } else {
            p.setVeicolo(this.selectedPratica.getVeicolo());
        }
        //seleziona l'attuale cliente del veicolo.
        p.setClienteidCliente(selectedCar.getCliente());
        p.setDataArrivo(Calendar.getInstance().getTime());
        this.praticheService.create(p);
        if (this.pratiche != null) {
            this.pratiche.clear();
        }
        this.pratiche = this.praticheService.findPraticaByVeicolo(selectedCar, PraticheUtils.MAX_PRATICHE_ESTRAIBILI);
        this.selectedMaterialePratica = new ArrayList<>();
        this.selectedPraticaLavoriCustom = new HashMap<>();
        this.selectedPraticaLavoriStandard = new HashMap<>();
        for (Categoriatipolavoro c : this.lavoriManagerBean.getCategorie()) {
            this.selectedPraticaLavoriCustom.put(c, new ArrayList());
            this.selectedPraticaLavoriStandard.put(c, new ArrayList<>());
        }
        this.selectedPratica = this.pratiche.get(0);
        this.activeIndexTab = 0;
    }

    public void deletePratica() {
        if (this.pratiche == null || this.pratiche.isEmpty()) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRORE!", "Nessuna pratica da cancellare");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return;
        }
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

    public void createVeicolo() {
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("draggable", true);
        options.put("modal", true);
        options.put("contentWidth", 900);
        RequestContext.getCurrentInstance().openDialog("gestione_veicolo/menuNewVeicolo", options, null);
    }

    public void onVeicoloCreated(SelectEvent event) {
        HashMap<String, Object> response = (HashMap<String, Object>) event.getObject();
        Veicolo v = (Veicolo) response.get(VeicoloNewDialog.ResponseParameter.VEICOLO.toString());
        Cliente c = (Cliente) response.get(VeicoloNewDialog.ResponseParameter.CLIENTE_ASSEGNATO.toString());
        if (c == null) {
            c = (Cliente) response.get(VeicoloNewDialog.ResponseParameter.CLIENTE_MODIFICATO.toString());
            if (c == null) {
                c = (Cliente) response.get(VeicoloNewDialog.ResponseParameter.CLIENTE_NUOVO.toString());
                if (c == null) {
                    throw new IllegalArgumentException();
                } else {
                    try {
                        this.clienteService.create(c);
                    } catch (EJBException ex) {
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRORE", "Il cliente è stato registrato con campi non validi.");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                        return;
                    }
                }
            } else {
                try {
                    this.clienteService.edit(c);
                } catch (EJBException ex) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRORE", "Il cliente è stato modificato male, riprovare!");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    return;
                }
            }
        }
        v.setCliente(c);
        try {
            this.veicoliService.create(v);
        } catch (EJBException ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRORE", "La piattaforma/autorarticolato presenta parametri errati, riprovare prego!");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return;
        }
        this.selectedCar = v;
        this.selectedPratica = null;
        this.newPratica();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCESSO", "Veicolo creato e aggiornato");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void chooseVeicoli() {
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("draggable", true);
        options.put("modal", true);
        options.put("contentWidth", 800);
        RequestContext.getCurrentInstance().openDialog("searchVeicoli", options, null);
    }

    public void openEditTemporaneiDialog() {
        if (this.selectedPratica == null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRORE",
                    "I dati temporanei sono associati alla pratica corrente, ma non è presente nessuna pratica selezionata al momento.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return;
        }
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("draggable", true);
        options.put("modal", true);
        options.put("contentWidth", 800);
        RequestContext.getCurrentInstance().openDialog("gestione_veicolo/menuEditTemporanei", options, null);
    }

    public void chooseLavoriStandard(Categoriatipolavoro cat) {
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("draggable", true);
        options.put("modal", true);
        options.put("contentWidth", 900);
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

    public void editDatiVeicolo() {
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("draggable", true);
        options.put("modal", true);
        RequestContext.getCurrentInstance().openDialog("gestione_veicolo/menuEditVeicolo", options, null);
    }

    public void editDatiCliente() {
        if (this.selectedPratica != null) {
            if (this.selectedPratica.getClienteidCliente().getIdCliente().equals(1)) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRORE",
                        "E' vietato modificare il cliente nullo. Assegnane uno già in anagrafica.");
                FacesContext.getCurrentInstance().addMessage(null, message);
                return;
            }
        } else {
            if (this.selectedCar.getCliente().getIdCliente().equals(1)) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRORE",
                        "E' vietato modificare il cliente nullo. Assegnane uno già in anagrafica.");
                FacesContext.getCurrentInstance().addMessage(null, message);
                return;
            }
        }
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("draggable", true);
        options.put("modal", true);
        RequestContext.getCurrentInstance().openDialog("gestione_veicolo/menuEditCliente", options, null);
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
        nullifyAllBlankField(selectedPratica);
        this.praticheService.edit(selectedPratica);
        //Se la pratica ha cambiato data di arrivo è sempre bene rinfrescare 
        //le pratiche
        this.pratiche.clear();
        this.pratiche = this.praticheService.findPraticaByVeicolo(selectedCar, PraticheUtils.MAX_PRATICHE_ESTRAIBILI);
        this.selectedPratica = this.pratiche.get(0);
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
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Pratica aggiornata", "Successo");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onEditTemporanei(SelectEvent event) {
        this.praticheService.edit(this.selectedPratica);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "AGGIORNAMENTO", "Dati temporanei aggiornati con successo.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onEditVeicolo(SelectEvent event) {
        Veicolo retVeicolo = (Veicolo) event.getObject();
        this.veicoliService.edit(retVeicolo);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Pratica aggiornata", "Successo");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onEditCliente(SelectEvent event) {
        Cliente retCliente = (Cliente) event.getObject();
        this.clienteService.edit(retCliente);
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
        this.selectedCar = car;
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

    private void writeTemplatePraticaMarker(XWPFTableCell cell) {
        String text = cell.getText();
        String rep = null;
        if (text != null) {
            if (text.contains("[marca]")) {
                rep = text.replace("[marca]", eStr(this.selectedPratica.getVeicolo().getMarca()));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[modello]")) {
                rep = text.replace("[modello]", eStr(this.selectedPratica.getVeicolo().getModello()));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[anno]")) {
                rep = text.replace("[anno]", eStr(this.selectedPratica.getVeicolo().getAnno()));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[portataMax]")) {
                rep = text.replace("[portataMax]", eStr(this.selectedPratica.getVeicolo().getPortataMax()));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[matricola]")) {
                rep = text.replace("[matricola]", eStr(this.selectedPratica.getVeicolo().getMatricola()));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[tipo]")) {
                rep = text.replace("[tipo]", eStr(this.selectedPratica.getVeicolo().getTipo()));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[targa]")) {
                rep = text.replace("[targa]", eStr(this.selectedPratica.getVeicolo().getTarga()));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[nome]")) {
                rep = text.replace("[nome]", eStr(this.selectedPratica.getClienteidCliente().getNome()));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[cognome]")) {
                rep = text.replace("[cognome]", eStr(this.selectedPratica.getClienteidCliente().getCognome()));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[localita]")) {
                rep = text.replace("[localita]", eStr(this.selectedPratica.getClienteidCliente().getLocalita()));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[cellulare]")) {
                rep = text.replace("[cellulare]", eStr(this.selectedPratica.getClienteidCliente().getCellulare()));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[arrivo]")) {
                rep = text.replace("[arrivo]", eStr(this.selectedPratica.getArrivo()));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[dataArrivo]")) {
                rep = text.replace("[dataArrivo]", eStr(PraticheUtils.getFormattedITDate(this.selectedPratica.getDataArrivo())));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[uscita]")) {
                rep = text.replace("[uscita]", eStr(this.selectedPratica.getUscita()));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[dataUscita]")) {
                rep = text.replace("[dataUscita]", eStr(PraticheUtils.getFormattedITDate(this.selectedPratica.getDataUscita())));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[numFattura]")) {
                rep = text.replace("[numFattura]", eStr(this.selectedPratica.getNumeroFattura()));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[dataFattura]")) {
                rep = text.replace("[dataFattura]", eStr(PraticheUtils.getFormattedITDate(this.selectedPratica.getDataFattura())));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[registroControllo]")) {
                rep = text.replace("[registroControllo]", eStr(PraticheUtils.getFormattedBoolean(this.selectedPratica.getRegistroDiControllo())));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[dataRegistroControllo]")) {
                rep = text.replace("[dataRegistroControllo]", eStr(PraticheUtils.getFormattedITDate(this.selectedPratica.getRegistroDiControlloData())));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[revisione]")) {
                rep = text.replace("[revisione]", eStr(PraticheUtils.getFormattedBoolean(this.selectedPratica.getRevisioneMctc())));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[dataRevisione]")) {
                rep = text.replace("[dataRevisione]", eStr(PraticheUtils.getFormattedITDate(this.selectedPratica.getRevisioneMctcData())));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[preventivoLavori")) {
                rep = text.replace("[preventivoLavori]", eStr(PraticheUtils.getFormattedBoolean(this.selectedPratica.getPreventivoLavori())));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[dataPreventivoLavori]")) {
                rep = text.replace("[dataPreventivoLavori]", eStr(PraticheUtils.getFormattedITDate(this.selectedPratica.getPreventivoLavoriData())));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[dife]")) {
                rep = text.replace("[dife]", eStr(PraticheUtils.getFormattedBoolean(this.selectedPratica.getDife())));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[dataIntervento]")) {
                rep = text.replace("[dataIntervento]", eStr(PraticheUtils.getFormattedITDate(this.selectedPratica.getInterventoData())));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[km]")) {
                rep = text.replace("[km]", eStr(this.selectedPratica.getKilometraggio()));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[ore]")) {
                rep = text.replace("[ore]", eStr(this.selectedPratica.getOre()));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[tempoPartenzaSede]")) {
                rep = text.replace("[tempoPartenzaSede]", eStr(PraticheUtils.getFormattedITDateTime(this.selectedPratica.getTempoPartenzaSede())));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[tempoInizioLavoro]")) {
                rep = text.replace("[tempoInizioLavoro]", eStr(PraticheUtils.getFormattedITDateTime(this.selectedPratica.getTempoInizioLavoro())));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[tempoFineLavoro]")) {
                rep = text.replace("[tempoFineLavoro]", eStr(PraticheUtils.getFormattedITDateTime(this.selectedPratica.getTempoFineLavoro())));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[tempoRientroSede]")) {
                rep = text.replace("[tempoRientroSede]", eStr(PraticheUtils.getFormattedITDateTime(this.selectedPratica.getTempoRientroSede())));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[totKmAndRit]")) {
                rep = text.replace("[totKmAndRit]", eStr(this.selectedPratica.getTotKmAndRit()));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[totOreLavoro]")) {
                rep = text.replace("[totOreLavoro]", eStr(this.selectedPratica.getTotOreLavoro()));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[totOreViaggio]")) {
                rep = text.replace("[totOreViaggio]", eStr(this.selectedPratica.getTotOreViaggio()));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[statoMacchinaArrivo]")) {
                rep = text.replace("[statoMacchinaArrivo]", eStr(this.selectedPratica.getStatoVeicoloArrivo()));
                cell.removeParagraph(0);
                cell.setText(rep);
            } else if (text.contains("[lavoriSegnalati]")) {
                rep = text.replace("[lavoriSegnalati]", eStr(this.selectedPratica.getLavoriSegnalati()));
                cell.removeParagraph(0);
                cell.setText(rep);
            }
        }
    }

    private boolean writeTemplateMaterialiMarker(XWPFTableCell cell, int i, List<Materialepratica> mp) {
        String text = cell.getText();
        String rep = null;
        boolean nulla = true;
        if (mp.size() >= i) {
            nulla = false;
        }
        if (text != null) {
            if (text.contains("[CODMAT" + i + "]")) {
                rep = text.replace("[CODMAT" + i + "]", eStr(nulla ? "" : mp.get(i - 1).getArticolo1().getIdArticolo()));
                cell.removeParagraph(0);
                cell.setText(rep);
                return true;
            } else if (text.contains("[DESC_ARTICOLO" + i + "]")) {
                rep = text.replace("[DESC_ARTICOLO" + i + "]", eStr(nulla ? "" : mp.get(i - 1).getArticolo1().getDescrizione()));
                cell.removeParagraph(0);
                cell.setText(rep);
                return true;
            } else if (text.contains("[QUANTITA" + i + "]")) {
                rep = text.replace("[QUANTITA" + i + "]", eStr(nulla ? "" : mp.get(i - 1).getQuantitaConsumata().setScale(2, BigDecimal.ROUND_UP)));
                cell.removeParagraph(0);
                cell.setText(rep);
                return true;
            }
        }
        return false;
    }

    private boolean writeTemplateCategoriaMarker(XWPFRun run, int i, List<Categoriatipolavoro> cat) {
        String text = run.getText(0);
        String rep = null;
        if (text != null) {
            if (text.contains("[CATEGORIA" + i + "]")) {
                rep = text.replace("[CATEGORIA" + i + "]", eStr(cat.get(i - 1).getNome()));
                run.setText(rep, 0);
                return true;
            }
        }
        return false;
    }

    private boolean writeTemplateLavoriStandardMarker(XWPFTableCell cell, int i, Lavoripratichestandard l) {
        String text = cell.getText();
        System.out.println(text + " e i=" + i);
        String rep = null;
        boolean nulla = false;
        if (l == null) {
            nulla = true;
        }
        if (text != null) {
            if (text.contains("[CODLAV" + i + "]")) {
                rep = text.replace("[CODLAV" + i + "]", eStr(nulla ? "" : l.getTipolavoro().getCodice()));
                XWPFRun run = cell.getParagraphs().get(0).getRuns().get(0);
                run.setText(rep, 0);
                return true;
            } else if (text.contains("[DESC_LAVORO" + i + "]")) {
                rep = text.replace("[DESC_LAVORO" + i + "]", eStr(nulla ? "" : l.getTipolavoro().getDescrizione()));
                XWPFRun run = cell.getParagraphs().get(0).getRuns().get(0);
                run.setText(rep, 0);
                return true;
            }
        }
        return false;
    }

    private boolean writeTemplateLavoriCustomMarker(XWPFTableCell cell, int i, Lavoripratichecustom l) {
        String text = cell.getText();
        String rep = null;
        boolean nulla = false;
        if (l == null) {
            nulla = true;
        }
        if (text != null) {
            if (text.contains("[CODLAV" + i + "]")) {
                rep = text.replace("[CODLAV" + i + "]", "PERS.");
                XWPFRun run = cell.getParagraphs().get(0).getRuns().get(0);
                run.setText(rep, 0);
                return true;
            } else if (text.contains("[DESC_LAVORO" + i + "]")) {
                rep = text.replace("[DESC_LAVORO" + i + "]", eStr(nulla ? "" : l.getDescrizione()));
                XWPFRun run = cell.getParagraphs().get(0).getRuns().get(0);
                run.setText(rep, 0);
                return true;
            }
        }
        return false;
    }

    private boolean writeTemplateDefaultMarker(XWPFTableCell cell, int i) {
        String text = cell.getText();
        String rep = null;
        boolean nulla = false;
        if (text != null) {
            if (text.contains("[CODLAV" + i + "]")) {
                XWPFRun run = cell.getParagraphs().get(0).getRuns().get(0);
                run.setText(" ", 0);
                return true;
            } else if (text.contains("[DESC_LAVORO" + i + "]")) {
                XWPFRun run = cell.getParagraphs().get(0).getRuns().get(0);
                run.setText(" ", 0);
                return true;
            }
        }
        return false;
    }

    private List<XWPFTable> findLavoriTables(XWPFDocument doc) {
        boolean tblFound = false;
        List<XWPFTable> tblLavori = new ArrayList();
        for (XWPFTable t : doc.getTables()) {
            for (XWPFTableRow row : t.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    if (cell.getText().contains("Cod.")) {
                        tblLavori.add(t);
                        tblFound = true;
                        break;
                    }
                }
                if (tblFound) {
                    break;
                }
            }
        }
        return tblLavori;
    }

}
