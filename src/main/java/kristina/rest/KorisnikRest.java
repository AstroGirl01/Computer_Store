package kristina.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import kristina.data.Korisnik;
import kristina.exception.prodavnica_exception;
import kristina.service.KorisnikService;

@Path("korisnik")
public class KorisnikRest {

    private final KorisnikService korisnikService = KorisnikService.getInstance();

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Korisnik getKorisnikByUsername(@PathParam("username") String username) throws prodavnica_exception {
        return korisnikService.findCustomer(username);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Korisnik> getAllKorisnici() throws prodavnica_exception {
        return korisnikService.getAllKorisnici();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addKorisnik(Korisnik k) throws prodavnica_exception {
        korisnikService.addNewCustomer(k);
        return Response.ok().build();
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
}
