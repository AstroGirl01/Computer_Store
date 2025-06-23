package kristina.dao;

import kristina.data.Podesavanje_Pretrage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Podesavanje_PretrageDao {

    private static final Podesavanje_PretrageDao instance = new Podesavanje_PretrageDao();

    private Podesavanje_PretrageDao() {}

    public static Podesavanje_PretrageDao getInstance() {
        return instance;
    }

    public Podesavanje_Pretrage find(int id, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Podesavanje_Pretrage pp = null;

        try {
            ps = con.prepareStatement("SELECT * FROM podesavanje_pretrage WHERE podesavanje_pretrage_id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                pp = new Podesavanje_Pretrage(
                    rs.getInt("podesavanje_pretrage_id"),
                    rs.getLong("min_cena"),
                    rs.getLong("max_cena"),
                    rs.getString("tip"),
                    rs.getString("kljucna_rec")
                );
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }

        return pp;
    }

    public List<Podesavanje_Pretrage> findAll(Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Podesavanje_Pretrage> list = new ArrayList<>();

        try {
            ps = con.prepareStatement("SELECT * FROM podesavanje_pretrage");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Podesavanje_Pretrage(
                    rs.getInt("podesavanje_pretrage_id"),
                    rs.getLong("min_cena"),
                    rs.getLong("max_cena"),
                    rs.getString("tip"),
                    rs.getString("kljucna_rec")
                ));
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }

        return list;
    }

    public int insert(Podesavanje_Pretrage pp, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = -1;

        try {
            ps = con.prepareStatement(
                "INSERT INTO podesavanje_pretrage (min_cena, max_cena, tip, kljucna_rec) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );
            ps.setLong(1, pp.getMinCena());
            ps.setLong(2, pp.getMaxCena());
            ps.setString(3, pp.getTip());
            ps.setString(4, pp.getKljucnaRec());

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

    public void update(Podesavanje_Pretrage pp, Connection con) throws SQLException {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(
                "UPDATE podesavanje_pretrage SET min_cena=?, max_cena=?, tip=?, kljucna_rec=? WHERE podesavanje_pretrage_id=?"
            );
            ps.setLong(1, pp.getMinCena());
            ps.setLong(2, pp.getMaxCena());
            ps.setString(3, pp.getTip());
            ps.setString(4, pp.getKljucnaRec());
            ps.setInt(5, pp.getPodesavanje_pretrage_id());

            ps.executeUpdate();
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }

    public void delete(int id, Connection con) throws SQLException {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("DELETE FROM podesavanje_pretrage WHERE podesavanje_pretrage_id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }
}
