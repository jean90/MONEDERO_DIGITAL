/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.controlador.inscripcion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FlowEvent;
import ud.ing.modi.controlador.ControlPersona;
import ud.ing.modi.entidades.PendienteRegis;
import ud.ing.modi.entidades.Persona;
import ud.ing.modi.entidades.TipoDocumento;
import ud.ing.modi.ldap.AccesoLDAP;
import ud.ing.modi.mapper.DocumentoMapper;
import ud.ing.modi.mapper.PendientesMapper;
import ud.ing.modi.mapper.PersonMapper;

/**
 *
 * @author Lufe
 */
@ManagedBean(name = "inscripcionPersona")
@ViewScoped
public class InscripcionPersona implements Serializable {

    private Persona persona = new Persona();
    private TipoDocumento tipoDocumento = new TipoDocumento(); 
    private PendienteRegis pendiente=new PendienteRegis();
    private String password;
    private String nick;

    public InscripcionPersona() {
        this.traerDocs();        
    }
     
    
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

    public PendienteRegis getPendiente() {
        return pendiente;
    }

    public void setPendiente(PendienteRegis pendiente) {
        this.pendiente = pendiente;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
    
    
    
    public String onFlowProcess(FlowEvent event) {
        System.out.println("Evento: " + event.getNewStep());
        return event.getNewStep();
    }
    
    public void save() {
        /*if(this.tipoDocumento.getCodigotipoDocumento()=="1"){
         this.tipoDocumento.setDesDocumento(null);
         }*/
        PersonMapper mapeador = new PersonMapper();
        PendientesMapper mapPend = new PendientesMapper();
        System.out.println("NICK: "+nick+"; PSS: "+password);
        try {
            mapeador.guardarPersona(this.persona);
            this.pendiente.setIdPersona(this.persona.getIdPersona());
            this.pendiente.setFechaSolic(new Date());
            mapPend.guardarPendiente(this.pendiente);
            registroLdap();
            FacesMessage msg = new FacesMessage("Successful", "Welcome :" + persona.getNombre());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            Logger.getLogger(InscripcionPersona.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessage msg = new FacesMessage("Error", "Ha ocurrido un error en su registro");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
        
    }
    
    public void registroLdap(){
        AccesoLDAP ldap= new AccesoLDAP();
        ldap.InsertarUsuario(persona, this.nick, this.password);
    }
    
    public void traerDocs(){
        DocumentoMapper mapDoc=new DocumentoMapper();
        List<TipoDocumento> tipoDoc=mapDoc.obtenerDocs();
       // tipoDoc=mapDoc.obtenerDocs(); 
        //TipoDocumento docs[]=new TipoDocumento[tipoDoc.size()];
        //tipoDoc.toArray(docs);
        for (int i = 0; i < tipoDoc.size(); i++) {
            System.out.println("LONGITUD: **** "+tipoDoc.size());
            TipoDocumento doc=tipoDoc.get(i);
            System.out.println("DOC: **** "+tipoDoc.get(i));
       //     System.out.println("DOCUMENTO "+i+1+"--"+((TipoDocumento)tipoDoc.get(i)).getDesDocumento());
        }
    }
    
}
