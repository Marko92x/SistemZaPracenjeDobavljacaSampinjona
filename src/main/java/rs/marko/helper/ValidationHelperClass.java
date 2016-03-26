/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.marko.helper;

import rs.marko.domain.Validation;
import rs.marko.exceptions.ValidationException;


/**
 *
 * @author marko
 */
public class ValidationHelperClass {
    
    public void isValid(Validation object){
        if (!object.isValid()){
            throw new ValidationException("Nisu ispravno uneti podaci!");
        }
    }
}
