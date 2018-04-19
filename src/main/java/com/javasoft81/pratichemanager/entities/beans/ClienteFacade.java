/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.entities.beans;

import com.javasoft81.pratichemanager.entities.Cliente;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author andrea
 */
@Stateless
public class ClienteFacade extends AbstractFacade<Cliente> {

    @PersistenceContext(unitName = "com.javasoft81_PraticheManager_war_1.0.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
        super(Cliente.class);
    }

    public Cliente findCliente(Cliente c) {
        return (Cliente)this.getEntityManager().createNamedQuery("Cliente.findByCliente")
                .setParameter("nome", c.getNome())
                .setParameter("cognome", c.getCognome())
                .setParameter("localita", c.getLocalita())
                .setParameter("cellulare",c.getCellulare()).getResultList().get(0);
    }
    
}
