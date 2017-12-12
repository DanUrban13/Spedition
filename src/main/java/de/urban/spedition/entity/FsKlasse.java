package de.urban.spedition.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FsKlasse {
    @Id
    private String fsKlasse;
    
    private String beschreibung;

    public String getFsKlasse() {
        return fsKlasse;
    }

    public void setFsKlasse(String fsKlasse) {
        this.fsKlasse = fsKlasse;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
    
    
    
}
