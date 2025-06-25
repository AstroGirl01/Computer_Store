package kristina.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import kristina.data.Korisnik;
import kristina.exception.prodavnica_exception;

public class KorisnikDao {

    private static final KorisnikDao instance = new KorisnikDao();

    private KorisnikDao() {}

    public static KorisnikDao getInstance() {
        return instance;
    }

    public List<Korisnik> getAllKorisnici() throws prodavnica_exception {
        List<Korisnik> korisnici = new ArrayList<>();
        String sql = "SELECT * FROM korisnik";  
        try (Connection con = ResourcesManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Korisnik k = new Korisnik();
                k.setKorisnik_id(rs.getInt("korisnik_id"));
                k.setIme_i_prezime(rs.getString("ime_i_prezime"));
                k.setUsername(rs.getString("username"));
                k.setPassword(rs.getString("password"));
                k.setE_mail(rs.getString("e_mail"));
                k.setDatum_rodjenja(rs.getString("datum_rodjenja"));
                k.setStanje_racuna(rs.getInt("stanje_racuna"));
                k.setKolicina_potrosenog_novca(rs.getInt("kolicina_potrosenog_novca"));
                korisnici.add(k);
            }
        } catch (SQLException ex) {
            throw new prodavnica_exception("Greška prilikom učitavanja korisnika", ex);
        }
        return korisnici;
    }

    public void registracija(Korisnik k, Connection con) throws SQLException {
        String sql = "INSERT INTO korisnik(ime_i_prezime, username, password, e_mail, datum_rodjenja, stanje_racuna, kolicina_potrosenog_novca) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, k.getIme_i_prezime());
            ps.setString(2, k.getUsername());
            ps.setString(3, k.getPassword());
            ps.setString(4, k.getE_mail());
            ps.setString(5, k.getDatum_rodjenja());
            ps.setInt(6, k.getStanje_racuna());
            ps.setInt(7, k.getKolicina_potrosenog_novca());
            ps.executeUpdate();
        }
    }

    public String login(String username, String password, Connection con) throws SQLException {
        String sql = "SELECT ime_i_prezime FROM korisnik WHERE username = ? AND password = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return "Dobrodošao/la u najbolju prodavnicu računarske opreme " + rs.getString("ime_i_prezime");
                }
            }
        }
        return null;
    }

    public Korisnik find(String username, Connection con) throws SQLException {
        String sql = "SELECT * FROM korisnik WHERE username = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Korisnik(
                        rs.getInt("korisnik_id"),
                        rs.getString("ime_i_prezime"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("e_mail"),
                        rs.getString("datum_rodjenja"),
                        rs.getInt("stanje_racuna"),
                        rs.getInt("kolicina_potrosenog_novca")
                    );
                }
            }
        }
        return null;
    }

    public Korisnik findById(int korisnik_id, Connection con) throws SQLException {
        String sql = "SELECT * FROM korisnik WHERE korisnik_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, korisnik_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Korisnik(
                        korisnik_id,
                        rs.getString("ime_i_prezime"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("e_mail"),
                        rs.getString("datum_rodjenja"),
                        rs.getInt("stanje_racuna"),
                        rs.getInt("kolicina_potrosenog_novca")
                    );
                }
            }
        }
        return null;
    }

public void updateAllFields(String username, Korisnik noviPodaci, Connection con) throws SQLException {
    PreparedStatement ps = null;
    try {
        ps = con.prepareStatement(
            "UPDATE korisnik SET ime_i_prezime = ?, password = ?, e_mail = ?, datum_rodjenja = ?, stanje_racuna = ?, kolicina_potrosenog_novca = ? WHERE username = ?"
        );
        ps.setString(1, noviPodaci.getIme_i_prezime());
        ps.setString(2, noviPodaci.getPassword());
        ps.setString(3, noviPodaci.getE_mail());
        ps.setString(4, noviPodaci.getDatum_rodjenja());
        ps.setInt(5, noviPodaci.getStanje_racuna());
        ps.setInt(6, noviPodaci.getKolicina_potrosenog_novca());
        ps.setString(7, username);
        ps.executeUpdate();
    } finally {
        ResourcesManager.closeResources(null, ps);
    }
}

   
    public void deleteByUsername(String username, Connection con) throws SQLException {
        String sql = "DELETE FROM korisnik WHERE username = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.executeUpdate();
        }
    }
     

    public void updateKorisnik(Korisnik customer, Connection con) throws SQLException {
        String sql = "UPDATE korisnik SET stanje_racuna = ? WHERE username = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customer.getStanje_racuna());
            ps.setString(2, customer.getUsername());
            ps.executeUpdate();
        }
    }
    
  

    public void updatePotroseno(Korisnik customer, Connection con) throws SQLException {
        String sql = "UPDATE korisnik SET kolicina_potrosenog_novca = ? WHERE username = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customer.getKolicina_potrosenog_novca());
            ps.setString(2, customer.getUsername());
            ps.executeUpdate();
        }
    }
} 
