package kristina.service;

import java.sql.Connection;
import java.sql.SQLException;
import kristina.dao.ResourcesManager;
import kristina.dao.KorisnikDao;
import kristina.dao.ProizvodDao;
import kristina.dao.KupovinaDao;
import kristina.data.Korisnik;
import kristina.data.Proizvod;
import kristina.data.Kupovina;
import kristina.exception.prodavnica_exception;

public class KupovinaService {

    private static final KupovinaService instance = new KupovinaService();

    private KupovinaService() {}

    public static KupovinaService getInstance() {
        return instance;
    }

    public void makeKupovina(Korisnik customer, Proizvod product) throws prodavnica_exception {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            if (product.getStanje_na_lageru() == 0) {
                throw new prodavnica_exception("Nema više proizvoda " + product.getNaziv() + " na lageru.");
            }

            if (customer.getStanjeRacuna() < product.getCena()) {
                throw new prodavnica_exception("Korisnik nema dovoljno novca za kupovinu. Stanje: " 
                    + customer.getStanjeRacuna() + ", cena proizvoda: " + product.getCena());
            }

            int novoStanje = customer.getStanjeRacuna() - product.getCena();
            customer.setStanjeRacuna(novoStanje);
            KorisnikDao.getInstance().update(customer, con);

            int novoPotroseno = customer.getKolicinaPotrosenogNovca() + product.getCena();
            customer.setKolicinaPotrosenogNovca(novoPotroseno);
            KorisnikDao.getInstance().updatePotroseno(customer, con);

            product.setStanje_na_lageru(product.getStanje_na_lageru() - 1);
            ProizvodDao.getInstance().update(product, con);

            Kupovina novaKupovina = new Kupovina(customer.getKorisnik_id(), product.getProizvod_id());
            KupovinaDao.getInstance().addKupovina(novaKupovina);

            con.commit();

            System.out.println("Korisnik " + customer.getUsername() + " je kupio proizvod " + product.getNaziv() + " po ceni " + product.getCena());
        } catch (SQLException ex) {
            ResourcesManager.rollbackTransactions(con);
            System.err.println("SQL greška: " + ex.getMessage());
            throw new prodavnica_exception("Neuspela kupovina", ex);
        }
    }
  }