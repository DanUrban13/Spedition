
package de.urban.spedition.entity.converter;

import de.urban.spedition.entity.FsKlasse;
import de.urban.spedition.entity.Mitarbeiter;
import de.urban.spedition.service.MitarbeiterService;
import de.urban.spedition.service.TransportfahrzeugService;
import java.io.Serializable;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
public class FsKlasseConverter implements Converter, Serializable{
    
    @Inject
    private TransportfahrzeugService transportfahrzeugService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
      
        if (value == null || value.equals(""))
            return "";
        FsKlasse fsKlasse = transportfahrzeugService.findeFsKlasse(Integer.parseInt(value));
 
        if (fsKlasse == null)
            return "";
        return fsKlasse;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null)
            return "oh null";
        if(!value.getClass().equals(FsKlasse.class))
            return null;
        
        long id = ((FsKlasse)value).getId();
        return Long.toString(id);
    }
}
