/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.DAO;
import modelo.Licenca;

/**
 *
 * @author luizam
 */
public class ControleLicenca {
    
    private DAO dao;
    private Licenca licenca;
            
    public void iniciaDao() {
        
        if (dao == null) {
            dao = new DAO();
        }
        
    }
    
    public void iniciaLicenca() {
        
        if (licenca == null) {
            licenca = new Licenca();
        }
        
    }
    
    public String getTipoLicenca(int codigoLicenca) {
        
        iniciaDao();
        iniciaLicenca();
        
        licenca = (Licenca) dao.busca(licenca, codigoLicenca);
        
        return licenca.getTipoLicenca();
        
    }
    
    public int getCodigoLicenca(String tipoLicenca) {
        
        iniciaDao();
        iniciaLicenca();
        
        licenca = (Licenca) dao.buscaLicenca(tipoLicenca, 0);
        
        return licenca.getCodigoLicenca();
        
    }
    
}
