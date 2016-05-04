/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.marko.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.marko.domain.Dobavljac;
import rs.marko.domain.Mesto;
import rs.marko.exceptions.DataNotFoundException;
import rs.marko.exceptions.MyRollbackException;
import rs.marko.exceptions.NotAuthorizedException;
import rs.marko.helper.HelperClass;
import rs.marko.helper.ValidationHelperClass;

/**
 *
 * @author Mare
 */
@Path("/dobavljac")
public class DobavljacRESTEndpoint {

    HelperClass helper;
    ValidationHelperClass validator;

    public DobavljacRESTEndpoint() {
        helper = new HelperClass();
        validator = new ValidationHelperClass();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDobavljac(@HeaderParam("authorization") String authorization, @QueryParam("search") String search, @QueryParam("place") String place) {
        EntityManager em = helper.getEntityManager();
        if (helper.isLogged(authorization, em)) {
            StringBuilder query = new StringBuilder("SELECT d FROM Dobavljac d ");
            if (search != null) {
                query.append("WHERE (d.jmbg LIKE '%")
                        .append(search)
                        .append("%' OR d.ime LIKE '%")
                        .append(search)
                        .append("%' OR d.prezime LIKE '%")
                        .append(search)
                        .append("%' OR d.mesto.naziv LIKE '%")
                        .append(search)
                        .append("%')");
            }
            if (place != null) {
                query.append(search != null ? " AND " : "WHERE ")
                        .append("d.mesto.naziv = '")
                        .append(place)
                        .append("'");
            }
            System.out.println(query);
            List<Dobavljac> dobavljaci = em.createQuery(query.toString()).getResultList();
            if (dobavljaci.isEmpty()) {
                throw new DataNotFoundException("Nema dobavljaca u bazi");
            } else {
                return Response.ok().entity(dobavljaci).build();
            }
        } else {
            throw new NotAuthorizedException("Nemate pristup ovom pozivu!");
        }
    }

    //ispraviti JMBG regex, nije dobar!!!
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertDobavljac(@HeaderParam("authorization") String authorization, Dobavljac dobavljac) {
        EntityManager em = helper.getEntityManager();
        if (helper.isLogged(authorization, em)) {
//            validator.isValid(dobavljac);
            try {
                helper.persistObject(em, dobavljac);
            } catch (RollbackException e) {
                throw new MyRollbackException("Ovaj dobavljac vec postoji u bazi!");
            }
            return Response.status(Response.Status.CREATED).build();
        } else {
            throw new NotAuthorizedException("Nemate pristup ovom pozivu!");
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDobavljac(@HeaderParam("authorization") String authorization, Dobavljac dobavljac) {
        EntityManager em = helper.getEntityManager();
        if (helper.isLogged(authorization, em)) {
            try {
                Dobavljac oldDobavljac = em.find(Dobavljac.class, dobavljac.getJmbg());
                if (oldDobavljac != null) {
                    dobavljac.setMesto(em.find(Mesto.class, dobavljac.getMesto().getPtt()));
                    Dobavljac d = (Dobavljac) helper.mergeValues(oldDobavljac, dobavljac);
//                    validator.isValid(d);
                    helper.mergeObject(em, dobavljac);
                    return Response.ok().build();

                } else {
                    throw new DataNotFoundException("Ovaj dobavljac ne postoji u bazi!");
                }
            } catch (IllegalArgumentException e) {
                throw new DataNotFoundException("Ovaj dobavljac ne postoji u bazi!");
            }
        } else {
            throw new NotAuthorizedException("Nemate pristup ovom pozivu!");
        }
    }
    
    @DELETE
    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDobavljac(@HeaderParam("authorization") String authorization, @PathParam("id") String id){
        EntityManager em = helper.getEntityManager();
        if (helper.isLogged(authorization, em)){
            Dobavljac dobavljac = em.find(Dobavljac.class, id);
            if (dobavljac != null){
                helper.removeObject(em, dobavljac);
                return Response.ok().entity("Uspesno obrisan dobavljac!").build();
            } else {
                throw new DataNotFoundException("Ovaj dobavljac ne postoji u bazi!");
            }
        } else {
            throw new NotAuthorizedException("Nemate pristup ovom pozivu!");
        }
    }
}