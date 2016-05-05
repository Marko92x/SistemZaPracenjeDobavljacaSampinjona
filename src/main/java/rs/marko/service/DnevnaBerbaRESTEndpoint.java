/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.marko.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.marko.domain.Dnevnaberba;
import rs.marko.exceptions.DataNotFoundException;
import rs.marko.exceptions.MyRollbackException;
import rs.marko.exceptions.NotAuthorizedException;
import rs.marko.helper.HelperClass;
import rs.marko.helper.ValidationHelperClass;

/**
 *
 * @author Mare
 */
@Path("/dnevnaBerba")
public class DnevnaBerbaRESTEndpoint {

    HelperClass helper;
    ValidationHelperClass validator;

    public DnevnaBerbaRESTEndpoint() {
        helper = new HelperClass();
        validator = new ValidationHelperClass();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{dobavljac}")
    public Response getDnevneBerbe(@HeaderParam("authorization") String authorization, @PathParam("dobavljac") String jmbg) {
        EntityManager em = helper.getEntityManager();
        if (helper.isLogged(authorization, em)) {
            List<Dnevnaberba> dnevneBerbe = em.createNamedQuery("Dnevnaberba.findByJmbg").setParameter("jmbg", jmbg).getResultList();
            if (dnevneBerbe.isEmpty()) {
                throw new DataNotFoundException("Nema dnevne berbe u bazi");
            } else {
                return Response.ok().entity(dnevneBerbe).build();
            }
        } else {
            throw new NotAuthorizedException("Nemate pristup ovom pozivu!");
        }
    }

    @POST
//    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertDnevnaBerba(@HeaderParam("authorization") String authorization, Dnevnaberba dnevnaBerba) {
        EntityManager em = helper.getEntityManager();
        if (helper.isLogged(authorization, em)) {
            validator.isValid(dnevnaBerba);
            try {
                helper.persistObject(em, dnevnaBerba);
            } catch (RollbackException e) {
                throw new MyRollbackException("Ovaj dobavljac vec postoji u bazi!");
            }
            return Response.status(Response.Status.CREATED).build();
        } else {
            throw new NotAuthorizedException("Nemate pristup ovom pozivu!");
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteDnevnaBerba(@HeaderParam("authorization") String authorization, @PathParam("id") int id) {
        EntityManager em = helper.getEntityManager();
        if (helper.isLogged(authorization, em)) {
            try {
                Dnevnaberba dnevnaBerba = (Dnevnaberba) em.createNamedQuery("Dnevnaberba.findByDnevnaberbaid").setParameter("dnevnaberbaid", id).getSingleResult();
                helper.removeObject(em, dnevnaBerba);
                return Response.ok().build();
            } catch (NoResultException e) {
                throw new DataNotFoundException("Ovaj dobavljac ne postoji u bazi!");
            }
        } else {
            throw new NotAuthorizedException("Nemate pristup ovom pozivu!");
        }
    }
}
