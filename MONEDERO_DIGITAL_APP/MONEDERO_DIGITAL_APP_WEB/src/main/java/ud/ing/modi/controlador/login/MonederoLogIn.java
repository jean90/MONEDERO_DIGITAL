package ud.ing.modi.controlador.login;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import ud.ing.modi.entidades.PendienteRegis;
import ud.ing.modi.entidades.Persona;
import ud.ing.modi.ldap.AccesoLDAP;
import ud.ing.modi.ldap.ConexionLdap;
import ud.ing.modi.mapper.PendientesMapper;

/**
 *
 * @author Administrador
 */
@ManagedBean
@RequestScoped
public class MonederoLogIn {

    private String userName;
    private String password;
    /**
     * Creates a new instance of MonederoLogIn
     */
    public MonederoLogIn() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String login() {
        String rol="error";
        FacesContext contexto= FacesContext.getCurrentInstance();
        ExternalContext contextoExterno = contexto.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) contextoExterno.getRequest();
        try{
            request.login(userName, password);
            if(request.isUserInRole("Admin")){
                rol="Admin";
            }else if(request.isUserInRole("Monedero")){
                rol="Monedero";
            }
        }catch (ServletException ex) {            
            System.out.println("ERROR DE LOGGEO "); 
            AccesoLDAP ldap= new AccesoLDAP();
            if(ldap.usuarioExiste(userName)){
                if(!ldap.validarPassword(userName, password)){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contraseña Incorrecta", "Verifique sus datos"));
                }
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario No Existe!", "Verifique sus datos"));
            }            
            Logger.getLogger(MonederoLogIn.class.getName()).log(Level.WARNING, null, ex); 
        }        
        return rol;
    }
    
    public void pruebaLdap(){
        /*ConexionLdap lc = new ConexionLdap();
        lc.ConexionManager("admin", "123456");
        lc.CerrarConLDAP(lc.getLc());*/
        Persona per = new Persona();
        per.setApellido("Gualtero");
        per.setNombre("David");
        AccesoLDAP ldap= new AccesoLDAP();
        //ldap.InsertarUsuario(per, "10003", "123456");
        ldap.buscarUsuario(this.userName);
        
    }
    public void pruebaPendientes(){
        PendienteRegis pendiente = new PendienteRegis();
        pendiente.setIdPersona(1);
        pendiente.setFechaSolic(new Date());
        PendientesMapper mapper = new PendientesMapper();
        try {
            mapper.guardarPendiente(pendiente);
        } catch (Exception ex) {
            Logger.getLogger(MonederoLogIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
