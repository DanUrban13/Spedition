package de.urban.spedition.entity.util;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class GeneratedIdEntity extends SingleIdEntity<Long> {
    
    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO)
    protected Long id;

    @Override
    public Long getId() {
        return this.id;
    }

}
