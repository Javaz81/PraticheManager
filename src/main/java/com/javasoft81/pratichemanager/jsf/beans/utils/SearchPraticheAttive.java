/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.jsf.beans.utils;

import com.javasoft81.pratichemanager.entities.Pratica;
import com.javasoft81.pratichemanager.jsf.beans.VeicoliSearchView;
import java.io.Serializable;
import java.util.List;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Andrea Iavazzo
 */
public class SearchPraticheAttive implements Serializable {

    protected List<Pratica> praticheAttive;
    
    protected VeicoliSearchView v;
    /**
     * Creates a new instance of SearchPraticheAttive
     */
    public SearchPraticheAttive() {
        FacesContext context = FacesContext.getCurrentInstance();
        v = context.getApplication().evaluateExpressionGet(context, "#{veicoliSearchView}", VeicoliSearchView.class);
        praticheAttive = v.getPraticheAttive();
    }

    public List<Pratica> getPraticheAttive() {
        return praticheAttive;
    }

    public void setPraticheAttive(List<Pratica> praticheAttive) {
        this.praticheAttive = praticheAttive;
    }
    
    public void selectPraticaAttiva(Pratica pratica){
        RequestContext.getCurrentInstance().closeDialog(pratica);
    }

}
