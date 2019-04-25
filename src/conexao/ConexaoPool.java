/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexao;

import dao.HibernateUtil;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author luizam
 */
public class ConexaoPool {
    
    private static ConexaoPool instance;
    
    public ConexaoPool() {
        
    }
    
    public static ConexaoPool getInstance() {
        
        if (instance == null) {
            
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            
            long id = 1; //para apenas 1 instancia
            ConexaoPool con = (ConexaoPool) session.get(ConexaoPool.class, id);
            
            if (con != null) {
                instance = con;
            } else {
                instance = new ConexaoPool();
                session.saveOrUpdate(instance);
            }
            
        }
        
        return instance;
        
    }
    
}
