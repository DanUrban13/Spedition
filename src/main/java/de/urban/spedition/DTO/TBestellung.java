
package de.urban.spedition.DTO;

import java.util.Date;

public class TBestellung {
    private long bestellNr;
    private Date lieferdatum;
    
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

    @Override
    public String toString() {
        return "TBestellung{" + "bestellNr=" + bestellNr + ", lieferdatum=" + lieferdatum + ", bestellungAbgeschlossen=" + bestellungAbgeschlossen + '}';
    }
        
}
