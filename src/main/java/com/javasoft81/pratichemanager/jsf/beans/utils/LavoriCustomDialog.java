/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.jsf.beans.utils;

import com.javasoft81.pratichemanager.entities.Lavoripratichecustom;
import com.javasoft81.pratichemanager.jsf.beans.VeicoliSearchView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author andrea
 */
public class LavoriCustomDialog implements Serializable {
    
    private class CustomList extends ArrayList<Lavoripratichecustom> {
        
        public CustomList(){  
            super();                        
        }
        
        public void setList(List<Lavoripratichecustom> arr){
            super.clear();
            super.addAll(arr);
        }
        
        @Override
        public boolean add(Lavoripratichecustom e) {
            Iterator<Lavoripratichecustom> it = this.iterator();            
            while(it.hasNext()) {
                 if(it.next().getDescrizione().equals(e.getDescrizione()))
                    return false;                 
            }           
            return super.add(e);
        }
    }

    private Lavoripratichecustom lavoro;

    private CustomList lavoriCustom;

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
        lavoriCustom = new CustomList();

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
        lavoriCustom.setList(lavs);
    }

     public void closeDialog(){
        RequestContext.getCurrentInstance().closeDialog(this.lavoriCustom);
    }
    
    
}
