/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import conexao.Conexao;
import conexao.ConexaoPool;
import excecoes.ExcCadastro;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author luizam
 */
public class DAO {

    private static SessionFactory factory;
    private HibernateUtil hibernateUtil = new HibernateUtil();
    private ConexaoPool conexao = new ConexaoPool();
    private Aluno aluno;
    private Cmei cmei;
    private Contrasenha contraSenha;
    private FuncaoCargo funcaoCargo;
    private Licenca licenca;
    private Permissoes permissoes;
    private Restricao restricao;
    private Servidor servidor;
    private TransferenciaServidor transferenciaServidor;
    private TransferenciaAluno transferenciaAluno;
    private Usuario usuario;
    private VersaoUsuario versaoUsuario;
    
    private Session session;

    public DAO() {
        
        if (factory == null) {
            factory = hibernateUtil.getSessionFactory();
        }
        
    }

//Metodos Dao========================================================================================================================================
    public void inserir(Object obj, int valorMensagem) {
        try {
            
            iniciaSessao();
            Transaction trx = session.beginTransaction();
            session.save(obj);
            session.flush();
            trx.commit();
            encerraSessao();
            if (valorMensagem == 7) {
                JOptionPane.showMessageDialog(null, "Salvo com Sucesso!", "OK", JOptionPane.INFORMATION_MESSAGE, null);
            }
            
        } catch (HibernateException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel Salvar!", "Erro", 0, null);
        }
    }

    public void atualizar(Object obj, int valorMensagem) {
        try {
            
            iniciaSessao();
            Transaction trx = session.beginTransaction();
            session.update(obj);
            session.flush();
            trx.commit();
            encerraSessao();
            if (valorMensagem == 7) {
                JOptionPane.showMessageDialog(null, "Atualizado com Sucesso!", "OK", JOptionPane.INFORMATION_MESSAGE, null);
            }
            
        } catch (HibernateException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel Atualizar!", "Erro", 0, null);
        }
    }

    public void excluir(Object obj, int valorMensagem) {
        try {
            
            iniciaSessao();
            Transaction trx = session.beginTransaction();
            session.delete(obj);
            session.flush();
            trx.commit();
            encerraSessao();
            if (valorMensagem == 7) {
                JOptionPane.showMessageDialog(null, "Excluido com Sucesso!", "OK", JOptionPane.INFORMATION_MESSAGE, null);
            }
            
        } catch (HibernateException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel Excluir!", "Erro", 0, null);
        }
    }
    
    public void excluirAlunoBkp(int codigoAluno, int valorMensagem) {
        
        try {
            
            PreparedStatement psmt = Conexao.getConexaoBackup().prepareStatement("delete from aluno where codigoaluno = ?");
            psmt.setInt(1, codigoAluno);

            //ResultSet rs = psmt.executeQuery();
            psmt.execute();
            //rs.close();
            psmt.close();
            
            System.out.println("Aluno de código: " + codigoAluno + " - Excluido com Sucesso do BKP!");
            
            if (valorMensagem == 7) {
                JOptionPane.showMessageDialog(null, "Excluido com Sucesso!", "OK", JOptionPane.INFORMATION_MESSAGE, null);
            }
            
        } catch (Exception e) {
            
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Não foi possivel Excluir o aluno de código nº " + codigoAluno + "!", "Erro", 0, null);
            
        }
        
    }
    
    public void excluirServidorBkp(int codigoServidor, int valorMensagem) {
        
        try {
            
            PreparedStatement psmt = Conexao.getConexaoBackup().prepareStatement("delete from servidor where codigoservidor = ?");
            psmt.setInt(1, codigoServidor);

            //ResultSet rs = psmt.executeQuery();
            psmt.execute();
            //rs.close();
            psmt.close();
            
            System.out.println("Servidor de código: " + codigoServidor + " - Excluido com Sucesso do BKP!");
            
            if (valorMensagem == 7) {
                JOptionPane.showMessageDialog(null, "Excluido com Sucesso!", "OK", JOptionPane.INFORMATION_MESSAGE, null);
            }
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Não foi possivel Excluir o Servidor de código nº " + codigoServidor + "!", "Erro", 0, null);
            
        }
        
    }
    
    public void excluirUsuarioBkp(int codigoUsuario, int valorMensagem) {
        
        try {
            
            PreparedStatement psmt = Conexao.getConexaoBackup().prepareStatement("delete from usuario where codigousuario = ?");
            psmt.setInt(1, codigoUsuario);

            //ResultSet rs = psmt.executeQuery();
            psmt.execute();
            //rs.close();
            psmt.close();
            
            System.out.println("Usuário de código: " + codigoUsuario + " - Excluido com Sucesso do BKP!");
            
            if (valorMensagem == 7) {
                JOptionPane.showMessageDialog(null, "Excluido com Sucesso!", "OK", JOptionPane.INFORMATION_MESSAGE, null);
            }
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Não foi possivel Excluir o Usuário de código nº " + codigoUsuario + "!", "Erro", 0, null);
            
        }
        
    }

    public Object busca(Object obj, int id) {
        iniciaSessao();
        Transaction trx = session.beginTransaction();
        Object obj2 = session.get(obj.getClass(), id);
        session.flush();
        trx.commit();
        encerraSessao();
        return obj2;
    }

    public Object busca(Object obj, String id) {
        iniciaSessao();
        Transaction trx = session.beginTransaction();
        Object obj2 = session.get(obj.getClass(), id);
        session.flush();
        trx.commit();
        encerraSessao();
        return obj2;
    }

    /*public Object busca(Object obj, Integer id) {
    try {
    Session session = factory.openSession();
    Transaction trx = session.beginTransaction();
    Object obj2 = session.get(obj.getClass(), id);
    session.flush();
    trx.commit();
    session.close();
    return obj2;
    } catch (HibernateException ex) {
    return null;
    }
    }*/
//Getters and Setters========================================================================================================================================
    public String getVersaoAtual() {

        String versao = "";

        try {

            PreparedStatement psmt = Conexao.getConexao().prepareStatement("select MAX(codigoversaoatual), numeroversao as linhas from versaoatual group by codigoversaoatual");

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                versao = rs.getString("linhas");

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return versao;

    }
    
    public String getVersaoUsuario(int codigoUsuario_Usuario) {
 
        String versao = "";
        PreparedStatement psmt = null;
        String selecao = "select MAX(codigoversaousuario), versaoatual as linhas from versaousuario";
        int contaSelecao = 0;

        try {

            if (codigoUsuario_Usuario != 0) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "codigoUsuario_Usuario = " + codigoUsuario_Usuario;
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "codigoUsuario_Usuario = " + codigoUsuario_Usuario;
                }
            }

            psmt = Conexao.getConexao().prepareStatement(selecao + " group by versaoatual");

            ResultSet rs = psmt.executeQuery();
            
            while (rs.next()) {

                versao = rs.getString("linhas");

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return versao;

    }
    
    public String getCodigoVersaoUsuario(int codigoUsuario_Usuario) {
 
        String versao = "";
        PreparedStatement psmt = null;
        String selecao = "select MAX(codigoversaousuario) as linhas from versaousuario";
        int contaSelecao = 0;

        try {

            if (codigoUsuario_Usuario != 0) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "codigoUsuario_Usuario like '%" + codigoUsuario_Usuario + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "codigoUsuario_Usuario like '%" + codigoUsuario_Usuario + "%'";
                }
            }

            psmt = Conexao.getConexao().prepareStatement(selecao + " group by codigoversaousuario");

            ResultSet rs = psmt.executeQuery();
            
            while (rs.next()) {

                versao = rs.getString("linhas");

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return versao;

    }
    
    public int getNumeroAlunos() {

        int linhas = 0;

        try {

            PreparedStatement psmt = Conexao.getConexao().prepareStatement("select count(*) as linhas from aluno where datadesistencia is null and dataegresso is null");
            //psmt.setString(1, ano);
            //psmt.setString(2, bairro);
            //psmt.setString(3, coordenadorAtual);

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                linhas = rs.getInt("linhas");

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return linhas;

    }

    public int getNumeroAlunosMatriculados(String nomeUnidade) {
        
        String versao = "";
        PreparedStatement psmt = null;
        String selecao = "select count(*) as linhas from aluno";
        int contaSelecao = 0;
        int linhas = 0;

        try {

            if (!nomeUnidade.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "nomeentidade like '%" + nomeUnidade + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "nomeentidade like '%" + nomeUnidade + "%'";
                }
            }
            
            psmt = Conexao.getConexao().prepareStatement(selecao + "and tipocadastro = 'MATRICULA'");

            ResultSet rs = psmt.executeQuery();
        
            while (rs.next()) {

                linhas = rs.getInt("linhas");

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return linhas;
        
    }
    
    public int getQuatitativoAlunos(String modalidade, String tipoCadastro, String nomeEntidade) {

        String versao = "";
        PreparedStatement psmt = null;
        String selecao = "select count(*) as linhas from aluno";
        int contaSelecao = 0;
        int linhas = 0;

        try {

            if (!nomeEntidade.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "nomeentidade like '%" + nomeEntidade + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "nomeentidade like '%" + nomeEntidade + "%'";
                }
            }
            
            if (!tipoCadastro.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "tipocadastro like '%" + tipoCadastro + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "tipocadastro like '%" + tipoCadastro + "%'";
                }
            }
            
            if (!modalidade.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "modalidade like '%" + modalidade + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "modalidade like '%" + modalidade + "%'";
                }
            }

            psmt = Conexao.getConexao().prepareStatement(selecao);

            ResultSet rs = psmt.executeQuery();
        
            while (rs.next()) {

                linhas = rs.getInt("linhas");

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return linhas;

    }

    public int getQuantitativoServidores(String modalidade, String nomeEntidade, String funcaoCargo, String statusContrato, int licenca_codigoLicenca, int restricao_codigoRestricao) {

        String versao = "";
        PreparedStatement psmt = null;
        String selecao = "select count(*) as linhas from servidor";
        int contaSelecao = 0;
        int linhas = 0;

        try {

            if (!nomeEntidade.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "nomeentidade like '%" + nomeEntidade + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "nomeentidade like '%" + nomeEntidade + "%'";
                }
            }
            
            if (!modalidade.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "modalidade like '%" + modalidade + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "modalidade like '%" + modalidade + "%'";
                }
            }
            
            if (!funcaoCargo.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "funcaocargo like '%" + funcaoCargo + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "funcaocargo like '%" + funcaoCargo + "%'";
                }
            }
            
            if (!statusContrato.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "statuscontrato like '%" + statusContrato + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "statuscontrato like '%" + statusContrato + "%'";
                }
            }
            
