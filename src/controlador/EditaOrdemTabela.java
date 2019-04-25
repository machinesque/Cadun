/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

/**
 *
 * @author luizam
 */
public class EditaOrdemTabela {
 
    public String editaOrdem(int ordem) {
        
        String ordemEditada = "";
        
        if (ordem < 10) {
            ordemEditada  = "0000" + ordem;
        } else if (ordem < 100) {
            ordemEditada = "000" + ordem;
        } else if (ordem < 1000) {
            ordemEditada = "00" + ordem;
        } else if (ordem < 10000) {
            ordemEditada = "0" + ordem;
        } else {
            ordemEditada = "" + ordem;
        }
        
        return ordemEditada;
        
    }
    
}
