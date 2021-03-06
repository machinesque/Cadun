/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * jfCadastroFuncaoCargo.java
 *
 * Created on 05/08/2010, 08:43:38
 */
package visual;

import conexao.Conexao;
import controlador.ClasseGeral;
import controlador.ControleImagem;
import controlador.ControleVersao;
import controlador.FixedLengthDocument;
import dao.DAO;
import excecoes.ExcCadastro;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Permissoes;
import modelo.Restricao;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author luizam
 */
public class jfCadastroRestricao extends javax.swing.JFrame {

    private static ControleVersao versao = new ControleVersao();
    private ClasseGeral classeGeral;
    private ControleImagem controleImagem = new ControleImagem();
    private DAO dao;
    private Restricao restricao;
    
    private jdBuscaRestricao jdbr;
    private JasperPrint jPrint;
    private JasperViewer jViewer;

    private int codigo;
    private String nomeUsuario;
    private String opcao = "salvar";
    private String restricaoVerificada;
    private boolean cadastrarRestricao;

    /** Creates new form jfCadastroFuncaoCargo */
    public jfCadastroRestricao() {
        super("Cadastro Restrição - " + versao.getVersao() + " - " + versao.getAno());
        initComponents();

        //Altera icone na barra de titulo
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.getImage(controleImagem.getIconeImagem());
        this.setIconImage(img);

        //centraliza tela
        setSize(getWidth(), getHeight());
        setLocationRelativeTo(null);

        setNumeroCaracteres();

        desabilitaBotoes();

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpBotoes = new javax.swing.JPanel();
        jbNovo = new javax.swing.JButton();
        jbSalvar = new javax.swing.JButton();
        jbBuscar = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();
        jbExcluir = new javax.swing.JButton();
        jbAlterar = new javax.swing.JButton();
        jbImprimir = new javax.swing.JButton();
        jpRestricao = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtfTipoRestricao = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaDescricaoRestricao = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jpBotoes.setBorder(new org.jdesktop.swingx.border.DropShadowBorder());

        jbNovo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/novo02.png"))); // NOI18N
        jbNovo.setText("Novo");
        jbNovo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbNovo.setEnabled(false);
        jbNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNovoActionPerformed(evt);
            }
        });

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
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jbNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap())
        );
        jpBotoesLayout.setVerticalGroup(
            jpBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jpRestricao.setBorder(new org.jdesktop.swingx.border.DropShadowBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Tipo de Restrição: ");

        jtfTipoRestricao.setBackground(new java.awt.Color(204, 204, 255));
        jtfTipoRestricao.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfTipoRestricao.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfTipoRestricao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfTipoRestricaoFocusLost(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Descrição da Restrição: ");

        jtaDescricaoRestricao.setBackground(new java.awt.Color(204, 204, 255));
        jtaDescricaoRestricao.setColumns(20);
        jtaDescricaoRestricao.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtaDescricaoRestricao.setLineWrap(true);
        jtaDescricaoRestricao.setRows(5);
        jtaDescricaoRestricao.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jScrollPane1.setViewportView(jtaDescricaoRestricao);

        javax.swing.GroupLayout jpRestricaoLayout = new javax.swing.GroupLayout(jpRestricao);
        jpRestricao.setLayout(jpRestricaoLayout);
        jpRestricaoLayout.setHorizontalGroup(
            jpRestricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpRestricaoLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jpRestricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpRestricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtfTipoRestricao)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpRestricaoLayout.setVerticalGroup(
            jpRestricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpRestricaoLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jpRestricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtfTipoRestricao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpRestricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpRestricao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jpBotoes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpRestricao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNovoActionPerformed
        cancelar();
}//GEN-LAST:event_jbNovoActionPerformed

    private void jbSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalvarActionPerformed

        if (!jtfTipoRestricao.getText().isEmpty() || !jtaDescricaoRestricao.getText().isEmpty()) {

            verificaCadastroRestricao();

            if (opcao.equals("salvar")) {

                if (!restricaoVerificada.isEmpty()) {
                    iniciaClasseGeral();
                    classeGeral.msgAtencao("Esse tipo de Restrição já esta cadastrada!");
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
            classeGeral.msgAtencao("Complete os Campos Obrigatórios, destacados em Azul!");

        }


    }//GEN-LAST:event_jbSalvarActionPerformed

    private void jbBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscarActionPerformed

        if (jdbr == null) {
            jdbr = new jdBuscaRestricao(null, true);
        }

        jdbr.setVisible(true);

        setRestricaoBuscada(jdbr.getRestricao());
        jdbr.setRestricao();

    }//GEN-LAST:event_jbBuscarActionPerformed

    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
        cancelar();
}//GEN-LAST:event_jbCancelarActionPerformed

    private void jbExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbExcluirActionPerformed
        excluir();
}//GEN-LAST:event_jbExcluirActionPerformed

    private void jbAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAlterarActionPerformed

        abilitaCampos();

        jbAlterar.setEnabled(false);

        opcao = "alterar";

        jbSalvar.setEnabled(true);
    }//GEN-LAST:event_jbAlterarActionPerformed

    private void jbImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbImprimirActionPerformed

        //ADOGeral ag = new ADOGeral();
        Conexao conexao = new Conexao();
        HashMap parametros = new HashMap();
        try {
            //System.out.println(codigoRegistro);
            //parametros.put("titulo", "Titulo Relatorio");
            parametros.put("codigoFuncaoCargo", new Integer(codigo));
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
            jPrint = JasperFillManager.fillReport("FuncaoCargo.jasper", parametros, conexao.getConexao());
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

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        fechar();
    }//GEN-LAST:event_formWindowClosing

    private void jtfTipoRestricaoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfTipoRestricaoFocusLost
        jtfTipoRestricao.setText(jtfTipoRestricao.getText().toUpperCase());
    }//GEN-LAST:event_jtfTipoRestricaoFocusLost
    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(new Runnable() {
    public void run() {
    new jfCadastroFuncaoCargo().setVisible(true);
    }
    });
    }*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbAlterar;
    private javax.swing.JButton jbBuscar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbExcluir;
    private javax.swing.JButton jbImprimir;
    private javax.swing.JButton jbNovo;
    private javax.swing.JButton jbSalvar;
    private javax.swing.JPanel jpBotoes;
    private javax.swing.JPanel jpRestricao;
    private javax.swing.JTextArea jtaDescricaoRestricao;
    private javax.swing.JTextField jtfTipoRestricao;
    // End of variables declaration//GEN-END:variables

