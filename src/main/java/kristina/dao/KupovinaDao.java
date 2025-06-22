/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kristina.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import kristina.data.Kupovina;
import kristina.exception.prodavnica_exception;

public class KupovinaDao {

    private static final KupovinaDao instance = new KupovinaDao();

    private KupovinaDao() {}

    public static KupovinaDao getInstance() {
        return instance;
    }

    // Metoda za dobijanje svih kupovina
    public List<Kupovina> getAllKupovine() throws prodavnica_exception {
        List<Kupovina> kupovine = new ArrayList<>();
        String sql = "SELECT * FROM kupovina";

        try (Connection con = ResourcesManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Kupovina k = new Kupovina();
                k.setkupovina_id(rs.getInt("kupovina_id"));
                k.setKorisnik_id(rs.getInt("korisnik_id"));
                k.setProizvod_id(rs.getInt("proizvod_id"));
                kupovine.add(k);
            }
        } catch (SQLException ex) {
            throw new prodavnica_exception("Error while fetching kupovine", ex);
        }

        return kupovine;
    }

    // Metoda za dodavanje nove kupovine
    public void addKupovina(Kupovina kupovina) throws prodavnica_exception {
        String sql = "INSERT INTO kupovina (korisnik_id, proizvod_id) VALUES (?, ?)";

        try (Connection con = ResourcesManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, kupovina.getKorisnik_id());
            ps.setInt(2, kupovina.getProizvod_id());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    kupovina.setkupovina_id(rs.getInt(1));
                }
            }
        } catch (SQLException ex) {
            throw new prodavnica_exception("Error while adding kupovina", ex);
        }
    }

    // Metoda za pronalaženje kupovine prema ID-u
    public Kupovina findById(int kupovinaId) throws prodavnica_exception {
        String sql = "SELECT * FROM kupovina WHERE kupovina_id = ?";
        Kupovina kupovina = null;

        try (Connection con = ResourcesManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, kupovinaId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    kupovina = new Kupovina();
                    kupovina.setkupovina_id(rs.getInt("kupovina_id"));
                    kupovina.setKorisnik_id(rs.getInt("korisnik_id"));
                    kupovina.setProizvod_id(rs.getInt("proizvod_id"));
                }
            }
        } catch (SQLException ex) {
            throw new prodavnica_exception("Error while finding kupovina by ID", ex);
        }

        return kupovina;
    }

    // Metoda za brisanje kupovine prema ID-u
    public void deleteById(int kupovinaId) throws prodavnica_exception {
        String sql = "DELETE FROM kupovina WHERE kupovina_id = ?";

        try (Connection con = ResourcesManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, kupovinaId);
            ps.executeUpdate();

        } catch (SQLException ex) {
            throw new prodavnica_exception("Error while deleting kupovina by ID", ex);
        }
    }

    // Metoda za pronalaženje svih kupovina korisnika prema korisnik_id
    public List<Kupovina> findByKorisnikId(int korisnikId) throws prodavnica_exception {
        List<Kupovina> kupovine = new ArrayList<>();
        String sql = "SELECT * FROM kupovina WHERE korisnik_id = ?";

        try (Connection con = ResourcesManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, korisnikId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Kupovina k = new Kupovina();
                    k.setkupovina_id(rs.getInt("kupovina_id"));
                    k.setKorisnik_id(rs.getInt("korisnik_id"));
                    k.setProizvod_id(rs.getInt("proizvod_id"));
                    kupovine.add(k);
                }
            }
        } catch (SQLException ex) {
            throw new prodavnica_exception("Error while finding kupovine by korisnik_id", ex);
        }

        return kupovine;
    }
}
