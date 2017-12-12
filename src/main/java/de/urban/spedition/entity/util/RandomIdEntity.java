package de.urban.spedition.entity.util;

import javax.persistence.MappedSuperclass;
import javax.persistence.Id;
import static java.util.UUID.randomUUID;

@MappedSuperclass
public abstract class RandomIdEntity extends SingleIdEntity<String> {

    @Id
    protected String id;
    
    // Nutzt die Java-Implementierung des UUID-Algorithmus
    protected RandomIdEntity(){
        this.id = randomUUID().toString();
    }
    
    @Override
    public String getId() {
        return this.id;
    }

}
