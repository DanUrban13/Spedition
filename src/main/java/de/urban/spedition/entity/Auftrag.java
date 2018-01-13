package de.urban.spedition.entity;

import de.urban.spedition.entity.util.GeneratedIdEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQuery(
        name="Auftrag.alle",
        query="SELECT a FROM Auftrag AS a"
)
public class Auftrag extends GeneratedIdEntity{
    
    private long bestellNr;
    private Date lieferDatum;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private Lieferadresse ziel;
    @OneToMany(mappedBy="auftrag", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<PaketContainer> container;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Paket> individualPakete;
    @ManyToOne
    private Transportfahrzeug transporter;

    public long getBestellNr() {
        return bestellNr;
    }

    public void setBestellNr(long bestellNr) {
        this.bestellNr = bestellNr;
    }

    public Date getLieferDatum() {
        return lieferDatum;
    }

    public void setLieferDatum(Date lieferDatum) {
        this.lieferDatum = lieferDatum;
    }

    public Lieferadresse getZiel() {
        return ziel;
    }

    public void setZiel(Lieferadresse ziel) {
        this.ziel = ziel;
    }

    public List<PaketContainer> getContainer() {
        if (container == null) container = new ArrayList<PaketContainer>();
        return container;
    }

    public void setContainer(List<PaketContainer> container) {
        this.container = container;
    }
    
    public List<Paket> getIndividualPakete() {
        if(individualPakete == null) {
            individualPakete = new ArrayList<Paket>();
        }
        return individualPakete;
    }

    public void setIndividualPakete(List<Paket> individualPakete) {
        this.individualPakete = individualPakete;
    }

    public Transportfahrzeug getTransporter() {
        return transporter;
    }

    public void setTransporter(Transportfahrzeug transporter) {
        this.transporter = transporter;
    }

    @XmlElement
    public Long getAuftragNr() {
        return getId();
    }

    public Auftrag() {
    }

    public Auftrag(long bestellNr, Lieferadresse ziel, List<Paket> individualPakete) {
        this.bestellNr = bestellNr;
        this.ziel = ziel;
        this.individualPakete = individualPakete;
    }   

    @Override
    public String toString() {
        return "Auftrag{" + "bestellNr=" + bestellNr + ", lieferDatum=" + lieferDatum + ", ziel=" + ziel + ", transporter=" + transporter + '}';
    }
    
    
    
}
