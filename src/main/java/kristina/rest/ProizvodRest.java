package kristina.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import kristina.data.Proizvod;
import kristina.exception.prodavnica_exception;
import kristina.service.ProizvodService;

@Path("/proizvod")
public class ProizvodRest {

    private final ProizvodService proizvodService = ProizvodService.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllProizvodi() {
        try {
            System.out.println("Fetching all proizvodi...");
            List<Proizvod> proizvodi = proizvodService.findAllProizvodi();
            return Response.ok(proizvodi).build();
        } catch (prodavnica_exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Došlo je do neočekivane greške.").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findProizvod_id(@PathParam("id") int id) {
        try {
            System.out.println("Fetching product with ID: " + id);
            Proizvod proizvod = ProizvodService.getInstance().findProizvod_id(id);
            return Response.ok(proizvod).build();
        } catch (prodavnica_exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An unexpected error occurred.").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProizvod(Proizvod proizvod) {
        try {
            System.out.println("Dodavanje proizvoda: " + proizvod);
            int noviId = proizvodService.addNoviProizvod(proizvod);
            System.out.println("Proizvod dodat sa ID: " + noviId);
            return Response.status(Response.Status.CREATED)
                    .entity("Proizvod kreiran sa ID " + noviId)
                    .build();
        } catch (prodavnica_exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Došlo je do neočekivane greške.").build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProizvod(@PathParam("id") int id, Proizvod proizvod) {
        try {
            proizvod.setProizvod_id(id);
            System.out.println("Ažuriranje proizvoda: " + proizvod);
            proizvodService.updateProizvod(proizvod);
            System.out.println("Proizvod ažuriran sa ID: " + id);
            return Response.ok("Proizvod sa ID " + id + " je ažuriran.").build();
        } catch (prodavnica_exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Došlo je do neočekivane greške.").build();
        }
    }

    @DELETE
    @Path("/naziv/{naziv}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProizvod(@PathParam("naziv") String naziv) throws prodavnica_exception {
        System.out.println("Brisanje proizvoda sa nazivom: " + naziv);
        proizvodService.deleteProizvodByName(naziv);
        System.out.println("Proizvod obrisan sa nazivom: " + naziv);
        return Response.ok("Proizvod pod nazivom " + naziv + " obrisan.").build();
    }

}
