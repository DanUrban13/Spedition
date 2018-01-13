package de.urban.spedition.model;

import de.urban.spedition.entity.Auftrag;
import de.urban.spedition.entity.FsKlasse;
import de.urban.spedition.entity.Mitarbeiter;
import de.urban.spedition.entity.Transportfahrzeug;
import de.urban.spedition.entity.converter.FsKlasseConverter;
import de.urban.spedition.entity.converter.TransportfahrzeugConverter;
import de.urban.spedition.service.TransportfahrzeugServiceIF;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class TransportmgmtModel implements Serializable {
    
    @Inject
    private TransportfahrzeugServiceIF transportfahrzeugService;
    
    @Inject 
    private TransportfahrzeugConverter transportfahrzeugConverter;
    
    @Inject
    private FsKlasseConverter fsKlasseConverter;
    
    @Inject
    private transient Logger logger;
    
    private Mitarbeiter fahrer;
    
    private List<Auftrag> aktuelleAuftraege;
    
    private FsKlasse fsBenoetigt;
    private String kennzeichen;
    private double ladeLaengeInM;
    private double ladeBreiteInM;
    private double ladeHoeheInM;
    private int ladeGewichtInKg;
    
    private Transportfahrzeug ausgewaehltesTransportfahrzeug;
    
    public TransportmgmtModel() {
    }
    
    public List<FsKlasse> leseAlleFsKlasse() {
        return transportfahrzeugService.leseAlleFsKlasse();
    }
    
    public String neuesTransportfahrzeug(){
        Transportfahrzeug neuesTpf = new Transportfahrzeug();
        neuesTpf.setFsBenoetigt(this.fsBenoetigt);
        neuesTpf.setKennzeichen(this.kennzeichen);
        neuesTpf.setLadeBreiteInM(this.ladeBreiteInM);
        neuesTpf.setLadeGewichtInKg(this.ladeGewichtInKg);
        neuesTpf.setLadeHoeheInM(this.ladeHoeheInM);
        neuesTpf.setLadeLaengeInM(this.ladeLaengeInM);
        transportfahrzeugService.erstelleTransportfahrzeug(neuesTpf);
        return "transportfahrzeugAnzeigen";
    }
    
    public String loescheTransportfahrzeug() {
        if (transportfahrzeugService.loescheTransportfahrzeug(ausgewaehltesTransportfahrzeug) == null) {
            return "transportfahrzeugAnzeigen";
        }
        return "transportfahrzeugAnzeigen";       
    }
    
    public List<Transportfahrzeug> leseAlleTransportfahrzeuge(){
        return this.transportfahrzeugService.leseAlleTransportfahrzeuge();
    }

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

    public String getKennzeichen() {
        return kennzeichen;
    }

    public void setKennzeichen(String kennzeichen) {
        this.kennzeichen = kennzeichen;
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

    public FsKlasseConverter getFsKlasseConverter() {
        return fsKlasseConverter;
    }

    public void setFsKlasseConverter(FsKlasseConverter fsKlasseConverter) {
        this.fsKlasseConverter = fsKlasseConverter;
    }

    public Transportfahrzeug getAusgewaehltesTransportfahrzeug() {
        return ausgewaehltesTransportfahrzeug;
    }

    public void setAusgewaehltesTransportfahrzeug(Transportfahrzeug ausgewaehltesTransportfahrzeug) {
        this.ausgewaehltesTransportfahrzeug = ausgewaehltesTransportfahrzeug;
    }

    public TransportfahrzeugConverter getTransportfahrzeugConverter() {
        return transportfahrzeugConverter;
    }

    public void setTransportfahrzeugConverter(TransportfahrzeugConverter transportfahrzeugConverter) {
        this.transportfahrzeugConverter = transportfahrzeugConverter;
    }


    
}
