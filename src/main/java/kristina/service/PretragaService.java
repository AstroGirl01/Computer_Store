package kristina.service;

import kristina.dao.PretragaDao;
import kristina.data.Pretraga;
import kristina.exception.prodavnica_exception;
import kristina.dao.ResourcesManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PretragaService {

    private static final PretragaService instance = new PretragaService();

    private PretragaService() {
    }

    public static PretragaService getInstance() {
        return instance;
    }

    public Pretraga getPretragaById(int pretraga_id) throws prodavnica_exception {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            return PretragaDao.getInstance().find(pretraga_id, con);
        } catch (SQLException e) {
            throw new prodavnica_exception("Neuspešno pronalaženje pretrage sa ID " + pretraga_id, e);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }

    public List<Pretraga> getAllPretrage() throws prodavnica_exception {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            return PretragaDao.getInstance().findAll(con);
        } catch (SQLException e) {
            throw new prodavnica_exception("Neuspešno učitavanje svih pretraga.", e);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }

    public int addPretraga(Pretraga pretraga) throws prodavnica_exception {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            int pretraga_id = PretragaDao.getInstance().insert(pretraga, con);

            con.commit();
            return pretraga_id;
        } catch (SQLException e) {
            ResourcesManager.rollbackTransactions(con);
            throw new prodavnica_exception("Neuspešno dodavanje pretrage.", e);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }

    public void updatePretraga(Pretraga pretraga) throws prodavnica_exception {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            PretragaDao.getInstance().update(pretraga, con);

            con.commit();
        } catch (SQLException e) {
            ResourcesManager.rollbackTransactions(con);
            throw new prodavnica_exception("Neuspešno ažuriranje pretrage sa ID " + pretraga.getPretraga_id(), e);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }

    public void deletePretraga(int Pretraga_id) throws prodavnica_exception {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            PretragaDao.getInstance().delete(Pretraga_id, con);

            con.commit();
        } catch (SQLException e) {
            ResourcesManager.rollbackTransactions(con);
            throw new prodavnica_exception("Neuspešno brisanje pretrage sa ID " + Pretraga_id, e);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
}
