/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.entities.beans;

import com.javasoft81.pratichemanager.entities.Articolo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author andrea
 */
@Stateless
public class ArticoloFacade extends AbstractFacade<Articolo> {

    @PersistenceContext(unitName = "com.javasoft81_PraticheManager_war_1.0.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArticoloFacade() {
        super(Articolo.class);
    }

    public Object findByDescrizione(String value) {
        return getEntityManager().createNamedQuery("Articolo.findByDescrizione")
                .setParameter("descrizione", value)
                .getResultList().get(0);
    }
}
