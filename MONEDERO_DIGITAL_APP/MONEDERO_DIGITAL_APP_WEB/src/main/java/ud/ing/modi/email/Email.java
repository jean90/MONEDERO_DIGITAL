/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.email;

import java.util.HashMap;

/**
 *
 * @author Administrador
 */
public abstract class Email {
    private String mensaje;
    protected final String asunto;
    protected final String template;
    private String destinatario;
    private HashMap datos;
    
    public Email(String asunto, String template){
        this.asunto=asunto;
        this.template=template;
    }
    public Email(String asunto, String template, String destinatario){   
        this.asunto=asunto;
        this.template=template;
        this.destinatario=destinatario;        
    }
    public void enviarMensaje() {
        EmailEnvio.enviarMail(this.destinatario, this.mensaje, this.asunto);
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }    

    public HashMap getDatos() {
        return datos;
    }

    public void setDatos(HashMap datos) {
        this.datos = datos;
    }
    
    protected void ensamblarMensaje(){
        this.mensaje=ConstructorEmail.construirMensaje(this.getDatos(),this.template);
    }
    
}
