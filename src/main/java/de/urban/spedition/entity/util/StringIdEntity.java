package de.urban.spedition.entity.util;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class StringIdEntity extends SingleIdEntity<String> {
    
    @Id
    protected String id;
    
    protected StringIdEntity(){
    }
    
    protected StringIdEntity(String id){
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }
    
    public void setId(String id){
        this.id=id;
    }

}
