package kristina.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import kristina.data.Korisnik;
import kristina.data.Kupovina;
import kristina.data.Proizvod;
import kristina.exception.prodavnica_exception;
import kristina.service.KorisnikService;
import kristina.service.KupovinaService;
import kristina.service.ProizvodService;

/**
 * REST servis za obradu kupovine proizvoda
 */
@Path("kupovina")
public class KupovinaRest {

    private final KupovinaService kupovinaService = KupovinaService.getInstance();

    @POST
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response makeKupovina(Kupovina kupovina) {
    try {
        Korisnik korisnik = KorisnikService.findById(kupovina.getKorisnik_id());
        Proizvod proizvod = ProizvodService.findById(kupovina.getProizvod_id());

        if (korisnik == null || proizvod == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Nevalidan korisnik ili proizvod.")
                           .build();
        }

        kupovinaService.makeKupovina(korisnik, proizvod);
        return Response.ok().build();
    } catch (Exception e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                       .entity("Došlo je do greške na serveru.")
                       .build();
    }
}

  