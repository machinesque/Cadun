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
public class TransferenciaAluno {
    
    private int codigoTransferenciaAluno;
    private int codigoAluno;
    private String cpfMae;//15
    private String cpfPai;//15
    private String filiacaoMae;//240
    private String filiacaoPai;//240
    private String nomeAluno; //240
    private String matriculaAluno;//30
    private String nomeEntidadeAnterior;//120
    private String nomeEntidadeAtual;//120
    private String motivoTransferencia;//400
    private Date dataTransferencia;

    public int getCodigoTransferenciaAluno() {
        return codigoTransferenciaAluno;
    }

    public void setCodigoTransferenciaAluno(int codigoTransferenciaAluno) {
        this.codigoTransferenciaAluno = codigoTransferenciaAluno;
    }

    public int getCodigoAluno() {
        return codigoAluno;
    }

    public void setCodigoAluno(int codigoAluno) {
        this.codigoAluno = codigoAluno;
    }

    public String getCpfMae() {
        return cpfMae;
    }

    public void setCpfMae(String cpfMae) {
        this.cpfMae = cpfMae;
    }

    public String getCpfPai() {
        return cpfPai;
    }

    public void setCpfPai(String cpfPai) {
        this.cpfPai = cpfPai;
    }

    public String getFiliacaoMae() {
        return filiacaoMae;
    }

    public void setFiliacaoMae(String filiacaoMae) {
        this.filiacaoMae = filiacaoMae;
    }

    public String getFiliacaoPai() {
        return filiacaoPai;
    }

    public void setFiliacaoPai(String filiacaoPai) {
        this.filiacaoPai = filiacaoPai;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getMatriculaAluno() {
        return matriculaAluno;
    }

    public void setMatriculaAluno(String matriculaAluno) {
        this.matriculaAluno = matriculaAluno;
    }

    public String getNomeEntidadeAnterior() {
        return nomeEntidadeAnterior;
    }

    public void setNomeEntidadeAnterior(String nomeEntidadeAnterior) {
        this.nomeEntidadeAnterior = nomeEntidadeAnterior;
    }

    public String getNomeEntidadeAtual() {
        return nomeEntidadeAtual;
    }

    public void setNomeEntidadeAtual(String nomeEntidadeAtual) {
        this.nomeEntidadeAtual = nomeEntidadeAtual;
    }

    public String getMotivoTransferencia() {
        return motivoTransferencia;
    }

    public void setMotivoTransferencia(String motivoTransferencia) {
        this.motivoTransferencia = motivoTransferencia;
    }

    public Date getDataTransferencia() {
        return dataTransferencia;
    }

    public void setDataTransferencia(Date dataTransferencia) {
        this.dataTransferencia = dataTransferencia;
    }

}
