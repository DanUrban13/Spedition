package de.urban.spedition.service;

import de.urban.spedition.entity.Auftrag;
import de.urban.spedition.entity.FsKlasse;
import de.urban.spedition.entity.Paket;
import de.urban.spedition.entity.PaketContainer;
import de.urban.spedition.entity.Transportfahrzeug;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;


@RequestScoped
public class TransportfahrzeugService implements TransportfahrzeugServiceIF {
    
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public FsKlasse erstelleFuehrerscheinklasse(FsKlasse fsk) {
        em.persist(fsk);
        if (fsk != null) return fsk;
        return null;
    }

    @Transactional
    @Override
    public Transportfahrzeug erstelleTransportfahrzeug(Transportfahrzeug neuesTpf) {
        neuesTpf.setFsBenoetigt(em.merge(neuesTpf.getFsBenoetigt()));
        em.persist(neuesTpf);
        return null;
    }

    @Override
    public List<FsKlasse> leseAlleFsKlasse() {
        
        TypedQuery<FsKlasse> query = em.createNamedQuery("FsKlasse.alle", FsKlasse.class);
        if (query != null) return query.getResultList();
        
        return null;
    }

    @Override
    public List<Transportfahrzeug> leseAlleTransportfahrzeuge() {
        
        TypedQuery<Transportfahrzeug> query = em.createNamedQuery("Transportfahrzeug.alle", Transportfahrzeug.class);
        return query.getResultList();
    }

    @Override
    public FsKlasse findeFsKlasse(long id) {
        FsKlasse f = em.find(FsKlasse.class, id);
        return f;
    }

    @Override
    public Transportfahrzeug findeTransportfahrzeug(long id) {
        Transportfahrzeug t = em.find(Transportfahrzeug.class, id);
        return t;
    }

    @Override
    public Transportfahrzeug findeVerfuegbaresFahrzeug(double volumen) {
        // Alle Fahrzeuge auslesen
        List<Transportfahrzeug> alleT = new ArrayList<Transportfahrzeug>();
        TypedQuery<Transportfahrzeug> query = em.createNamedQuery("Transportfahrzeug.alle", Transportfahrzeug.class);
        if (query != null) alleT = query.getResultList();
        
        
        Transportfahrzeug res = new Transportfahrzeug();
        for (Transportfahrzeug i : alleT) {
            // berechne aktuelles Ladevolumen
            double aktuellesLadevolumen = 0;
            for (Auftrag a: i.getAktuelleAuftraege()) {
                for (PaketContainer p : a.getContainer()) {
                    aktuellesLadevolumen = aktuellesLadevolumen + 1.0;
                }
                for (Paket p : a.getIndividualPakete()) {
                    aktuellesLadevolumen = aktuellesLadevolumen + 
                            p.getBreiteInM()*p.getHoeheInM()*p.getLaengeInM();
                }
            }
            
            // waehle Fahrzeug welches schon eine Ladung hat
            if (aktuellesLadevolumen != 0) {
                double maxLadeVolumen = i.getLadeBreiteInM()*i.getLadeHoeheInM()*i.getLadeLaengeInM();
                // fuellgrad max 80 Prozent
                if ((maxLadeVolumen-aktuellesLadevolumen-volumen)>(maxLadeVolumen*0.2)) {
                    res = i;
                    return res;
                }
            } else {
                res = i;
            }
        }
        return res;
    }

    @Transactional
    @Override
    public Transportfahrzeug loescheTransportfahrzeug(Transportfahrzeug tpf) {
        tpf = em.find(Transportfahrzeug.class, tpf.getId());
        em.remove(tpf);
        return null;
    }
    
    
}
