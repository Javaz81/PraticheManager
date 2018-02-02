/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.entities.beans;

import com.javasoft81.pratichemanager.entities.Pratica;
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
    
}
