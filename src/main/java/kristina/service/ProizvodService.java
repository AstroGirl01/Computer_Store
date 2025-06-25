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
   public List<Proizvod> findAllProizvodi() throws prodavnica_exception {
    Connection con = null;
    try {
        con = ResourcesManager.getConnection();
        return ProizvodDao.getInstance().findAllProizvodi(con);
    } catch (SQLException e) {
        throw new prodavnica_exception("Failed to retrieve all products.", e);
    } finally {
        ResourcesManager.closeConnection(con);
    }
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
    public Proizvod findProizvod_id(int proizvod_id) throws prodavnica_exception {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            return ProizvodDao.getInstance().findById(proizvod_id, con);
        } catch (SQLException ex) {
            throw new prodavnica_exception("Failed to find product with ID " + proizvod_id, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
    
    public void updateProizvod(Proizvod proizvod) throws prodavnica_exception {
    Connection con = null;
    try {
        con = ResourcesManager.getConnection();
        con.setAutoCommit(false);

        ProizvodDao.getInstance().update(proizvod.getProizvod_id(), proizvod, con);

        con.commit();
    } catch (SQLException e) {
        ResourcesManager.rollbackTransactions(con);
        throw new prodavnica_exception("Neuspešno ažuriranje proizvoda sa ID " + proizvod.getProizvod_id(), e);
    } finally {
        ResourcesManager.closeConnection(con);
    }
}

    public int addNoviProizvod(Proizvod proizvod) throws prodavnica_exception {
    Connection con = null;
    try {
        con = ResourcesManager.getConnection();
        con.setAutoCommit(false);

        int noviId = ProizvodDao.getInstance().insert(proizvod, con);

        con.commit();

        return noviId;
    } catch (SQLException e) {
        ResourcesManager.rollbackTransactions(con);
        throw new prodavnica_exception("Greška prilikom dodavanja proizvoda", e);
    } finally {
        ResourcesManager.closeConnection(con);
    }
}


    public void deleteProizvodByName(String naziv) throws prodavnica_exception {
    try (Connection con = ResourcesManager.getConnection()) {
        proizvodDAO.deleteByName(naziv, con);
    } catch (Exception e) {
        throw new prodavnica_exception("Greška prilikom brisanja proizvoda po nazivu: " + e.getMessage(), e);
    }
}

}

