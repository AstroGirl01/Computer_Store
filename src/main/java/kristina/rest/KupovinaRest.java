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
import kristina.service.KorisnikService;
import kristina.service.KupovinaService;
import kristina.service.ProizvodService;

@Path("kupovina")
public class KupovinaRest {

    private final KupovinaService kupovinaService = KupovinaService.getInstance();
    private final KorisnikService korisnikService = KorisnikService.getInstance();
    private final ProizvodService proizvodService = ProizvodService.getInstance();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response makeKupovina(Kupovina kupovina) {
        try {
            Korisnik korisnik = korisnikService.findKorisnik_id(kupovina.getKorisnik_id());
            Proizvod proizvod = proizvodService.findProizvod_id(kupovina.getProizvod_id());

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
}
