package kristina.data;

import java.io.Serializable;

public class Korisnik implements Serializable {
    private int korisnik_id = -1;
    private String ime_i_prezime;
    private String username;
    private String password;
    private String e_mail;
    private String datum_rodjenja;
    private int stanje_racuna;
    private int kolicina_potrosenog_novca;

    public Korisnik() {
    }

    public Korisnik(int korisnik_id, String ime_i_prezime, String username, String password,
                    String e_mail, String datum_rodjenja, int stanje_racuna, int kolicina_potrosenog_novca) {
        this.korisnik_id = korisnik_id;
        this.ime_i_prezime = ime_i_prezime;
        this.username = username;
        this.password = password;
        this.e_mail = e_mail;
        this.datum_rodjenja = datum_rodjenja;
        this.stanje_racuna = stanje_racuna;
        this.kolicina_potrosenog_novca = kolicina_potrosenog_novca;
    }

    public Korisnik(String ime_i_prezime, String username, String password,
                    String e_mail, String datum_rodjenja, int stanje_racuna, int kolicina_potrosenog_novca) {
        this.ime_i_prezime = ime_i_prezime;
        this.username = username;
        this.password = password;
        this.e_mail = e_mail;
        this.datum_rodjenja = datum_rodjenja;
        this.stanje_racuna = stanje_racuna;
        this.kolicina_potrosenog_novca = kolicina_potrosenog_novca;
    }

    public int getKorisnik_id() {
        return korisnik_id;
    }

    public void setKorisnik_id(int korisnik_id) {
        this.korisnik_id = korisnik_id;
    }

    public String getIme_i_prezime() {
        return ime_i_prezime;
    }

    public void setIme_i_prezime(String ime_i_prezime) {
        this.ime_i_prezime = ime_i_prezime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getDatum_rodjenja() {
        return datum_rodjenja;
    }

    public void setDatum_rodjenja(String datum_rodjenja) {
        this.datum_rodjenja = datum_rodjenja;
    }

    public int getStanje_racuna() {
        return stanje_racuna;
    }

    public void setStanje_racuna(int stanje_racuna) {
        this.stanje_racuna = stanje_racuna;
    }

    public int getKolicina_potrosenog_novca() {
        return kolicina_potrosenog_novca;
    }

    public void setKolicina_potrosenog_novca(int kolicina_potrosenog_novca) {
        this.kolicina_potrosenog_novca = kolicina_potrosenog_novca;
    }

    @Override
    public String toString() {
        return "korisnik{" +
                "korisnik_id=" + korisnik_id +
                ", ime_i_prezime='" + ime_i_prezime + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", e_mail='" + e_mail + '\'' +
                ", datum_rodjenja='" + datum_rodjenja + '\'' +
                ", stanje_racuna=" + stanje_racuna +
                ", kolicina_potrosenog_novca=" + kolicina_potrosenog_novca +
                '}';
    }
}
