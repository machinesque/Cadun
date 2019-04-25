/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.DAO;
import modelo.Usuario;

/**
 *
 * @author luizam
 */
public class ControleUsuario {
    
    private DAO dao;
    private Usuario usuario;
            
    public void iniciaDao() {
        
        if (dao == null) {
            dao = new DAO();
        }
        
    }
    
    public void iniciaUsuario() {
        
        if (usuario == null) {
            usuario = new Usuario();
        }
        
    }
    
    public String getNomeUsuario(int codigoUsuario) {
        
        iniciaUsuario();
        
        usuario = (Usuario) dao.busca(usuario, codigoUsuario);
        
        return usuario.getNomeUsuario();
        
    }
    
}
