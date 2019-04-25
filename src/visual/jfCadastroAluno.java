/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * jfCadastroAluno.java
 *
 * Created on 05/08/2010, 14:13:51
 */
package visual;

import com.toedter.calendar.JDateChooser;
import conexao.Conexao;
import controlador.*;
import dao.DAO;
import excecoes.ExcCadastro;
import java.awt.Image;
import java.awt.Toolkit;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Aluno;
import modelo.Cmei;
import modelo.Permissoes;
import modelo.TransferenciaAluno;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author luizam
 */
public class jfCadastroAluno extends javax.swing.JFrame {

    private static ControleVersao versao = new ControleVersao();
    private DAO dao;
    private Aluno aluno;
    private Formatos formatos = new Formatos();
    private ValidaData validaData = new ValidaData();
    private jdBuscaAluno jdba;
    private ClasseGeral classeGeral;
    private ControleCaracteresEspeciais controleCaracteresEspeciais;
    private ControleImagem controleImagem = new ControleImagem();
    private ControleNumeroCertidaoNascimento controleNumeroCertidaoNascimento;
    private TransferenciaAluno transferenciaAluno;
    private VerificadorModalidades verificadorModalidades;
    
    private JasperPrint jPrint;
    private JasperReport jReport;
    private JasperViewer jViewer;
    
    private int codigo = 0;
    private String nomeUsuario;
    private String alunoVerificado;
    private String opcao = "salvar";
    private String tipoCadastro = "FILA";
    private String modalidade = "BERÇÁRIO";
    private String tipoCadastroUsuario;
    private boolean cadastrarAluno;

