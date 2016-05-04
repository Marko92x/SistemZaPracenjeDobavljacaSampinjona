/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.marko.service;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.marko.domain.Administrativniradnik;
import rs.marko.domain.Dobavljac;
import rs.marko.domain.Zaduzenje;
import rs.marko.domain.ZaduzenjePK;
import rs.marko.exceptions.DataNotFoundException;
import rs.marko.exceptions.MyRollbackException;
import rs.marko.exceptions.NotAuthorizedException;
import rs.marko.helper.HelperClass;
import rs.marko.helper.ValidationHelperClass;

/**
 *
 * @author Mare
 */
@Path("/zaduzenja")
public class ZaduzenjaRESTEndpoint {

    HelperClass helper;
    ValidationHelperClass validator;

    public ZaduzenjaRESTEndpoint() {
        helper = new HelperClass();
        validator = new ValidationHelperClass();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{dobavljac}")
    public Response getZaduzenja(@HeaderParam("authorization") String authorization, @PathParam("dobavljac") String jmbg) {
        EntityManager em = helper.getEntityManager();
        if (helper.isLogged(authorization, em)) {
            List<Zaduzenje> zaduzenja = em.createNamedQuery("Zaduzenje.findByJmbg").setParameter("jmbg", jmbg).getResultList();
            if (zaduzenja.isEmpty()) {
                throw new DataNotFoundException("Nema zaduzenja u bazi!");
            } else {
                return Response.ok().entity(zaduzenja).build();
            }
        } else {
            throw new NotAuthorizedException("Nemate pristup ovom pozivu!");
        }
    }

    @POST
//    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{dobavljac}")
    public Response insertZaduzenje(@HeaderParam("authorization") String authorization, @PathParam("dobavljac") String jmbg, Zaduzenje zaduzenje) {
        EntityManager em = helper.getEntityManager();
        if (helper.isLogged(authorization, em)) {
            Dobavljac dobavljac = em.find(Dobavljac.class, jmbg);
            int id = Integer.parseInt(helper.getAbstractToken().decode(authorization).split("##")[1]);
            Administrativniradnik ar = em.find(Administrativniradnik.class, id);
            zaduzenje.setDobavljac(dobavljac);
            zaduzenje.setZaduzio(ar);
            ZaduzenjePK pk = new ZaduzenjePK();
            pk.setJmbg(dobavljac.getJmbg());
            zaduzenje.setZaduzenjePK(pk);
            zaduzenje.setDatumzaduzenja(new Date());
//            validator.isValid(zaduzenje);
            try {
                helper.persistObject(em, zaduzenje);
            } catch (RollbackException e) {
                throw new MyRollbackException("Ovaj zaduzenje vec postoji u bazi!");
            }
            return Response.status(Response.Status.CREATED).build();
        } else {
            throw new NotAuthorizedException("Nemate pristup ovom pozivu!");
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response updateZaduzenje(@HeaderParam("authorization") String authorization, @PathParam("id") int zaduzenjeID) {
        EntityManager em = helper.getEntityManager();
        if (helper.isLogged(authorization, em)) {
            try {
                int id = Integer.parseInt(helper.getAbstractToken().decode(authorization).split("##")[1]);
                Administrativniradnik ar = em.find(Administrativniradnik.class, id);
                Zaduzenje zaduzenje = (Zaduzenje) em.createNamedQuery("Zaduzenje.findByZaduzenjeid").setParameter("zaduzenjeid", zaduzenjeID).getSingleResult();
                zaduzenje.setRazduzio(ar);
                zaduzenje.setDatumrazduzenja(new Date());
                helper.mergeObject(em, zaduzenje);
                return Response.ok().build();
            } catch (NoResultException e) {
                throw new DataNotFoundException("Ovo zaduzenje ne postoji u bazi!");
            }

        } else {
            throw new NotAuthorizedException("Nemate pristup ovom pozivu!");
        }
    }

}