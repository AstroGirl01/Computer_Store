package kristina.service;

import kristina.dao.Podesavanje_PretrageDao;
import kristina.data.Podesavanje_Pretrage;
import kristina.exception.prodavnica_exception;
import kristina.dao.ResourcesManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PodesavanjePretrageService {

    private static final PodesavanjePretrageService instance = new PodesavanjePretrageService();

    private PodesavanjePretrageService() {}

    public static PodesavanjePretrageService getInstance() {
        return instance;
    }

    public Podesavanje_Pretrage getPodesavanjePretrageById(int id) throws prodavnica_exception {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            return Podesavanje_PretrageDao.getInstance().find(id, con);
        } catch (SQLException e) {
            throw new prodavnica_exception("Greška prilikom pronalaženja podešavanja pretrage sa ID " + id, e);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }

    public List<Podesavanje_Pretrage> getAllPodesavanjaPretrage() throws prodavnica_exception {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            return Podesavanje_PretrageDao.getInstance().findAll(con);
        } catch (SQLException e) {
            throw new prodavnica_exception("Greška prilikom dobavljanja svih podešavanja pretrage.", e);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }

    public int addPodesavanjePretrage(Podesavanje_Pretrage podesavanje) throws prodavnica_exception {
    Connection con = null;
    try {
        con = ResourcesManager.getConnection();
        con.setAutoCommit(false);

        int podesavanjeId = Podesavanje_PretrageDao.getInstance().insert(podesavanje, con);

        con.commit();
        return podesavanjeId;
    } catch (SQLException e) {
        ResourcesManager.rollbackTransactions(con);
        throw new prodavnica_exception("Greška prilikom dodavanja podešavanja pretrage.", e);
    } finally {
        ResourcesManager.closeConnection(con);
    }
}


    public void updatePodesavanjePretrage(Podesavanje_Pretrage pp) throws prodavnica_exception {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            Podesavanje_PretrageDao.getInstance().update(pp, con);
            con.commit();
        } catch (SQLException e) {
            ResourcesManager.rollbackTransactions(con);
            throw new prodavnica_exception("Greška prilikom ažuriranja podešavanja pretrage sa ID " + pp.getPodesavanje_pretrage_id(), e);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }

    public void deletePodesavanjePretrage(int podesavanje_pretrage_id) throws prodavnica_exception {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            Podesavanje_PretrageDao.getInstance().delete(podesavanje_pretrage_id, con);
            con.commit();
        } catch (SQLException e) {
            ResourcesManager.rollbackTransactions(con);
            throw new prodavnica_exception("Greška prilikom brisanja podešavanja pretrage sa ID " + podesavanje_pretrage_id, e);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
}
