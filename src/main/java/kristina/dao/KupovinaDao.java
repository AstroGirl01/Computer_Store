package kristina.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import kristina.data.Korisnik;
import kristina.data.Kupovina;
import kristina.data.Proizvod;
import kristina.exception.prodavnica_exception;

public class KupovinaDao {

    private static final KupovinaDao instance = new KupovinaDao();

    private KupovinaDao() {
    }

    public static KupovinaDao getInstance() {
        return instance;
    }

    public Kupovina findById(int kupovina_id, Connection con) throws SQLException, prodavnica_exception {
        String sql = "SELECT * FROM kupovina WHERE id_kupovina = ?";
        Kupovina kupovina = null;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, kupovina_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    Korisnik korisnik = KorisnikDao.getInstance().findById(rs.getInt("fk_korisnik"), con);
                    Proizvod proizvod = ProizvodDao.getInstance().findById(rs.getInt("fk_proizvod"), con);

                    kupovina = new Kupovina();
                    kupovina.setKupovina_id(rs.getInt("id_kupovina"));
                    kupovina.setKorisnik(korisnik);
                    kupovina.setProizvod(proizvod);
                }
            }
        }

        return kupovina;
    }

    public List<Kupovina> findAllKupovine(Connection con) throws SQLException, prodavnica_exception {
        String sql = "SELECT * FROM kupovina";
        List<Kupovina> kupovine = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Korisnik korisnik = KorisnikDao.getInstance().findById(rs.getInt("fk_korisnik"), con);
                Proizvod proizvod = ProizvodDao.getInstance().findById(rs.getInt("fk_proizvod"), con);

                Kupovina kupovina = new Kupovina();
                kupovina.setKupovina_id(rs.getInt("id_kupovina"));
                kupovina.setKorisnik(korisnik);
                kupovina.setProizvod(proizvod);

                kupovine.add(kupovina);
            }
        }

        return kupovine;
    }

    public List<Kupovina> findByKorisnikId(int korisnik_id) throws prodavnica_exception {
        List<Kupovina> kupovine = new ArrayList<>();
        String sql = "SELECT * FROM kupovina WHERE fk_korisnik = ?";

        try (Connection con = ResourcesManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, korisnik_id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    Korisnik korisnik = KorisnikDao.getInstance().findById(rs.getInt("fk_korisnik"), con);
                    Proizvod proizvod = ProizvodDao.getInstance().findById(rs.getInt("fk_proizvod"), con);

                    Kupovina k = new Kupovina();
                    k.setKupovina_id(rs.getInt("id_kupovina"));
                    k.setKorisnik(korisnik);
                    k.setProizvod(proizvod);

                    kupovine.add(k);
                }
            }
        } catch (SQLException e) {
            throw new prodavnica_exception("Greška prilikom pronalaženja kupovina po korisnik_id", e);
        }

        return kupovine;
    }

    public void addKupovina(Kupovina kupovina) throws prodavnica_exception {
        String sql = "INSERT INTO kupovina (fk_korisnik, fk_proizvod) VALUES (?, ?)";

        try (Connection con = ResourcesManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, kupovina.getKorisnik().getKorisnik_id());
            ps.setInt(2, kupovina.getProizvod().getProizvod_id());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new prodavnica_exception("Dodavanje kupovine nije uspelo, nije kreiran nijedan red.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    kupovina.setKupovina_id(rs.getInt(1));
                } else {
                    throw new prodavnica_exception("Nije moguće dobiti ID nove kupovine.");
                }
            }
        } catch (SQLException ex) {
            throw new prodavnica_exception("Greška prilikom dodavanja kupovine", ex);
        }
    }

    public int insert(Kupovina kupovina, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = -1;
        try {
            ps = con.prepareStatement(
                    "INSERT INTO kupovina (fk_korisnik, fk_proizvod) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setInt(1, kupovina.getKorisnik().getKorisnik_id());
            ps.setInt(2, kupovina.getProizvod().getProizvod_id());
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

    public void update(Kupovina kupovina, Connection con) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(
                    "UPDATE kupovina SET fk_korisnik = ?, fk_proizvod = ? WHERE id_kupovina = ?"
            );
            ps.setInt(1, kupovina.getKorisnik().getKorisnik_id());
            ps.setInt(2, kupovina.getProizvod().getProizvod_id());
            ps.setInt(3, kupovina.getKupovina_id());
            ps.executeUpdate();
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }

    public void deleteById(int kupovina_id, Connection con) throws SQLException {
        String sql = "DELETE FROM kupovina WHERE id_kupovina = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, kupovina_id);
            ps.executeUpdate();
        }
    }

}
