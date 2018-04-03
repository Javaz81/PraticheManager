/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.jsf.beans;

import com.javasoft81.pratichemanager.entities.Categoriatipolavoro;
import com.javasoft81.pratichemanager.entities.Lavoripratichecustom;
import com.javasoft81.pratichemanager.entities.Lavoripratichestandard;
import com.javasoft81.pratichemanager.entities.Pratica;
import com.javasoft81.pratichemanager.entities.Tipolavoro;
import com.javasoft81.pratichemanager.entities.beans.CategoriatipolavoroFacade;
import com.javasoft81.pratichemanager.entities.beans.LavoripratichecustomFacade;
import com.javasoft81.pratichemanager.entities.beans.LavoripratichestandardFacade;
import com.javasoft81.pratichemanager.entities.beans.TipolavoroFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 * **************** Application Scoped!!!
 *
 * @author andrea
 */
public class LavoriManagerBean implements Serializable {

    @EJB
    private CategoriatipolavoroFacade categoriaService;
    
    @EJB
    private TipolavoroFacade tipoLavoroService;

    @EJB
    private LavoripratichecustomFacade lavoriCustomService;

    @EJB
    private LavoripratichestandardFacade lavoriStandardService;

    private AtomicBoolean loadingBusy;

    private List<Categoriatipolavoro> categorie;

    private HashMap<Integer, String> categorieDesc;

    private HashMap<Integer, String> lavoriDesc;

    private List<Tipolavoro> lavori;

    private HashMap<Categoriatipolavoro, List<Tipolavoro>> categoriaLavori;

    public LavoriManagerBean() {
    }

    @PostConstruct
    public void init() {
        this.categorie = (List<Categoriatipolavoro>) categoriaService.findAll();
        this.lavori = (List<Tipolavoro>) tipoLavoroService.findAll();
        this.categorieDesc = new HashMap(this.categorie.size());
        this.lavoriDesc = new HashMap(this.lavori.size());
        this.categoriaLavori = new HashMap<>();
        for (Categoriatipolavoro c : this.categorie) {
            this.categorieDesc.put(c.getIdCategoriaTipoLavoro(), c.getNome());
            List<Tipolavoro> lc = new ArrayList<>();
            for (Tipolavoro tl : this.lavori) {
                if (tl.getCategoria().equals(c)) {
                    lc.add(tl);
                }
            }
            this.categoriaLavori.put(c, lc);
        }
        for (Tipolavoro t : this.getLavori()) {
            this.lavoriDesc.put(t.getIdTipoLavoro(), "[".concat(t.getCodice()).concat("] - ").concat(t.getDescrizione()));
        }

        loadingBusy = new AtomicBoolean(true);
    }

    /**
     * Ricarica tutte le categorie e i lavori standard.
     */
    public synchronized void reloadAll() {
        if (loadingBusy.compareAndSet(true, false)) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(LavoriManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.categorie = (List<Categoriatipolavoro>) categoriaService.findAll();
        this.lavori = (List<Tipolavoro>) tipoLavoroService.findAll();
        this.lavoriDesc.clear();
        this.categorieDesc.clear();
        this.categoriaLavori.clear();
        for (Categoriatipolavoro c : this.categorie) {
            this.categorieDesc.put(c.getIdCategoriaTipoLavoro(), c.getNome());
            List<Tipolavoro> lc = new ArrayList<>();
            for (Tipolavoro tl : this.lavori) {
                if (tl.getCategoria().equals(c)) {
                    lc.add(tl);
                }
            }
            this.categoriaLavori.put(c, lc);
        }
        for (Tipolavoro t : this.getLavori()) {
            this.lavoriDesc.put(t.getIdTipoLavoro(), "[".concat(t.getCodice()).concat("] - ").concat(t.getDescrizione()));
        }
        loadingBusy.compareAndSet(false, true);
        notify();
    }

    public synchronized List<Categoriatipolavoro> getCategorie() {
        return categorie;
    }

    public synchronized List<Tipolavoro> getLavori() {
        return lavori;
    }

    public HashMap<Integer, String> getCategorieDesc() {
        return categorieDesc;
    }

    public HashMap<Integer, String> getLavoriDesc() {
        return lavoriDesc;
    }

    private List<Lavoripratichecustom> getLavoriCustom(Pratica p) {
        return this.lavoriCustomService.getLavoriCustomPerPratica(p);
    }

    public HashMap<Categoriatipolavoro, List<Tipolavoro>> getCategoriaLavori() {
        return categoriaLavori;
    }
    
    public Boolean isLavoroSelected(Pratica p, Tipolavoro t) {
        List<Lavoripratichestandard> lav = getLavoriStandard(p);
        for (Lavoripratichestandard l : lav) {
            if (l.getTipolavoro().getIdTipoLavoro().equals(t.getIdTipoLavoro())) {
                return true;
            }
        }
        return false;
    }

    public List<Lavoripratichestandard> getLavoriStandardByCategoria(Categoriatipolavoro c, Pratica p) {
        List<Lavoripratichestandard> result = new ArrayList<>();
        for (Lavoripratichestandard l : getLavoriStandard(p)) {           
            if (l.getTipolavoro().getCategoria().getIdCategoriaTipoLavoro().equals(c.getIdCategoriaTipoLavoro())) {
                result.add(l);
            }
        }
        return result;
    }

    public List<Lavoripratichecustom> getLavoriCustomByCategoria(Categoriatipolavoro c, Pratica p) {
        List<Lavoripratichecustom> result = new ArrayList<>();
        for (Lavoripratichecustom l : getLavoriCustom(p)) {
            if (l.getCategoria().getIdCategoriaTipoLavoro().equals(c.getIdCategoriaTipoLavoro())) {
                result.add(l);
            }
        }
        return result;
    }

    public void cancellaTuttiLavoriDiCategoria(List<Lavoripratichestandard> s, List<Lavoripratichecustom> c){
        for(Lavoripratichestandard lps: s){
            this.lavoriStandardService.remove(lps);
        }
        for(Lavoripratichecustom lpc: c){
            this.lavoriCustomService.remove(lpc);
        }
    }
    
    private List<Lavoripratichestandard> getLavoriStandard(Pratica p) {
        return this.lavoriStandardService.getLavoriStandardPerPratica(p);
    }

}
