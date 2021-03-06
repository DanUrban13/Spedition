/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.urban.spedition.service;

import de.karatay.webshop.service.Bestellung;
import de.urban.spedition.DTO.TBestellung;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

@RequestScoped
@Alternative
public class TestBestellungService implements BestellungIF {

    @Inject
    private Logger logger;

    @Override
    public TBestellung lieferdatumPublizieren(long bestellNr, Date lieferdatum) {
        TBestellung b = new TBestellung();
        try {
           b.setBestellNr(bestellNr);
           b.setLieferdatum(lieferdatum);
           b.setbA(new Bestellung());
        } catch(Exception e) {
           logger.log(Level.INFO, e.toString());
        } 
        return b;
    }
}
