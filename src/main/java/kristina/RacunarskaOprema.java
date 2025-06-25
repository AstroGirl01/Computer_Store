package kristina;

import kristina.data.Korisnik;
import kristina.data.Proizvod;
import kristina.data.Kupovina;
import kristina.exception.prodavnica_exception;
import kristina.service.KorisnikService;
import kristina.service.ProizvodService;
import kristina.service.KupovinaService;

public class RacunarskaOprema {

    public static void main(String[] args) {
        try {
            System.out.println("Aplikacija se pokrece");

            // --- Korisnik servis ---
            KorisnikService korisnikService = KorisnikService.getInstance();
            System.out.println("\n=== Operacije sa korisnicima ===");

            Korisnik noviKorisnik = new Korisnik("Marko Markovic", "marko", "marko", "marko@example.com", "1990-01-01", 1000, 0);
            korisnikService.addKorisnik(noviKorisnik);
            System.out.println("Dodat je korisnik: " + noviKorisnik.getIme_i_prezime());
              

            Korisnik preuzetKorisnik = korisnikService.findKorisnik_id(noviKorisnik.getKorisnik_id());
            System.out.println("Preuzet je korisnik: " + preuzetKorisnik);

            // --- Proizvod servis ---
            ProizvodService proizvodService = ProizvodService.getInstance();
            System.out.println("\n=== Operacije sa proizvodima ===");

            Proizvod noviProizvod = new Proizvod( "Grafička karta", 1500, "RTX 4060", 5);
            proizvodService.addNoviProizvod(noviProizvod);
            System.out.println("Dodat proizvod: " + noviProizvod);

            Proizvod preuzetProizvod = proizvodService.findProizvod_id(noviProizvod.getProizvod_id());
            System.out.println("Preuzet proizvod: " + preuzetProizvod);

            // --- Kupovina servis ---
            KupovinaService kupovinaService = KupovinaService.getInstance();
            System.out.println("\n=== Operacije sa kupovinama ===");

           Kupovina novaKupovina = new Kupovina(preuzetKorisnik, preuzetProizvod);
           int newId = kupovinaService.addKupovina(novaKupovina);

            System.out.println("Kupovina uspešno evidentirana.");

            // --- Opcionalno brisanje (ako želiš) ---
            // proizvodService.deleteProduct(noviProizvod.getProizvod_id());
            // korisnikService.deleteCustomer(noviKorisnik.getKorisnik_id());

        } catch (prodavnica_exception e) {
            System.err.println("Greška u aplikaciji: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Neočekivana greška: " + e.getMessage());
        }
    }
}
