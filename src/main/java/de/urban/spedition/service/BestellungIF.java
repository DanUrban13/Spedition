/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.urban.spedition.service;

import de.urban.spedition.DTO.TBestellung;
import java.util.Date;

/**
 *
 * @author Dan
 */
public interface BestellungIF {
    public TBestellung lieferdatumPublizieren(String bestellNr, Date lieferdatum);
}
