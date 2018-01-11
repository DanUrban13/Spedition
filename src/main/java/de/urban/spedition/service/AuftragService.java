package de.urban.spedition.service;

import de.urban.spedition.entity.Auftrag;
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
        try {
//            for ()
//            neuerAuftrag.set
//                    neuesTpf.setFsBenoetigt(em.merge(neuesTpf.getFsBenoetigt()));
//            em.persist(neuesTpf);
            //em.persist(em);
            neuerAuftrag.setLieferDatum(new Date());
        } catch (Exception e) {
            logger.log(Level.INFO, e.toString());
            return null;
        }
        String message = "Auftrag Erstellung eingegangen. Auftrag: " + neuerAuftrag.toString();
        logger.log(Level.INFO,message);
        return neuerAuftrag;
    }
    
    @WebMethod(exclude=true)
    @Override
    public List<PaketContainer> packePakete(List<Paket> pakete) {
        System.out.println("packePakete");
        List<PaketContainer> container = new ArrayList<PaketContainer>();
        List<Paket> packbarePakete = new ArrayList<Paket>();
        for (Paket p : pakete) {
            if(!(p.getBreiteInM() > 1.0 || p.getHoeheInM() > 1.0 || p.getLaengeInM() > 1))
                packbarePakete.add(p);
        }
        
        TypedQuery<PaketContainer> query = em.createNamedQuery("PaketContainer.alle", PaketContainer.class);
        container = query.getResultList();
        System.out.println("Alle Container");
        for (PaketContainer pC: container) System.out.println(pC.toString());
        List<PaketContainer> verfuegbareContainer = new ArrayList<PaketContainer>();
        for (PaketContainer i : container) {
            if (i.getPakete().isEmpty()) verfuegbareContainer.add(i);
        }
        System.out.println("verfuegbare Container");
        for (PaketContainer pC: verfuegbareContainer) System.out.println(pC.toString());
        System.out.println("packbare Pakete");
        for (Paket p : packbarePakete) System.out.println(p.toString());
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
                    System.out.println("Fuege Paket zu Container");
                    i.getPakete().add(packbarePakete.get(counter));
                    System.out.println("test1");
                    iVolumen = iVolumen - paketVolumen;
                    iMaxGewicht = iMaxGewicht - packbarePakete.get(counter).getGewichtInKg();
                    System.out.println("test2");
                    packbarePakete.remove(counter);
                    counter--;
                    System.out.println("test3");
                } else {
                    System.out.println("test");
                    break;
                }
                counter++;
            }
            if (!(i.getPakete().isEmpty())) result.add(i);
        }
        System.out.println("ergebnis");
        return result;
    }  
       
    private Transportfahrzeug findePassendesFahrzeug(){
        
        return null;
    }
    
    private Mitarbeiter findePassendenMitarbeiter(){
        return null;
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

//    @WebMethod(exclude=true)
//    @Transactional
//    @Override
//    public Paket erstellePaket(Paket p) {
//        try {
//            em.persist(p);
//            return p;
//        } catch (Exception e) {
//            logger.log(Level.INFO, e.toString());
//            return null;
//        }
//    }

    @WebMethod(exclude=true)
    @Transactional
    @Override
    public List<Paket> erstelleTestPakete() {
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
            testPakete.add(p);
        } 
        return testPakete;
    }

    @WebMethod(exclude=true)
    @Override
    public List<Auftrag> leseAlleAuftraege() {
        TypedQuery<Auftrag> query = em.createNamedQuery("Auftrag.alle", Auftrag.class);
        return query.getResultList();
    }
    
    
    
    
      

    
}
