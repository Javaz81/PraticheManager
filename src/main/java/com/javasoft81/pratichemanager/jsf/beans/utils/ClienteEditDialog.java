/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.jsf.beans.utils;

import com.javasoft81.pratichemanager.entities.Cliente;
import com.javasoft81.pratichemanager.jsf.beans.VeicoliSearchView;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Andrea Iavazzo
 */
public class ClienteEditDialog implements Serializable{

    private Cliente cliente;
    
    private VeicoliSearchView v;
    
    /**
     * Creates a new instance of ClienteEditDialog
     */
    public ClienteEditDialog() {
    }
    
    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();     
        v = context.getApplication().evaluateExpressionGet(context, "#{veicoliSearchView}", VeicoliSearchView.class);
        if(v.getSelectedPratica()!=null)
            cliente = v.getSelectedPratica().getClienteidCliente();
        else
            cliente = v.getSelectedCar().getCliente();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public void endEditDialog(){
        RequestContext.getCurrentInstance().closeDialog(cliente);
    }
}
