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
public class TransferenciaServidor {
    
    private int codigoTransferenciaServidor;
    private int codigoServidor;
    private String nomeServidor;//240
    private String matriculaServidor;//
    private String nomeEntidadeAnterior;//120
    private String nomeEntidadeAtual;//120
    private String motivoTransferencia;//400
    private Date dataTransferencia;

    public int getCodigoServidor() {
        return codigoServidor;
    }

    public void setCodigoServidor(int codigoServidor) {
        this.codigoServidor = codigoServidor;
    }

    public int getCodigoTransferenciaServidor() {
        return codigoTransferenciaServidor;
    }

    public void setCodigoTransferenciaServidor(int codigoTransferenciaServidor) {
        this.codigoTransferenciaServidor = codigoTransferenciaServidor;
    }

    public Date getDataTransferencia() {
        return dataTransferencia;
    }

    public void setDataTransferencia(Date dataTransferencia) {
        this.dataTransferencia = dataTransferencia;
    }

    public String getMatriculaServidor() {
        return matriculaServidor;
    }

    public void setMatriculaServidor(String matriculaServidor) {
        this.matriculaServidor = matriculaServidor;
    }

    public String getMotivoTransferencia() {
        return motivoTransferencia;
    }

    public void setMotivoTransferencia(String motivoTransferencia) {
        this.motivoTransferencia = motivoTransferencia;
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

    public String getNomeServidor() {
        return nomeServidor;
    }

    public void setNomeServidor(String nomeServidor) {
        this.nomeServidor = nomeServidor;
    }
    
}
