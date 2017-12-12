package de.urban.spedition.entity;

import de.urban.spedition.entity.util.GeneratedIdEntity;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Paket extends GeneratedIdEntity{
    @ManyToOne
    private PaketContainer container;
    private double gewichtInKg;
    private double hoeheInM;
    private double laengeInM;
    private double breiteInM;

    public PaketContainer getContainer() {
        return container;
    }

    public void setContainer(PaketContainer container) {
        this.container = container;
    }

    public double getGewichtInKg() {
        return gewichtInKg;
    }

    public void setGewichtInKg(double gewichtInKg) {
        this.gewichtInKg = gewichtInKg;
    }

    public double getHoeheInM() {
        return hoeheInM;
    }

    public void setHoeheInM(double hoeheInM) {
        this.hoeheInM = hoeheInM;
    }

    public double getLaengeInM() {
        return laengeInM;
    }

    public void setLaengeInM(double laengeInM) {
        this.laengeInM = laengeInM;
    }

    public double getBreiteInM() {
        return breiteInM;
    }

    public void setBreiteInM(double breiteInM) {
        this.breiteInM = breiteInM;
    }

    public Long getPaketNr() {
        return id;
    }
    
    
    
}
