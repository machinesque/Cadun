/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.DAO;
import java.util.ArrayList;
import java.util.List;
import modelo.Aluno;
import visual.jdVerificaDuplicidades;

/**
 *
 * @author luizam
 */
public class ConfereDuplicidades implements Runnable {

    private Aluno aluno = new Aluno();
    private Aluno aluno2 = new Aluno();
    private DAO dao;
    private jdVerificaDuplicidades jdvd;
    private List listaDuplicidade1 = new ArrayList();
    private List listaDuplicidade2 = new ArrayList();
    private List listaDuplicidadeRelacao = new ArrayList();

//Outros Metodos ===========================================================================================================================================
    public void run() {

        confereDuplicidades();
        /*try {
         this.finalize();
         } catch (Throwable ex) {
         Logger.getLogger(ConfereDuplicidades.class.getName()).log(Level.SEVERE, null, ex);
         }*/

    }

    public void confereDuplicidades() {

        String codigoAluno1, codigoAluno2; // remover depois
        String nomeAluno1, nomeAluno2;
        String paiAluno1, paiAluno2;
        String maeAluno1, maeAluno2;
        String cpfPaiAluno1, cpfPaiAluno2;
        String cpfMaeAluno1, cpfMaeAluno2;

        iniciaDao();
        System.out.println("Confere aqui");

        listaDuplicidade1 = dao.listarAlunosCadastrados("", "", "", "", "", "", "", "", "");
        listaDuplicidade2 = dao.listarAlunosCadastrados("", "", "", "", "", "", "", "", "");

        for (int i = 0; i < listaDuplicidade1.size(); i++) {

            aluno = (Aluno) listaDuplicidade1.get(i);
            codigoAluno1 = String.valueOf(aluno.getCodigoAluno());
            nomeAluno1 = aluno.getNomeAluno();
            paiAluno1 = aluno.getFiliacaoPai();
            maeAluno1 = aluno.getFiliacaoMae();
            cpfMaeAluno1 = aluno.getCpfMae();
            cpfPaiAluno1 = aluno.getCpfPai();

            for (int j = 0; j < listaDuplicidade2.size(); j++) {

                aluno2 = (Aluno) listaDuplicidade2.get(j);
                codigoAluno2 = String.valueOf(aluno2.getCodigoAluno());
                nomeAluno2 = aluno2.getNomeAluno();
                paiAluno2 = aluno2.getFiliacaoPai();
                maeAluno2 = aluno2.getFiliacaoMae();
                cpfMaeAluno2 = aluno2.getCpfMae();
                cpfPaiAluno2 = aluno2.getCpfPai();

                if (aluno.getNomeAluno().equals(aluno2.getNomeAluno()) || (aluno.getFiliacaoPai().equals(aluno2.getFiliacaoPai()) && !aluno.getFiliacaoPai().isEmpty()) 
                        || (aluno.getFiliacaoMae().equals(aluno2.getFiliacaoMae()) && !aluno.getFiliacaoMae().isEmpty())) {

                    if ((!aluno.getCpfMae().equals("   .   .   -  ") && !aluno2.getCpfMae().equals("   .   .   -  ") && aluno.getCpfMae().equals(aluno2.getCpfMae()))
                            || (!aluno.getCpfPai().equals("   .   .   -  ") && !aluno2.getCpfMae().equals("   .   .   -  ")) && aluno.getCpfPai().equals(aluno2.getCpfPai())) {

                        efetivaVerificacaoListagem(j);

                    }

                }

                aluno2 = null;

                System.out.println("Qtde aluno lista 2 = " + j);

            }
            
            aluno = null;

            System.out.println("Qtde aluno lista 1 = " + i);
            System.out.println("Tamanho Lista 1 = " + listaDuplicidade1.size());
            System.out.println("Tamanho Lista 2 = " + listaDuplicidade2.size());

        }

        if (jdvd == null) {
            System.out.println("A Conferência Terminou!");
            jdvd = new jdVerificaDuplicidades(null, false);
        }

        jdvd.setListaDuplicidades(listaDuplicidadeRelacao);
        jdvd.setVisible(true);

    }

    public void efetivaVerificacaoListagem(int posicaoListaDuplicidade2) {

        listaDuplicidadeRelacao.add(aluno);
        listaDuplicidadeRelacao.add(aluno2);

        System.out.println("Provavel duplicidade aqui aluno " + aluno.getNomeAluno() + " = " + aluno2.getNomeAluno());
        System.out.println("Provavel duplicidade aqui Mae " + aluno.getFiliacaoMae() + " = " + aluno2.getFiliacaoMae());
        System.out.println("Provavel duplicidade aqui Pai " + aluno.getFiliacaoPai() + " = " + aluno2.getFiliacaoPai());
        System.out.println("Provavel duplicidade aqui cpfmae " + aluno.getCpfMae() + " = " + aluno2.getCpfMae());
        System.out.println("Provavel duplicidade aqui cpfpai " + aluno.getCpfPai() + " = " + aluno2.getCpfPai());
        System.out.println("");
        System.out.println("==============================================================================================");
        System.out.println("");

        listaDuplicidade2.remove(posicaoListaDuplicidade2);

    }

//Getters, Setters===========================================================================================================================================
//Geradores, Validadores===========================================================================================================================================
    public void iniciaDao() {

        if (dao == null) {
            dao = new DAO();
        }

    }
//Metodos DAO===========================================================================================================================================
}
