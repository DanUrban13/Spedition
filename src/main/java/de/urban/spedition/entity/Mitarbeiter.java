package de.urban.spedition.entity;

import de.urban.spedition.entity.util.GeneratedIdEntity;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@NamedQuery(
        name="Mitarbeiter.alle",
        query="SELECT m FROM Mitarbeiter AS m"
)
public class Mitarbeiter extends GeneratedIdEntity{
    @OneToOne(mappedBy="fahrer", cascade = CascadeType.MERGE)
    private Transportfahrzeug fahrzeug;
    @ManyToMany
    @Fetch(FetchMode.SUBSELECT)
    private List<FsKlasse> fuehrerscheinklassen;
    private String name;
    private String vorname;
    private Date eintrittsDatum;
    private Date geburtsDatum;
    
    @PreRemove
    private void preRemove() {
        fahrzeug = null;
    }

    public Mitarbeiter() {
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

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Mitarbeiter{" + "fahrzeug=" + fahrzeug + ", fuehrerscheinklassen=" + fuehrerscheinklassen + ", name=" + name + ", vorname=" + vorname +  ", eintrittsDatum=" + eintrittsDatum + ", geburtsDatum=" + geburtsDatum + '}';
    }
   
    
    
}
