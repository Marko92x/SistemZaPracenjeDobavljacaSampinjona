/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.marko.service;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.marko.domain.Dnevnaberba;
import rs.marko.domain.DnevnaberbaPK;
import rs.marko.domain.Stavkadnevneberbe;
import rs.marko.domain.StavkadnevneberbePK;
import rs.marko.exceptions.DataNotFoundException;
import rs.marko.exceptions.MyRollbackException;
import rs.marko.exceptions.NotAuthorizedException;
import rs.marko.helper.HelperClass;
import rs.marko.helper.ValidationHelperClass;

/**
 *
 * @author Mare
 */
@Path("/stavka")
public class StavkaDnevneBerbeRESTEndpoint {

    HelperClass helper;
    ValidationHelperClass validator;

    public StavkaDnevneBerbeRESTEndpoint() {
        helper = new HelperClass();
        validator = new ValidationHelperClass();
    }

    @POST
    @Path("/{jmbg}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertStavke(@HeaderParam("authorization") String authorization, @PathParam("jmbg") String jmbg, List<Stavkadnevneberbe> stavke) {
        EntityManager em = helper.getEntityManager();
        Dnevnaberba db = new Dnevnaberba();
        db.setDatum(new Date());
        DnevnaberbaPK pk = new DnevnaberbaPK();
        pk.setJmbg(jmbg);
        db.setDnevnaberbaPK(pk);
        if (helper.isLogged(authorization, em)) {
            if (stavke != null && !stavke.isEmpty()) {
                helper.persistObject(em, db);
                String query = "SELECT db FROM Dnevnaberba db ORDER BY db.dnevnaberbaPK.dnevnaberbaid DESC";
                Dnevnaberba dbPk = (Dnevnaberba) em.createQuery(query).setMaxResults(1).getSingleResult();
                try {
                    for (Stavkadnevneberbe s : stavke) {
                        s.setDnevnaberba(dbPk);
                        StavkadnevneberbePK sdPk = new StavkadnevneberbePK(dbPk.getDnevnaberbaPK().getJmbg(), dbPk.getDnevnaberbaPK().getDnevnaberbaid(), 0);
                        s.setStavkadnevneberbePK(sdPk);
//                        validator.isValid(s);
                        em.persist(s);
                    }
                    em.getTransaction().begin();
                    em.getTransaction().commit();
                    return Response.status(Response.Status.CREATED).build();
                } catch (PersistenceException e) {
                    helper.removeObject(em, dbPk);
                    throw new MyRollbackException("Greska pri ubacivanju dnevnih berbi i stavki!");
                }
            } else {
                throw new MyRollbackException("Greska pri ubacivanju dnevnih berbi i stavki!");
            }
        } else {
            throw new NotAuthorizedException("Nemate pristup ovom pozivu!");
        }
    }

    @GET
    @Path("/{dnevnaBerba}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStavka(@HeaderParam("authorization") String authorization, @PathParam("dnevnaBerba") int dnevnaBerba) {
        EntityManager em = helper.getEntityManager();
        if (helper.isLogged(authorization, em)) {
            List<Stavkadnevneberbe> stavke = em.createNamedQuery("Stavkadnevneberbe.findByDnevnaberbaid").setParameter("dnevnaberbaid", dnevnaBerba).getResultList();
            if (stavke.isEmpty()){
                throw new DataNotFoundException("Nema stavki za ovu dnevnu berbu!");
            }
            return Response.ok().entity(stavke).build();
        } else {
            throw new NotAuthorizedException("Nemate pristup ovom pozivu!");
        }
    }
}
