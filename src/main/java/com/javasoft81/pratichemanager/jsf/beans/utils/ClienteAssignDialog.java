/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.jsf.beans.utils;

import com.javasoft81.pratichemanager.entities.Cliente;
import com.javasoft81.pratichemanager.jsf.beans.VeicoliSearchView;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author iavazzo.andrea
 */
public class ClienteAssignDialog implements Serializable{

    private List<Cliente> clienti;
    
    private Cliente cliente;
    
    private List<Cliente> filtered;
    
    /**
     * Creates a new instance of ClienteAssignDialog
     */
    public ClienteAssignDialog() {
         FacesContext context = FacesContext.getCurrentInstance();     
         clienti = context.getApplication().evaluateExpressionGet(context, "#{veicoliSearchView}", VeicoliSearchView.class).getClienti();
    }
    
    @PostConstruct
    public void init(){
        
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getFiltered() {
        return filtered;
    }

    public void setFiltered(List<Cliente> filtered) {
        this.filtered = filtered;
    }
    
    public void assignCliente(Cliente c) {
        RequestContext.getCurrentInstance().closeDialog(c);
    }

    public List<Cliente> getClienti() {
        return clienti;
    }

    public void setClienti(List<Cliente> clienti) {
        this.clienti = clienti;
    }
    
    
}
