/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.entities.beans;

import com.javasoft81.pratichemanager.entities.Appctxvar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author andrea
 */
@Stateless
public class AppctxvarFacade extends AbstractFacade<Appctxvar> {

    @PersistenceContext(unitName = "com.javasoft81_PraticheManager_war_1.0.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AppctxvarFacade() {
        super(Appctxvar.class);
    }

    @Override
    public void create(Appctxvar entity) {
        try {
            //super.create(entity);
            throw new ForbiddenOperationException("Creazione di una nuova istanza vietata.");
        } catch (ForbiddenOperationException ex) {
            Logger.getLogger(AppctxvarFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void remove(Appctxvar entity) {
         try {
            //super.remove(entity);
            throw new ForbiddenOperationException("Creazione di una nuova istanza vietata.");
        } catch (ForbiddenOperationException ex) {
            Logger.getLogger(AppctxvarFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
