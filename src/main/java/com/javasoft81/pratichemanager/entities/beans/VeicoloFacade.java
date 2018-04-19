/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.entities.beans;

import com.javasoft81.pratichemanager.entities.Veicolo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author andrea
 */
@Stateless
public class VeicoloFacade extends AbstractFacade<Veicolo> {

    @PersistenceContext(unitName = "com.javasoft81_PraticheManager_war_1.0.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VeicoloFacade() {
        super(Veicolo.class);
    }

    public Veicolo findByVeicolo(Veicolo v) {
        return (Veicolo)this.getEntityManager().createNamedQuery("Veicolo.findByVeicolo")
                .setParameter("marca", v.getMarca() )
                .setParameter("modello", v.getModello())
                .setParameter("matricola", v.getMatricola())
                .setParameter("anno", v.getAnno())
                .setParameter("portataMax", v.getPortataMax())
                .setParameter("targa", v.getTarga())
                .setParameter("tipo", v.getTipo())
                .getResultList().get(0);
    }
    
}
