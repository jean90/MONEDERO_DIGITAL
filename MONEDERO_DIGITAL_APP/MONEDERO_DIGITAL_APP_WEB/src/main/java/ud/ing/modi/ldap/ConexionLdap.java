/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.ldap;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrador
 */
public class ConexionLdap {
    private int ldapPort;
     private int ldapVersion;
     private LDAPConnection lc;
     private String login;
     private String ldapHost;

    public ConexionLdap() {
        this.ldapPort=10389;
        this.ldapVersion = LDAPConnection.LDAP_V3;
        this.ldapHost="localhost";
    }
     
      
     public LDAPConnection ConexionManager(String strManager, String strPassword) {
          this.login="uid="+strManager+",ou=system"; 
         //login = "cn=" + strManager + ",o=utpl,c=ec";
          System.out.println("" + login);          
          System.out.println("puerto: " + ldapPort);          
          System.out.println("Vesion: " + ldapVersion);
          System.out.println("HOST: " + ldapHost);
          try {
               lc = new LDAPConnection();
               lc.connect(ldapHost, ldapPort);
               System.out.println("====Conectado al Servidor LDAP====");
               lc.bind(ldapVersion, login, strPassword.getBytes("UTF8"));
               System.out.println("Autenticado en el servidor....");
          } catch (UnsupportedEncodingException ex) {
               Logger.getLogger(ConexionLdap.class.getName()).log(Level.SEVERE,null, ex);
          } catch (LDAPException ex) {
               Logger.getLogger(ConexionLdap.class.getName()).log(Level.SEVERE,null, ex);
          }
          return lc;
     }
 
     /**
     * Este metodo permite realizar la conexion al servidor de LDAP
     * Para el usuario manager
     * @param strUser
     * @param strPassword
     */
 
     public LDAPConnection ConexionUser(String strUser, String strPassword) {
          login = "uid=" + strUser + ",ou=People,o=utpl,c=ec";
          System.out.println("" + login);
          ldapPort = LDAPConnection.DEFAULT_PORT;
          System.out.println("puerto: " + ldapPort);
          ldapVersion = LDAPConnection.LDAP_V3;
          System.out.println("Vesion: " + ldapVersion);
          System.out.println("HOST: " + ldapHost);
          try {
               lc = new LDAPConnection();
               lc.connect(ldapHost, ldapPort);
               System.out.println("====Conectado al Servidor LDAP====");
               lc.bind(ldapVersion, login, strPassword.getBytes("UTF8"));
          } catch (UnsupportedEncodingException ex) {
               Logger.getLogger(ConexionLdap.class.getName()).log(Level.SEVERE,null, ex);
          } catch (LDAPException ex) {
               Logger.getLogger(ConexionLdap.class.getName()).log(Level.SEVERE,null, ex);
          }
          return lc;
     }
 
     public void CerrarConLDAP(LDAPConnection lc) {
          try {
               lc.disconnect();
               System.out.println("Conexion Cerrada Correctamente...");
          } catch (LDAPException ex) {
               Logger.getLogger(ConexionLdap.class.getName()).log(Level.SEVERE,null, ex);
          }
 
     }

    public LDAPConnection getLc() {
        return lc;
    }
      
}