    /**
     * Creates new form jfCadastroAluno
     */
    public jfCadastroAluno() {
        super("Cadastro Aluno - " + versao.getVersao() + " - " + versao.getAno());
        initComponents();

        //Altera icone na barra de titulo
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.getImage(controleImagem.getIconeImagem());
        this.setIconImage(img);

        //maximiza tela
        //this.setExtendedState(MAXIMIZED_BOTH);

        //centraliza tela
        setSize(getWidth(), getHeight());
        setLocationRelativeTo(null);

        try {
            jftfTelefone.setFormatterFactory(formatos.getFormatoTelefone());
            jftfCelular1.setFormatterFactory(formatos.getFormatoTelefone());
            jftfCelular2.setFormatterFactory(formatos.getFormatoTelefone());
            jftfTelefoneComercialMae.setFormatterFactory(formatos.getFormatoTelefone());
            jftfTelefoneComercialPai.setFormatterFactory(formatos.getFormatoTelefone());
            jftfTelefoneResponsavel.setFormatterFactory(formatos.getFormatoTelefone());
            jftfTelefoneComercialResponsavel.setFormatterFactory(formatos.getFormatoTelefone());
        } catch (ParseException ex) {
            iniciaClasseGeral();
            Logger.getLogger(jfCadastroCmei.class.getName()).log(Level.SEVERE, null, ex);
            classeGeral.msgErro("Não foi possivel formatar campos de telefone, \n Contate o Administrador!");
        }

        try {
            jftfCpfMae.setFormatterFactory(formatos.getFormatoCpf());
            jftfCpfPai.setFormatterFactory(formatos.getFormatoCpf());
            jftfCpfResponsavel.setFormatterFactory(formatos.getFormatoCpf());
        } catch (ParseException ex) {
            iniciaClasseGeral();
            Logger.getLogger(jfCadastroCmei.class.getName()).log(Level.SEVERE, null, ex);
            classeGeral.msgErro("Não foi possivel formatar campos de CPF, \n Contate o Administrador!");
        }

        try {
            jftfRgMae.setFormatterFactory(formatos.getFormatoRg());
            jftfRgPai.setFormatterFactory(formatos.getFormatoRg());
            jftfRgResponsavel.setFormatterFactory(formatos.getFormatoRg());
        } catch (ParseException ex) {
            iniciaClasseGeral();
            Logger.getLogger(jfCadastroCmei.class.getName()).log(Level.SEVERE, null, ex);
            classeGeral.msgErro("Não foi possivel formatar campos de RG, \n Contate o Administrador!");
        }

        try {
            jftfNumeroIdentificadorCopel.setFormatterFactory(formatos.getFormatoNumeroIdentificadorCopel());
        } catch (ParseException ex) {
            iniciaClasseGeral();
            Logger.getLogger(jfCadastroCmei.class.getName()).log(Level.SEVERE, null, ex);
            classeGeral.msgErro("Não foi possivel formatar campos de RG, \n Contate o Administrador!");
        }

        try {
            jftfMatriculaCertidao.setFormatterFactory(formatos.getFormatoCertidaoNascimento());
        } catch (ParseException ex) {
            iniciaClasseGeral();
            Logger.getLogger(jfCadastroCmei.class.getName()).log(Level.SEVERE, null, ex);
            classeGeral.msgErro("Não foi possivel formatar campos de RG, \n Contate o Administrador!");
        }

        try {
            jftfNumeroIdentificadorCopel.setFormatterFactory(formatos.getFormatoNumeroIdentificadorCopel());
        } catch (ParseException ex) {
            iniciaClasseGeral();
            Logger.getLogger(jfCadastroCmei.class.getName()).log(Level.SEVERE, null, ex);
            classeGeral.msgErro("Não foi possivel formatar o campo Matrícula da Certidão de Nascimento, \n Contate o Administrador!");
        }
        
        try {
            jftfHoraSolicitacaoTransferencia.setFormatterFactory(formatos.getFormatoHora());
        } catch (ParseException ex) {
            iniciaClasseGeral();
            Logger.getLogger(jfCadastroCmei.class.getName()).log(Level.SEVERE, null, ex);
            classeGeral.msgErro("Não foi possivel formatar o campo Hora da solicitação de transferência, \n Contate o Administrador!");
        }
        
        setNumeroCaracteres();

        jDateChooserCadastro.setDate(validaData.getDateAtual());

        jtfMunicipio.setText("CASCAVEL");

        jtfVagasDisponiveis.setEnabled(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btgSelecaoModalidade = new javax.swing.ButtonGroup();
        btgSelecaoMatricula = new javax.swing.ButtonGroup();
        jpBotoes = new javax.swing.JPanel();
        jbSalvar = new javax.swing.JButton();
        jbBuscar = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();
        jbExcluir = new javax.swing.JButton();
        jbAlterar = new javax.swing.JButton();
        jbImprimirPerfilCompleto = new javax.swing.JButton();
        jbTransferencia = new javax.swing.JButton();
        jbImprimir = new javax.swing.JButton();
        jtpCadastroAluno = new javax.swing.JTabbedPane();
        jpCadAluno = new javax.swing.JPanel();
        jpCadastroAluno = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jtfNomeAluno = new javax.swing.JTextField();
        jtfMatriculaAluno = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jcbNomeEntidade = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        jtfVagasDisponiveis = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jDateChooserNascimento = new com.toedter.calendar.JDateChooser();
        jtfEndereco = new javax.swing.JTextField();
        jtfNumeroEndereco = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jcbUf = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        jtfBairro = new javax.swing.JTextField();
        jtfMunicipio = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jftfTelefone = new javax.swing.JFormattedTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jftfCelular1 = new javax.swing.JFormattedTextField();
        jftfCelular2 = new javax.swing.JFormattedTextField();
        jPanel1 = new javax.swing.JPanel();
        jrbBercario = new javax.swing.JRadioButton();
        jrbMaternal1 = new javax.swing.JRadioButton();
        jrbMaternal2 = new javax.swing.JRadioButton();
        jrbPre1 = new javax.swing.JRadioButton();
        jrbPre2 = new javax.swing.JRadioButton();
        jlbTipoCadastro = new javax.swing.JLabel();
        jpSelecaoMatricula = new javax.swing.JPanel();
        jrbMatricula = new javax.swing.JRadioButton();
        jrbFilaEspera = new javax.swing.JRadioButton();
        jrbTransferencia = new javax.swing.JRadioButton();
        jDateChooserTransferencia = new com.toedter.calendar.JDateChooser();
        jLabel59 = new javax.swing.JLabel();
        jftfHoraSolicitacaoTransferencia = new javax.swing.JFormattedTextField();
        jLabel16 = new javax.swing.JLabel();
        jcbSexo = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jtfNaturalidade = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jftfNumeroIdentificadorCopel = new javax.swing.JFormattedTextField();
        jDateChooserCadastro = new com.toedter.calendar.JDateChooser();
        jpSelecaoDesistencia = new javax.swing.JPanel();
        jrbDesistencia = new javax.swing.JRadioButton();
        jDateChooserDesistencia = new com.toedter.calendar.JDateChooser();
        jpControleEgresso = new javax.swing.JPanel();
        jrbEgresso = new javax.swing.JRadioButton();
        jDateChooserControleEgresso = new com.toedter.calendar.JDateChooser();
        jLabel33 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jcbNomeEntidadeOpcao2 = new javax.swing.JComboBox();
        jcbNomeEntidadeOpcao3 = new javax.swing.JComboBox();
        jLabel54 = new javax.swing.JLabel();
        jcbBolsaFamilia = new javax.swing.JComboBox();
        jpCadPais = new javax.swing.JPanel();
        jpCadastroPais = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jtfFiliacaoPai = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jftfCpfPai = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jftfRgPai = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        jDateChooserNascimentoPai = new com.toedter.calendar.JDateChooser();
        jLabel30 = new javax.swing.JLabel();
        jtfProfissaoPai = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jftfTelefoneComercialPai = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        jtfEnderecoComercialPai = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jtfNumeroEnderecoComercialPai = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jtfFiliacaoMae = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jftfCpfMae = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        jftfRgMae = new javax.swing.JFormattedTextField();
        jLabel27 = new javax.swing.JLabel();
        jDateChooserNascimentoMae = new com.toedter.calendar.JDateChooser();
        jLabel31 = new javax.swing.JLabel();
        jtfProfissaoMae = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jftfTelefoneComercialMae = new javax.swing.JFormattedTextField();
        jLabel28 = new javax.swing.JLabel();
        jtfEnderecoComercialMae = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jtfNumeroEnderecoComercialMae = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jftfPassaportePai = new javax.swing.JFormattedTextField();
        jftfPassaporteMae = new javax.swing.JFormattedTextField();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jpCadCertidaoNascimento = new javax.swing.JPanel();
        jpBuscaFuncaoCargo = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jtfLivroCertidaoNascimento = new javax.swing.JTextField();
        jtfFolhaCertidaoNascimento = new javax.swing.JTextField();
        jtfTermoCertidaoNascimento = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jftfMatriculaCertidao = new javax.swing.JFormattedTextField();
        jLabel50 = new javax.swing.JLabel();
        jtfAnoRegistroCertidaoNascimento = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jtfDigitoVerificadorCertidaoNascimento = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jtfCartorioCertidaoNascimento = new javax.swing.JTextField();
        jpCadResponsavel = new javax.swing.JPanel();
        jpCadastroResponsavel = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jtfNomeResponsavel = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jftfCpfResponsavel = new javax.swing.JFormattedTextField();
        jLabel38 = new javax.swing.JLabel();
        jftfRgResponsavel = new javax.swing.JFormattedTextField();
        jLabel39 = new javax.swing.JLabel();
        jDateChooserNascimentoResponsavel = new com.toedter.calendar.JDateChooser();
        jLabel40 = new javax.swing.JLabel();
        jtfProfissaoResponsavel = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jftfTelefoneResponsavel = new javax.swing.JFormattedTextField();
        jLabel42 = new javax.swing.JLabel();
        jtfEnderecoResponsavel = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jtfNumeroEnderecoResponsavel = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jtfEnderecoComercialResponsavel = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jtfNumeroEnderecoComercialResponsavel = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jftfTelefoneComercialResponsavel = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jpBotoes.setBorder(new org.jdesktop.swingx.border.DropShadowBorder());

        jbSalvar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/OK.png"))); // NOI18N
        jbSalvar.setText("Salvar");
        jbSalvar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalvarActionPerformed(evt);
            }
        });

        jbBuscar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/pesquisar.png"))); // NOI18N
        jbBuscar.setText("Buscar");
        jbBuscar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscarActionPerformed(evt);
            }
        });

        jbCancelar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/cancelar.png"))); // NOI18N
        jbCancelar.setText("Cancelar");
        jbCancelar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarActionPerformed(evt);
            }
        });

        jbExcluir.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/delete02.png"))); // NOI18N
        jbExcluir.setText("Excluir");
        jbExcluir.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbExcluir.setEnabled(false);
        jbExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbExcluirActionPerformed(evt);
            }
        });

        jbAlterar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/reload.png"))); // NOI18N
        jbAlterar.setText("Alterar");
        jbAlterar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbAlterar.setEnabled(false);
        jbAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAlterarActionPerformed(evt);
            }
        });

        jbImprimirPerfilCompleto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbImprimirPerfilCompleto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imprimir.png"))); // NOI18N
        jbImprimirPerfilCompleto.setText("Perfil Completo");
        jbImprimirPerfilCompleto.setToolTipText("Impressão do Perfil Completo do Aluno!");
        jbImprimirPerfilCompleto.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbImprimirPerfilCompleto.setEnabled(false);
        jbImprimirPerfilCompleto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbImprimirPerfilCompletoActionPerformed(evt);
            }
        });

        jbTransferencia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbTransferencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/transferencia_16x16.png"))); // NOI18N
        jbTransferencia.setText("Transferir");
        jbTransferencia.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbTransferencia.setEnabled(false);
        jbTransferencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbTransferenciaActionPerformed(evt);
            }
        });

        jbImprimir.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imprimir.png"))); // NOI18N
        jbImprimir.setText("Imprimir");
        jbImprimir.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbImprimir.setEnabled(false);
        jbImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpBotoesLayout = new javax.swing.GroupLayout(jpBotoes);
        jpBotoes.setLayout(jpBotoesLayout);
        jpBotoesLayout.setHorizontalGroup(
            jpBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBotoesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbTransferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jbImprimirPerfilCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpBotoesLayout.setVerticalGroup(
            jpBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbImprimirPerfilCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbTransferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jpCadastroAluno.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cadastro Aluno", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Nome: ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Matrícula: ");

        jtfNomeAluno.setBackground(new java.awt.Color(204, 204, 255));
        jtfNomeAluno.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfNomeAluno.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfNomeAluno.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jtfNomeAluno.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfNomeAlunoFocusLost(evt);
            }
        });

        jtfMatriculaAluno.setBackground(new java.awt.Color(204, 204, 255));
        jtfMatriculaAluno.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfMatriculaAluno.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfMatriculaAluno.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfMatriculaAlunoFocusLost(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Data de Nascimento: ");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Nome do CMEI: ");

        jcbNomeEntidade.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jcbNomeEntidade.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jcbNomeEntidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbNomeEntidadeActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Número de Vagas: ");

        jtfVagasDisponiveis.setText("0");
        jtfVagasDisponiveis.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Data de Cadastro: ");

        jDateChooserNascimento.setBackground(new java.awt.Color(204, 204, 255));
        jDateChooserNascimento.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, new java.awt.Color(204, 204, 255)));
        jDateChooserNascimento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jDateChooserNascimento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jDateChooserNascimentoFocusLost(evt);
            }
        });

        jtfEndereco.setBackground(new java.awt.Color(204, 204, 255));
        jtfEndereco.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfEndereco.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfEndereco.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfEnderecoFocusLost(evt);
            }
        });

        jtfNumeroEndereco.setBackground(new java.awt.Color(204, 204, 255));
        jtfNumeroEndereco.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfNumeroEndereco.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfNumeroEndereco.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfNumeroEnderecoFocusLost(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("Bairro: ");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel19.setText("UF: ");

        jcbUf.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jcbUf.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PE", "PR", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
        jcbUf.setSelectedIndex(16);
        jcbUf.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel20.setText("Município: ");

        jtfBairro.setBackground(new java.awt.Color(204, 204, 255));
        jtfBairro.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfBairro.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfBairro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfBairroFocusLost(evt);
            }
        });

        jtfMunicipio.setBackground(new java.awt.Color(204, 204, 255));
        jtfMunicipio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfMunicipio.setText("CASCAVEL");
        jtfMunicipio.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfMunicipio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfMunicipioFocusLost(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("Número: ");

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel21.setText("Telefone: ");

        jftfTelefone.setBackground(new java.awt.Color(204, 204, 255));
        jftfTelefone.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jftfTelefone.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel23.setText("Celular 1: ");

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel24.setText("Celular 2: ");

        jftfCelular1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jftfCelular1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jftfCelular2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jftfCelular2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Seleção de Modalidade", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btgSelecaoModalidade.add(jrbBercario);
        jrbBercario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jrbBercario.setText("Berçário");
        jrbBercario.setToolTipText("Berçário: 0 a 1 ano");
        jrbBercario.setEnabled(false);
        jrbBercario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbBercarioActionPerformed(evt);
            }
        });

        btgSelecaoModalidade.add(jrbMaternal1);
        jrbMaternal1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jrbMaternal1.setText("Maternal I");
        jrbMaternal1.setToolTipText("Maternal I: 1 a 2 anos");
        jrbMaternal1.setEnabled(false);
        jrbMaternal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbMaternal1ActionPerformed(evt);
            }
        });

        btgSelecaoModalidade.add(jrbMaternal2);
        jrbMaternal2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jrbMaternal2.setText("Maternal II");
        jrbMaternal2.setToolTipText("Maternal II: 2 anos");
        jrbMaternal2.setEnabled(false);
        jrbMaternal2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbMaternal2ActionPerformed(evt);
            }
        });

        btgSelecaoModalidade.add(jrbPre1);
        jrbPre1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jrbPre1.setText("Pré I");
        jrbPre1.setToolTipText("Maternal III: 3 anos");
        jrbPre1.setEnabled(false);
        jrbPre1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbPre1ActionPerformed(evt);
            }
        });

        btgSelecaoModalidade.add(jrbPre2);
        jrbPre2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jrbPre2.setText("Pré II");
        jrbPre2.setToolTipText("Pré II: 4 anos");
        jrbPre2.setEnabled(false);
        jrbPre2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbPre2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jrbBercario)
                    .addComponent(jrbMaternal1)
                    .addComponent(jrbMaternal2))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jrbPre2)
                    .addComponent(jrbPre1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jrbBercario)
                    .addComponent(jrbPre1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jrbMaternal1)
                    .addComponent(jrbPre2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jrbMaternal2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jlbTipoCadastro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlbTipoCadastro.setText("Tipo de Cadastro:");

        jpSelecaoMatricula.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Seleção Matrícula", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jpSelecaoMatricula.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btgSelecaoMatricula.add(jrbMatricula);
        jrbMatricula.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jrbMatricula.setText("Matrícula");
        jrbMatricula.setEnabled(false);
        jrbMatricula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbMatriculaActionPerformed(evt);
            }
        });

        btgSelecaoMatricula.add(jrbFilaEspera);
        jrbFilaEspera.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jrbFilaEspera.setText("Fila de Espera");
        jrbFilaEspera.setEnabled(false);
        jrbFilaEspera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbFilaEsperaActionPerformed(evt);
            }
        });

        btgSelecaoMatricula.add(jrbTransferencia);
        jrbTransferencia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jrbTransferencia.setText("Transferência");
        jrbTransferencia.setEnabled(false);
        jrbTransferencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbTransferenciaActionPerformed(evt);
            }
        });

        jDateChooserTransferencia.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jDateChooserTransferencia.setEnabled(false);
        jDateChooserTransferencia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel59.setText("Hora: ");

        jftfHoraSolicitacaoTransferencia.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jftfHoraSolicitacaoTransferencia.setEnabled(false);
        jftfHoraSolicitacaoTransferencia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jpSelecaoMatriculaLayout = new javax.swing.GroupLayout(jpSelecaoMatricula);
        jpSelecaoMatricula.setLayout(jpSelecaoMatriculaLayout);
        jpSelecaoMatriculaLayout.setHorizontalGroup(
            jpSelecaoMatriculaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpSelecaoMatriculaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpSelecaoMatriculaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jrbMatricula)
                    .addComponent(jrbFilaEspera))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jpSelecaoMatriculaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jpSelecaoMatriculaLayout.createSequentialGroup()
                        .addComponent(jLabel59)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jftfHoraSolicitacaoTransferencia))
                    .addComponent(jrbTransferencia)
                    .addComponent(jDateChooserTransferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jpSelecaoMatriculaLayout.setVerticalGroup(
            jpSelecaoMatriculaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpSelecaoMatriculaLayout.createSequentialGroup()
                .addGroup(jpSelecaoMatriculaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jrbMatricula)
                    .addComponent(jrbTransferencia))
                .addGap(4, 4, 4)
                .addGroup(jpSelecaoMatriculaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jrbFilaEspera)
                    .addComponent(jDateChooserTransferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpSelecaoMatriculaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59)
                    .addComponent(jftfHoraSolicitacaoTransferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Sexo: ");

        jcbSexo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jcbSexo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "M", "F" }));
        jcbSexo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("Endereco: ");

        jLabel47.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel47.setText("Naturalidade : ");

        jtfNaturalidade.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfNaturalidade.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfNaturalidade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfNaturalidadeFocusLost(evt);
            }
        });

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel48.setText("Número Identificador da Copel: ");

        jftfNumeroIdentificadorCopel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jftfNumeroIdentificadorCopel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jDateChooserCadastro.setBackground(new java.awt.Color(204, 204, 255));
        jDateChooserCadastro.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, new java.awt.Color(204, 204, 255)));
        jDateChooserCadastro.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jpSelecaoDesistencia.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Seleção Desistência", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jpSelecaoDesistencia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btgSelecaoMatricula.add(jrbDesistencia);
        jrbDesistencia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jrbDesistencia.setText("Desistência em:");
        jrbDesistencia.setEnabled(false);
        jrbDesistencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbDesistenciaActionPerformed(evt);
            }
        });

        jDateChooserDesistencia.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jDateChooserDesistencia.setEnabled(false);
        jDateChooserDesistencia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jpSelecaoDesistenciaLayout = new javax.swing.GroupLayout(jpSelecaoDesistencia);
        jpSelecaoDesistencia.setLayout(jpSelecaoDesistenciaLayout);
        jpSelecaoDesistenciaLayout.setHorizontalGroup(
            jpSelecaoDesistenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpSelecaoDesistenciaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpSelecaoDesistenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooserDesistencia, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                    .addComponent(jrbDesistencia))
                .addContainerGap())
        );
        jpSelecaoDesistenciaLayout.setVerticalGroup(
            jpSelecaoDesistenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpSelecaoDesistenciaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jrbDesistencia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jDateChooserDesistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpControleEgresso.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Controle de Egresso", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jpControleEgresso.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btgSelecaoMatricula.add(jrbEgresso);
        jrbEgresso.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jrbEgresso.setText("Egresso em:");
        jrbEgresso.setEnabled(false);
        jrbEgresso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbEgressoActionPerformed(evt);
            }
        });

        jDateChooserControleEgresso.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jDateChooserControleEgresso.setEnabled(false);
        jDateChooserControleEgresso.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jpControleEgressoLayout = new javax.swing.GroupLayout(jpControleEgresso);
        jpControleEgresso.setLayout(jpControleEgressoLayout);
        jpControleEgressoLayout.setHorizontalGroup(
            jpControleEgressoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpControleEgressoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpControleEgressoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooserControleEgresso, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jrbEgresso))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpControleEgressoLayout.setVerticalGroup(
            jpControleEgressoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpControleEgressoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jrbEgresso)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jDateChooserControleEgresso, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel33.setText("Nome do CMEI Opção 2: ");

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel53.setText("Nome do CMEI Opção 3: ");

        jcbNomeEntidadeOpcao2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jcbNomeEntidadeOpcao2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jcbNomeEntidadeOpcao2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbNomeEntidadeOpcao2ActionPerformed(evt);
            }
        });

        jcbNomeEntidadeOpcao3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jcbNomeEntidadeOpcao3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jcbNomeEntidadeOpcao3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbNomeEntidadeOpcao3ActionPerformed(evt);
            }
        });

        jLabel54.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel54.setText("Possui Bolsa Família: ");

        jcbBolsaFamilia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jcbBolsaFamilia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NÃO", "SIM" }));
        jcbBolsaFamilia.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jpCadastroAlunoLayout = new javax.swing.GroupLayout(jpCadastroAluno);
        jpCadastroAluno.setLayout(jpCadastroAlunoLayout);
        jpCadastroAlunoLayout.setHorizontalGroup(
            jpCadastroAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadastroAlunoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCadastroAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpCadastroAlunoLayout.createSequentialGroup()
                        .addComponent(jLabel53)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbNomeEntidadeOpcao3, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jpCadastroAlunoLayout.createSequentialGroup()
                        .addGroup(jpCadastroAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpCadastroAlunoLayout.createSequentialGroup()
                                .addGroup(jpCadastroAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpCadastroAlunoLayout.createSequentialGroup()
                                        .addGap(37, 37, 37)
                                        .addGroup(jpCadastroAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel12)
                                            .addComponent(jLabel10)))
                                    .addComponent(jLabel33))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpCadastroAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpCadastroAlunoLayout.createSequentialGroup()
                                        .addComponent(jcbNomeEntidade, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtfVagasDisponiveis, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jDateChooserCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jcbNomeEntidadeOpcao2, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jpCadastroAlunoLayout.createSequentialGroup()
                                .addGroup(jpCadastroAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel48)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpCadastroAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpCadastroAlunoLayout.createSequentialGroup()
                                        .addGroup(jpCadastroAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jpCadastroAlunoLayout.createSequentialGroup()
                                                .addComponent(jDateChooserNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel47)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jtfNaturalidade))
                                            .addComponent(jtfBairro, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jtfEndereco, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jpCadastroAlunoLayout.createSequentialGroup()
                                                .addComponent(jcbUf, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel20)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jtfMunicipio)))
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtfNumeroEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jpCadastroAlunoLayout.createSequentialGroup()
                                        .addGroup(jpCadastroAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jpCadastroAlunoLayout.createSequentialGroup()
                                                .addGroup(jpCadastroAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(jftfNumeroIdentificadorCopel, javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jftfCelular2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel54)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jcbBolsaFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jpCadastroAlunoLayout.createSequentialGroup()
                                                .addComponent(jftfTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel23)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jftfCelular1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jpCadastroAlunoLayout.createSequentialGroup()
                                                .addComponent(jtfMatriculaAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel16)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jcbSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jtfNomeAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 656, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(jpCadastroAlunoLayout.createSequentialGroup()
                                .addGroup(jpCadastroAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jlbTipoCadastro)
                                    .addGroup(jpCadastroAlunoLayout.createSequentialGroup()
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jpSelecaoMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jpSelecaoDesistencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jpControleEgresso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(1, 1, 1)))
                        .addContainerGap())))
        );
        jpCadastroAlunoLayout.setVerticalGroup(
            jpCadastroAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadastroAlunoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCadastroAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jDateChooserCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jcbNomeEntidade, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfVagasDisponiveis, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbNomeEntidadeOpcao2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbNomeEntidadeOpcao3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel53))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpCadastroAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpControleEgresso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpSelecaoDesistencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpSelecaoMatricula, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlbTipoCadastro)
                .addGap(6, 6, 6)
                .addGroup(jpCadastroAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtfNomeAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtfMatriculaAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jcbSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jDateChooserNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel47)
                    .addComponent(jtfNaturalidade, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jtfEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jtfNumeroEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jtfBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jcbUf, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jtfMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jftfTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(jftfCelular1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jftfCelular2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(jftfNumeroIdentificadorCopel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54)
                    .addComponent(jcbBolsaFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout jpCadAlunoLayout = new javax.swing.GroupLayout(jpCadAluno);
        jpCadAluno.setLayout(jpCadAlunoLayout);
        jpCadAlunoLayout.setHorizontalGroup(
            jpCadAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadAlunoLayout.createSequentialGroup()
                .addComponent(jpCadastroAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 855, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );
        jpCadAlunoLayout.setVerticalGroup(
            jpCadAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpCadastroAluno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jtpCadastroAluno.addTab("Cadastro Aluno", jpCadAluno);

        jpCadastroPais.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cadastro Pais", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Filiação Pai: ");

        jtfFiliacaoPai.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfFiliacaoPai.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfFiliacaoPai.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfFiliacaoPaiFocusLost(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("CPF do Pai: ");

        jftfCpfPai.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jftfCpfPai.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jftfCpfPai.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jftfCpfPaiFocusLost(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("RG do Pai: ");

        jftfRgPai.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jftfRgPai.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jftfRgPai.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jftfRgPaiFocusLost(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("Data de Nascimento(Pai): ");

        jDateChooserNascimentoPai.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jDateChooserNascimentoPai.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel30.setText("Profissão(Pai): ");

        jtfProfissaoPai.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfProfissaoPai.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfProfissaoPai.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfProfissaoPaiFocusLost(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel22.setText("Telefone Comercial(Pai): ");

        jftfTelefoneComercialPai.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jftfTelefoneComercialPai.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("Endereço Comercial(Pai): ");

        jtfEnderecoComercialPai.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfEnderecoComercialPai.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfEnderecoComercialPai.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfEnderecoComercialPaiFocusLost(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel25.setText("Número: ");

        jtfNumeroEnderecoComercialPai.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfNumeroEnderecoComercialPai.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfNumeroEnderecoComercialPai.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfNumeroEnderecoComercialPaiFocusLost(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Filiação Mãe: ");

        jtfFiliacaoMae.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfFiliacaoMae.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfFiliacaoMae.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfFiliacaoMaeFocusLost(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("CPF da Mãe: ");

        jftfCpfMae.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jftfCpfMae.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jftfCpfMae.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jftfCpfMaeFocusLost(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("RG da Mãe:");

        jftfRgMae.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jftfRgMae.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel27.setText("Data de Nascimento(Mãe): ");

        jDateChooserNascimentoMae.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jDateChooserNascimentoMae.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel31.setText("Profissão(Mãe): ");

        jtfProfissaoMae.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfProfissaoMae.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfProfissaoMae.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfProfissaoMaeFocusLost(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel26.setText("Telefone Comercial(Mãe): ");

        jftfTelefoneComercialMae.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jftfTelefoneComercialMae.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel28.setText("Endereço Comercial(Mãe): ");

        jtfEnderecoComercialMae.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfEnderecoComercialMae.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfEnderecoComercialMae.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfEnderecoComercialMaeFocusLost(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel29.setText("Número: ");

        jtfNumeroEnderecoComercialMae.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfNumeroEnderecoComercialMae.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfNumeroEnderecoComercialMae.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfNumeroEnderecoComercialMaeFocusLost(evt);
            }
        });

        jLabel55.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel55.setText("Passporte: ");

        jLabel56.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel56.setText("Passporte: ");

        jftfPassaportePai.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jftfPassaportePai.setToolTipText("Este campo é somente para estrangeiros!");

        jftfPassaporteMae.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jftfPassaporteMae.setToolTipText("Este campo é somente para estrangeiros!");

        jLabel57.setText("(Modelo: AA0000000)");

        jLabel58.setText("(Modelo: AA0000000)");

        javax.swing.GroupLayout jpCadastroPaisLayout = new javax.swing.GroupLayout(jpCadastroPais);
        jpCadastroPais.setLayout(jpCadastroPaisLayout);
        jpCadastroPaisLayout.setHorizontalGroup(
            jpCadastroPaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadastroPaisLayout.createSequentialGroup()
                .addGroup(jpCadastroPaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel56)
                    .addComponent(jLabel55)
                    .addComponent(jLabel28)
                    .addComponent(jLabel7)
                    .addComponent(jLabel4)
                    .addComponent(jLabel18)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroPaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfFiliacaoPai, javax.swing.GroupLayout.PREFERRED_SIZE, 689, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpCadastroPaisLayout.createSequentialGroup()
                        .addGap(567, 567, 567)
                        .addComponent(jDateChooserNascimentoPai, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpCadastroPaisLayout.createSequentialGroup()
                        .addComponent(jtfProfissaoPai, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jftfTelefoneComercialPai, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpCadastroPaisLayout.createSequentialGroup()
                        .addComponent(jtfEnderecoComercialPai, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfNumeroEnderecoComercialPai, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jtfFiliacaoMae, javax.swing.GroupLayout.PREFERRED_SIZE, 689, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpCadastroPaisLayout.createSequentialGroup()
                        .addComponent(jftfCpfMae, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jftfRgMae, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooserNascimentoMae, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpCadastroPaisLayout.createSequentialGroup()
                        .addComponent(jtfProfissaoMae, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jftfTelefoneComercialMae, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpCadastroPaisLayout.createSequentialGroup()
                        .addComponent(jtfEnderecoComercialMae, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfNumeroEnderecoComercialMae, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpCadastroPaisLayout.createSequentialGroup()
                        .addGroup(jpCadastroPaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jftfPassaportePai, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jftfCpfPai, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jpCadastroPaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpCadastroPaisLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jftfRgPai, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel17))
                            .addComponent(jLabel57)))
                    .addGroup(jpCadastroPaisLayout.createSequentialGroup()
                        .addComponent(jftfPassaporteMae, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel58)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpCadastroPaisLayout.setVerticalGroup(
            jpCadastroPaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadastroPaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCadastroPaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtfFiliacaoPai, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroPaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpCadastroPaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(jLabel6)
                        .addComponent(jftfCpfPai, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jftfRgPai, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17))
                    .addComponent(jDateChooserNascimentoPai, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroPaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(jftfPassaportePai, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroPaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jtfProfissaoPai, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(jftfTelefoneComercialPai, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroPaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jtfEnderecoComercialPai, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(jtfNumeroEnderecoComercialPai, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroPaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtfFiliacaoMae, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroPaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpCadastroPaisLayout.createSequentialGroup()
                        .addGroup(jpCadastroPaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jftfCpfMae, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jftfRgMae, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpCadastroPaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel56)
                            .addComponent(jftfPassaporteMae, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel58))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpCadastroPaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(jtfProfissaoMae, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26)
                            .addComponent(jftfTelefoneComercialMae, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpCadastroPaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(jtfEnderecoComercialMae, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29)
                            .addComponent(jtfNumeroEnderecoComercialMae, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jDateChooserNascimentoMae, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpCadPaisLayout = new javax.swing.GroupLayout(jpCadPais);
        jpCadPais.setLayout(jpCadPaisLayout);
        jpCadPaisLayout.setHorizontalGroup(
            jpCadPaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpCadPaisLayout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addComponent(jpCadastroPais, javax.swing.GroupLayout.PREFERRED_SIZE, 854, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jpCadPaisLayout.setVerticalGroup(
            jpCadPaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadPaisLayout.createSequentialGroup()
                .addComponent(jpCadastroPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 233, Short.MAX_VALUE))
        );

        jtpCadastroAluno.addTab("Cadastro Pais", jpCadPais);

        jpBuscaFuncaoCargo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Certidão de Nascimento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel32.setText("Livro: ");

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel34.setText("Folha: ");

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel35.setText("Termo: ");

        jtfLivroCertidaoNascimento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfLivroCertidaoNascimento.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfLivroCertidaoNascimento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfLivroCertidaoNascimentoFocusLost(evt);
            }
        });

        jtfFolhaCertidaoNascimento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfFolhaCertidaoNascimento.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfFolhaCertidaoNascimento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfFolhaCertidaoNascimentoFocusLost(evt);
            }
        });

        jtfTermoCertidaoNascimento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfTermoCertidaoNascimento.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfTermoCertidaoNascimento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfTermoCertidaoNascimentoFocusLost(evt);
            }
        });

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel49.setText("Número da Matrícula da Certidão: ");

        jftfMatriculaCertidao.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jftfMatriculaCertidao.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jftfMatriculaCertidao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jftfMatriculaCertidaoFocusLost(evt);
            }
        });

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel50.setText("Ano de Registro: ");

        jtfAnoRegistroCertidaoNascimento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfAnoRegistroCertidaoNascimento.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfAnoRegistroCertidaoNascimento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfAnoRegistroCertidaoNascimentoFocusLost(evt);
            }
        });

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel51.setText("Dígito Verificador: ");

        jtfDigitoVerificadorCertidaoNascimento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfDigitoVerificadorCertidaoNascimento.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfDigitoVerificadorCertidaoNascimento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfDigitoVerificadorCertidaoNascimentoFocusLost(evt);
            }
        });

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel52.setText("Cartório: ");

        jtfCartorioCertidaoNascimento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfCartorioCertidaoNascimento.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfCartorioCertidaoNascimento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfCartorioCertidaoNascimentoFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jpBuscaFuncaoCargoLayout = new javax.swing.GroupLayout(jpBuscaFuncaoCargo);
        jpBuscaFuncaoCargo.setLayout(jpBuscaFuncaoCargoLayout);
        jpBuscaFuncaoCargoLayout.setHorizontalGroup(
            jpBuscaFuncaoCargoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBuscaFuncaoCargoLayout.createSequentialGroup()
                .addGroup(jpBuscaFuncaoCargoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpBuscaFuncaoCargoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel49)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jftfMatriculaCertidao, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpBuscaFuncaoCargoLayout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addGroup(jpBuscaFuncaoCargoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel34)
                            .addComponent(jLabel32)
                            .addComponent(jLabel50)
                            .addComponent(jLabel35)
                            .addComponent(jLabel51)
                            .addComponent(jLabel52))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpBuscaFuncaoCargoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfLivroCertidaoNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfFolhaCertidaoNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfTermoCertidaoNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfAnoRegistroCertidaoNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfDigitoVerificadorCertidaoNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfCartorioCertidaoNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(102, Short.MAX_VALUE))
        );
        jpBuscaFuncaoCargoLayout.setVerticalGroup(
            jpBuscaFuncaoCargoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBuscaFuncaoCargoLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jpBuscaFuncaoCargoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(jftfMatriculaCertidao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jpBuscaFuncaoCargoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(jtfCartorioCertidaoNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBuscaFuncaoCargoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(jtfAnoRegistroCertidaoNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBuscaFuncaoCargoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jtfLivroCertidaoNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBuscaFuncaoCargoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(jtfFolhaCertidaoNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBuscaFuncaoCargoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(jtfTermoCertidaoNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBuscaFuncaoCargoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(jtfDigitoVerificadorCertidaoNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
        );

        javax.swing.GroupLayout jpCadCertidaoNascimentoLayout = new javax.swing.GroupLayout(jpCadCertidaoNascimento);
        jpCadCertidaoNascimento.setLayout(jpCadCertidaoNascimentoLayout);
        jpCadCertidaoNascimentoLayout.setHorizontalGroup(
            jpCadCertidaoNascimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadCertidaoNascimentoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpBuscaFuncaoCargo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpCadCertidaoNascimentoLayout.setVerticalGroup(
            jpCadCertidaoNascimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadCertidaoNascimentoLayout.createSequentialGroup()
                .addComponent(jpBuscaFuncaoCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 280, Short.MAX_VALUE))
        );

        jtpCadastroAluno.addTab("Cadastro Certidão de Nascimento", jpCadCertidaoNascimento);

        jpCadastroResponsavel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cadastro Responsável", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel36.setText("Nome Responsável: ");

        jtfNomeResponsavel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfNomeResponsavel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfNomeResponsavel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfNomeResponsavelFocusLost(evt);
            }
        });

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel37.setText("CPF : ");

        jftfCpfResponsavel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jftfCpfResponsavel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jftfCpfResponsavel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jftfCpfResponsavelFocusLost(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel38.setText("RG : ");

        jftfRgResponsavel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jftfRgResponsavel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel39.setText("Data de Nascimento : ");

        jDateChooserNascimentoResponsavel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jDateChooserNascimentoResponsavel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel40.setText("Profissão : ");

        jtfProfissaoResponsavel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfProfissaoResponsavel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfProfissaoResponsavel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfProfissaoResponsavelFocusLost(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel41.setText("Telefone : ");

        jftfTelefoneResponsavel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jftfTelefoneResponsavel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel42.setText("Endereço : ");

        jtfEnderecoResponsavel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfEnderecoResponsavel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfEnderecoResponsavel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfEnderecoResponsavelFocusLost(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel43.setText("Número : ");

        jtfNumeroEnderecoResponsavel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfNumeroEnderecoResponsavel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfNumeroEnderecoResponsavel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfNumeroEnderecoResponsavelFocusLost(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel44.setText("Endereço Comercial : ");

        jtfEnderecoComercialResponsavel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfEnderecoComercialResponsavel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfEnderecoComercialResponsavel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfEnderecoComercialResponsavelFocusLost(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel45.setText("Número : ");

        jtfNumeroEnderecoComercialResponsavel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfNumeroEnderecoComercialResponsavel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfNumeroEnderecoComercialResponsavel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfNumeroEnderecoComercialResponsavelFocusLost(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel46.setText("Telefone Comercial : ");

        jftfTelefoneComercialResponsavel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jftfTelefoneComercialResponsavel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jpCadastroResponsavelLayout = new javax.swing.GroupLayout(jpCadastroResponsavel);
        jpCadastroResponsavel.setLayout(jpCadastroResponsavelLayout);
        jpCadastroResponsavelLayout.setHorizontalGroup(
            jpCadastroResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadastroResponsavelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jpCadastroResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel46)
                    .addComponent(jLabel41)
                    .addComponent(jLabel44)
                    .addComponent(jLabel42)
                    .addComponent(jLabel37)
                    .addComponent(jLabel36)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jftfTelefoneComercialResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpCadastroResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jpCadastroResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpCadastroResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jtfNomeResponsavel)
                                .addGroup(jpCadastroResponsavelLayout.createSequentialGroup()
                                    .addComponent(jftfCpfResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel38)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jftfRgResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel39)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jDateChooserNascimentoResponsavel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpCadastroResponsavelLayout.createSequentialGroup()
                                    .addComponent(jtfProfissaoResponsavel)
                                    .addGap(238, 238, 238)))
                            .addGroup(jpCadastroResponsavelLayout.createSequentialGroup()
                                .addGroup(jpCadastroResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jtfEnderecoComercialResponsavel, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtfEnderecoResponsavel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jpCadastroResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel43)
                                    .addComponent(jLabel45))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpCadastroResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jtfNumeroEnderecoComercialResponsavel)
                                    .addComponent(jtfNumeroEnderecoResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addComponent(jftfTelefoneResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(126, 126, 126))
        );
        jpCadastroResponsavelLayout.setVerticalGroup(
            jpCadastroResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadastroResponsavelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCadastroResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(jtfNomeResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpCadastroResponsavelLayout.createSequentialGroup()
                        .addGroup(jpCadastroResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel37)
                            .addComponent(jLabel38)
                            .addComponent(jftfCpfResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jftfRgResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel39))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpCadastroResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel40)
                            .addComponent(jtfProfissaoResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jDateChooserNascimentoResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(jtfEnderecoResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43)
                    .addComponent(jtfNumeroEnderecoResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(jtfEnderecoComercialResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45)
                    .addComponent(jtfNumeroEnderecoComercialResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(jftfTelefoneResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(jftfTelefoneComercialResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpCadResponsavelLayout = new javax.swing.GroupLayout(jpCadResponsavel);
        jpCadResponsavel.setLayout(jpCadResponsavelLayout);
        jpCadResponsavelLayout.setHorizontalGroup(
            jpCadResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadResponsavelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpCadastroResponsavel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpCadResponsavelLayout.setVerticalGroup(
            jpCadResponsavelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadResponsavelLayout.createSequentialGroup()
                .addComponent(jpCadastroResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(321, Short.MAX_VALUE))
        );

        jtpCadastroAluno.addTab("Cadastro Responsável", jpCadResponsavel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpCadastroAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jpBotoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jtpCadastroAluno)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalvarActionPerformed

        iniciaEventoBotaoSalvar();
        
    }//GEN-LAST:event_jbSalvarActionPerformed

    private void jbBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscarActionPerformed
        
        cancelar();
        
        if (jdba == null) {
            jdba = new jdBuscaAluno(null, true);
        }

        jdba.setTipoCadastroUsuario(tipoCadastroUsuario);
        jdba.setVisible(true);

        setAlunoBuscado(jdba.getAluno());
        jdba.setAluno();
    }//GEN-LAST:event_jbBuscarActionPerformed

    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
        cancelar();
}//GEN-LAST:event_jbCancelarActionPerformed

    private void jbExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbExcluirActionPerformed
        excluir();
}//GEN-LAST:event_jbExcluirActionPerformed

    private void jbAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAlterarActionPerformed

        abilitaCampos();

        jrbDesistencia.setEnabled(true);
        jrbEgresso.setEnabled(true);

        jbAlterar.setEnabled(false);

        opcao = "alterar";

        if (tipoCadastro.equals("TRANSFERENCIA")) {
           jbTransferencia.setEnabled(true);
        } else {
            jbSalvar.setEnabled(true);
        }
                
    }//GEN-LAST:event_jbAlterarActionPerformed

    private void jbImprimirPerfilCompletoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbImprimirPerfilCompletoActionPerformed

        //ADOGeral ag = new ADOGeral();
        Conexao conexao = new Conexao();
        HashMap parametros = new HashMap();
        try {
            //System.out.println(codigoRegistro);
            //parametros.put("titulo", "Titulo Relatorio");
            parametros.put("codigoAluno", new Integer(codigo));
            //parametros.put("codigo", lista.get(0).getNome();
            //ADOGeral.conectar();
            conexao.getConexao();
        } catch (ExcCadastro ex) {
            Logger.getLogger(jfCadastroCmei.class.getName()).log(Level.SEVERE, null, ex);
        }

        //        JRDataSource jrds = new JRBeanCollectionDataSource(lista);

        try {

            //jReport = JasperCompileManager.compileReport("E:/LUIZ/PROJETOS JAVA/CORPORATIVO/m2GestaoClinicas/FichaPaciente.jrxml");
            //jPrint = JasperFillManager.fillReport("C:/m2GestaoClinicas/FichaPaciente.jasper", parametros, ADOGeral.getConexao());
            jPrint = JasperFillManager.fillReport("AlunoCompleto.jasper", parametros, conexao.getConexao());
            //jPrint = JasperFillManager.fillReport(jReport, parametros, jrds);

            //if (!(jViewer == null)) {
            //jViewer = new JasperViewer(jPrint, false);
            //jViewer.setExtendedState(JFrame.MAXIMIZED_BOTH);
            jViewer.viewReport(jPrint, false);
            //}

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jbImprimirPerfilCompletoActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        fechar();
    }//GEN-LAST:event_formWindowClosing

    private void jcbNomeEntidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbNomeEntidadeActionPerformed
        buscaCmei();
    }//GEN-LAST:event_jcbNomeEntidadeActionPerformed

    private void jrbMatriculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbMatriculaActionPerformed

        tipoCadastro = "MATRICULA";
        jlbTipoCadastro.setText("O Cadastro será feito para Matrícula!");
        jDateChooserControleEgresso.setDate(null);
        jDateChooserDesistencia.setDate(null);
        jDateChooserTransferencia.setDate(null);
        jbSalvar.setEnabled(true);
        jbTransferencia.setEnabled(false);

    }//GEN-LAST:event_jrbMatriculaActionPerformed

    private void jrbFilaEsperaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbFilaEsperaActionPerformed

        tipoCadastro = "FILA";
        jlbTipoCadastro.setText("O Cadastro será feito para Fila de Espera!");
        jDateChooserControleEgresso.setDate(null);
        jDateChooserDesistencia.setDate(null);
        jDateChooserTransferencia.setDate(null);
        jbSalvar.setEnabled(true);
        jbTransferencia.setEnabled(false);

    }//GEN-LAST:event_jrbFilaEsperaActionPerformed

    private void jtfMatriculaAlunoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfMatriculaAlunoFocusLost
        
        if (!jtfMatriculaAluno.getText().isEmpty()) {
            iniciaClasseGeral();

            jtfMatriculaAluno.setText(classeGeral.somenteNumero(jtfMatriculaAluno.getText()));
        }
        
    }//GEN-LAST:event_jtfMatriculaAlunoFocusLost

    private void jtfNumeroEnderecoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfNumeroEnderecoFocusLost
        if (!jtfNumeroEndereco.getText().isEmpty()) {
            jtfNumeroEndereco.setText(jtfNumeroEndereco.getText().toUpperCase());
        }
    }//GEN-LAST:event_jtfNumeroEnderecoFocusLost

    private void jtfBairroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfBairroFocusLost
        if (!jtfBairro.getText().isEmpty()) {
            jtfBairro.setText(jtfBairro.getText().toUpperCase());
        }
    }//GEN-LAST:event_jtfBairroFocusLost

    private void jtfMunicipioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfMunicipioFocusLost
        if (!jtfMunicipio.getText().isEmpty()) {
            jtfMunicipio.setText(jtfMunicipio.getText().toUpperCase());
        }
    }//GEN-LAST:event_jtfMunicipioFocusLost

    private void jrbBercarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbBercarioActionPerformed

        if (jrbBercario.isSelected()) {
            modalidade = "BERÇÁRIO";
        }

    }//GEN-LAST:event_jrbBercarioActionPerformed

    private void jrbMaternal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbMaternal1ActionPerformed

        if (jrbMaternal1.isSelected()) {
            modalidade = "MATERNAL I";
        }

    }//GEN-LAST:event_jrbMaternal1ActionPerformed

    private void jrbMaternal2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbMaternal2ActionPerformed

        if (jrbMaternal2.isSelected()) {
            modalidade = "MATERNAL II";
        }

    }//GEN-LAST:event_jrbMaternal2ActionPerformed

    private void jrbPre1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbPre1ActionPerformed

        if (jrbPre1.isSelected()) {
            modalidade = "PRÉ I";
        }

    }//GEN-LAST:event_jrbPre1ActionPerformed

    private void jrbPre2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbPre2ActionPerformed

        if (jrbPre2.isSelected()) {
            modalidade = "PRÉ II";
        }

    }//GEN-LAST:event_jrbPre2ActionPerformed

    private void jftfCpfPaiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jftfCpfPaiFocusLost

        if (!jftfCpfPai.getText().equals("   .   .   -  ")) {
            ValidaCpf validaCpf = new ValidaCpf();

            if (validaCpf.validaCpf(jftfCpfPai.getText())) {
            } else {
                iniciaClasseGeral();
                classeGeral.msgAtencao("CPF Inválido!");
                jftfCpfPai.setText("");
            }
        }

}//GEN-LAST:event_jftfCpfPaiFocusLost

    private void jtfNumeroEnderecoComercialPaiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfNumeroEnderecoComercialPaiFocusLost
        if (!jtfNumeroEnderecoComercialPai.getText().isEmpty()) {
            jtfNumeroEnderecoComercialPai.setText(jtfNumeroEnderecoComercialPai.getText().toUpperCase());
        }
}//GEN-LAST:event_jtfNumeroEnderecoComercialPaiFocusLost

    private void jftfCpfMaeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jftfCpfMaeFocusLost

        if (!jftfCpfMae.getText().equals("   .   .   -  ")) {
            ValidaCpf validaCpf = new ValidaCpf();

            if (validaCpf.validaCpf(jftfCpfMae.getText())) {
            } else {
                iniciaClasseGeral();
                classeGeral.msgAtencao("CPF Inválido!");
                jftfCpfMae.setText("");
            }
        }
        
}//GEN-LAST:event_jftfCpfMaeFocusLost

    private void jtfNumeroEnderecoComercialMaeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfNumeroEnderecoComercialMaeFocusLost
        jtfNumeroEnderecoComercialMae.setText(jtfNumeroEnderecoComercialMae.getText().toUpperCase());
}//GEN-LAST:event_jtfNumeroEnderecoComercialMaeFocusLost

    private void jftfCpfResponsavelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jftfCpfResponsavelFocusLost

        ValidaCpf validaCpf = new ValidaCpf();

        if (validaCpf.validaCpf(jftfCpfResponsavel.getText())) {
        } else {
            iniciaClasseGeral();
            classeGeral.msgAtencao("CPF Inválido!");
            jftfCpfResponsavel.setText("");
        }

    }//GEN-LAST:event_jftfCpfResponsavelFocusLost

    private void jtfNumeroEnderecoResponsavelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfNumeroEnderecoResponsavelFocusLost
        jtfNumeroEnderecoResponsavel.setText(jtfNumeroEnderecoResponsavel.getText().toUpperCase());
    }//GEN-LAST:event_jtfNumeroEnderecoResponsavelFocusLost

    private void jcbSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbSexoActionPerformed
    }//GEN-LAST:event_jcbSexoActionPerformed

    private void jrbDesistenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbDesistenciaActionPerformed
        tipoCadastro = "DESISTENCIA";
        jDateChooserDesistencia.setDate(validaData.getDateAtual());
        jDateChooserControleEgresso.setDate(null);
        jDateChooserTransferencia.setDate(null);
    }//GEN-LAST:event_jrbDesistenciaActionPerformed

    private void jrbEgressoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbEgressoActionPerformed
        tipoCadastro = "EGRESSO";
        jDateChooserControleEgresso.setDate(validaData.getDateAtual());
        jDateChooserDesistencia.setDate(null);
        jDateChooserTransferencia.setDate(null);
    }//GEN-LAST:event_jrbEgressoActionPerformed

    private void jtfNaturalidadeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfNaturalidadeFocusLost
        jtfNaturalidade.setText(jtfNaturalidade.getText().toUpperCase());
    }//GEN-LAST:event_jtfNaturalidadeFocusLost

    private void jtfProfissaoResponsavelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfProfissaoResponsavelFocusLost
        jtfProfissaoResponsavel.setText(jtfProfissaoResponsavel.getText().toUpperCase());
    }//GEN-LAST:event_jtfProfissaoResponsavelFocusLost

    private void jtfNumeroEnderecoComercialResponsavelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfNumeroEnderecoComercialResponsavelFocusLost
        jtfNumeroEnderecoComercialResponsavel.setText(jtfNumeroEnderecoComercialResponsavel.getText().toUpperCase());
    }//GEN-LAST:event_jtfNumeroEnderecoComercialResponsavelFocusLost

    private void jtfProfissaoMaeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfProfissaoMaeFocusLost
        jtfProfissaoMae.setText(jtfProfissaoMae.getText().toUpperCase());
    }//GEN-LAST:event_jtfProfissaoMaeFocusLost

    private void jtfProfissaoPaiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfProfissaoPaiFocusLost
        jtfProfissaoPai.setText(jtfProfissaoPai.getText().toUpperCase());
    }//GEN-LAST:event_jtfProfissaoPaiFocusLost

    private void jtfLivroCertidaoNascimentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfLivroCertidaoNascimentoFocusLost
        jtfLivroCertidaoNascimento.setText(jtfLivroCertidaoNascimento.getText().toUpperCase());
    }//GEN-LAST:event_jtfLivroCertidaoNascimentoFocusLost

    private void jtfFolhaCertidaoNascimentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfFolhaCertidaoNascimentoFocusLost
        jtfFolhaCertidaoNascimento.setText(jtfFolhaCertidaoNascimento.getText().toUpperCase());
    }//GEN-LAST:event_jtfFolhaCertidaoNascimentoFocusLost

    private void jtfTermoCertidaoNascimentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfTermoCertidaoNascimentoFocusLost
        jtfTermoCertidaoNascimento.setText(jtfTermoCertidaoNascimento.getText().toUpperCase());
    }//GEN-LAST:event_jtfTermoCertidaoNascimentoFocusLost

    private void jftfRgPaiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jftfRgPaiFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jftfRgPaiFocusLost

    private void jtfAnoRegistroCertidaoNascimentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfAnoRegistroCertidaoNascimentoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfAnoRegistroCertidaoNascimentoFocusLost

    private void jtfDigitoVerificadorCertidaoNascimentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfDigitoVerificadorCertidaoNascimentoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfDigitoVerificadorCertidaoNascimentoFocusLost

    private void jtfCartorioCertidaoNascimentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfCartorioCertidaoNascimentoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfCartorioCertidaoNascimentoFocusLost

    private void jftfMatriculaCertidaoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jftfMatriculaCertidaoFocusLost
        
        if (!jftfMatriculaCertidao.getText().isEmpty()) {
            iniciaControleNumeroCertidaoNascimento();
        
            controleNumeroCertidaoNascimento.setNumeroCertidaoNascimento(jftfMatriculaCertidao.getText());
            jtfAnoRegistroCertidaoNascimento.setText(controleNumeroCertidaoNascimento.getAnoRegistro());
            jtfLivroCertidaoNascimento.setText(controleNumeroCertidaoNascimento.getLivro());
            jtfFolhaCertidaoNascimento.setText(controleNumeroCertidaoNascimento.getFolha());
            jtfTermoCertidaoNascimento.setText(controleNumeroCertidaoNascimento.getTermo());
            jtfDigitoVerificadorCertidaoNascimento.setText(controleNumeroCertidaoNascimento.getDigitoVerificador());
        }
        
    }//GEN-LAST:event_jftfMatriculaCertidaoFocusLost

    private void jtfNomeAlunoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfNomeAlunoFocusLost
        if (!jtfNomeAluno.getText().isEmpty()) {
            jtfNomeAluno.setText(jtfNomeAluno.getText().toUpperCase());
        }
    }//GEN-LAST:event_jtfNomeAlunoFocusLost

    private void jtfEnderecoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfEnderecoFocusLost
        if (!jtfEndereco.getText().isEmpty()) {
            jtfEndereco.setText(jtfEndereco.getText().toUpperCase());
        }
    }//GEN-LAST:event_jtfEnderecoFocusLost

    private void jtfFiliacaoPaiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfFiliacaoPaiFocusLost
        if (!jtfFiliacaoPai.getText().isEmpty()) {
            jtfFiliacaoPai.setText(jtfFiliacaoPai.getText().toUpperCase());
        }
    }//GEN-LAST:event_jtfFiliacaoPaiFocusLost

    private void jtfEnderecoComercialPaiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfEnderecoComercialPaiFocusLost
        if (!jtfEnderecoComercialPai.getText().isEmpty()) {
            jtfEnderecoComercialPai.setText(jtfEnderecoComercialPai.getText().toUpperCase());
        }
    }//GEN-LAST:event_jtfEnderecoComercialPaiFocusLost

    private void jtfFiliacaoMaeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfFiliacaoMaeFocusLost
        if (!jtfFiliacaoMae.getText().isEmpty()) {
            jtfFiliacaoMae.setText(jtfFiliacaoMae.getText().toUpperCase());
        }
    }//GEN-LAST:event_jtfFiliacaoMaeFocusLost

    private void jtfEnderecoComercialMaeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfEnderecoComercialMaeFocusLost
        if (!jtfEnderecoComercialMae.getText().isEmpty()) {
            jtfEnderecoComercialMae.setText(jtfEnderecoComercialMae.getText().toUpperCase());
        }
    }//GEN-LAST:event_jtfEnderecoComercialMaeFocusLost

    private void jtfNomeResponsavelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfNomeResponsavelFocusLost
        if (!jtfNomeResponsavel.getText().isEmpty()) {
            jtfNomeResponsavel.setText(jtfNomeResponsavel.getText().toUpperCase());
        }
    }//GEN-LAST:event_jtfNomeResponsavelFocusLost

    private void jtfEnderecoResponsavelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfEnderecoResponsavelFocusLost
        if (!jtfEnderecoResponsavel.getText().isEmpty()) {
            jtfEnderecoResponsavel.setText(jtfEnderecoResponsavel.getText().toUpperCase());
        }
    }//GEN-LAST:event_jtfEnderecoResponsavelFocusLost

    private void jtfEnderecoComercialResponsavelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfEnderecoComercialResponsavelFocusLost
        if (!jtfEnderecoComercialResponsavel.getText().isEmpty()) {
            jtfEnderecoComercialResponsavel.setText(jtfEnderecoComercialResponsavel.getText().toUpperCase());
        }
    }//GEN-LAST:event_jtfEnderecoComercialResponsavelFocusLost

    private void jcbNomeEntidadeOpcao2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbNomeEntidadeOpcao2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbNomeEntidadeOpcao2ActionPerformed

    private void jcbNomeEntidadeOpcao3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbNomeEntidadeOpcao3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbNomeEntidadeOpcao3ActionPerformed

    private void jDateChooserNascimentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jDateChooserNascimentoFocusLost
        
        if (jDateChooserNascimento.getDate() != null) {
            
            iniciaVerificadorModalidades();
            String modalidadeVerificada;
            
            modalidadeVerificada = verificadorModalidades.verificaIdadeModalidade(jDateChooserNascimento.getDate());
            
            modalidade = modalidadeVerificada;
            
            switch (modalidadeVerificada) {
                
                case "BERÇÁRIO":
                    jrbBercario.setSelected(true);
                    break;
                    
                case "MATERNAL I":
                    jrbMaternal1.setSelected(true);
                    break;
                    
                case "MATERNAL II":
                    jrbMaternal2.setSelected(true);
                    break;
                    
                case "PRÉ I":
                    jrbPre1.setSelected(true);
                    break;
                    
                case "PRÉ II":
                    jrbPre2.setSelected(true);
                    break;
                    
                case "ANO":
                    jrbPre2.setSelected(true);
                    jrbEgresso.setSelected(true);
                    modalidade = "ANO";
                    break;
                
            }
            
        }
        
    }//GEN-LAST:event_jDateChooserNascimentoFocusLost

    private void jbImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbImprimirActionPerformed
        //ADOGeral ag = new ADOGeral();
        Conexao conexao = new Conexao();
        HashMap parametros = new HashMap();
        try {
            //System.out.println(codigoRegistro);
            //parametros.put("titulo", "Titulo Relatorio");
            parametros.put("codigoAluno", new Integer(codigo));
            //parametros.put("codigo", lista.get(0).getNome();
            //ADOGeral.conectar();
            conexao.getConexao();
        } catch (ExcCadastro ex) {
            Logger.getLogger(jfCadastroCmei.class.getName()).log(Level.SEVERE, null, ex);
        }

        //        JRDataSource jrds = new JRBeanCollectionDataSource(lista);

        try {

            //jReport = JasperCompileManager.compileReport("E:/LUIZ/PROJETOS JAVA/CORPORATIVO/m2GestaoClinicas/FichaPaciente.jrxml");
            //jPrint = JasperFillManager.fillReport("C:/m2GestaoClinicas/FichaPaciente.jasper", parametros, ADOGeral.getConexao());
            jPrint = JasperFillManager.fillReport("Aluno.jasper", parametros, conexao.getConexao());
            //jPrint = JasperFillManager.fillReport(jReport, parametros, jrds);

            //if (!(jViewer == null)) {
            //jViewer = new JasperViewer(jPrint, false);
            //jViewer.setExtendedState(JFrame.MAXIMIZED_BOTH);
            jViewer.viewReport(jPrint, false);
            //}

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jbImprimirActionPerformed

    private void jrbTransferenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbTransferenciaActionPerformed
        
        tipoCadastro = "TRANSFERENCIA";
        jlbTipoCadastro.setText("O Cadastro será feito para Transferência!");
        jDateChooserControleEgresso.setDate(null);
        jDateChooserDesistencia.setDate(null);
        jDateChooserTransferencia.setDate(validaData.getDateAtual());
        
    }//GEN-LAST:event_jrbTransferenciaActionPerformed

    private void jbTransferenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbTransferenciaActionPerformed

        switch (tipoCadastro) {

            case "EGRESSO":
                iniciaClasseGeral();
                classeGeral.msgAtencao("Não é possivel transferir aluno Egresso!");
                break;

            case "DESISTENCIA":
                iniciaClasseGeral();
                classeGeral.msgAtencao("Não é possivel transferir aluno Desistente!");
                break;

            default:
                
                iniciaControleCaracteresEspeciais();
                iniciaTransferenciaAluno();
                
                transferenciaAluno.setCodigoAluno(aluno.getCodigoAluno());
                transferenciaAluno.setCpfMae(jftfCpfMae.getText());
                transferenciaAluno.setCpfPai(jftfCpfPai.getText());
                transferenciaAluno.setDataTransferencia(validaData.getDateAtual());
                transferenciaAluno.setFiliacaoMae(controleCaracteresEspeciais.getCodigoCaracterEpecial(jtfFiliacaoMae.getText()));
                transferenciaAluno.setFiliacaoPai(controleCaracteresEspeciais.getCodigoCaracterEpecial(jtfFiliacaoPai.getText()));
                transferenciaAluno.setMatriculaAluno(jtfMatriculaAluno.getText());
                transferenciaAluno.setMotivoTransferencia("");
                transferenciaAluno.setNomeAluno(controleCaracteresEspeciais.getCodigoCaracterEpecial(jtfNomeAluno.getText()));
                transferenciaAluno.setNomeEntidadeAnterior(jcbNomeEntidade.getSelectedItem().toString());
                transferenciaAluno.setNomeEntidadeAtual(jcbNomeEntidadeOpcao2.getSelectedItem().toString());
                
                dao.inserir(transferenciaAluno, 0);
                
                opcao = "alterar";
                
                jcbNomeEntidade.setSelectedIndex(jcbNomeEntidadeOpcao2.getSelectedIndex());
                jcbNomeEntidadeOpcao2.setSelectedIndex(0);
                jrbMatricula.setSelected(true);
                tipoCadastro = "MATRICULA";
                jDateChooserTransferencia.setDate(null);
                
                iniciaEventoBotaoSalvar();
                
                break;

        }
    }//GEN-LAST:event_jbTransferenciaActionPerformed
    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
     java.awt.EventQueue.invokeLater(new Runnable() {
     public void run() {
     new jfCadastroAluno().setVisible(true);
     }
     });
     }*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btgSelecaoMatricula;
    private javax.swing.ButtonGroup btgSelecaoModalidade;
    private com.toedter.calendar.JDateChooser jDateChooserCadastro;
    private com.toedter.calendar.JDateChooser jDateChooserControleEgresso;
    private com.toedter.calendar.JDateChooser jDateChooserDesistencia;
    private com.toedter.calendar.JDateChooser jDateChooserNascimento;
    private com.toedter.calendar.JDateChooser jDateChooserNascimentoMae;
    private com.toedter.calendar.JDateChooser jDateChooserNascimentoPai;
    private com.toedter.calendar.JDateChooser jDateChooserNascimentoResponsavel;
    private com.toedter.calendar.JDateChooser jDateChooserTransferencia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbAlterar;
    private javax.swing.JButton jbBuscar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbExcluir;
    private javax.swing.JButton jbImprimir;
    private javax.swing.JButton jbImprimirPerfilCompleto;
    private javax.swing.JButton jbSalvar;
    private javax.swing.JButton jbTransferencia;
    private javax.swing.JComboBox jcbBolsaFamilia;
    private javax.swing.JComboBox jcbNomeEntidade;
    private javax.swing.JComboBox jcbNomeEntidadeOpcao2;
    private javax.swing.JComboBox jcbNomeEntidadeOpcao3;
    private javax.swing.JComboBox jcbSexo;
    private javax.swing.JComboBox jcbUf;
    private javax.swing.JFormattedTextField jftfCelular1;
    private javax.swing.JFormattedTextField jftfCelular2;
    private javax.swing.JFormattedTextField jftfCpfMae;
    private javax.swing.JFormattedTextField jftfCpfPai;
    private javax.swing.JFormattedTextField jftfCpfResponsavel;
    private javax.swing.JFormattedTextField jftfHoraSolicitacaoTransferencia;
    private javax.swing.JFormattedTextField jftfMatriculaCertidao;
    private javax.swing.JFormattedTextField jftfNumeroIdentificadorCopel;
    private javax.swing.JFormattedTextField jftfPassaporteMae;
    private javax.swing.JFormattedTextField jftfPassaportePai;
    private javax.swing.JFormattedTextField jftfRgMae;
    private javax.swing.JFormattedTextField jftfRgPai;
    private javax.swing.JFormattedTextField jftfRgResponsavel;
    private javax.swing.JFormattedTextField jftfTelefone;
    private javax.swing.JFormattedTextField jftfTelefoneComercialMae;
    private javax.swing.JFormattedTextField jftfTelefoneComercialPai;
    private javax.swing.JFormattedTextField jftfTelefoneComercialResponsavel;
    private javax.swing.JFormattedTextField jftfTelefoneResponsavel;
    private javax.swing.JLabel jlbTipoCadastro;
    private javax.swing.JPanel jpBotoes;
    private javax.swing.JPanel jpBuscaFuncaoCargo;
    private javax.swing.JPanel jpCadAluno;
    private javax.swing.JPanel jpCadCertidaoNascimento;
    private javax.swing.JPanel jpCadPais;
    private javax.swing.JPanel jpCadResponsavel;
    private javax.swing.JPanel jpCadastroAluno;
    private javax.swing.JPanel jpCadastroPais;
    private javax.swing.JPanel jpCadastroResponsavel;
    private javax.swing.JPanel jpControleEgresso;
    private javax.swing.JPanel jpSelecaoDesistencia;
    private javax.swing.JPanel jpSelecaoMatricula;
    private javax.swing.JRadioButton jrbBercario;
    private javax.swing.JRadioButton jrbDesistencia;
    private javax.swing.JRadioButton jrbEgresso;
    private javax.swing.JRadioButton jrbFilaEspera;
    private javax.swing.JRadioButton jrbMaternal1;
    private javax.swing.JRadioButton jrbMaternal2;
    private javax.swing.JRadioButton jrbMatricula;
    private javax.swing.JRadioButton jrbPre1;
    private javax.swing.JRadioButton jrbPre2;
    private javax.swing.JRadioButton jrbTransferencia;
    private javax.swing.JTextField jtfAnoRegistroCertidaoNascimento;
    private javax.swing.JTextField jtfBairro;
    private javax.swing.JTextField jtfCartorioCertidaoNascimento;
    private javax.swing.JTextField jtfDigitoVerificadorCertidaoNascimento;
    private javax.swing.JTextField jtfEndereco;
    private javax.swing.JTextField jtfEnderecoComercialMae;
    private javax.swing.JTextField jtfEnderecoComercialPai;
    private javax.swing.JTextField jtfEnderecoComercialResponsavel;
    private javax.swing.JTextField jtfEnderecoResponsavel;
    private javax.swing.JTextField jtfFiliacaoMae;
    private javax.swing.JTextField jtfFiliacaoPai;
    private javax.swing.JTextField jtfFolhaCertidaoNascimento;
    private javax.swing.JTextField jtfLivroCertidaoNascimento;
    private javax.swing.JTextField jtfMatriculaAluno;
    private javax.swing.JTextField jtfMunicipio;
    private javax.swing.JTextField jtfNaturalidade;
    private javax.swing.JTextField jtfNomeAluno;
    private javax.swing.JTextField jtfNomeResponsavel;
    private javax.swing.JTextField jtfNumeroEndereco;
    private javax.swing.JTextField jtfNumeroEnderecoComercialMae;
    private javax.swing.JTextField jtfNumeroEnderecoComercialPai;
    private javax.swing.JTextField jtfNumeroEnderecoComercialResponsavel;
    private javax.swing.JTextField jtfNumeroEnderecoResponsavel;
    private javax.swing.JTextField jtfProfissaoMae;
    private javax.swing.JTextField jtfProfissaoPai;
    private javax.swing.JTextField jtfProfissaoResponsavel;
    private javax.swing.JTextField jtfTermoCertidaoNascimento;
    private javax.swing.JTextField jtfVagasDisponiveis;
    private javax.swing.JTabbedPane jtpCadastroAluno;
    // End of variables declaration//GEN-END:variables