            if (licenca_codigoLicenca != 0) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "licenca_codigolicenca = " + licenca_codigoLicenca;
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "licenca_codigolicenca = " + licenca_codigoLicenca;
                }
            }

            if (restricao_codigoRestricao != 0) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "restricao_codigorestricao = " + restricao_codigoRestricao;
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "restricao_codigorestricao = " + restricao_codigoRestricao;
                }
            }
            
            psmt = Conexao.getConexao().prepareStatement(selecao);

            ResultSet rs = psmt.executeQuery();
        
            while (rs.next()) {

                linhas = rs.getInt("linhas");

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return linhas;

    }

//Buscadores========================================================================================================================================
    public Object buscaCmei(String nomeCmei, String bairro, String coordenadorAtual, String endereco) {

        PreparedStatement psmt = null;
        String versao = "";
        String selecao = "select * from cmei";
        int contaSelecao = 0;
        int linhas = 0;

        try {

            if (!nomeCmei.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "nomeentidade like '%" + nomeCmei + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "nomeentidade like '%" + nomeCmei + "%'";
                }
            }
            
            if (!bairro.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "bairro like '%" + bairro + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "bairro like '%" + bairro + "%'";
                }
            }
            
            if (!coordenadorAtual.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "coordenadoratual like '%" + coordenadorAtual + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "coordenadoratual like '%" + coordenadorAtual + "%'";
                }
            }
            
            if (!endereco.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "endereco like '%" + endereco + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "endereco like '%" + endereco + "%'";
                }
            }

            psmt = Conexao.getConexao().prepareStatement(selecao);

            ResultSet rs = psmt.executeQuery();
        
            while (rs.next()) {

                iniciaCmei();
                
                cmei.setNomeEntidade(rs.getString("nomeentidade"));
                cmei.setBairro(rs.getString("bairro"));
                cmei.setCapacidadeAlunos(rs.getString("capacidadealunos"));
                cmei.setVagasDisponiveis(rs.getInt("vagasdisponiveis"));
                cmei.setCodigoCmei(rs.getInt("codigocmei"));
                cmei.setCoordenadorAtual(rs.getString("coordenadoratual"));
                cmei.setEndereco(rs.getString("endereco"));
                cmei.setMunicipio(rs.getString("municipio"));
                cmei.setNumeroEndereco(rs.getString("numeroendereco"));
                cmei.setTelefone(rs.getString("telefone"));
                cmei.setServidoresConcursados(rs.getInt("servidoresconcursados"));
                cmei.setNumeroEstagiarios(rs.getInt("numeroestagiarios"));
                cmei.setNumeroLotacoes(rs.getInt("numerolotacoes"));
                cmei.setNumeroSalasBercarios(rs.getInt("numerosalasbercarios"));
                cmei.setNumeroSalasMaternal(rs.getInt("numerosalasmaternal"));
                cmei.setNumeroSalasPre(rs.getInt("numerosalaspre"));

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cmei;

    }

    /*public Object buscaEscola(String nomeEscola, String bairro, String diretorAtual, String endereco) {
    
    Escola escol = new Escola();
    
    try {
    
    PreparedStatement psmt = Conexao.getConexao().prepareStatement("select * from escola where nomeescola = ? and bairro = ? and diretoratual = ? and endereco = ?");
    psmt.setString(1, nomeEscola);
    psmt.setString(2, bairro);
    psmt.setString(3, diretorAtual);
    psmt.setString(4, endereco);
    
    ResultSet rs = psmt.executeQuery();
    
    while (rs.next()) {
    
    escol.setNomeEscola(rs.getString("nomeescola"));
    escol.setBairro(rs.getString("bairro"));
    escol.setCapacidadeAlunos(rs.getString("capacidadealunos"));
    escol.setCodigoEscola(rs.getInt("codigoescola"));
    escol.setDiretorAtual(rs.getString("diretoratual"));
    escol.setDiretorUltimo(rs.getString("diretorultimo"));
    escol.setEndereco(rs.getString("endereco"));
    escol.setMunicipio(rs.getString("municipio"));
    escol.setNumeroEndereco(rs.getString("numeroendereco"));
    escol.setTelefone(rs.getString("telefone"));
    
    }
    
    rs.close();
    psmt.close();
    
    } catch (ExcCadastro ex) {
    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return escol;
    
    }*/
    public Object buscaFuncaoCargo(String nomeFuncaoCargo, int codigoFuncaoCargo) {

        try {

            PreparedStatement psmt = Conexao.getConexao().prepareStatement("select * from funcaocargo where nomefuncaocargo = ? and codigofuncaocargo = ?");
            psmt.setString(1, nomeFuncaoCargo);
            psmt.setInt(2, codigoFuncaoCargo);

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaFuncaoCargo();
                
                funcaoCargo.setNomeFuncaoCargo(rs.getString("nomefuncaocargo"));
                funcaoCargo.setCodigoFuncaoCargo(rs.getInt("codigofuncaocargo"));

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return funcaoCargo;

    }

    public Object buscaAluno(String nomeAluno, String codigoAluno) {

        try {

            PreparedStatement psmt = Conexao.getConexao().prepareStatement("select * from aluno where nomealuno = ? and codigoaluno = ?");
            psmt.setString(1, nomeAluno);
            psmt.setInt(2, Integer.valueOf(codigoAluno));

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaAluno();
                
                aluno.setAnoRegistroCertidaoNascimento(rs.getString("anoregistrocertidaonascimento"));
                aluno.setBairro(rs.getString("bairro"));
                aluno.setCartorioCertidaoNascimento(rs.getString("cartoriocertidaonascimento"));
                aluno.setCelular1(rs.getString("celular1"));
                aluno.setCelular2(rs.getString("celular2"));
                aluno.setCodigoAluno(rs.getInt("codigoaluno"));
                aluno.setCpfMae(rs.getString("cpfmae"));
                aluno.setCpfPai(rs.getString("cpfpai"));
                aluno.setCpfResponsavel(rs.getString("cpfresponsavel"));
                aluno.setDataCadastro(rs.getDate("datacadastro"));
                aluno.setDataDesistencia(rs.getDate("datadesistencia"));
                aluno.setDataEgresso(rs.getDate("dataegresso"));
                aluno.setDataNascimento(rs.getDate("datanascimento"));
                aluno.setDataNascimentoMae(rs.getDate("datanascimentomae"));
                aluno.setDataNascimentoPai(rs.getDate("datanascimentopai"));
                aluno.setDataNascimentoResponsavel(rs.getDate("datanascimentoresponsavel"));
                aluno.setDataSolicitacaoTransferencia(rs.getDate("datasolicitacaotransferencia"));
                aluno.setDigitoVerificadorCertidaoNascimento(rs.getString("digitoverificadorcertidaonascimento"));
                aluno.setEndereco(rs.getString("endereco"));
                aluno.setEnderecoComercialMae(rs.getString("enderecocomercialmae"));
                aluno.setEnderecoComercialPai(rs.getString("enderecocomercialpai"));
                aluno.setEnderecoComercialResponsavel(rs.getString("enderecocomercialresponsavel"));
                aluno.setEnderecoResponsavel(rs.getString("enderecoresponsavel"));
                aluno.setFiliacaoMae(rs.getString("filiacaomae"));
                aluno.setFiliacaoPai(rs.getString("filiacaopai"));
                aluno.setFolhaCertidaoNascimento(rs.getString("folhacertidaonascimento"));
                aluno.setLivroCertidaoNascimento(rs.getString("livrocertidaonascimento"));
                aluno.setMatricula(rs.getString("matricula"));
                aluno.setModalidade(rs.getString("modalidade"));
                aluno.setMunicipio(rs.getString("municipio"));
                aluno.setNaturalidade(rs.getString("naturalidade"));
                aluno.setNomeAluno(rs.getString("nomealuno"));
                aluno.setNomeEntidade(rs.getString("nomeentidade"));
                aluno.setNomeEntidadeOpcao2(rs.getString("nomeentidadeopcao2"));
                aluno.setNomeEntidadeOpcao3(rs.getString("nomeentidadeopcao3"));
                aluno.setNomeResponsavel(rs.getString("nomeresponsavel"));
                aluno.setNumeroEndereco(rs.getString("numeroendereco"));
                aluno.setNumeroEnderecoComercialMae(rs.getString("numeroenderecocomercialmae"));
                aluno.setNumeroEnderecoComercialPai(rs.getString("numeroenderecocomercialpai"));
                aluno.setNumeroEnderecoResponsavel(rs.getString("numeroenderecoresponsavel"));
                aluno.setNumeroEnderecoComercialResponsavel(rs.getString("numeroenderecocomercialresponsavel"));
                aluno.setNumeroIdentificadorCopel(rs.getString("numeroidentificadorcopel"));
                aluno.setNumeroMatriculaCertidaoNascimento(rs.getString("numeromatriculacertidaonascimento"));
                aluno.setProfissaoMae(rs.getString("profissaomae"));
                aluno.setProfissaoPai(rs.getString("profissaopai"));
                aluno.setProfissaoResponsavel(rs.getString("profissaoresponsavel"));
                aluno.setRgMae(rs.getString("rgmae"));
                aluno.setRgPai(rs.getString("rgpai"));
                aluno.setRgResponsavel(rs.getString("rgresponsavel"));
                aluno.setSexo(rs.getString("sexo"));
                aluno.setTelefone(rs.getString("telefone"));
                aluno.setTelefoneComercialMae(rs.getString("telefonecomercialmae"));
                aluno.setTelefoneComercialPai(rs.getString("telefonecomercialpai"));
                aluno.setTelefoneComercialResponsavel(rs.getString("telefonecomercialresponsavel"));
                aluno.setTelefoneResponsavel(rs.getString("telefoneresponsavel"));
                aluno.setTermoCertidaoNascimento(rs.getString("termocertidaonascimento"));
                aluno.setTipoCadastro(rs.getString("tipocadastro"));
                aluno.setUf(rs.getString("uf"));

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return aluno;

    }

    public Object buscaUsuario(String nomeUsuario, String matricula) {
        
        PreparedStatement psmt = null;

        try {

            if (!nomeUsuario.isEmpty() && !matricula.isEmpty()) {

                psmt = Conexao.getConexao().prepareStatement("select * from usuario where nomeusuario = ? and matricula = ?");
                psmt.setString(1, nomeUsuario);
                psmt.setInt(2, Integer.valueOf(matricula));

            } else if (!nomeUsuario.isEmpty()) {

                psmt = Conexao.getConexao().prepareStatement("select * from usuario where nomeusuario = ?");
                psmt.setString(1, nomeUsuario);

            } else if (!matricula.isEmpty()) {

                psmt = Conexao.getConexao().prepareStatement("select * from usuario where matricula = ?");
                psmt.setInt(1, Integer.valueOf(matricula));

            }

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaUsuario();
                
                usuario.setNomeUsuario(rs.getString("nomeusuario"));
                usuario.setCodigoUsuario(rs.getInt("codigousuario"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setContraSenha(rs.getString("contrasenha"));
                usuario.setTemaEscolhido(rs.getString("temaescolhido"));
                usuario.setTipoCadastro(rs.getString("tipocadastro"));
                usuario.setMatricula(rs.getInt("matricula"));

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(usuario.getNomeUsuario());
        return usuario;

    }

    public Object buscaUsuarioLogin(String nomeUsuario) {

        try {

            PreparedStatement psmt = Conexao.getConexao().prepareStatement("select * from usuario where nomeusuario = ?");
            psmt.setString(1, nomeUsuario);

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaUsuario();
                
                usuario.setCodigoUsuario(rs.getInt("codigousuario"));
                usuario.setNomeUsuario(rs.getString("nomeusuario"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setContraSenha(rs.getString("contrasenha"));
                usuario.setTipoCadastro(rs.getString("tipocadastro"));
                usuario.setTemaEscolhido(rs.getString("temaescolhido"));
                usuario.setMatricula(rs.getInt("matricula"));

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return usuario;

    }

    /*public Object buscaContraSenha(String contraSenha) {

        try {

            PreparedStatement psmt1 = Conexao.getConexao().prepareStatement("select * from contrasenha where contrasenha = ?");
            psmt1.setString(1, contraSenha);

            ResultSet rs1 = psmt1.executeQuery();

            while (rs1.next()) {

                iniciaContraSenha();
                
                this.contraSenha.setCodigoContraSenha(rs1.getInt("codigocontrasenha"));
                this.contraSenha.setContraSenha(rs1.getString("contrasenha"));
                this.contraSenha.setTipoContraSenha(rs1.getString("tipocontrasenha"));

            }

            rs1.close();
            psmt1.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return this.contraSenha;

    }*/

    public Object buscaPermissoes(String nomeUsuario) {

        try {

            PreparedStatement psmt = Conexao.getConexao().prepareStatement("select * from permissoes where nomeusuario = ?");
            psmt.setString(1, nomeUsuario);

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaPermissoes();
                
                permissoes.setCodigoPermissoes(rs.getInt("codigopermissoes"));
                permissoes.setCadastrarAluno(rs.getBoolean("cadastraraluno"));
                permissoes.setCadastrarCmei(rs.getBoolean("cadastrarcmei"));
                permissoes.setCadastrarFuncaoCargo(rs.getBoolean("cadastrarfuncaocargo"));
                permissoes.setCadastrarLicenca(rs.getBoolean("cadastrarlicenca"));
                permissoes.setCadastrarRestricao(rs.getBoolean("cadastrarrestricao"));
                permissoes.setCadastrarServidor(rs.getBoolean("cadastrarservidor"));
                permissoes.setCadastrarUsuario(rs.getBoolean("cadastrarusuario"));
                permissoes.setNomeUsuario(rs.getString("nomeusuario"));
                permissoes.setImprimirCmei(rs.getBoolean("imprimircmei"));
                permissoes.setImprimirAluno(rs.getBoolean("imprimiraluno"));

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return permissoes;

    }

    public Object buscaServidor(String nomeServidor, String funcaoCargo, String nomeEntidade, String modalidade, String matricula) {

        PreparedStatement psmt;

        try {

            psmt = Conexao.getConexao().prepareStatement("select * from servidor where nomeservidor = '" + nomeServidor + "' and nomeentidade = '" + nomeEntidade + "' and funcaocargo = '" + funcaoCargo + "' and modalidade = '" + modalidade + "' and matricula = '" + matricula + "'order by nomeservidor");

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaServidor();
                
                servidor.setNomeServidor(rs.getString("nomeservidor"));
                servidor.setCodigoServidor(rs.getInt("codigoservidor"));
                servidor.setBairro(rs.getString("bairro"));
                servidor.setCelular(rs.getString("celular"));
                servidor.setEndereco(rs.getString("endereco"));
                servidor.setFuncaoCargo(rs.getString("funcaocargo"));
                servidor.setLicenca_codigoLicenca(rs.getInt("licenca_codigolicenca"));
                servidor.setMatricula(rs.getString("matricula"));
                servidor.setModalidade(rs.getString("modalidade"));
                servidor.setMunicipio(rs.getString("municipio"));
                servidor.setNumeroEndereco(rs.getString("numeroendereco"));
                servidor.setRestricao_codigoRestricao(rs.getInt("restricao_codigorestricao"));
                servidor.setTelefone(rs.getString("telefone"));
                servidor.setTipoEntidade(rs.getString("tipoentidade"));
                servidor.setUf(rs.getString("uf"));
                servidor.setNomeEntidade(rs.getString("nomeentidade"));
                servidor.setStatusContrato(rs.getString("statuscontrato"));

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return servidor;

    }
    
    public Object buscaRestricao(String tipoRestricao, int codigoRestricao) {

        restricao = null;
        PreparedStatement psmt = null;
        String versao = "";
        String selecao = "select * from restricao";
        int contaSelecao = 0;
        int linhas = 0;

        try {

            if (!tipoRestricao.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "tiporestricao like '%" + tipoRestricao + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "tiporestricao like '%" + tipoRestricao + "%'";
                }
            }
            
            if (codigoRestricao != 0) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "codigorestricao = " + codigoRestricao;
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "codigorestricao = " + codigoRestricao;
                }
            }
            
            psmt = Conexao.getConexao().prepareStatement(selecao);

            ResultSet rs = psmt.executeQuery();
        
            while (rs.next()) {

                iniciaRestricao();
                
                restricao.setCodigoRestricao(rs.getInt("codigorestricao"));
                restricao.setDescricaoRestricao(rs.getString("descricaorestricao"));
                restricao.setTipoRestricao(rs.getString("tiporestricao"));

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return restricao;
        
    }
    
    public Object buscaLicenca(String tipoLicenca, int codigoLicenca) {

        licenca = null;
        PreparedStatement psmt = null;
        String versao = "";
        String selecao = "select * from licenca";
        int contaSelecao = 0;
        int linhas = 0;

        try {

            if (!tipoLicenca.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "tipolicenca like '%" + tipoLicenca + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "tipolicenca like '%" + tipoLicenca + "%'";
                }
            }
            
            if (codigoLicenca != 0) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "codigolicenca = " + codigoLicenca;
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "codigolicenca = " + codigoLicenca;
                }
            }
            
            psmt = Conexao.getConexao().prepareStatement(selecao);

            ResultSet rs = psmt.executeQuery();
        
            while (rs.next()) {

                iniciaLicenca();
                
                licenca.setCodigoLicenca(rs.getInt("codigolicenca"));
                licenca.setDescricaoLicenca(rs.getString("descricaolicenca"));
                licenca.setTipoLicenca(rs.getString("tipolicenca"));

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return licenca;
        
    }

//Listadores========================================================================================================================================
    public List listarCmeis() {

        List lista = new ArrayList();
        try {

            PreparedStatement psmt = Conexao.getConexao().prepareStatement("select nomeentidade from cmei order by nomeentidade");

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaCmei();
                
                cmei.setNomeEntidade(rs.getString("nomeentidade"));
                
                lista.add(cmei.getNomeEntidade());
                cmei = null;
                
            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;

    }

    public List listarCmeisBackup() {

        List lista = new ArrayList();

        try {

            PreparedStatement psmt = Conexao.getConexaoBackup().prepareStatement("select * from cmei order by codigocmei");
            //psmt.setString(1, nomeAluno);
            //psmt.setInt(2, Integer.valueOf(codigoAluno));

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaCmei();

                cmei.setNomeEntidade(rs.getString("nomeentidade"));
                cmei.setBairro(rs.getString("bairro"));
                cmei.setCapacidadeAlunos(rs.getString("capacidadealunos"));
                cmei.setVagasDisponiveis(rs.getInt("vagasdisponiveis"));
                cmei.setCodigoCmei(rs.getInt("codigocmei"));
                cmei.setCoordenadorAtual(rs.getString("coordenadoratual"));
                cmei.setEndereco(rs.getString("endereco"));
                cmei.setMunicipio(rs.getString("municipio"));
                cmei.setNumeroEndereco(rs.getString("numeroendereco"));
                cmei.setTelefone(rs.getString("telefone"));
                cmei.setServidoresConcursados(rs.getInt("servidoresconcursados"));
                cmei.setNumeroEstagiarios(rs.getInt("numeroestagiarios"));
                cmei.setNumeroLotacoes(rs.getInt("numerolotacoes"));
                cmei.setNumeroSalasBercarios(rs.getInt("numerosalasbercarios"));
                cmei.setNumeroSalasMaternal(rs.getInt("numerosalasmaternal"));
                cmei.setNumeroSalasPre(rs.getInt("numerosalaspre"));

                lista.add(cmei);
                cmei = null;

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;

    }

    /*public List listarEscolas() {
    
    List lista = new ArrayList();
    try {
    
    PreparedStatement psmt = Conexao.getConexao().prepareStatement("select nomeescola from escola order by nomeescola");
    
    ResultSet rs = psmt.executeQuery();
    
    while (rs.next()) {
    
    Escola escol = new Escola();
    escol.setNomeEscola(rs.getString("nomeescola"));
    lista.add(escol.getNomeEscola());
    
    }
    
    rs.close();
    psmt.close();
    
    } catch (ExcCadastro ex) {
    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return lista;
    
    }*/
    public List listarFuncoesCargos() {

        List lista = new ArrayList();
        PreparedStatement psmt;

        try {

            psmt = Conexao.getConexao().prepareStatement("select * from funcaocargo order by nomefuncaocargo");

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaFuncaoCargo();
                funcaoCargo.setNomeFuncaoCargo(rs.getString("nomefuncaocargo"));

                lista.add(funcaoCargo.getNomeFuncaoCargo());
                funcaoCargo = null;

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;

    }

    public List listarFuncoesCargosCadastrados(String nomeFuncaoCargo) {

        List lista = new ArrayList();
        PreparedStatement psmt = null;
        String selecao = "select * from funcaocargo";
        int contaSelecao = 0;

        try {

            if (!nomeFuncaoCargo.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "nomefuncaocargo like '%" + nomeFuncaoCargo + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "nomefuncaocargo like '%" + nomeFuncaoCargo + "%'";
                }
            }

            psmt = Conexao.getConexao().prepareStatement(selecao + " order by nomefuncaocargo");

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaFuncaoCargo();
                
                funcaoCargo.setNomeFuncaoCargo(rs.getString("nomefuncaocargo"));
                funcaoCargo.setCodigoFuncaoCargo(rs.getInt("codigofuncaocargo"));

                lista.add(funcaoCargo);
                funcaoCargo = null;

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;

    }

    public List listarFuncoesCargosBackup() {

        List lista = new ArrayList();

        try {

            PreparedStatement psmt = Conexao.getConexaoBackup().prepareStatement("select * from funcaocargo order by codigofuncaocargo");
            //psmt.setString(1, nomeAluno);
            //psmt.setInt(2, Integer.valueOf(codigoAluno));

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaFuncaoCargo();
                funcaoCargo.setNomeFuncaoCargo(rs.getString("nomefuncaocargo"));
                funcaoCargo.setCodigoFuncaoCargo(rs.getInt("codigofuncaocargo"));

                lista.add(funcaoCargo);
                funcaoCargo = null;

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;

    }

    public List listarServidores(String nomeEntidade) {

        List lista = new ArrayList();
        PreparedStatement psmt;

        try {

            psmt = Conexao.getConexao().prepareStatement("select * from servidor where nomeentidade = ? order by nomeservidor");
            psmt.setString(1, nomeEntidade);

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaServidor();
                
                servidor.setNomeServidor(rs.getString("nomeservidor"));
                servidor.setCodigoServidor(rs.getInt("codigoservidor"));
                servidor.setBairro(rs.getString("bairro"));
                servidor.setCelular(rs.getString("celular"));
                servidor.setEndereco(rs.getString("endereco"));
                servidor.setFuncaoCargo(rs.getString("funcaocargo"));
                servidor.setLicenca_codigoLicenca(rs.getInt("licenca_codigolicenca"));
                servidor.setMatricula(rs.getString("matricula"));
                servidor.setModalidade(rs.getString("modalidade"));
                servidor.setMunicipio(rs.getString("municipio"));
                servidor.setNumeroEndereco(rs.getString("numeroendereco"));
                servidor.setRestricao_codigoRestricao(rs.getInt("restricao_codigorestricao"));
                servidor.setTelefone(rs.getString("telefone"));
                servidor.setTipoEntidade(rs.getString("tipoentidade"));
                servidor.setUf(rs.getString("uf"));
                servidor.setNomeEntidade(rs.getString("nomeentidade"));
                servidor.setStatusContrato(rs.getString("statuscontrato"));

                lista.add(servidor);
                servidor = null;

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;

    }

    public List listarServidoresCadastrados(String nomeServidor, String nomeEntidade, String funcaoCargo) {

        List lista = new ArrayList();
        PreparedStatement psmt = null;
        String selecao = "select * from servidor";
        int contaSelecao = 0;

        try {

            if (!nomeServidor.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "nomeservidor like '%" + nomeServidor + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "nomeservidor like '%" + nomeServidor + "%'";
                }
            }

            if (!nomeEntidade.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "nomeentidade like '%" + nomeEntidade + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "nomeentidade like '%" + nomeEntidade + "%'";
                }
            }

            if (!funcaoCargo.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "funcaocargo like '%" + funcaoCargo + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "funcaocargo like '%" + funcaoCargo + "%'";
                }
            }

            psmt = Conexao.getConexao().prepareStatement(selecao + " order by nomeservidor");

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaServidor();
                
                servidor.setNomeServidor(rs.getString("nomeservidor"));
                servidor.setCodigoServidor(rs.getInt("codigoservidor"));
                servidor.setBairro(rs.getString("bairro"));
                servidor.setCelular(rs.getString("celular"));
                servidor.setEndereco(rs.getString("endereco"));
                servidor.setFuncaoCargo(rs.getString("funcaocargo"));
                servidor.setLicenca_codigoLicenca(rs.getInt("licenca_codigolicenca"));
                servidor.setMatricula(rs.getString("matricula"));
                servidor.setModalidade(rs.getString("modalidade"));
                servidor.setMunicipio(rs.getString("municipio"));
                servidor.setNumeroEndereco(rs.getString("numeroendereco"));
                servidor.setRestricao_codigoRestricao(rs.getInt("restricao_codigorestricao"));
                servidor.setTelefone(rs.getString("telefone"));
                servidor.setTipoEntidade(rs.getString("tipoentidade"));
                servidor.setUf(rs.getString("uf"));
                servidor.setNomeEntidade(rs.getString("nomeentidade"));
                servidor.setStatusContrato(rs.getString("statuscontrato"));

                lista.add(servidor);
                servidor = null;

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }


        return lista;

    }

    public List listarServidoresBackup(String nomeEntidade) {

        List lista = new ArrayList();
        PreparedStatement psmt = null;
        String selecao = "select * from servidor";
        int contaSelecao = 0;

        try {

            if (!nomeEntidade.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "nomeentidade like '%" + nomeEntidade + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "nomeentidade like '%" + nomeEntidade + "%'";
                }
            }
            
            psmt = Conexao.getConexao().prepareStatement(selecao + " order by nomeservidor");

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaServidor();
                
                servidor.setNomeServidor(rs.getString("nomeservidor"));
                servidor.setCodigoServidor(rs.getInt("codigoservidor"));
                servidor.setBairro(rs.getString("bairro"));
                servidor.setCelular(rs.getString("celular"));
                servidor.setEndereco(rs.getString("endereco"));
                servidor.setFuncaoCargo(rs.getString("funcaocargo"));
                servidor.setLicenca_codigoLicenca(rs.getInt("licenca_codigolicenca"));
                servidor.setMatricula(rs.getString("matricula"));
                servidor.setModalidade(rs.getString("modalidade"));
                servidor.setMunicipio(rs.getString("municipio"));
                servidor.setNumeroEndereco(rs.getString("numeroendereco"));
                servidor.setRestricao_codigoRestricao(rs.getInt("restricao_codigorestricao"));
                servidor.setTelefone(rs.getString("telefone"));
                servidor.setTipoEntidade(rs.getString("tipoentidade"));
                servidor.setUf(rs.getString("uf"));
                servidor.setNomeEntidade(rs.getString("nomeentidade"));
                servidor.setStatusContrato(rs.getString("statuscontrato"));
                
                lista.add(servidor);
                servidor = null;

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;

    }

    public List listarAlunosCadastrados(String nomeAluno, String cpfPai, String cpfMae, String filiacaoPai, String filiacaoMae, String matricula, String nomeEntidade, String tipoCadastro, String modalidade) {

        List lista = new ArrayList();
        PreparedStatement psmt = null;
        String selecao = "select * from aluno";
        int contaSelecao = 0;

        try {

            if (!nomeAluno.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "nomealuno like '%" + nomeAluno + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "nomealuno like '%" + nomeAluno + "%'";
                }
            }

            if (!cpfPai.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "cpfpai = '" + cpfPai + "'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "cpfpai = '" + cpfPai + "'";
                }
            }

            if (!cpfMae.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "cpfmae = '" + cpfMae + "'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "cpfmae = '" + cpfMae + "'";
                }
            }

            if (!filiacaoPai.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "filiacaopai like '%" + filiacaoPai + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "filiacaopai like '%" + filiacaoPai + "%'";
                }
            }

            if (!filiacaoMae.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "filiacaomae like '%" + filiacaoMae + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "filiacaomae like '%" + filiacaoMae + "%'";
                }
            }

            if (!matricula.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "matricula like '%" + matricula + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "matricula like '%" + matricula + "%'";
                }
            }

            if (tipoCadastro.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "tipocadastro not like '%DESISTENCIA%' AND tipocadastro not like '%EGRESSO%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "tipocadastro not like '%DESISTENCIA%' AND tipocadastro not like '%EGRESSO%'";
                }
            } else {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "tipocadastro like '%" + tipoCadastro + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "tipocadastro like '%" + tipoCadastro + "%'";
                }
            }

            if (!modalidade.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "modalidade = '" + modalidade + "'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "modalidade = '" + modalidade + "'";
                }
            }

            if (!nomeEntidade.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "(nomeentidade like '%" + nomeEntidade + "%' or nomeentidadeopcao2 like '%" + nomeEntidade + "%' "
                            + "or nomeentidadeopcao3 like '%" + nomeEntidade + "%')";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "(nomeentidade like '%" + nomeEntidade + "%' or nomeentidadeopcao2 like '%" + nomeEntidade + "%' "
                            + "or nomeentidadeopcao3 like '%" + nomeEntidade + "%')";
                }
            }
            
            if (tipoCadastro.equals("TRANSFERENCIA")) {
                psmt = Conexao.getConexao().prepareStatement(selecao + " order by datasolicitacaotransferencia, nomeentidade, modalidade");
            } else {
                psmt = Conexao.getConexao().prepareStatement(selecao + " order by datacadastro, nomeentidade, modalidade");
            }
            
            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaAluno();
                
                aluno.setAnoRegistroCertidaoNascimento(rs.getString("anoregistrocertidaonascimento"));
                aluno.setBairro(rs.getString("bairro"));
                aluno.setCartorioCertidaoNascimento(rs.getString("cartoriocertidaonascimento"));
                aluno.setCelular1(rs.getString("celular1"));
                aluno.setCelular2(rs.getString("celular2"));
                aluno.setCodigoAluno(rs.getInt("codigoaluno"));
                aluno.setCpfMae(rs.getString("cpfmae"));
                aluno.setCpfPai(rs.getString("cpfpai"));
                aluno.setCpfResponsavel(rs.getString("cpfresponsavel"));
                aluno.setDataCadastro(rs.getDate("datacadastro"));
                aluno.setDataDesistencia(rs.getDate("datadesistencia"));
                aluno.setDataEgresso(rs.getDate("dataegresso"));
                aluno.setDataNascimento(rs.getDate("datanascimento"));
                aluno.setDataNascimentoMae(rs.getDate("datanascimentomae"));
                aluno.setDataNascimentoPai(rs.getDate("datanascimentopai"));
                aluno.setDataNascimentoResponsavel(rs.getDate("datanascimentoresponsavel"));
                aluno.setDataSolicitacaoTransferencia(rs.getDate("datasolicitacaotransferencia"));
                aluno.setDigitoVerificadorCertidaoNascimento(rs.getString("digitoverificadorcertidaonascimento"));
                aluno.setEndereco(rs.getString("endereco"));
                aluno.setEnderecoComercialMae(rs.getString("enderecocomercialmae"));
                aluno.setEnderecoComercialPai(rs.getString("enderecocomercialpai"));
                aluno.setEnderecoComercialResponsavel(rs.getString("enderecocomercialresponsavel"));
                aluno.setEnderecoResponsavel(rs.getString("enderecoresponsavel"));
                aluno.setFiliacaoMae(rs.getString("filiacaomae"));
                aluno.setFiliacaoPai(rs.getString("filiacaopai"));
                aluno.setFolhaCertidaoNascimento(rs.getString("folhacertidaonascimento"));
                aluno.setLivroCertidaoNascimento(rs.getString("livrocertidaonascimento"));
                aluno.setMatricula(rs.getString("matricula"));
                aluno.setModalidade(rs.getString("modalidade"));
                aluno.setMunicipio(rs.getString("municipio"));
                aluno.setNaturalidade(rs.getString("naturalidade"));
                aluno.setNomeAluno(rs.getString("nomealuno"));
                aluno.setNomeEntidade(rs.getString("nomeentidade"));
                aluno.setNomeEntidadeOpcao2(rs.getString("nomeentidadeopcao2"));
                aluno.setNomeEntidadeOpcao3(rs.getString("nomeentidadeopcao3"));
                aluno.setNomeResponsavel(rs.getString("nomeresponsavel"));
                aluno.setNumeroEndereco(rs.getString("numeroendereco"));
                aluno.setNumeroEnderecoComercialMae(rs.getString("numeroenderecocomercialmae"));
                aluno.setNumeroEnderecoComercialPai(rs.getString("numeroenderecocomercialpai"));
                aluno.setNumeroEnderecoResponsavel(rs.getString("numeroenderecoresponsavel"));
                aluno.setNumeroEnderecoComercialResponsavel(rs.getString("numeroenderecocomercialresponsavel"));
                aluno.setNumeroIdentificadorCopel(rs.getString("numeroidentificadorcopel"));
                aluno.setNumeroMatriculaCertidaoNascimento(rs.getString("numeromatriculacertidaonascimento"));
                aluno.setProfissaoMae(rs.getString("profissaomae"));
                aluno.setProfissaoPai(rs.getString("profissaopai"));
                aluno.setProfissaoResponsavel(rs.getString("profissaoresponsavel"));
                aluno.setRgMae(rs.getString("rgmae"));
                aluno.setRgPai(rs.getString("rgpai"));
                aluno.setRgResponsavel(rs.getString("rgresponsavel"));
                aluno.setSexo(rs.getString("sexo"));
                aluno.setTelefone(rs.getString("telefone"));
                aluno.setTelefoneComercialMae(rs.getString("telefonecomercialmae"));
                aluno.setTelefoneComercialPai(rs.getString("telefonecomercialpai"));
                aluno.setTelefoneComercialResponsavel(rs.getString("telefonecomercialresponsavel"));
                aluno.setTelefoneResponsavel(rs.getString("telefoneresponsavel"));
                aluno.setTermoCertidaoNascimento(rs.getString("termocertidaonascimento"));
                aluno.setTipoCadastro(rs.getString("tipocadastro"));
                aluno.setUf(rs.getString("uf"));

                lista.add(aluno);
                aluno = null;

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;

    }

    public List listarAlunosBackup(String nomeEntidade) {

        List lista = new ArrayList();
        PreparedStatement psmt = null;
        String selecao = "select * from aluno";
        int contaSelecao = 0;
        
        try {
            
            if (!nomeEntidade.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "nomeentidade like '%" + nomeEntidade + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "nomeentidade like '%" + nomeEntidade + "%'";
                }
            }

            psmt = Conexao.getConexaoBackup().prepareStatement(selecao + " order by codigoaluno");

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaAluno();

                aluno.setAnoRegistroCertidaoNascimento(rs.getString("anoregistrocertidaonascimento"));
                aluno.setBairro(rs.getString("bairro"));
                aluno.setCartorioCertidaoNascimento(rs.getString("cartoriocertidaonascimento"));
                aluno.setCelular1(rs.getString("celular1"));
                aluno.setCelular2(rs.getString("celular2"));
                aluno.setCodigoAluno(rs.getInt("codigoaluno"));
                aluno.setCpfMae(rs.getString("cpfmae"));
                aluno.setCpfPai(rs.getString("cpfpai"));
                aluno.setCpfResponsavel(rs.getString("cpfresponsavel"));
                aluno.setDataCadastro(rs.getDate("datacadastro"));
                aluno.setDataDesistencia(rs.getDate("datadesistencia"));
                aluno.setDataEgresso(rs.getDate("dataegresso"));
                aluno.setDataNascimento(rs.getDate("datanascimento"));
                aluno.setDataNascimentoMae(rs.getDate("datanascimentomae"));
                aluno.setDataNascimentoPai(rs.getDate("datanascimentopai"));
                aluno.setDataNascimentoResponsavel(rs.getDate("datanascimentoresponsavel"));
                aluno.setDataSolicitacaoTransferencia(rs.getDate("datasolicitacaotransferencia"));
                aluno.setDigitoVerificadorCertidaoNascimento(rs.getString("digitoverificadorcertidaonascimento"));
                aluno.setEndereco(rs.getString("endereco"));
                aluno.setEnderecoComercialMae(rs.getString("enderecocomercialmae"));
                aluno.setEnderecoComercialPai(rs.getString("enderecocomercialpai"));
                aluno.setEnderecoComercialResponsavel(rs.getString("enderecocomercialresponsavel"));
                aluno.setEnderecoResponsavel(rs.getString("enderecoresponsavel"));
                aluno.setFiliacaoMae(rs.getString("filiacaomae"));
                aluno.setFiliacaoPai(rs.getString("filiacaopai"));
                aluno.setFolhaCertidaoNascimento(rs.getString("folhacertidaonascimento"));
                aluno.setLivroCertidaoNascimento(rs.getString("livrocertidaonascimento"));
                aluno.setMatricula(rs.getString("matricula"));
                aluno.setModalidade(rs.getString("modalidade"));
                aluno.setMunicipio(rs.getString("municipio"));
                aluno.setNaturalidade(rs.getString("naturalidade"));
                aluno.setNomeAluno(rs.getString("nomealuno"));
                aluno.setNomeEntidade(rs.getString("nomeentidade"));
                aluno.setNomeResponsavel(rs.getString("nomeresponsavel"));
                aluno.setNumeroEndereco(rs.getString("numeroendereco"));
                aluno.setNumeroEnderecoComercialMae(rs.getString("numeroenderecocomercialmae"));
                aluno.setNumeroEnderecoComercialPai(rs.getString("numeroenderecocomercialpai"));
                aluno.setNumeroEnderecoResponsavel(rs.getString("numeroenderecoresponsavel"));
                aluno.setNumeroEnderecoComercialResponsavel(rs.getString("numeroenderecocomercialresponsavel"));
                aluno.setNumeroIdentificadorCopel(rs.getString("numeroidentificadorcopel"));
                aluno.setNumeroMatriculaCertidaoNascimento(rs.getString("numeromatriculacertidaonascimento"));
                aluno.setProfissaoMae(rs.getString("profissaomae"));
                aluno.setProfissaoPai(rs.getString("profissaopai"));
                aluno.setProfissaoResponsavel(rs.getString("profissaoresponsavel"));
                aluno.setRgMae(rs.getString("rgmae"));
                aluno.setRgPai(rs.getString("rgpai"));
                aluno.setRgResponsavel(rs.getString("rgresponsavel"));
                aluno.setSexo(rs.getString("sexo"));
                aluno.setTelefone(rs.getString("telefone"));
                aluno.setTelefoneComercialMae(rs.getString("telefonecomercialmae"));
                aluno.setTelefoneComercialPai(rs.getString("telefonecomercialpai"));
                aluno.setTelefoneComercialResponsavel(rs.getString("telefonecomercialresponsavel"));
                aluno.setTelefoneResponsavel(rs.getString("telefoneresponsavel"));
                aluno.setTermoCertidaoNascimento(rs.getString("termocertidaonascimento"));
                aluno.setTipoCadastro(rs.getString("tipocadastro"));
                aluno.setUf(rs.getString("uf"));

                lista.add(aluno);
                aluno = null;

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;

    }

    public List listarUsuarios(String nomeUsuario, String matricula, String tipoCadastro) {

        List lista = new ArrayList();
        PreparedStatement psmt = null;
        String selecao = "select * from usuario";
        int contaSelecao = 0;

        try {

            if (!nomeUsuario.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "nomeusuario like '%" + nomeUsuario + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "nomeusuario like '%" + nomeUsuario + "%'";
                }
            }

            if (!matricula.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "matricula like '%" + matricula + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "matricula like '%" + matricula + "%'";
                }
            }

            if (!tipoCadastro.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "tipocadastro like '%" + tipoCadastro + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "tipocadastro like '%" + tipoCadastro + "%'";
                }
            }

            psmt = Conexao.getConexao().prepareStatement(selecao + " order by nomeusuario");

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaUsuario();
                
                usuario.setNomeUsuario(rs.getString("nomeusuario"));
                usuario.setCodigoUsuario(rs.getInt("codigousuario"));
                usuario.setMatricula(rs.getInt("matricula"));
                usuario.setSenha(rs.getString("senha"));

                lista.add(usuario);
                usuario = null;

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;

    }

    public List listarUsuariosBackup(String tipoCadastro) {

        List lista = new ArrayList();

        try {

            PreparedStatement psmt = Conexao.getConexaoBackup().prepareStatement("select * from usuario where tipocadastro = ? order by codigousuario");
            psmt.setString(1, tipoCadastro);
            //psmt.setInt(2, Integer.valueOf(codigoAluno));

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaUsuario();

                usuario.setNomeUsuario(rs.getString("nomeusuario"));
                usuario.setCodigoUsuario(rs.getInt("codigousuario"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setContraSenha(rs.getString("contrasenha"));
                usuario.setTemaEscolhido(rs.getString("temaescolhido"));
                usuario.setTipoCadastro(rs.getString("tipocadastro"));
                usuario.setMatricula(rs.getInt("matricula"));

                lista.add(usuario);
                usuario = null;

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;

    }

    public List listarVersaoUsuariosBackup() {
        
        List lista = new ArrayList();
        
        try {

            PreparedStatement psmt = Conexao.getConexaoBackup().prepareStatement("select * from versaousuario order by codigoversaousuario");
            //psmt.setString(1, nomeAluno);
            //psmt.setInt(2, Integer.valueOf(codigoAluno));

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaVersaoUsuario();
                
                versaoUsuario.setCodigoVersaoUsuario(rs.getInt("codigoversaousuario"));
                versaoUsuario.setCodigoUsuario_Usuario(rs.getInt("codigousuario_usuario"));
                versaoUsuario.setDataAtualizacao(rs.getDate("dataatualizacao"));
                versaoUsuario.setVersaoAtual(rs.getString("versaoatual"));

                lista.add(versaoUsuario);
                versaoUsuario = null;

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
        
    }
    
    public List listarTransferenciaServidorBackup() {
        
        List lista = new ArrayList();
        
        try {

            PreparedStatement psmt = Conexao.getConexaoBackup().prepareStatement("select * from transferenciaservidor order by codigotransferenciaservidor");
            //psmt.setString(1, nomeAluno);
            //psmt.setInt(2, Integer.valueOf(codigoAluno));

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaTransferenciaServidor();
                
                transferenciaServidor.setCodigoServidor(rs.getInt("codigoservidor"));
                transferenciaServidor.setCodigoTransferenciaServidor(rs.getInt("codigotransferenciaservidor"));
                transferenciaServidor.setDataTransferencia(rs.getDate("datatransferencia"));
                transferenciaServidor.setMatriculaServidor(rs.getString("matriculaservidor"));
                transferenciaServidor.setMotivoTransferencia(rs.getString("motivotransferencia"));
                transferenciaServidor.setNomeEntidadeAnterior(rs.getString("nomeentidadeanterior"));
                transferenciaServidor.setNomeEntidadeAtual(rs.getString("nomeentidadeatual"));
                transferenciaServidor.setNomeServidor(rs.getString("nomeservidor"));

                lista.add(transferenciaServidor);
                transferenciaServidor = null;

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
        
    }
    
    public List listarTransferenciaAlunoBackup() {
        
        List lista = new ArrayList();
        
        try {

            PreparedStatement psmt = Conexao.getConexaoBackup().prepareStatement("select * from transferenciaaluno order by codigotransferenciaaluno");
            //psmt.setString(1, nomeAluno);
            //psmt.setInt(2, Integer.valueOf(codigoAluno));

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaTransferenciaAluno();
                
                transferenciaAluno.setCodigoAluno(rs.getInt("codigoaluno"));
                transferenciaAluno.setCodigoTransferenciaAluno(rs.getInt("codigotransferenciaaluno"));
                transferenciaAluno.setDataTransferencia(rs.getDate("datatransferencia"));
                transferenciaAluno.setMatriculaAluno(rs.getString("matriculaaluno"));
                transferenciaAluno.setMotivoTransferencia(rs.getString("motivotransferencia"));
                transferenciaAluno.setNomeEntidadeAnterior(rs.getString("nomeentidadeanterior"));
                transferenciaAluno.setNomeEntidadeAtual(rs.getString("nomeentidadeatual"));
                transferenciaAluno.setNomeAluno(rs.getString("nomealuno"));

                lista.add(transferenciaAluno);
                transferenciaAluno = null;

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
        
    }
    
    public List listarCmeisCadastrados(String nomeCmei, String bairro, String coordenadorAtual) {

        List lista = new ArrayList();
        PreparedStatement psmt = null;
        String selecao = "select * from cmei";
        int contaSelecao = 0;

        try {

            if (!nomeCmei.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "nomeentidade like '%" + nomeCmei + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "nomeentidade like '%" + nomeCmei + "%'";
                }
            }

            if (!bairro.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "bairro like '%" + bairro + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "bairro like '%" + bairro + "%'";
                }
            }

            if (!coordenadorAtual.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "coordenadoratual like '%" + coordenadorAtual + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "coordenadoratual like '%" + coordenadorAtual + "%'";
                }
            }

            psmt = Conexao.getConexao().prepareStatement(selecao + " order by nomeentidade");

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaCmei();

                cmei.setNomeEntidade(rs.getString("nomeentidade"));
                cmei.setBairro(rs.getString("bairro"));
                cmei.setCapacidadeAlunos(rs.getString("capacidadealunos"));
                cmei.setCodigoCmei(rs.getInt("codigocmei"));
                cmei.setCoordenadorAtual(rs.getString("coordenadoratual"));
                cmei.setEndereco(rs.getString("endereco"));
                cmei.setMunicipio(rs.getString("municipio"));
                cmei.setNumeroEndereco(rs.getString("numeroendereco"));
                cmei.setTelefone(rs.getString("telefone"));

                lista.add(cmei);
                cmei = null;

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;

    }

    /*public List listarEscolasCadastradas(String nomeEscola, String bairro, String diretorAtual) {
    
    List lista = new ArrayList();
    
    try {
    
    PreparedStatement psmt = Conexao.getConexao().prepareStatement("select * from escola where nomeescola like '" + nomeEscola + "%' or bairro like '" + bairro + "%' or diretoratual like '" + diretorAtual + "%' order by nomeescola");
    //psmt.setString(1, nomeEscola);
    //psmt.setString(2, bairro);
    //psmt.setString(3, diretorAtual);
    
    ResultSet rs = psmt.executeQuery();
    
    while (rs.next()) {
    
    Escola escol = new Escola();
    escol.setNomeEscola(rs.getString("nomeescola"));
    escol.setBairro(rs.getString("bairro"));
    escol.setCapacidadeAlunos(rs.getString("capacidadealunos"));
    escol.setCodigoEscola(rs.getInt("codigoescola"));
    escol.setDiretorAtual(rs.getString("diretoratual"));
    escol.setDiretorUltimo(rs.getString("diretorultimo"));
    escol.setEndereco(rs.getString("endereco"));
    escol.setMunicipio(rs.getString("municipio"));
    escol.setNumeroEndereco(rs.getString("numeroendereco"));
    escol.setTelefone(rs.getString("telefone"));
    
    lista.add(escol);
    
    }
    
    rs.close();
    psmt.close();
    
    } catch (ExcCadastro ex) {
    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return lista;
    
    }*/
    public List listarPermissoes(String nomeUsuario) {

        List lista = new ArrayList();
        PreparedStatement psmt;

        try {

            psmt = Conexao.getConexao().prepareStatement("select * from permissoes where nomeusuario like '%" + nomeUsuario + "%' order by nomeusuario");
            //psmt.setDate(1, new java.sql.Date(dataSolicitacao.getTime()));
            //psmt.setString(2, tipoOficio);

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaPermissoes();
                
                permissoes.setCodigoPermissoes(rs.getInt("codigopermissoes"));
                permissoes.setCadastrarCmei(rs.getBoolean("cadastrarcmei"));
                permissoes.setCadastrarFuncaoCargo(rs.getBoolean("cadastrarfuncaocargo"));
                permissoes.setCadastrarAluno(rs.getBoolean("cadastraraluno"));
                permissoes.setCadastrarUsuario(rs.getBoolean("cadastrarusuario"));
                permissoes.setCadastrarServidor(rs.getBoolean("cadastrarservidor"));
                permissoes.setNomeUsuario(rs.getString("nomeusuario"));
                permissoes.setImprimirCmei(rs.getBoolean("imprimircmei"));
                permissoes.setImprimirAluno(rs.getBoolean("imprimiraluno"));

                lista.add(permissoes);
                permissoes = null;

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;

    }

    public List listarPermissoesBackpup() {

        List lista = new ArrayList();

        try {

            PreparedStatement psmt = Conexao.getConexaoBackup().prepareStatement("select * from permissoes order by codigopermissoes");
            //psmt.setString(1, nomeAluno);
            //psmt.setInt(2, Integer.valueOf(codigoAluno));

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaPermissoes();
                
                permissoes.setCodigoPermissoes(rs.getInt("codigopermissoes"));
                permissoes.setCadastrarCmei(rs.getBoolean("cadastrarcmei"));
                permissoes.setCadastrarFuncaoCargo(rs.getBoolean("cadastrarfuncaocargo"));
                permissoes.setCadastrarAluno(rs.getBoolean("cadastraraluno"));
                permissoes.setCadastrarUsuario(rs.getBoolean("cadastrarusuario"));
                permissoes.setCadastrarServidor(rs.getBoolean("cadastrarservidor"));
                permissoes.setNomeUsuario(rs.getString("nomeusuario"));
                permissoes.setImprimirCmei(rs.getBoolean("imprimircmei"));
                permissoes.setImprimirAluno(rs.getBoolean("imprimiraluno"));

                lista.add(permissoes);
                permissoes = null;

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;

    }

    public List listarTransferenciasServidor(String nomeServidor, String matricula, String nomeEntidadeAnterior, String nomeEntidadeAtual, Date dataInicio, Date dataTermino) {
        
        List lista = new ArrayList();
        PreparedStatement psmt = null;
        String selecao = "select * from transferenciaservidor";
        int contaSelecao = 0;
        
        try {

            if (!nomeServidor.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "nomeservidor like '%" + nomeServidor + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "nomeservidor like '%" + nomeServidor + "%'";
                }
            }

            if (!matricula.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "matriculaservidor = " + matricula;
                    contaSelecao++;
                } else {
                    
                    selecao = selecao + " and " + "matriculaservidor = " + matricula;
                }
            }

            if (!nomeEntidadeAnterior.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "nomeentidadeanterior like '%" + nomeEntidadeAnterior + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "nomeentidadeanterior like '%" + nomeEntidadeAnterior + "%'";
                }
            }
            
            if (!nomeEntidadeAtual.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "nomeentidadeatual like '%" + nomeEntidadeAtual + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "nomeentidadeatual like '%" + nomeEntidadeAtual + "%'";
                }
            }

            if (dataInicio != null && dataTermino != null) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "datatransferencia between '" + new java.sql.Date(dataInicio.getTime()) + "' and '" + new java.sql.Date(dataTermino.getTime()) + "'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "datatransferencia between '" + new java.sql.Date(dataInicio.getTime()) + "' and '" + new java.sql.Date(dataTermino.getTime()) + "'";
                }
            }

            if (dataInicio != null && dataTermino == null) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "datatransferencia = '" + new java.sql.Date(dataInicio.getTime()) + "'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "datatransferencia = '" + new java.sql.Date(dataInicio.getTime()) + "'";
                }
            }

            if (dataInicio == null && dataTermino != null) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "datatransferencia = '" + new java.sql.Date(dataTermino.getTime()) + "'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "datatransferencia = '" + new java.sql.Date(dataTermino.getTime()) + "'";
                }
            }
            
            
            psmt = Conexao.getConexao().prepareStatement(selecao + " order by nomeservidor");

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaTransferenciaServidor();

                transferenciaServidor.setCodigoServidor(rs.getInt("codigoservidor"));
                transferenciaServidor.setCodigoTransferenciaServidor(rs.getInt("codigotransferenciaservidor"));
                transferenciaServidor.setDataTransferencia(rs.getDate("datatransferencia"));
                transferenciaServidor.setMatriculaServidor(rs.getString("matriculaservidor"));
                transferenciaServidor.setMotivoTransferencia(rs.getString("motivotransferencia"));
                transferenciaServidor.setNomeEntidadeAnterior(rs.getString("nomeentidadeanterior"));
                transferenciaServidor.setNomeEntidadeAtual(rs.getString("nomeentidadeatual"));
                transferenciaServidor.setNomeServidor(rs.getString("nomeservidor"));
                
                lista.add(transferenciaServidor);
                transferenciaServidor = null;
                
            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
        
    }
    
    public List listarTransferenciasAlunos(String nomeAluno, String cpfPaiMae, String filiacaoPaiMae, String nomeEntidadeAnterior, String nomeEntidadeAtual, Date dataInicio, Date dataTermino) {
        
        List lista = new ArrayList();
        PreparedStatement psmt = null;
        String selecao = "select * from transferenciaaluno";
        int contaSelecao = 0;
        
        try {

            if (!nomeAluno.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "nomealuno like '%" + nomeAluno + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "nomealuno like '%" + nomeAluno + "%'";
                }
            }

            if (!cpfPaiMae.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "(cpfpai = " + cpfPaiMae + " or cpfmae = " + cpfPaiMae + ")";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "(cpfpai = " + cpfPaiMae + " or cpfmae = " + cpfPaiMae + ")";
                }
            }

            if (!filiacaoPaiMae.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "(filiacaopai like '%" + filiacaoPaiMae + "%' or filiacaomae like '%" + filiacaoPaiMae + "%')";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "(filiacaopai like '%" + filiacaoPaiMae + "%' or filiacaomae like '%" + filiacaoPaiMae + "%')";
                }
            }
            
            if (!nomeEntidadeAnterior.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "nomeentidadeanterior like '%" + nomeEntidadeAnterior + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "nomeentidadeanterior like '%" + nomeEntidadeAnterior + "%'";
                }
            }
            
            if (!nomeEntidadeAtual.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "nomeentidadeatual like '%" + nomeEntidadeAtual + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "nomeentidadeatual like '%" + nomeEntidadeAtual + "%'";
                }
            }

            if (dataInicio != null && dataTermino != null) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "datatransferencia between '" + new java.sql.Date(dataInicio.getTime()) + "' and '" + new java.sql.Date(dataTermino.getTime()) + "'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "datatransferencia between '" + new java.sql.Date(dataInicio.getTime()) + "' and '" + new java.sql.Date(dataTermino.getTime()) + "'";
                }
            }

            if (dataInicio != null && dataTermino == null) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "datatransferencia = '" + new java.sql.Date(dataInicio.getTime()) + "'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "datatransferencia = '" + new java.sql.Date(dataInicio.getTime()) + "'";
                }
            }

            if (dataInicio == null && dataTermino != null) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "datatransferencia = '" + new java.sql.Date(dataTermino.getTime()) + "'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "datatransferencia = '" + new java.sql.Date(dataTermino.getTime()) + "'";
                }
            }
            
            
            psmt = Conexao.getConexao().prepareStatement(selecao + " order by nomealuno");

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaTransferenciaAluno();

                transferenciaAluno.setCodigoAluno(rs.getInt("codigoaluno"));
                transferenciaAluno.setCodigoTransferenciaAluno(rs.getInt("codigotransferenciaaluno"));
                transferenciaAluno.setCpfMae(rs.getString("cpfmae"));
                transferenciaAluno.setCpfPai(rs.getString("cpfpai"));
                transferenciaAluno.setFiliacaoMae(rs.getString("filiacaomae"));
                transferenciaAluno.setFiliacaoPai(rs.getString("filiacaopai"));
                transferenciaAluno.setDataTransferencia(rs.getDate("datatransferencia"));
                transferenciaAluno.setMatriculaAluno(rs.getString("matriculaaluno"));
                transferenciaAluno.setMotivoTransferencia(rs.getString("motivotransferencia"));
                transferenciaAluno.setNomeEntidadeAnterior(rs.getString("nomeentidadeanterior"));
                transferenciaAluno.setNomeEntidadeAtual(rs.getString("nomeentidadeatual"));
                transferenciaAluno.setNomeAluno(rs.getString("nomealuno"));
                
                lista.add(transferenciaAluno);
                transferenciaAluno = null;

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
        
    }
    
    public List listarRestricoesCadastradas(String tipoRestricao) {

        List lista = new ArrayList();
        PreparedStatement psmt = null;
        String selecao = "select * from restricao";
        int contaSelecao = 0;

        try {

            if (!tipoRestricao.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "tiporestricao like '%" + tipoRestricao + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "tiporestricao like '%" + tipoRestricao + "%'";
                }
            }

            psmt = Conexao.getConexao().prepareStatement(selecao + " order by codigorestricao, tiporestricao");

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaRestricao();
                
                restricao.setCodigoRestricao(rs.getInt("codigorestricao"));
                restricao.setDescricaoRestricao(rs.getString("descricaorestricao"));
                restricao.setTipoRestricao(rs.getString("tiporestricao"));

                lista.add(restricao);
                restricao = null;

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;

    }
    
    public List listarLicencasCadastradas(String tipoLicenca) {

        List lista = new ArrayList();
        PreparedStatement psmt = null;
        String selecao = "select * from licenca";
        int contaSelecao = 0;

        try {

            if (!tipoLicenca.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "tipolicenca like '%" + tipoLicenca + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "tipolicenca like '%" + tipoLicenca + "%'";
                }
            }

            psmt = Conexao.getConexao().prepareStatement(selecao + " order by codigolicenca, tipolicenca");

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                iniciaLicenca();
                
                licenca.setCodigoLicenca(rs.getInt("codigolicenca"));
                licenca.setDescricaoLicenca(rs.getString("descricaolicenca"));
                licenca.setTipoLicenca(rs.getString("tipolicenca"));

                lista.add(licenca);
                licenca = null;

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;

    }
    
