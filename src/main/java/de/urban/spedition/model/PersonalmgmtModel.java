package de.urban.spedition.model;

import de.urban.spedition.entity.Mitarbeiter;
import de.urban.spedition.service.MitarbeiterServiceIF;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class PersonalmgmtModel implements Serializable {
    
    @Inject
    private MitarbeiterServiceIF mitarbeiterService;
    
    private String Vorname;
    private String Nachname;

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

    public PersonalmgmtModel() {
    }
    
    
    public void neuerMitarbeiter(){
        Mitarbeiter neuerMitarbeiter = new Mitarbeiter();
        neuerMitarbeiter.setVorname(this.Vorname);
        neuerMitarbeiter.setName(this.Nachname);
        
        mitarbeiterService.erstelleMitarbeiter(neuerMitarbeiter);
        
    }
    
}
