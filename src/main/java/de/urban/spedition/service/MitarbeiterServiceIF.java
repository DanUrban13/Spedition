package de.urban.spedition.service;

import de.urban.spedition.entity.Mitarbeiter;
import java.io.Serializable;
import java.util.List;

public interface MitarbeiterServiceIF extends Serializable {
    public Mitarbeiter erstelleMitarbeiter(Mitarbeiter neuerMitarbeiter);
    public List<Mitarbeiter> leseAlleMitarbeiter();
    public Mitarbeiter findeMitarbeiter(long id);
    public Mitarbeiter aendereMitarbeiter(Mitarbeiter mitarbeiter);
    public Mitarbeiter loescheMitarbeiter(Mitarbeiter mitarbeiter);
}