//Outros Metodos ===========================================================================================================================================
    public void fechar() {

        if (!jtfNomeAluno.getText().isEmpty() || !jtfBairro.getText().isEmpty() || !jtfEndereco.getText().isEmpty() || !jtfFiliacaoMae.getText().isEmpty() || !jtfFiliacaoPai.getText().isEmpty()
                || !jtfMatriculaAluno.getText().isEmpty() || !jtfMunicipio.getText().equals("CASCAVEL") || !jtfNumeroEndereco.getText().isEmpty()
                || !jftfCelular1.getText().equals("(  )    -    ") || !jftfCelular2.getText().equals("(  )    -    ") || !jftfCpfMae.getText().equals("   .   .   -  ")
                || !jftfCpfPai.getText().equals("   .   .   -  ") || !jftfRgMae.getText().equals("  .   .   - ") || !jftfRgPai.getText().equals("  .   .   - ")
                || !jftfTelefone.getText().equals("(  )    -    ") || !jftfTelefoneComercialPai.getText().equals("(  )    -    ") || jcbUf.getSelectedIndex() != 16
                || jDateChooserNascimento.getDate() != null || !jtfCartorioCertidaoNascimento.getText().isEmpty() || !jftfMatriculaCertidao.getText().equals("                                        ")
                || jcbSexo.getSelectedIndex() != 0 || !jtfNaturalidade.getText().isEmpty() || jcbBolsaFamilia.getSelectedIndex() != 0
                || !jtfNomeResponsavel.getText().isEmpty() || !jftfCpfResponsavel.getText().equals("   .   .   -  ") || !jftfRgResponsavel.getText().equals("  .   .   - ") || jDateChooserNascimentoResponsavel.getDate() != null
                || !jtfProfissaoResponsavel.getText().isEmpty() || !jtfEnderecoResponsavel.getText().isEmpty() || !jtfEnderecoComercialResponsavel.getText().isEmpty()
                || !jtfNumeroEnderecoResponsavel.getText().isEmpty() || !jtfNumeroEnderecoComercialResponsavel.getText().isEmpty() || !jftfTelefoneResponsavel.getText().equals("(  )    -    ")
                || !jftfTelefoneComercialResponsavel.getText().equals("(  )    -    ") || !jftfNumeroIdentificadorCopel.getText().equals("  .   .   - ") || !jftfPassaporteMae.getText().isEmpty()
                || !jftfPassaportePai.getText().isEmpty()) {

            iniciaClasseGeral();
            classeGeral.msgAtencao("Cancele ou Salve o Cadastro em Edição!");

        } else {
            dao = null;
            this.dispose();
        }

    }

    public void cancelar() {

        btgSelecaoModalidade.clearSelection();

        jtfBairro.setText("");
        jtfEndereco.setText("");
        jtfFiliacaoMae.setText("");
        jtfFiliacaoPai.setText("");

        jtfMatriculaAluno.setText("");
        jtfMunicipio.setText("CASCAVEL");
        jtfNaturalidade.setText("");
        jtfNomeAluno.setText("");
        jtfNumeroEndereco.setText("");
        jtfVagasDisponiveis.setText("0");
        jtfProfissaoMae.setText("");
        jtfProfissaoPai.setText("");
        jtfEnderecoComercialMae.setText("");
        jtfEnderecoComercialPai.setText("");
        jtfNumeroEnderecoComercialMae.setText("");
        jtfNumeroEnderecoComercialPai.setText("");
        jftfHoraSolicitacaoTransferencia.setText("");
        jftfHoraSolicitacaoTransferencia.setValue(null);
        jftfNumeroIdentificadorCopel.setText("");
        jftfNumeroIdentificadorCopel.setValue(null);

        jcbSexo.setSelectedIndex(0);
        jcbBolsaFamilia.setSelectedIndex(0);

        if (!tipoCadastroUsuario.equals("GERAL")) {
            jcbNomeEntidade.setSelectedItem(tipoCadastroUsuario);
        } else {
            jcbNomeEntidade.setSelectedIndex(0);
        }

        jcbNomeEntidadeOpcao2.setSelectedIndex(0);
        jcbNomeEntidadeOpcao3.setSelectedIndex(0);
        jcbUf.setSelectedIndex(16);
        jftfCelular1.setText("");
        jftfCelular1.setValue(null);
        jftfCelular2.setText("");
        jftfCelular2.setValue(null);
        jftfCpfMae.setText("");
        jftfCpfMae.setValue(null);
        jftfCpfPai.setText("");
        jftfCpfPai.setValue(null);
        jftfRgMae.setText("");
        jftfRgMae.setValue(null);
        jftfRgPai.setText("");
        jftfRgPai.setValue(null);

        jftfTelefone.setText("");
        jftfTelefone.setValue(null);
        jftfTelefoneComercialPai.setText("");
        jftfTelefoneComercialPai.setValue(null);
        jftfTelefoneComercialMae.setText("");
        jftfTelefoneComercialMae.setValue(null);

        jcbSexo.setSelectedIndex(0);
        jDateChooserCadastro.setDate(validaData.getDateAtual());
        jDateChooserDesistencia.setDate(null);
        jDateChooserControleEgresso.setDate(null);
        jDateChooserNascimento.setDate(null);
        jDateChooserNascimentoMae.setDate(null);
        jDateChooserNascimentoPai.setDate(null);
        jDateChooserTransferencia.setDate(null);

        jftfMatriculaCertidao.setText("");
        jftfMatriculaCertidao.setValue(null);
        jtfAnoRegistroCertidaoNascimento.setText("");
        jtfCartorioCertidaoNascimento.setText("");
        jtfDigitoVerificadorCertidaoNascimento.setText("");
        jtfFolhaCertidaoNascimento.setText("");
        jtfLivroCertidaoNascimento.setText("");
        jtfTermoCertidaoNascimento.setText("");

        jtfNomeResponsavel.setText("");
        jtfEnderecoResponsavel.setText("");
        jtfNumeroEnderecoResponsavel.setText("");
        jftfCpfResponsavel.setText("");
        jftfCpfResponsavel.setValue(null);
        jftfRgResponsavel.setText("");
        jftfRgResponsavel.setValue(null);
        jDateChooserNascimentoResponsavel.setDate(null);
        jtfProfissaoResponsavel.setText("");
        jftfTelefoneResponsavel.setText("");
        jftfTelefoneResponsavel.setValue(null);
        jtfEnderecoComercialResponsavel.setText("");
        jtfNumeroEnderecoComercialResponsavel.setText("");
        jftfTelefoneComercialResponsavel.setText("");
        jftfTelefoneComercialResponsavel.setValue(null);
        jftfPassaporteMae.setText("");
        jftfPassaporteMae.setValue(null);
        jftfPassaportePai.setText("");
        jftfPassaportePai.setValue(null);
        
        jrbBercario.setEnabled(false);
        jrbMaternal1.setEnabled(false);
        jrbMaternal2.setEnabled(false);
        jrbPre1.setEnabled(false);
        jrbPre2.setEnabled(false);

        jrbMatricula.setEnabled(false);
        jrbFilaEspera.setSelected(true);
        jrbDesistencia.setEnabled(false);
        jrbEgresso.setEnabled(false);

        abilitaCampos();

        desabilitaBotoes();

        jcbNomeEntidade.transferFocusBackward();

        opcao = "salvar";
        tipoCadastro = "FILA";
        jlbTipoCadastro.setText("O Cadastro será feito para Fila de Espera!");
        modalidade = "BERÇÁRIO";
        
        aluno = null;

    }

    public void abilitaBotoes() {

        jbImprimir.setEnabled(true);
        jbCancelar.setEnabled(true);
        jbBuscar.setEnabled(true);
        jbImprimirPerfilCompleto.setEnabled(true);

        if (cadastrarAluno == true) {
            jbSalvar.setEnabled(false);
            jbTransferencia.setEnabled(true);
            if (jcbNomeEntidade.getSelectedItem().toString().equals(tipoCadastroUsuario) || jcbNomeEntidadeOpcao2.getSelectedItem().toString().equals(tipoCadastroUsuario) 
                    || jcbNomeEntidadeOpcao3.getSelectedItem().toString().equals(tipoCadastroUsuario) || tipoCadastroUsuario.equals("GERAL")) {
                jbExcluir.setEnabled(true);
                jbAlterar.setEnabled(true);
            } else {
                jbExcluir.setEnabled(false);
                jbAlterar.setEnabled(false);
            }
        } else {
            jbSalvar.setEnabled(false);
            jbExcluir.setEnabled(false);
            jbAlterar.setEnabled(false);
        }

    }

    public void abilitaCampos() {

        jtfBairro.setEditable(true);
        jtfEndereco.setEditable(true);
        jtfMatriculaAluno.setEditable(true);
        jtfMunicipio.setEditable(true);
        jtfNaturalidade.setEditable(true);
        jtfNomeAluno.setEditable(true);
        jtfNumeroEndereco.setEditable(true);
        jcbNomeEntidadeOpcao2.setEnabled(true);
        jcbNomeEntidadeOpcao3.setEnabled(true);
        
        if (tipoCadastroUsuario.equals("GERAL")) {
            jcbNomeEntidade.setEnabled(true);
        }

        jcbBolsaFamilia.setEnabled(true);
        jcbUf.setEnabled(true);
        jcbSexo.setEnabled(true);
        jftfCelular1.setEditable(true);
        jftfCelular2.setEditable(true);
        jftfTelefone.setEditable(true);
        jDateChooserCadastro.setEnabled(true);
        jDateChooserNascimento.setEnabled(true);
        jftfNumeroIdentificadorCopel.setEnabled(true);

        jrbBercario.setEnabled(true);
        jrbMaternal1.setEnabled(true);
        jrbMaternal2.setEnabled(true);
        jrbPre1.setEnabled(true);
        jrbPre2.setEnabled(true);
        jrbFilaEspera.setEnabled(true);
        jrbMatricula.setEnabled(true);
        jrbTransferencia.setEnabled(true);

        jtfFiliacaoMae.setEditable(true);
        jtfFiliacaoPai.setEditable(true);
        jftfCpfMae.setEditable(true);
        jftfCpfPai.setEditable(true);
        jftfRgMae.setEditable(true);
        jftfRgPai.setEditable(true);
        jftfTelefoneComercialPai.setEditable(true);
        jftfTelefoneComercialMae.setEditable(true);
        jtfEnderecoComercialMae.setEditable(true);
        jtfEnderecoComercialPai.setEditable(true);
        jtfNumeroEnderecoComercialMae.setEditable(true);
        jtfNumeroEnderecoComercialPai.setEditable(true);
        jtfProfissaoMae.setEditable(true);
        jtfProfissaoPai.setEditable(true);
        jDateChooserNascimentoMae.setEnabled(true);
        jDateChooserNascimentoPai.setEnabled(true);

        jftfMatriculaCertidao.setEditable(true);
        jtfAnoRegistroCertidaoNascimento.setEditable(true);
        jtfCartorioCertidaoNascimento.setEditable(true);
        jtfDigitoVerificadorCertidaoNascimento.setEditable(true);
        jtfFolhaCertidaoNascimento.setEditable(true);
        jtfLivroCertidaoNascimento.setEditable(true);
        jtfTermoCertidaoNascimento.setEditable(true);
        jftfPassaporteMae.setEditable(true);
        jftfPassaportePai.setEditable(true);

        jtfNomeResponsavel.setEditable(true);
        jftfCpfResponsavel.setEditable(true);
        jftfRgResponsavel.setEditable(true);
        jDateChooserNascimentoResponsavel.setEnabled(true);
        jtfProfissaoResponsavel.setEditable(true);
        jtfEnderecoResponsavel.setEditable(true);
        jtfNumeroEnderecoResponsavel.setEditable(true);
        jtfEnderecoComercialResponsavel.setEditable(true);
        jtfNumeroEnderecoComercialResponsavel.setEditable(true);
        jftfTelefoneResponsavel.setEditable(true);
        jftfTelefoneComercialResponsavel.setEditable(true);

    }

    public void atualizaHora() {
        //jftfHoraSolicitacao.setText(getHoraAtual());
    }
    
    public void desabilitaBotoes() {

        jbImprimir.setEnabled(false);
        jbCancelar.setEnabled(true);
        jbBuscar.setEnabled(true);
        jbImprimirPerfilCompleto.setEnabled(false);
        jbTransferencia.setEnabled(false);

        if (cadastrarAluno == true) {
            jbSalvar.setEnabled(true);
            jbExcluir.setEnabled(false);
            jbAlterar.setEnabled(false);
        } else {
            jbSalvar.setEnabled(false);
            jbExcluir.setEnabled(false);
            jbAlterar.setEnabled(false);
        }

    }

    public void desabilitaCampos() {

        jtfBairro.setEditable(false);
        jtfEndereco.setEditable(false);
        jtfMatriculaAluno.setEditable(false);
        jtfMunicipio.setEditable(false);
        jtfNaturalidade.setEditable(false);
        jtfNomeAluno.setEditable(false);
        jtfNumeroEndereco.setEditable(false);
        jcbBolsaFamilia.setEnabled(false);
        jcbNomeEntidade.setEnabled(false);
        jcbNomeEntidadeOpcao2.setEnabled(false);
        jcbNomeEntidadeOpcao3.setEnabled(false);
        jcbUf.setEnabled(false);
        jcbSexo.setEnabled(false);
        jftfCelular1.setEditable(false);
        jftfCelular2.setEditable(false);
        jftfTelefone.setEditable(false);
        jDateChooserCadastro.setEnabled(false);
        jDateChooserNascimento.setEnabled(false);
        jftfNumeroIdentificadorCopel.setEnabled(false);

        jrbBercario.setEnabled(false);
        jrbMaternal1.setEnabled(false);
        jrbMaternal2.setEnabled(false);
        jrbPre1.setEnabled(false);
        jrbPre2.setEnabled(false);
        jrbFilaEspera.setEnabled(false);
        jrbMatricula.setEnabled(false);
        jrbTransferencia.setEnabled(false);

        jtfFiliacaoMae.setEditable(false);
        jtfFiliacaoPai.setEditable(false);
        jftfCpfMae.setEditable(false);
        jftfCpfPai.setEditable(false);
        jftfRgMae.setEditable(false);
        jftfRgPai.setEditable(false);
        jftfTelefoneComercialPai.setEditable(false);
        jftfTelefoneComercialMae.setEditable(false);
        jtfEnderecoComercialMae.setEditable(false);
        jtfEnderecoComercialPai.setEditable(false);
        jtfNumeroEnderecoComercialMae.setEditable(false);
        jtfNumeroEnderecoComercialPai.setEditable(false);
        jtfProfissaoMae.setEditable(false);
        jtfProfissaoPai.setEditable(false);
        jDateChooserNascimentoMae.setEnabled(false);
        jDateChooserNascimentoPai.setEnabled(false);

        jftfMatriculaCertidao.setEditable(false);
        jtfAnoRegistroCertidaoNascimento.setEditable(false);
        jtfCartorioCertidaoNascimento.setEditable(false);
        jtfDigitoVerificadorCertidaoNascimento.setEditable(false);
        jtfFolhaCertidaoNascimento.setEditable(false);
        jtfLivroCertidaoNascimento.setEditable(false);
        jtfTermoCertidaoNascimento.setEditable(false);
        jftfPassaporteMae.setEditable(false);
        jftfPassaportePai.setEditable(false);

        jtfNomeResponsavel.setEditable(false);
        jftfCpfResponsavel.setEditable(false);
        jftfRgResponsavel.setEditable(false);
        jDateChooserNascimentoResponsavel.setEnabled(false);
        jtfProfissaoResponsavel.setEditable(false);
        jtfEnderecoResponsavel.setEditable(false);
        jtfNumeroEnderecoResponsavel.setEditable(false);
        jtfEnderecoComercialResponsavel.setEditable(false);
        jtfNumeroEnderecoComercialResponsavel.setEditable(false);
        jftfTelefoneResponsavel.setEditable(false);
        jftfTelefoneComercialResponsavel.setEditable(false);

    }
    
    public void iniciaEventoBotaoSalvar() {
        
        if (tipoCadastro.equals("MATRICULA")) {

            if (!jtfNomeAluno.getText().isEmpty() && !jtfMatriculaAluno.getText().isEmpty() && jDateChooserNascimento.getDate() != null
                    && !jtfEndereco.getText().isEmpty() && !jtfNumeroEndereco.getText().isEmpty() && !jtfBairro.getText().isEmpty() && !jtfMunicipio.getText().isEmpty()
                    && !jftfTelefone.getText().equals("(  )    -    ")) {

                if (validaData.validaAno(jDateChooserCadastro.getDate()) && validaData.validaAno(jDateChooserNascimento.getDate())) {

                    procedimentoParaSalvar();

                } else {
                    iniciaClasseGeral();
                    classeGeral.msgAtencao("Atualize os campos, data de cadastro ou data de nascimento, corretamente!");
                }


            } else {
                iniciaClasseGeral();
                classeGeral.msgAtencao("Complete os Campos Obrigatórios, destacados em Azul!");
            }

        } else {

            if (!jtfNomeAluno.getText().isEmpty() && jDateChooserNascimento.getDate() != null
                    && !jtfEndereco.getText().isEmpty() && !jtfNumeroEndereco.getText().isEmpty() && !jtfBairro.getText().isEmpty() && !jtfMunicipio.getText().isEmpty()
                    && !jftfTelefone.getText().equals("(  )    -    ")) {

                if (validaData.validaAno(jDateChooserCadastro.getDate()) && validaData.validaAno(jDateChooserNascimento.getDate())) {

                    procedimentoParaSalvar();

                } else {
                    iniciaClasseGeral();
                    classeGeral.msgAtencao("Atualize os campos, data de cadastro ou data de nascimento, corretamente!");
                }

            } else {
                iniciaClasseGeral();
                classeGeral.msgAtencao("Complete os Campos Obrigatórios, destacados em Azul!");
            }

        }
        
    }
    
    public void preencheComboCmeis() {

        iniciaDao();
        //DAO dao = new DAO();
        List listaCmeis = new ArrayList();

        jcbNomeEntidade.removeAllItems();
        jcbNomeEntidade.addItem("");

        listaCmeis = dao.listarCmeis();

        for (int i = 0; i < listaCmeis.size(); i++) {
            jcbNomeEntidade.addItem(listaCmeis.get(i));
        }

        if (!tipoCadastroUsuario.equals("GERAL")) {
            jcbNomeEntidade.setSelectedItem(tipoCadastroUsuario);
        }

    }
    
    public void preencheComboCmeisOpcao2() {

        iniciaDao();
        //DAO dao = new DAO();
        List listaCmeis = new ArrayList();

        jcbNomeEntidadeOpcao2.removeAllItems();
        jcbNomeEntidadeOpcao2.addItem("");

        listaCmeis = dao.listarCmeis();

        for (int i = 0; i < listaCmeis.size(); i++) {
            jcbNomeEntidadeOpcao2.addItem(listaCmeis.get(i));
        }

    }

    public void preencheComboCmeisOpcao3() {

        iniciaDao();
        //DAO dao = new DAO();
        List listaCmeis = new ArrayList();

        jcbNomeEntidadeOpcao3.removeAllItems();
        jcbNomeEntidadeOpcao3.addItem("");

        listaCmeis = dao.listarCmeis();

        for (int i = 0; i < listaCmeis.size(); i++) {
            jcbNomeEntidadeOpcao3.addItem(listaCmeis.get(i));
        }

    }
    
    public void buscaCmei() {

        iniciaDao();
        //DAO dao = new DAO();
        Cmei cmei;

        if (jcbNomeEntidade.getSelectedItem().toString().isEmpty()) {
            
            jtfVagasDisponiveis.setText("0");
            
        } else {

            cmei = (Cmei) dao.buscaCmei((String) jcbNomeEntidade.getSelectedItem(), "", "", "");

            jtfVagasDisponiveis.setText("" + cmei.getVagasDisponiveis());

            if (cmei.getVagasDisponiveis() == 0) {

                jlbTipoCadastro.setText("O Cadastro será feito para Fila de Espera!");
                jrbBercario.setEnabled(true);
                jrbMaternal1.setEnabled(true);
                jrbMaternal2.setEnabled(true);
                jrbPre1.setEnabled(true);
                jrbPre2.setEnabled(true);

                jrbFilaEspera.setEnabled(true);
                jrbFilaEspera.setSelected(true);
                tipoCadastro = "FILA";

            } else {

                jlbTipoCadastro.setText("O Cadastro será feito para Matrícula!");
                jrbBercario.setEnabled(true);
                jrbMaternal1.setEnabled(true);
                jrbMaternal2.setEnabled(true);
                jrbPre1.setEnabled(true);
                jrbPre2.setEnabled(true);

                jrbFilaEspera.setEnabled(true);
                jrbMatricula.setEnabled(true);
                jrbMatricula.setSelected(true);
                tipoCadastro = "MATRICULA";

            }

        }

    }

    public void procedimentoParaSalvar() {

        if (!jftfCpfMae.getText().equals("   .   .   -  ")) {
            ValidaCpf validaCpf = new ValidaCpf();

            if (validaCpf.validaCpf(jftfCpfMae.getText())) {
            } else {
                jftfCpfMae.setText("");
            }
        }
        
        if (!jftfCpfMae.getText().equals("   .   .   -  ")) {
            ValidaCpf validaCpf = new ValidaCpf();

            if (validaCpf.validaCpf(jftfCpfMae.getText())) {
            } else {
                jftfCpfMae.setText("");
            }
        }
        
        if (!jftfCpfResponsavel.getText().equals("   .   .   -  ")) {
            ValidaCpf validaCpf = new ValidaCpf();

            if (validaCpf.validaCpf(jftfCpfResponsavel.getText())) {
            } else {
                jftfCpfResponsavel.setText("");
            }
        }
        
        if ((!jtfFiliacaoPai.getText().isEmpty() && (!jftfCpfPai.getText().equals("   .   .   -  ") || !jftfPassaportePai.getText().isEmpty())
                || (!jtfFiliacaoMae.getText().isEmpty() && (!jftfCpfMae.getText().equals("   .   .   -  ") ) || !jftfPassaporteMae.getText().isEmpty()))
                || (!jtfNomeResponsavel.getText().isEmpty() && jftfCpfResponsavel.getText().equals("   .   .   -  "))) {

            verificaCadastroAluno();

            if (opcao.equals("salvar")) {

                if (!alunoVerificado.isEmpty()) {
                    iniciaClasseGeral();
                    classeGeral.msgAtencao("Esse Aluno já esta cadastrado!");
                } else {
                    salvar();
                    opcao = "salvar";
                }

            } else if (opcao.equals("alterar")) {

                atualizar();
                opcao = "salvar";


            }

        } else {
            iniciaClasseGeral();
            classeGeral.msgAtencao("Informe os dados ao menos de um dos pais ou responsável, ou do Pai ou da Mãe ou do Responsável \n ");
        }

    }

    public void zerarCpfPais() {

        jftfCpfMae.setText("");
        jftfCpfPai.setText("");

    }

    public void zerarCpfResponsavel() {

        jftfCpfResponsavel.setText("");

    }

    public void zerarRgPais() {

        jftfRgMae.setText("");
        jftfRgPai.setText("");

    }

    public void zerarRgResponsavel() {

        jftfRgResponsavel.setText("");

    }

    public void zerarTelefoneAluno() {

        jftfTelefone.setText("");
        jftfCelular1.setText("");
        jftfCelular2.setText("");

    }

    public void zerarTelefonePais() {

        jftfTelefoneComercialMae.setText("");
        jftfTelefoneComercialPai.setText("");

    }

    public void zerarTelefoneResponsavel() {

        jftfTelefoneComercialResponsavel.setText("");
        jftfTelefoneResponsavel.setText("");

    }
    
