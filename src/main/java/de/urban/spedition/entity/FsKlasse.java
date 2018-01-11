package de.urban.spedition.entity;

import de.urban.spedition.entity.util.GeneratedIdEntity;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(
        name="FsKlasse.alle",
        query="SELECT f FROM FsKlasse AS f"
)
public class FsKlasse extends GeneratedIdEntity{
    
    private String fsName;
    
    private String beschreibung;

    public FsKlasse() {
    }

    public FsKlasse(String fsKlasse, String beschreibung) {
        this.fsName = fsKlasse;
        this.beschreibung = beschreibung;
    }

    public String getFsKlasse() {
        return fsName;
    }

    public void setFsKlasse(String fsKlasse) {
        this.fsName = fsKlasse;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.fsName);
        hash = 83 * hash + Objects.hashCode(this.beschreibung);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FsKlasse other = (FsKlasse) obj;
        if (!Objects.equals(this.fsName, other.fsName)) {
            return false;
        }
        if (!Objects.equals(this.beschreibung, other.beschreibung)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FsKlasse{" + "fsKlasse=" + fsName + ", beschreibung=" + beschreibung + '}';
    }
    
    
    
}
