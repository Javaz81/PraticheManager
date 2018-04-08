/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.entities.beans;

import com.javasoft81.pratichemanager.entities.Lavoripratichestandard;
import com.javasoft81.pratichemanager.entities.Pratica;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author andrea
 */
@Stateless
public class LavoripratichestandardFacade extends AbstractFacade<Lavoripratichestandard> {

    @PersistenceContext(unitName = "com.javasoft81_PraticheManager_war_1.0.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    

    public LavoripratichestandardFacade() {
        super(Lavoripratichestandard.class);
    }
    
     public List<Lavoripratichestandard> getLavoriStandardPerPratica(Pratica p){
        Query q = getEntityManager().createNamedQuery("Lavoripratichestandard.findByPratica");
        q.setParameter("pratica", p);
        List<Lavoripratichestandard> lps = q.getResultList();
        return lps;
    }    
}
