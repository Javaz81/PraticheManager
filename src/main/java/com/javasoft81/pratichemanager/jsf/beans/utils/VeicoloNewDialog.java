/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.jsf.beans.utils;

import com.javasoft81.pratichemanager.entities.Cliente;
import com.javasoft81.pratichemanager.entities.Veicolo;
import com.javasoft81.pratichemanager.entities.beans.ForbiddenOperationException;
import com.javasoft81.pratichemanager.jsf.beans.AppContextBean;
import com.javasoft81.pratichemanager.jsf.beans.VeicoliSearchView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author andrea
 */
public class VeicoloNewDialog implements Serializable {

    private Cliente cliente;

    private Veicolo veicolo;

    private String[] tipiVeicolo;

    private VeicoliSearchView v;

    private List<Cliente> clienti;

    private List<Cliente> filtered;

    //Proprieta veicolo
    private String anno;
    private String marca;
    private String modello;
    private String matricola;
    private String tipo;
    private String targa;
    private Integer portataMax;

    //Proprietà cliente
    private String nome;
    private String cognome;
    private String localita;
    private String cellulare;
    private final String ANNO_DEFAULT = "1900";
    
    private AppContextBean appVars;
    private boolean targaGenerated = false;

    /**
     * Creates a new instance of VeicoloNewDialog
     */
    public VeicoloNewDialog() {
    }

    public static enum ResponseParameter {
        CLIENTE_ASSEGNATO("assegnato"),
        CLIENTE_MODIFICATO("modificato"),
        CLIENTE_NUOVO("nuovo"),
        VEICOLO("veicolo");

        private String parName;

        ResponseParameter(String pN) {
            this.parName = pN;
        }