//Geradores, Validadores===========================================================================================================================================
    public void iniciaAluno() {
        
        if (aluno == null) {
            aluno = new Aluno();
        }
        
    }
    
    public void iniciaClasseGeral() {
        
        if (classeGeral == null) {
            classeGeral = new ClasseGeral();
        }
        
    }
    
    public void iniciaDao() {

        if (dao == null) {
            dao = new DAO();
        }

    }
    
    public void iniciaControleCaracteresEspeciais() {
        
        if (controleCaracteresEspeciais == null) {
            controleCaracteresEspeciais = new ControleCaracteresEspeciais();
        }
        
    }
    
    public void iniciaControleNumeroCertidaoNascimento() {
        
        if (controleNumeroCertidaoNascimento == null) {
            controleNumeroCertidaoNascimento = new ControleNumeroCertidaoNascimento();
        }
        
    }
    
    public void iniciaTransferenciaAluno() {
        
        if (transferenciaAluno == null) {
            transferenciaAluno = new TransferenciaAluno();
        }
        
    }
    
    public void iniciaVerificadorModalidades() {
        
        if (verificadorModalidades == null) {
            verificadorModalidades = new VerificadorModalidades();
        }
        
    }
    
    public void verificaCadastroAluno() {

        iniciaDao();
        //DAO dao = new DAO();
        String cpfP; //CPF do Pai
        String cpfM; //CPF da Mãe

        if (jftfCpfPai.getText().equals("   .   .   -  ")) {
            cpfP = "";
        } else {
            cpfP = jftfCpfPai.getText();
        }

        if (jftfCpfMae.equals("   .   .   -  ")) {
            cpfM = "";
        } else {
            cpfM = jftfCpfMae.getText();
        }

        alunoVerificado = dao.verificarCadastroAluno(jtfNomeAluno.getText(), jtfFiliacaoPai.getText(), cpfP, jtfFiliacaoMae.getText(), cpfM);

    }

