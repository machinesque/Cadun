/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * jdBuscaAgendamento.java
 *
 * Created on 02/02/2010, 09:10:34
 */
package visual;

import controlador.ClasseGeral;
import controlador.ControleImagem;
import controlador.ControleVersao;
import controlador.FixedLengthDocument;
import dao.DAO;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.Servidor;

/**
 *
 * @author luizam
 */
public class jdBuscaServidor extends javax.swing.JDialog {

    private static ControleVersao versao = new ControleVersao();
    private DAO dao;
    private Servidor servidor = new Servidor();
    private ClasseGeral classeGeral;
    private ControleImagem controleImagem = new ControleImagem();
    
    private DefaultTableModel modelo;

    private String tipoCadastroUsuario;

    /** Creates new form jdBuscaAgendamento */
    public jdBuscaServidor(java.awt.Frame parent, boolean modal) {
        super(parent, "Buscar Sevidor - " + versao.getVersao() + " - " + versao.getAno(), modal);
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

        setNumeroCaracteres();

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
        jbBuscaFechar = new javax.swing.JButton();
        jbBuscaEnviarDados = new javax.swing.JButton();
        jbBuscaCancelar = new javax.swing.JButton();
        jbBuscaPesquisar = new javax.swing.JButton();
        jpBuscaServidor = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbBuscaServidor = new javax.swing.JTable();
        jcbBuscaNomeEntidade = new javax.swing.JComboBox();
        jtfBuscaNomeServidor = new javax.swing.JTextField();
        jcbBuscaFuncaoCargo = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jpBotoes.setBorder(new org.jdesktop.swingx.border.DropShadowBorder());

        jbBuscaFechar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbBuscaFechar.setText("Fechar");
        jbBuscaFechar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbBuscaFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscaFecharActionPerformed(evt);
            }
        });

        jbBuscaEnviarDados.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbBuscaEnviarDados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Enviar.png"))); // NOI18N
        jbBuscaEnviarDados.setText("Enviar Dados");
        jbBuscaEnviarDados.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbBuscaEnviarDados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscaEnviarDadosActionPerformed(evt);
            }
        });

        jbBuscaCancelar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbBuscaCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/cancelar.png"))); // NOI18N
        jbBuscaCancelar.setText("Cancelar");
        jbBuscaCancelar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbBuscaCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscaCancelarActionPerformed(evt);
            }
        });

        jbBuscaPesquisar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbBuscaPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/pesquisar.png"))); // NOI18N
        jbBuscaPesquisar.setText("Pesquisar");
        jbBuscaPesquisar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbBuscaPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscaPesquisarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpBotoesLayout = new javax.swing.GroupLayout(jpBotoes);
        jpBotoes.setLayout(jpBotoesLayout);
        jpBotoesLayout.setHorizontalGroup(
            jpBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBotoesLayout.createSequentialGroup()
                .addContainerGap(470, Short.MAX_VALUE)
                .addComponent(jbBuscaPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbBuscaCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbBuscaEnviarDados, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbBuscaFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpBotoesLayout.setVerticalGroup(
            jpBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbBuscaFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBuscaEnviarDados, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBuscaCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBuscaPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpBuscaServidor.setBorder(new org.jdesktop.swingx.border.DropShadowBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Nome do Servidor: ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Função / Cargo: ");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Entidade: ");

        jtbBuscaServidor.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jtbBuscaServidor.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtbBuscaServidor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome do Servidor", "Função / Cargo", "Nome Entidade", "Modalidade", "Status Contrato", "Matrícula"
            }
        ));
        jtbBuscaServidor.setGridColor(new java.awt.Color(204, 204, 204));
        jtbBuscaServidor.setRowHeight(19);
        jtbBuscaServidor.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jtbBuscaServidor.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jtbBuscaServidor);

        jcbBuscaNomeEntidade.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jcbBuscaNomeEntidade.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jtfBuscaNomeServidor.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtfBuscaNomeServidor.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfBuscaNomeServidor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfBuscaNomeServidorFocusLost(evt);
            }
        });

        jcbBuscaFuncaoCargo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jcbBuscaFuncaoCargo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jpBuscaServidorLayout = new javax.swing.GroupLayout(jpBuscaServidor);
        jpBuscaServidor.setLayout(jpBuscaServidorLayout);
        jpBuscaServidorLayout.setHorizontalGroup(
            jpBuscaServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBuscaServidorLayout.createSequentialGroup()
                .addGroup(jpBuscaServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpBuscaServidorLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jpBuscaServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpBuscaServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jcbBuscaNomeEntidade, 0, 518, Short.MAX_VALUE)
                            .addComponent(jtfBuscaNomeServidor)
                            .addComponent(jcbBuscaFuncaoCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBuscaServidorLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 878, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpBuscaServidorLayout.setVerticalGroup(
            jpBuscaServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBuscaServidorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBuscaServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jcbBuscaNomeEntidade, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBuscaServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtfBuscaNomeServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBuscaServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jcbBuscaFuncaoCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpBuscaServidor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jpBotoes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpBuscaServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        fechar();
    }//GEN-LAST:event_formWindowClosing

    private void jbBuscaFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscaFecharActionPerformed
        fechar();
    }//GEN-LAST:event_jbBuscaFecharActionPerformed

    private void jbBuscaPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscaPesquisarActionPerformed
        pesquisar();
    }//GEN-LAST:event_jbBuscaPesquisarActionPerformed

    private void jbBuscaCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscaCancelarActionPerformed
        cancelar();
    }//GEN-LAST:event_jbBuscaCancelarActionPerformed

    private void jbBuscaEnviarDadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscaEnviarDadosActionPerformed
        buscaDados();
        fechar();
    }//GEN-LAST:event_jbBuscaEnviarDadosActionPerformed

    private void jtfBuscaNomeServidorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfBuscaNomeServidorFocusLost
        jtfBuscaNomeServidor.setText(jtfBuscaNomeServidor.getText().toUpperCase());
    }//GEN-LAST:event_jtfBuscaNomeServidorFocusLost
    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(new Runnable() {
    public void run() {
    jdBuscaAgendamento dialog = new jdBuscaAgendamento(new javax.swing.JFrame(), true);
    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
    public void windowClosing(java.awt.event.WindowEvent e) {
    System.exit(0);
    }
    });
    dialog.setVisible(true);
    }
    });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbBuscaCancelar;
    private javax.swing.JButton jbBuscaEnviarDados;
    private javax.swing.JButton jbBuscaFechar;
    private javax.swing.JButton jbBuscaPesquisar;
    private javax.swing.JComboBox jcbBuscaFuncaoCargo;
    private javax.swing.JComboBox jcbBuscaNomeEntidade;
    private javax.swing.JPanel jpBotoes;
    private javax.swing.JPanel jpBuscaServidor;
    private javax.swing.JTable jtbBuscaServidor;
    private javax.swing.JTextField jtfBuscaNomeServidor;
    // End of variables declaration//GEN-END:variables

