/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.jsf.beans;

import java.io.Serializable;

/**
 *
 * @author iavazzo.andrea
 */
public class SearchValidationView implements Serializable{
    
    private String searchTerm;
    /**
     * Creates a new instance of SearchValidationView
     */
    public SearchValidationView() {
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
    
    
    
}
