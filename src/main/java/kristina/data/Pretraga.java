package kristina.data;

import java.io.Serializable;

public class Pretraga implements Serializable {
    private int pretraga_id = -1;
    private Korisnik korisnik;
    private Podesavanje_Pretrage podesavanje_pretrage;

    public Pretraga() {
    }

    public Pretraga(int pretraga_id, Korisnik korisnik, Podesavanje_Pretrage podesavanje_pretrage) {
        this.pretraga_id = pretraga_id;
        this.korisnik = korisnik;
        this.podesavanje_pretrage = podesavanje_pretrage;
    }

    public Pretraga(Korisnik korisnik, Podesavanje_Pretrage podesavanje_pretrage) {
        this.korisnik = korisnik;
        this.podesavanje_pretrage = podesavanje_pretrage;
    }

    public int getPretraga_id() {
        return pretraga_id;
    }

    public void setPretraga_id(int pretraga_id) {
        this.pretraga_id = pretraga_id;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Podesavanje_Pretrage getPodesavanje_pretrage() {
        return podesavanje_pretrage;
    }

    public void setPodesavanje_pretrage(Podesavanje_Pretrage podesavanje_pretrage) {
        this.podesavanje_pretrage = podesavanje_pretrage;
    }

    @Override
    public String toString() {
        return "Pretraga{" +
                "pretraga_id=" + pretraga_id +
                ", korisnik=" + korisnik +
                ", podesavanje_pretrage=" + podesavanje_pretrage +
                '}';
    }
}