//Outros Metodos ===========================================================================================================================================
    public void fechar() {

        if (!jtfTipoRestricao.getText().isEmpty() || !jtaDescricaoRestricao.getText().isEmpty()) {

            iniciaClasseGeral();
            classeGeral.msgAtencao("Cancele ou Salve o Cadastro em Edição!");

        } else {
            dao = null;
            this.dispose();
        }

    }

    public void cancelar() {

        jtfTipoRestricao.setText("");
        jtaDescricaoRestricao.setText("");
        desabilitaBotoes();
        jtaDescricaoRestricao.transferFocusBackward();
        
        abilitaCampos();
        
        restricao = null;
        
    }

    public void abilitaCampos() {

        jtfTipoRestricao.setEditable(true);
        jtaDescricaoRestricao.setEditable(true);

    }
    
    public void desabilitaCampos() {
        
        jtfTipoRestricao.setEditable(false);
        jtaDescricaoRestricao.setEditable(false);
        
    }

    public void abilitaBotoes() {

        jbNovo.setEnabled(true);
        jbCancelar.setEnabled(true);
        jbBuscar.setEnabled(true);

        if (cadastrarRestricao == true) {
            jbSalvar.setEnabled(false);
            jbExcluir.setEnabled(true);
            jbAlterar.setEnabled(true);
        } else {
            jbSalvar.setEnabled(false);
            jbExcluir.setEnabled(false);
            jbAlterar.setEnabled(false);
        }

    }

    public void desabilitaBotoes() {

        jbNovo.setEnabled(false);
        jbCancelar.setEnabled(true);
        jbBuscar.setEnabled(true);
        jbImprimir.setEnabled(false);

        if (cadastrarRestricao == true) {
            jbSalvar.setEnabled(true);
            jbExcluir.setEnabled(false);
            jbAlterar.setEnabled(false);
        } else {
            jbSalvar.setEnabled(false);
            jbExcluir.setEnabled(false);
            jbAlterar.setEnabled(false);
        }

    }

