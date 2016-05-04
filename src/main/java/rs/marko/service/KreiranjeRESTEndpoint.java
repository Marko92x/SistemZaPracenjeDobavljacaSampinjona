/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.marko.service;

import java.io.File;
import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import rs.marko.domain.Stavkadnevneberbe;
import rs.marko.helper.HelperClass;
import rs.marko.helper.Kreiranje;

/**
 *
 * @author Mare
 */
@Path("kreiraj")
public class KreiranjeRESTEndpoint {
    
    HelperClass helper;

    public KreiranjeRESTEndpoint() {
        helper = new HelperClass();
    }
    
    @GET
    @Path("obracun/{datumOd}/{datumDo}")
    public Response kreirajObracun(@HeaderParam("authorization") String authorization, @PathParam("datumOd") String datumOd, @PathParam("datumDo") String datumDo){
        EntityManager em = helper.getEntityManager();
        List<Stavkadnevneberbe> stavke = em.createQuery("Select s from Stavkadnevneberbe s join s.dnevnaberba d where d.datum between '" + datumOd + "' and '" + datumDo + "'", Stavkadnevneberbe.class).getResultList();
        String [] odNiz = datumOd.split("-");
        String [] doNiz = datumDo.split("-");
        GregorianCalendar dOd = new GregorianCalendar(Integer.parseInt(odNiz[0]), Integer.parseInt(odNiz[1]), Integer.parseInt(odNiz[2]));
        GregorianCalendar dDo = new GregorianCalendar(Integer.parseInt(doNiz[0]), Integer.parseInt(doNiz[1]), Integer.parseInt(doNiz[2]));
        Date datumOdD = new Date(dOd.getTimeInMillis());
        Date datumDoD = new Date(dDo.getTimeInMillis());
//        Kreiranje.createPdfObracuni(stavke, datumOdD, datumDoD);
        File f = Kreiranje.createPdfObracuni(stavke, datumOdD, datumDoD);
        Response.ResponseBuilder responseBuilder = Response.ok(f);
        responseBuilder.header("Content-Disposition", "attachment; filename=\"" + f.getName() + "\"");
        return responseBuilder.build();
    }
    
    @GET
    @Path("statistika/{datumOd}/{datumDo}")
    public Response kreirajStatistiku(@HeaderParam("authorization") String authorization, @PathParam("datumOd") String datumOd, @PathParam("datumDo") String datumDo){
        EntityManager em = helper.getEntityManager();
        List<Stavkadnevneberbe> stavke = em.createQuery("Select s from Stavkadnevneberbe s join s.dnevnaberba d where d.datum between '" + datumOd + "' and '" + datumDo + "'", Stavkadnevneberbe.class).getResultList();
        String [] odNiz = datumOd.split("-");
        String [] doNiz = datumDo.split("-");
        GregorianCalendar dOd = new GregorianCalendar(Integer.parseInt(odNiz[0]), Integer.parseInt(odNiz[1]), Integer.parseInt(odNiz[2]));
        GregorianCalendar dDo = new GregorianCalendar(Integer.parseInt(doNiz[0]), Integer.parseInt(doNiz[1]), Integer.parseInt(doNiz[2]));
        Date datumOdD = new Date(dOd.getTimeInMillis());
        Date datumDoD = new Date(dDo.getTimeInMillis());
//        Kreiranje.createPdfObracuni(stavke, datumOdD, datumDoD);
        File f = Kreiranje.createPdfStatistika(stavke, datumOdD, datumDoD);
        Response.ResponseBuilder responseBuilder = Response.ok(f);
        responseBuilder.header("Content-Disposition", "attachment; filename=\"" + f.getName() + "\"");
        return responseBuilder.build();
    }
}
