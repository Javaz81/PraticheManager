/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.jsf.beans.utils;

import com.javasoft81.pratichemanager.entities.Lavoripratichecustom;
import com.javasoft81.pratichemanager.entities.Tipolavoro;
import com.javasoft81.pratichemanager.jsf.beans.VeicoliSearchView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author andrea
 */
public class LavoriCustomDialog implements Serializable {

    private Lavoripratichecustom lavoro;

    private List<Lavoripratichecustom> lavoriCustom;

    private VeicoliSearchView v;

    /**
     * Creates a new instance of LavoriCustomDialog
     */
    public LavoriCustomDialog() {
    }

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        v = context.getApplication().evaluateExpressionGet(context, "#{veicoliSearchView}", VeicoliSearchView.class);
        lavoro = new Lavoripratichecustom();
        lavoro.setPratica(v.getSelectedPratica());
        lavoro.setCategoria(v.getSelectedCategoriaDialog());
        lavoriCustom = new ArrayList<>();

    }

    public void createNew() {
        lavoriCustom.add(lavoro);
        lavoro = new Lavoripratichecustom();
        lavoro.setCategoria(v.getSelectedCategoriaDialog());
        lavoro.setPratica(v.getSelectedPratica());
    }

    public String reinit() {
        lavoro = new Lavoripratichecustom();
        lavoro.setCategoria(v.getSelectedCategoriaDialog());
        lavoro.setPratica(v.getSelectedPratica());
        return null;
    }

    public Lavoripratichecustom getLavoro() {
        return lavoro;
    }

    public void setLavoro(Lavoripratichecustom lavoro) {
        this.lavoro = lavoro;
    }

    public List<Lavoripratichecustom> getLavoriCustom() {
        return lavoriCustom;
    }
    
    public void setLavoriCustom(List<Lavoripratichecustom> lavs) {
        lavoriCustom = lavs;
    }

     public void closeDialog(){
        RequestContext.getCurrentInstance().closeDialog(this.lavoriCustom);
    }
    
    
}
