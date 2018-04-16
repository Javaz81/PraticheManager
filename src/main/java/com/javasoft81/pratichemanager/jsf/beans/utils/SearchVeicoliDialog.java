/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.jsf.beans.utils;

import com.javasoft81.pratichemanager.entities.Cliente;
import com.javasoft81.pratichemanager.entities.Veicolo;
import com.javasoft81.pratichemanager.jsf.beans.VeicoliSearchView;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author andrea
 */
public class SearchVeicoliDialog implements Serializable{
    
    private List<Veicolo> filteredVeicoli;
    
    private VeicoliSearchView v;
    
    private List<Veicolo> veicoli;


    /**
     * Creates a new instance of SearchVeicoliDialog
     */
    public SearchVeicoliDialog() {
        
    }
    
    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();     
        v = context.getApplication().evaluateExpressionGet(context, "#{veicoliSearchView}", VeicoliSearchView.class);
        veicoli = v.getVeicoli();
    }

    public List<Veicolo> getFilteredVeicoli() {
        return filteredVeicoli;
    }

    public void setFilteredVeicoli(List<Veicolo> filteredVeicoli) {
        this.filteredVeicoli = filteredVeicoli;
    }    

    public List<Veicolo> getVeicoli() {
        return veicoli;
    }

    public void setVeicoli(List<Veicolo> veicoli) {
        this.veicoli = veicoli;
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
    
    
    public void selectCarFromDialog(Veicolo veicolo){
        RequestContext.getCurrentInstance().closeDialog(veicolo);
    }
    
    
}
