
package de.urban.spedition.entity.converter;

import de.urban.spedition.entity.Transportfahrzeug;
import de.urban.spedition.service.TransportfahrzeugServiceIF;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

@RequestScoped
public class TransportfahrzeugConverter implements Converter, Serializable{
    
    @Inject
    private TransportfahrzeugServiceIF tpfs;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
      
        if (value == null || value.equals(""))
            return "";
        Transportfahrzeug fahrzeug = tpfs.findeTransportfahrzeug(Integer.parseInt(value));
        if (fahrzeug == null)
            return "";
        return fahrzeug;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null)
            return "oh null";
        if(!value.getClass().equals(Transportfahrzeug.class))
            return null;
        
        long id = ((Transportfahrzeug)value).getId();
        return Long.toString(id);
    }
}