//Getters and Setters===========================================================================================================================================
    public void setAlunoBuscado(Aluno alunoBuscado) {

        if (alunoBuscado == null) {
            System.out.println("Nenhum Resultado!");
        } else {

            iniciaAluno();
            iniciaControleCaracteresEspeciais();
            
            jDateChooserCadastro.setDate(alunoBuscado.getDataCadastro());
            jDateChooserNascimento.setDate(alunoBuscado.getDataNascimento());
            jDateChooserNascimentoMae.setDate(alunoBuscado.getDataNascimentoMae());
            jDateChooserNascimentoPai.setDate(alunoBuscado.getDataNascimentoPai());
            jDateChooserNascimentoResponsavel.setDate(alunoBuscado.getDataNascimentoResponsavel());
            jDateChooserControleEgresso.setDate(alunoBuscado.getDataEgresso());
            jDateChooserDesistencia.setDate(alunoBuscado.getDataDesistencia());
            jtfAnoRegistroCertidaoNascimento.setText(alunoBuscado.getAnoRegistroCertidaoNascimento());
            jtfBairro.setText(alunoBuscado.getBairro());
            jtfCartorioCertidaoNascimento.setText(alunoBuscado.getCartorioCertidaoNascimento());
            jtfDigitoVerificadorCertidaoNascimento.setText(alunoBuscado.getDigitoVerificadorCertidaoNascimento());
            jtfEndereco.setText(controleCaracteresEspeciais.getCaracterEspecial(alunoBuscado.getEndereco()));
            jtfEnderecoComercialMae.setText(controleCaracteresEspeciais.getCaracterEspecial(alunoBuscado.getEnderecoComercialMae()));
            jtfEnderecoComercialPai.setText(controleCaracteresEspeciais.getCaracterEspecial(alunoBuscado.getEnderecoComercialPai()));
            jtfEnderecoResponsavel.setText(controleCaracteresEspeciais.getCaracterEspecial(alunoBuscado.getEnderecoResponsavel()));
            jtfEnderecoComercialResponsavel.setText(controleCaracteresEspeciais.getCaracterEspecial(alunoBuscado.getEnderecoComercialResponsavel()));
            jtfFiliacaoMae.setText(controleCaracteresEspeciais.getCaracterEspecial(alunoBuscado.getFiliacaoMae()));
            jtfFiliacaoPai.setText(controleCaracteresEspeciais.getCaracterEspecial(alunoBuscado.getFiliacaoPai()));
            jtfFolhaCertidaoNascimento.setText(alunoBuscado.getFolhaCertidaoNascimento());
            jtfLivroCertidaoNascimento.setText(alunoBuscado.getLivroCertidaoNascimento());
            jtfMatriculaAluno.setText(alunoBuscado.getMatricula());
            jtfMunicipio.setText(alunoBuscado.getMunicipio());
            jtfNaturalidade.setText(alunoBuscado.getNaturalidade());
            jtfNomeAluno.setText(controleCaracteresEspeciais.getCaracterEspecial(alunoBuscado.getNomeAluno()));
            jtfNumeroEndereco.setText(alunoBuscado.getNumeroEndereco());
            jtfNumeroEnderecoComercialMae.setText(alunoBuscado.getNumeroEnderecoComercialMae());
            jtfNumeroEnderecoComercialPai.setText(alunoBuscado.getNumeroEnderecoComercialPai());
            jtfNomeResponsavel.setText(controleCaracteresEspeciais.getCaracterEspecial(alunoBuscado.getNomeResponsavel()));
            jtfNumeroEnderecoResponsavel.setText(alunoBuscado.getNumeroEnderecoResponsavel());
            jtfNumeroEnderecoComercialResponsavel.setText(alunoBuscado.getNumeroEnderecoComercialResponsavel());
            jftfNumeroIdentificadorCopel.setText(alunoBuscado.getNumeroIdentificadorCopel());
            jtfProfissaoMae.setText(alunoBuscado.getProfissaoMae());
            jtfProfissaoPai.setText(alunoBuscado.getProfissaoPai());
            jtfProfissaoResponsavel.setText(alunoBuscado.getProfissaoResponsavel());
            jtfTermoCertidaoNascimento.setText(alunoBuscado.getTermoCertidaoNascimento());
            
            if (alunoBuscado.isBolsaFamilia()) {
                jcbBolsaFamilia.setSelectedIndex(1);
            } else {
                jcbBolsaFamilia.setSelectedIndex(0);
            }
            
            jcbNomeEntidade.setSelectedItem(alunoBuscado.getNomeEntidade());
            
            if (!alunoBuscado.getNomeEntidadeOpcao2().isEmpty()) {
                jcbNomeEntidadeOpcao2.setSelectedItem(alunoBuscado.getNomeEntidadeOpcao2());
            }
            
            if (!alunoBuscado.getNomeEntidadeOpcao3().isEmpty()) {
                jcbNomeEntidadeOpcao3.setSelectedItem(alunoBuscado.getNomeEntidadeOpcao3());
            }
            
            jcbSexo.setSelectedItem(alunoBuscado.getSexo());
            jcbUf.setSelectedItem(alunoBuscado.getUf());
            jftfCelular1.setText(alunoBuscado.getCelular1());
            jftfCelular2.setText(alunoBuscado.getCelular2());
            jftfCpfMae.setText(alunoBuscado.getCpfMae());
            jftfCpfPai.setText(alunoBuscado.getCpfPai());
            jftfCpfResponsavel.setText(alunoBuscado.getCpfResponsavel());
            jftfMatriculaCertidao.setText(alunoBuscado.getNumeroMatriculaCertidaoNascimento());
            jftfPassaporteMae.setText(alunoBuscado.getPassaporteMae());
            jftfPassaportePai.setText(alunoBuscado.getPassaportePai());
            jftfRgMae.setText(alunoBuscado.getRgMae());
            jftfRgPai.setText(alunoBuscado.getRgPai());
            jftfRgResponsavel.setText(alunoBuscado.getRgResponsavel());
            jftfTelefone.setText(alunoBuscado.getTelefone());
            jftfTelefoneComercialMae.setText(alunoBuscado.getTelefoneComercialMae());
            jftfTelefoneComercialPai.setText(alunoBuscado.getTelefoneComercialPai());
            jftfTelefoneResponsavel.setText(alunoBuscado.getTelefoneResponsavel());
            jftfTelefoneComercialResponsavel.setText(alunoBuscado.getTelefoneComercialResponsavel());

            if (alunoBuscado.getModalidade().equals("BERÇÁRIO")) {
                modalidade = "BERÇÁRIO";
                jrbBercario.setSelected(true);
            } else if (alunoBuscado.getModalidade().equals("MATERNAL I")) {
                modalidade = "MATERNAL I";
                jrbMaternal1.setSelected(true);
            } else if (alunoBuscado.getModalidade().equals("MATERNAL II")) {
                modalidade = "MATERNAL II";
                jrbMaternal2.setSelected(true);
            } else if (alunoBuscado.getModalidade().equals("PRÉ I")) {
                modalidade = "PRÉ I";
                jrbPre1.setSelected(true);
            } else if (alunoBuscado.getModalidade().equals("PRÉ II")) {
                modalidade = "PRÉ II";
                jrbPre2.setSelected(true);
            } 

            if (alunoBuscado.getTipoCadastro().equals("FILA")) {
                tipoCadastro = "FILA";
                jlbTipoCadastro.setText("O Cadastro será feito para Fila de Espera!");
                jrbFilaEspera.setSelected(true);
            } else if (alunoBuscado.getTipoCadastro().equals("MATRICULA")) {
                tipoCadastro = "MATRICULA";
                jlbTipoCadastro.setText("O Cadastro será feito para Matrícula!");
                jrbMatricula.setSelected(true);
            } else if (alunoBuscado.getTipoCadastro().equals("DESISTENCIA")) {
                tipoCadastro = "DESISTENCIA";
                jrbDesistencia.setSelected(true);
            } else if (alunoBuscado.getTipoCadastro().equals("EGRESSO")) {
                tipoCadastro = "EGRESSO";
                jrbEgresso.setSelected(true);
            } else if (alunoBuscado.getTipoCadastro().equals("TRANSFERENCIA")) {
                
                tipoCadastro = "TRANSFERENCIA";
                jlbTipoCadastro.setText("O Cadastro será feito para Transferência!");
                jrbTransferencia.setSelected(true);
                
                jftfHoraSolicitacaoTransferencia.setText(getHoraSetada(alunoBuscado.getDataSolicitacaoTransferencia().getHours(), alunoBuscado.getDataSolicitacaoTransferencia().getMinutes()));
                jDateChooserTransferencia.setDate(alunoBuscado.getDataSolicitacaoTransferencia());
            }

            codigo = alunoBuscado.getCodigoAluno();
            aluno.setCodigoAluno(codigo);

            desabilitaCampos();

            abilitaBotoes();
            
        }

    }

    public void setNumeroCaracteres() {

        jtfBairro.setDocument(new FixedLengthDocument(60));
        jtfEndereco.setDocument(new FixedLengthDocument(60));
        jtfFiliacaoMae.setDocument(new FixedLengthDocument(60));
        jtfFiliacaoPai.setDocument(new FixedLengthDocument(60));
        jtfMatriculaAluno.setDocument(new FixedLengthDocument(15));
        jtfMunicipio.setDocument(new FixedLengthDocument(60));
        jtfNomeAluno.setDocument(new FixedLengthDocument(60));
        jtfNumeroEndereco.setDocument(new FixedLengthDocument(15));
        jtfEnderecoComercialMae.setDocument(new FixedLengthDocument(60));
        jtfEnderecoComercialPai.setDocument(new FixedLengthDocument(60));
        jtfNumeroEnderecoComercialMae.setDocument(new FixedLengthDocument(15));
        jtfNumeroEnderecoComercialPai.setDocument(new FixedLengthDocument(15));
        jtfProfissaoMae.setDocument(new FixedLengthDocument(60));
        jtfProfissaoPai.setDocument(new FixedLengthDocument(60));
        
        jftfPassaporteMae.setDocument(new FixedLengthDocument(8));
        jftfPassaportePai.setDocument(new FixedLengthDocument(8));

        jtfAnoRegistroCertidaoNascimento.setDocument(new FixedLengthDocument(5));
        jtfCartorioCertidaoNascimento.setDocument(new FixedLengthDocument(120));
        jtfDigitoVerificadorCertidaoNascimento.setDocument(new FixedLengthDocument(2));
        jtfTermoCertidaoNascimento.setDocument(new FixedLengthDocument(7));
        jtfFolhaCertidaoNascimento.setDocument(new FixedLengthDocument(3));
        jtfLivroCertidaoNascimento.setDocument(new FixedLengthDocument(5));

        jtfNomeResponsavel.setDocument(new FixedLengthDocument(60));
        jtfProfissaoResponsavel.setDocument(new FixedLengthDocument(60));
        jtfEnderecoResponsavel.setDocument(new FixedLengthDocument(60));
        jtfEnderecoComercialResponsavel.setDocument(new FixedLengthDocument(60));
        jtfNumeroEnderecoComercialResponsavel.setDocument(new FixedLengthDocument(15));
        jtfNumeroEnderecoResponsavel.setDocument(new FixedLengthDocument(15));

    }

    public void getPermissoes() {

        Permissoes permissoes;
        iniciaDao();
        //DAO dao = new DAO();

        permissoes = (Permissoes) dao.buscaPermissoes(getNomeUsuario());

        cadastrarAluno = permissoes.isCadastrarAluno();

    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
        preencheComboCmeis();
        preencheComboCmeisOpcao2();
        preencheComboCmeisOpcao3();
        getPermissoes();
        jDateChooserCadastro.setDate(validaData.getDateAtual());
        abilitaCampos();
        desabilitaBotoes();
        jrbFilaEspera.setSelected(true);
        jlbTipoCadastro.setText("O Cadastro será feito para Fila de Espera!");
    }

    public String getTipoCadastroUsuario() {
        return tipoCadastroUsuario;
    }

    public void setTipoCadastroUsuario(String tipoCadastroUsuario) {
        this.tipoCadastroUsuario = tipoCadastroUsuario;
    }

    public JDateChooser getjDateChooserCadastro() {
        return jDateChooserCadastro;
    }

    public void setjDateChooserCadastro(JDateChooser jDateChooserCadastro) {
        this.jDateChooserCadastro = jDateChooserCadastro;
    }

    public JDateChooser getjDateChooserNascimento() {
        return jDateChooserNascimento;
    }

    public void setjDateChooserNascimento(JDateChooser jDateChooserNascimento) {
        this.jDateChooserNascimento = jDateChooserNascimento;
    }
    
    public String getHoraAtual() {

        GregorianCalendar calendar = new GregorianCalendar();
        Date data = calendar.getTime();

        String hora;
        String minuto;

        if (data.getHours() < 10) {
            hora = "0" + data.getHours();
        } else {
            hora = String.valueOf(data.getHours());
        }

        if (data.getMinutes() < 10) {
            minuto = "0" + data.getMinutes();
        } else {
            minuto = String.valueOf(data.getMinutes());
        }

        return hora + minuto;

    }
    
    public String getHoraSetada(int horas, int minutos) {
        
        String hora;
        String minuto;

        if (horas < 10) {
            hora = "0" + horas;
        } else {
            hora = String.valueOf(horas);
        }

        if (minutos < 10) {
            minuto = "0" + minutos;
        } else {
            minuto = String.valueOf(minutos);
        }

        return hora + minuto;
        
    }
    
