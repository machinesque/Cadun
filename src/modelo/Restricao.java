/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author luizam
 */
public class Restricao {
    
    private int codigoRestricao;
    private String tipoRestricao;//120
    private String descricaoRestricao;//400

    public int getCodigoRestricao() {
        return codigoRestricao;
    }

    public void setCodigoRestricao(int codigoRestricao) {
        this.codigoRestricao = codigoRestricao;
    }

    public String getTipoRestricao() {
        return tipoRestricao;
    }

    public void setTipoRestricao(String tipoRestricao) {
        this.tipoRestricao = tipoRestricao;
    }

    public String getDescricaoRestricao() {
        return descricaoRestricao;
    }

    public void setDescricaoRestricao(String descricaoRestricao) {
        this.descricaoRestricao = descricaoRestricao;
    }
    
}
