/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.jsf.beans;

import com.javasoft81.pratichemanager.entities.Appctxvar;
import com.javasoft81.pratichemanager.entities.beans.AppctxvarFacade;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 * Questa classe offre le utilità di base per tutta l'applicazione.
 *
 * @author andrea
 */
public class AppContextBean implements Serializable {
    
    private static final String TARGA_SPECIAL_PREFIX = "T_";
    
    @EJB
    private AppctxvarFacade appctxvarService;
    
    private Appctxvar appVars;
    
    private final ReentrantLock lock = new ReentrantLock();
    
    /**
     * Creates a new instance of AppContextBean
     */
    public AppContextBean() {
    }
    
    @PostConstruct
    public void init(){
        appVars = this.appctxvarService.findAll().get(0);
    }
    /**
     * Genera una nuova targa fittizia. E' progressiva.
     * @return Una stringa che comincia per 'T_' ed è concatenata con un numero
     * progressivo.
     */
    public String getNextTarga(){
        String s = null;
        int c;
        try{
            lock.lock();            
            c = appVars.getTargaCounter();
            s = TARGA_SPECIAL_PREFIX.concat(Integer.toString(c));
            appVars.setTargaCounter(c+1);
            this.appctxvarService.edit(appVars);
        }finally{
            lock.unlock();
        }
        return s;
    }
    /**
     * Genera una nuovo codice fittizia da utilizzare in arrivo o uscita. 
     * E' progressivo.
     * @return Una stringa che identifica il prossimo codice con un numero
     * progressivo.
     */
    public String getNextCodice(){
        String s = null;
        int t;
        try{
            lock.lock();            
            t = appVars.getCodiceCounter();
            s = Integer.toString(t);
            appVars.setTargaCounter(t+1);
            this.appctxvarService.edit(appVars);
        }finally{
            lock.unlock();
        }
        return s;
    }
    
    /**
     * Fornisce la targa attuale senza incrementarne il progressivo.
     * @return Una string con prefisso 'T_' con l'attuale progressivo.
     * Non incrementa.
     */
    public String lookCurrentTarga(){
        String s = null;
        try{
            lock.lock();
            s = AppContextBean.TARGA_SPECIAL_PREFIX.concat(Integer.toString(appVars.getTargaCounter()));
        }finally{
            lock.unlock();
        }
        return s;
    }
    
    /**
     * Fornisce il codice attuale senza incrementarne il progressivo.
     * @return Una stringa del codice attuale con il relativo progressivo.
     * Non incrementa.
     */
    public String lookCurrentCodice(){
        String s = null;
        try{
            lock.lock();
            s = Integer.toString(appVars.getCodiceCounter());
        }finally{
            lock.unlock();
        }
        return s;
    }
    
    /**
     * Imposta il prossimo codice di arrivo/uscita per questa applicazione.
     * @param codiceIniziale Il codice iniziale da impostare.
     */
    public void setInitialCodice(int codiceIniziale){
        try{
            lock.lock();
            appVars.setCodiceCounter(codiceIniziale);
            this.appctxvarService.edit(appVars);
        }finally{
            lock.unlock();
        }
    }
}
