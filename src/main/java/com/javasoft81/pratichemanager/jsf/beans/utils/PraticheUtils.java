/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.jsf.beans.utils;

import com.javasoft81.pratichemanager.entities.Pratica;
import com.javasoft81.pratichemanager.entities.Veicolo;
import com.javasoft81.pratichemanager.entities.beans.PraticaFacade;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 * @author andrea
 */
public class PraticheUtils implements Serializable {
    
    public static final int MAX_PRATICHE_ESTRAIBILI=200;
    
    private PraticaFacade service;
    
    private Pratica selected;
    
    private List<Pratica> list;    
    
    private Veicolo currentCar;
    
    private Long currentCarPratiche;
    
    public PraticheUtils() {
    
    }
    /**
     * Fornisce la data di arrivo della pratica in formato dd-MM-yyyy.
     * @param p la pratica in questione
     * @return una stringa che rappresenta la data dell'arrivo della pratica in formato italiano.
     */
    public static String getDate(Pratica p){        
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
        cal.getTime();
        SimpleDateFormat sdf =new SimpleDateFormat("dd-MMM-yyyy"); 
        return sdf.format(p.getDataArrivo());        
    }
    public List<String> getStatiArrivo(){
        return Pratica.STATO_ARRIVO_VEICOLO;
    }
    public void loadPratiche(Veicolo veicolo){
        this.currentCar = veicolo;
        //Numero di pratiche totali associate
        this.currentCarPratiche =  this.service.countPraticheByVeicolo(this.currentCar);
        //Carica le ultime 100 pratiche
        this.list = (List<Pratica>) this.service.findPraticaByVeicolo(this.currentCar, MAX_PRATICHE_ESTRAIBILI);
        this.selected = this.list.isEmpty()?null:this.list.get(0);
    }
    
    public Pratica aggiornaPratica(Pratica p){
        return this.service.refreshPratica(p);
    }
    public void saveSelectedPratica(){
        this.service.edit(selected);
    }
    public List<Pratica> getList() {
        return list;
    }

    public void setList(List<Pratica> list) {
        this.list = list;
    }

    public Long getCurrentCarPratiche() {
        return currentCarPratiche;
    }

    public void setCurrentCarPratiche(Long currentCarPratiche) {
        this.currentCarPratiche = currentCarPratiche;
    }

    public Pratica getSelected() {
        return selected;
    }

    public void setSelected(Pratica selected) {
        this.selected = selected;
    }

    public Veicolo getCurrentCar() {
        return currentCar;
    }

    public void setCurrentCar(Veicolo currentCar) {
        this.currentCar = currentCar;
    }

    public PraticaFacade getService() {
        return service;
    }

    public void setService(PraticaFacade service) {
        this.service = service;
    }
    
    
}