        @Override
        public String toString() {
            return this.parName;
        }
    }

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        v = context.getApplication().evaluateExpressionGet(context, "#{veicoliSearchView}", VeicoliSearchView.class);
        appVars = context.getApplication().evaluateExpressionGet(context, "#{appContextBean}", AppContextBean.class);
        veicolo = new Veicolo();
        cliente = new Cliente();
        clienti = v.getClienti();
        tipiVeicolo = PraticheUtils.TIPI_VEICOLO;
        filtered = new ArrayList<>();
    }

    public List<Cliente> getFiltered() {
        return filtered;
    }

    public void setFiltered(List<Cliente> filtered) {
        this.filtered = filtered;
    }

    public List<Cliente> getClienti() {
        return clienti;
    }

    public String[] getTipiVeicolo() {
        return tipiVeicolo;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModello() {
        return modello;
    }

    public void setModello(String modello) {
        this.modello = modello;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }

    public Integer getPortataMax() {
        return portataMax;
    }

    public void setPortataMax(Integer portataMax) {
        this.portataMax = portataMax;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getLocalita() {
        return localita;
    }

    public void setLocalita(String localita) {
        this.localita = localita;
    }

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public void assignCliente(Cliente c) {
        RequestContext.getCurrentInstance().closeDialog(c);
    }

    public void openClienteDialog() {
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("draggable", true);
        options.put("modal", true);
        options.put("contentWidth", 800);
        RequestContext.getCurrentInstance().openDialog("menuAssignCliente", options, null);
    }

    public void onClienteAssigned(SelectEvent event) {
        Cliente c = (Cliente) event.getObject();
        this.cliente = c;
        this.nome = c.getNome();
        this.cognome = c.getCognome();
        this.localita = c.getLocalita();
        this.cellulare = c.getCellulare();
    }

    private void emptyNullField() {
        //cliente...        
        if (nome == null || nome.trim().isEmpty()) {
            throw new NullPointerException();
        }
        if (cognome == null) {
            cognome = "";
        }
        if (localita == null) {
            localita = "";
        }
        if (cellulare == null) {
            cellulare = "";
        }
        //veicolo...
        if (marca == null || marca.trim().isEmpty()) {
            throw new NullPointerException();
        }
        if (matricola == null) {
            matricola = "";
        }
        if (anno == null) {
            anno = ANNO_DEFAULT;
        }
        if (targa == null || targa.trim().isEmpty()) {
            //può essere nulla, ma se valorizzata deve essere unique.     
            targa = null;
        }
        
        if (portataMax == null) {
            portataMax = 0;
        }
        if (tipo == null) {
            tipo = this.tipiVeicolo[0];
        }
    }

    public void generateTarga(){
        if(!this.targaGenerated){
            this.targa = appVars.getNextTarga();
            this.targaGenerated = true;                    
        }
    }
    
    public void assingNoCliente() {
        for (Cliente c : clienti) {
            if (c.getIdCliente().equals(1)) {
                cliente = c;
                nome= c.getNome();
                cognome = c.getCognome();
                localita = c.getLocalita();
                cellulare = c.getCellulare();
                break;
            }
        }
    }

    public void endEditDialog() {
        //Validate veicolo and client
        boolean validate = false;
        HashMap<String, Object> response = new HashMap<>();
        //Vediamo se il cliente è stato scelto dalla lista, è nullo, è stato assegnato da lista oppure assegnato e modificato.
        //Il cliente con ID = 1 è il cliente nullo e non è modificabile.
        boolean edit = false;
        try {
            //Se il cliente è stato preso da un assegnamento da lista...
            if (cliente != null) {
                //Se id=1 (NN) non è modificabile  e si passa come assegnato...          
                if (cliente.getIdCliente() != null && cliente.getIdCliente().equals(1)) {
                    response.put(ResponseParameter.CLIENTE_ASSEGNATO.toString(), cliente);
                } else {
                    emptyNullField();
                    //se è stato modificato, allora modificalo...
                    if (cliente.getNome() == null) {
                        if (nome == null) {
                            throw new NullPointerException();
                        } else {
                            if (nome.trim().isEmpty()) {
                                throw new NullPointerException();
                            } else {
                                cliente.setNome(nome);
                                edit = true;
                            }
                        }
                    } else {
                        if (!cliente.getNome().equals(nome)) {
                            cliente.setNome(nome);
                            edit = true;
                        }
                    }

                    if (cliente.getCognome() == null) {
                        if (cognome != null) {
                            cliente.setCognome(cognome);
                            edit = true;
                        }
                    } else {
                        if (!cliente.getCognome().equals(cognome)) {
                            cliente.setCognome(cognome);
                            edit = true;
                        }
                    }

                    if (cliente.getLocalita() == null) {
                        if (localita != null) {
                            cliente.setLocalita(localita);
                            edit = true;
                        }
                    } else {
                        if (!cliente.getLocalita().equals(localita)) {
                            cliente.setLocalita(localita);
                            edit = true;
                        }
                    }
                    if (cliente.getCellulare() == null) {
                        if (cellulare != null) {
                            cliente.setCellulare(cellulare);
                            edit = true;
                        }
                    } else {
                        if (!cliente.getCellulare().equals(cellulare)) {
                            cliente.setCellulare(cellulare);
                            edit = true;
                        }
                    }

                    if (edit) {
                        if(cliente.getIdCliente()==null)
                            response.put(ResponseParameter.CLIENTE_NUOVO.toString(), cliente);
                        else
                            response.put(ResponseParameter.CLIENTE_MODIFICATO.toString(), cliente);
                    } else {
                        response.put(ResponseParameter.CLIENTE_ASSEGNATO.toString(), cliente);
                    }
                }
            } else {
                //cliente nuovo,
                cliente = new Cliente();
                cliente.setCellulare(cellulare);
                cliente.setCognome(cognome);
                cliente.setLocalita(localita);
                cliente.setNome(nome);
                response.put(ResponseParameter.CLIENTE_NUOVO.toString(), cliente);
            }
        } catch (NullPointerException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore!", "Manca qualcosa nel cliente"
                    + " o nel veicolo! La MARCA del veicolo e il NOME/RAG. SOCIALE del cliente sono obbligatori!. La targa deve essere univoca (se valorizzata) tra tutte le altre piattaforme/autoarticolati!"));
            return;
        }
        try {
            this.veicolo = new Veicolo();
            if (anno != null) {
                try {
                    this.veicolo.setAnno(Integer.parseInt(anno));
                } catch (java.lang.NumberFormatException ex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore!", "ANNO deve essere valorizzato correttamente!"));
                    return;
                }
            }
            //se siamo qui, allora matricola arriva già non nulla...il controllo di univocità lo farà la persistenza nel metodo di ritorno in VeicoliSearchView...
            this.veicolo.setMarca(marca);

            if (matricola != null) {
                this.veicolo.setMatricola(matricola);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore!", "La matricola deve essere valorizzata!"));
                return;
            }
            this.veicolo.setPortataMax(portataMax);
            this.veicolo.setModello(modello);
            this.veicolo.setTipo(tipo);
            if(targa!=null){
                //controlla se la targa è unica...
                if(v.getVeicoli().stream().anyMatch(i->{return i.getTarga()==null?false:i.getTarga().equalsIgnoreCase(targa);})){
                    throw new ForbiddenOperationException("La TARGA deve essere UNIVOCA (cioè non ci devono essere altre piattaforme,autoarticolati etc... che hanno questa targa). Controlla che non sia già presente per un'altro veicolo, oppure generane una fittizzia con l'apposito pulsante.");
                }else{
                    this.veicolo.setTarga(targa);
                }
            }else{
                this.veicolo.setTarga(targa);
            }
            response.put(ResponseParameter.VEICOLO.toString(), veicolo);
            //il cliente del veicolo lo setterà durante la persistenza in modo tale che se c'è un errore di persistenza stesso, non venga
            //creato niente . . . 
            RequestContext.getCurrentInstance().closeDialog(response);
        } catch (NullPointerException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore!", "Manca qualcosa!Ricorda che la targa è obbligatoria.!"));
        } catch (ForbiddenOperationException foe){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore!", foe.getMessage()));
        }
    }
}
