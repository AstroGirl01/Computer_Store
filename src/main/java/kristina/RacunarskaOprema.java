package kristina;

import kristina.data.*;
import kristina.service.*;
import kristina.exception.prodavnica_exception;

public class RacunarskaOprema {

    public static void main(String[] args) {
        try {
            System.out.println("Aplikacija se pokrece");

            KorisnikService korisnikService = KorisnikService.getInstance();
            System.out.println("\n=== Operacije sa korisnicima ===");

            Korisnik noviKorisnik = new Korisnik("Marko Markovic", "marko", "marko", "marko@example.com", "1990-01-01", 1000, 0);
            korisnikService.addKorisnik(noviKorisnik);
            System.out.println("Dodat je korisnik: " + noviKorisnik.getIme_i_prezime());

            Korisnik preuzetKorisnik = korisnikService.findKorisnikByID(noviKorisnik.getKorisnik_id());
            System.out.println("Preuzet je korisnik: " + preuzetKorisnik);

            ProizvodService proizvodService = ProizvodService.getInstance();
            System.out.println("\n=== Operacije sa proizvodima ===");

            Proizvod noviProizvod = new Proizvod("Grafička karta", 1500, "RTX 4060", 5);
            proizvodService.addNoviProizvod(noviProizvod);
            System.out.println("Dodat proizvod: " + noviProizvod);

            Proizvod preuzetProizvod = proizvodService.findProizvod_id(noviProizvod.getProizvod_id());
            System.out.println("Preuzet proizvod: " + preuzetProizvod);

            KupovinaService kupovinaService = KupovinaService.getInstance();
            System.out.println("\n=== Operacije sa kupovinama ===");

            Kupovina novaKupovina = new Kupovina(preuzetKorisnik, preuzetProizvod);
            int kupovinaId = kupovinaService.addKupovina(novaKupovina);
            System.out.println("Kupovina uspešno evidentirana." + kupovinaId);

            // proizvodService.deleteProduct(noviProizvod.getProizvod_id());
            // korisnikService.deleteCustomer(noviKorisnik.getKorisnik_id());
            
        } catch (prodavnica_exception e) {
            System.err.println("Greška u aplikaciji: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Neočekivana greška: " + e.getMessage());
        }
    }
}
