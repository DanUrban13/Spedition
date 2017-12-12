package de.urban.spedition.entity;

import de.urban.spedition.entity.util.GeneratedIdEntity;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Mitarbeiter extends GeneratedIdEntity{
    @OneToOne(mappedBy="fahrer")
    private Transportfahrzeug fahrzeug;
    @ManyToMany
    private List<FsKlasse> fuehrerscheinklassen;
    private String name;
    private String vorname;
    private double gehalt;
    private Date eintrittsDatum;
    private Date geburtsDatum;

    public Mitarbeiter() {
    }

    public Mitarbeiter(String name, String vorname, double gehalt, Date geburtsDatum) {
        this.name = name;
        this.vorname = vorname;
        this.gehalt = gehalt;
        this.geburtsDatum = geburtsDatum;
    }
    
    public Transportfahrzeug getFahrzeug() {
        return fahrzeug;
    }

    public void setFahrzeug(Transportfahrzeug fahrzeug) {
        this.fahrzeug = fahrzeug;
    }

    public List<FsKlasse> getFuehrerscheinklassen() {
        return fuehrerscheinklassen;
    }

    public void setFuehrerscheinklassen(List<FsKlasse> fuehrerscheinklassen) {
        this.fuehrerscheinklassen = fuehrerscheinklassen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public double getGehalt() {
        return gehalt;
    }

    public void setGehalt(double gehalt) {
        this.gehalt = gehalt;
    }

    public Date getEintrittsDatum() {
        return eintrittsDatum;
    }

    public void setEintrittsDatum(Date eintrittsDatum) {
        this.eintrittsDatum = eintrittsDatum;
    }

    public Date getGeburtsDatum() {
        return geburtsDatum;
    }

    public void setGeburtsDatum(Date geburtsDatum) {
        this.geburtsDatum = geburtsDatum;
    }

    public Long getPersonalNr() {
        return id;
    }
   
    
    
}
