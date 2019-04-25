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
public class Aluno {

    private Integer codigoAluno;
    private String anoRegistroCertidaoNascimento;//5
    private String bairro;//120
    private String cartorioCertidaoNascimento;//120
    private String celular1;//13
    private String celular2;//13
    private String cpfPai;//15
    private String cpfMae;//15
    private String cpfResponsavel;//15
    private String digitoVerificadorCertidaoNascimento;//2
    private String endereco;//120
    private String enderecoComercialPai;//120
    private String enderecoComercialMae;//120
    private String enderecoComercialResponsavel;//120
    private String enderecoResponsavel;//120
    private String filiacaoMae;//240
    private String filiacaoPai;//240
    private String folhaCertidaoNascimento;//3
    private String livroCertidaoNascimento;//5
    private String matricula;//30
    private String modalidade;//15
    private String municipio;//120
    private String naturalidade;//120
    private String nomeAluno;//240
    private String nomeEntidade;//120
    private String nomeEntidadeOpcao2;//120
    private String nomeEntidadeOpcao3;//120
    private String nomeResponsavel;//240
    private String numeroEndereco;//15
    private String numeroEnderecoComercialPai;//15
    private String numeroEnderecoComercialMae;//15
    private String numeroEnderecoComercialResponsavel;//15
    private String numeroEnderecoResponsavel;//15
    private String numeroIdentificadorCopel;//15
    private String numeroMatriculaCertidaoNascimento;//50
    private String passaporteMae;
    private String passaportePai;
    private String profissaoMae;//60
    private String profissaoPai;//60
    private String profissaoResponsavel;//60
    private String rgPai;//15
    private String rgMae;//15
    private String rgResponsavel;//13
    private String sexo;//1
    private String telefone;//13
    private String telefoneComercialPai;//13
    private String telefoneComercialMae;//13
    private String telefoneComercialResponsavel;//13
    private String telefoneResponsavel;//13
    private String termoCertidaoNascimento;//7
    private String tipoCadastro;//15
    private String uf;//2
    private Date dataCadastro;
    private Date dataDesistencia;
    private Date dataEgresso;
    private Date dataNascimento;
    private Date dataNascimentoPai;
    private Date dataNascimentoMae;
    private Date dataNascimentoResponsavel;
    private Date dataSolicitacaoTransferencia;
    private boolean bolsaFamilia;
    private boolean ordemJudicial;

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public boolean isBolsaFamilia() {
        return bolsaFamilia;
    }

    public void setBolsaFamilia(boolean bolsaFamilia) {
        this.bolsaFamilia = bolsaFamilia;
    }

    public String getTermoCertidaoNascimento() {
        return termoCertidaoNascimento;
    }

    public void setTermoCertidaoNascimento(String termoCertidaoNascimento) {
        this.termoCertidaoNascimento = termoCertidaoNascimento;
    }

    public String getCelular1() {
        return celular1;
    }

    public void setCelular1(String celular1) {
        this.celular1 = celular1;
    }

    public String getCelular2() {
        return celular2;
    }

    public void setCelular2(String celular2) {
        this.celular2 = celular2;
    }

    public Integer getCodigoAluno() {
        return codigoAluno;
    }

