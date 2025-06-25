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

    private KupovinaDao() {}

    public static KupovinaDao getInstance() {
        return instance;
    }

     // Pronalazi kupovinu po ID-u koristeći postojeću konekciju
public Kupovina findById(int kupovina_id, Connection con) throws SQLException, prodavnica_exception {
    String sql = "SELECT * FROM kupovina WHERE kupovina_id = ?";
    Kupovina kupovina = null;

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, kupovina_id);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                // Učitavanje povezanih objekata
                Korisnik korisnik = KorisnikDao.getInstance().findById(rs.getInt("korisnik_id"), con);
                Proizvod proizvod = ProizvodDao.getInstance().findById(rs.getInt("proizvod_id"), con);

                // Kreiranje objekta Kupovina sa punim objektima korisnika i proizvoda
                kupovina = new Kupovina();
                kupovina.setKupovina_id(rs.getInt("kupovina_id"));
                kupovina.setKorisnik(korisnik);    // postavlja ceo objekat Korisnik
                kupovina.setProizvod(proizvod);    // postavlja ceo objekat Proizvod
            }
        }
    }

    return kupovina;
}

    // Vraća sve kupovine iz baze
     // Pronalazi sve kupovine
public List<Kupovina> findAllKupovine(Connection con) throws SQLException, prodavnica_exception {
    String sql = "SELECT * FROM kupovina";
    List<Kupovina> kupovine = new ArrayList<>();

    try (PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Korisnik korisnik = KorisnikDao.getInstance().findById(rs.getInt("korisnik_id"), con);
            Proizvod proizvod = ProizvodDao.getInstance().findById(rs.getInt("proizvod_id"), con);

            Kupovina kupovina = new Kupovina();
            kupovina.setKupovina_id(rs.getInt("kupovina_id"));
            kupovina.setKorisnik(korisnik);   // setuj ceo objekat korisnik
            kupovina.setProizvod(proizvod);   // setuj ceo objekat proizvod

            kupovine.add(kupovina);
        }
    }

    return kupovine;
}


 public List<Kupovina> findByKorisnikId(int korisnik_id) throws prodavnica_exception {
    List<Kupovina> kupovine = new ArrayList<>();
    String sql = "SELECT * FROM kupovina WHERE korisnik_id = ?";

    try (Connection con = ResourcesManager.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, korisnik_id);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                // Uzimamo korisnika i proizvod iz baze koristeći njihove DAO klase i ID-jeve iz ResultSeta
                Korisnik korisnik = KorisnikDao.getInstance().findById(rs.getInt("korisnik_id"), con);
                Proizvod proizvod = ProizvodDao.getInstance().findById(rs.getInt("proizvod_id"), con);

                // Kreiramo Kupovina objekat sa korisnikom i proizvodom kao objektima
                Kupovina k = new Kupovina();
                k.setKupovina_id(rs.getInt("kupovina_id"));
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

    // Dodaje novu kupovinu u bazu i postavlja generisani ID nazad u objekat
    public void addKupovina(Kupovina kupovina) throws prodavnica_exception {
        String sql = "INSERT INTO kupovina (korisnik_id, proizvod_id) VALUES (?, ?)";

        try (Connection con = ResourcesManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

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
            "INSERT INTO kupovina (korisnik_id, proizvod_id) VALUES (?, ?)",
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
            "UPDATE kupovina SET korisnik_id = ?, proizvod_id = ? WHERE kupovina_id = ?"
        );
        ps.setInt(1, kupovina.getKorisnik().getKorisnik_id());
        ps.setInt(2, kupovina.getProizvod().getProizvod_id());
        ps.setInt(3, kupovina.getKupovina_id());
        ps.executeUpdate();
    } finally {
        ResourcesManager.closeResources(null, ps);
    }
}





    // Briše kupovinu po ID-u
    
    // Briše kupovinu po ID-u
    public void deleteById(int kupovina_id, Connection con) throws SQLException {
        String sql = "DELETE FROM kupovina WHERE kupovina_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, kupovina_id);
            ps.executeUpdate();
        }
    }

    }
    

    // Helper metoda za mapiranje ResultSet-a u objekat Kupovina
    
