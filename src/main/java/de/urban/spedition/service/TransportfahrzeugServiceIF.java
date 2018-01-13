package de.urban.spedition.service;

import de.urban.spedition.entity.FsKlasse;
import de.urban.spedition.entity.Transportfahrzeug;
import java.io.Serializable;
import java.util.List;
import javax.jws.WebService;

@WebService
public interface TransportfahrzeugServiceIF extends Serializable {
    public FsKlasse erstelleFuehrerscheinklasse(FsKlasse fsk);
    public Transportfahrzeug erstelleTransportfahrzeug(Transportfahrzeug neuesTpf);
    public Transportfahrzeug loescheTransportfahrzeug(Transportfahrzeug tpf);
    public List<FsKlasse> leseAlleFsKlasse();
    public List<Transportfahrzeug> leseAlleTransportfahrzeuge();
    public FsKlasse findeFsKlasse(long id);
    public Transportfahrzeug findeTransportfahrzeug(long id);
    public Transportfahrzeug findeVerfuegbaresFahrzeug(double volumen);
    
}
