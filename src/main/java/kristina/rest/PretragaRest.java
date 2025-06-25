package kristina.rest;

import kristina.data.Pretraga;
import kristina.exception.prodavnica_exception;
import kristina.service.PretragaService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/pretrage")
public class PretragaRest {

    private final PretragaService pretragaService = PretragaService.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPretrage() {
        try {
            List<Pretraga> pretrage = pretragaService.getAllPretrage();
            return Response.ok(pretrage).build();
        } catch (prodavnica_exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Došlo je do neočekivane greške.").build();
        }
    }

    @GET
    @Path("/{pretraga_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPretragaById(@PathParam("pretraga_id") int pretraga_id) {
        try {
            Pretraga pretraga = pretragaService.getPretragaById(pretraga_id);
            return Response.ok(pretraga).build();
        } catch (prodavnica_exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Došlo je do neočekivane greške.").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPretraga(Pretraga pretraga) {
        try {
            int newId = pretragaService.addPretraga(pretraga);
            return Response.status(Response.Status.CREATED).entity("Pretraga kreirana sa ID " + newId).build();
        } catch (prodavnica_exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Došlo je do neočekivane greške.").build();
        }
    }

    @PUT
    @Path("/{pretraga_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePretraga(@PathParam("pretraga_id") int pretraga_id, Pretraga pretraga) {
        try {
            pretraga.setPretraga_id(pretraga_id);
            pretragaService.updatePretraga(pretraga);
            return Response.ok("Pretraga sa ID " + pretraga_id + " je ažurirana.").build();
        } catch (prodavnica_exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Došlo je do neočekivane greške.").build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePretraga(@PathParam("id") int id) {
        try {
            pretragaService.deletePretraga(id);
            return Response.ok("Pretraga sa ID " + id + " je obrisana.").build();
        } catch (prodavnica_exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Došlo je do neočekivane greške.").build();
        }
    }
}
