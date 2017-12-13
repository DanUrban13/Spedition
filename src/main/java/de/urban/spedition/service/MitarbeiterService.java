package de.urban.spedition.service;

import de.urban.spedition.entity.Mitarbeiter;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@WebService
public class MitarbeiterService implements MitarbeiterServiceIF {
    
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Mitarbeiter erstelleMitarbeiter(Mitarbeiter neuerMitarbeiter) {
        System.out.println("Erstelle neuen Mitarbeiter: "
                + neuerMitarbeiter.getVorname() + " " + neuerMitarbeiter.getName());
        em.persist(neuerMitarbeiter);
        return null;
    }
    
}
