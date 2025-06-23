/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kristina.data;


import java.io.Serializable;


public class Pretraga implements Serializable {
    private int pretraga_id = -1;
    private int podesavanje_pretrage_id;
    private int korisnik_id;

    public Pretraga() {
    }

    
    public Pretraga(int pretraga_id, int podesavanje_pretrage_id, int korisnik_id) {
        this.pretraga_id = pretraga_id;
        this.podesavanje_pretrage_id = podesavanje_pretrage_id;
        this.korisnik_id = korisnik_id;
    }

    
    public Pretraga(int podesavanje_pretrage_id, int korisnik_id) {
        this.podesavanje_pretrage_id = podesavanje_pretrage_id;
        this.korisnik_id = korisnik_id;
    }

    
    public int getPretraga_id() {
        return pretraga_id;
    }

    public void setPretraga_id(int pretraga_id) {
        this.pretraga_id = pretraga_id;
    }

    public int getPodesavanje_Pretrage_id() {
        return podesavanje_pretrage_id;
    }

    public void setPodesavanje_Pretrage_id(int podesavanje_pretrage_id) {
        this.podesavanje_pretrage_id = podesavanje_pretrage_id;
    }

    public int getKorisnik_id() {
        return korisnik_id;
    }

    public void setKorisnik_id(int korisnik_id) {
        this.korisnik_id = korisnik_id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("pretraga{")
          .append("pretraga_id=").append(pretraga_id)
          .append(", podesavanje_pretrage_id=").append(podesavanje_pretrage_id)
          .append(", korisnik_id=").append(korisnik_id)
          .append('}');
        return sb.toString();
    }
}

