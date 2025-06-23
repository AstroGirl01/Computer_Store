/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kristina.service;

import java.sql.Connection;
import java.sql.SQLException;
import kristina.dao.ProizvodDao;
import kristina.data.Proizvod;
import kristina.exception.prodavnica_exception;
import java.util.List;
import kristina.dao.ResourcesManager;

public class ProizvodService {
    
    private static final ProizvodService instance = new ProizvodService();
    private final ProizvodDao proizvodDAO = ProizvodDao.getInstance();

    private ProizvodService() {}

    public static ProizvodService getInstance() {
        return instance;
    }

    // Metoda koja vraća sve proizvode
    public List<Proizvod> getAllProizvodi() throws prodavnica_exception {
        return proizvodDAO.getAllProizvodi();
    }
    
    // Pretraga proizvoda po nazivu
    public Proizvod findProizvod(String naziv) throws prodavnica_exception {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            return proizvodDAO.find(naziv, con);
        } catch (SQLException ex) {
            throw new prodavnica_exception("Failed to find product with name " + naziv, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }

    // Pretraga proizvoda po ID-u (za detaljan prikaz)
    public Proizvod findProizvodId(int proizvodId) throws prodavnica_exception {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            return ProizvodDao.getInstance().findId(proizvodId, con);
        } catch (SQLException ex) {
            throw new prodavnica_exception("Failed to find product with ID " + proizvodId, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
    
    public void addNoviProizvod(Proizvod proizvod) throws prodavnica_exception {
    Connection con = null;
    try {
        con = ResourcesManager.getConnection();
        con.setAutoCommit(false);

        proizvodDAO.insert(proizvod, con); // Ova metoda treba da postoji u ProizvodDao
        con.commit();
    } catch (SQLException ex) {
        ResourcesManager.rollbackTransactions(con);
        throw new prodavnica_exception("Failed to add new product: " + proizvod, ex);
    } finally {
        ResourcesManager.closeConnection(con);
    }
}

}

