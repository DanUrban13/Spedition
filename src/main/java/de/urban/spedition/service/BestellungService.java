
package de.urban.spedition.service;

import de.karatay.webshop.service.Bestellung;
import de.urban.spedition.DTO.TBestellung;
import java.time.LocalDate;
import java.util.Date;
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
    public TBestellung lieferdatumPublizieren(String bestellNr, Date lieferdatum) {
        TBestellung b = new TBestellung();
        try {
            //TODO setzte Lieferdatum bei Karatay
            Bestellung bA = new Bestellung();
            bA.setBestellNr(Long.parseLong(bestellNr,10));
            LocalDate in = LocalDate.parse("2017-01-01");
            XMLGregorianCalendar out = DatatypeFactory.newInstance().newXMLGregorianCalendar(in.toString());
            bA.setLieferDatum(out);
            
            try { // Call Web Service Operation
                de.karatay.webshop.service.BestellungService_Service service = new de.karatay.webshop.service.BestellungService_Service();
                de.karatay.webshop.service.BestellungService port = service.getBestellungServicePort();
                de.karatay.webshop.service.Bestellung result = port.setzeLieferdatum(bA);
                if (result != null) {
                    return b;
                } else {
                    return null;
                }
            } catch (Exception ex) {
                // TODO handle custom exceptions here
            }

        } catch(Exception e) {
            logger.log(Level.INFO, e.toString());
        } 
        
        return b;
        
    }
    
}