    public void setCodigoAluno(Integer codigoAluno) {
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

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Date getDataNascimentoMae() {
        return dataNascimentoMae;
    }

    public void setDataNascimentoMae(Date dataNascimentoMae) {
        this.dataNascimentoMae = dataNascimentoMae;
    }

    public Date getDataNascimentoPai() {
        return dataNascimentoPai;
    }

    public void setDataNascimentoPai(Date dataNascimentoPai) {
        this.dataNascimentoPai = dataNascimentoPai;
    }

    public Date getDataSolicitacaoTransferencia() {
        return dataSolicitacaoTransferencia;
    }

    public void setDataSolicitacaoTransferencia(Date dataSolicitacaoTransferencia) {
        this.dataSolicitacaoTransferencia = dataSolicitacaoTransferencia;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEnderecoComercialMae() {
        return enderecoComercialMae;
    }

    public void setEnderecoComercialMae(String enderecoComercialMae) {
        this.enderecoComercialMae = enderecoComercialMae;
    }

    public String getEnderecoComercialPai() {
        return enderecoComercialPai;
    }

    public void setEnderecoComercialPai(String enderecoComercialPai) {
        this.enderecoComercialPai = enderecoComercialPai;
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

    public String getFolhaCertidaoNascimento() {
        return folhaCertidaoNascimento;
    }

    public void setFolhaCertidaoNascimento(String folhaCertidaoNascimento) {
        this.folhaCertidaoNascimento = folhaCertidaoNascimento;
    }

    public String getLivroCertidaoNascimento() {
        return livroCertidaoNascimento;
    }

    public void setLivroCertidaoNascimento(String livroCertidaoNascimento) {
        this.livroCertidaoNascimento = livroCertidaoNascimento;
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

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getNomeEntidade() {
        return nomeEntidade;
    }

    public void setNomeEntidade(String nomeEntidade) {
        this.nomeEntidade = nomeEntidade;
    }

    public String getNomeEntidadeOpcao2() {
        return nomeEntidadeOpcao2;
    }

    public void setNomeEntidadeOpcao2(String nomeEntidadeOpcao2) {
        this.nomeEntidadeOpcao2 = nomeEntidadeOpcao2;
    }

    public String getNomeEntidadeOpcao3() {
        return nomeEntidadeOpcao3;
    }

    public void setNomeEntidadeOpcao3(String nomeEntidadeOpcao3) {
        this.nomeEntidadeOpcao3 = nomeEntidadeOpcao3;
    }

    public String getAnoRegistroCertidaoNascimento() {
        return anoRegistroCertidaoNascimento;
    }

    public void setAnoRegistroCertidaoNascimento(String anoRegistroCertidaoNascimento) {
        this.anoRegistroCertidaoNascimento = anoRegistroCertidaoNascimento;
    }

    public String getDigitoVerificadorCertidaoNascimento() {
        return digitoVerificadorCertidaoNascimento;
    }

    public void setDigitoVerificadorCertidaoNascimento(String digitoVerificadorCertidaoNascimento) {
        this.digitoVerificadorCertidaoNascimento = digitoVerificadorCertidaoNascimento;
    }

    public String getNumeroEndereco() {
        return numeroEndereco;
    }

    public void setNumeroEndereco(String numeroEndereco) {
        this.numeroEndereco = numeroEndereco;
    }

    public String getNumeroEnderecoComercialMae() {
        return numeroEnderecoComercialMae;
    }

    public void setNumeroEnderecoComercialMae(String numeroEnderecoComercialMae) {
        this.numeroEnderecoComercialMae = numeroEnderecoComercialMae;
    }

    public String getNumeroEnderecoComercialPai() {
        return numeroEnderecoComercialPai;
    }

    public void setNumeroEnderecoComercialPai(String numeroEnderecoComercialPai) {
        this.numeroEnderecoComercialPai = numeroEnderecoComercialPai;
    }

    public String getProfissaoMae() {
        return profissaoMae;
    }

    public void setProfissaoMae(String profissaoMae) {
        this.profissaoMae = profissaoMae;
    }

    public String getProfissaoPai() {
        return profissaoPai;
    }

    public void setProfissaoPai(String profissaoPai) {
        this.profissaoPai = profissaoPai;
    }

    public String getRgMae() {
        return rgMae;
    }

    public void setRgMae(String rgMae) {
        this.rgMae = rgMae;
    }

    public String getRgPai() {
        return rgPai;
    }

    public void setRgPai(String rgPai) {
        this.rgPai = rgPai;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefoneComercialMae() {
        return telefoneComercialMae;
    }

    public void setTelefoneComercialMae(String telefoneComercialMae) {
        this.telefoneComercialMae = telefoneComercialMae;
    }

    public String getTelefoneComercialPai() {
        return telefoneComercialPai;
    }

    public void setTelefoneComercialPai(String telefoneComercialPai) {
        this.telefoneComercialPai = telefoneComercialPai;
    }

    public String getTelefoneResponsavel() {
        return telefoneResponsavel;
    }

    public void setTelefoneResponsavel(String telefoneResponsavel) {
        this.telefoneResponsavel = telefoneResponsavel;
    }

    public String getTipoCadastro() {
        return tipoCadastro;
    }

    public void setTipoCadastro(String tipoCadastro) {
        this.tipoCadastro = tipoCadastro;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Date getDataNascimentoResponsavel() {
        return dataNascimentoResponsavel;
    }

    public void setDataNascimentoResponsavel(Date dataNascimentoResponsavel) {
        this.dataNascimentoResponsavel = dataNascimentoResponsavel;
    }

    public String getEnderecoComercialResponsavel() {
        return enderecoComercialResponsavel;
    }

    public void setEnderecoComercialResponsavel(String enderecoComercialResponsavel) {
        this.enderecoComercialResponsavel = enderecoComercialResponsavel;
    }

    public String getEnderecoResponsavel() {
        return enderecoResponsavel;
    }

    public void setEnderecoResponsavel(String enderecoResponsavel) {
        this.enderecoResponsavel = enderecoResponsavel;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getPassaportePai() {
        return passaportePai;
    }

    public void setPassaportePai(String passaportePai) {
        this.passaportePai = passaportePai;
    }

    public String getPassaporteMae() {
        return passaporteMae;
    }

    public void setPassaporteMae(String passaporteMae) {
        this.passaporteMae = passaporteMae;
    }

    public String getProfissaoResponsavel() {
        return profissaoResponsavel;
    }

    public void setProfissaoResponsavel(String profissaoResponsavel) {
        this.profissaoResponsavel = profissaoResponsavel;
    }

    public String getRgResponsavel() {
        return rgResponsavel;
    }

    public void setRgResponsavel(String rgResponsavel) {
        this.rgResponsavel = rgResponsavel;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefoneComercialResponsavel() {
        return telefoneComercialResponsavel;
    }

    public void setTelefoneComercialResponsavel(String telefoneComercialResponsavel) {
        this.telefoneComercialResponsavel = telefoneComercialResponsavel;
    }

    public String getNumeroEnderecoComercialResponsavel() {
        return numeroEnderecoComercialResponsavel;
    }

    public void setNumeroEnderecoComercialResponsavel(String numeroEnderecoComercialResponsavel) {
        this.numeroEnderecoComercialResponsavel = numeroEnderecoComercialResponsavel;
    }

    public String getNumeroEnderecoResponsavel() {
        return numeroEnderecoResponsavel;
    }

    public void setNumeroEnderecoResponsavel(String numeroEnderecoResponsavel) {
        this.numeroEnderecoResponsavel = numeroEnderecoResponsavel;
    }

    public String getCpfResponsavel() {
        return cpfResponsavel;
    }

    public void setCpfResponsavel(String cpfResponsavel) {
        this.cpfResponsavel = cpfResponsavel;
    }

    public String getNumeroIdentificadorCopel() {
        return numeroIdentificadorCopel;
    }

    public void setNumeroIdentificadorCopel(String numeroIdentificadorCopel) {
        this.numeroIdentificadorCopel = numeroIdentificadorCopel;
    }

    public Date getDataDesistencia() {
        return dataDesistencia;
    }

    public void setDataDesistencia(Date dataDesistencia) {
        this.dataDesistencia = dataDesistencia;
    }

    public Date getDataEgresso() {
        return dataEgresso;
    }

    public void setDataEgresso(Date dataEgresso) {
        this.dataEgresso = dataEgresso;
    }

    public boolean isOrdemJudicial() {
        return ordemJudicial;
    }

    public void setOrdemJudicial(boolean ordemJudicial) {
        this.ordemJudicial = ordemJudicial;
    }

    public String getNumeroMatriculaCertidaoNascimento() {
        return numeroMatriculaCertidaoNascimento;
    }

    public void setNumeroMatriculaCertidaoNascimento(String numeroMatriculaCertidaoNascimento) {
        this.numeroMatriculaCertidaoNascimento = numeroMatriculaCertidaoNascimento;
    }

    public String getCartorioCertidaoNascimento() {
        return cartorioCertidaoNascimento;
    }

    public void setCartorioCertidaoNascimento(String cartorioCertidaoNascimento) {
        this.cartorioCertidaoNascimento = cartorioCertidaoNascimento;
    }
    
}
