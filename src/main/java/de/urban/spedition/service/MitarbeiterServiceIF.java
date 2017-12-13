package de.urban.spedition.service;

import de.urban.spedition.entity.Mitarbeiter;
import java.io.Serializable;

public interface MitarbeiterServiceIF extends Serializable {
    public Mitarbeiter erstelleMitarbeiter(Mitarbeiter neuerMitarbeiter);
}
