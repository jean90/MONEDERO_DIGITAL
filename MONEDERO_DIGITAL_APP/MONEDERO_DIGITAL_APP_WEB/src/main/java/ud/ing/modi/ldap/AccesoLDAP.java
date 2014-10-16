package ud.ing.modi.ldap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPModification;
import com.novell.ldap.LDAPSearchResults;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import ud.ing.modi.entidades.Persona;

/**
 *
 * @author Administrador
 */
public class AccesoLDAP {

    private LDAPConnection conLdap;
    private final String baseBusqueda="ou=Users,dc=monederodigital,dc=com,dc=co";

    public AccesoLDAP() {
        conLdap=new LDAPConnection();
    }
    
    public void InsertarUsuario(Persona persona, String uid, String password){
        try{
            LDAPEntry usuarioLdap = cargarDatos(persona,uid,password);
            ConexionLdap conn=new ConexionLdap();
            conn.ConexionManager("admin", "123456");
            conn.getLc().add(usuarioLdap);
            conn.CerrarConLDAP(conn.getLc());
        }catch(LDAPException e){
            System.out.println("fallo al insertar");
            e.printStackTrace();
        }
    }
    private LDAPEntry cargarDatos(Persona persona, String uid, String password){
        LDAPAttributeSet setAtr = new LDAPAttributeSet();
        setAtr.add(new LDAPAttribute("objectclass","inetOrgPerson"));
        setAtr.add(new LDAPAttribute("objectclass","organizationalPerson"));
        setAtr.add(new LDAPAttribute("objectclass","person"));
        setAtr.add(new LDAPAttribute("objectclass","top"));
        setAtr.add(new LDAPAttribute("cn",persona.getNombre()));
        setAtr.add(new LDAPAttribute("sn",persona.getApellido()));
        setAtr.add(new LDAPAttribute("givenName",persona.getNombre()));
        setAtr.add(new LDAPAttribute("userPassword",password));
        String dn="uid="+uid+",ou=Users,dc=monederodigital,dc=com,dc=co";
        LDAPEntry entrada=new LDAPEntry(dn, setAtr);
        return entrada;
    }
    
    
    public void buscarUsuario(String usuario){
        LDAPSearchResults resultado;        
        int salida = LDAPConnection.SCOPE_SUB;
        String filtro="(uid="+usuario+")";
        try{
           ConexionLdap conn=new ConexionLdap();
           conLdap=conn.ConexionManager("admin", "123456");
           resultado = conLdap.search(this.baseBusqueda, salida, filtro, null, false);
           while(resultado.hasMore()){
               LDAPEntry entrada=null;
               try{
                   entrada=resultado.next();
               }catch(LDAPException e){
                   System.out.println("Error: "+e.toString());
                   continue;
               }
               LDAPAttributeSet attributeSet = entrada.getAttributeSet();
               Iterator atributos = attributeSet.iterator();
               while(atributos.hasNext()){
                   LDAPAttribute atributo=(LDAPAttribute)atributos.next();
                   String nombreAtributo = atributo.getName();
                   Enumeration valores = atributo.getStringValues();
                   if(valores!=null){
                       while(valores.hasMoreElements()){
                           String valor=(String)valores.nextElement();
                           System.out.println(nombreAtributo+": "+valor);
                       }
                   }
               }
           }
           conn.CerrarConLDAP(conn.getLc());
        }catch(LDAPException ex){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null);
        }
    }
    
    public boolean usuarioExiste(String usuario){
        boolean existeUsuario=false;
        LDAPSearchResults resultado;
        String basebusqueda  = "ou=Users,dc=monederodigital,dc=com,dc=co";
        int salida = LDAPConnection.SCOPE_SUB;
        String filtro="(uid="+usuario+")";
        try{
           ConexionLdap conn=new ConexionLdap();
           conLdap=conn.ConexionManager("admin", "123456");
           resultado = conLdap.search(basebusqueda, salida, filtro, null, false);
           if(resultado.getCount()>0){
               existeUsuario = true;
           }           
           conn.CerrarConLDAP(conn.getLc());
        }catch(LDAPException ex){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null);
        }
        return existeUsuario;                
    }
    public boolean validarPassword(String usuario, String password){
        boolean passOK=false;
        String dn="uid="+usuario+","+baseBusqueda;
        try {
            
            conLdap=new LDAPConnection();
            conLdap.bind(LDAPConnection.LDAP_V3, dn, password.getBytes("UTF8"));
            LDAPAttribute atributo=new LDAPAttribute("userPassword",password);
            passOK=conLdap.compare(dn, atributo);
            
        } catch (LDAPException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null);
        } catch (UnsupportedEncodingException e){
           Logger.getLogger(getClass().getName()).log(Level.SEVERE, null); 
        }
        return passOK;
    }
}
