package kristina.dao;

import kristina.data.Proizvod;
import kristina.exception.prodavnica_exception;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProizvodDao {

    private static final ProizvodDao instance = new ProizvodDao();

    private ProizvodDao() {}

    public static ProizvodDao getInstance() {
        return instance;
    }

    // Vrati listu svih proizvoda iz baze
    public List<Proizvod> getAllProizvodi() throws prodavnica_exception {
        List<Proizvod> proizvodi = new ArrayList<>();
        String sql = "SELECT * FROM proizvod";  // pazi da ime tabele bude malo slovo ako takva je u bazi
        try (Connection con = ResourcesManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Proizvod p = new Proizvod();
                p.setProizvod_id(rs.getInt("proizvod_id"));
                p.setNaziv(rs.getString("naziv"));
                p.setCena(rs.getInt("cena"));
                p.setVrsta_opreme(rs.getString("vrsta_opreme"));
                p.setStanje_na_lageru(rs.getInt("stanje_na_lageru"));
                
                proizvodi.add(p);
            }
        } catch (SQLException ex) {
            throw new prodavnica_exception("Greška pri učitavanju proizvoda iz baze", ex);
        }
        return proizvodi;
    }
    
    // Pronađi proizvod po nazivu
    public Proizvod find(String naziv, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Proizvod p = null;
        try {
            ps = con.prepareStatement("SELECT * FROM proizvod WHERE naziv = ?");
            ps.setString(1, naziv);
            rs = ps.executeQuery();
            if (rs.next()) {
                p = new Proizvod(
                    rs.getInt("proizvod_id"),
                    naziv,
                    rs.getInt("cena"),
                    rs.getString("vrsta_opreme"),
                    rs.getInt("stanje_na_lageru")
                );
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return p;
    }
    
    // Pronađi proizvod po ID-u
    public Proizvod findId(int proizvodId, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Proizvod p = null;
        try {
            ps = con.prepareStatement("SELECT * FROM proizvod WHERE proizvod_id = ?");
            ps.setInt(1, proizvodId);
            rs = ps.executeQuery();
            if (rs.next()) {
                p = new Proizvod(
                    proizvodId,
                    rs.getString("naziv"),
                    rs.getInt("cena"),
                    rs.getString("vrsta_opreme"),
                    rs.getInt("stanje_na_lageru")
                );
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return p;
    }

    // Update proizvoda (npr. stanje na lageru)
    public void update(Proizvod proizvod, Connection con) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("UPDATE proizvod SET naziv = ?, cena = ?, vrsta_opreme = ?, stanje_na_lageru = ? WHERE proizvod_id = ?");
            ps.setString(1, proizvod.getNaziv());
            ps.setInt(2, proizvod.getCena());
            ps.setString(3, proizvod.getVrsta_opreme());
            ps.setInt(4, proizvod.getStanje_na_lageru());
            ps.setInt(5, proizvod.getProizvod_id());
            ps.executeUpdate();
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }

    // Ubaci novi proizvod u bazu
    public void insert(Proizvod proizvod, Connection con) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("INSERT INTO proizvod (naziv, cena, vrsta_opreme, stanje_na_lageru) VALUES (?, ?, ?, ?)");
            ps.setString(1, proizvod.getNaziv());
            ps.setInt(2, proizvod.getCena());
            ps.setString(3, proizvod.getVrsta_opreme());
            ps.setInt(4, proizvod.getStanje_na_lageru());
            ps.executeUpdate();
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }

    
    // Obriši proizvod iz baze
    public void delete(int proizvodId, Connection con) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM proizvod WHERE proizvod_id = ?");
            ps.setInt(1, proizvodId);
            ps.executeUpdate();
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }
}
