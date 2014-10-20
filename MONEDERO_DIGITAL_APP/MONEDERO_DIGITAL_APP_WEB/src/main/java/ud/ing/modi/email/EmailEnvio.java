/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.email;

import java.io.Serializable;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.JOptionPane;

@ManagedBean(name="emailEnvio")
@RequestScoped
/**
 *
 * @author Lufe
 */
public class EmailEnvio implements Serializable{

    static String destinatario="monedero.digital@gmail.com";
    String host="smtp.gmail.com";//"190.146.42.16";
    String port="587";//"25";
    Properties props;
    private String correo;
    
    public EmailEnvio() {
        props=new Properties();
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", this.host);
        props.put("mail.smtp.port", this.port);
    }
    
    public void enviarMail(){
        System.out.println("Método! yuhuuuu "+this.correo);
        Session sesion=Session.getInstance(props, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
           return new PasswordAuthentication("monedero.digital@gmail.com","lufejean"); 
           }
        });
        
        try{
            Message mes=new MimeMessage(sesion);
            mes.setFrom(new InternetAddress(destinatario));
                mes.setRecipients(Message.RecipientType.TO, InternetAddress.parse(this.correo));
                mes.setSubject("Asunto 1");
                mes.setText("Cuerpo del mensaje");
                
                Transport.send(mes);
                JOptionPane.showMessageDialog(null, "Su mensaje ha sido enviado! :)");
        }catch(MessagingException e){
            System.out.println("Error "+e);
        }
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    
}
