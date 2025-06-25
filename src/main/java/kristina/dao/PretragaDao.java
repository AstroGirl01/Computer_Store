package kristina.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import kristina.data.Pretraga;
import kristina.data.Korisnik;
import kristina.data.Podesavanje_Pretrage;     
import kristina.dao.KorisnikDao;
import kristina.dao.Podesavanje_PretrageDao;    


public class PretragaDao {

    private static final PretragaDao instance = new PretragaDao();

    private PretragaDao() {}

    public static PretragaDao getInstance() {
        return instance;
    }

    public Pretraga find(int pretraga_id, Connection con) throws SQLException {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Pretraga pretraga = null;

    try {
        ps = con.prepareStatement("SELECT * FROM pretraga WHERE pretraga_id=?");
        ps.setInt(1, pretraga_id);
        rs = ps.executeQuery();

        if (rs.next()) {
            // Učitaj ID-jeve
            int korisnikId = rs.getInt("korisnik_id");
            int podesavanjeId = rs.getInt("podesavanje_pretrage_id");

            // Učitaj povezane objekte pomoću DAO-a
            Korisnik korisnik = KorisnikDao.getInstance().findById(korisnikId, con);
            Podesavanje_Pretrage podesavanje = Podesavanje_PretrageDao.getInstance().find(pretraga_id, con);

            // Kreiraj objekat Pretraga sa pravim objektima
            pretraga = new Pretraga(
                rs.getInt("pretraga_id"),
                korisnik,
                podesavanje
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
            // Učitaj povezane objekte preko ID-jeva iz tabele
            Korisnik korisnik = KorisnikDao.getInstance().findById(rs.getInt("korisnik_id"), con);
            Podesavanje_Pretrage podesavanje = Podesavanje_PretrageDao.getInstance().find(rs.getInt("podesavanje_pretrage_id"), con);

            // Dodaj novu instancu Pretraga sa objektima
            pretrage.add(new Pretraga(
                    rs.getInt("pretraga_id"),
                    korisnik,
                    podesavanje
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
        int pretraga_id = -1;
        try {
            ps = con.prepareStatement(
                    "INSERT INTO pretraga (podesavanje_pretrage_id, korisnik_id) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setInt(1, pretraga.getPodesavanje_pretrage().getPodesavanje_pretrage_id());
            ps.setInt(2, pretraga.getKorisnik().getKorisnik_id());


            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                pretraga_id = rs.getInt(1);
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return pretraga_id;
    }

    public void update(Pretraga pretraga, Connection con) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(
                    "UPDATE pretraga SET podesavanje_pretrage_id=?, korisnik_id=? WHERE pretraga_id=?"
            );
            ps.setInt(1, pretraga.getPodesavanje_pretrage().getPodesavanje_pretrage_id());
            ps.setInt(2, pretraga.getKorisnik().getKorisnik_id());
            ps.setInt(3, pretraga.getPretraga_id());
            ps.executeUpdate();
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }

    public void delete(int Pretraga_id, Connection con) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM pretraga WHERE pretraga_id=?");
            ps.setInt(1, Pretraga_id);
            ps.executeUpdate();
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }
}
