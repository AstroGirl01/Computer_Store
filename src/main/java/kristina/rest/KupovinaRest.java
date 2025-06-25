package kristina.rest;

import kristina.data.Kupovina;
import kristina.data.Korisnik;
import kristina.data.Proizvod;

import kristina.exception.prodavnica_exception;
import kristina.service.KupovinaService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/kupovina")
public class KupovinaRest {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllKupovine() {
        try {
            List<Kupovina> kupovine = KupovinaService.getInstance().getAllKupovine();
            return Response.ok(kupovine).build();
        } catch (prodavnica_exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Došlo je do neočekivane greške.").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKupovinaById(@PathParam("id") int id) {
        try {
            Kupovina kupovina = KupovinaService.getInstance().getKupovinaById(id);
            return Response.ok(kupovina).build();
        } catch (prodavnica_exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Neočekivana greška.").build();
        }
    }

        @POST
     @Consumes(MediaType.APPLICATION_JSON)
     @Produces(MediaType.APPLICATION_JSON)
     public Response addKupovina(Kupovina kupovina) {
         try {
             int newId = KupovinaService.getInstance().addKupovina(kupovina);
             return Response.status(Response.Status.CREATED)
                            .entity("Kupovina kreirana sa ID " + newId)
                            .build();
         } catch (prodavnica_exception e) {
             return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("Neočekivana greška pri kreiranju kupovine.")
                            .build();
         }
     }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateKupovina(@PathParam("id") int id, Kupovina kupovina) {
        try {
            kupovina.setKupovina_id(id);
            KupovinaService.getInstance().updateKupovina(kupovina);
            return Response.ok("Kupovina sa ID " + id + " je ažurirana.").build();
        } catch (prodavnica_exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Neočekivana greška.").build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteKupovina(@PathParam("id") int id) {
        try {
            KupovinaService.getInstance().deleteKupovina(id);
            return Response.ok("Kupovina sa ID " + id + " je obrisana.").build();
        } catch (prodavnica_exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Neočekivana greška.").build();
        }
    }
}
