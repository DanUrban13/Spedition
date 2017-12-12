package de.urban.spedition.entity;

import de.urban.spedition.entity.util.GeneratedIdEntity;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Auftrag extends GeneratedIdEntity{
    
    private long bestellNr;
    private Date lieferDatum;
    @ManyToOne
    private Lieferadresse ziel;
    @OneToMany(mappedBy="auftrag")
    private List<PaketContainer> container;
    @OneToMany
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
        return container;
    }

    public void setContainer(List<PaketContainer> container) {
        this.container = container;
    }

    public List<Paket> getIndividualPakete() {
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

    public Long getAuftragNr() {
        return id;
    }
   
    
    
}
