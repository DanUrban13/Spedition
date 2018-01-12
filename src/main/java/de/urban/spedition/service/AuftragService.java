package de.urban.spedition.service;

import de.urban.spedition.entity.Auftrag;
import de.urban.spedition.entity.FsKlasse;
import de.urban.spedition.entity.Mitarbeiter;
import de.urban.spedition.entity.Paket;
import de.urban.spedition.entity.PaketContainer;
import de.urban.spedition.entity.Transportfahrzeug;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;

@RequestScoped
@WebService
public class AuftragService implements AuftragServiceIF {
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private MitarbeiterServiceIF mS;
    
    @Inject
    private TransportfahrzeugServiceIF tfS;
    
    @Inject
    private BestellungIF bestellService;
    
    @Inject
    private Logger logger;

    @WebMethod
    @Transactional
    @Override
    public Auftrag erstelleAuftrag(@WebParam(name = "neuerAuftrag") Auftrag neuerAuftrag) {
            // wenn von aussen - noch keine Container verwendet
            if (neuerAuftrag.getContainer().isEmpty()) packePakete(neuerAuftrag.getIndividualPakete());
            // berechne Aufrag Volumen
            double volumen = 0;
            for (PaketContainer p : neuerAuftrag.getContainer()) {
                volumen = volumen + 1.0;
            }
            for (Paket p : neuerAuftrag.getIndividualPakete()) {
                volumen = volumen + 
                        p.getBreiteInM()*p.getHoeheInM()*p.getLaengeInM();
            }
            // finde Fahrzeug in welches der Auftrag passt
            Transportfahrzeug tf = new Transportfahrzeug();
            tf = tfS.findeVerfuegbaresFahrzeug(volumen);
            // finde Mitarbeiter welcher Fahrzeug bedienen kann
            // wenn fahrzeug noch keinen Mitarbeiter zugewiesen hat
            if (tf.getFahrer() == null) {
                for (Mitarbeiter m : mS.leseAlleMitarbeiter()){
                    for (FsKlasse fs : m.getFuehrerscheinklassen()){
                        if(fs.getFsKlasse().equalsIgnoreCase(tf.getFsBenoetigt().getFsKlasse())) {
                            tf.setFahrer(m);
                            break;
                        }
                    }
                    if (tf.getFsBenoetigt().getFsKlasse() != null) break;
                }
            }
            
            neuerAuftrag.setZiel(em.merge(neuerAuftrag.getZiel()));
            for (Paket p : neuerAuftrag.getIndividualPakete()) {
                em.persist(p);
            }

            neuerAuftrag.setTransporter(em.merge(tf));
            
            neuerAuftrag.setLieferDatum(new Date());
            em.persist(neuerAuftrag);
            for (PaketContainer pC : neuerAuftrag.getContainer()) {
                pC.setAuftrag(neuerAuftrag);
                em.merge(pC);
                for (Paket p: pC.getPakete()) {
                    p.setContainer(pC);
                    em.persist(p);
                }
            }
            
        String message = "Auftrag Erstellung eingegangen. Auftrag";
        return neuerAuftrag;
    }
    
    @WebMethod(exclude=true)
    @Override
    public List<PaketContainer> packePakete(List<Paket> pakete) {
        List<PaketContainer> container = new ArrayList<PaketContainer>();
        List<Paket> packbarePakete = new ArrayList<Paket>();
        for (Paket p : pakete) {
            if(!(p.getBreiteInM() > 1.0 || p.getHoeheInM() > 1.0 || p.getLaengeInM() > 1))
                packbarePakete.add(p);
        }
        
        TypedQuery<PaketContainer> query = em.createNamedQuery("PaketContainer.alle", PaketContainer.class);
        container = query.getResultList();
        List<PaketContainer> verfuegbareContainer = new ArrayList<PaketContainer>();
        for (PaketContainer i : container) {
            if (i.getPakete().isEmpty()) verfuegbareContainer.add(i);
        }
        int counter = 0;
        List<PaketContainer> result = new ArrayList<PaketContainer>();
        for (PaketContainer i : verfuegbareContainer) {
            double iVolumen = i.getBreiteInM()*i.getHoeheInM()*i.getLaengeInM();
            double iMaxGewicht = i.getMaxGewichtInKg();
            while (!(packbarePakete.isEmpty())) {
                double paketVolumen = packbarePakete.get(counter).getBreiteInM()
                        *packbarePakete.get(counter).getHoeheInM()
                        *packbarePakete.get(counter).getLaengeInM();
                
                if ((iVolumen*0.8-paketVolumen)>0 
                        && (iMaxGewicht-packbarePakete.get(counter).getGewichtInKg())>0) {
                    i.getPakete().add(packbarePakete.get(counter));
                    iVolumen = iVolumen - paketVolumen;
                    iMaxGewicht = iMaxGewicht - packbarePakete.get(counter).getGewichtInKg();
                    packbarePakete.remove(counter);
                    counter--;
                } else {
                    break;
                }
                counter++;
            }
            if (!(i.getPakete().isEmpty())) result.add(i);
        }
        return result;
    }  

    @WebMethod
    @Override
    public Auftrag findeAuftrag(Auftrag auftrag) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    @WebMethod(exclude=true)
    @Override
    public Auftrag aendereAuftrag(Auftrag auftrag) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @WebMethod(exclude=true)
    @Override
    public Auftrag loescheAuftrag(Auftrag auftrag) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @WebMethod(exclude=true)
    @Transactional
    @Override
    public PaketContainer erstellePaketContainer(PaketContainer p) {
        try {
            p.setAktuellesGewichtInKg(0);
            p.setAuftrag(null);
            em.persist(p);
            return p;
        } catch (Exception e) {
            logger.log(Level.INFO, e.toString());
            return null;
        }
    }

    @WebMethod(exclude=true)
    @Transactional
    @Override
    public List<Paket> erstelleTestPakete(int nr) {
        List<Paket> testPakete = new ArrayList<Paket>();
        Random rng = new Random();
        for (int i = 1; i <= 15; i++) {
            Paket p = new Paket();
            double gewicht = rng.nextDouble()*25;
            int s = 0;
            if (gewicht>10) s=1;
            p.setBreiteInM(rng.nextDouble()+s);
            p.setLaengeInM(rng.nextDouble()+s);
            p.setHoeheInM(rng.nextDouble()+s);
            p.setGewichtInKg(gewicht);
            p.setPfuschNummer(++nr);
            testPakete.add(p);
        }        
        for (int i = 1; i <= 15; i++) {
            Paket p = new Paket();
            double gewicht = rng.nextDouble()*5;
            int s = 0;
            if (gewicht>10) s=1;
            p.setBreiteInM(rng.nextDouble()+s);
            p.setLaengeInM(rng.nextDouble()+s);
            p.setHoeheInM(rng.nextDouble()+s);
            p.setGewichtInKg(gewicht);
            p.setPfuschNummer(++nr);
            testPakete.add(p);
        } 
        return testPakete;
    }

    @WebMethod(exclude=true)
    @Override
    public List<Auftrag> leseAlleAuftraege() {
        List<Auftrag> res = new ArrayList<Auftrag>();
        TypedQuery<Auftrag> query = em.createNamedQuery("Auftrag.alle", Auftrag.class);
        res = query.getResultList();
        return res;
    }
    
    
    
    
      

    
}
