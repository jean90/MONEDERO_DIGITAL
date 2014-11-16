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
            System.out.println(req.getParameter("id"));
            res.sendRedirect(req.getContextPath()+"/faces/LogIn/LogIn.xhtml");
    }

}
