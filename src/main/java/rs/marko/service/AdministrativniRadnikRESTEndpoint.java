/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.marko.service;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.marko.domain.Administrativniradnik;
import rs.marko.exceptions.DataNotFoundException;
import rs.marko.exceptions.NotAuthorizedException;
import rs.marko.helper.HelperClass;
import rs.marko.helper.ValidationHelperClass;

/**
 *
 * @author Mare
 */
@Path("radnik")
public class AdministrativniRadnikRESTEndpoint {
    
    HelperClass helper;
    ValidationHelperClass validator;

    public AdministrativniRadnikRESTEndpoint() {
        helper = new HelperClass();
        validator = new ValidationHelperClass();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRadnici(@HeaderParam("authorization") String authorization){
        EntityManager em = helper.getEntityManager();
        if (helper.isLogged(authorization, em)){
            Administrativniradnik ar = em.find(Administrativniradnik.class, Integer.parseInt(helper.getAbstractToken().decode(authorization).split("##")[1]));
            if (ar != null){
                return Response.ok().entity(ar).build();
            } else {
                throw new DataNotFoundException("Greska!!!");
            }
        } else {
            throw new NotAuthorizedException("Nemate pristup ovom pozivu!");
        }
    }
}
