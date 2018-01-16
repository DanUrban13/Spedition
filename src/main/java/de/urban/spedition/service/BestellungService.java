
package de.urban.spedition.service;

import de.karatay.webshop.service.Bestellung;
import de.urban.spedition.DTO.TBestellung;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

@RequestScoped
@Alternative
public class BestellungService implements BestellungIF {

    @Inject
    private Logger logger;
    
    @Override
    public TBestellung lieferdatumPublizieren(long bestellNr, Date lieferdatum) {
        TBestellung b = new TBestellung();
        try {
            //TODO setzte Lieferdatum bei Karatay
            Bestellung bA = new Bestellung();
            bA.setId(bestellNr);
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(lieferdatum);
            XMLGregorianCalendar lieferdatumGC = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            bA.setLieferDatum(lieferdatumGC);
            
            try { // Call Web Service Operation
                de.karatay.webshop.service.BestellungService_Service service = new de.karatay.webshop.service.BestellungService_Service();
                de.karatay.webshop.service.BestellungService port = service.getBestellungServicePort();
                de.karatay.webshop.service.Bestellung result = port.setzeLieferdatum(bA);
                b.setbA(result);;
                if (result != null) {
                    return b;
                } else {
                    return null;
                }
            } catch (Exception ex) {
                logger.log(Level.INFO, ex.toString() );
                logger.log(Level.INFO, "webshop nicht erreichbar" );
                return null;
            }

        } catch(Exception e) {
            logger.log(Level.INFO, e.toString());
            return null;
        }        
    }
    
}
