package de.urban.spedition.model;

import de.urban.spedition.entity.FsKlasse;
import de.urban.spedition.entity.Mitarbeiter;
import de.urban.spedition.entity.converter.FsKlasseConverter;
import de.urban.spedition.entity.converter.MitarbeiterConverter;
import de.urban.spedition.exceptions.CouldNotDeleteException;
import de.urban.spedition.service.MitarbeiterServiceIF;
import de.urban.spedition.service.TransportfahrzeugServiceIF;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class PersonalmgmtModel implements Serializable {
    
    @Inject
    private MitarbeiterServiceIF mitarbeiterService;
    
    @Inject
    private MitarbeiterConverter mitarbeiterConverter;
    
    @Inject
    private TransportfahrzeugServiceIF transportfahrzeugService;
    
    @Inject
    private FsKlasseConverter fsKlasseConverter;
    
    @Inject
    private transient Logger logger;
    
    Mitarbeiter ausgewaehlterMitarbeiter;
    
    private String Vorname;
    private String Nachname;
    private int gebTag;
    private int gebMonat;
    private int gebJahr;
    
    private int eintrittTag;
    private int eintrittMonat;
    private int eintrittJahr;
    private List<FsKlasse> fsKlassen;
    private FsKlasse fsKlasseAusgewaehlt;
    
    public String klasseHinzufuegen(){
        fsKlassen.add(fsKlasseAusgewaehlt);
        return "mitarbeiterErstellen_1";
    }  
    
    public List<FsKlasse> leseAlleFsKlasse() {
        return transportfahrzeugService.leseAlleFsKlasse();
    }
    
    public String schrittZwei() {
        fsKlassen = new ArrayList<FsKlasse>();
        return "mitarbeiterErstellen_1";
    }
        
    public String neuerMitarbeiter(){
        Mitarbeiter neuerMitarbeiter = new Mitarbeiter();
        neuerMitarbeiter.setVorname(this.Vorname);
        neuerMitarbeiter.setName(this.Nachname);
        neuerMitarbeiter.setGeburtsDatum(new Date(this.gebJahr-1900, this.gebMonat-1, this.gebTag));
        neuerMitarbeiter.setEintrittsDatum(new Date(this.eintrittJahr-1900, this.eintrittMonat-1, this.eintrittTag));
        neuerMitarbeiter.setFuehrerscheinklassen(fsKlassen);
        mitarbeiterService.erstelleMitarbeiter(neuerMitarbeiter);
        
        Vorname = "";
        Nachname = "";
        fsKlassen.clear();
        return "mitarbeiterAnzeigen";
    }
    
    public String aendereMitarbeiterAuswahl(){
        try {
            logger.log(Level.INFO, "aendere Mitarbeiter");
            this.ausgewaehlterMitarbeiter = this.mitarbeiterService.findeMitarbeiter(this.ausgewaehlterMitarbeiter.getId());
            this.Vorname = this.ausgewaehlterMitarbeiter.getVorname();
            this.Nachname = this.ausgewaehlterMitarbeiter.getName();
            this.eintrittJahr = this.ausgewaehlterMitarbeiter.getEintrittsDatum().getYear()+1900;
            this.eintrittMonat = this.ausgewaehlterMitarbeiter.getEintrittsDatum().getMonth()+1;
            this.eintrittTag = this.ausgewaehlterMitarbeiter.getEintrittsDatum().getDate();
            this.gebJahr = this.ausgewaehlterMitarbeiter.getGeburtsDatum().getYear()+1900;
            this.gebMonat = this.ausgewaehlterMitarbeiter.getGeburtsDatum().getMonth()+1;
            this.gebTag = this.ausgewaehlterMitarbeiter.getGeburtsDatum().getDate();
            return "aendereMitarbeiterErweitert";
        } catch(Exception e) {
            
            return "mitarbeiterErstellen";
        }
    }
    
    public String aendereMitarbeiterEintrag(){
        this.ausgewaehlterMitarbeiter.setName(this.Nachname);
        this.ausgewaehlterMitarbeiter.setVorname(this.Vorname);
        this.ausgewaehlterMitarbeiter.setGeburtsDatum(new Date(this.gebJahr-1900, this.gebMonat-1, this.gebTag));
        this.ausgewaehlterMitarbeiter.setEintrittsDatum(new Date(this.eintrittJahr-1900, this.eintrittMonat-1, this.eintrittTag));
        this.ausgewaehlterMitarbeiter = this.mitarbeiterService.aendereMitarbeiter(this.ausgewaehlterMitarbeiter);
        if(this.ausgewaehlterMitarbeiter != null) return "mitarbeiterAnzeigen";
        else return "mitarbeiterErstellen";
    }
    
    public String loescheMitarbeiter(){
        try {
            if (this.mitarbeiterService.loescheMitarbeiter(this.ausgewaehlterMitarbeiter) == null)
                return "mitarbeiterAnzeigen";
            else 
                throw new CouldNotDeleteException();
        } catch (Exception e) {
            return "mitarbeiterAnzeigen";
        }
    }
    
    @PostConstruct
    public void init(){
        Date aktuellesDatum = new Date();
        this.eintrittJahr = aktuellesDatum.getYear()+1900;
        this.eintrittMonat = aktuellesDatum.getMonth()+1;
        this.eintrittTag = aktuellesDatum.getDate();
    }    

    public Mitarbeiter getAusgewaehlterMitarbeiter() {
        return ausgewaehlterMitarbeiter;
    }

    public void setAusgewaehlterMitarbeiter(Mitarbeiter ausgewaehlterMitarbeiter) {
        this.ausgewaehlterMitarbeiter = ausgewaehlterMitarbeiter;
    }

    
    
    public MitarbeiterConverter getMitarbeiterConverter() {
        return mitarbeiterConverter;
    }

    public void setMitarbeiterConverter(MitarbeiterConverter mitarbeiterConverter) {
        this.mitarbeiterConverter = mitarbeiterConverter;
    }

    
    
    public String getVorname() {
        return Vorname;
    }

    public void setVorname(String Vorname) {
        this.Vorname = Vorname;
    }

    public String getNachname() {
        return Nachname;
    }

    public void setNachname(String Nachname) {
        this.Nachname = Nachname;
    }

    public int getGebTag() {
        return gebTag;
    }

    public void setGebTag(int gebTag) {
        this.gebTag = gebTag;
    }

    public int getGebMonat() {
        return gebMonat;
    }

    public void setGebMonat(int gebMonat) {
        this.gebMonat = gebMonat;
    }

    public int getGebJahr() {
        return gebJahr;
    }

    public void setGebJahr(int gebJahr) {
        this.gebJahr = gebJahr;
    }

    public int getEintrittTag() {
        return eintrittTag;
    }

    public void setEintrittTag(int eintrittTag) {
        this.eintrittTag = eintrittTag;
    }

    public int getEintrittMonat() {
        return eintrittMonat;
    }

    public void setEintrittMonat(int eintrittMonat) {
        this.eintrittMonat = eintrittMonat;
    }

    public int getEintrittJahr() {
        return eintrittJahr;
    }

    public void setEintrittJahr(int eintrittJahr) {
        this.eintrittJahr = eintrittJahr;
    }
    
    
    public PersonalmgmtModel() {
    }
    
    public List<Mitarbeiter> leseAlleMitarbeiter(){
        return this.mitarbeiterService.leseAlleMitarbeiter();
    }

    public List<FsKlasse> getFsKlassen() {
        return fsKlassen;
    }

    public void setFsKlassen(List<FsKlasse> fsKlassen) {
        this.fsKlassen = fsKlassen;
    }

    public FsKlasse getFsKlasseAusgewaehlt() {
        return fsKlasseAusgewaehlt;
    }

    public void setFsKlasseAusgewaehlt(FsKlasse fsKlasseAusgewaehlt) {
        this.fsKlasseAusgewaehlt = fsKlasseAusgewaehlt;
    }

    public FsKlasseConverter getFsKlasseConverter() {
        return fsKlasseConverter;
    }

    public void setFsKlasseConverter(FsKlasseConverter fsKlasseConverter) {
        this.fsKlasseConverter = fsKlasseConverter;
    }
    
    
    
}
