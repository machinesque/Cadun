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
public class VersaoUsuario {
    
    private int codigoVersaoUsuario;
    private int codigoUsuario_Usuario;
    private String versaoAtual;//10
    private Date dataAtualizacao;

    public int getCodigoUsuario_Usuario() {
        return codigoUsuario_Usuario;
    }

    public void setCodigoUsuario_Usuario(int codigoUsuario_Usuario) {
        this.codigoUsuario_Usuario = codigoUsuario_Usuario;
    }

    public int getCodigoVersaoUsuario() {
        return codigoVersaoUsuario;
    }

    public void setCodigoVersaoUsuario(int codigoVersaoUsuario) {
        this.codigoVersaoUsuario = codigoVersaoUsuario;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public String getVersaoAtual() {
        return versaoAtual;
    }

    public void setVersaoAtual(String versaoAtual) {
        this.versaoAtual = versaoAtual;
    }
    
}
