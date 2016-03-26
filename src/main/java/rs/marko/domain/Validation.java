/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.marko.domain;

/**
 *
 * @author marko
 */
public interface Validation {
    
    String JMBG_REGEX = "(((0[1-9]|[12]\\d|3[01])(0[1-9]|1[012]))|((0[1-9]|[12]\\d)02))(9|0)\\d\\d\\d\\d(5|0)\\d\\d\\d";
    String INT_REGEX = "\\d+";
    String TEKUCI_RACUN_REGEX = "\\d+\\p{Z}*-\\p{Z}*\\d+\\p{Z}*-\\p{Z}*\\d+";
    
    boolean isValid();
}
