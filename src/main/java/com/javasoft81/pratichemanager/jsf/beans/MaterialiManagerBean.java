/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.jsf.beans;

import com.javasoft81.pratichemanager.entities.Materialepratica;
import com.javasoft81.pratichemanager.entities.Pratica;
import com.javasoft81.pratichemanager.entities.beans.MaterialepraticaFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author andrea
 */
public class MaterialiManagerBean implements Serializable{    
    
    @EJB
    private MaterialepraticaFacade materialeService;
    
    /**
     * Creates a new instance of MaterialiManagerBean
     */
    public MaterialiManagerBean() {
    }
    
    public List<Materialepratica> getMaterialePratica(Pratica p){
        return materialeService.findByPratica(p);
    }
}
