package kristina.data;

import java.io.Serializable;

public class Podesavanje_Pretrage implements Serializable {

    private int podesavanje_pretrage_id;
    private long min_cena;
    private long max_cena;
    private String tip;
    private String kljucna_rec;

    public Podesavanje_Pretrage() {}

    public Podesavanje_Pretrage(int podesavanje_pretrage_id, long min_cena, long max_cena, String tip, String kljucna_rec) {
        this.podesavanje_pretrage_id = podesavanje_pretrage_id;
        this.min_cena = min_cena;
        this.max_cena = max_cena;
        this.tip = tip;
        this.kljucna_rec = kljucna_rec;
    }

    public Podesavanje_Pretrage(long min_cena, long max_cena, String tip, String kljucna_rec) {
        this.min_cena = min_cena;
        this.max_cena = max_cena;
        this.tip = tip;
        this.kljucna_rec = kljucna_rec;
    }

    public int getPodesavanje_pretrage_id() {
        return podesavanje_pretrage_id;
    }

    public void setPodesavanje_pretrage_id(int podesavanje_pretrage_id) {
        this.podesavanje_pretrage_id = podesavanje_pretrage_id;
    }

    public long getMinCena() {
        return min_cena;
    }

    public void setMinCena(long min_cena) {
        this.min_cena = min_cena;
    }

    public long getMaxCena() {
        return max_cena;
    }

    public void setMaxCena(long max_cena) {
        this.max_cena = max_cena;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getKljucnaRec() {
        return kljucna_rec;
    }

    public void setKljucnaRec(String kljucna_rec) {
        this.kljucna_rec = kljucna_rec;
    }

    @Override
    public String toString() {
        return "Podesavanje_Pretrage{" +
                "podesavanje_pretrage_id=" + podesavanje_pretrage_id +
                ", min_cena=" + min_cena +
                ", max_cena=" + max_cena +
                ", tip='" + tip + '\'' +
                ", kljucna_rec='" + kljucna_rec + '\'' +
                '}';
    }
}
