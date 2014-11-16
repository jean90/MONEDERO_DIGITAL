package ud.ing.modi;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.hibernate.*;
import org.hibernate.cfg.AnnotationConfiguration;
import ud.ing.modi.entidades.Persona;
import ud.ing.modi.mapper.PersonMapper;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
         /*EntityManagerFactory emf = 
            Persistence.createEntityManagerFactory("DB_MONEDERO"); 
        EntityManager em = emf.createEntityManager(); 
        EntityTransaction tx = em.getTransaction(); */

        Persona a =null;
        PersonMapper aMapper=new PersonMapper();
        a=aMapper.obtenerUsuario("00001");
        System.out.println(a.getIdPersona());

        
    }
   
}
