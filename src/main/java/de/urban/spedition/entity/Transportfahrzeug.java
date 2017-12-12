package de.urban.spedition.entity;

import de.urban.spedition.entity.util.GeneratedIdEntity;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Transportfahrzeug extends GeneratedIdEntity{
    @OneToOne
    private Mitarbeiter fahrer;
    @OneToMany(mappedBy="transporter")
    private List<Auftrag> aktuelleAuftraege;
    @ManyToOne
    private FsKlasse fsBenoetigt;
    private double ladeLaengeInM;
    private double ladeBreiteInM;
    private double ladeHoeheInM;
    private int ladeGewichtInKg;

    public Mitarbeiter getFahrer() {
        return fahrer;
    }

    public void setFahrer(Mitarbeiter fahrer) {
        this.fahrer = fahrer;
    }

    public List<Auftrag> getAktuelleAuftraege() {
        return aktuelleAuftraege;
    }

    public void setAktuelleAuftraege(List<Auftrag> aktuelleAuftraege) {
        this.aktuelleAuftraege = aktuelleAuftraege;
    }

    public FsKlasse getFsBenoetigt() {
        return fsBenoetigt;
    }

    public void setFsBenoetigt(FsKlasse fsBenoetigt) {
        this.fsBenoetigt = fsBenoetigt;
    }

    public double getLadeLaengeInM() {
        return ladeLaengeInM;
    }

    public void setLadeLaengeInM(double ladeLaengeInM) {
        this.ladeLaengeInM = ladeLaengeInM;
    }

    public double getLadeBreiteInM() {
        return ladeBreiteInM;
    }

    public void setLadeBreiteInM(double ladeBreiteInM) {
        this.ladeBreiteInM = ladeBreiteInM;
    }

    public double getLadeHoeheInM() {
        return ladeHoeheInM;
    }

    public void setLadeHoeheInM(double ladeHoeheInM) {
        this.ladeHoeheInM = ladeHoeheInM;
    }

    public int getLadeGewichtInKg() {
        return ladeGewichtInKg;
    }

    public void setLadeGewichtInKg(int ladeGewichtInKg) {
        this.ladeGewichtInKg = ladeGewichtInKg;
    }

    public Long getFahrzeugNr() {
        return id;
    }
    
    
    
}
