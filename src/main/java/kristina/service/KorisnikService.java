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

/**
 *
 * @author Kiki
 */
public class KorisnikService {
    
    private static final KorisnikService instance = new KorisnikService();
    private final KorisnikDao korisnikDAO = KorisnikDao.getInstance(); 

    private KorisnikService() {}

    public static KorisnikService getInstance() {
        return instance;
    }

    public List<Korisnik> getAllKorisnici() throws prodavnica_exception {
        return korisnikDAO.getAllKorisnici();  
    }
    
    public Korisnik findCustomer(String username) throws prodavnica_exception {
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
    
    public void addNewCustomer(Korisnik k) throws prodavnica_exception {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);
            korisnikDAO.registracija(k, con);
            con.commit();
        } catch (SQLException ex) {
            ResourcesManager.rollbackTransactions(con);
            throw new prodavnica_exception("Failed to add new customer " + k, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
    
    public String login(String username, String password) throws prodavnica_exception {
    Connection con = null;
  try {
      con = ResourcesManager.getConnection();
      return KorisnikDao.getInstance().login(username, password, con);
  } catch (SQLException ex) {
      throw new prodavnica_exception("Prijavljivanje nije uspesno za korisnika " + username, ex);
  } finally {
      ResourcesManager.closeConnection(con);
  }

    }
    
    public String login(String username, String password, Connection con) throws SQLException {
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
        preparedStatement = con.prepareStatement("SELECT ime_i_prezime FROM korisnik WHERE username = ? AND password = ?");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String imeIPrezime = resultSet.getString("ime_i_prezime");
            return "Dobrodošao u najjaču računarsku prodavnicu " + imeIPrezime;
        }
        return null;  // ako ne postoji takav korisnik
    } finally {
        ResourcesManager.closeResources(resultSet, preparedStatement);
    }
}

}