//Verificadores========================================================================================================================================
    public Object verificarNomeUsuario(String nomeUsuario) {

        iniciaUsuario();
        String selecao = "SELECT u FROM Usuario u";
        int contador = 0;
        
        if (!nomeUsuario.isEmpty()) {
            if (contador == 0) {
                selecao = selecao + " WHERE u.nomeUsuario like :nomeusuario";
                contador = 1;
            } else {
                selecao = selecao + " AND u.nomeUsuario like :nomeusuario";
            }
        }
        
        iniciaSessao();
        org.hibernate.Query query = session.createQuery(selecao);
        
        if (!nomeUsuario.isEmpty()) {
            query.setParameter("nomeusuario", "%" + nomeUsuario + "%");
        }
        
        usuario = (Usuario) query.uniqueResult();

        encerraSessao();
        
        if (usuario == null) {
            return null;
        } else {
            return usuario; 
        }
        
        /*String verificador = "";

        try {

            PreparedStatement psmt = Conexao.getConexao().prepareStatement("select nomeusuario from usuario where nomeusuario = ?");
            psmt.setString(1, usuarioVerificado);
            //psmt.setString(2, bairro);
            //psmt.setString(3, coordenadorAtual);

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                verificador = rs.getString("nomeusuario");

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return verificador;*/

    }

    public String verificarCadastroCmei(String cmeiVerificado) {

        String verificador = "";

        try {

            PreparedStatement psmt = Conexao.getConexao().prepareStatement("select nomeentidade from cmei where nomeentidade = ?");
            psmt.setString(1, cmeiVerificado);
            //psmt.setString(2, bairro);
            //psmt.setString(3, coordenadorAtual);

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                verificador = rs.getString("nomeentidade");

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return verificador;

    }

    public String verificarCadastroFuncaoCargo(String funcaoCargoVerificado) {

        String verificador = "";

        try {

            PreparedStatement psmt = Conexao.getConexao().prepareStatement("select nomefuncaocargo from funcaocargo where nomefuncaocargo = ?");
            psmt.setString(1, funcaoCargoVerificado);
            //psmt.setString(2, bairro);
            //psmt.setString(3, coordenadorAtual);

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                verificador = rs.getString("nomefuncaocargo");

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return verificador;

    }

    public String verificarCadastroAluno(String alunoVerificado, String filiacaoPai, String cpfPai, String filiacaoMae, String cpfMae) {

        String verificador = "";
        PreparedStatement psmt = null;
        String selecao = "select nomealuno from aluno";
        int contaSelecao = 0;

        try {

            if (!alunoVerificado.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "nomealuno like '%" + alunoVerificado + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "nomealuno like '%" + alunoVerificado + "%'";
                }
            }


            if (!filiacaoPai.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "filiacaopai like '%" + filiacaoPai + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "filiacaopai like '%" + filiacaoPai + "%'";
                }
            }

            if (!cpfPai.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "cpfpai like '%" + cpfPai + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "cpfpai like '%" + cpfPai + "%'";
                }
            }

            /*psmt = Conexao.getConexao().prepareStatement("select nomealuno from aluno where nomealuno = ? and filiacaopai like '%" + filiacaoPai + "%' and cpfpai = ?");
            psmt.setString(1, alunoVerificado);
            psmt.setString(2, cpfPai);*/


            if (!filiacaoMae.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "filiacaomae like '%" + filiacaoMae + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "filiacaomae like '%" + filiacaoMae + "%'";
                }
            }

            if (!cpfMae.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "cpfmae like '%" + cpfMae + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "cpfmae like '%" + cpfMae + "%'";
                }
            }

            /*psmt = Conexao.getConexao().prepareStatement("select nomealuno from aluno where nomealuno = ? and filiacaomae like '%" + filiacaoMae + "%' and cpfmae = ?");
            psmt.setString(1, alunoVerificado);
            psmt.setString(2, cpfMae);*/


            psmt = Conexao.getConexao().prepareStatement(selecao);

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                verificador = rs.getString("nomealuno");

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return verificador;

    }

    public String verificarCadastroServidorEmUsuario(String nomeServidor, String matricula) {

        String verificador = "";
        PreparedStatement psmt;

        try {

            psmt = Conexao.getConexao().prepareStatement("select nomeservidor from servidor where nomeservidor = ? and matricula = ?");
            psmt.setString(1, nomeServidor);
            psmt.setString(2, matricula);

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                verificador = rs.getString("nomeservidor");

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return verificador;

    }

    public Object verificarCadastroServidor(String nomeServidor, String matricula) {

        iniciaServidor();
        String selecao = "SELECT s FROM Servidor s";
        int contador = 0;
        
        if (!nomeServidor.isEmpty()) {
            if (contador == 0) {
                selecao = selecao + " WHERE s.nomeServidor like :nomeservidor";
                contador = 1;
            } else {
                selecao = selecao + " AND s.nomeServidor like :nomeservidor";
            }
        }
        
        if (!matricula.isEmpty()) {
            if (contador == 0) {
                selecao = selecao + " WHERE s.matricula like :matricula";
                contador = 1;
            } else {
                selecao = selecao + " AND s.matricula like :matricula";
            }
        }
        
        iniciaSessao();
        org.hibernate.Query query = session.createQuery(selecao);
        
        if (!nomeServidor.isEmpty()) {
            query.setParameter("nomeservidor", "%" + nomeServidor + "%");
        }
        
        if (!matricula.isEmpty()) {
            query.setParameter("matricula", "%" + matricula + "%");
        }
        
        servidor = (Servidor) query.uniqueResult();

        encerraSessao();
        
        if (servidor == null) {
            return null;
        } else {
            return servidor; 
        }
        
        /*String verificador = "";
        PreparedStatement psmt;

        try {

            psmt = Conexao.getConexao().prepareStatement("select nomeservidor from servidor where nomeservidor = ? and matricula = ?");
            psmt.setString(1, nomeServidor);
            psmt.setString(2, matricula);

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                verificador = rs.getString("nomeservidor");

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return verificador;*/

    }

    public String verificarCadastroPermissoes(String nomeUsuario) {

        String verificador = "";
        PreparedStatement psmt;

        try {

            psmt = Conexao.getConexao().prepareStatement("select nomeusuario from permissoes where nomeusuario = ?");
            psmt.setString(1, nomeUsuario);

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                verificador = rs.getString("nomeusuario");

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return verificador;

    }
    
    public String verificarCadastroVersaoUsuario(int codigousuario_usuario, String versaoAtual) { 
     
        String verificador = "";
        
        PreparedStatement psmt;

        try {

            psmt = Conexao.getConexao().prepareStatement("select codigoversaousuario from versaousuario where codigousuario_usuario = ? and versaoatual = ?");
            psmt.setInt(1, codigousuario_usuario);
            psmt.setString(2, versaoAtual);

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                verificador = rs.getString("codigoversaousuario");

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return verificador;
        
    }
    
    public String verificarCadastroTransferenciaServidor(String nomeServidor, String matriculaServidor, String nomeEntidadeAnterior, String nomeEntidadeAtual) { 
     
        String verificador = "";
        
        PreparedStatement psmt;

        try {

            psmt = Conexao.getConexao().prepareStatement("select nomeservidor from transferenciaservidor where nomeservidor = ? and matriculaservidor = ? and nomeentidadeanterior = ? and nomeentidadeatual = ?");
            psmt.setString(1, nomeServidor);
            psmt.setString(2, matriculaServidor);
            psmt.setString(3, nomeEntidadeAnterior);
            psmt.setString(4, nomeEntidadeAtual);

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                verificador = rs.getString("nomeservidor");

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return verificador;
        
    }
    
    public String verificarCadastroTransferenciaAluno(String nomeAluno, String matriculaAluno, String nomeEntidadeAnterior, String nomeEntidadeAtual) { 
     
        String verificador = "";
        
        PreparedStatement psmt;

        try {

            psmt = Conexao.getConexao().prepareStatement("select nomealuno from transferenciaaluno where nomealuno = ? and matriculaaluno = ? and nomeentidadeanterior = ? and nomeentidadeatual = ?");
            psmt.setString(1, nomeAluno);
            psmt.setString(2, matriculaAluno);
            psmt.setString(3, nomeEntidadeAnterior);
            psmt.setString(4, nomeEntidadeAtual);

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                verificador = rs.getString("nomealuno");

            }

            rs.close();
            psmt.close();

        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return verificador;
        
    }
    
//Quantidades========================================================================================================================================
    /**
    * Retorna quantidade de alunos por tipo de cadastro, baseado no nome do cmei (se houver), e modalidade.
    */
    public int getQuantidadeCadastro(String nomeCmei, String tipoCadastro, String modalidade) {
        
        PreparedStatement psmt = null;
        
        int quantidade = 0;
        int contaSelecao = 0;
        String selecao = "select count(nomealuno) as quantidade from aluno";
        

        try {

            if (!nomeCmei.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "nomeentidade like '%" + nomeCmei + "%'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "nomeentidade like '%" + nomeCmei + "%'";
                }
            }
            
             if (!tipoCadastro.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "tipocadastro = '" + tipoCadastro + "'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "tipocadastro = '" + tipoCadastro + "'";
                }
            }
             
             if (!modalidade.isEmpty()) {
                if (contaSelecao == 0) {
                    selecao = selecao + " where " + "modalidade = '" + modalidade + "'";
                    contaSelecao++;
                } else {
                    selecao = selecao + " and " + "modalidade = '" + modalidade + "'";
                }
            }
            
            psmt = Conexao.getConexao().prepareStatement(selecao);

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                quantidade = rs.getInt("quantidade");
                
            }

            rs.close();
            psmt.close();
            
        } catch (ExcCadastro ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return quantidade;
        
    }
    
//Geradores=====================================================================================================================================================
    public void iniciaAluno() {
        if (aluno == null) {
            aluno = new Aluno();
        }
    }
    public void iniciaCmei() {
        if (cmei == null) {
            cmei = new Cmei();
        }
    }
    
    public void iniciaContraSenha() {
        if (contraSenha == null) {
            contraSenha = new Contrasenha();
        }
    }
    
    public void iniciaFuncaoCargo() {
        if (funcaoCargo == null) {
            funcaoCargo = new FuncaoCargo();
        }
    }
    
    public void iniciaLicenca() {
        if (licenca == null) {
            licenca = new Licenca();
        }
    }
    
    public void iniciaPermissoes() {
        if (permissoes == null) {
            permissoes = new Permissoes();
        }
    }
    
    public void iniciaRestricao() {
        if (restricao == null) {
            restricao = new Restricao();
        }
    }
    
    public void iniciaServidor() {
        if (servidor == null) {
            servidor = new Servidor();
        }
    }
    
    public void encerraSessao() {
        session.close();
    }
    
    public void iniciaSessao() {
        session = factory.openSession();
    }
    
    public void iniciaTransferenciaServidor() {
        if (transferenciaServidor == null) {
            transferenciaServidor = new TransferenciaServidor();
        } 
    }
    
    public void iniciaTransferenciaAluno() {
        if (transferenciaAluno == null) {
            transferenciaAluno = new TransferenciaAluno();
        } 
    }
    
    public void iniciaUsuario() {
        if (usuario == null) {
            usuario = new Usuario();
        }
    }
    
    public void iniciaVersaoUsuario() {
        if (versaoUsuario == null) {
            versaoUsuario = new VersaoUsuario();
        }
    }
    
}
