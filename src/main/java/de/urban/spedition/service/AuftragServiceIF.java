package de.urban.spedition.service;

import de.urban.spedition.entity.Auftrag;
import de.urban.spedition.entity.Paket;
import de.urban.spedition.entity.PaketContainer;
import java.io.Serializable;
import java.util.List;

public interface AuftragServiceIF extends Serializable {
    public Auftrag erstelleAuftrag(Auftrag neuerAuftrag);
    public Auftrag findeAuftrag(long id);
    public Auftrag aendereAuftrag(Auftrag auftrag);
    public Auftrag loescheAuftrag(Auftrag auftrag);
    public List<Auftrag> leseAlleAuftraege();
    public List<Paket> erstelleTestPakete(int nr);
    public void lieferdatumPublizieren(long nr);
    public List<PaketContainer> packePakete(List<Paket> pakete);
    public PaketContainer erstellePaketContainer(PaketContainer p);
}
