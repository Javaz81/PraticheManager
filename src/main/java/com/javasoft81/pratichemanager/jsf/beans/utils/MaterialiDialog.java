/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.jsf.beans.utils;

import com.javasoft81.pratichemanager.entities.Articolo;
import com.javasoft81.pratichemanager.entities.beans.ArticoloFacade;
import com.javasoft81.pratichemanager.jsf.beans.VeicoliSearchView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.context.RequestContext;

/**
 *
 * @author andrea
 */
public class MaterialiDialog implements Serializable{

    @EJB
    private ArticoloFacade articoloService;
    
    private VeicoliSearchView veicoliSearchView;
    
    private List<Articolo> materiali;
    
    private List<Articolo> selectedMateriale;
    /**
     * Creates a new instance of MaterialiDialog
     */
    public MaterialiDialog() {        
    }
    
    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();     
        veicoliSearchView = context.getApplication().evaluateExpressionGet(context, "#{veicoliSearchView}", VeicoliSearchView.class);
        selectedMateriale = new ArrayList<Articolo>();
        veicoliSearchView.getSelectedMaterialePratica().forEach(i->{
            selectedMateriale.add(i.getArticolo1());
        });
        materiali = articoloService.findAll();
    }

    public List<Articolo> getMateriali() {
        return materiali;
    }

    public void setMateriali(List<Articolo> materiali) {
        this.materiali = materiali;
    }

    public List<Articolo> getSelectedMateriale() {
        return selectedMateriale;
    }

    public void setSelectedMateriale(List<Articolo> selectedMateriale) {
        this.selectedMateriale = selectedMateriale;
    }
    public void closeMaterialiDialog(){
         RequestContext.getCurrentInstance().closeDialog(this.selectedMateriale);
    }
    
    @FacesConverter(forClass = Articolo.class)
    public static class MaterialiDialogConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MaterialiDialog diagView;
            FacesContext context = FacesContext.getCurrentInstance();     
            diagView = context.getApplication().evaluateExpressionGet(context, "#{materialiDialog}", MaterialiDialog.class);
            return diagView.articoloService.findByDescrizione(value);
           
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Articolo) {
                Articolo o = (Articolo) object;
                return o.getDescrizione();
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Articolo.class.getName()});
                return null;
            }
        }

    }
}
