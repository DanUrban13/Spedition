package de.urban.spedition.service;

import de.urban.spedition.DTO.TBestellung;
import de.urban.spedition.entity.Adresse;
import de.urban.spedition.entity.Auftrag;
import de.urban.spedition.entity.FsKlasse;
import de.urban.spedition.entity.Lieferadresse;
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
        // remap des inputs
        Auftrag auftrag = new Auftrag();
        List<Paket> pakete = new ArrayList<Paket>();
        for (Paket p : neuerAuftrag.getIndividualPakete()) {
            Paket neu = new Paket();
            neu.setBreiteInM(p.getBreiteInM());
            neu.setHoeheInM(p.getHoeheInM());
            neu.setLaengeInM(p.getLaengeInM());
            neu.setGewichtInKg(p.getGewichtInKg());
            pakete.add(neu);
        }
        int counter = 1;
        if (!(neuerAuftrag.getContainer().isEmpty())) {
            for (PaketContainer pC : neuerAuftrag.getContainer()) {
                for (Paket p : pC.getPakete()) {
                    Paket neu = new Paket();
                    neu.setBreiteInM(p.getBreiteInM());
                    neu.setHoeheInM(p.getHoeheInM());
                    neu.setLaengeInM(p.getLaengeInM());
                    neu.setGewichtInKg(p.getGewichtInKg());
                    neu.setPfuschNummer(counter);
                    pakete.add(neu);
                    counter++;
                }
            }
        };
        auftrag.setIndividualPakete(pakete);
        auftrag.setContainer(new ArrayList<PaketContainer>());
        auftrag.setContainer(packePakete(auftrag.getIndividualPakete()));
//        for (PaketContainer pC : auftrag.getContainer()) {
//            for (int i = 0; i< auftrag.getIndividualPakete().size();i++) {
//                for (Paket s : pC.getPakete()) {
//                    if (auftrag.getIndividualPakete().get(i) != null) {
//                        if (auftrag.getIndividualPakete().get(i).getPfuschNummer() == s.getPfuschNummer()) {
//                            auftrag.getIndividualPakete().remove(i);
//                        }
//                    }
//                }
//            }
//        }
        auftrag.setBestellNr(neuerAuftrag.getBestellNr());
        auftrag.setLieferDatum(new Date());
        auftrag.setTransporter(new Transportfahrzeug());
        Lieferadresse l = new Lieferadresse();
        l.setLieferName(neuerAuftrag.getZiel().getLieferName());
        Adresse a = new Adresse();
        a.setNr(neuerAuftrag.getZiel().getAdresse().getNr());
        a.setOrt(neuerAuftrag.getZiel().getAdresse().getOrt());
        a.setPlz(neuerAuftrag.getZiel().getAdresse().getPlz());
        a.setStrasse(neuerAuftrag.getZiel().getAdresse().getStrasse());
        l.setAdresse(a);
        auftrag.setZiel(l);
        // berechne Aufrag Volumen
        double volumen = 0;
        for (PaketContainer p : auftrag.getContainer()) {
            volumen = volumen + 1.0;
        }
        for (Paket p : auftrag.getIndividualPakete()) {
            volumen = volumen + 
                    p.getBreiteInM()*p.getHoeheInM()*p.getLaengeInM();
        }
        // finde Fahrzeug in welches der Auftrag passt
        Transportfahrzeug tf = new Transportfahrzeug();
        tf = tfS.findeVerfuegbaresFahrzeug(volumen);
        // finde Mitarbeiter welcher Fahrzeug bedienen kann
        // wenn fahrzeug noch keinen Mitarbeiter zugewiesen hat
        if (tf.getFahrer() == null && tf.getKennzeichen()!= null && tf.getKennzeichen().length() != 0) {
            for (Mitarbeiter m : mS.leseAlleMitarbeiter()){
                if (m.getFahrzeug() == null) {
                    for (FsKlasse fs : m.getFuehrerscheinklassen()){
                        if(fs.getId() == tf.getFsBenoetigt().getId()) {
                            tf.setFahrer(m);
                            break;
                        }
                    }
                }
                if (tf.getFahrer() != null) break;
            }
        }
        
        auftrag.setZiel(em.merge(auftrag.getZiel()));
        for (Paket p : auftrag.getIndividualPakete()) {
            em.persist(p);
        }

        if (tf.getKennzeichen()!= null && tf.getKennzeichen().length() != 0)
            auftrag.setTransporter(em.merge(tf));
        
        // berechne Lieferdatum aufgrund der Auftraege des Transportfahrzeuges
        Date lieferdatum = new Date();
        // garantierte lieferzeit sind 5 tage
        lieferdatum.setDate(lieferdatum.getDate()+5);
        for (Auftrag aa : tf.getAktuelleAuftraege()){
            if (aa.getLieferDatum().before(lieferdatum)) lieferdatum = aa.getLieferDatum();
        }        
        
        auftrag.setLieferDatum(lieferdatum);
                
        em.persist(auftrag);
        
        for (PaketContainer pC : auftrag.getContainer()) {
            pC.setAuftrag(auftrag);
            for (Paket p: pC.getPakete()) {
                p.setContainer(pC);
                em.persist(p);
            }
            em.merge(pC);
        }
        
        TBestellung b = bestellService.lieferdatumPublizieren(auftrag.getBestellNr(), auftrag.getLieferDatum());
        
        if (b.getbA() == null) logger.log(Level.INFO, "webshop brachte null als return");

        return auftrag;
    }
    
    @WebMethod(exclude=true)
    @Override
    public List<PaketContainer> packePakete(List<Paket> pakete) {
        List<PaketContainer> container = new ArrayList<PaketContainer>();
        List<Paket> packbarePakete = new ArrayList<Paket>();
        for (Paket p : pakete) {
            if(!(p.getBreiteInM() > 1.0 || p.getHoeheInM() > 1.0 || p.getLaengeInM() > 1) && p!=null)
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
    public Auftrag findeAuftrag(long id) {
        return em.find(Auftrag.class, id);
    }

    
    @WebMethod(exclude=true)
    @Transactional
    @Override
    public Auftrag aendereAuftrag(Auftrag auftrag) {
        System.out.println(auftrag.toString());
        Auftrag auftragO = em.find(Auftrag.class, auftrag.getId());
        auftragO.setLieferDatum(auftrag.getLieferDatum());
        auftragO.setZiel(em.merge(auftrag.getZiel()));
        em.merge(auftragO);
        return auftragO;
    }

    @WebMethod(exclude=true)
    @Transactional
    @Override
    public Auftrag loescheAuftrag(Auftrag auftrag) {
        for (PaketContainer pC : auftrag.getContainer()) {
            pC.setAuftrag(null);
            TypedQuery<Paket> query = em.createNamedQuery("Paket.container", Paket.class);
            long param = 0;
            param = pC.getId();
            query.setParameter("c", param);
            List<Paket> pList = query.getResultList();
            for (Paket p : pList) {
                if (p != null){
                    p = em.find(Paket.class, p.getId());
                    em.remove(p);
                }
            }
            pC.setPakete(null);
            em.merge(pC);
        }
        auftrag = em.merge(auftrag);
        em.remove(auftrag);
        return null;
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

    @WebMethod(exclude=true)
    @Override
    public void lieferdatumPublizieren(long nr) {
        Auftrag auftrag = em.find(Auftrag.class, nr);
        if (auftrag != null) {
            bestellService.lieferdatumPublizieren(auftrag.getBestellNr(), auftrag.getLieferDatum());
        }
    }
}
