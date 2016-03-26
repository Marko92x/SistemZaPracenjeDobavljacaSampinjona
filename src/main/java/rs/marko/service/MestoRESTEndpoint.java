/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.marko.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.marko.domain.Mesto;
import rs.marko.exceptions.DataNotFoundException;
import rs.marko.exceptions.NotAuthorizedException;
import rs.marko.helper.HelperClass;

/**
 *
 * @author Mare
 */
@Path("/mesto")
public class MestoRESTEndpoint {

    HelperClass helper;

    public MestoRESTEndpoint() {
        helper = new HelperClass();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMesto(@HeaderParam("authorization") String authorization) {
        EntityManager em = helper.getEntityManager();
        if (helper.isLogged(authorization, em)) {
            List<Mesto> mesta = em.createNamedQuery("Mesto.findAll").getResultList();
            if (mesta.isEmpty()){
                throw new DataNotFoundException("There is no places!");
            } else {
                return Response.ok().entity(mesta).build();
            }
        } else {
            throw new NotAuthorizedException("Nemate pristup ovom pozivu!");
        }
    }
}
