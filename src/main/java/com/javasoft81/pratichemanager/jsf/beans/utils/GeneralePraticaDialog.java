/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.jsf.beans.utils;

import com.javasoft81.pratichemanager.entities.Pratica;
import com.javasoft81.pratichemanager.jsf.beans.VeicoliSearchView;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author andrea
 */
public class GeneralePraticaDialog implements Serializable{

    private VeicoliSearchView v;
    private Pratica p;
    
    /**
     * Creates a new instance of GeneralePraticaDialog
     */
    public GeneralePraticaDialog() {
    }
    
    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        v = context.getApplication().evaluateExpressionGet(context, "#{veicoliSearchView}", VeicoliSearchView.class);
        p = v.getSelectedPratica();
    }
    
    public void closeDialog(){
        RequestContext.getCurrentInstance().closeDialog(this.p);
    }

    public Pratica getP() {
        return p;
    }

    public void setP(Pratica p) {
        this.p = p;
    }
    
    
    
}
