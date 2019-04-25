/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 *
 * @author luiz
 */
public class Usuario {

    private int codigoUsuario;
    private String nomeUsuario;
    private String senha;
    private String contraSenha;
    private String temaEscolhido;
    private String tipoCadastro;
    private int matricula;

    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getContraSenha() {
        return contraSenha;
    }

    public void setContraSenha(String contraSenha) {
        this.contraSenha = contraSenha;
    }

    public String getTemaEscolhido() {
        return temaEscolhido;
    }

    public void setTemaEscolhido(String temaEscolhido) {
        this.temaEscolhido = temaEscolhido;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getTipoCadastro() {
        return tipoCadastro;
    }

    public void setTipoCadastro(String tipoCadastro) {
        this.tipoCadastro = tipoCadastro;
    }

}
