/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.DAO;
import modelo.Restricao;

/**
 *
 * @author luizam
 */
public class ControleRestricao {
    
    private DAO dao;
    private Restricao restricao;
            
    public void iniciaDao() {
        
        if (dao == null) {
            dao = new DAO();
        }
        
    }
    
    public void iniciaRestricao() {
        
        if (restricao == null) {
            restricao = new Restricao();
        }
        
    }
    
    public int getCodigoRestricao(String tipoRestricao) {
        
        iniciaDao();
        iniciaRestricao();
        
        restricao = (Restricao) dao.buscaRestricao(tipoRestricao, 0);
        
        return restricao.getCodigoRestricao();
        
    }
    
    public String getTipoRestricao(int codigoRestricao) {
        
        iniciaDao();
        iniciaRestricao();
        
        restricao = (Restricao) dao.busca(restricao, codigoRestricao);
        
        return restricao.getTipoRestricao();
        
    }
}
