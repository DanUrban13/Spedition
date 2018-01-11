package de.urban.spedition.entity;

import de.urban.spedition.entity.util.GeneratedIdEntity;
import javax.persistence.Entity;

@Entity
public class Lieferadresse extends GeneratedIdEntity{
    
    private String lieferName;
    private Adresse adresse;

    public String getLieferName() {
        return lieferName;
    }

    public void setLieferName(String lieferName) {
        this.lieferName = lieferName;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }
    
    

    public Long getAdressNr() {
        return id;
    }
    
    
    
    
}
