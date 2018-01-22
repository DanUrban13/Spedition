package de.urban.spedition.service;

import de.urban.spedition.entity.FsKlasse;
import de.urban.spedition.entity.Mitarbeiter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@RequestScoped
public class MitarbeiterService implements MitarbeiterServiceIF {
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private Logger logger;

    @Transactional
    @Override
    public Mitarbeiter erstelleMitarbeiter(Mitarbeiter neuerMitarbeiter) {
        for (FsKlasse f : neuerMitarbeiter.getFuehrerscheinklassen()) {
            if (f != null) em.find(FsKlasse.class, f.getId());
        }
        em.persist(neuerMitarbeiter);
        return neuerMitarbeiter;
    }

    @Override
    public List<Mitarbeiter> leseAlleMitarbeiter() {
        TypedQuery<Mitarbeiter> query = em.createNamedQuery("Mitarbeiter.alle", Mitarbeiter.class);
        try {
            List<Mitarbeiter> res = query.getResultList();
            return res;
        } catch (Exception e) {
            logger.log(Level.INFO, e.toString());
            return null;
        }
        
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
            logger.log(Level.INFO, e.toString());
            return mitarbeiter;
        }
    }
    
}
