/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * jdVerificaDuplicidades.java
 *
 * Created on 02/12/2011, 10:47:54
 */
package visual;

import controlador.ClasseGeral;
import controlador.ControleImagem;
import controlador.ControleVersao;
import controlador.EditaOrdemTabela;
import dao.DAO;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import modelo.Aluno;

/**
 *
 * @author luizam
 */
public class jdVerificaDuplicidades extends javax.swing.JDialog {

    static ControleVersao versao = new ControleVersao();
    Aluno aluno = new Aluno();
    DAO dao;
    EditaOrdemTabela editaOrdemTabela;
    ControleImagem controleImagem = new ControleImagem();
    
    private List listaDuplicidades;
    private String nomeUsuario;

    /** Creates new form jdVerificaDuplicidades */
    public jdVerificaDuplicidades(java.awt.Frame parent, boolean modal) {
        super(parent, "Verifica Duplicidades - " + versao.getVersao() + " - " + versao.getAno(), modal);
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

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpmAcoesDuplicidades = new javax.swing.JPopupMenu();
        jmiExcluir = new javax.swing.JMenuItem();
        jspDuplicidades = new javax.swing.JScrollPane();
        jtbDuplicidades = new javax.swing.JTable();

        jmiExcluir.setText("Excluir");
        jmiExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiExcluirActionPerformed(evt);
            }
        });
        jpmAcoesDuplicidades.add(jmiExcluir);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jtbDuplicidades.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtbDuplicidades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "Filiação Pai", "CPF Pai", "Filiação Mãe", "CPF Mãe", "Data de Nascimento", "Data de Cadastro", "CMEI", "Tipo de Cadastro"
            }
        ));
        jtbDuplicidades.setComponentPopupMenu(jpmAcoesDuplicidades);
        jspDuplicidades.setViewportView(jtbDuplicidades);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jspDuplicidades, javax.swing.GroupLayout.DEFAULT_SIZE, 992, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jspDuplicidades, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jmiExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiExcluirActionPerformed

    ClasseGeral cg = new ClasseGeral();

    if (cg.msgConfirmaAtualizacao("Deseja Excluir o aluno selecionado?")) {
        buscaDados();
        excluir();
    } else {
        JOptionPane.showMessageDialog(null, "Você não tem permissões para usar esta opção!", "Atenção", JOptionPane.WARNING_MESSAGE, null);
    }

}//GEN-LAST:event_jmiExcluirActionPerformed
    /**
     * @param args the command line arguments
     */
    //public static void main(String args[]) {
        /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
     * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    

    
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//            if ("Nimbus".equals(info.getName())) {
//                try {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                } catch (ClassNotFoundException ex) {
//                    ex.printStackTrace();
//                } catch (InstantiationException ex) {
//                    ex.printStackTrace();
//                } catch (IllegalAccessException ex) {
//                    ex.printStackTrace();
//                } catch (UnsupportedLookAndFeelException ex) {
//                    ex.printStackTrace();
//                }
//                break;
//            }
//        }
//    }
//    
//    catch (ClassNotFoundException ex
//
//    
//        ) {
//            java.util.logging.Logger.getLogger(jdVerificaDuplicidades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//    }
//    
//    catch (InstantiationException ex
//
//    
//        ) {
//            java.util.logging.Logger.getLogger(jdVerificaDuplicidades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//    }
//    
//    catch (IllegalAccessException ex
//
//    
//        ) {
//            java.util.logging.Logger.getLogger(jdVerificaDuplicidades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//    }
//    
//    catch (javax.swing.UnsupportedLookAndFeelException ex
//
//    
//        ) {
//            java.util.logging.Logger.getLogger(jdVerificaDuplicidades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//    }
    //</editor-fold>

    /* Create and display the dialog */
    /*java.awt.EventQueue.invokeLater(new Runnable() {
    
    public void run() {
    jdVerificaDuplicidades dialog = new jdVerificaDuplicidades(new javax.swing.JFrame(), true);
    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
    
    @Override
    public void windowClosing(java.awt.event.WindowEvent e) {
    System.exit(0);
    }
    });
    dialog.setVisible(true);
    }
    });
    }*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem jmiExcluir;
    private javax.swing.JPopupMenu jpmAcoesDuplicidades;
    private javax.swing.JScrollPane jspDuplicidades;
    private javax.swing.JTable jtbDuplicidades;
    // End of variables declaration//GEN-END:variables

//Outros Metodos ===========================================================================================================================================    
    public void buscaDados() {

        iniciaDao();
        //DAO dao = new DAO();

        int codigo;

        codigo = Integer.valueOf((String) jtbDuplicidades.getValueAt(jtbDuplicidades.getSelectedRow(), 0));

        aluno = (Aluno) dao.busca(aluno, codigo);

    }

    public void preencheTabela() {

        DefaultTableModel modelo = (DefaultTableModel) jtbDuplicidades.getModel();
        final TableRowSorter<TableModel> tabelaSorter = new TableRowSorter<TableModel>(modelo);
        jtbDuplicidades.setRowSorter(tabelaSorter);

        modelo.setRowCount(0);

        for (int i = 0; i < listaDuplicidades.size(); i++) {

            aluno = (Aluno) listaDuplicidades.get(i);

            modelo.addRow(new String[]{String.valueOf(aluno.getCodigoAluno()), String.valueOf(aluno.getNomeAluno()), String.valueOf(aluno.getFiliacaoPai()),
                        String.valueOf(aluno.getCpfPai()), String.valueOf(aluno.getFiliacaoMae()), String.valueOf(aluno.getCpfMae()), String.valueOf(aluno.getDataNascimento()),
                        String.valueOf(aluno.getDataCadastro()), String.valueOf(aluno.getNomeEntidade()), String.valueOf(aluno.getTipoCadastro())});

            if (jtbDuplicidades.getRowCount() > 0) {
                jtbDuplicidades.getSelectionModel().setSelectionInterval(0, 0);
            }
        }

    }
    
//Geradores, Validadores===========================================================================================================================================
     public void iniciaDao() {

        if (dao == null) {
            dao = new DAO();
        }

    }
     
    public void iniciaEditaOrdemTabela() {
        
        if (editaOrdemTabela == null) {
            editaOrdemTabela = new EditaOrdemTabela();
        }
        
    }
    
    public void iniciaLista() {
        
        if (listaDuplicidades == null) {
            listaDuplicidades = new ArrayList();
        }
        
    }
     
//Getters and Setters===========================================================================================================================================
    public List getListaDuplicidades() {
        return listaDuplicidades;
    }

    public void setListaDuplicidades(List listaDuplicidades) {
        iniciaLista();
        this.listaDuplicidades = listaDuplicidades;
        preencheTabela();
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

//Metodos DAO===========================================================================================================================================
    public void excluir() {

        iniciaDao();

        dao.excluir(aluno, 7);

    }
}