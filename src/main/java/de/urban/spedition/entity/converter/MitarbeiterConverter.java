
package de.urban.spedition.entity.converter;

import de.urban.spedition.entity.Mitarbeiter;
import de.urban.spedition.service.MitarbeiterService;
import java.io.Serializable;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
public class MitarbeiterConverter implements Converter, Serializable{
    
    @Inject
    private MitarbeiterService mitarbeiterService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
      
        if (value == null || value.equals(""))
            return "";
        Mitarbeiter mitarbeiter = mitarbeiterService.findeMitarbeiter(Integer.parseInt(value));
        if (mitarbeiter == null)
            return "";
        return mitarbeiter;
//        return mitarbeiterService.findeMitarbeiter(Long.parseLong(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null)
            return "oh null";
        if(!value.getClass().equals(Mitarbeiter.class))
            return null;
        
        long id = ((Mitarbeiter)value).getId();
        return Long.toString(id);
    }
}
