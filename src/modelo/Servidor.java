/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 *
 * @author luizam
 */
public class Servidor {

    private int codigoServidor;
    private int restricao_codigoRestricao;
    private int licenca_codigoLicenca;
    private String nomeServidor;//240
    private String matricula;//10
    private String nomeEntidade;//120
    private String endereco;//120
    private String tipoEntidade;//30
    private String numeroEndereco;//10
    private String uf;//2
    private String municipio;//120
    private String bairro;//120
    private String funcaoCargo;//120
    private String modalidade;//60
    private String telefone;//13
    private String celular;//13
    private String statusContrato;//60

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public int getCodigoServidor() {
        return codigoServidor;
    }

    public int getRestricao_codigoRestricao() {
        return restricao_codigoRestricao;
    }

    public void setRestricao_codigoRestricao(int restricao_codigoRestricao) {
        this.restricao_codigoRestricao = restricao_codigoRestricao;
    }
    
    public void setCodigoServidor(int codigoServidor) {
        this.codigoServidor = codigoServidor;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getFuncaoCargo() {
        return funcaoCargo;
    }

    public void setFuncaoCargo(String funcaoCargo) {
        this.funcaoCargo = funcaoCargo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getNomeEntidade() {
        return nomeEntidade;
    }

    public void setNomeEntidade(String nomeEntidade) {
        this.nomeEntidade = nomeEntidade;
    }

    public String getNomeServidor() {
        return nomeServidor;
    }

    public void setNomeServidor(String nomeServidor) {
        this.nomeServidor = nomeServidor;
    }

    public String getNumeroEndereco() {
        return numeroEndereco;
    }

    public void setNumeroEndereco(String numeroEndereco) {
        this.numeroEndereco = numeroEndereco;
    }

    public String getTipoEntidade() {
        return tipoEntidade;
    }

    public void setTipoEntidade(String tipoEntidade) {
        this.tipoEntidade = tipoEntidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getStatusContrato() {
        return statusContrato;
    }

    public void setStatusContrato(String statusContrato) {
        this.statusContrato = statusContrato;
    }

    public int getLicenca_codigoLicenca() {
        return licenca_codigoLicenca;
    }

    public void setLicenca_codigoLicenca(int licenca_codigoLicenca) {
        this.licenca_codigoLicenca = licenca_codigoLicenca;
    }

}
