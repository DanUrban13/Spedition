package de.urban.spedition.model;

import de.urban.spedition.entity.Adresse;
import de.urban.spedition.entity.Auftrag;
import de.urban.spedition.entity.Lieferadresse;
import de.urban.spedition.entity.Mitarbeiter;
import de.urban.spedition.entity.Paket;
import de.urban.spedition.entity.PaketContainer;
import de.urban.spedition.entity.Transportfahrzeug;
import de.urban.spedition.entity.converter.AuftragConverter;
import de.urban.spedition.entity.converter.MitarbeiterConverter;
import de.urban.spedition.entity.converter.TransportfahrzeugConverter;
import de.urban.spedition.service.AuftragServiceIF;
import de.urban.spedition.service.MitarbeiterServiceIF;
import de.urban.spedition.service.TransportfahrzeugServiceIF;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class AuftragServiceModel implements Serializable {
    
    @Inject
    private AuftragServiceIF auftragService;
    
    @Inject
    private MitarbeiterServiceIF mitarbeiterService;
    
    @Inject
    private MitarbeiterConverter mitarbeiterConverter;
    
    @Inject
    private TransportfahrzeugServiceIF tpfs;
    
    @Inject
    private TransportfahrzeugConverter fahrzeugConverter;
    
    @Inject
    private AuftragConverter auftragConverter;
    
    @Inject
    private transient Logger logger;
    
    private Mitarbeiter ausgewaehlterMitarbeiter;
    private Transportfahrzeug ausgewaehltesFahrzeug;
    private List<Paket> aktuellePakete;
    private List<PaketContainer> paketContainer;
    
    private double containerMaxGewichtInKg;
    private double containerLaengeInM;
    private double containerHoeheInM;
    private double containerBreiteInM;
    private Long containerId;
    
    private String auftragName;
    private Date auftragDatum;
    private Long auftragBestellNr;
    private String auftragStrasse;
    private String auftragHausNr;
    private String auftragPLZ;
    private String auftragOrt;
    private Long auftragNr;
    private Auftrag ausgewaehlterAuftrag;
    private int lieferTag;
    private int lieferMonat;
    private int lieferJahr;
    
    private double neuesPaketGewichtInKg;
    private double neuesPaketLaengeInM;
    private double neuesPaketHoeheInM;
    private double neuesPaketBreiteInM;
    private Long neuesPaketId;
    
    private int aktuellePfuschNummer;
    
    private PaketContainer neuerContainer;
    
    private double maxVolumen;
    private double aktuellesVolumen;
    
    public String erstelleAufrag() {
        logger.log(Level.INFO, "erstelle neuen Auftrag");
        Auftrag neuerAuftrag = new Auftrag();
        neuerAuftrag.setBestellNr(this.auftragBestellNr);
        if (paketContainer == null) paketContainer = new ArrayList<PaketContainer>();
        neuerAuftrag.setContainer(this.paketContainer);
        if (aktuellePakete == null) aktuellePakete = new ArrayList<Paket>();
        neuerAuftrag.setIndividualPakete(this.aktuellePakete);
        Lieferadresse l = new Lieferadresse();
        l.setLieferName(this.auftragName);
        Adresse a = new Adresse();
        a.setNr(this.auftragPLZ);
        a.setOrt(this.auftragOrt);
        a.setPlz(this.auftragPLZ);
        a.setStrasse(this.auftragStrasse);
        l.setAdresse(a);
        neuerAuftrag.setZiel(l);      
        try {
            neuerAuftrag = this.auftragService.erstelleAuftrag(neuerAuftrag);
            this.auftragDatum = neuerAuftrag.getLieferDatum();
        } catch (Exception e) {
            logger.log(Level.INFO,e.toString());
            return "auftragAnzeigen";
        }
        this.ausgewaehlterMitarbeiter = neuerAuftrag.getTransporter().getFahrer();
        this.ausgewaehltesFahrzeug = neuerAuftrag.getTransporter();
        this.auftragNr = neuerAuftrag.getId();
        return "auftragErstellt";
    }
    
    public String neuerAuftrag() {
        this.aktuellePakete = null;
        this.ausgewaehlterMitarbeiter = null;
        this.ausgewaehltesFahrzeug = null;
        this.paketContainer = null;
        return "auftragErstellen";
    }
    
    public String aendereAuftrag(){
        ausgewaehlterAuftrag.setLieferDatum(new Date(lieferJahr-1900, lieferMonat-1, lieferTag+1));
        auftragService.aendereAuftrag(ausgewaehlterAuftrag);
        return "auftragAnzeigen";
    }
    
    public String aendereAuftragLesen(){
        this.lieferJahr = this.ausgewaehlterAuftrag.getLieferDatum().getYear()+1900;
        this.lieferMonat = this.ausgewaehlterAuftrag.getLieferDatum().getMonth()+1;
        this.lieferTag = this.ausgewaehlterAuftrag.getLieferDatum().getDate();
        return "auftragAendern_1";
    }
        
    public String loescheAuftrag(){
        this.auftragService.loescheAuftrag(ausgewaehlterAuftrag);
        return "auftragAnzeigen";
    }
    
    public List<Transportfahrzeug> leseAlleFahrzeuge(){
        return this.tpfs.leseAlleTransportfahrzeuge();
    }
    
    public List<Auftrag> leseAlleAuftraege(){
        return this.auftragService.leseAlleAuftraege();
    }
    
    public String erstellePaket(){
        Paket p = new Paket();
        p.setGewichtInKg(this.neuesPaketGewichtInKg);
        p.setBreiteInM(this.neuesPaketBreiteInM);
        p.setHoeheInM(this.neuesPaketHoeheInM);
        p.setLaengeInM(this.neuesPaketLaengeInM);
        p.setPfuschNummer(++aktuellePfuschNummer);
        this.aktuellePakete.add(p);
        return "auftragErstellen";
    }
    
    public String erstelleTestPakete(){
        if(this.aktuellePakete == null) this.aktuellePakete = new ArrayList<Paket>();
        this.aktuellePakete.addAll(auftragService.erstelleTestPakete(aktuellePfuschNummer));
        return "auftragErstellen";
    }
    
    public String packePakete(){
        this.paketContainer = this.auftragService.packePakete(this.aktuellePakete);
        for (PaketContainer pC : this.paketContainer) {
            try {
                
                for (int i = 0; i< aktuellePakete.size();i++) {
                    for (Paket s : pC.getPakete()) {
                        if (aktuellePakete.get(i).getPfuschNummer() == s.getPfuschNummer()) {
                            aktuellePakete.remove(i);
                            
                        }
                    }
                }
            } catch (Exception e) {
                logger.log(Level.INFO, e.toString());
            }
        }
        return "auftragErstellen";
    }
    
    public String erstellePaketContainer(){
        this.neuerContainer = new PaketContainer();
        this.neuerContainer.setMaxGewichtInKg(250);
        this.neuerContainer.setBreiteInM(1);
        this.neuerContainer.setHoeheInM(1);
        this.neuerContainer.setLaengeInM(1);
        this.neuerContainer.setAktuellesGewichtInKg(0.0);
        this.neuerContainer = this.auftragService.erstellePaketContainer(neuerContainer);
        if (this.neuerContainer != null) {
            this.containerId = this.neuerContainer.getId();
            return "container_eintrag_io";
        } else {
            return "container_eintrag_nio";
        }
    } 
    
    public AuftragServiceModel() {
    }

    public List<PaketContainer> getPaketContainer() {
        return paketContainer;
    }

    public void setPaketContainer(List<PaketContainer> paketContainer) {
        this.paketContainer = paketContainer;
    }
    
    public List<Paket> getAktuellePakete() {
        return aktuellePakete;
    }

    public void setAktuellePakete(List<Paket> aktuellePakete) {
        this.aktuellePakete = aktuellePakete;
    }

    public TransportfahrzeugConverter getFahrzeugConverter() {
        return fahrzeugConverter;
    }

    public void setFahrzeugConverter(TransportfahrzeugConverter fahrzeugConverter) {
        this.fahrzeugConverter = fahrzeugConverter;
    }

    public Transportfahrzeug getAusgewaehltesFahrzeug() {
        return ausgewaehltesFahrzeug;
    }

    public void setAusgewaehltesFahrzeug(Transportfahrzeug ausgewaehltesFahrzeug) {
        this.ausgewaehltesFahrzeug = ausgewaehltesFahrzeug;
    }

    public Mitarbeiter getAusgewaehlterMitarbeiter() {
        return ausgewaehlterMitarbeiter;
    }

    public void setAusgewaehlterMitarbeiter(Mitarbeiter ausgewaehlterMitarbeiter) {
        this.ausgewaehlterMitarbeiter = ausgewaehlterMitarbeiter;
    }
        
    public List<Mitarbeiter> leseAlleMitarbeiter(){
        return this.mitarbeiterService.leseAlleMitarbeiter();
    }

    public MitarbeiterConverter getMitarbeiterConverter() {
        return mitarbeiterConverter;
    }

    public void setMitarbeiterConverter(MitarbeiterConverter mitarbeiterConverter) {
        this.mitarbeiterConverter = mitarbeiterConverter;
    }

    public double getContainerMaxGewichtInKg() {
        return containerMaxGewichtInKg;
    }

    public void setContainerMaxGewichtInKg(double containerMaxGewichtInKg) {
        this.containerMaxGewichtInKg = containerMaxGewichtInKg;
    }

    public double getContainerLaengeInM() {
        return containerLaengeInM;
    }

    public void setContainerLaengeInM(double containerLaengeInM) {
        this.containerLaengeInM = containerLaengeInM;
    }

    public double getContainerHoeheInM() {
        return containerHoeheInM;
    }

    public void setContainerHoeheInM(double containerHoeheInM) {
        this.containerHoeheInM = containerHoeheInM;
    }

    public double getContainerBreiteInM() {
        return containerBreiteInM;
    }

    public void setContainerBreiteInM(double containerBreiteInM) {
        this.containerBreiteInM = containerBreiteInM;
    }

    public PaketContainer getNeuerContainer() {
        return neuerContainer;
    }

    public void setNeuerContainer(PaketContainer neuerContainer) {
        this.neuerContainer = neuerContainer;
    }

    public Long getContainerId() {
        return containerId;
    }

    public void setContainerId(Long containerId) {
        this.containerId = containerId;
    }

    public String getAuftragName() {
        return auftragName;
    }

    public void setAuftragName(String auftragName) {
        this.auftragName = auftragName;
    }

    public Date getAuftragDatum() {
        return auftragDatum;
    }

    public void setAuftragDatum(Date auftragDatum) {
        this.auftragDatum = auftragDatum;
    }

    public Long getAuftragBestellNr() {
        return auftragBestellNr;
    }

    public void setAuftragBestellNr(Long auftragBestellNr) {
        this.auftragBestellNr = auftragBestellNr;
    }

    public String getAuftragStrasse() {
        return auftragStrasse;
    }

    public void setAuftragStrasse(String auftragStrasse) {
        this.auftragStrasse = auftragStrasse;
    }

    public String getAuftragHausNr() {
        return auftragHausNr;
    }

    public void setAuftragHausNr(String auftragHausNr) {
        this.auftragHausNr = auftragHausNr;
    }

    public String getAuftragPLZ() {
        return auftragPLZ;
    }

    public void setAuftragPLZ(String auftragPLZ) {
        this.auftragPLZ = auftragPLZ;
    }

    public String getAuftragOrt() {
        return auftragOrt;
    }

    public void setAuftragOrt(String auftragOrt) {
        this.auftragOrt = auftragOrt;
    }

    public double getNeuesPaketLaengeInM() {
        return neuesPaketLaengeInM;
    }

    public void setNeuesPaketLaengeInM(double neuesPaketLaengeInM) {
        this.neuesPaketLaengeInM = neuesPaketLaengeInM;
    }

    public double getNeuesPaketHoeheInM() {
        return neuesPaketHoeheInM;
    }

    public void setNeuesPaketHoeheInM(double neuesPaketHoeheInM) {
        this.neuesPaketHoeheInM = neuesPaketHoeheInM;
    }

    public double getNeuesPaketBreiteInM() {
        return neuesPaketBreiteInM;
    }

    public void setNeuesPaketBreiteInM(double neuesPaketBreiteInM) {
        this.neuesPaketBreiteInM = neuesPaketBreiteInM;
    }

    public Long getNeuesPaketId() {
        return neuesPaketId;
    }

    public void setNeuesPaketId(Long neuesPaketId) {
        this.neuesPaketId = neuesPaketId;
    }

    public double getNeuesPaketGewichtInKg() {
        return neuesPaketGewichtInKg;
    }

    public void setNeuesPaketGewichtInKg(double neuesPaketGewichtInKg) {
        this.neuesPaketGewichtInKg = neuesPaketGewichtInKg;
    }

    public double getMaxVolumen() {
        return maxVolumen;
    }

    public void setMaxVolumen(double maxVolumen) {
        this.maxVolumen = maxVolumen;
    }

    public double getAktuellesVolumen() {
        return aktuellesVolumen;
    }

    public void setAktuellesVolumen(double aktuellesVolumen) {
        this.aktuellesVolumen = aktuellesVolumen;
    }

    public Long getAuftragNr() {
        return auftragNr;
    }

    public void setAuftragNr(Long auftragNr) {
        this.auftragNr = auftragNr;
    }

    public Auftrag getAusgewaehlterAuftrag() {
        return ausgewaehlterAuftrag;
    }

    public void setAusgewaehlterAuftrag(Auftrag ausgewaehlterAuftrag) {
        this.ausgewaehlterAuftrag = ausgewaehlterAuftrag;
    }

    public AuftragConverter getAuftragConverter() {
        return auftragConverter;
    }

    public void setAuftragConverter(AuftragConverter auftragConverter) {
        this.auftragConverter = auftragConverter;
    }

    public int getLieferTag() {
        return lieferTag;
    }

    public void setLieferTag(int lieferTag) {
        this.lieferTag = lieferTag;
    }

    public int getLieferMonat() {
        return lieferMonat;
    }

    public void setLieferMonat(int lieferMonat) {
        this.lieferMonat = lieferMonat;
    }

    public int getLieferJahr() {
        return lieferJahr;
    }

    public void setLieferJahr(int lieferJahr) {
        this.lieferJahr = lieferJahr;
    }
    
    
    
    
}
