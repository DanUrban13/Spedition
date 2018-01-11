package de.urban.spedition.service;

import de.urban.spedition.entity.FsKlasse;
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
        System.out.println("neues tpf" + neuesTpf.toString());
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
        if (query != null) return query.getResultList();
        
        return null;
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
    public Transportfahrzeug findeGroesstesVerfuegbares() {
        List<Transportfahrzeug> alleT = new ArrayList<Transportfahrzeug>();
        TypedQuery<Transportfahrzeug> query = em.createNamedQuery("Transportfahrzeug.alle", Transportfahrzeug.class);
        if (query != null) alleT = query.getResultList();
        Transportfahrzeug res = new Transportfahrzeug();
        for (Transportfahrzeug i : alleT) {
            double volume = i.getLadeBreiteInM()*i.getLadeHoeheInM()*i.getLadeLaengeInM();
            double resVolume = res.getLadeBreiteInM()*res.getLadeHoeheInM()*res.getLadeLaengeInM();
            if(volume>resVolume && i.getAktuellerAuftrag()==null) res = i;
        }
        return res;
    }
    
    
}
