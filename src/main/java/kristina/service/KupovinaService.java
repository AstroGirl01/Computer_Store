package kristina.service;

import kristina.dao.KupovinaDao;
import kristina.dao.KorisnikDao;
import kristina.dao.ProizvodDao;
import kristina.dao.ResourcesManager;
import kristina.data.Kupovina;
import kristina.data.Korisnik;
import kristina.data.Proizvod;
import kristina.exception.prodavnica_exception;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class KupovinaService {

    private static final KupovinaService instance = new KupovinaService();

    private KupovinaService() {}

    public static KupovinaService getInstance() {
        return instance;
    }

   public Kupovina getKupovinaById(int kupovina_id) throws prodavnica_exception {
    Connection con = null;
    try {
        con = ResourcesManager.getConnection();
        return KupovinaDao.getInstance().findById(kupovina_id, con);
    } catch (SQLException e) {
        throw new prodavnica_exception("Neuspešno dohvaćanje kupovine sa ID " + kupovina_id, e);
    } finally {
        ResourcesManager.closeConnection(con);
    }
}


    public List<Kupovina> getAllKupovine() throws prodavnica_exception {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            return KupovinaDao.getInstance().findAllKupovine(con);
        } catch (SQLException e) {
            throw new prodavnica_exception("Neuspešno dohvatanje svih kupovina.", e);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }

    public int addKupovina(Kupovina kupovina) throws prodavnica_exception {
    Connection con = null;
    try {
        con = ResourcesManager.getConnection();
        con.setAutoCommit(false);

        // Učitaj korisnika i proizvod iz baze preko ID-jeva u kupovini
        Korisnik korisnik = KorisnikDao.getInstance().findById(kupovina.getKorisnik().getKorisnik_id(), con);
        Proizvod proizvod = ProizvodDao.getInstance().findById(kupovina.getProizvod().getProizvod_id(), con);


        if (korisnik == null) {
            throw new prodavnica_exception("Korisnik nije pronađen.");
        }
        if (proizvod == null) {
            throw new prodavnica_exception("Proizvod nije pronađen.");
        }

        // Provera da li korisnik ima dovoljno sredstava
        if (korisnik.getStanje_racuna() < proizvod.getCena()) {
            throw new prodavnica_exception("Korisnik nema dovoljno sredstava za kupovinu.");
        }

        // Provera da li ima dovoljno proizvoda na lageru
        if (proizvod.getStanje_na_lageru() <= 0) {
            throw new prodavnica_exception("Proizvod je trenutno rasprodat.");
        }

        // Umanji iznos na računu korisnika
        int novoStanje = korisnik.getStanje_racuna() - proizvod.getCena();
        korisnik.setStanje_racuna(novoStanje);
        KorisnikDao.getInstance().updateKorisnik(korisnik, con);

        // Umanji količinu proizvoda na lageru
        int novaKolicina = proizvod.getStanje_na_lageru() - 1;
        proizvod.setStanje_na_lageru(novaKolicina);
        ProizvodDao.getInstance().update(proizvod.getProizvod_id(), proizvod, con);

        // Ubaci kupovinu u bazu
        int kupovina_id = KupovinaDao.getInstance().update(kupovina, con);

        // Potvrdi transakciju
        con.commit();
        return kupovina_id;

    } catch (SQLException e) {
        ResourcesManager.rollbackTransactions(con);
        throw new prodavnica_exception("Kupovina nije uspešno izvršena.", e);
    } finally {
        ResourcesManager.closeConnection(con);
    }
}




    public void updateKupovina(Kupovina kupovina) throws prodavnica_exception {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            KupovinaDao.getInstance().update(kupovina, con);

            con.commit();
        } catch (SQLException e) {
            ResourcesManager.rollbackTransactions(con);
            throw new prodavnica_exception("Neuspešno ažuriranje kupovine sa ID " + kupovina.getKupovina_id(), e);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }

    public void deleteKupovina(int kupovina_id) throws prodavnica_exception {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            KupovinaDao.getInstance().deleteById(kupovina_id, con);

            con.commit();
        } catch (SQLException e) {
            ResourcesManager.rollbackTransactions(con);
            throw new prodavnica_exception("Neuspešno brisanje kupovine sa ID " + kupovina_id, e);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
}
