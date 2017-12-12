package de.urban.spedition.entity;

import de.urban.spedition.entity.util.GeneratedIdEntity;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class PaketContainer extends GeneratedIdEntity{
    @OneToMany(mappedBy="container")
    private List<Paket> pakete;
    @ManyToOne
    private Auftrag auftrag;
    private double maxGweichtInKg;
    private double aktuellesGweichtInKg;
    private double laengeInM;
    private double hoeheInM;
    private double breiteInM;

    public List<Paket> getPakete() {
        return pakete;
    }

    public void setPakete(List<Paket> pakete) {
        this.pakete = pakete;
    }

    public Auftrag getAuftrag() {
        return auftrag;
    }

    public void setAuftrag(Auftrag auftrag) {
        this.auftrag = auftrag;
    }

    public double getMaxGweichtInKg() {
        return maxGweichtInKg;
    }

    public void setMaxGweichtInKg(double maxGweichtInKg) {
        this.maxGweichtInKg = maxGweichtInKg;
    }

    public double getAktuellesGweichtInKg() {
        return aktuellesGweichtInKg;
    }

    public void setAktuellesGweichtInKg(double aktuellesGweichtInKg) {
        this.aktuellesGweichtInKg = aktuellesGweichtInKg;
    }

    public double getLaengeInM() {
        return laengeInM;
    }

    public void setLaengeInM(double laengeInM) {
        this.laengeInM = laengeInM;
    }

    public double getHoeheInM() {
        return hoeheInM;
    }

    public void setHoeheInM(double hoeheInM) {
        this.hoeheInM = hoeheInM;
    }

    public double getBreiteInM() {
        return breiteInM;
    }

    public void setBreiteInM(double breiteInM) {
        this.breiteInM = breiteInM;
    }

    public Long getPaketContainerNr() {
        return id;
    }
    
    
    
    
}
