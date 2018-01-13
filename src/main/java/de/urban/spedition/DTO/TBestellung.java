
package de.urban.spedition.DTO;

import de.karatay.webshop.service.Bestellung;
import java.util.Date;

public class TBestellung {
    private long bestellNr;
    private Date lieferdatum;
    private Bestellung bA;
    
    private boolean bestellungAbgeschlossen;

    public long getBestellNr() {
        return bestellNr;
    }

    public void setBestellNr(long bestellNr) {
        this.bestellNr = bestellNr;
    }

    public Date getLieferdatum() {
        return lieferdatum;
    }

    public void setLieferdatum(Date lieferdatum) {
        this.lieferdatum = lieferdatum;
    }

    public boolean isBestellungAbgeschlossen() {
        return bestellungAbgeschlossen;
    }

    public void setBestellungAbgeschlossen(boolean bestellungAbgeschlossen) {
        this.bestellungAbgeschlossen = bestellungAbgeschlossen;
    }

    public Bestellung getbA() {
        return bA;
    }

    public void setbA(Bestellung bA) {
        this.bA = bA;
    }

    @Override
    public String toString() {
        return "TBestellung{" + "bestellNr=" + bestellNr + ", lieferdatum=" + lieferdatum + ", bestellungAbgeschlossen=" + bestellungAbgeschlossen + '}';
    }
        
}
