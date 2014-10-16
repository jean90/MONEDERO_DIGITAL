/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.controlador.inscripcion;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FlowEvent;
import ud.ing.modi.entidades.Persona;
import ud.ing.modi.entidades.TipoDocumento;

/**
 *
 * @author Administrador
 */
@ManagedBean (name="inscripcionPersona")
@ViewScoped
public class InscripcionPersona implements Serializable{
    private Persona persona = new Persona();
    private TipoDocumento tipoDocumento = new TipoDocumento();    

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    
    public String onFlowProcess(FlowEvent event){
        System.out.println("Evento: "+event.getNewStep());
        return event.getNewStep();
    }

    public void save() {       
        FacesMessage msg = new FacesMessage("Successful", "Welcome :" + persona.getNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    
}
