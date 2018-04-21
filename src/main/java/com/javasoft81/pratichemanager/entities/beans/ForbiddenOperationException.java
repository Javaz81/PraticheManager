/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.entities.beans;

/**
 *
 * @author andrea
 */
public class ForbiddenOperationException extends Exception {

    private final String message;

    public ForbiddenOperationException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message; //To change body of generated methods, choose Tools | Templates.
    }

}
