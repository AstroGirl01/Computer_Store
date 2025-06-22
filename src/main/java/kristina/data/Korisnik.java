package kristina.data;

import java.io.Serializable;

public class Korisnik implements Serializable {
    private int korisnikId = -1;
    private String ime_i_prezime;
    private String username;
    private String password;
    private String e_mail;
    private String datum_rodjenja;
    private int stanje_racuna;
    private int kolicina_potrosenog_novca;

    
    public Korisnik() {
    }

    
    public Korisnik(int korisnikId, String ime_i_prezime, String username,String password, String e_mail, String datum_rodjenja, int stanje_racuna, int kolicina_potrosenog_novca) {
        this.korisnikId = korisnikId;
        this.ime_i_prezime = ime_i_prezime;
        this.username = username;
        this.password = password;
        this.e_mail = e_mail;
        this.datum_rodjenja = datum_rodjenja;
        this.stanje_racuna = stanje_racuna;
        this.kolicina_potrosenog_novca = kolicina_potrosenog_novca;
    }

  
    public Korisnik(String ime_i_prezime, String username,String password, String e_mail, String datum_rodjenja, int stanje_racuna, int kolicina_potrosenog_novca) {
        this.ime_i_prezime = ime_i_prezime;
        this.username = username;
        this.password = password;
        this.e_mail = e_mail;
        this.datum_rodjenja = datum_rodjenja;
        this.stanje_racuna = stanje_racuna;
        this.kolicina_potrosenog_novca = kolicina_potrosenog_novca;
    }

   
    public int getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(int korisnikId) {
        this.korisnikId = korisnikId;
    }

    public String getImeIPrezime() {
        return ime_i_prezime;
    }

    public void setImeIPrezime(String ime_i_prezime) {
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

    public String getEmail() {
        return e_mail;
    }

    public void setEmail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getDatumRodjenja() {
        return datum_rodjenja;
    }

    public void setDatumRodjenja(String datum_rodjenja) {
        this.datum_rodjenja = datum_rodjenja;
    }

    public int getStanjeRacuna() {
        return stanje_racuna;
    }

    public void setStanjeRacuna(int stanje_racuna) {
        this.stanje_racuna = stanje_racuna;
    }

    public int getKolicinaPotrosenogNovca() {
        return kolicina_potrosenog_novca;
    }

    public void setKolicinaPotrosenogNovca(int kolicina_potrosenog_novca) {
        this.kolicina_potrosenog_novca = kolicina_potrosenog_novca;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("korisnik{")
          .append("korisnikId=").append(korisnikId)
          .append(", ime_i_prezime='").append(ime_i_prezime).append('\'')
          .append(", username='").append(username).append('\'')
          .append(", password='").append(password).append('\'')
          .append(", e_mail='").append(e_mail).append('\'')
          .append(", datum_rodjenja=").append(datum_rodjenja)
          .append(", stanje_racuna=").append(stanje_racuna)
          .append(", kolicina_potrosenog_novca=").append(kolicina_potrosenog_novca)
          .append('}');
        return sb.toString();
    }
}
