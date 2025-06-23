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
                k.setKorisnikId(rs.getInt("korisnik_id"));
                k.setImeIPrezime(rs.getString("ime_i_prezime"));
                k.setUsername(rs.getString("username"));
                k.setPassword(rs.getString("password"));
                k.setEmail(rs.getString("e_mail"));
                k.setDatumRodjenja(rs.getString("datum_rodjenja"));
                k.setStanjeRacuna(rs.getInt("stanje_racuna"));
                k.setKolicinaPotrosenogNovca(rs.getInt("kolicina_potrosenog_novca"));
                korisnici.add(k);
            }
        } catch (SQLException ex) {
            throw new prodavnica_exception("Greška prilikom učitavanja korisnika", ex);
        }
        return korisnici;
    }

    public void registracija(Korisnik k, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("INSERT INTO korisnik(ime_i_prezime, username, password, e_mail, datum_rodjenja, stanje_racuna, kolicina_potrosenog_novca) VALUES(?,?,?,?,?,?,?)");
            
            ps.setString(1, k.getImeIPrezime());
            ps.setString(2, k.getUsername());
            ps.setString(3, k.getPassword());
            ps.setString(4, k.getEmail());
            ps.setString(5, k.getDatumRodjenja());
            ps.setInt(6, k.getStanjeRacuna());
            ps.setInt(7, k.getKolicinaPotrosenogNovca());
            
            ps.executeUpdate();

        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
    }
    
    public String login(String username, String password, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = con.prepareStatement("SELECT ime_i_prezime FROM korisnik WHERE username = ? AND password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                String imeIPrezime = rs.getString("ime_i_prezime");
                return "Dobrodošao u najjaču računarsku prodavnicu " + imeIPrezime;
            } 
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return null;
    }
    
    public Korisnik find(String username, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Korisnik k = null;
        try {
            ps = con.prepareStatement("SELECT * FROM korisnik WHERE username = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                k = new Korisnik(
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
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return k;
    }
    
    public Korisnik findID(int korisnikId, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Korisnik k = null;
        try {
            ps = con.prepareStatement("SELECT * FROM korisnik WHERE korisnik_id = ?");
            ps.setInt(1, korisnikId);
            rs = ps.executeQuery();
            if (rs.next()) {
                k = new Korisnik(
                    korisnikId,
                    rs.getString("ime_i_prezime"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("e_mail"),
                    rs.getString("datum_rodjenja"),
                    rs.getInt("stanje_racuna"),
                    rs.getInt("kolicina_potrosenog_novca")
                );
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return k;
    }

    public void update(Korisnik customer, Connection con) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("UPDATE korisnik SET stanje_racuna = ? WHERE username = ?");
            ps.setInt(1, customer.getStanjeRacuna());
            ps.setString(2, customer.getUsername());
            ps.executeUpdate();
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }
    
    public void updatePotroseno(Korisnik customer, Connection con) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("UPDATE korisnik SET kolicina_potrosenog_novca = ? WHERE username = ?");
            ps.setInt(1, customer.getKolicinaPotrosenogNovca());
            ps.setString(2, customer.getUsername());
            ps.executeUpdate();
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }
}
