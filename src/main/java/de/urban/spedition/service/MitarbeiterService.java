package de.urban.spedition.service;

import de.urban.spedition.entity.Mitarbeiter;
import javax.jws.WebService;

@WebService
public class MitarbeiterService implements MitarbeiterServiceIF {

    @Override
    public Mitarbeiter erstelleMitarbeiter(Mitarbeiter neuerMitarbeiter) {
        System.out.println("Erstelle neuen Mitarbeiter: "
                + neuerMitarbeiter.getVorname() + " " + neuerMitarbeiter.getName());
        return null;
    }
    
}
