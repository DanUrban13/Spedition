package de.urban.spedition.entity;

import de.urban.spedition.entity.util.GeneratedIdEntity;
import javax.persistence.Entity;

@Entity
public class Lieferadresse extends GeneratedIdEntity{
    
    private String lieferName;
    private String strasse;
    private String nr;
    private String plz;
    private String ort;

    public String getLieferName() {
        return lieferName;
    }

    public void setLieferName(String lieferName) {
        this.lieferName = lieferName;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public Long getAdressNr() {
        return id;
    }
    
    
    
    
}
