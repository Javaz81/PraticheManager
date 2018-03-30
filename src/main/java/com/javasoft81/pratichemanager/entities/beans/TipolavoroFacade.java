/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.entities.beans;

import com.javasoft81.pratichemanager.entities.Categoriatipolavoro;
import com.javasoft81.pratichemanager.entities.Tipolavoro;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author andrea
 */
@Stateless
public class TipolavoroFacade extends AbstractFacade<Tipolavoro> {

    @PersistenceContext(unitName = "com.javasoft81_PraticheManager_war_1.0.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipolavoroFacade() {
        super(Tipolavoro.class);
    }
    
    public List<Tipolavoro> findByCategoria(Categoriatipolavoro categoria){
        return getEntityManager().createNamedQuery("Tipolavoro.findByCategoria")
                .setParameter("categoria", categoria).getResultList();
    }
    
}
