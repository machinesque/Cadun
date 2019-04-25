/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador;

import dao.DAO;
import java.util.Date;
import java.util.GregorianCalendar;
import modelo.VersaoUsuario;

/**
 *
 * @author luiz
 */
public class ControleVersao {

    private DAO dao;
    private VersaoUsuario versaoUsuario = new VersaoUsuario();
    private GregorianCalendar calendar;
    
    private String versaoAtual;
    private String versaoSistema = "1.4.6";
    private String ano;
    private int codigoUsuario;

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getVersao() {
        iniciaDao();
        return versaoSistema;
    }
    
    public void setCodigoUsuario(int codigoUsuario) {
        
        iniciaDao();
        iniciaVersaoUsuario();
        
        versaoUsuario.setCodigoUsuario_Usuario(codigoUsuario);
        this.codigoUsuario = codigoUsuario;
        System.out.println("Codigo de Usuario = " + codigoUsuario);
        versaoUsuario.setVersaoAtual(dao.getVersaoUsuario(codigoUsuario));
        versaoAtual = dao.getVersaoAtual();
        
    }
    
//Geradores, Validadores===========================================================================================================================================
    public Date getDateHoje() {

        iniciaCalendar();
        Date data = calendar.getTime();

        return data;

    }
    
    public String getAno() {
        
        iniciaCalendar();
        Date data = calendar.getTime();

        String ano2 = (data.getYear() + 1900 + "");

        return ano2;
    }
    
    public boolean comparaVersao() {
        
        boolean avancar;
        
        iniciaDao();
        
        if (versaoSistema.equals(versaoAtual)) {
            avancar = true;
            salvar();
        } else {
            avancar = false;
        }
        
        return avancar;
        
    }
    
    public void iniciaDao() {
        
        if (dao == null) {
            dao = new DAO();
        }
        
    }
    
    public void iniciaVersaoUsuario() {
        
        if  (versaoUsuario == null) {
            versaoUsuario = new VersaoUsuario();
        }
        
    }
    
    public void iniciaCalendar() {
        
        if (calendar == null) {
            calendar = new GregorianCalendar();
        }
        
    }
    
//Metodos DAO===============================================================================================================================================================
    public void salvar() {
        
        iniciaDao();
        iniciaVersaoUsuario();
        
        if (versaoUsuario.getVersaoAtual().equals(versaoSistema)) {
            System.out.println("Versão OK!");
        } else {
            
            System.out.println("Codigo de Usuario = " + codigoUsuario);
            versaoUsuario.setCodigoUsuario_Usuario(this.codigoUsuario);
            versaoUsuario.setDataAtualizacao(getDateHoje());
            versaoUsuario.setVersaoAtual(versaoAtual);
            
            dao.inserir(versaoUsuario, 0);
            
        }
        
    }

}
