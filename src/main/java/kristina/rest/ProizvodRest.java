package kristina.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import javax.ws.rs.PathParam;
import kristina.data.Proizvod;
import kristina.exception.prodavnica_exception;
import kristina.service.ProizvodService;

@Path("proizvod")
public class ProizvodRest {

    private final ProizvodService proizvodService = ProizvodService.getInstance();

    @GET
    @Path("/naziv/{naziv}")
    @Produces(MediaType.APPLICATION_JSON)
    public Proizvod getProizvodByName(@PathParam("naziv") String naziv) throws prodavnica_exception {
        return proizvodService.findProizvod(naziv);
    }

    @GET
    @Path("/id/{proizvodId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Proizvod getProizvodById(@PathParam("proizvodId") int proizvodId) throws prodavnica_exception {
        return proizvodService.findProizvod_id(proizvodId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Proizvod> getAllProizvodi() throws prodavnica_exception {
        return proizvodService.getAllProizvodi();
    }
}
