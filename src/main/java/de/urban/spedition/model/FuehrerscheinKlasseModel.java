package de.urban.spedition.model;

import de.urban.spedition.entity.FsKlasse;
import de.urban.spedition.entity.converter.FsKlasseConverter;
import de.urban.spedition.service.TransportfahrzeugServiceIF;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class FuehrerscheinKlasseModel implements Serializable {
    
    @Inject
    TransportfahrzeugServiceIF tf;
    
    @Inject
    FsKlasseConverter fsKlasseConverter;
    
    @Inject
    private transient Logger logger;
    
    private String fsKlasseName;
    private String fsKlasseBeschreibung;
    private FsKlasse neueFsKlasse;
    private FsKlasse ausgewaehlteFsKlasse;
    
    public String neueKlasse() {
        fsKlasseName = "";
        fsKlasseBeschreibung = "";
        return "fuehrerscheinKlasseErstellen";
    }
    
    public String loescheKlasse() {
        tf.loescheFsKlasse(ausgewaehlteFsKlasse);
        return "fuehrerscheinKlasseAnzeigen";
    }

    public FuehrerscheinKlasseModel() {
    }

    public String getFsKlasseName() {
        return fsKlasseName;
    }

    public void setFsKlasseName(String fsKlasseName) {
        this.fsKlasseName = fsKlasseName;
    }

    public String getFsKlasseBeschreibung() {
        return fsKlasseBeschreibung;
    }

    public void setFsKlasseBeschreibung(String fsKlasseBeschreibung) {
        this.fsKlasseBeschreibung = fsKlasseBeschreibung;
    }

    public FsKlasse getNeueFsKlasse() {
        return neueFsKlasse;
    }

    public void setNeueFsKlasse(FsKlasse neueFsKlasse) {
        this.neueFsKlasse = neueFsKlasse;
    }
    
    
        
    public String erstelleFuehrerscheinklasse(){
        FsKlasse neu = new FsKlasse();
        neu.setFsKlasse(this.fsKlasseName);
        neu.setBeschreibung(this.fsKlasseBeschreibung);
        this.neueFsKlasse = tf.erstelleFuehrerscheinklasse(neu);
        
        return "eintragFSok";
    }
    
    public List<FsKlasse> leseAlleFsKlasse(){
        return this.tf.leseAlleFsKlasse();
    }

    public FsKlasse getAusgewaehlteFsKlasse() {
        return ausgewaehlteFsKlasse;
    }

    public void setAusgewaehlteFsKlasse(FsKlasse ausgewaehlteFsKlasse) {
        this.ausgewaehlteFsKlasse = ausgewaehlteFsKlasse;
    }

    public FsKlasseConverter getFsKlasseConverter() {
        return fsKlasseConverter;
    }

    public void setFsKlasseConverter(FsKlasseConverter fsKlasseConverter) {
        this.fsKlasseConverter = fsKlasseConverter;
    }
    
    
    
    
    
}
