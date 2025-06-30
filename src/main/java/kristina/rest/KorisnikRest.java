package kristina.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import kristina.data.Korisnik;
import kristina.exception.prodavnica_exception;
import kristina.service.KorisnikService;

@Path("korisnik")
public class KorisnikRest {

    private final KorisnikService korisnikService = KorisnikService.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Korisnik> getAllKorisnici() throws prodavnica_exception {
        return korisnikService.getAllKorisnici();
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Korisnik getKorisnikByUsername(@PathParam("username") String username) throws prodavnica_exception {
        return korisnikService.findByUsername(username);
    }

    @GET
    @Path("/korisnikID/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKorisnikById(@PathParam("id") int id) {
        try {
            Korisnik korisnik = korisnikService.findKorisnikByID(id);
            if (korisnik == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Korisnik nije pronađen.")
                        .build();
            }
            return Response.ok(korisnik).build();
        } catch (prodavnica_exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addKorisnik(Korisnik k) {
        try {
            korisnikService.addKorisnik(k);
            return Response.status(Response.Status.CREATED)
                    .entity("Korisnik " + k.getIme_i_prezime() + " je dodat.")
                    .build();
        } catch (prodavnica_exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(Korisnik k) throws prodavnica_exception {
        String rezultat = korisnikService.login(k.getUsername(), k.getPassword());
        if (rezultat != null) {
            return Response.ok(rezultat).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Netacno korisnicko ime ili lozinka").build();
        }
    }

    @PUT
    @Path("/update/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateKorisnik(Korisnik korisnik) {
        try {
            korisnikService.updateKorisnik(korisnik);
            return Response.ok("Korisnik je uspešno ažuriran.").build();
        } catch (prodavnica_exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Greška prilikom ažuriranja korisnika: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/by-username/{username}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteUserByUsername(@PathParam("username") String username) {
        try {
            KorisnikService.getInstance().deleteKorisnik(username);
            return Response.ok("Korisnik sa username-om '" + username + "' je uspešno obrisan.").build();
        } catch (prodavnica_exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Korisnik nije pronađen: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Došlo je do neočekivane greške: " + e.getMessage())
                    .build();
        }
    }

}
