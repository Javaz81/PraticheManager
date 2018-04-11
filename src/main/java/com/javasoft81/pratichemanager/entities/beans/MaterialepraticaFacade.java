/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.entities.beans;

import com.javasoft81.pratichemanager.entities.Materialepratica;
import com.javasoft81.pratichemanager.entities.Pratica;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author andrea
 */
@Stateless
public class MaterialepraticaFacade extends AbstractFacade<Materialepratica> {

    @PersistenceContext(unitName = "com.javasoft81_PraticheManager_war_1.0.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MaterialepraticaFacade() {
        super(Materialepratica.class);
    }
       public List<Materialepratica> findByPratica(Pratica p) {
        return getEntityManager().createNamedQuery("Materialepratica.findByPratica")
                .setParameter("pratica", p.getIdPratica()).getResultList();
    }

    public Materialepratica recuperaMaterialePratica(Materialepratica unregisteredMateriale) {
        this.create(unregisteredMateriale);
        return (Materialepratica)this.getEntityManager().createNamedQuery("Materialepratica.findByArticoloPratica")
                .setParameter("articolo", unregisteredMateriale.getArticolo1())
                .setParameter("pratica", unregisteredMateriale.getPratica1())
                .getResultList().get(0);
    }
}
