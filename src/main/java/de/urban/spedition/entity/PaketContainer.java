package de.urban.spedition.entity;

import de.urban.spedition.entity.util.GeneratedIdEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(
        name="PaketContainer.alle",
        query="SELECT p FROM PaketContainer AS p "
)
public class PaketContainer extends GeneratedIdEntity{
    @OneToMany(mappedBy="container",fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Paket> pakete;
    @ManyToOne
    private Auftrag auftrag;
    private double maxGewichtInKg;
    private double aktuellesGewichtInKg;
    private double laengeInM;
    private double hoeheInM;
    private double breiteInM;

    public PaketContainer() {
    }    
    
    public List<Paket> getPakete() {
        if(this.pakete==null) this.pakete = new ArrayList<Paket>();
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

    public double getMaxGewichtInKg() {
        return maxGewichtInKg;
    }

    public void setMaxGewichtInKg(double maxGewichtInKg) {
        this.maxGewichtInKg = maxGewichtInKg;
    }

    public double getAktuellesGewichtInKg() {
        return aktuellesGewichtInKg;
    }

    public void setAktuellesGewichtInKg(double aktuellesGewichtInKg) {
        this.aktuellesGewichtInKg = aktuellesGewichtInKg;
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
        return getId();
    }

    @Override
    public String toString() {
        return "PaketContainer{" + "pakete=" + pakete + ", auftrag=" + auftrag + ", maxGewichtInKg=" + maxGewichtInKg + ", aktuellesGewichtInKg=" + aktuellesGewichtInKg + ", laengeInM=" + laengeInM + ", hoeheInM=" + hoeheInM + ", breiteInM=" + breiteInM + '}';
    }
    
    
    
    
}
