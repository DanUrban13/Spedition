package de.urban.spedition.service;

import de.urban.spedition.entity.Mitarbeiter;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@RequestScoped
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

    @Override
    public List<Mitarbeiter> leseAlleMitarbeiter() {
        TypedQuery<Mitarbeiter> query = em.createNamedQuery("Mitarbeiter.alle", Mitarbeiter.class);
        return query.getResultList();
    }

    @Override
    public Mitarbeiter findeMitarbeiter(long id) {
        Mitarbeiter m = em.find(Mitarbeiter.class, id);
        return m;
    }

    @Transactional
    @Override
    public Mitarbeiter aendereMitarbeiter(Mitarbeiter mitarbeiter) {
        this.em.merge(mitarbeiter);
        return mitarbeiter;
    }

    @Transactional
    @Override
    public Mitarbeiter loescheMitarbeiter(Mitarbeiter mitarbeiter) {
        try {
            mitarbeiter = em.merge(mitarbeiter);
            this.em.remove(mitarbeiter);
            return null;
        } catch (Exception e) {
            return mitarbeiter;
        }
    }
    
}
