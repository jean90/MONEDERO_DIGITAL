/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ud.ing.modi.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ud.ing.modi.config.Config;
import ud.ing.modi.ldap.AccesoLDAP;
import ud.ing.modi.mapper.PendientesMapper;
import ud.ing.modi.utilidades.Cifrado;

/**
 *
 * @author Administrador
 */
public class ActivacionCuentaServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        doPost(req, res);
    }

    /**
     * Este método se ejecuta tan pronto se ejecuta la url enviada al correo para activar la cuenta ejecutando este servlet. Aquí se llama el método de desencripción para el código de solicitud y se valida que esa solicitud exista realmente. Finalmente activa la cuenta.
     * @param req Corresponde al request de la petición
     * @param res Corresponde al response de la petición
     * @throws ServletException
     * @throws IOException 
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try{//Puse el try catch porque si el registro en BD no tenía nick botaba error en código de la página, más no direccionaba a página de ErrorLogin
            System.out.println("ID ANTES DE: "+req.getParameter("id"));
            String id=req.getParameter("id");
            PendientesMapper mapeador=new PendientesMapper();
            Cifrado cifra=new Cifrado();
            cifra.addKey(Config.getConfig().getPropiedad("CLAVE_PRIVADA_MENSAJERIA"));
            System.out.println("CLAVE PRIVADA: "+Config.getConfig().getPropiedad("CLAVE_PRIVADA_MENSAJERIA"));
            id=cifra.desencriptar(id);
            System.out.println("ID DESPUÉS DE: "+id);
            System.out.println("ENCONTRADO: "+mapeador.buscarSolicitud(id));
            AccesoLDAP ldap=new AccesoLDAP();
            
            String nick=mapeador.buscarSolicitud(id);
            System.out.println("ESTADOOOOOOO "+ldap.getEstadoCuenta(nick));
            if (nick!=null&&ldap.getEstadoCuenta(nick).equals(AccesoLDAP.CUENTA_PENDIENTE_ACTIVACION)) {//Se puso tmbn la validación de si el estado actual es pendiente de activación. Si no, va a la pantalla de error.
                System.out.println("ACTIVANDO CUENTA ...");
                ldap.modificarEstadoCuenta(nick, AccesoLDAP.CUENTA_ACTIVA);
                mapeador.borrarPendiente(id);
                System.out.println("CUENTA ACTIVADA!");
                res.sendRedirect(req.getContextPath());
            }else{
                res.sendRedirect(req.getContextPath()+"/faces/LogIn/ErrorLogIn.xhtml");//Esta página se va a cambiar
            }
            
         }catch(Exception e){
             System.out.println("Error en el Servlet "+e.toString());
             res.sendRedirect(req.getContextPath()+"/faces/LogIn/ErrorLogIn.xhtml");
         }   
            
    }

}
