/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Date;

/**
 *
 * @author luizam
 */
public class VersaoAtual {
    
    private int codigoVersaoAtual;
    private String numeroVersao;//10
    private Date dataVersao;

    public int getCodigoVersaoAtual() {
        return codigoVersaoAtual;
    }

    public void setCodigoVersaoAtual(int codigoVersaoAtual) {
        this.codigoVersaoAtual = codigoVersaoAtual;
    }

    public String getNumeroVersao() {
        return numeroVersao;
    }

    public void setNumeroVersao(String numeroVersao) {
        this.numeroVersao = numeroVersao;
    }

    public Date getDataVersao() {
        return dataVersao;
    }

    public void setDataVersao(Date dataVersao) {
        this.dataVersao = dataVersao;
    }
    
}