//Outros Metodos ===========================================================================================================================================
    public void fechar() {

        cancelar();
        this.dispose();

    }

    public void cancelar() {

        iniciaDefaulTable();

        jtfBuscaNomeServidor.setText("");
        jcbBuscaFuncaoCargo.setSelectedIndex(0);

        if (!tipoCadastroUsuario.equals("GERAL")) {
            jcbBuscaNomeEntidade.setSelectedItem(tipoCadastroUsuario);
        } else {
            jcbBuscaNomeEntidade.setSelectedIndex(0);
        }

        modelo.setRowCount(0);

    }

    public void pesquisar() {

        iniciaDefaulTable();
        iniciaDao();
        //DAO dao = new DAO();
        Servidor serv;
        List listaServidores = new ArrayList();
        String nomeEntid;
        String nomeServ;
        String funcCarg;

        if (jtfBuscaNomeServidor.getText().isEmpty()) {
            nomeServ = "";
        } else {
            nomeServ = jtfBuscaNomeServidor.getText();
        }

        if (jcbBuscaFuncaoCargo.getSelectedIndex() == 0) {
            funcCarg = "";
        } else {
            funcCarg = jcbBuscaFuncaoCargo.getSelectedItem().toString();
        }

        if (jcbBuscaNomeEntidade.getSelectedIndex() == 0) {
            nomeEntid = "";
        } else {
            nomeEntid = jcbBuscaNomeEntidade.getSelectedItem().toString();
        }

        listaServidores = (List) dao.listarServidoresCadastrados(nomeServ, nomeEntid, funcCarg);

        modelo.setRowCount(0);

        for (int i = 0; i < listaServidores.size(); i++) {

            serv = (Servidor) listaServidores.get(i);

            modelo.addRow(new String[]{String.valueOf(serv.getNomeServidor()), String.valueOf(serv.getFuncaoCargo()), String.valueOf(serv.getNomeEntidade()), String.valueOf(serv.getModalidade()), String.valueOf(serv.getStatusContrato()), String.valueOf(serv.getMatricula())});

        }

        if (jtbBuscaServidor.getRowCount() > 0) {
            jtbBuscaServidor.getSelectionModel().setSelectionInterval(0, 0);
        } else {
            iniciaClasseGeral();
            
            classeGeral.msgInforma("Não há servidor com estes dados!");
        }

    }

    public void buscaDados() {

        iniciaDao();
        iniciaServidor();
        //DAO dao = new DAO();

        String nomeServ;
        String funcCarg;
        String nomeEntid;
        String modal;
        String matric;

        nomeServ = jtbBuscaServidor.getValueAt(jtbBuscaServidor.getSelectedRow(), 0).toString();
        funcCarg = jtbBuscaServidor.getValueAt(jtbBuscaServidor.getSelectedRow(), 1).toString();
        nomeEntid = jtbBuscaServidor.getValueAt(jtbBuscaServidor.getSelectedRow(), 2).toString();
        modal = jtbBuscaServidor.getValueAt(jtbBuscaServidor.getSelectedRow(), 3).toString();
        matric = jtbBuscaServidor.getValueAt(jtbBuscaServidor.getSelectedRow(), 5).toString();

        servidor = (Servidor) dao.buscaServidor(nomeServ, funcCarg, nomeEntid, modal, matric);

    }

    public void preencheComboCmeis() {

        iniciaDao();
        //DAO dao = new DAO();
        List listaCmeis = new ArrayList();

        jcbBuscaNomeEntidade.removeAllItems();
        jcbBuscaNomeEntidade.addItem("");

        listaCmeis = dao.listarCmeis();

        for (int i = 0; i < listaCmeis.size(); i++) {
            jcbBuscaNomeEntidade.addItem(listaCmeis.get(i));
        }

        if (!tipoCadastroUsuario.equals("GERAL")) {
            jcbBuscaNomeEntidade.setSelectedItem(tipoCadastroUsuario);
            jcbBuscaNomeEntidade.setEnabled(false);
        }

    }

    public void preencheComboFuncaoCargo() {

        iniciaDao();
        List listaFuncoesCargos = new ArrayList();

        jcbBuscaFuncaoCargo.removeAllItems();
        jcbBuscaFuncaoCargo.addItem("");

        listaFuncoesCargos = dao.listarFuncoesCargos();

        for (int i = 0; i < listaFuncoesCargos.size(); i++) {
            jcbBuscaFuncaoCargo.addItem(listaFuncoesCargos.get(i));
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
    
    public void iniciaDefaulTable() {
        
        if (modelo == null) {
            modelo = (DefaultTableModel) jtbBuscaServidor.getModel();
        }
        
    }
    
    public void iniciaServidor() {
        
        if (servidor == null) {
            servidor = new Servidor();
        }
        
    }
    
//Getters and Setters===========================================================================================================================================
    public void setNumeroCaracteres() {

        jtfBuscaNomeServidor.setDocument(new FixedLengthDocument(240));

    }

    public Servidor getServidor() {
        return servidor;
    }

    public void setServidor() {
        servidor = null;
    }

    public String getTipoCadastroUsuario() {
        return tipoCadastroUsuario;
    }

    public void setTipoCadastroUsuario(String tipoCadastroUsuario) {
        this.tipoCadastroUsuario = tipoCadastroUsuario;
        preencheComboCmeis();
        preencheComboFuncaoCargo();
    }
    
//Metodos DAO===========================================================================================================================================
    
}