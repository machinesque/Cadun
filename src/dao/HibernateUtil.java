/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author luizam
 */
public class HibernateUtil {
    
    private static final SessionFactory sessionfactory;
    
    static {
        
        try {
            sessionfactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Falha ao criar SessionFactory!" + ex);
            throw new ExceptionInInitializerError(ex);
        }
        
    }
    
    public static SessionFactory getSessionFactory(){
        return sessionfactory;
    }
    
}
