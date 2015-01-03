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

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
            System.out.println("ID ANTES DE: "+req.getParameter("id"));
            String id=req.getParameter("id");
            PendientesMapper mapeador=new PendientesMapper();
            Cifrado cifra=new Cifrado();
            cifra.addKey(Config.getConfig().getPropiedad("CLAVE_PRIVADA_MENSAJERIA"));
            System.out.println("CLAVE PRIVADA: "+Config.getConfig().getPropiedad("CLAVE_PRIVADA_MENSAJERIA"));
            id=cifra.desencriptar(id);
            System.out.println("ID DESPUÉS DE: "+id);
            System.out.println("ENCONTRADO: "+mapeador.buscarSolicitud(id));
            if (mapeador.buscarSolicitud(id)) {
                res.sendRedirect(req.getContextPath()+"/faces/LogIn/LogIn.xhtml");
            }else{
                res.sendRedirect(req.getContextPath()+"/faces/LogIn/ErrorLogIn.xhtml");
            }
            
    }

}