//Metodos DAO===========================================================================================================================================
    public void salvar() {

        iniciaDao();
        iniciaAluno();
        iniciaControleCaracteresEspeciais();

        aluno.setAnoRegistroCertidaoNascimento(jtfAnoRegistroCertidaoNascimento.getText());
        aluno.setBairro(jtfBairro.getText());
        aluno.setCartorioCertidaoNascimento(jtfCartorioCertidaoNascimento.getText());
        aluno.setCelular1(jftfCelular1.getText());
        aluno.setCelular2(jftfCelular2.getText());
        aluno.setCpfMae(jftfCpfMae.getText());
        aluno.setCpfPai(jftfCpfPai.getText());
        aluno.setCpfResponsavel(jftfCpfResponsavel.getText());
        aluno.setDataCadastro(jDateChooserCadastro.getDate());
        aluno.setDataDesistencia(jDateChooserDesistencia.getDate());
        aluno.setDataEgresso(jDateChooserControleEgresso.getDate());
        aluno.setDataNascimento(jDateChooserNascimento.getDate());
        aluno.setDataNascimentoMae(jDateChooserNascimentoMae.getDate());
        aluno.setDataNascimentoPai(jDateChooserNascimentoPai.getDate());
        aluno.setDataNascimentoResponsavel(jDateChooserNascimentoResponsavel.getDate());
        aluno.setDataSolicitacaoTransferencia(jDateChooserTransferencia.getDate());
        aluno.setDigitoVerificadorCertidaoNascimento(jtfDigitoVerificadorCertidaoNascimento.getText());
        aluno.setEndereco(controleCaracteresEspeciais.getCodigoCaracterEpecial(jtfEndereco.getText()));
        aluno.setEnderecoComercialMae(controleCaracteresEspeciais.getCodigoCaracterEpecial(jtfEnderecoComercialMae.getText()));
        aluno.setEnderecoComercialPai(controleCaracteresEspeciais.getCodigoCaracterEpecial(jtfEnderecoComercialPai.getText()));
        aluno.setEnderecoResponsavel(controleCaracteresEspeciais.getCodigoCaracterEpecial(jtfEnderecoResponsavel.getText()));
        aluno.setEnderecoComercialResponsavel(controleCaracteresEspeciais.getCodigoCaracterEpecial(jtfEnderecoComercialResponsavel.getText()));
        aluno.setFiliacaoMae(controleCaracteresEspeciais.getCodigoCaracterEpecial(jtfFiliacaoMae.getText()));
        aluno.setFiliacaoPai(controleCaracteresEspeciais.getCodigoCaracterEpecial(jtfFiliacaoPai.getText()));
        aluno.setFolhaCertidaoNascimento(jtfFolhaCertidaoNascimento.getText());
        aluno.setLivroCertidaoNascimento(jtfLivroCertidaoNascimento.getText());
        aluno.setMatricula(jtfMatriculaAluno.getText());
        aluno.setModalidade(modalidade);
        aluno.setMunicipio(jtfMunicipio.getText());
        aluno.setNaturalidade(jtfNaturalidade.getText());
        aluno.setNomeAluno(controleCaracteresEspeciais.getCodigoCaracterEpecial(jtfNomeAluno.getText()));
        
        if (jcbBolsaFamilia.getSelectedIndex() == 0) {
            aluno.setBolsaFamilia(false);
        } else {
            aluno.setBolsaFamilia(true);
        }
        
        if (!tipoCadastro.equals("MATRICULA")) {
                
            aluno.setNomeEntidade(jcbNomeEntidade.getSelectedItem().toString());
                
            if (jcbNomeEntidadeOpcao2.getSelectedIndex() != 0) {
                aluno.setNomeEntidadeOpcao2(jcbNomeEntidadeOpcao2.getSelectedItem().toString());
            } else {
                aluno.setNomeEntidadeOpcao2("");
            }
                
            if (jcbNomeEntidadeOpcao3.getSelectedIndex() != 0) {
                aluno.setNomeEntidadeOpcao3(jcbNomeEntidadeOpcao3.getSelectedItem().toString());
            } else {
                aluno.setNomeEntidadeOpcao3("");
            }
                        
        } else if (!tipoCadastro.equals("TRANSFERENCIA")) {
            
            aluno.setNomeEntidade(jcbNomeEntidade.getSelectedItem().toString());
            aluno.setNomeEntidadeOpcao2(jcbNomeEntidadeOpcao2.getSelectedItem().toString());
            aluno.setNomeEntidadeOpcao3("");
            
        } else {
            
            aluno.setNomeEntidade(jcbNomeEntidade.getSelectedItem().toString());
            aluno.setNomeEntidadeOpcao2("");
            aluno.setNomeEntidadeOpcao3("");
            
        }
        
        aluno.setNumeroEndereco(jtfNumeroEndereco.getText());
        aluno.setNumeroEnderecoComercialMae(jtfNumeroEnderecoComercialMae.getText());
        aluno.setNumeroEnderecoComercialPai(jtfNumeroEnderecoComercialPai.getText());
        aluno.setNomeResponsavel(controleCaracteresEspeciais.getCodigoCaracterEpecial(jtfNomeResponsavel.getText()));
        aluno.setNumeroEnderecoResponsavel(jtfNumeroEnderecoResponsavel.getText());
        aluno.setNumeroEnderecoComercialResponsavel(jtfNumeroEnderecoComercialResponsavel.getText());
        aluno.setNumeroIdentificadorCopel(jftfNumeroIdentificadorCopel.getText());
        aluno.setNumeroMatriculaCertidaoNascimento(jftfMatriculaCertidao.getText());
        aluno.setPassaporteMae(jftfPassaporteMae.getText());
        aluno.setPassaportePai(jftfPassaportePai.getText());
        aluno.setProfissaoMae(jtfProfissaoMae.getText());
        aluno.setProfissaoPai(jtfProfissaoPai.getText());
        aluno.setProfissaoResponsavel(jtfProfissaoResponsavel.getText());
        aluno.setRgMae(jftfRgMae.getText());
        aluno.setRgPai(jftfRgPai.getText());
        aluno.setRgResponsavel(jftfRgResponsavel.getText());
        aluno.setTelefone(jftfTelefone.getText());
        aluno.setTelefoneComercialMae(jftfTelefoneComercialMae.getText());
        aluno.setTelefoneComercialPai(jftfTelefoneComercialPai.getText());
        aluno.setTelefoneResponsavel(jftfTelefoneResponsavel.getText());
        aluno.setTelefoneComercialResponsavel(jftfTelefoneComercialResponsavel.getText());
        aluno.setTermoCertidaoNascimento(jtfTermoCertidaoNascimento.getText());
        aluno.setTipoCadastro(tipoCadastro);
        aluno.setUf(jcbUf.getSelectedItem().toString());
        aluno.setSexo(jcbSexo.getSelectedItem().toString());

        dao.inserir(aluno, 7);
        cancelar();

    }

    public void atualizar() {

        iniciaDao();
        iniciaAluno();
        iniciaControleCaracteresEspeciais();

        aluno.setAnoRegistroCertidaoNascimento(jtfAnoRegistroCertidaoNascimento.getText());
        aluno.setBairro(jtfBairro.getText());
        aluno.setCartorioCertidaoNascimento(jtfCartorioCertidaoNascimento.getText());
        aluno.setCelular1(jftfCelular1.getText());
        aluno.setCelular2(jftfCelular2.getText());
        aluno.setCpfMae(jftfCpfMae.getText());
        aluno.setCpfPai(jftfCpfPai.getText());
        aluno.setCpfResponsavel(jftfCpfResponsavel.getText());
        aluno.setDataCadastro(jDateChooserCadastro.getDate());
        aluno.setDataDesistencia(jDateChooserDesistencia.getDate());
        aluno.setDataEgresso(jDateChooserControleEgresso.getDate());
        aluno.setDataNascimento(jDateChooserNascimento.getDate());
        aluno.setDataNascimentoMae(jDateChooserNascimentoMae.getDate());
        aluno.setDataNascimentoPai(jDateChooserNascimentoPai.getDate());
        aluno.setDataNascimentoResponsavel(jDateChooserNascimentoResponsavel.getDate());
        aluno.setDataSolicitacaoTransferencia(jDateChooserTransferencia.getDate());
        aluno.setDigitoVerificadorCertidaoNascimento(jtfDigitoVerificadorCertidaoNascimento.getText());
        aluno.setEndereco(controleCaracteresEspeciais.getCodigoCaracterEpecial(jtfEndereco.getText()));
        aluno.setEnderecoComercialMae(controleCaracteresEspeciais.getCodigoCaracterEpecial(jtfEnderecoComercialMae.getText()));
        aluno.setEnderecoComercialPai(controleCaracteresEspeciais.getCodigoCaracterEpecial(jtfEnderecoComercialPai.getText()));
        aluno.setEnderecoResponsavel(controleCaracteresEspeciais.getCodigoCaracterEpecial(jtfEnderecoResponsavel.getText()));
        aluno.setEnderecoComercialResponsavel(controleCaracteresEspeciais.getCodigoCaracterEpecial(jtfEnderecoComercialResponsavel.getText()));
        aluno.setFiliacaoMae(controleCaracteresEspeciais.getCodigoCaracterEpecial(jtfFiliacaoMae.getText()));
        aluno.setFiliacaoPai(controleCaracteresEspeciais.getCodigoCaracterEpecial(jtfFiliacaoPai.getText()));
        aluno.setFolhaCertidaoNascimento(jtfFolhaCertidaoNascimento.getText());
        aluno.setLivroCertidaoNascimento(jtfLivroCertidaoNascimento.getText());
        aluno.setMatricula(jtfMatriculaAluno.getText());
        aluno.setModalidade(modalidade);
        aluno.setMunicipio(jtfMunicipio.getText());
        aluno.setNaturalidade(jtfNaturalidade.getText());
        aluno.setNomeAluno(controleCaracteresEspeciais.getCodigoCaracterEpecial(jtfNomeAluno.getText()));
        
        if (jcbBolsaFamilia.getSelectedIndex() == 0) {
            aluno.setBolsaFamilia(false);
        } else {
            aluno.setBolsaFamilia(true);
        }
        
        if (!tipoCadastro.equals("MATRICULA")) {
                
            aluno.setNomeEntidade(jcbNomeEntidade.getSelectedItem().toString());
                
            if (jcbNomeEntidadeOpcao2.getSelectedIndex() != 0) {
                aluno.setNomeEntidadeOpcao2(jcbNomeEntidadeOpcao2.getSelectedItem().toString());
            } else {
                aluno.setNomeEntidadeOpcao2("");
            }
                
            if (jcbNomeEntidadeOpcao3.getSelectedIndex() != 0) {
                aluno.setNomeEntidadeOpcao3(jcbNomeEntidadeOpcao3.getSelectedItem().toString());
            } else {
                aluno.setNomeEntidadeOpcao3("");
            }
                        
        } else if (!tipoCadastro.equals("TRANSFERENCIA")) {
            
            aluno.setNomeEntidade(jcbNomeEntidade.getSelectedItem().toString());
            aluno.setNomeEntidadeOpcao2(jcbNomeEntidadeOpcao2.getSelectedItem().toString());
            aluno.setNomeEntidadeOpcao3("");
            
        } else {
            aluno.setNomeEntidade(jcbNomeEntidade.getSelectedItem().toString());
            aluno.setNomeEntidadeOpcao2("");
            aluno.setNomeEntidadeOpcao3("");
        }
        
        aluno.setNumeroEndereco(jtfNumeroEndereco.getText());
        aluno.setNumeroEnderecoComercialMae(jtfNumeroEnderecoComercialMae.getText());
        aluno.setNumeroEnderecoComercialPai(jtfNumeroEnderecoComercialPai.getText());
        aluno.setNomeResponsavel(controleCaracteresEspeciais.getCodigoCaracterEpecial(jtfNomeResponsavel.getText()));
        aluno.setNumeroEnderecoResponsavel(jtfNumeroEnderecoResponsavel.getText());
        aluno.setNumeroEnderecoComercialResponsavel(jtfNumeroEnderecoComercialResponsavel.getText());
        aluno.setNumeroIdentificadorCopel(jftfNumeroIdentificadorCopel.getText());
        aluno.setNumeroMatriculaCertidaoNascimento(jftfMatriculaCertidao.getText());
        aluno.setPassaporteMae(jftfPassaporteMae.getText());
        aluno.setPassaportePai(jftfPassaportePai.getText());
        aluno.setProfissaoMae(jtfProfissaoMae.getText());
        aluno.setProfissaoPai(jtfProfissaoPai.getText());
        aluno.setProfissaoResponsavel(jtfProfissaoResponsavel.getText());
        aluno.setRgMae(jftfRgMae.getText());
        aluno.setRgPai(jftfRgPai.getText());
        aluno.setRgResponsavel(jftfRgResponsavel.getText());
        aluno.setTelefone(jftfTelefone.getText());
        aluno.setTelefoneComercialMae(jftfTelefoneComercialMae.getText());
        aluno.setTelefoneComercialPai(jftfTelefoneComercialPai.getText());
        aluno.setTelefoneResponsavel(jftfTelefoneResponsavel.getText());
        aluno.setTelefoneComercialResponsavel(jftfTelefoneComercialResponsavel.getText());
        aluno.setTermoCertidaoNascimento(jtfTermoCertidaoNascimento.getText());
        aluno.setTipoCadastro(tipoCadastro);
        aluno.setUf(jcbUf.getSelectedItem().toString());
        aluno.setSexo(jcbSexo.getSelectedItem().toString());

        dao.atualizar(aluno, 7);
        cancelar();

    }

    public void excluir() {

        iniciaClasseGeral();

        if (classeGeral.msgConfirma("Deseja excluir este Cadastro?")) {

            iniciaDao();
            iniciaAluno();
            //DAO dao = new DAO();
            dao.excluir(aluno, 7);
            cancelar();

        }

    }
}
