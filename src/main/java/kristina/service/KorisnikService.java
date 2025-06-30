package kristina.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import kristina.dao.KorisnikDao;
import kristina.data.Korisnik;
import kristina.exception.prodavnica_exception;
import java.util.List;
import kristina.dao.ResourcesManager;

public class KorisnikService {

    private static final KorisnikService instance = new KorisnikService();
    private final KorisnikDao korisnikDAO = KorisnikDao.getInstance();

    private KorisnikService() {
    }

    public static KorisnikService getInstance() {
        return instance;
    }
// Vrati sve korisnike

    public List<Korisnik> getAllKorisnici() throws prodavnica_exception {
        return korisnikDAO.getAllKorisnici();
    }

    // Vrati korisnika po username-u
    public Korisnik findByUsername(String username) throws prodavnica_exception {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            return korisnikDAO.find(username, con);
        } catch (SQLException ex) {
            throw new prodavnica_exception("Failed to find customer with username " + username, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
// Vrati korisnika po ID-u

    public Korisnik findKorisnikByID(int id) throws prodavnica_exception {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            return korisnikDAO.findById(id, con);
        } catch (SQLException ex) {
            throw new prodavnica_exception("Failed to find customer with ID " + id, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
// Dodaj novog korisnika u bazu

    public int addKorisnik(Korisnik k) throws prodavnica_exception {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            int korisnikId = korisnikDAO.insertKorisnik(k, con);

            con.commit();
            return korisnikId;
        } catch (SQLException ex) {
            ResourcesManager.rollbackTransactions(con);
            throw new prodavnica_exception("Greška prilikom dodavanja korisnika: " + k, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }

    // Uloguj se sa username-om i passwordom
    public String login(String username, String password) throws prodavnica_exception {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            return korisnikDAO.login(username, password, con);
        } catch (SQLException ex) {
            throw new prodavnica_exception("Prijavljivanje nije uspesno za korisnika " + username, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }

// Azuriraj podatke korisnika
    public void updateKorisnik(Korisnik korisnik) throws prodavnica_exception {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);
            korisnikDAO.updatePodatke(korisnik.getUsername(), korisnik, con);
            con.commit();
        } catch (SQLException ex) {
            ResourcesManager.rollbackTransactions(con);
            throw new prodavnica_exception("Greška prilikom ažuriranja korisnika sa ID " + korisnik.getKorisnik_id(), ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }

    // Obrisi korisnika po username-u
    public void deleteKorisnik(String username) throws prodavnica_exception {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            int affectedRows = korisnikDAO.getInstance().deleteByUsername(username, con);
            if (affectedRows == 0) {
                throw new prodavnica_exception("Korisnik sa username-om '" + username + "' ne postoji.");
            }

            con.commit();
        } catch (SQLException e) {
            ResourcesManager.rollbackTransactions(con);
            throw new prodavnica_exception("Greška prilikom brisanja korisnika iz baze.", e);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }

}
