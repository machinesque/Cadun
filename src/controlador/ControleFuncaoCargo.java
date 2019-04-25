/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.DAO;
import modelo.FuncaoCargo;

/**
 *
 * @author luizam
 */
public class ControleFuncaoCargo {
    
    private DAO dao;
    private FuncaoCargo funcaoCargo;
            
    public void iniciaDao() {
        
        if (dao == null) {
            dao = new DAO();
        }
        
    }
    
    public void iniciaFuncaoCargo() {
        
        if (funcaoCargo == null) {
            funcaoCargo = new FuncaoCargo();
        }
        
    }
    
    public String getNomeFuncaoCargo(int codigoFuncaoCargo) {
        
        iniciaFuncaoCargo();
        funcaoCargo = (FuncaoCargo) dao.busca(funcaoCargo, codigoFuncaoCargo);
        
        return funcaoCargo.getNomeFuncaoCargo();
        
    }
    
    public int getCodigoFuncaoCargo(String nomeFuncaoCargo) {
        
        iniciaFuncaoCargo();
        funcaoCargo = (FuncaoCargo) dao.buscaFuncaoCargo(nomeFuncaoCargo, 0);
        
        return funcaoCargo.getCodigoFuncaoCargo();
        
    }
    
}
