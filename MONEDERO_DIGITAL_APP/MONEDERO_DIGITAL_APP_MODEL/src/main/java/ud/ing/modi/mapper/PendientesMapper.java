package ud.ing.modi.mapper;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import ud.ing.modi.entidades.PendienteRegis;


public class PendientesMapper {
    
    private static final SessionFactory sessionFactory;
    private Session sesion;
    private Transaction tx;
    
    static {
        try {
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (HibernateException he) {
            System.err.println("Ocurrió un error en la inicialización de la SessionFactory: " + he);
            throw new ExceptionInInitializerError(he);
        }
    }

    private void iniciaOperacion() throws HibernateException {
        sesion = this.sessionFactory.openSession();
        tx = sesion.beginTransaction();
    }
    
    public void guardarPendiente(PendienteRegis pendiente) throws Exception {
        try {
            iniciaOperacion();
            sesion.save(pendiente);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            sesion.close();
        }
    }
    
    public boolean buscarSolicitud(String codSolic) throws HibernateException {
        int codSol = Integer.parseInt(codSolic); 
        PendienteRegis pendiente = null;
        try {
            iniciaOperacion();
            pendiente = (PendienteRegis) sesion.get(PendienteRegis.class, codSol);
            return pendiente!=null;
        } finally {
            sesion.close();
        }
    }
    
}

