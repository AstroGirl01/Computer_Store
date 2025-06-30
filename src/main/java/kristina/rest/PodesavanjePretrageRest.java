/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kristina.rest;

/**
 *
 * @author MyPC
 */

import kristina.data.Podesavanje_Pretrage;
import kristina.exception.prodavnica_exception;
import kristina.service.PodesavanjePretrageService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/podesavanja-pretrage")
public class PodesavanjePretrageRest {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPodesavanjaPretrage() {
        try {
            List<Podesavanje_Pretrage> lista = PodesavanjePretrageService.getInstance().getAllPodesavanjaPretrage();
            return Response.ok(lista).build();
        } catch (prodavnica_exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Došlo je do neočekivane greške.").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPodesavanjePretrageById(@PathParam("id") int id) {
        try {
            Podesavanje_Pretrage podesavanje = PodesavanjePretrageService.getInstance().getPodesavanjePretrageById(id);
            return Response.ok(podesavanje).build();
        } catch (prodavnica_exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Neočekivana greška.").build();
        }
    }

@POST
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response addPodesavanjePretrage(Podesavanje_Pretrage podesavanje) {
    try {
        int newId = PodesavanjePretrageService.getInstance().addPodesavanjePretrage(podesavanje);
        return Response.status(Response.Status.CREATED).entity("Podešavanje pretrage kreirano sa ID " + newId).build();
    } catch (prodavnica_exception e) {
        e.printStackTrace(); // DODATO
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    } catch (Exception e) {
        e.printStackTrace(); // DODATO
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Neočekivana greška.").build();
    }
}


    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePodesavanjePretrage(@PathParam("id") int id, Podesavanje_Pretrage podesavanje) {
        try {
            podesavanje.setPodesavanje_pretrage_id(id);
            PodesavanjePretrageService.getInstance().updatePodesavanjePretrage(podesavanje);
            return Response.ok("Podešavanje pretrage sa ID " + id + " je ažurirano.").build();
        } catch (prodavnica_exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Neočekivana greška.").build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePodesavanjePretrage(@PathParam("id") int id) {
        try {
            PodesavanjePretrageService.getInstance().deletePodesavanjePretrage(id);
            return Response.ok("Podešavanje pretrage sa ID " + id + " je obrisano.").build();
        } catch (prodavnica_exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Neočekivana greška.").build();
        }
    }
}
