/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 *
 * @author luizam
 */
public class Permissoes {

    private int codigoPermissoes;
    private String nomeUsuario;
    private boolean cadastrarAluno;
    private boolean cadastrarCmei;
    private boolean cadastrarFuncaoCargo;
    private boolean cadastrarLicenca;
    private boolean cadastrarRestricao;
    private boolean cadastrarServidor;
    private boolean cadastrarUsuario;
    private boolean imprimirCmei;
    private boolean imprimirAluno;


    public boolean isCadastrarCmei() {
        return cadastrarCmei;
    }

    public void setCadastrarCmei(boolean cadastrarCmei) {
        this.cadastrarCmei = cadastrarCmei;
    }

    public boolean isCadastrarFuncaoCargo() {
        return cadastrarFuncaoCargo;
    }

    public void setCadastrarFuncaoCargo(boolean cadastrarFuncaoCargo) {
        this.cadastrarFuncaoCargo = cadastrarFuncaoCargo;
    }

    public int getCodigoPermissoes() {
        return codigoPermissoes;
    }

    public void setCodigoPermissoes(int codigoPermissoes) {
        this.codigoPermissoes = codigoPermissoes;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public boolean isCadastrarAluno() {
        return cadastrarAluno;
    }

    public void setCadastrarAluno(boolean cadastrarAluno) {
        this.cadastrarAluno = cadastrarAluno;
    }

    public boolean isCadastrarUsuario() {
        return cadastrarUsuario;
    }

    public void setCadastrarUsuario(boolean cadastrarUsuario) {
        this.cadastrarUsuario = cadastrarUsuario;
    }

    public boolean isImprimirAluno() {
        return imprimirAluno;
    }

    public void setImprimirAluno(boolean imprimirAluno) {
        this.imprimirAluno = imprimirAluno;
    }

    public boolean isImprimirCmei() {
        return imprimirCmei;
    }

    public void setImprimirCmei(boolean imprimirCmei) {
        this.imprimirCmei = imprimirCmei;
    }

    public boolean isCadastrarServidor() {
        return cadastrarServidor;
    }

    public void setCadastrarServidor(boolean cadastrarServidor) {
        this.cadastrarServidor = cadastrarServidor;
    }

    public boolean isCadastrarLicenca() {
        return cadastrarLicenca;
    }

    public void setCadastrarLicenca(boolean cadastrarLicenca) {
        this.cadastrarLicenca = cadastrarLicenca;
    }

    public boolean isCadastrarRestricao() {
        return cadastrarRestricao;
    }

    public void setCadastrarRestricao(boolean cadastrarRestricao) {
        this.cadastrarRestricao = cadastrarRestricao;
    }

}
