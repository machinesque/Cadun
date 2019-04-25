/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

/**
 *
 * @author luizam
 */
public class ControleCaracteresEspeciais {
    
    public String getCaracterEspecial(String nome) {
        
        if (nome.contains("&RSQUO")) {
            nome = nome.replace("&RSQUO", "'");
        }
        
        return nome;
        
    }
    
    public String getCodigoCaracterEpecial(String nome) {
        
        if (nome.contains("'")) {
            nome = nome.replace("'", "&RSQUO");
        }
        
        return nome;
        
    }
    
}
