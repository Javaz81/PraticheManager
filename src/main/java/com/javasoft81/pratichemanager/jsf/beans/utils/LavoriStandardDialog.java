/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.jsf.beans.utils;

import com.javasoft81.pratichemanager.entities.Lavoripratichestandard;
import com.javasoft81.pratichemanager.entities.Tipolavoro;
import com.javasoft81.pratichemanager.jsf.beans.VeicoliSearchView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

/**
 *
 * @author andrea
 */
public class LavoriStandardDialog implements Serializable {

    private HashMap<Tipolavoro,Boolean> valori;
    private List<Boolean> componentValues;
    
    private List<Tipolavoro> selezioni;
    
    private List<Lavoripratichestandard> selezionati;

    private VeicoliSearchView veicoliSearchView;
    
    public LavoriStandardDialog() {
    }

    @PostConstruct
    public void init() {
        //Get caller bean       
        FacesContext context = FacesContext.getCurrentInstance();     
        veicoliSearchView = context.getApplication().evaluateExpressionGet(context, "#{veicoliSearchView}", VeicoliSearchView.class);
        selezioni = new ArrayList<>();
        selezionati = new ArrayList<>();        
        selezioni.addAll(veicoliSearchView.getLavoriManagerBean().getLavoriCategoria(veicoliSearchView.getSelectedCategoriaDialog()));
        selezionati.addAll(veicoliSearchView.getSelectedPraticaLavoriStandard().get(veicoliSearchView.getSelectedCategoriaDialog()));
        valori = new HashMap<>();
        componentValues = new ArrayList<>(selezioni.size());
        selezioni.forEach((Tipolavoro i) -> {
            boolean checked = false;
            valori.put(i, checked);
            for(Lavoripratichestandard lav : selezionati){
                if(lav.getTipolavoro().getIdTipoLavoro().equals(i.getIdTipoLavoro())){
                    checked=true;
                    break;
                }
            }
            componentValues.add(checked);
            valori.put(i, checked);
        });
    }
    
    
    public List<Boolean> getComponentValues() {
        return componentValues;
    }

    public void setComponentValues(List<Boolean> componentValues) {
        this.componentValues = componentValues;
    }

    public HashMap<Tipolavoro, Boolean> getValori() {
        return valori;
    }

    public void setValori(HashMap<Tipolavoro, Boolean> valori) {
        this.valori = valori;
    }

    public List<Tipolavoro> getSelezioni() {
        return selezioni;
    }

    public void setSelezioni(List<Tipolavoro> selezioni) {
        this.selezioni = selezioni;
    }

    public List<Lavoripratichestandard> getSelezionati() {
        return selezionati;
    }

    public void setSelezionati(List<Lavoripratichestandard> selezionati) {
        this.selezionati = selezionati;
    }

    public VeicoliSearchView getVeicoliSearchView() {
        return veicoliSearchView;
    }

    public void setVeicoliSearchView(VeicoliSearchView veicoliSearchView) {
        this.veicoliSearchView = veicoliSearchView;
    }
    
    
    
}
