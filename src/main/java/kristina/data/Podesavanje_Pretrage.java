/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kristina.data;

import java.io.Serializable;


public class Podesavanje_Pretrage implements Serializable {
    private int podesavanje_pretrage_id = -1;
    private int donja_granica_obima_cene;
    private int gornja_granica_obima_cene;
    private String vrsta_opreme;
    private String kljucna_rec;

  
    public Podesavanje_Pretrage() {
    }

    public Podesavanje_Pretrage(int podesavanje_pretrage_id, int donja_granica_obima_cene, int gornja_granica_obima_cene, String vrsta_opreme, String kljucna_rec) {
        this.podesavanje_pretrage_id = podesavanje_pretrage_id;
        this.donja_granica_obima_cene = donja_granica_obima_cene;
        this.gornja_granica_obima_cene = gornja_granica_obima_cene;
        this.vrsta_opreme = vrsta_opreme;
        this.kljucna_rec = kljucna_rec;
    }

    
    public Podesavanje_Pretrage(int donja_granica_obima_cene, int gornja_granica_obima_cene, String vrsta_opreme, String kljucna_rec) {
        this.donja_granica_obima_cene = donja_granica_obima_cene;
        this.gornja_granica_obima_cene = gornja_granica_obima_cene;
        this.vrsta_opreme = vrsta_opreme;
        this.kljucna_rec = kljucna_rec;
    }

    public int getpodesavanje_pretrageId() {
        return podesavanje_pretrage_id;
    }

    public void setpodesavanje_pretrageId(int podesavanje_pretrage_id) {
        this.podesavanje_pretrage_id = podesavanje_pretrage_id;
    }

    public int getDonjaGranicaObimaCene() {
        return donja_granica_obima_cene;
    }

    public void setDonjaGranicaObimaCene(int donja_granica_obima_cene) {
        this.donja_granica_obima_cene = donja_granica_obima_cene;
    }

    public int getGornjaGranicaObimaCene() {
        return gornja_granica_obima_cene;
    }

    public void setGornjaGranicaObimaCene(int gornja_granica_obima_cene) {
        this.gornja_granica_obima_cene = gornja_granica_obima_cene;
    }

    public String getVrstaOpreme() {
        return vrsta_opreme;
    }

    public void setVrstaOpreme(String vrsta_opreme) {
        this.vrsta_opreme = vrsta_opreme;
    }

    public String getKljucnaRec() {
        return kljucna_rec;
    }

    public void setKljucnaRec(String kljucna_rec) {
        this.kljucna_rec = kljucna_rec;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("podesavanje_pretrage{")
          .append("podesavanje_pretrage_id=").append(podesavanje_pretrage_id)
          .append(", donja_granica_obima_cene=").append(donja_granica_obima_cene)
          .append(", gornja_granica_obima_cene=").append(gornja_granica_obima_cene)
          .append(", vrsta_opreme='").append(vrsta_opreme).append('\'')
          .append(", kljucna_rec='").append(kljucna_rec).append('\'')
          .append('}');
        return sb.toString();
    }
}

