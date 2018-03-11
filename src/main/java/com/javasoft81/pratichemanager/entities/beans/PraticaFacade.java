/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.entities.beans;

import com.javasoft81.pratichemanager.entities.Pratica;
import com.javasoft81.pratichemanager.entities.Veicolo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author andrea
 */
@Stateless
public class PraticaFacade extends AbstractFacade<Pratica> {

    @PersistenceContext(unitName = "com.javasoft81_PraticheManager_war_1.0.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PraticaFacade() {
        super(Pratica.class);
    }
    /**
     * Trova le ultime "limit"-esima pratiche per quel veicolo in ordine temporale
     * discendente
     * @param currentCar il veicolo in questione.
     * @param limit Il numero delle ultime pratiche del veicolo in ordine di data
     * di creazione.
     * @return Ritorna una lista di pratiche associate a quel veicolo dalla più 
     * recente fino alla più lontana "limit"-esima. 
     */
    public List<Pratica> findPraticaByVeicolo(Veicolo currentCar, int limit) {
        return (List<Pratica>)this.em.createNamedQuery("Pratica.findByVeicolo")
                .setParameter("veicolo", currentCar)                
                .setMaxResults(limit).getResultList();                
    }
    /**
     * Calcola il numero di pratiche associate a quel veicolo.
     * @param currentCar Il veicolo in questione
     * @return Un Integer rappresentante il numero di pratiche di quel veicolo.
     */
    public Long countPraticheByVeicolo(Veicolo currentCar){
        return (Long) this.em.createNamedQuery("Pratica.countByVeicolo")
                .setParameter("veicolo", currentCar)
                .getSingleResult();
    }
}
