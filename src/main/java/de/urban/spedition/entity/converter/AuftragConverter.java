
package de.urban.spedition.entity.converter;

import de.urban.spedition.entity.Auftrag;
import de.urban.spedition.entity.Mitarbeiter;
import de.urban.spedition.service.AuftragServiceIF;
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
public class AuftragConverter implements Converter, Serializable{
    
    @Inject
    private AuftragServiceIF auftragService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
      
        if (value == null || value.equals(""))
            return "";
        Auftrag auftrag = auftragService.findeAuftrag(Integer.parseInt(value));
        if (auftrag == null)
            return "";
        return auftrag;
//        return mitarbeiterService.findeMitarbeiter(Long.parseLong(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null)
            return "oh null";
        if(!value.getClass().equals(Auftrag.class))
            return null;
        
        long id = ((Auftrag)value).getId();
        return Long.toString(id);
    }
}
