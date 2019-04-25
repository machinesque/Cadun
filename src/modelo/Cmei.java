/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 *
 * @author luiz
 */
public class Cmei {

    private int codigoCmei;
    private String nomeEntidade;
    private String endereco;
    private String numeroEndereco;
    private String municipio;
    private String bairro;
    private String telefone;
    private String coordenadorAtual;
    private String capacidadeAlunos;
    private int vagasDisponiveis;
    private int numeroLotacoes;
    private int servidoresConcursados;
    private int numeroEstagiarios;
    private int numeroSalasBercarios;
    private int numeroSalasMaternal;
    private int numeroSalasPre;

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCapacidadeAlunos() {
        return capacidadeAlunos;
    }

    public void setCapacidadeAlunos(String capacidadeAlunos) {
        this.capacidadeAlunos = capacidadeAlunos;
    }

    public int getCodigoCmei() {
        return codigoCmei;
    }

    public void setCodigoCmei(int codigoCmei) {
        this.codigoCmei = codigoCmei;
    }

    public String getCoordenadorAtual() {
        return coordenadorAtual;
    }

    public void setCoordenadorAtual(String coordenadorAtual) {
        this.coordenadorAtual = coordenadorAtual;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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

    public String getNumeroEndereco() {
        return numeroEndereco;
    }

    public void setNumeroEndereco(String numeroEndereco) {
        this.numeroEndereco = numeroEndereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getVagasDisponiveis() {
        return vagasDisponiveis;
    }

    public void setVagasDisponiveis(int vagasDisponiveis) {
        this.vagasDisponiveis = vagasDisponiveis;
    }

    public int getNumeroEstagiarios() {
        return numeroEstagiarios;
    }

    public void setNumeroEstagiarios(int numeroEstagiarios) {
        this.numeroEstagiarios = numeroEstagiarios;
    }

    public int getNumeroLotacoes() {
        return numeroLotacoes;
    }

    public void setNumeroLotacoes(int numeroLotacoes) {
        this.numeroLotacoes = numeroLotacoes;
    }

    public int getServidoresConcursados() {
        return servidoresConcursados;
    }

    public void setServidoresConcursados(int servidoresConcursados) {
        this.servidoresConcursados = servidoresConcursados;
    }

    public int getNumeroSalasBercarios() {
        return numeroSalasBercarios;
    }

    public void setNumeroSalasBercarios(int numeroSalasBercarios) {
        this.numeroSalasBercarios = numeroSalasBercarios;
    }

    public int getNumeroSalasMaternal() {
        return numeroSalasMaternal;
    }

    public void setNumeroSalasMaternal(int numeroSalasMaternal) {
        this.numeroSalasMaternal = numeroSalasMaternal;
    }

    public int getNumeroSalasPre() {
        return numeroSalasPre;
    }

    public void setNumeroSalasPre(int numeroSalasPre) {
        this.numeroSalasPre = numeroSalasPre;
    }

}
