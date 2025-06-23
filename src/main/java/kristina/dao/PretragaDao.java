package kristina.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import kristina.data.Pretraga;

public class PretragaDao {

    private static final PretragaDao instance = new PretragaDao();

    private PretragaDao() {}

    public static PretragaDao getInstance() {
        return instance;
    }

    public Pretraga find(int idPretraga, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Pretraga pretraga = null;
        try {
            ps = con.prepareStatement("SELECT * FROM pretraga WHERE id_pretraga=?");
            ps.setInt(1, idPretraga);
            rs = ps.executeQuery();
            if (rs.next()) {
                pretraga = new Pretraga(
                        rs.getInt("id_pretraga"),
                        rs.getInt("fk_pretraga_postavke"),
                        rs.getInt("fk_korisnik")
                );
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return pretraga;
    }

    public List<Pretraga> findAll(Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Pretraga> pretrage = new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT * FROM pretraga");
            rs = ps.executeQuery();
            while (rs.next()) {
                pretrage.add(new Pretraga(
                        rs.getInt("id_pretraga"),
                        rs.getInt("fk_pretraga_postavke"),
                        rs.getInt("fk_korisnik")
                ));
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return pretrage;
    }

    public int insert(Pretraga pretraga, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = -1;
        try {
            ps = con.prepareStatement(
                    "INSERT INTO pretraga (fk_pretraga_postavke, fk_korisnik) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setInt(1, pretraga.getPodesavanje_Pretrage_id());
            ps.setInt(2, pretraga.getKorisnik_id());

            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return id;
    }

    public void update(Pretraga pretraga, Connection con) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(
                    "UPDATE pretraga SET fk_pretraga_postavke=?, fk_korisnik=? WHERE id_pretraga=?"
            );
            ps.setInt(1, pretraga.getPodesavanje_Pretrage_id());
            ps.setInt(2, pretraga.getKorisnik_id());
            ps.setInt(3, pretraga.getPretraga_id());
            ps.executeUpdate();
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }

    public void delete(int idPretraga, Connection con) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM pretraga WHERE id_pretraga=?");
            ps.setInt(1, idPretraga);
            ps.executeUpdate();
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }
}
