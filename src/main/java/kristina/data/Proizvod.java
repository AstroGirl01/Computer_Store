package kristina.data;

import java.io.Serializable;
import java.util.List;

public class Proizvod implements Serializable {

    public static List<Proizvod> getAllProizvodi() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    private int proizvod_id = -1;
    private String naziv;
    private int cena;
    private String vrsta_opreme;
    private int stanje_na_lageru;

   
    public Proizvod() {
    }

    
    public Proizvod(int proizvod_id, String naziv,int cena, String vrsta_opreme, int stanje_na_lageru) {
        this.proizvod_id = proizvod_id;
        this.naziv = naziv;
        this.cena = cena;
        this.vrsta_opreme = vrsta_opreme;
        this.stanje_na_lageru = stanje_na_lageru;
    }

   
    public Proizvod(String naziv, int cena, String vrsta_opreme, int stanje_na_lageru) {
        this.naziv = naziv;
        this.cena = cena;
        this.vrsta_opreme = vrsta_opreme;
        this.stanje_na_lageru = stanje_na_lageru;
    }

    
    public int getProizvod_id() {
        return proizvod_id;
    }

    public void setProizvod_id(int proizvod_id) {
        this.proizvod_id = proizvod_id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public String getVrsta_opreme() {
        return vrsta_opreme;
    }

    public void setVrsta_opreme(String vrsta_opreme) {
        this.vrsta_opreme = vrsta_opreme;
    }

    public int getStanje_na_lageru() {
        return stanje_na_lageru;
    }

    public void setStanje_na_lageru(int stanje_na_lageru) {
        this.stanje_na_lageru = stanje_na_lageru;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("proizvod{")
          .append("proizvod_id=").append(proizvod_id)
          .append(", naziv='").append(naziv).append('\'')
          .append(", cena=").append(cena)
          .append(", vrsta_opreme='").append(vrsta_opreme).append('\'')
          .append(", stanje_na_lageru=").append(stanje_na_lageru)
          .append('}');
        return sb.toString();
    }
}
