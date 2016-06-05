/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.marko.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.marko.domain.Administrativniradnik;
import rs.marko.exceptions.BasicAuthenticationException;
import rs.marko.helper.HelperClass;
import rs.marko.token.AbstractTokenCreator;
import rs.marko.token.JsonToken;

/**
 *
 * @author Mare
 */
@Path("authorization")
public class AuthorizationRESTEndpoint {
    
    HelperClass helper;
    AbstractTokenCreator tokenHelper;

    public AuthorizationRESTEndpoint() {
        helper = new HelperClass();
        tokenHelper = helper.getAbstractToken();
    }
    
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@HeaderParam("authorization") String authorization){
        try{
            String[] userPass = tokenHelper.decodeBasicAuth(authorization);
            EntityManager em = helper.getEntityManager();
            Administrativniradnik ar = (Administrativniradnik) em
                    .createQuery("SELECT ar FROM Administrativniradnik ar WHERE ar.korisnickoime = :korisnickoIme AND ar.korisnickasifra = :korisnickaSifra")
                    .setParameter("korisnickoIme", userPass[0])
                    .setParameter("korisnickaSifra", userPass[1])
                    .getSingleResult();
            
            if (ar.getToken() == null || ar.getToken().equals("")){
                ar.setToken(tokenHelper.createToken(ar.getAdministrativniradnikid()));
                helper.mergeObject(em, ar);
            }
            JsonToken jsonToken = new JsonToken(tokenHelper.encode(ar.getToken()));
            return Response.ok().entity(jsonToken).build();
        } catch (RuntimeException e){
            Logger.getLogger(AuthorizationRESTEndpoint.class.getName()).log(Level.SEVERE, null, e);
            throw new BasicAuthenticationException(e.getMessage());
        }
    }
    
    @POST
    @Path("/logout")
    public Response logout(@HeaderParam("authorization") String authorization){
        EntityManager em = helper.getEntityManager();
        Administrativniradnik ar = em.find(Administrativniradnik.class, Integer.parseInt(tokenHelper.decode(authorization).split("##")[1]));
        if (ar != null){
            ar.setToken(null);
            helper.mergeObject(em, ar);
            return Response.ok().entity("Uspesno ste se izlogovali!").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
