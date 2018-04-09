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
        this.categorie.stream().map((c) -> {
            this.categorieDesc.put(c.getIdCategoriaTipoLavoro(), c.getNome());
            return c;
        }).forEachOrdered((c) -> {
            List<Tipolavoro> lc = new ArrayList<>();
            this.lavori.stream().filter((tl) -> (tl.getCategoria().equals(c))).forEachOrdered((tl) -> {
                lc.add(tl);
            });
            this.categoriaLavori.put(c, lc);
        });
        this.getLavori().forEach((t) -> {
            this.lavoriDesc.put(t.getIdTipoLavoro(), "[".concat(t.getCodice()).concat("] - ").concat(t.getDescrizione()));
        });

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
        this.categorie.stream().map((c) -> {
            this.categorieDesc.put(c.getIdCategoriaTipoLavoro(), c.getNome());
            return c;
        }).forEachOrdered((c) -> {
            List<Tipolavoro> lc = new ArrayList<>();
            this.lavori.stream().filter((tl) -> (tl.getCategoria().equals(c))).forEachOrdered((tl) -> {
                lc.add(tl);
            });
            this.categoriaLavori.put(c, lc);
        });
        this.getLavori().forEach((t) -> {
            this.lavoriDesc.put(t.getIdTipoLavoro(), "[".concat(t.getCodice()).concat("] - ").concat(t.getDescrizione()));
        });
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
        return lav.stream().anyMatch((l) -> (l.getTipolavoro().getIdTipoLavoro().equals(t.getIdTipoLavoro())));
    }

    public List<Lavoripratichestandard> getLavoriStandardByCategoria(Categoriatipolavoro c, Pratica p) {
        List<Lavoripratichestandard> result = new ArrayList<>();
        getLavoriStandard(p).stream().filter((l) -> (l.getTipolavoro().getCategoria().getIdCategoriaTipoLavoro().equals(c.getIdCategoriaTipoLavoro()))).forEachOrdered((l) -> {
            result.add(l);
        });
        return result;
    }

    public List<Lavoripratichecustom> getLavoriCustomByCategoria(Categoriatipolavoro c, Pratica p) {
        List<Lavoripratichecustom> result = new ArrayList<>();
        getLavoriCustom(p).stream().filter((l) -> (l.getCategoria().getIdCategoriaTipoLavoro().equals(c.getIdCategoriaTipoLavoro()))).forEachOrdered((l) -> {
            result.add(l);
        });
        return result;
    }

    public void cancellaTuttiLavoriDiCategoria(List<Lavoripratichestandard> s, List<Lavoripratichecustom> c) {
        s.forEach((lps) -> {
            this.lavoriStandardService.remove(lps);
        });
        c.forEach((lpc) -> {
            this.lavoriCustomService.remove(lpc);
        });
    }

    private List<Lavoripratichestandard> getLavoriStandard(Pratica p) {
        return this.lavoriStandardService.getLavoriStandardPerPratica(p);
    }

    public void cancellaLavoroStandard(Lavoripratichestandard s) {
        this.lavoriStandardService.remove(s);
    }

    public void cancellaLavoroCustom(Lavoripratichecustom c) {
        this.lavoriCustomService.remove(c);
    }

    public void editLavoroCustom(Lavoripratichecustom c) {
        this.lavoriCustomService.edit(c);
    }

    public Lavoripratichestandard creaNuovoLavoroStandard(Pratica selectedPratica, Tipolavoro i) {
        Lavoripratichestandard l = new Lavoripratichestandard();
        l.setPratica(selectedPratica);
        l.setTipolavoro(i);
        this.lavoriStandardService.create(l);
        for (Lavoripratichestandard ll : this.lavoriStandardService.getLavoriStandardPerPratica(selectedPratica)) {
            if (ll.getTipolavoro().getIdTipoLavoro().equals(i.getIdTipoLavoro())) {
                return ll;
            }
        }
        //It should be never happen...
        return null;
    }

    public List<Tipolavoro> getLavoriCategoria(Categoriatipolavoro cat) {
        List<Tipolavoro> lavcat = new ArrayList<>();
        this.lavori.forEach(i -> {
            if (i.getCategoria().getIdCategoriaTipoLavoro().equals(cat.getIdCategoriaTipoLavoro())) {
                lavcat.add(i);
            }
        });
        return lavcat;
    }

    Lavoripratichecustom creaNuovoLavoroCustom(Pratica selectedPratica, Lavoripratichecustom l) {
        this.lavoriCustomService.create(l);
        Lavoripratichecustom found = null;
        List<Lavoripratichecustom> list = this.lavoriCustomService.getLavoriCustomPerPratica(selectedPratica);
        for(Lavoripratichecustom i : list){
            if (i.getCategoria().getIdCategoriaTipoLavoro().equals(l.getCategoria().getIdCategoriaTipoLavoro())
                    && i.getDescrizione().equals(l.getDescrizione())) {
                return i;
            }
        }
        return null;
    }
}
