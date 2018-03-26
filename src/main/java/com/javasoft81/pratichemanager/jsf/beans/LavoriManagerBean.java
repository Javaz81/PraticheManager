/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.jsf.beans;

import com.javasoft81.pratichemanager.entities.Categoriatipolavoro;
import com.javasoft81.pratichemanager.entities.Tipolavoro;
import com.javasoft81.pratichemanager.entities.beans.CategoriatipolavoroFacade;
import com.javasoft81.pratichemanager.entities.beans.TipolavoroFacade;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 * ****************     Application Scoped!!!
 * @author andrea
 */
public class LavoriManagerBean implements Serializable{

    @EJB
    private CategoriatipolavoroFacade categoriaService;
    @EJB
    private TipolavoroFacade tipoLavoroService;

    private List<Tipolavoro> lavori;
    private List<Categoriatipolavoro> categorie;
    
    private AtomicBoolean loadingBusy;
    
    public LavoriManagerBean() {
    }
    
    @PostConstruct
    public void init(){
        this.categorie = (List<Categoriatipolavoro>) categoriaService.findAll();
        this.lavori = (List<Tipolavoro>) tipoLavoroService.findAll();
        loadingBusy = new AtomicBoolean(true);
    }
    /**
     * Ricarica tutte le categorie e i lavori standard.
     */
    public synchronized void reloadAll(){
        if(loadingBusy.compareAndSet(true, false)){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(LavoriManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.categorie = (List<Categoriatipolavoro>) categoriaService.findAll();
        this.lavori = (List<Tipolavoro>) tipoLavoroService.findAll();
        loadingBusy.compareAndSet(false, true);
        notify(); 
    }
    
}
