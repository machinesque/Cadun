/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.DAO;
import modelo.VersaoUsuario;

/**
 *
 * @author luizam
 */
public class ControleVersaoUsuario {

    private DAO dao;
    private VersaoUsuario versaoUsuario;

    public void iniciaDao() {

        if (dao == null) {
            dao = new DAO();
        }

    }

    public void iniciaVersaoUsuario() {

        if (versaoUsuario == null) {
            versaoUsuario = new VersaoUsuario();
        }

    }

    public String getVersaoAtual(int codigoVersaoUsuario) { //por enquanto sem utilidade

        iniciaVersaoUsuario();
        versaoUsuario = (VersaoUsuario) dao.busca(versaoUsuario, codigoVersaoUsuario);

        return versaoUsuario.getVersaoAtual();

    }
}