//Geradores, Validadores===========================================================================================================================================
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
    
    public void iniciaRestricao() {

        if (restricao == null) {
            restricao = new Restricao();
        }
        
    }
    
    public void verificaCadastroRestricao() {

        iniciaDao();
        iniciaRestricao();
        //DAO dao = new DAO();

        restricao = (Restricao) dao.buscaRestricao(jtfTipoRestricao.getText(), 0);
        
        if (restricao == null) {
            restricaoVerificada = "";
        } else {
            restricaoVerificada = restricao.getTipoRestricao();
        }
        
    }

//Getters and Setters===========================================================================================================================================
    public void setRestricaoBuscada(Restricao restricaoBuscado) {

        if (restricaoBuscado == null) {
        } else {

            iniciaRestricao();

            jtfTipoRestricao.setText(restricaoBuscado.getTipoRestricao());
            jtaDescricaoRestricao.setText(restricaoBuscado.getDescricaoRestricao());

            codigo = restricaoBuscado.getCodigoRestricao();
            restricao.setCodigoRestricao(codigo);

            desabilitaCampos();

            abilitaBotoes();

        }

    }

    public void setNumeroCaracteres() {

        jtfTipoRestricao.setDocument(new FixedLengthDocument(120));
        jtaDescricaoRestricao.setDocument(new FixedLengthDocument(2000));

    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
        getPermissoes();
        desabilitaBotoes();
    }

    public void getPermissoes() {

        Permissoes permissoes;
        iniciaDao();
        //DAO dao = new DAO();

        permissoes = (Permissoes) dao.buscaPermissoes(getNomeUsuario());

        cadastrarRestricao = permissoes.isCadastrarRestricao();

    }

//Metodos DAO===================================================================================================================================================
    public void salvar() {

        if (!jtfTipoRestricao.getText().isEmpty() || !jtaDescricaoRestricao.getText().isEmpty()) {

            iniciaDao();
            iniciaRestricao();

            restricao.setTipoRestricao(jtfTipoRestricao.getText());
            restricao.setDescricaoRestricao(jtaDescricaoRestricao.getText());

            dao.inserir(restricao, 7);
            cancelar();

        } else {
            iniciaClasseGeral();
            classeGeral.msgAtencao("Complete os Campos Obrigatórios, destacados em Azul!");
        }

    }

    public void atualizar() {

        if (!jtfTipoRestricao.getText().isEmpty() || !jtaDescricaoRestricao.getText().isEmpty()) {

            iniciaDao();
            iniciaRestricao();

            restricao.setTipoRestricao(jtfTipoRestricao.getText());
            restricao.setDescricaoRestricao(jtaDescricaoRestricao.getText());

            dao.atualizar(restricao, 7);
            cancelar();

        } else {
            iniciaClasseGeral();
            classeGeral.msgAtencao("Complete os Campos Obrigatórios, destacados em Azul!");
        }

    }

    public void excluir() {

        iniciaClasseGeral();

        if (classeGeral.msgConfirma("Deseja excluir esse cadastro?")) {

            iniciaDao();
            //DAO dao = new DAO();
            dao.excluir(restricao, 7);
            cancelar();
        }

    }
}
