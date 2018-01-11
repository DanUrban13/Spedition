package de.urban.spedition.entity;

import de.urban.spedition.entity.util.GeneratedIdEntity;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@NamedQuery(
        name="Transportfahrzeug.alle",
        query="SELECT t FROM Transportfahrzeug AS t"
)
public class Transportfahrzeug extends GeneratedIdEntity{
    @OneToOne
    private Mitarbeiter fahrer;
    @OneToOne(mappedBy="transporter", fetch = FetchType.EAGER)
    private Auftrag aktuellerAuftrag;
    @ManyToOne
    private FsKlasse fsBenoetigt;
    
    private String kennzeichen;
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

    public Auftrag getAktuellerAuftrag() {
        return aktuellerAuftrag;
    }

    public void setAktuellerAuftrag(Auftrag aktuellerAuftrag) {
        this.aktuellerAuftrag = aktuellerAuftrag;
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

    public String getKennzeichen() {
        return kennzeichen;
    }

    public void setKennzeichen(String kennzeichen) {
        this.kennzeichen = kennzeichen;
    }

    public Long getId() {
        return id;
    }
    
    
    
}
