/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.DAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Aluno;
import modelo.Cmei;
import modelo.FuncaoCargo;
import modelo.Permissoes;
import modelo.Servidor;
import modelo.TransferenciaAluno;
import modelo.TransferenciaServidor;
import modelo.Usuario;
import modelo.VersaoUsuario;

/**
 *
 * @author luizam
 */
public class AtualizaBaseDados implements Runnable {

    private Aluno aluno;
    private DAO dao = null;
    private ControleUsuario controleUsuario;
    private Servidor servidor;
    private Servidor servidorVerificado;
    private TransferenciaAluno transferenciaAluno;
    private TransferenciaServidor transferenciaServidor;
    private Usuario usuario;
    private Usuario usuarioVerificado;
    
    private String alunoVerificado;
    private String cmeiVerificado;
    private String funcaoCargoVerificado;
    private String permissaoVerificado;
    private String transferenciaAlunoVerificada;
    private String transferenciaServidorVerificada;
    private String versaoUsuarioVerificada;
    private String nomeEntidade;

//Outros Metodos ===========================================================================================================================================
    public void run() {
        atualizaBaseDados();
        try {
            this.finalize();
        } catch (Throwable ex) {
            Logger.getLogger(AtualizaBaseDados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void atualizaBaseDados() {

        iniciaAluno();
        iniciaServidor();
        iniciaUsuario();
        Cmei cme;
        FuncaoCargo funcaoCarg;
        Permissoes permiss;
        VersaoUsuario versaoUsuario;

        List listaAlunosBkp = new ArrayList();//alunos do backup
        //List listaPermissoes = new ArrayList();
        List listaServidoresBkp = new ArrayList();
        List listaUsuariosBkp = new ArrayList();
        List listaVersaoUsuariosBkp = new ArrayList();
        List listaTransferenciaAlunoBkp = new ArrayList();
        List listaTransferenciaServidorBkp = new ArrayList();
        
        iniciaDao();

        listaAlunosBkp = (List) dao.listarAlunosBackup(nomeEntidade);
        //listaPermissoes = (List) dao.listarPermissoesBackpup();
        listaServidoresBkp = (List) dao.listarServidoresBackup(nomeEntidade);
        listaUsuariosBkp = (List) dao.listarUsuariosBackup(nomeEntidade);
        listaVersaoUsuariosBkp = (List) dao.listarVersaoUsuariosBackup();
        listaTransferenciaAlunoBkp = (List) dao.listarTransferenciaAlunoBackup();
        listaTransferenciaServidorBkp = (List) dao.listarTransferenciaServidorBackup();

        /*for (int i = 0; i < listaCmeis.size(); i++) {
        
        cme = (Cmei) listaCmeis.get(i);
        
        verificaCadastroCmei(cme);
        
        if (cmeiVerificado.isEmpty()) {
        System.out.println("O Cmei é " + cme.getNomeEntidade());
        cme.setCodigoCmei(0);
        inserir(cme);
        } else {
        atualizar(cme);
        }
        
        }*/

        for (int i = 0; i < listaAlunosBkp.size(); i++) {

            int codigoAlunoBkp;
            
            this.aluno = (Aluno) listaAlunosBkp.get(i);
            codigoAlunoBkp = this.aluno.getCodigoAluno();

            verificaCadastroAluno(this.aluno);

            if (alunoVerificado.isEmpty()) {
                System.out.println("O Aluno é " + this.aluno.getNomeAluno());
                this.aluno.setCodigoAluno(0);
                inserir(this.aluno);
                excluirAlunoDoBkp(this.aluno.getCodigoAluno());
            } else {

                if (verificaDataCadastroAluno(this.aluno)) {
                    atualizar(this.aluno);
                    excluirAlunoDoBkp(codigoAlunoBkp);
                } else if (verificaGeralCadastroAluno(this.aluno)) {
                    atualizar(this.aluno);
                    excluirAlunoDoBkp(codigoAlunoBkp);
                }

            }
        }

        for (int i = 0; i < listaServidoresBkp.size(); i++) {

            int codigoServidorBkp;
            
            this.servidor = (Servidor) listaServidoresBkp.get(i);
            codigoServidorBkp = this.servidor.getCodigoServidor();

            verificaCadastroServidor(this.servidor);

            if (servidorVerificado == null) {
                System.out.println("O Servidor é " + this.servidor.getNomeServidor());
                this.servidor.setCodigoServidor(0);
                inserir(this.servidor);
                excluirServidorDoBkp(this.servidor.getCodigoServidor());
            } else {
                atualizar(this.servidor);
                excluirServidorDoBkp(codigoServidorBkp);
            }

        }

        for (int i = 0; i < listaUsuariosBkp.size(); i++) {

            int codigoUsuarioBkp;
            
            this.usuario = (Usuario) listaUsuariosBkp.get(i);
            codigoUsuarioBkp = this.usuario.getCodigoUsuario();            

            verificaCadastroUsuario(this.usuario);

            if (usuarioVerificado == null) {
                System.out.println("O Usuário é " + this.usuario.getNomeUsuario());
                this.usuario.setCodigoUsuario(0);
                inserir(this.usuario);
                excluirUsuarioDoBkp(codigoUsuarioBkp);
            } else {
                atualizar(this.usuario);
                excluirUsuarioDoBkp(codigoUsuarioBkp);
            }

        }

        /*for (int i = 0; i < listaPermissoes.size(); i++) {

            permiss = (Permissoes) listaPermissoes.get(i);

            verificaCadastroPermissoes(permiss);

            if (permissaoVerificado.isEmpty()) {
                System.out.println("As permissões são do Usuário " + permiss.getNomeUsuario());
                permiss.setCodigoPermissoes(0);
                inserir(permiss);
            } else {
                atualizar(permiss);
            }

        }*/
        
        /*for (int i = 0; i < listaTransferenciaAlunoBkp.size(); i++) {
            
            iniciaTransferenciaAluno();
            
            transferenciaAluno = (TransferenciaAluno) listaTransferenciaAlunoBkp.get(i);
            
            verificaCadastroTransferenciaAluno(transferenciaAluno);
            
            if (transferenciaAlunoVerificada.isEmpty()) {
                System.out.println("O aluno transferido é " + transferenciaAluno.getNomeAluno());
                transferenciaAluno.setCodigoTransferenciaAluno(0);
                inserir(transferenciaAluno);
            } else {
                atualizar(transferenciaAluno);
            }
            
        }
        
        for (int i = 0; i < listaTransferenciaServidorBkp.size(); i++) {
            
            iniciaTransferenciaServidor();
            
            transferenciaServidor = (TransferenciaServidor) listaTransferenciaServidorBkp.get(i);
            
            verificaCadastroTransferenciaServidor(transferenciaServidor);
            
            if (transferenciaAlunoVerificada.isEmpty()) {
                System.out.println("O servidor transferido é " + transferenciaServidor.getNomeServidor());
                transferenciaServidor.setCodigoTransferenciaServidor(0);
                inserir(transferenciaServidor);
            } else {
                atualizar(transferenciaServidor);
            }
            
        }
        
        for (int i = 0; i < listaVersaoUsuariosBkp.size(); i++) {
            
            versaoUsuario = (VersaoUsuario) listaVersaoUsuariosBkp.get(i);
            
            verificaCadastroVersaoUsuario(versaoUsuario.getCodigoUsuario_Usuario(), versaoUsuario.getVersaoAtual());
            
            if (versaoUsuarioVerificada.isEmpty()) {
                iniciaControleUsuario();
                System.out.println("Versão " + versaoUsuario.getVersaoAtual() + " do usuario " + controleUsuario.getNomeUsuario(versaoUsuario.getCodigoUsuario_Usuario()));
                versaoUsuario.setCodigoVersaoUsuario(0);
                inserir(versaoUsuario);
            }
            
        }*/
        
        System.out.println("A atualização terminou!");

    }

//Geradores, Validadores===========================================================================================================================================
    public void verificaCadastroAluno(Aluno aluno) {

        iniciaDao();
        //DAO dao = new DAO();
        String cpfP; //CPF do Pai
        String cpfM; //CPF da Mãe

        alunoVerificado = "";

        if (aluno.getCpfPai().equals("   .   .   -  ")) {
            cpfP = "";
        } else {
            cpfP = aluno.getCpfPai();
        }

        if (aluno.getCpfMae().equals("   .   .   -  ")) {
            cpfM = "";
        } else {
            cpfM = aluno.getCpfMae();
        }

        alunoVerificado = dao.verificarCadastroAluno(aluno.getNomeAluno(), aluno.getFiliacaoPai(), cpfP, aluno.getFiliacaoMae(), cpfM);
        System.out.println(alunoVerificado);
    }

    public boolean verificaGeralCadastroAluno(Aluno aluno) {

        Aluno alun = null;
        List listaAluno = new ArrayList();
        
        boolean retorno = false;

        listaAluno = dao.listarAlunosCadastrados(aluno.getNomeAluno(), aluno.getCpfPai(), aluno.getCpfMae(), aluno.getFiliacaoPai(), aluno.getFiliacaoMae(), "", aluno.getNomeEntidade(), "", "");

        for (int i = 0; i < listaAluno.size(); i++) {

            alun = (Aluno) listaAluno.get(i);

            if (!alun.getModalidade().equals(aluno.getModalidade()) || !alun.getBairro().equals(aluno.getBairro()) || !alun.getCelular1().equals(aluno.getCelular1())
                    || alun.getDataDesistencia() != null || alun.getDataEgresso() != null || !alun.getEndereco().equals(aluno.getEndereco()) || ! !alun.getEnderecoComercialMae().equals(aluno.getEnderecoComercialMae())
                    || !alun.getEnderecoComercialPai().equals(aluno.getEnderecoComercialPai()) || !alun.getEnderecoComercialResponsavel().equals(aluno.getEnderecoComercialResponsavel())
                    || !alun.getEnderecoResponsavel().equals(aluno.getEnderecoResponsavel()) || !alun.getNumeroEndereco().equals(aluno.getNumeroEndereco()) || !alun.getNumeroEnderecoComercialMae().equals(aluno.getNumeroEnderecoComercialMae())
                    || !alun.getNumeroEnderecoComercialPai().equals(aluno.getNumeroEnderecoComercialPai()) || !alun.getNumeroEnderecoComercialResponsavel().equals(aluno.getNumeroEnderecoResponsavel())
                    || !alun.getNumeroEnderecoResponsavel().equals(aluno.getNumeroEnderecoResponsavel()) || !alun.getProfissaoMae().equals(aluno.getProfissaoMae()) || !alun.getProfissaoPai().equals(aluno.getProfissaoPai())
                    || !alun.getProfissaoResponsavel().equals(aluno.getProfissaoResponsavel()) || !alun.getTelefone().equals(aluno.getTelefone()) || !alun.getTelefoneComercialMae().equals(aluno.getTelefoneComercialMae())
                    || !alun.getTelefoneComercialPai().equals(aluno.getTelefoneComercialPai()) || !alun.getTelefoneComercialResponsavel().equals(aluno.getTelefoneComercialResponsavel())
                    || !alun.getTelefoneResponsavel().equals(aluno.getTelefoneResponsavel())) {
                System.out.println("Atualizado...");
                this.aluno.setCodigoAluno(alun.getCodigoAluno());
                retorno = true;
            } else {
                System.out.println("Não atualizado...");
                retorno = false;
            }

        }

        return retorno;

    }

    public boolean verificaDataCadastroAluno(Aluno aluno) {

        Aluno alun = null;
        List listaAluno = new ArrayList();

        listaAluno = dao.listarAlunosCadastrados(aluno.getNomeAluno(), aluno.getCpfPai(), aluno.getCpfMae(), aluno.getFiliacaoPai(), aluno.getFiliacaoMae(), "", 
                aluno.getNomeEntidade(), "", "");

        for (int i = 0; i < listaAluno.size(); i++) {

            alun = (Aluno) listaAluno.get(i);
            System.out.println(alun.getCodigoAluno());

        }

        
        //System.out.println("alun = " + alun.getTipoCadastro());
        //System.out.println("aluno = " + aluno.getTipoCadastro());
        
        if (!alun.getTipoCadastro().equals(aluno.getTipoCadastro()) && !aluno.getTipoCadastro().equals("FILA")) { //alun = semedcadun, aluno = semedcadunbackup
            System.out.println("Atualizado...");
            this.aluno.setCodigoAluno(alun.getCodigoAluno());
            return true;
        } else if (alun.getDataCadastro().after(aluno.getDataCadastro())) {
            System.out.println("Atualizado...");
            this.aluno.setCodigoAluno(alun.getCodigoAluno());
            return true;
        } else {
            System.out.println("Não atualizado...");
            return false;
        }
        
        /*if (alun.getTipoCadastro().equals(aluno.getTipoCadastro()) && aluno.getTipoCadastro().equals("FILA")) {
            if (alun.getDataCadastro().after(aluno.getDataCadastro())) {
                System.out.println("Atualizado...");
                return true;
            } else {
                System.out.println("Não atualizado...");
                return false;
            }
        } else {
            
        }*/

    }

    public void verificaCadastroServidor(Servidor servidor) {

        iniciaDao();

        servidorVerificado = null;

        servidorVerificado = (Servidor) dao.verificarCadastroServidor(servidor.getNomeServidor(), servidor.getMatricula());
        this.servidor.setCodigoServidor(servidorVerificado.getCodigoServidor());

    }

    public void verificaCadastroUsuario(Usuario usuario) {

        iniciaDao();

        usuarioVerificado = null;

        usuarioVerificado = (Usuario) dao.verificarNomeUsuario(usuario.getNomeUsuario());
        this.usuario.setCodigoUsuario(usuarioVerificado.getCodigoUsuario());

    }

    /*public void verificaCadastroPermissoes(Permissoes permissoes) {

        iniciaDao();

        permissaoVerificado = "";

        permissaoVerificado = dao.verificarCadastroPermissoes(permissoes.getNomeUsuario());

    }*/
    
    public void verificaCadastroVersaoUsuario(int codigoUsuario_Usuario, String versaoAtual) {
        
        iniciaDao();
        
        versaoUsuarioVerificada = "";
        
        versaoUsuarioVerificada = dao.verificarCadastroVersaoUsuario(codigoUsuario_Usuario, versaoAtual);
        
    }
    
    public void verificaCadastroTransferenciaServidor(TransferenciaServidor transfServidor) {
        
        iniciaDao();
        
        transferenciaServidorVerificada = "";
        
        transferenciaServidorVerificada = dao.verificarCadastroTransferenciaServidor(transfServidor.getNomeServidor(), transfServidor.getMatriculaServidor(), transfServidor.getNomeEntidadeAnterior(), transfServidor.getNomeEntidadeAtual());
        
    }
    
    public void verificaCadastroTransferenciaAluno(TransferenciaAluno transfAluno) {
        
        iniciaDao();
        
        transferenciaAlunoVerificada = "";
        
        transferenciaAlunoVerificada = dao.verificarCadastroTransferenciaAluno(transfAluno.getNomeAluno(), transfAluno.getMatriculaAluno(), transfAluno.getNomeEntidadeAnterior(), transfAluno.getNomeEntidadeAtual());
        
    }

    public void iniciaAluno() {
        
        if (aluno == null) {
            aluno = new Aluno();
        }
    }
    
    public void iniciaControleUsuario() {
        
        if (controleUsuario == null) {
            controleUsuario = new ControleUsuario();
        }
    }
    
    public void iniciaServidor() {
        
        if (servidor == null) {
            servidor = new Servidor();
        }
        
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
    
//Getters and Setters===========================================================================================================================================
    public String getNomeEntidade() {
        return nomeEntidade;
    }

    public void setNomeEntidade(String nomeEntidade) {
        this.nomeEntidade = nomeEntidade;
    }

//Metodos DAO===========================================================================================================================================
    public void iniciaDao() {

        if (dao == null) {
            dao = new DAO();
        }

    }

    public void inserir(Object obj) {

        iniciaDao();

        dao.inserir(obj, 0);

    }

    public void atualizar(Object obj) {

        iniciaDao();

        dao.atualizar(obj, 0);

    }
    
    public void excluirAlunoDoBkp(int codigoAluno) {
        
        iniciaDao();
        
        dao.excluirAlunoBkp(codigoAluno, 0);
        
    }
    
    public void excluirServidorDoBkp(int codigoServidor) {
        
        iniciaDao();
        
        dao.excluirServidorBkp(codigoServidor, 0);
        
    }
    
    public void excluirUsuarioDoBkp(int codigoUsuario) {
        
        iniciaDao();
        
        dao.excluirUsuarioBkp(codigoUsuario, 0);
        
    }
    
}